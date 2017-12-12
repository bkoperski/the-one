/*
 * Copyright 2010 Aalto University, ComNet
 * Released under GPLv3. See LICENSE.txt for details.
 */
package io.github.akiranen.input;

import io.github.akiranen.core.SimClock;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the ScheduledUpdatesQueue
 */
public class ScheduledUpdatesQueueTest {
    private static double MAX = Double.MAX_VALUE;
    private ScheduledUpdatesQueue suq;
    private SimClock sc = SimClock.getInstance();

    @Before
    public void setUp() {
        SimClock.reset();
        suq = new ScheduledUpdatesQueue();
    }

    @Test
    public void testUpdates() {
        assertEquals(MAX, suq.nextEventsTime(), 0.001);
        suq.addUpdate(1);
        suq.addUpdate(1.5);
        suq.addUpdate(20);
        suq.addUpdate(3);
        suq.addUpdate(0);
        suq.addUpdate(5.3);

        assertEquals(0.0, suq.nextEventsTime(), 0.001);
        assertEquals(0.0, suq.nextEvent().getTime(), 0.001);

        assertEquals(1.0, suq.nextEventsTime(), 0.001);
        assertEquals(1.0, suq.nextEventsTime(), 0.001); // twice the same request
        assertEquals(1.0, suq.nextEvent().getTime(), 0.001);

        assertEquals(1.5, suq.nextEvent().getTime(), 0.001);
        assertEquals(3.0, suq.nextEvent().getTime(), 0.001);
        assertEquals(5.3, suq.nextEvent().getTime(), 0.001);
        assertEquals(20.0, suq.nextEvent().getTime(), 0.001);

        assertEquals(MAX, suq.nextEventsTime(), 0.001);
        assertEquals(MAX, suq.nextEvent().getTime(), 0.001);
    }

    @Test
    public void testInterlavedRequests() {
        suq.addUpdate(4);
        suq.addUpdate(7);
        suq.addUpdate(9);

        sc.setTime(1.0);
        assertEquals(4.0, suq.nextEvent().getTime(), 0.001);

        suq.addUpdate(8.5);

        suq.addUpdate(3); // to the top
        assertEquals(3.0, suq.nextEvent().getTime(), 0.001);

        suq.addUpdate(10); // to the bottom
        sc.setTime(4.0);
        assertEquals(7.0, suq.nextEvent().getTime(), 0.001);

        sc.setTime(7.5);
        assertEquals(8.5, suq.nextEvent().getTime(), 0.001);
        sc.setTime(8.8);
        assertEquals(9.0, suq.nextEvent().getTime(), 0.001);
        sc.setTime(9.8);
        assertEquals(10.0, suq.nextEvent().getTime(), 0.001);
        sc.setTime(15);
        assertEquals(MAX, suq.nextEvent().getTime(), 0.001);
    }

    @Test
    public void testNegativeAndZeroValues() {
        suq.addUpdate(3.2);
        suq.addUpdate(-2.1);
        suq.addUpdate(0);
        suq.addUpdate(15);
        suq.addUpdate(-4);
        suq.addUpdate(0.1);

        sc.setTime(-5);

        assertEquals(-4.0, suq.nextEvent().getTime(), 0.001);
        assertEquals(-2.1, suq.nextEvent().getTime(), 0.001);
        assertEquals(0.0, suq.nextEvent().getTime(), 0.001);
        assertEquals(0.1, suq.nextEvent().getTime(), 0.001);
        assertEquals(3.2, suq.nextEvent().getTime(), 0.001);
        assertEquals(15.0, suq.nextEvent().getTime(), 0.001);
        assertEquals(MAX, suq.nextEvent().getTime(), 0.001);
    }

    @Test
    public void testDuplicateValues() {
        suq.addUpdate(4.0);
        suq.addUpdate(5.0);
        suq.addUpdate(4.0); // these should be merged to the first value
        suq.addUpdate(4.0);
        suq.addUpdate(1.0);
        suq.addUpdate(1.0);
        suq.addUpdate(8.0);

        assertEquals(1.0, suq.nextEvent().getTime(), 0.001);
        assertEquals(4.0, suq.nextEvent().getTime(), 0.001);
        assertEquals(5.0, suq.nextEvent().getTime(), 0.001);
        assertEquals(8.0, suq.nextEvent().getTime(), 0.001);
    }
}
