package io.github.cargoshipping.application.impl;

import io.github.cargoshipping.application.ApplicationEvents;
import io.github.cargoshipping.application.HandlingEventService;
import io.github.cargoshipping.domain.model.cargo.TrackingId;
import io.github.cargoshipping.domain.model.handling.CannotCreateHandlingEventException;
import io.github.cargoshipping.domain.model.handling.HandlingEvent;
import io.github.cargoshipping.domain.model.handling.HandlingEventFactory;
import io.github.cargoshipping.domain.model.handling.HandlingEventRepository;
import io.github.cargoshipping.domain.model.location.UnLocode;
import io.github.cargoshipping.domain.model.voyage.VoyageNumber;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

public class HandlingEventServiceImpl implements HandlingEventService {

    private final ApplicationEvents applicationEvents;
    private final HandlingEventRepository handlingEventRepository;
    private final HandlingEventFactory handlingEventFactory;
    private final Log logger = LogFactory.getLog(HandlingEventServiceImpl.class);

    public HandlingEventServiceImpl(final HandlingEventRepository handlingEventRepository,
                                    final ApplicationEvents applicationEvents,
                                    final HandlingEventFactory handlingEventFactory) {
        this.handlingEventRepository = handlingEventRepository;
        this.applicationEvents = applicationEvents;
        this.handlingEventFactory = handlingEventFactory;
    }

    @Override
    @Transactional(rollbackFor = CannotCreateHandlingEventException.class)
    public void registerHandlingEvent(final Date completionTime,
                                      final TrackingId trackingId,
                                      final VoyageNumber voyageNumber,
                                      final UnLocode unLocode,
                                      final HandlingEvent.Type type) throws CannotCreateHandlingEventException {
        final Date registrationTime = new Date();
    /* Using a factory to create a HandlingEvent (aggregate). This is where
       it is determined wether the incoming data, the attempt, actually is capable
       of representing a real handling event. */
        final HandlingEvent event = handlingEventFactory.createHandlingEvent(
                registrationTime, completionTime, trackingId, voyageNumber, unLocode, type
        );

    /* Store the new handling event, which updates the persistent
       state of the handling event aggregate (but not the cargo aggregate -
       that happens asynchronously!)
     */
        handlingEventRepository.store(event);

        /* Publish an event stating that a cargo has been handled. */
        applicationEvents.cargoWasHandled(event);

        logger.info("Registered handling event");
    }

}
