/*
 * Copyright 2011 Aalto University, ComNet
 * Released under GPLv3. See LICENSE.txt for details.
 */
package io.github.akiranen.report;

import io.github.akiranen.core.DTNHost;
import io.github.akiranen.core.Message;

import java.util.*;

/**
 * Reports the amount of messages in the system at each time interval. Uses the
 * same settings as the {@link MessageLocationReport}
 */
public class MessageCopyCountReport extends MessageLocationReport {

	/**
	 * Creates a snapshot of message counts
	 * @param hosts The list of hosts in the world
	 */
	@Override
	protected void createSnapshot(List<DTNHost> hosts) {
		Map<String, Integer> counts = new HashMap<String, Integer>();
		write("[" + (int) getSimTime() + "]"); /* write sim time stamp */
		ArrayList<String> keys;

		for (DTNHost host : hosts) {
			for (Message m : host.getMessageCollection()) {
				Integer oldCount;
				if (!isTracked(m)) {
					continue;
				}
				oldCount = counts.get(m.getId());
				counts.put(m.getId(), (oldCount == null ? 1 : oldCount + 1));
			}
		}

		keys = new ArrayList<String>(counts.keySet());
		Collections.sort(keys);

		for (String key : keys) {
			write(key + " " + counts.get(key));
		}

	}

}
