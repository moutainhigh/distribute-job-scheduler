package com.ylpu.thales.scheduler.executor.flink;

public class FlinkParameters {
    
    private String mode;
    private Integer slotNumber;
    private String applicationName;
    private Integer taskManagerNumber;
    private String taskManagerMemory;
    private String jobManagerMemory;
    private String className;
    private String jarName;
    
    public String getMode() {
        return mode;
    }
    public void setMode(String mode) {
        this.mode = mode;
    }
    public Integer getSlotNumber() {
        return slotNumber;
    }
    public void setSlotNumber(Integer slotNumber) {
        this.slotNumber = slotNumber;
    }
    public String getApplicationName() {
        return applicationName;
    }
    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }
    public Integer getTaskManagerNumber() {
        return taskManagerNumber;
    }
    public void setTaskManagerNumber(Integer taskManagerNumber) {
        this.taskManagerNumber = taskManagerNumber;
    }
    public String getTaskManagerMemory() {
        return taskManagerMemory;
    }
    public void setTaskManagerMemory(String taskManagerMemory) {
        this.taskManagerMemory = taskManagerMemory;
    }
    public String getJobManagerMemory() {
        return jobManagerMemory;
    }
    public void setJobManagerMemory(String jobManagerMemory) {
        this.jobManagerMemory = jobManagerMemory;
    }
    public String getClassName() {
        return className;
    }
    public void setClassName(String className) {
        this.className = className;
    }
    public String getJarName() {
        return jarName;
    }
    public void setJarName(String jarName) {
        this.jarName = jarName;
    }
}
