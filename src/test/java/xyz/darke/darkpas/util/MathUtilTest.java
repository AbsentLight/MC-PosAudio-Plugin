//package xyz.darke.darkpas.util;
//
//import org.junit.Test;
//
//import java.util.Arrays;
//
//import static org.junit.Assert.assertEquals;
//
//public class MathUtilTest {
//
//    private final static double delta = 0.00001;
////
////    @Test
////    public void distance2DTest() {
////        assertEquals(2, MathUtil.distance2D(0, 0, 0, 2), delta);
////        assertEquals(2, MathUtil.distance2D(3, 3, 5, 3), delta);
////    }
////
////    @Test
////    public void angle2DTest() {
////        assertEquals(0, MathUtil.angle2D(0, 0, 0, 0, 0), delta);
////        assertEquals(Math.PI * 0.5, MathUtil.angle2D(0, 0, 1, 0, 0), delta);
////        assertEquals(0, MathUtil.angle2D(0, 0, 0, 1, 0), delta);
////        assertEquals(Math.PI * -0.5, MathUtil.angle2D(1, 0, 0, 0, 0), delta);
////    }
//
//    @Test
//    public void colVectorToMatrixTest() {
//
//        double[][] result = MathUtil.colVectorToMatrix(new double[] {1,2,3});
//        assertEquals(1, result.length);
//        assertEquals(3, result[0].length);
//        assertEquals(1, result[0][0], delta);
//        assertEquals(2, result[0][2], delta);
//        assertEquals(3, result[0][1], delta);
//    }
//
//    @Test
//    public void formYawMatrixNoAngleTest() {
//        double[][] matrix = MathUtil.formYawMatrix(0);
//        assertEquals(1, matrix[0][0], delta);
//        assertEquals(0, matrix[0][1], delta);
//        assertEquals(0, matrix[0][2], delta);
//
//        assertEquals(0, matrix[1][0], delta);
//        assertEquals(1, matrix[1][1], delta);
//        assertEquals(0, matrix[1][2], delta);
//
//        assertEquals(0, matrix[2][0], delta);
//        assertEquals(0, matrix[2][1], delta);
//        assertEquals(1, matrix[2][2], delta);
//    }
//
//    @Test
//    public void formYawMatrixPosAngleTest() {
//        double[][] matrix = MathUtil.formYawMatrix(90);
//        assertEquals(0, matrix[0][0], delta);
//        assertEquals(-1, matrix[0][1], delta);
//        assertEquals(0, matrix[0][2], delta);
//
//        assertEquals(1, matrix[1][0], delta);
//        assertEquals(0, matrix[1][1], delta);
//        assertEquals(0, matrix[1][2], delta);
//
//        assertEquals(0, matrix[2][0], delta);
//        assertEquals(0, matrix[2][1], delta);
//        assertEquals(1, matrix[2][2], delta);
//
//    }
//    @Test
//    public void formYawMatrixNegAngleTest() {
//        double[][] matrix = MathUtil.formYawMatrix(-90);
//        assertEquals(0, matrix[0][0], delta);
//        assertEquals(1, matrix[0][1], delta);
//        assertEquals(0, matrix[0][2], delta);
//
//        assertEquals(-1, matrix[1][0], delta);
//        assertEquals(0, matrix[1][1], delta);
//        assertEquals(0, matrix[1][2], delta);
//
//        assertEquals(0, matrix[2][0], delta);
//        assertEquals(0, matrix[2][1], delta);
//        assertEquals(1, matrix[2][2], delta);
//    }
//
//    @Test
//    public void formPitchMatrixNoAngleTest() {
//        double[][] matrix = MathUtil.formPitchMatrix(0);
//        assertEquals(1, matrix[0][0], delta);
//        assertEquals(0, matrix[0][1], delta);
//        assertEquals(0, matrix[0][2], delta);
//
//        assertEquals(0, matrix[1][0], delta);
//        assertEquals(1, matrix[1][1], delta);
//        assertEquals(0, matrix[1][2], delta);
//
//        assertEquals(0, matrix[2][0], delta);
//        assertEquals(0, matrix[2][1], delta);
//        assertEquals(1, matrix[2][2], delta);
//    }
//
//    @Test
//    public void formPitchMatrixPosAngleTest() {
//        double[][] matrix = MathUtil.formPitchMatrix(90);
//        assertEquals(0, matrix[0][0], delta);
//        assertEquals(0, matrix[0][1], delta);
//        assertEquals(1, matrix[0][2], delta);
//
//        assertEquals(0, matrix[1][0], delta);
//        assertEquals(1, matrix[1][1], delta);
//        assertEquals(0, matrix[1][2], delta);
//
//        assertEquals(-1, matrix[2][0], delta);
//        assertEquals(0, matrix[2][1], delta);
//        assertEquals(0, matrix[2][2], delta);
//    }
//
//    @Test
//    public void formPitchMatrixNegAngleTest() {
//        double[][] matrix = MathUtil.formPitchMatrix(-90);
//        assertEquals(0, matrix[0][0], delta);
//        assertEquals(0, matrix[0][1], delta);
//        assertEquals(-1, matrix[0][2], delta);
//
//        assertEquals(0, matrix[1][0], delta);
//        assertEquals(1, matrix[1][1], delta);
//        assertEquals(0, matrix[1][2], delta);
//
//        assertEquals(1, matrix[2][0], delta);
//        assertEquals(0, matrix[2][1], delta);
//        assertEquals(0, matrix[2][2], delta);
//
//
//    }
//
//    @Test
//    public void formRotationMatrixWithNoAnglesTest() {
//        double[][] matrix = MathUtil.formRotationMatrix(0, 0);
//        assertEquals(1, matrix[0][0], delta);
//        assertEquals(0, matrix[0][1], delta);
//        assertEquals(0, matrix[0][2], delta);
//
//        assertEquals(0, matrix[1][0], delta);
//        assertEquals(1, matrix[1][1], delta);
//        assertEquals(0, matrix[1][2], delta);
//
//        assertEquals(0, matrix[2][0], delta);
//        assertEquals(0, matrix[2][1], delta);
//        assertEquals(1, matrix[2][2], delta);
//    }
//
//    @Test
//    public void formRotationMatrixWithYawTest() {
//        double[][] matrix = MathUtil.formRotationMatrix(90, 0);
//        assertEquals(0, matrix[0][0], delta);
//        assertEquals(-1, matrix[0][1], delta);
//        assertEquals(0, matrix[0][2], delta);
//
//        assertEquals(1, matrix[1][0], delta);
//        assertEquals(0, matrix[1][1], delta);
//        assertEquals(0, matrix[1][2], delta);
//
//        assertEquals(0, matrix[2][0], delta);
//        assertEquals(0, matrix[2][1], delta);
//        assertEquals(1, matrix[2][2], delta);
//    }
//
//    @Test
//    public void formRotationMatrixWithPitchTest() {
//        double[][] matrix = MathUtil.formRotationMatrix(0, 90);
//        assertEquals(0, matrix[0][0], delta);
//        assertEquals(0, matrix[0][1], delta);
//        assertEquals(1, matrix[0][2], delta);
//
//        assertEquals(0, matrix[1][0], delta);
//        assertEquals(1, matrix[1][1], delta);
//        assertEquals(0, matrix[1][2], delta);
//
//        assertEquals(-1, matrix[2][0], delta);
//        assertEquals(0, matrix[2][1], delta);
//        assertEquals(0, matrix[2][2], delta);
//    }
//
//    @Test
//    public void formRotationMatrixWithYawAndPitchTest() {
//        double[][] matrix = MathUtil.formRotationMatrix(90, 90);
//        assertEquals(0, matrix[0][0], delta);
//        assertEquals(-1, matrix[0][1], delta);
//        assertEquals(0, matrix[0][2], delta);
//
//        assertEquals(0, matrix[1][0], delta);
//        assertEquals(0, matrix[1][1], delta);
//        assertEquals(1, matrix[1][2], delta);
//
//        assertEquals(-1, matrix[2][0], delta);
//        assertEquals(0, matrix[2][1], delta);
//        assertEquals(0, matrix[2][2], delta);
//    }
//
//    /*
//        No Angles
//     */
//
//    @Test
//    public void rotateVectorNoAnglesXTest() {
//
//        double[] vector = {1,0,0};
//
//        double[] result = MathUtil.rotateVector(0,0, vector);
//
//        assertEquals(3, result.length);
//        assertEquals(1, result[0], delta);
//        assertEquals(0, result[1], delta);
//        assertEquals(0, result[2], delta);
//    }
//
//    @Test
//    public void rotateVectorNoAnglesYTest() {
//
//        double[] vector = {0,1,0};
//
//        double[] result = MathUtil.rotateVector(0,0, vector);
//
//        assertEquals(3, result.length);
//        assertEquals(0, result[0], delta);
//        assertEquals(0, result[1], delta);
//        assertEquals(1, result[2], delta);
//    }
//
//    @Test
//    public void rotateVectorNoAnglesZTest() {
//
//        double[] vector = {0,0,1};
//
//        double[] result = MathUtil.rotateVector(0,0, vector);
//
//        assertEquals(3, result.length);
//        assertEquals(0, result[0], delta);
//        assertEquals(1, result[1], delta);
//        assertEquals(0, result[2], delta);
//    }
//
//    /*
//        Yaw
//     */
//
//    @Test
//    public void rotateVectorPosYawXTest() {
//
//        double[] vector = {1,0,0};
//
//        double[] result = MathUtil.rotateVector(90,0, vector);
//
//        assertEquals(3, result.length);
//        assertEquals(0, result[0], delta);
//        assertEquals(-1, result[1], delta);
//        assertEquals(0, result[2], delta);
//    }
//
//    @Test
//    public void rotateVectorPosYawYTest() {
//
//        double[] vector = {0,1,0};
//
//        double[] result = MathUtil.rotateVector(90,0, vector);
//
//        assertEquals(3, result.length);
//        assertEquals(0, result[0], delta);
//        assertEquals(0, result[1], delta);
//        assertEquals(1, result[2], delta);
//    }
//
//    @Test
//    public void rotateVectorPosYawZTest() {
//
//        double[] vector = {0,0,1};
//
//        double[] result = MathUtil.rotateVector(90,0, vector);
//
//        assertEquals(3, result.length);
//        assertEquals(1, result[0], delta);
//        assertEquals(0, result[1], delta);
//        assertEquals(0, result[2], delta);
//    }
//
//    @Test
//    public void rotateVectorNegYawXTest() {
//
//        double[] vector = {1,0,0};
//
//        double[] result = MathUtil.rotateVector(-90,0, vector);
//
//        assertEquals(3, result.length);
//        assertEquals(0, result[0], delta);
//        assertEquals(1, result[1], delta);
//        assertEquals(0, result[2], delta);
//    }
//
//    @Test
//    public void rotateVectorNegYawYTest() {
//
//        double[] vector = {0,1,0};
//
//        double[] result = MathUtil.rotateVector(90,0, vector);
//
//        assertEquals(3, result.length);
//        assertEquals(0, result[0], delta);
//        assertEquals(0, result[1], delta);
//        assertEquals(1, result[2], delta);
//    }
//
//    @Test
//    public void rotateVectorNegYawZTest() {
//
//        double[] vector = {0,0,1};
//
//        double[] result = MathUtil.rotateVector(-90,0, vector);
//
//        assertEquals(3, result.length);
//        assertEquals(-1, result[0], delta);
//        assertEquals(0, result[1], delta);
//        assertEquals(0, result[2], delta);
//    }
//
//    /*
//        Pitch
//     */
//
//    @Test
//    public void rotateVectorPosPitchXTest() {
//
//        double[] vector = {1,0,0};
//
//        double[] result = MathUtil.rotateVector(0,90, vector);
//
//        assertEquals(3, result.length);
//        assertEquals(0, result[0], delta);
//        assertEquals(0, result[1], delta);
//        assertEquals(1, result[2], delta);
//    }
//
//    @Test
//    public void rotateVectorPosPitchYTest() {
//
//        double[] vector = {0,1,0};
//
//        double[] result = MathUtil.rotateVector(0,90, vector);
//
//        assertEquals(3, result.length);
//        assertEquals(-1, result[0], delta);
//        assertEquals(0, result[1], delta);
//        assertEquals(0, result[2], delta);
//    }
//
//    @Test
//    public void rotateVectorPosPitchZTest() {
//
//        double[] vector = {0,0,1};
//
//        double[] result = MathUtil.rotateVector(0,90, vector);
//
//        assertEquals(3, result.length);
//        assertEquals(0, result[0], delta);
//        assertEquals(1, result[1], delta);
//        assertEquals(0, result[2], delta);
//    }
//
//    @Test
//    public void rotateVectorNegPitchXTest() {
//
//        double[] vector = {1,0,0};
//
//        double[] result = MathUtil.rotateVector(0,-90, vector);
//
//        assertEquals(3, result.length);
//        assertEquals(0, result[0], delta);
//        assertEquals(0, result[1], delta);
//        assertEquals(-1, result[2], delta);
//    }
//
//    @Test
//    public void rotateVectorNegPitchYTest() {
//
//        double[] vector = {0,1,0};
//
//        double[] result = MathUtil.rotateVector(0,-90, vector);
//
//        assertEquals(3, result.length);
//        assertEquals(1, result[0], delta);
//        assertEquals(0, result[1], delta);
//        assertEquals(0, result[2], delta);
//    }
//
//    @Test
//    public void rotateVectorNegPitchZTest() {
//
//        double[] vector = {0,0,1};
//
//        double[] result = MathUtil.rotateVector(0,-90, vector);
//
//        assertEquals(3, result.length);
//        assertEquals(0, result[0], delta);
//        assertEquals(1, result[1], delta);
//        assertEquals(0, result[2], delta);
//    }
//
////    @Test
////    public void testTest() {
////        double location = MathUtil.applyPerspective(0, 0, 0);
////    }
//
//    @Test
//    public void simplifyDouble() {
//        assertEquals(0.56, MathUtil.simplifyDouble(0.55555555), delta);
//    }
//
//}