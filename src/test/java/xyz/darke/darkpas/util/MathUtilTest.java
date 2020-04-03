package xyz.darke.darkpas.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MathUtilTest {

    @Test
    public void distance2D() {
        assertEquals(2, MathUtil.distance2D(0, 0, 0, 2), 0.00001);
        assertEquals(2, MathUtil.distance2D(3, 3, 5, 3), 0.00001);
    }

    @Test
    public void angle2D() {
        assertEquals(0, MathUtil.angle2D(0, 0, 0, 0, 0), 0.00001);
        assertEquals(Math.PI * 0.5, MathUtil.angle2D(0, 0, 1, 0, 0), 0.00001);
        assertEquals(0, MathUtil.angle2D(0, 0, 0, 1, 0), 0.00001);
        assertEquals(Math.PI * -0.5, MathUtil.angle2D(1, 0, 0, 0, 0), 0.00001);
    }
}