package io.github.cargoshipping.application.impl;

import io.github.cargoshipping.application.ApplicationEvents;
import io.github.cargoshipping.application.CargoInspectionService;
import io.github.cargoshipping.domain.model.cargo.Cargo;
import io.github.cargoshipping.domain.model.cargo.CargoRepository;
import io.github.cargoshipping.domain.model.cargo.TrackingId;
import io.github.cargoshipping.domain.model.handling.HandlingEventRepository;
import io.github.cargoshipping.domain.model.handling.HandlingHistory;
import org.apache.commons.lang3.Validate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

public class CargoInspectionServiceImpl implements CargoInspectionService {

    private final ApplicationEvents applicationEvents;
    private final CargoRepository cargoRepository;
    private final HandlingEventRepository handlingEventRepository;
    private final Log logger = LogFactory.getLog(getClass());

    public CargoInspectionServiceImpl(final ApplicationEvents applicationEvents,
                                      final CargoRepository cargoRepository,
                                      final HandlingEventRepository handlingEventRepository) {
        this.applicationEvents = applicationEvents;
        this.cargoRepository = cargoRepository;
        this.handlingEventRepository = handlingEventRepository;
    }

    @Override
    @Transactional
    public void inspectCargo(final TrackingId trackingId) {
        Validate.notNull(trackingId, "Tracking ID is required");
        final Cargo cargo = cargoRepository.find(trackingId);
        if (cargo == null) {
            logger.warn("Can't inspect non-existing cargo " + trackingId);
            return;
        }
        final HandlingHistory handlingHistory = handlingEventRepository.lookupHandlingHistoryOfCargo(trackingId);
        cargo.deriveDeliveryProgress(handlingHistory);
        if (cargo.delivery().isMisdirected()) {
            applicationEvents.cargoWasMisdirected(cargo);
        }
        if (cargo.delivery().isUnloadedAtDestination()) {
            applicationEvents.cargoHasArrived(cargo);
        }
        cargoRepository.store(cargo);
    }

}
