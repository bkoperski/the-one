/*
 * Copyright 2010 Aalto University, ComNet
 * Released under GPLv3. See LICENSE.txt for details.
 */
package io.github.akiranen.input;

import io.github.akiranen.core.Coord;
import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class WKTPointReaderTest {

    private WKTReader r;

    private final String POINT_DATA =
            "POINT (2552448.388211649 6673384.4020657055) \n" +
                    "POINT (2552275.9398973365 6673509.820852942)\n" +
                    "POINT (2552361.289603607 6673630.088457832)\n" +
                    "LINESTRING (1.0 2.0, 2.0 3.0)\n" + // should skip this line
                    "POINT (2552782.3212060533 6673285.5993876355)\n";

    private final Coord[] POINTS = {
            new Coord(2552448.388211649, 6673384.4020657055),
            new Coord(2552275.9398973365, 6673509.820852942),
            new Coord(2552361.289603607, 6673630.088457832),
            new Coord(2552782.3212060533, 6673285.5993876355)
    };

    @Before
    public void setUp() {
        r = new WKTReader();
    }

    @Test
    public void testReader() throws Exception {
        StringReader input = new StringReader(POINT_DATA);
        List<Coord> coords = r.readPoints(input);

        assertEquals(POINTS.length, coords.size());

        for (int i = 0; i < POINTS.length; i++) {
            assertEquals(coords.get(i), POINTS[i]);
        }
    }
}
