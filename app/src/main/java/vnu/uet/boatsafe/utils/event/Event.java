package vnu.uet.boatsafe.utils.event;

import org.greenrobot.eventbus.EventBus;



public class Event {

    public static void postEvent (EventMessage eventMessage) {

        EventBus.getDefault().post(eventMessage);
    }

    public static void postEventSticky (EventMessage eventMessage) {

        EventBus.getDefault().postSticky(eventMessage);
    }
}
