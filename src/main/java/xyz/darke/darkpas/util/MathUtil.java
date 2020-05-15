package xyz.darke.darkpas.util;

import xyz.darke.darkpas.DarkPAS;
import xyz.darke.darkpas.data.ServerConfig;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;

public class MathUtil {

//    public static double distance2D(final double x1, final double y1, final double x2, final double y2) {
//        double x = x2 - x1;
//        double y = y2 - y1;
//
//        x *= x; // x ** x
//        y *= y; // y ** y
//
//        return Math.sqrt(x + y);
//    }
//
//    public static double angle2D(final double x1, final double y1, final double x2, final double y2, final double mod) {
//        return Math.atan2(x2 - x1, y2 - y1) + mod;
//    }
//
//    public static double distance3D(final double[] p1, final double[] p2) {
//
//        final double a = Math.pow((p2[0] - p1[0]), 2);
//        final double b = Math.pow((p2[1] - p1[1]), 2);
//        final double c = Math.pow((p2[2] - p1[2]), 2);
//
//        return Math.sqrt(a + b + c);
//    }

    public static double[] normalise3D(final double[] p1, final double[] p2) {
        // p1 -> [0] * 3
        // p2 normalised rel to p1

        double[] normalisedPos = new double[3];
        normalisedPos[0] = p2[0] - p1[0];
        normalisedPos[1] = p2[1] - p1[1];
        normalisedPos[2] = p2[2] - p1[2];
        return normalisedPos;
    }

    public static double[][] formYawMatrix(double yawAngle) {

        yawAngle = Math.toRadians(yawAngle);

        double[][] matrix = new double[3][3];

        matrix[0][0] = Math.cos(yawAngle);
        matrix[0][1] = Math.sin(yawAngle) * -1;
        matrix[0][2] = 0;

        matrix[1][0] = Math.sin(yawAngle);
        matrix[1][1] = Math.cos(yawAngle);
        matrix[1][2] = 0;

        matrix[2][0] = 0;
        matrix[2][1] = 0;
        matrix[2][2] = 1;

        return matrix;
    }

    public static double[][] formPitchMatrix(double pitchAngle) {

        pitchAngle = Math.toRadians(pitchAngle);

        double[][] matrix = new double[3][3];

        matrix[0][0] = Math.cos(pitchAngle);
        matrix[0][1] = 0;
        matrix[0][2] = Math.sin(pitchAngle);

        matrix[1][0] = 0;
        matrix[1][1] = 1;
        matrix[1][2] = 0;

        matrix[2][0] = Math.sin(pitchAngle) * -1;
        matrix[2][1] = 0;
        matrix[2][2] = Math.cos(pitchAngle);

        return matrix;
    }

    public static double[][] formRollMatrix(double rollAngle) {

        rollAngle = Math.toRadians(rollAngle);

        double[][] matrix = new double[3][3];
        matrix[0][0] = 1;
        matrix[0][1] = 0;
        matrix[0][2] = 0;

        matrix[1][0] = 0;
        matrix[1][1] = Math.cos(rollAngle);
        matrix[1][2] = Math.sin(rollAngle) * -1;

        matrix[2][0] = 0;
        matrix[2][1] = Math.sin(rollAngle);
        matrix[2][2] = Math.cos(rollAngle);

        return matrix;
    }

    public static double[][] getIdentityMatrix() {
        double[][] matrix = new double[3][3];
        matrix[0][0] = 1;
        matrix[0][1] = 0;
        matrix[0][2] = 0;

        matrix[1][0] = 0;
        matrix[1][1] = 1;
        matrix[1][2] = 0;

        matrix[2][0] = 0;
        matrix[2][1] = 0;
        matrix[2][2] = 1;

        return matrix;
    }

