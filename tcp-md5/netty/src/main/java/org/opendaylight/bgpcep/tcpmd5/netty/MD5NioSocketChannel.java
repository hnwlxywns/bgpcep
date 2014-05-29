/*
 * Copyright (c) 2013 Robert Varga. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.bgpcep.tcpmd5.netty;

import io.netty.channel.socket.nio.NioSocketChannel;

import org.opendaylight.bgpcep.tcpmd5.MD5SocketChannel;

/**
 * {@link io.netty.channel.socket.nio.NioSocketChannel} enabled with support
 * for TCP MD5 Signature option.
 */
public class MD5NioSocketChannel extends NioSocketChannel {
	private final MD5SocketChannelConfig config;
	private final MD5SocketChannel channel;

	public MD5NioSocketChannel() {
		super();
		this.channel = new MD5SocketChannel(javaChannel());
		this.config = new ProxyMD5SocketChannelConfig(super.config(), channel);
	}

	@Override
	public MD5SocketChannelConfig config() {
		return this.config;
	}

	@Override
	protected MD5SocketChannel javaChannel() {
		return channel;
	}
}
