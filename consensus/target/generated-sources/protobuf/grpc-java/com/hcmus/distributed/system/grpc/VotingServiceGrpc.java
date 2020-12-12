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
public final class VotingServiceGrpc {

  private VotingServiceGrpc() {}

  public static final String SERVICE_NAME = "com.hcmus.distributed.system.grpc.VotingService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.hcmus.distributed.system.grpc.VotingRequest,
      com.hcmus.distributed.system.grpc.VotingResponse> METHOD_VOTE =
      io.grpc.MethodDescriptor.<com.hcmus.distributed.system.grpc.VotingRequest, com.hcmus.distributed.system.grpc.VotingResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "com.hcmus.distributed.system.grpc.VotingService", "vote"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.hcmus.distributed.system.grpc.VotingRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.hcmus.distributed.system.grpc.VotingResponse.getDefaultInstance()))
          .build();

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static VotingServiceStub newStub(io.grpc.Channel channel) {
    return new VotingServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static VotingServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new VotingServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static VotingServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new VotingServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class VotingServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void vote(com.hcmus.distributed.system.grpc.VotingRequest request,
        io.grpc.stub.StreamObserver<com.hcmus.distributed.system.grpc.VotingResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_VOTE, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_VOTE,
            asyncUnaryCall(
              new MethodHandlers<
                com.hcmus.distributed.system.grpc.VotingRequest,
                com.hcmus.distributed.system.grpc.VotingResponse>(
                  this, METHODID_VOTE)))
          .build();
    }
  }

  /**
   */
  public static final class VotingServiceStub extends io.grpc.stub.AbstractStub<VotingServiceStub> {
    private VotingServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private VotingServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected VotingServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new VotingServiceStub(channel, callOptions);
    }

    /**
     */
    public void vote(com.hcmus.distributed.system.grpc.VotingRequest request,
        io.grpc.stub.StreamObserver<com.hcmus.distributed.system.grpc.VotingResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_VOTE, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class VotingServiceBlockingStub extends io.grpc.stub.AbstractStub<VotingServiceBlockingStub> {
    private VotingServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private VotingServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected VotingServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new VotingServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.hcmus.distributed.system.grpc.VotingResponse vote(com.hcmus.distributed.system.grpc.VotingRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_VOTE, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class VotingServiceFutureStub extends io.grpc.stub.AbstractStub<VotingServiceFutureStub> {
    private VotingServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private VotingServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected VotingServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new VotingServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.hcmus.distributed.system.grpc.VotingResponse> vote(
        com.hcmus.distributed.system.grpc.VotingRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_VOTE, getCallOptions()), request);
    }
  }

  private static final int METHODID_VOTE = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final VotingServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(VotingServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_VOTE:
          serviceImpl.vote((com.hcmus.distributed.system.grpc.VotingRequest) request,
              (io.grpc.stub.StreamObserver<com.hcmus.distributed.system.grpc.VotingResponse>) responseObserver);
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

  private static final class VotingServiceDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.hcmus.distributed.system.grpc.Msg.getDescriptor();
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (VotingServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new VotingServiceDescriptorSupplier())
              .addMethod(METHOD_VOTE)
              .build();
        }
      }
    }
    return result;
  }
}
