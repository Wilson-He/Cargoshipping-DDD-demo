package io.github.cargoshipping.interfaces.booking.facade.internal.assembler;

import io.github.cargoshipping.domain.model.cargo.Itinerary;
import io.github.cargoshipping.domain.model.cargo.Leg;
import io.github.cargoshipping.domain.model.location.Location;
import io.github.cargoshipping.domain.model.location.LocationRepository;
import io.github.cargoshipping.domain.model.location.UnLocode;
import io.github.cargoshipping.domain.model.voyage.Voyage;
import io.github.cargoshipping.domain.model.voyage.VoyageNumber;
import io.github.cargoshipping.domain.model.voyage.VoyageRepository;
import io.github.cargoshipping.interfaces.booking.facade.dto.LegDTO;
import io.github.cargoshipping.interfaces.booking.facade.dto.RouteCandidateDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Assembler class for the ItineraryCandidateDTO.
 */
public class ItineraryCandidateDTOAssembler {

    /**
     * @param itinerary itinerary
     * @return A route candidate DTO
     */
    public RouteCandidateDTO toDTO(final Itinerary itinerary) {
        final List<LegDTO> legDTOs = new ArrayList<LegDTO>(itinerary.legs().size());
        for (Leg leg : itinerary.legs()) {
            legDTOs.add(toLegDTO(leg));
        }
        return new RouteCandidateDTO(legDTOs);
    }

    /**
     * @param leg leg
     * @return A leg DTO
     */
    protected LegDTO toLegDTO(final Leg leg) {
        final VoyageNumber voyageNumber = leg.voyage().voyageNumber();
        final UnLocode from = leg.loadLocation().unLocode();
        final UnLocode to = leg.unloadLocation().unLocode();
        return new LegDTO(voyageNumber.idString(), from.idString(), to.idString(), leg.loadTime(), leg.unloadTime());
    }

    /**
     * @param routeCandidateDTO  route candidate DTO
     * @param voyageRepository   voyage repository
     * @param locationRepository location repository
     * @return An itinerary
     */
    public Itinerary fromDTO(final RouteCandidateDTO routeCandidateDTO,
                             final VoyageRepository voyageRepository,
                             final LocationRepository locationRepository) {
        final List<Leg> legs = new ArrayList<Leg>(routeCandidateDTO.getLegs().size());
        for (LegDTO legDTO : routeCandidateDTO.getLegs()) {
            final VoyageNumber voyageNumber = new VoyageNumber(legDTO.getVoyageNumber());
            final Voyage voyage = voyageRepository.find(voyageNumber);
            final Location from = locationRepository.find(new UnLocode(legDTO.getFrom()));
            final Location to = locationRepository.find(new UnLocode(legDTO.getTo()));
            legs.add(new Leg(voyage, from, to, legDTO.getLoadTime(), legDTO.getUnloadTime()));
        }
        return new Itinerary(legs);
    }
}
