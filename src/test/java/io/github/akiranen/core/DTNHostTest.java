package io.github.akiranen.core;

import io.github.akiranen.movement.MovementModel;
import io.github.akiranen.movement.Path;
import io.github.akiranen.routing.MessageRouter;
import io.github.akiranen.routing.PassiveRouter;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertFalse;

/**
 * @author teemuk
 */
public class DTNHostTest {

    /**
     * Tests the case where the DTNHost has no interfaces configured.
     */
    @Test
    public void testNoInterfaces() {
        final DTNHost host = new DTNHost(
                new ArrayList<>(),
                new ArrayList<>(),
                "",
                new ArrayList<>(),
                null,
                makeMovementModel(),
                makeMessageRouter());

        assertFalse("Radio reported as active.", host.isRadioActive());
    }

    private static MovementModel makeMovementModel() {
        return new MovementModel() {
            @Override
            public Path getPath() {
                return null;
            }

            @Override
            public Coord getInitialLocation() {
                return null;
            }

            @Override
            public MovementModel replicate() {
                return makeMovementModel();
            }
        };
    }

    private static MessageRouter makeMessageRouter() {
        return new PassiveRouter(new Settings());
    }
}