    public static double[][] getCorrectionMatrix() {
        double[][] yawMatrix = formYawMatrix(DarkPAS.serverConfig.getModYaw());
        double[][] pitchMatrix = formPitchMatrix(DarkPAS.serverConfig.getModPitch());
        double[][] rollMatrix = formRollMatrix(DarkPAS.serverConfig.getModRoll());

        double[][] t = matrixMult(pitchMatrix, rollMatrix);

        return matrixMult(yawMatrix, t);
    }

    public static double[][] formRotationMatrix(double yawAngle, double pitchAngle) {
        double[][] yawRotM = formYawMatrix(yawAngle);
//        System.out.println("Yaw Matrix");
//        printMatrix(yawRotM);

        double[][] pitchRotM = formPitchMatrix(pitchAngle);
//        System.out.println("Pitch Matrix");
//        printMatrix(pitchRotM);

        double[][] rollRotM = formRollMatrix(0);
//        System.out.println("Roll Matrix");
//        printMatrix(rollRotM);

        double[][] tempM = matrixMult(pitchRotM, rollRotM);
//        System.out.println("Pitch*Roll Matrix");
//        printMatrix(tempM);
        return  matrixMult(yawRotM, tempM);
    }

    public static double[][] matrixMult(double[][] m1, double[][] m2) {
        double[][] result = new double[m1.length][m2[0].length];

        for (int row = 0; row < result.length; row++) {
            for (int col = 0; col < result[row].length; col++) {
                result[row][col] = matrixCellMult(m1, m2, row, col);
            }
        }

        return result;
    }

    private static double matrixCellMult(double[][] m1, double[][] m2, int row, int col) {
        double cell = 0;
        for (int i = 0; i < m2.length; i++) {
            cell += m1[row][i] * m2[i][col];
        }
        return cell;
    }

    public static double[][] colVectorToMatrix(double[] v1) {
        double[][] colVector = new double[1][v1.length];

        colVector[0][0] = v1[0];
        colVector[0][1] = v1[1];
        colVector[0][2] = v1[2];

//        for (int i=0; i<v1.length; i++) {
//            colVector[0][i] = v1[i];
//        }
        return colVector;
    }

    public static double vectorMagnitude(double[] v1) {
        return Math.sqrt(Math.pow(v1[0],2) + Math.pow(v1[1],2) + Math.pow(v1[2],2));
    }

    public static double simplifyDouble(double val) {
        BigDecimal d = BigDecimal.valueOf(val).setScale(2, RoundingMode.HALF_UP);
        return d.doubleValue();
    }

    public static double[] rotateVector(double yaw, double pitch, double[] vector) {
        double[][] rotationMatrix  = MathUtil.formRotationMatrix(yaw, pitch);
//        System.out.println("Rotation Matrix");
//        printMatrix(rotationMatrix);
        double[][] newMatrix = MathUtil.matrixMult(MathUtil.colVectorToMatrix(vector), rotationMatrix);

        double[] newVector = colVecMatrixToArray(newMatrix);

        return newVector;
    }

    public static double[] applyPerspective(double yaw, double pitch, double[] vector) {
        double[] rotatedVector = rotateVector(yaw, pitch, vector);
        double[][] newMatrix = MathUtil.matrixMult(MathUtil.colVectorToMatrix(rotatedVector), getCorrectionMatrix());
        return colVecMatrixToArray(newMatrix);
    }

    private static double[] colVecMatrixToArray(double[][] matrix) {
        double[] newVector = new double[3];

        for (int i=0; i<3; i++) {
            newVector[i] = matrix[0][i];
        }
        return newVector;
    }

    public static void printMatrix(double[][] matrix) {

        double[][] simplified = new double[3][3];

        for (int r=0; r<3; r++) {
            for (int c=0; c<3; c++) {
                simplified[r][c] = simplifyDouble(matrix[r][c]);
            }
        }

        System.out.println("[" + Arrays.toString(simplified[0]));
        System.out.println(" " + Arrays.toString(simplified[1]));
        System.out.println(" " + Arrays.toString(simplified[2]) + "]");
    }

}
