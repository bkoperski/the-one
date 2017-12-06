/*
 * Copyright 2016 Aalto University, ComNet
 * Released under GPLv3. See LICENSE.txt for details.
 */

package io.github.akiranen.report;

import io.github.akiranen.core.Coord;
import io.github.akiranen.core.DTNHost;

/**
 * Node location snapshot report. Reports the location of all 
 * (or only some, see {@link SnapshotReport#REPORTED_NODES}) nodes every 
 * configurable-amount-of seconds (see {@link SnapshotReport#GRANULARITY}).
 * Uses {@link Report#format} for location formatting.
 */
public class LocationSnapshotReport extends SnapshotReport {

	@Override
	protected void writeSnapshot(DTNHost h) {
		Coord location = h.getLocation();
		write(h.toString() + " " +  format(location.getX()) + 
				" " + format(location.getY()));
	}

}
