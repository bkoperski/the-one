package io.github.akiranen.core;

import io.github.akiranen.report.MovementListenerTestReport;
import io.github.akiranen.report.Report;
import io.github.akiranen.ui.DTNSimUI;
import org.junit.Test;

import java.io.StringBufferInputStream;

import static org.junit.Assert.*;

/**
 * @author teemuk
 */
public class MovementListenerTest {

    private static int INITIAL_LOC_TEST_NODE_COUNT = 10;
    private static String INITIAL_LOC_TEST_SETTINGS =
            "Scenario.name = InitialLocTest\n" +
                    "Scenario.endTime = 600\n" +
                    "Scenario.updateInterval = 0.1\n" +
                    "Scenario.simulateConnections = false\n" +
                    "MovementModel.worldSize = 1000,1000\n" +
                    "Report.nrofReports = 1\n" +
                    "Report.reportDir = test\n" +
                    "Report.report1 = MovementListenerTestReport\n" +
                    "Events.nrof = 0\n" +
                    "Scenario.nrofHostGroups = 1\n" +
                    "Group1.groupID = x\n" +
                    "Group1.nrofHosts = " + INITIAL_LOC_TEST_NODE_COUNT + "\n" +
                    "Group1.nrofInterfaces = 0\n" +
                    "Group1.movementModel = RandomWaypoint\n" +
                    "Group1.router = PassiveRouter\n";

    /**
     * Tests whether initialLocation() gets called correctly when settings up
     * a SimScenario
     */
    @Test
    public void testInitialLocation() {
        // TODO: If more test cases are added that use Settings, they might
        // be run in parallel and break.

        // Setup the settings
        Settings.initFromStream(
                new StringBufferInputStream(INITIAL_LOC_TEST_SETTINGS));
        final DTNSimUI ui = new DTNSimUI() {
            @Override
            protected void runSim() {
                super.done();
            }
        };

        // Set the delegate for MovementListenerTestReport
        MovementListenerTestReport.setDelegate(new MovementReport());
        ui.start();

        assertEquals("initialLocation() called incorrect number of time.",
                INITIAL_LOC_TEST_NODE_COUNT, INITIAL_LOC_CALL_COUNT);
    }

    private static int INITIAL_LOC_CALL_COUNT = 0;

    private static class MovementReport extends Report implements MovementListener {
        @Override
        public void newDestination(
                final DTNHost host,
                final Coord destination,
                final double speed) {
        }

        @Override
        public void initialLocation(
                final DTNHost host,
                final Coord location) {
            INITIAL_LOC_CALL_COUNT++;
        }
    }
}
