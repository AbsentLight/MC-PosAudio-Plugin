package xyz.darke.darkpas.util;

public class MathUtil {




    public static double distance2D(final double x1, final double y1, final double x2, final double y2) {
        double x = x2 - x1;
        double y = y2 - y1;

        x *= x; // x ** x
        y *= y; // y ** y

        return Math.sqrt(x + y);
    }

    public static double angle2D(final double x1, final double y1, final double x2, final double y2, final double mod) {
        return Math.atan2(x2 - x1, y2 - y1) + mod;
    }

    public static double distance3D(final double[] p1, final double[] p2) {

        final double a = Math.pow((p2[0] - p1[0]), 2);
        final double b = Math.pow((p2[1] - p1[1]), 2);
        final double c = Math.pow((p2[2] - p1[2]), 2);

        return Math.sqrt(a + b + c);
    }

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

    public static double[][] getRollMatrix() {
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

    public static double[][] formRotationMatrix(double yawAngle, double pitchAngle) {
        double[][] yawRotM = formYawMatrix(yawAngle);
        double[][] pitchRotM = formPitchMatrix(pitchAngle);
        double[][] rollRotM = getRollMatrix();

        double[][] tempM = matrixMult(pitchRotM,rollRotM);
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
        for (int i=0; i<v1.length; i++) {
            colVector[0][i] = v1[i];
        }
        return colVector;
    }

    public static double vectorMagnitude(double[] v1) {
        return Math.sqrt(Math.pow(v1[0],2) + Math.pow(v1[1],2) + Math.pow(v1[2],2));
    }

}
