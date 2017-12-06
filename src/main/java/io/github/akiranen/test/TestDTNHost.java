/*
 * Copyright 2010 Aalto University, ComNet
 * Released under GPLv3. See LICENSE.txt for details.
 */
package io.github.akiranen.test;

import java.util.List;

import io.github.akiranen.routing.PassiveRouter;
import io.github.akiranen.core.Coord;
import io.github.akiranen.core.DTNHost;
import io.github.akiranen.core.Message;
import io.github.akiranen.core.ModuleCommunicationBus;
import io.github.akiranen.core.NetworkInterface;
import io.github.akiranen.core.Settings;
import io.github.akiranen.core.SimClock;

/**
 * A test stub of DTNHost for testing. All fields are public so they can be
 * easily read from test cases.
 */
public class TestDTNHost extends DTNHost {
	public double lastUpdate = 0;
	public int nrofConnect = 0;
	public int nrofUpdate = 0;
	public Message recvMessage;
	public DTNHost recvFrom;
	public String abortedId;
	public DTNHost abortedFrom;
	public int abortedBytesRemaining;

	public String transferredId;
	public DTNHost transferredFrom;


	public TestDTNHost(List<NetworkInterface> li,
			ModuleCommunicationBus comBus, Settings testSettings) {
		super(null,null,"TST", li, comBus,
				new StationaryMovement(new Coord(0,0)),
				new PassiveRouter(
						(testSettings == null ? new TestSettings() :
							testSettings)));
	}

	@Override
	public void connect(DTNHost anotherHost) {
		this.nrofConnect++;
	}

	@Override
	public void update(boolean up) {
		this.nrofUpdate++;
		this.lastUpdate = SimClock.getTime();
	}

	@Override
	public int receiveMessage(Message m, DTNHost from) {
		this.recvMessage = m;
		this.recvFrom = from;
		return routing.MessageRouter.RCV_OK;
	}

	@Override
	public void messageAborted(String id, DTNHost from, int bytesRemaining) {
		this.abortedId = id;
		this.abortedFrom = from;
		this.abortedBytesRemaining = bytesRemaining;
	}

	@Override
	public void messageTransferred(String id, DTNHost from) {
		this.transferredId = id;
		this.transferredFrom = from;
	}
}
