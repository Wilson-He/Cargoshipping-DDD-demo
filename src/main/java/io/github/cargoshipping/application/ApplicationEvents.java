package io.github.cargoshipping.application;

import io.github.cargoshipping.domain.model.cargo.Cargo;
import io.github.cargoshipping.domain.model.handling.HandlingEvent;
import io.github.cargoshipping.interfaces.handling.HandlingEventRegistrationAttempt;

/**
 * This interface provides a way to let other parts
 * of the system know about events that have occurred.
 * <p/>
 * It may be implemented synchronously or asynchronously, using
 * for example JMS.
 */
public interface ApplicationEvents {

    /**
     * A cargo has been handled.
     *
     * @param event handling event
     */
    void cargoWasHandled(HandlingEvent event);

    /**
     * A cargo has been misdirected.
     *
     * @param cargo cargo
     */
    void cargoWasMisdirected(Cargo cargo);

    /**
     * A cargo has arrived at its final destination.
     *
     * @param cargo cargo
     */
    void cargoHasArrived(Cargo cargo);

    /**
     * A handling event regitration attempt is received.
     *
     * @param attempt handling event registration attempt
     */
    void receivedHandlingEventRegistrationAttempt(HandlingEventRegistrationAttempt attempt);

}
