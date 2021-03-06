package com.ylpu.thales.scheduler.executor;

import com.google.protobuf.ByteString;
import com.ylpu.thales.scheduler.core.config.Configuration;
import com.ylpu.thales.scheduler.core.curator.CuratorHelper;
import com.ylpu.thales.scheduler.core.rpc.entity.JobInstanceRequestRpc;
import com.ylpu.thales.scheduler.core.rpc.entity.JobStatusRequestRpc;
import com.ylpu.thales.scheduler.core.utils.ByteUtils;
import com.ylpu.thales.scheduler.core.utils.DateUtils;
import com.ylpu.thales.scheduler.core.utils.FileUtils;
import com.ylpu.thales.scheduler.core.utils.MetricsUtils;
import com.ylpu.thales.scheduler.core.utils.TaskProcessUtils;
import com.ylpu.thales.scheduler.enums.TaskState;
import com.ylpu.thales.scheduler.executor.rpc.client.WorkerGrpcClient;
import com.ylpu.thales.scheduler.request.JobInstanceRequest;

import java.io.File;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class AbstractCommonExecutor {

    private static Log LOG = LogFactory.getLog(AbstractCommonExecutor.class);

    public static final int LOG_SERVER_PORT = 9099;

    public static final String DEFAULT_LOG_DIR = "/tmp/log/worker";

    private JobInstanceRequest request;

    private JobInstanceRequestRpc requestRpc;

    public AbstractCommonExecutor() {

    }

    public AbstractCommonExecutor(JobInstanceRequestRpc requestRpc, JobInstanceRequest request) {
        this.requestRpc = requestRpc;
        this.request = request;
    }

    public abstract void preExecute() throws Exception;
    
    public abstract void postExecute() throws Exception;
    
    public abstract void kill() throws Exception;

    public abstract String[] buildCommand(String configFile) throws Exception;
    
    public String getJobName() {
        return requestRpc.getJob().getJobName() + "-" + 
                DateUtils.getDateAsString(DateUtils.getDatetime(requestRpc.getScheduleTime()),DateUtils.TIME_FORMAT) + 
                "-" + requestRpc.getRetryTimes();
    }
    

    public void killProcess() throws Exception {
        Integer pid = requestRpc.getPid();
        if (pid != null) {
            TaskProcessUtils.execCommand("./src/script/killProcess.sh", "/tmp/pid/" + pid + ".out",
                    "/tmp/pid/" + pid + ".error", pid);
        }
    }

    public void execute() throws Exception {

        Properties prop = Configuration.getConfig();
        int logServerPort = Configuration.getInt(prop, "thales.log.server.port", LOG_SERVER_PORT);

        String logDir = Configuration.getString(prop, "thales.worker.log.path", DEFAULT_LOG_DIR);
        String logPath = logDir + File.separator + requestRpc.getJob().getId() + "-" + request.getId() + "-"
                + DateUtils.getDateAsString(request.getStartTime(), DateUtils.TIME_FORMAT);
        String logOutPath = logPath + ".out";
        String logUrl = "http://" + MetricsUtils.getHostName() + ":" + logServerPort + "/api/log/viewLog/"
                + requestRpc.getId();
        try {
            
            request.setLogPath(logOutPath);
            request.setLogUrl(logUrl);
            
            String[] command = buildCommand(requestRpc.getJob().getJobConfiguration());
            Process process = Runtime.getRuntime().exec(command);
            FileUtils.writeOuput(process.getInputStream(), logOutPath);
            FileUtils.writeOuput(process.getErrorStream(), logOutPath);
            Long pid = TaskProcessUtils.getLinuxPid(process);

            request.setPid(pid.intValue());
            request.setTaskState(TaskState.RUNNING.getCode());

            // 修改任务状态
            JobStatusRequestRpc jobStatusRequestRpc = buildJobStatusRequestRpc(requestRpc.getRequestId(), TaskState.RUNNING,
                    request);
            transitJobStatusToRunning(jobStatusRequestRpc);

            int c = process.waitFor();
            if (c != 0) {
                throw new RuntimeException("failed to execute task " + requestRpc.getId());
            }
        } catch (Exception e) {
            // execute exception
            FileUtils.writeFile("failed to execute task " + request.getId() + " with exception " + e.getMessage(),logOutPath);
            throw new RuntimeException(e);
        }
    }

    public JobStatusRequestRpc buildJobStatusRequestRpc(String requestId, TaskState taskState, JobInstanceRequest request) {
        JobStatusRequestRpc.Builder builder = JobStatusRequestRpc.newBuilder();
        builder.setRequestId(requestId);
        builder.setTaskState(taskState.getCode());
        builder.setData(ByteString.copyFrom(ByteUtils.objectToByteArray(request)));
        return builder.build();
    }

    public int transitJobStatusToRunning(JobStatusRequestRpc request) {
        WorkerGrpcClient client = null;
        int returnCode = 200;
        String master = "";
        //确保master连上active的master，如果master在切换中，需要一直等待
        while(true) {
            try {
                master = CuratorHelper.getActiveMaster(); 
                if(StringUtils.isNoneBlank(master)) {
                    String[] hostAndPort = master.split(":");
                    client = new WorkerGrpcClient(hostAndPort[0], NumberUtils.toInt(hostAndPort[1]));
                    returnCode = client.updateJobStatus(request);
                    break;  
                }
            }catch (Exception e) {
                returnCode = 500;
                LOG.error(e);
            }finally {
                if (client != null) {
                    try {
                        client.shutdown();
                    } catch (InterruptedException e) {
                        LOG.error(e);
                    }
                }
            }
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e1) {
                LOG.error(e1);
            }
        }
        return returnCode;
    }
    
    public String replaceParameters(Map<String, Object> parameters, String fileContent) {
        if (parameters != null) {
            for (Entry<String, Object> entry : parameters.entrySet()) {
                Class<?> cls = entry.getValue().getClass();
                if (cls == Integer.class || cls == Double.class || cls == Long.class || cls == Float.class) {
                    fileContent = fileContent.replace("${" + entry.getKey() + "}", String.valueOf(entry.getValue()));
                } else {
                    fileContent = fileContent.replace("${" + entry.getKey() + "}",
                            "'" + String.valueOf(entry.getValue()) + "'");
                }
            }
            return fileContent;
        }
        return "";
    }
}
