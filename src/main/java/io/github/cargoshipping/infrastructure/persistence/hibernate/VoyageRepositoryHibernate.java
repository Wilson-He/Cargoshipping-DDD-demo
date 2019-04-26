package io.github.cargoshipping.infrastructure.persistence.hibernate;

import io.github.cargoshipping.domain.model.voyage.Voyage;
import io.github.cargoshipping.domain.model.voyage.VoyageNumber;
import io.github.cargoshipping.domain.model.voyage.VoyageRepository;
import org.springframework.stereotype.Repository;

/**
 * Hibernate implementation of CarrierMovementRepository.
 */
@Repository
public final class VoyageRepositoryHibernate extends HibernateRepository implements VoyageRepository {

    @Override
    public Voyage find(final VoyageNumber voyageNumber) {
        return (Voyage) getSession().
                createQuery("from Voyage where voyageNumber = :vn").
                setParameter("vn", voyageNumber).
                uniqueResult();
    }

}
