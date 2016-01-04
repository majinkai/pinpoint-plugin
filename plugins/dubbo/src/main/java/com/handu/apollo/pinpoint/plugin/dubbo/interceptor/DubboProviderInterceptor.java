package com.handu.apollo.pinpoint.plugin.dubbo.interceptor;

import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.RpcInvocation;
import com.handu.apollo.pinpoint.plugin.dubbo.DubboConstants;
import com.navercorp.pinpoint.bootstrap.context.*;
import com.navercorp.pinpoint.bootstrap.interceptor.SpanSimpleAroundInterceptor;
import com.navercorp.pinpoint.bootstrap.util.NumberUtils;
import com.navercorp.pinpoint.common.trace.ServiceType;

/**
 * @author Jinkai.Ma
 */
public class DubboProviderInterceptor extends SpanSimpleAroundInterceptor {

    public DubboProviderInterceptor(TraceContext traceContext, MethodDescriptor descriptor) {
        super(traceContext, descriptor, DubboProviderInterceptor.class);
    }

    /**
     * In this method, you have to check if the current request contains following informations:
     *
     * 1. Marker that indicates this transaction must not be traced
     * 2. Data required to continue tracing a transaction. transaction id, parent id and so on.
     *
     * Then you have to create appropriate Trace object.
     */
    @Override
    protected Trace createTrace(Object target, Object[] args) {
        Invoker invoker = (Invoker) target;

        if (DubboConstants.MONITOR_SERVICE_FQCN.equals(invoker.getInterface().getName())) {
            return traceContext.disableSampling();
        }

        RpcInvocation invocation = (RpcInvocation)args[0];

        // If this transaction is not traceable, mark as disabled.
        if (invocation.getAttachment(DubboConstants.META_DO_NOT_TRACE) != null) {
            return traceContext.disableSampling();
        }

        String transactionId = invocation.getAttachment(DubboConstants.META_TRANSACTION_ID);

        // If there's no trasanction id, a new trasaction begins here.
        if (transactionId == null) {
            return traceContext.newTraceObject();
        }

        // otherwise, continue tracing with given data.
        long parentSpanID = NumberUtils.parseLong(invocation.getAttachment(DubboConstants.META_PARENT_SPAN_ID), SpanId.NULL);
        long spanID = NumberUtils.parseLong(invocation.getAttachment(DubboConstants.META_SPAN_ID), SpanId.NULL);
        short flags = NumberUtils.parseShort(invocation.getAttachment(DubboConstants.META_FLAGS), (short) 0);
        TraceId traceId = traceContext.createTraceId(transactionId, parentSpanID, spanID, flags);

        return traceContext.continueTraceObject(traceId);
    }


    @Override
    protected void doInBeforeTrace(SpanRecorder recorder, Object target, Object[] args) {
        RpcInvocation invocation = (RpcInvocation)args[0];
        RpcContext rpcContext = RpcContext.getContext();

        // You have to record a service type within Server range.
        recorder.recordServiceType(DubboConstants.DUBBO_PROVIDER_SERVICE_TYPE);

        // Record rpc name, client address, server address.
        recorder.recordRpcName(invocation.getInvoker().getInterface().getSimpleName() + ":" + invocation.getMethodName());
        recorder.recordEndPoint(rpcContext.getLocalAddressString());
        recorder.recordRemoteAddress(rpcContext.getRemoteAddressString());

        // If this transaction did not begin here, record parent(client who sent this request) information
        if (!recorder.isRoot()) {
            String parentApplicationName = invocation.getAttachment(DubboConstants.META_PARENT_APPLICATION_NAME);

            if (parentApplicationName != null) {
                short parentApplicationType = NumberUtils.parseShort(invocation.getAttachment(DubboConstants.META_PARENT_APPLICATION_TYPE), ServiceType.UNDEFINED.getCode());
                recorder.recordParentApplication(parentApplicationName, parentApplicationType);

                String serverHostName = rpcContext.getLocalHostName();

                if (serverHostName != null) {
                    recorder.recordAcceptorHost(serverHostName);
                } else {
                    recorder.recordAcceptorHost(rpcContext.getLocalAddressString());
                }
            }
        }
    }

    @Override
    protected void doInAfterTrace(SpanRecorder recorder, Object target, Object[] args, Object result, Throwable throwable) {
        RpcInvocation invocation = (RpcInvocation)args[0];

        recorder.recordApi(methodDescriptor);
        recorder.recordAttribute(DubboConstants.DUBBO_ARGS_ANNOTATION_KEY, invocation.getArguments());

        if (throwable == null) {
            recorder.recordAttribute(DubboConstants.DUBBO_RESULT_ANNOTATION_KEY, result);
        } else {
            recorder.recordException(throwable);
        }
    }
}
