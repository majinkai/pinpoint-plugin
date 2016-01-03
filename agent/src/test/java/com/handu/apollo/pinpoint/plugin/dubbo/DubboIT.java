package com.handu.apollo.pinpoint.plugin.dubbo;

import com.handu.apollo.pinpoint.plugin.PinpointTestConstants;
import com.navercorp.pinpoint.test.plugin.Dependency;
import com.navercorp.pinpoint.test.plugin.PinpointAgent;
import com.navercorp.pinpoint.test.plugin.PinpointPluginTestSuite;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jinkai.Ma
 */
@RunWith(PinpointPluginTestSuite.class)
@PinpointAgent(PinpointTestConstants.AGENT_PATH)
@Dependency({ "com.alibaba:dubbo:[2.5.3]" })
public class DubboIT {
    @Test
    public void test() throws Exception {
    }
}
