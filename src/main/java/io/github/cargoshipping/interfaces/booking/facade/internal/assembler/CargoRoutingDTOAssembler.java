package io.github.cargoshipping.interfaces.booking.facade.internal.assembler;

import io.github.cargoshipping.domain.model.cargo.Cargo;
import io.github.cargoshipping.domain.model.cargo.Leg;
import io.github.cargoshipping.domain.model.cargo.RoutingStatus;
import io.github.cargoshipping.interfaces.booking.facade.dto.CargoRoutingDTO;

/**
 * Assembler class for the CargoRoutingDTO.
 */
public class CargoRoutingDTOAssembler {

    /**
     * @param cargo cargo
     * @return A cargo routing DTO
     */
    public CargoRoutingDTO toDTO(final Cargo cargo) {
        final CargoRoutingDTO dto = new CargoRoutingDTO(
                cargo.trackingId().idString(),
                cargo.origin().unLocode().idString(),
                cargo.routeSpecification().destination().unLocode().idString(),
                cargo.routeSpecification().arrivalDeadline(),
                cargo.delivery().routingStatus().sameValueAs(RoutingStatus.MISROUTED));
        for (Leg leg : cargo.itinerary().legs()) {
            dto.addLeg(
                    leg.voyage().voyageNumber().idString(),
                    leg.loadLocation().unLocode().idString(),
                    leg.unloadLocation().unLocode().idString(),
                    leg.loadTime(),
                    leg.unloadTime());
        }
        return dto;
    }

}
