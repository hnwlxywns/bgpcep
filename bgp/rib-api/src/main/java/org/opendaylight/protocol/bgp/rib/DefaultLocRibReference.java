/*
 * Copyright (c) 2013 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.protocol.bgp.rib;

import org.opendaylight.protocol.concepts.DefaultInstanceReference;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.bgp.rib.rev130925.LocRib;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;

/**
 *
 */
public class DefaultLocRibReference extends DefaultInstanceReference<LocRib> implements LocRibReference {
	public DefaultLocRibReference(final InstanceIdentifier<LocRib> instanceIdentifier) {
		super(instanceIdentifier);
	}
}