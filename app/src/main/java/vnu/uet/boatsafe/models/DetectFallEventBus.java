package vnu.uet.boatsafe.models;

public class DetectFallEventBus {
    private final boolean FallState;


    public DetectFallEventBus(boolean fallState) {
        FallState = fallState;
    }

    public boolean isFallState() {
        return FallState;
    }
}
