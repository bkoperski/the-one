/*
 * Copyright 2010 Aalto University, ComNet
 * Released under GPLv3. See LICENSE.txt for details.
 */
package io.github.akiranen.movement;

import io.github.akiranen.core.Coord;

/**
 * MovementModels used for transportation should implement this interface
 *
 * @author Frans Ekman
  */
public interface TransportMovement extends SwitchableMovement {

	public void setNextRoute(Coord nodeLocation, Coord nodeDestination);

}
