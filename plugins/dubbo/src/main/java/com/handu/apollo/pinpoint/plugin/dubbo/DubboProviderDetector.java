package com.handu.apollo.pinpoint.plugin.dubbo;

import com.navercorp.pinpoint.bootstrap.plugin.ApplicationTypeDetector;
import com.navercorp.pinpoint.bootstrap.resolver.ConditionProvider;
import com.navercorp.pinpoint.common.trace.ServiceType;

/**
 * @author Jinkai.Ma
 */
public class DubboProviderDetector implements ApplicationTypeDetector {
    @Override
    public ServiceType getApplicationType() {
        return DubboConstants.DUBBO_PROVIDER_SERVICE_TYPE;
    }

    @Override
    public boolean detect(ConditionProvider provider) {
        return provider.checkMainClass("com.alibaba.dubbo.rpc.proxy.AbstractProxyInvoker");
    }
}
