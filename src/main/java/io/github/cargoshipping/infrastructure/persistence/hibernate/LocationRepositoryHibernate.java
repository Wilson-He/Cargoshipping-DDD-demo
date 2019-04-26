package io.github.cargoshipping.infrastructure.persistence.hibernate;

import io.github.cargoshipping.domain.model.location.Location;
import io.github.cargoshipping.domain.model.location.LocationRepository;
import io.github.cargoshipping.domain.model.location.UnLocode;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public final class LocationRepositoryHibernate extends HibernateRepository implements LocationRepository {

    @Override
    public Location find(final UnLocode unLocode) {
        return (Location) getSession().
                createQuery("from Location where unLocode = ?").
                setParameter(0, unLocode).
                uniqueResult();
    }

    @Override
    public List<Location> findAll() {
        return getSession().createQuery("from Location").list();
    }

}
