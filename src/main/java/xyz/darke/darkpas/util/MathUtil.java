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

}
