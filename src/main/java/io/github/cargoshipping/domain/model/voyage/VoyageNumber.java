package io.github.cargoshipping.domain.model.voyage;

import io.github.cargoshipping.domain.shared.ValueObject;
import org.apache.commons.lang3.Validate;

/**
 * Identifies a voyage.
 */
public class VoyageNumber implements ValueObject<VoyageNumber> {

    private String number;

    public VoyageNumber(String number) {
        Validate.notNull(number);

        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (!(o instanceof VoyageNumber)) {
            return false;
        }

        final VoyageNumber other = (VoyageNumber) o;

        return sameValueAs(other);
    }

    @Override
    public int hashCode() {
        return number.hashCode();
    }

    @Override
    public boolean sameValueAs(VoyageNumber other) {
        return other != null && this.number.equals(other.number);
    }

    @Override
    public String toString() {
        return number;
    }

    public String idString() {
        return number;
    }

    VoyageNumber() {
        // Needed by Hibernate
    }

}