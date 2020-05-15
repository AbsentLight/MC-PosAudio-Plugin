package xyz.darke.darkpas.util;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class MathUtilTest {

    private final static double delta = 0.00001;

    @Test
    public void distance2D() {
        assertEquals(2, MathUtil.distance2D(0, 0, 0, 2), delta);
        assertEquals(2, MathUtil.distance2D(3, 3, 5, 3), delta);
    }

    @Test
    public void angle2D() {
        assertEquals(0, MathUtil.angle2D(0, 0, 0, 0, 0), delta);
        assertEquals(Math.PI * 0.5, MathUtil.angle2D(0, 0, 1, 0, 0), delta);
        assertEquals(0, MathUtil.angle2D(0, 0, 0, 1, 0), delta);
        assertEquals(Math.PI * -0.5, MathUtil.angle2D(1, 0, 0, 0, 0), delta);
    }

    @Test
    public void colVectorToMatrix() {

        double[][] result = MathUtil.colVectorToMatrix(new double[] {1,2,3});
        assertEquals(1, result.length);
        assertEquals(3, result[0].length);
        assertEquals(1, result[0][0], delta);
        assertEquals(2, result[0][1], delta);
        assertEquals(3, result[0][2], delta);
    }

    @Test
    public void formYawMatrix() {
        double[][] matrix = MathUtil.formYawMatrix(0);
        assertEquals(1, matrix[0][0], delta);
        assertEquals(0, matrix[0][1], delta);
        assertEquals(0, matrix[0][2], delta);

        assertEquals(0, matrix[1][0], delta);
        assertEquals(1, matrix[1][1], delta);
        assertEquals(0, matrix[1][2], delta);

        assertEquals(0, matrix[2][0], delta);
        assertEquals(0, matrix[2][1], delta);
        assertEquals(1, matrix[2][2], delta);

        matrix = MathUtil.formYawMatrix(180);
        assertEquals(-1, matrix[0][0], delta);
        assertEquals(0, matrix[0][1], delta);
        assertEquals(0, matrix[0][2], delta);

        assertEquals(0, matrix[1][0], delta);
        assertEquals(-1, matrix[1][1], delta);
        assertEquals(0, matrix[1][2], delta);

        assertEquals(0, matrix[2][0], delta);
        assertEquals(0, matrix[2][1], delta);
        assertEquals(1, matrix[2][2], delta);

        matrix = MathUtil.formYawMatrix(90);
        assertEquals(0, matrix[0][0], delta);
        assertEquals(-1, matrix[0][1], delta);
        assertEquals(0, matrix[0][2], delta);

        assertEquals(1, matrix[1][0], delta);
        assertEquals(0, matrix[1][1], delta);
        assertEquals(0, matrix[1][2], delta);

        assertEquals(0, matrix[2][0], delta);
        assertEquals(0, matrix[2][1], delta);
        assertEquals(1, matrix[2][2], delta);
    }

    @Test
    public void formPitchMatrix() {
        double[][] matrix = MathUtil.formPitchMatrix(0);
        assertEquals(1, matrix[0][0], delta);
        assertEquals(0, matrix[0][1], delta);
        assertEquals(0, matrix[0][2], delta);

        assertEquals(0, matrix[1][0], delta);
        assertEquals(1, matrix[1][1], delta);
        assertEquals(0, matrix[1][2], delta);

        assertEquals(0, matrix[2][0], delta);
        assertEquals(0, matrix[2][1], delta);
        assertEquals(1, matrix[2][2], delta);

        matrix = MathUtil.formPitchMatrix(180);
        assertEquals(-1, matrix[0][0], delta);
        assertEquals(0, matrix[0][1], delta);
        assertEquals(0, matrix[0][2], delta);

        assertEquals(0, matrix[1][0], delta);
        assertEquals(1, matrix[1][1], delta);
        assertEquals(0, matrix[1][2], delta);

        assertEquals(0, matrix[2][0], delta);
        assertEquals(0, matrix[2][1], delta);
        assertEquals(-1, matrix[2][2], delta);

        matrix = MathUtil.formPitchMatrix(90);
        assertEquals(0, matrix[0][0], delta);
        assertEquals(0, matrix[0][1], delta);
        assertEquals(1, matrix[0][2], delta);

        assertEquals(0, matrix[1][0], delta);
        assertEquals(1, matrix[1][1], delta);
        assertEquals(0, matrix[1][2], delta);

        assertEquals(-1, matrix[2][0], delta);
        assertEquals(0, matrix[2][1], delta);
        assertEquals(0, matrix[2][2], delta);
    }

    @Test
    public void formRotationMatrix() {
        double[][] matrix = MathUtil.formRotationMatrix(90, 180);
        assertEquals(0, matrix[0][0], delta);
        assertEquals(-1, matrix[0][1], delta);
        assertEquals(0, matrix[0][2], delta);

        assertEquals(-1, matrix[1][0], delta);
        assertEquals(0, matrix[1][1], delta);
        assertEquals(-0, matrix[1][2], delta);

        assertEquals(0, matrix[2][0], delta);
        assertEquals(0, matrix[2][1], delta);
        assertEquals(-1, matrix[2][2], delta);

        matrix = MathUtil.formRotationMatrix(45, 90);
        assertEquals(0, matrix[0][0], delta);
        assertEquals(-0.7071067812, matrix[0][1], delta);
        assertEquals(0.7071067812, matrix[0][2], delta);

        assertEquals(0, matrix[1][0], delta);
        assertEquals(0.7071067812, matrix[1][1], delta);
        assertEquals(0.7071067812, matrix[1][2], delta);

        assertEquals(-1, matrix[2][0], delta);
        assertEquals(0, matrix[2][1], delta);
        assertEquals(0, matrix[2][2], delta);
    }

    @Test
    public void rotateColVectorTest() {

        double[][] rMatrix  = MathUtil.formRotationMatrix(90, 180);
        double[]   position = {12,25,13};

        double[][] result = MathUtil.matrixMult(MathUtil.colVectorToMatrix(position), rMatrix);

        assertEquals(1,   result.length);
        assertEquals(3,   result[0].length);
        assertEquals(-25, result[0][0], delta);
        assertEquals(-12, result[0][1], delta);
        assertEquals(-13, result[0][2], delta);

    }

}