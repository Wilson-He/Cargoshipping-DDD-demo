package io.github.cargoshipping.domain.service;

import io.github.cargoshipping.domain.model.cargo.Itinerary;
import io.github.cargoshipping.domain.model.cargo.RouteSpecification;

import java.util.List;

/**
 * Routing service.
 */
public interface RoutingService {

    /**
     * @param routeSpecification route specification
     * @return A list of itineraries that satisfy the specification. May be an empty list if no route is found.
     */
    List<Itinerary> fetchRoutesForSpecification(RouteSpecification routeSpecification);

}
