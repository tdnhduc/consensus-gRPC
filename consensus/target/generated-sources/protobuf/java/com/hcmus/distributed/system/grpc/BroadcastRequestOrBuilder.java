// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: Msg.proto

package com.hcmus.distributed.system.grpc;

public interface BroadcastRequestOrBuilder extends
    // @@protoc_insertion_point(interface_extends:com.hcmus.distributed.system.grpc.BroadcastRequest)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   **
   *Need to add all every broad cast request
   * </pre>
   *
   * <code>.com.hcmus.distributed.system.grpc.BaseRequest baseRequest = 1;</code>
   */
  boolean hasBaseRequest();
  /**
   * <pre>
   **
   *Need to add all every broad cast request
   * </pre>
   *
   * <code>.com.hcmus.distributed.system.grpc.BaseRequest baseRequest = 1;</code>
   */
  com.hcmus.distributed.system.grpc.BaseRequest getBaseRequest();
  /**
   * <pre>
   **
   *Need to add all every broad cast request
   * </pre>
   *
   * <code>.com.hcmus.distributed.system.grpc.BaseRequest baseRequest = 1;</code>
   */
  com.hcmus.distributed.system.grpc.BaseRequestOrBuilder getBaseRequestOrBuilder();

  /**
   * <code>string pidNeedConfirm = 2;</code>
   */
  java.lang.String getPidNeedConfirm();
  /**
   * <code>string pidNeedConfirm = 2;</code>
   */
  com.google.protobuf.ByteString
      getPidNeedConfirmBytes();

  /**
   * <code>bool isLegal = 3;</code>
   */
  boolean getIsLegal();
}
