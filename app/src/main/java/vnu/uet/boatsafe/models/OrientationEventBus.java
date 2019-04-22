package vnu.uet.boatsafe.models;

public class OrientationEventBus {
    private final double ax;
    private final double ay;
    private final double az;
    private final double a;
    private final double azimuth;

    public OrientationEventBus(double ax, double ay, double az, double a, double azimuth) {
        this.ax = ax;
        this.ay = ay;
        this.az = az;
        this.a = a;
        this.azimuth = azimuth;
    }

    public double getAx() {
        return ax;
    }

    public double getAy() {
        return ay;
    }

    public double getAz() {
        return az;
    }

    public double getA() {
        return a;
    }

    public double getAzimuth() {
        return azimuth;
    }
}
