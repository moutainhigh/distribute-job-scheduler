// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: worker.proto

package com.ylpu.thales.scheduler.core.rpc.entity;

public interface JobStatusRequestRpcOrBuilder extends
    // @@protoc_insertion_point(interface_extends:JobStatusRequestRpc)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>string requestId = 1;</code>
   */
  java.lang.String getRequestId();
  /**
   * <code>string requestId = 1;</code>
   */
  com.google.protobuf.ByteString
      getRequestIdBytes();

  /**
   * <code>int32 taskState = 2;</code>
   */
  int getTaskState();

  /**
   * <code>bytes data = 3;</code>
   */
  com.google.protobuf.ByteString getData();
}
