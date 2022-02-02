

package com.evieclient.events;

import com.evieclient.Evie;

/** Basic event class.
 * <p>All events will extend this class in some way.</p>
 * @author Nora Cos | Nora#0001
 * @since 1.0.0 **/
public class Event {

    /** Posts the event to the EventBus. **/
    public void post() {
        Evie.EVENT_BUS.post(this);
    }
}
