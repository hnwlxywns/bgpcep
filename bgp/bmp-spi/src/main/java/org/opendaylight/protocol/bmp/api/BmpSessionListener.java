/*
 * Copyright (c) 2015 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.protocol.bmp.api;

import java.util.EventListener;
import org.opendaylight.yangtools.yang.binding.Notification;

public interface BmpSessionListener extends EventListener {

    void onSessionUp(BmpSession session);

    void onSessionDown(Exception e);

    void onMessage(Notification message);
}
