/*
 * Copyright 2016 Aalto University, ComNet
 * Released under GPLv3. See LICENSE.txt for details.
 */
package io.github.akiranen.report;


import io.github.akiranen.core.DTNHost;
import io.github.akiranen.core.SimError;
import io.github.akiranen.core.UpdateListener;
import io.github.akiranen.routing.util.EnergyModel;

/**
 * Node energy level report. Reports the energy level of all 
 * (or only some, see {@link #REPORTED_NODES}) nodes every 
 * configurable-amount-of seconds (see {@link #GRANULARITY}).
 * Works only if all nodes use energy model; see 
 * {@link io.github.akiranen.routing.util.EnergyModel}.
 */
public class EnergyLevelReport extends SnapshotReport 
	implements UpdateListener {

	@Override
	protected void writeSnapshot(DTNHost h) {
		Double value = (Double)h.getComBus().
				getProperty(EnergyModel.ENERGY_VALUE_ID);
			if (value == null) {
				throw new SimError("Host " + h +
						" is not using energy model");
			}
			write(h.toString() + " " +  format(value));
	}

}
