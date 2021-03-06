// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: job.proto

package com.ylpu.thales.scheduler.core.rpc.entity;

public interface JobInstanceRequestRpcOrBuilder extends
        // @@protoc_insertion_point(interface_extends:JobInstanceRequestRpc)
        com.google.protobuf.MessageOrBuilder {

    /**
     * <code>int32 id = 1;</code>
     */
    int getId();

    /**
     * <code>string requestId = 2;</code>
     */
    java.lang.String getRequestId();

    /**
     * <code>string requestId = 2;</code>
     */
    com.google.protobuf.ByteString getRequestIdBytes();

    /**
     * <code>.JobRequestRpc job = 3;</code>
     */
    boolean hasJob();

    /**
     * <code>.JobRequestRpc job = 3;</code>
     */
    com.ylpu.thales.scheduler.core.rpc.entity.JobRequestRpc getJob();

    /**
     * <code>.JobRequestRpc job = 3;</code>
     */
    com.ylpu.thales.scheduler.core.rpc.entity.JobRequestRpcOrBuilder getJobOrBuilder();

    /**
     * <code>int32 taskState = 4;</code>
     */
    int getTaskState();

    /**
     * <code>string logUrl = 5;</code>
     */
    java.lang.String getLogUrl();

    /**
     * <code>string logUrl = 5;</code>
     */
    com.google.protobuf.ByteString getLogUrlBytes();

    /**
     * <code>string logPath = 6;</code>
     */
    java.lang.String getLogPath();

    /**
     * <code>string logPath = 6;</code>
     */
    com.google.protobuf.ByteString getLogPathBytes();

    /**
     * <code>string worker = 7;</code>
     */
    java.lang.String getWorker();

    /**
     * <code>string worker = 7;</code>
     */
    com.google.protobuf.ByteString getWorkerBytes();

    /**
     * <code>string creatorName = 8;</code>
     */
    java.lang.String getCreatorName();

    /**
     * <code>string creatorName = 8;</code>
     */
    com.google.protobuf.ByteString getCreatorNameBytes();

    /**
     * <code>string creatorEmail = 9;</code>
     */
    java.lang.String getCreatorEmail();

    /**
     * <code>string creatorEmail = 9;</code>
     */
    com.google.protobuf.ByteString getCreatorEmailBytes();

    /**
     * <code>int32 retryTimes = 10;</code>
     */
    int getRetryTimes();

    /**
     * <code>int32 pid = 11;</code>
     */
    int getPid();

    /**
     * <code>string applicationid = 12;</code>
     */
    java.lang.String getApplicationid();

    /**
     * <code>string applicationid = 12;</code>
     */
    com.google.protobuf.ByteString getApplicationidBytes();

    /**
     * <code>.google.protobuf.Timestamp scheduleTime = 13;</code>
     */
    boolean hasScheduleTime();

    /**
     * <code>.google.protobuf.Timestamp scheduleTime = 13;</code>
     */
    com.google.protobuf.Timestamp getScheduleTime();

    /**
     * <code>.google.protobuf.Timestamp scheduleTime = 13;</code>
     */
    com.google.protobuf.TimestampOrBuilder getScheduleTimeOrBuilder();

    /**
     * <code>.google.protobuf.Timestamp startTime = 14;</code>
     */
    boolean hasStartTime();

    /**
     * <code>.google.protobuf.Timestamp startTime = 14;</code>
     */
    com.google.protobuf.Timestamp getStartTime();

    /**
     * <code>.google.protobuf.Timestamp startTime = 14;</code>
     */
    com.google.protobuf.TimestampOrBuilder getStartTimeOrBuilder();

    /**
     * <code>.google.protobuf.Timestamp endTime = 15;</code>
     */
    boolean hasEndTime();

    /**
     * <code>.google.protobuf.Timestamp endTime = 15;</code>
     */
    com.google.protobuf.Timestamp getEndTime();

    /**
     * <code>.google.protobuf.Timestamp endTime = 15;</code>
     */
    com.google.protobuf.TimestampOrBuilder getEndTimeOrBuilder();

    /**
     * <code>int32 elapseTime = 16;</code>
     */
    int getElapseTime();
}
