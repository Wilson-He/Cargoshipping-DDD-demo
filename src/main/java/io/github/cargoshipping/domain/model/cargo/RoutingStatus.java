package io.github.cargoshipping.domain.model.cargo;

import io.github.cargoshipping.domain.shared.ValueObject;

/**
 * Routing status.
 */
public enum RoutingStatus implements ValueObject<RoutingStatus> {
    NOT_ROUTED, ROUTED, MISROUTED;

    @Override
    public boolean sameValueAs(final RoutingStatus other) {
        return this.equals(other);
    }

}
