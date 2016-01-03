package com.handu.apollo.pinpoint.plugin.dubbo;

import com.navercorp.pinpoint.common.trace.AnnotationKey;
import com.navercorp.pinpoint.common.trace.AnnotationKeyFactory;
import com.navercorp.pinpoint.common.trace.ServiceType;
import com.navercorp.pinpoint.common.trace.ServiceTypeFactory;

/**
 * @author Jinkai.Ma
 */
public interface DubboConstants {

    ServiceType DUBBO_PROVIDER_SERVICE_TYPE = ServiceTypeFactory.of(1919, "DUBBO_PROVIDER");
    ServiceType DUBBO_CONSUMER_SERVICE_TYPE = ServiceTypeFactory.of(9909, "DUBBO_CONSUMER");
    AnnotationKey DUBBO_ARGUMENT_ANNOTATION_KEY = AnnotationKeyFactory.of(996, "DUBBO_ARGUMENT");
    AnnotationKey DUBBO_PROCEDURE_ANNOTATION_KEY = AnnotationKeyFactory.of(997, "DUBBO_PROCEDURE");
    AnnotationKey DUBBO_RESULT_ANNOTATION_KEY = AnnotationKeyFactory.of(998, "DUBBO_RESULT");

    String META_DO_NOT_TRACE = "_DUBBO_DO_NOT_TRACE";
    String META_TRANSACTION_ID = "_DUBBO_TRASACTION_ID";
    String META_SPAN_ID = "_DUBBO_SPAN_ID";
    String META_PARENT_SPAN_ID = "_DUBBO_PARENT_SPAN_ID";
    String META_PARENT_APPLICATION_NAME = "_DUBBO_PARENT_APPLICATION_NAME";
    String META_PARENT_APPLICATION_TYPE = "_DUBBO_PARENT_APPLICATION_TYPE";
    String META_FLAGS = "_DUBBO_FLAGS";

    String MONITOR_SERVICE_QFN = "com.alibaba.dubbo.monitor.MonitorService";
}
