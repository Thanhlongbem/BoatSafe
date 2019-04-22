package vnu.uet.boatsafe.models;



public class LocationEventBus {

    private final boolean stateLocation;


    public LocationEventBus(boolean stateLocation) {

        this.stateLocation = stateLocation;
    }


    public boolean isStateLocation() {
        return stateLocation;
    }
}
