package io.github.cargoshipping.interfaces.tracking;

import io.github.cargoshipping.domain.model.cargo.Cargo;
import io.github.cargoshipping.domain.model.cargo.Delivery;
import io.github.cargoshipping.domain.model.cargo.HandlingActivity;
import io.github.cargoshipping.domain.model.handling.HandlingEvent;
import io.github.cargoshipping.domain.model.location.Location;
import io.github.cargoshipping.domain.model.voyage.Voyage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * View adapter for displaying a cargo in a tracking context.
 */
public final class CargoTrackingViewAdapter {

    private final Cargo cargo;
    private final List<HandlingEventViewAdapter> events;
    private final String FORMAT = "yyyy-MM-dd hh:mm";

    /**
     * Constructor.
     *
     * @param cargo
     */
    public CargoTrackingViewAdapter(Cargo cargo,
                                    List<HandlingEvent> handlingEvents) {
        this.cargo = cargo;

        this.events = new ArrayList<HandlingEventViewAdapter>(handlingEvents.size());
        for (HandlingEvent handlingEvent : handlingEvents) {
            events.add(new HandlingEventViewAdapter(handlingEvent));
        }
    }

    /**
     * @param location a location
     * @return A formatted string for displaying the location.
     */
    private String getDisplayText(Location location) {
        return location.name();
    }

    /**
     * @return An unmodifiable list of handling event view adapters.
     */
    public List<HandlingEventViewAdapter> getEvents() {
        return Collections.unmodifiableList(events);
    }

    /**
     * @return A translated string describing the cargo status.
     */
    public String getStatusText() {
        final Delivery delivery = cargo.delivery();
        final String code = "cargo.status." + delivery.transportStatus().name();

        final Object[] args;
        switch (delivery.transportStatus()) {
            case IN_PORT:
                args = new Object[]{getDisplayText(delivery.lastKnownLocation())};
                break;
            case ONBOARD_CARRIER:
                args = new Object[]{delivery.currentVoyage().voyageNumber().idString()};
                break;
            case CLAIMED:
            case NOT_RECEIVED:
            case UNKNOWN:
            default:
                args = null;
                break;
        }

        return code;
    }

    /**
     * @return Cargo destination location.
     */
    public String getDestination() {
        return getDisplayText(cargo.routeSpecification().destination());
    }

    /**
     * @return Cargo osigin location.
     */
    public String getOrigin() {
        return getDisplayText(cargo.origin());
    }

    /**
     * @return Cargo tracking id.
     */
    public String getTrackingId() {
        return cargo.trackingId().idString();
    }

    public String getEta() {
        Date eta = cargo.delivery().estimatedTimeOfArrival();

        if (eta == null) {
            return "?";
        } else {
            return new SimpleDateFormat(FORMAT).format(eta);
        }
    }

    public String getNextExpectedActivity() {
        HandlingActivity activity = cargo.delivery().nextExpectedActivity();
        if (activity == null) {
            return "";
        }

        String text = "Next expected activity is to ";
        HandlingEvent.Type type = activity.type();
        if (type.sameValueAs(HandlingEvent.Type.LOAD)) {
            return
                    text + type.name().toLowerCase() + " cargo onto voyage " + activity.voyage().voyageNumber() +
                            " in " + activity.location().name();
        } else if (type.sameValueAs(HandlingEvent.Type.UNLOAD)) {
            return
                    text + type.name().toLowerCase() + " cargo off of " + activity.voyage().voyageNumber() +
                            " in " + activity.location().name();
        } else {
            return text + type.name().toLowerCase() + " cargo in " + activity.location().name();
        }
    }

    /**
     * @return True if cargo is misdirected.
     */
    public boolean isMisdirected() {
        return cargo.delivery().isMisdirected();
    }

    /**
     * Handling event view adapter component.
     */
    public final class HandlingEventViewAdapter {

        private final HandlingEvent handlingEvent;

        /**
         * Constructor.
         *
         * @param handlingEvent handling event
         */
        public HandlingEventViewAdapter(HandlingEvent handlingEvent) {
            this.handlingEvent = handlingEvent;
        }

        /**
         * @return Location where the event occurred.
         */
        public String getLocation() {
            return handlingEvent.location().name();
        }

        /**
         * @return Time when the event was completed.
         */
        public String getTime() {
            return new SimpleDateFormat(FORMAT).format(handlingEvent.completionTime());
        }

        /**
         * @return Type of event.
         */
        public String getType() {
            return handlingEvent.type().toString();
        }

        /**
         * @return Voyage number, or empty string if not applicable.
         */
        public String getVoyageNumber() {
            final Voyage voyage = handlingEvent.voyage();
            return voyage.voyageNumber().idString();
        }

        /**
         * @return True if the event was expected, according to the cargo's itinerary.
         */
        public boolean isExpected() {
            return cargo.itinerary().isExpected(handlingEvent);
        }

        public String getDescription() {
            Object[] args;

            switch (handlingEvent.type()) {
                case LOAD:
                case UNLOAD:
                    args = new Object[]{
                            handlingEvent.voyage().voyageNumber().idString(),
                            handlingEvent.location().name(),
                            handlingEvent.completionTime()
                    };
                    break;

                case RECEIVE:
                case CLAIM:
                    args = new Object[]{
                            handlingEvent.location().name(),
                            handlingEvent.completionTime()
                    };
                    break;

                default:
                    args = new Object[]{};
            }

            String key = "deliveryHistory.eventDescription." + handlingEvent.type().name();

            return key;
        }

    }

}
