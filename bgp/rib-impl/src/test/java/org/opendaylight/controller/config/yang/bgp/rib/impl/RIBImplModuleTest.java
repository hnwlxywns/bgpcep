/*
 * Copyright (c) 2013 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.controller.config.yang.bgp.rib.impl;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;
import org.opendaylight.controller.config.api.ValidationException;
import org.opendaylight.controller.config.api.jmx.CommitStatus;
import org.opendaylight.controller.config.util.ConfigTransactionJMXClient;
import org.opendaylight.yang.gen.v1.urn.ietf.params.xml.ns.yang.ietf.inet.types.rev130715.AsNumber;

public class RIBImplModuleTest extends AbstractRIBImplModuleTest {
    private static final int EXP_INSTANCES = 7;
    private static final AsNumber AS_NUMBER = new AsNumber(500L);
    private static final String INSTANCE_NAME = "rib-impl";
    private static final String FACTORY_NAME = RIBImplModuleFactory.NAME;

    @Test
    public void testValidationExceptionRibIdNotSet() throws Exception {
        try {
            createRIBImplModuleInstance(null, AS_NUMBER, BGP_ID, CLUSTER_ID);
            fail();
        } catch (final ValidationException e) {
            assertTrue(e.getMessage().contains("RibId is not set."));
        }
    }

    @Test
    public void testValidationExceptionLocalAsNotSet() throws Exception {
        try {
            createRIBImplModuleInstance(RIB_ID, null, BGP_ID, CLUSTER_ID);
            fail();
        } catch (final ValidationException e) {
            assertTrue(e.getMessage().contains("LocalAs is not set."));
        }
    }

    @Test
    public void testValidationExceptionBgpIdNotSet() throws Exception {
        try {
            createRIBImplModuleInstance(RIB_ID, AS_NUMBER, null, CLUSTER_ID);
            fail();
        } catch (final ValidationException e) {
            assertTrue(e.getMessage().contains("BgpRibId is not set."));
        }
    }

    @Test
    public void testCreateBean() throws Exception {
        final CommitStatus status = createInstance();
        assertBeanCount(1, FACTORY_NAME);
        assertStatus(status, EXP_INSTANCES, 0, 0);
    }

    @Test
    public void testReusingOldInstance() throws Exception {
        createInstance();
        final ConfigTransactionJMXClient transaction = this.configRegistryClient.createTransaction();
        assertBeanCount(1, FACTORY_NAME);
        final CommitStatus status = transaction.commit();
        assertBeanCount(1, FACTORY_NAME);
        assertStatus(status, 0, 0, EXP_INSTANCES);
    }

    @Test
    public void testReconfigure() throws Exception {
        createInstance();
        final ConfigTransactionJMXClient transaction = this.configRegistryClient.createTransaction();
        assertBeanCount(1, FACTORY_NAME);
        final RIBImplModuleMXBean mxBean = transaction.newMXBeanProxy(transaction.lookupConfigBean(FACTORY_NAME, INSTANCE_NAME),
                RIBImplModuleMXBean.class);
        mxBean.setLocalAs(new AsNumber(100L));
        final CommitStatus status = transaction.commit();
        assertBeanCount(1, FACTORY_NAME);
        assertStatus(status, 0, 1, EXP_INSTANCES - 1);
    }
}
