package io.github.akiranen.report;

import io.github.akiranen.core.Coord;
import io.github.akiranen.core.DTNHost;
import io.github.akiranen.core.MovementListener;

/**
 * Class used by MovementListenerTest. Not for simulation use.
 *
 * @author teemuk
 */
public final class MovementListenerTestReport
extends Report
implements MovementListener {
	private static MovementListener DELEGATE;

	public static void setDelegate(final MovementListener delegate) {
		DELEGATE = delegate;
	}

	@Override
	public void newDestination(DTNHost host, Coord destination, double speed) {
		if (DELEGATE != null) {
			DELEGATE.newDestination(host, destination, speed);
		}
	}

	@Override
	public void initialLocation(DTNHost host, Coord location) {
		if (DELEGATE != null) {
			DELEGATE.initialLocation(host, location);
		}
	}
}
