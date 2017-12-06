/*
 * Copyright 2010 Aalto University, ComNet
 * Released under GPLv3. See LICENSE.txt for details.
 */
package io.github.akiranen.core;

import io.github.akiranen.DummyStationaryMovement;
import io.github.akiranen.routing.MessageRouter;
import io.github.akiranen.routing.PassiveRouter;

import java.util.List;

/**
 * A test stub of DTNHost for testing. All fields are public so they can be
 * easily read from test cases.
 */
public class DummyDTNHost extends DTNHost {
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

	public DummyDTNHost(List<NetworkInterface> li,
			            ModuleCommunicationBus comBus,
                        Settings testSettings) {
		super(null,null,"TST", li, comBus,
				new DummyStationaryMovement(new Coord(0,0)),
				new PassiveRouter(testSettings == null ? new DummySettings() : testSettings)
        );
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
		return MessageRouter.RCV_OK;
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
