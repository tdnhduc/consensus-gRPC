package com.hcmus.distributed.system.grpc;

import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.4.0)",
    comments = "Source: Msg.proto")
public final class ConsensusServiceGrpc {

  private ConsensusServiceGrpc() {}

  public static final String SERVICE_NAME = "com.hcmus.distributed.system.grpc.ConsensusService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.hcmus.distributed.system.grpc.BroadcastRequest,
      com.hcmus.distributed.system.grpc.BroadcastResponse> METHOD_BROADCAST =
      io.grpc.MethodDescriptor.<com.hcmus.distributed.system.grpc.BroadcastRequest, com.hcmus.distributed.system.grpc.BroadcastResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "com.hcmus.distributed.system.grpc.ConsensusService", "broadcast"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.hcmus.distributed.system.grpc.BroadcastRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.hcmus.distributed.system.grpc.BroadcastResponse.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.hcmus.distributed.system.grpc.BaseRequest,
      com.hcmus.distributed.system.grpc.BaseResponse> METHOD_SCHEDULE =
      io.grpc.MethodDescriptor.<com.hcmus.distributed.system.grpc.BaseRequest, com.hcmus.distributed.system.grpc.BaseResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "com.hcmus.distributed.system.grpc.ConsensusService", "schedule"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.hcmus.distributed.system.grpc.BaseRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.hcmus.distributed.system.grpc.BaseResponse.getDefaultInstance()))
          .build();

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ConsensusServiceStub newStub(io.grpc.Channel channel) {
    return new ConsensusServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ConsensusServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new ConsensusServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ConsensusServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new ConsensusServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class ConsensusServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void broadcast(com.hcmus.distributed.system.grpc.BroadcastRequest request,
        io.grpc.stub.StreamObserver<com.hcmus.distributed.system.grpc.BroadcastResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_BROADCAST, responseObserver);
    }

    /**
     */
    public void schedule(com.hcmus.distributed.system.grpc.BaseRequest request,
        io.grpc.stub.StreamObserver<com.hcmus.distributed.system.grpc.BaseResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SCHEDULE, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_BROADCAST,
            asyncUnaryCall(
              new MethodHandlers<
                com.hcmus.distributed.system.grpc.BroadcastRequest,
                com.hcmus.distributed.system.grpc.BroadcastResponse>(
                  this, METHODID_BROADCAST)))
          .addMethod(
            METHOD_SCHEDULE,
            asyncUnaryCall(
              new MethodHandlers<
                com.hcmus.distributed.system.grpc.BaseRequest,
                com.hcmus.distributed.system.grpc.BaseResponse>(
                  this, METHODID_SCHEDULE)))
          .build();
    }
  }

  /**
   */
  public static final class ConsensusServiceStub extends io.grpc.stub.AbstractStub<ConsensusServiceStub> {
    private ConsensusServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ConsensusServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ConsensusServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ConsensusServiceStub(channel, callOptions);
    }

    /**
     */
    public void broadcast(com.hcmus.distributed.system.grpc.BroadcastRequest request,
        io.grpc.stub.StreamObserver<com.hcmus.distributed.system.grpc.BroadcastResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_BROADCAST, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void schedule(com.hcmus.distributed.system.grpc.BaseRequest request,
        io.grpc.stub.StreamObserver<com.hcmus.distributed.system.grpc.BaseResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SCHEDULE, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class ConsensusServiceBlockingStub extends io.grpc.stub.AbstractStub<ConsensusServiceBlockingStub> {
    private ConsensusServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ConsensusServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ConsensusServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ConsensusServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.hcmus.distributed.system.grpc.BroadcastResponse broadcast(com.hcmus.distributed.system.grpc.BroadcastRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_BROADCAST, getCallOptions(), request);
    }

    /**
     */
    public com.hcmus.distributed.system.grpc.BaseResponse schedule(com.hcmus.distributed.system.grpc.BaseRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SCHEDULE, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class ConsensusServiceFutureStub extends io.grpc.stub.AbstractStub<ConsensusServiceFutureStub> {
    private ConsensusServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ConsensusServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ConsensusServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ConsensusServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.hcmus.distributed.system.grpc.BroadcastResponse> broadcast(
        com.hcmus.distributed.system.grpc.BroadcastRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_BROADCAST, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.hcmus.distributed.system.grpc.BaseResponse> schedule(
        com.hcmus.distributed.system.grpc.BaseRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SCHEDULE, getCallOptions()), request);
    }
  }

  private static final int METHODID_BROADCAST = 0;
  private static final int METHODID_SCHEDULE = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final ConsensusServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(ConsensusServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_BROADCAST:
          serviceImpl.broadcast((com.hcmus.distributed.system.grpc.BroadcastRequest) request,
              (io.grpc.stub.StreamObserver<com.hcmus.distributed.system.grpc.BroadcastResponse>) responseObserver);
          break;
        case METHODID_SCHEDULE:
          serviceImpl.schedule((com.hcmus.distributed.system.grpc.BaseRequest) request,
              (io.grpc.stub.StreamObserver<com.hcmus.distributed.system.grpc.BaseResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static final class ConsensusServiceDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.hcmus.distributed.system.grpc.Msg.getDescriptor();
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (ConsensusServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ConsensusServiceDescriptorSupplier())
              .addMethod(METHOD_BROADCAST)
              .addMethod(METHOD_SCHEDULE)
              .build();
        }
      }
    }
    return result;
  }
}
