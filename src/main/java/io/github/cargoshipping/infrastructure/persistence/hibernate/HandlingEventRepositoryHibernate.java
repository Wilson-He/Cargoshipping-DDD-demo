package io.github.cargoshipping.infrastructure.persistence.hibernate;

import io.github.cargoshipping.domain.model.cargo.TrackingId;
import io.github.cargoshipping.domain.model.handling.HandlingEvent;
import io.github.cargoshipping.domain.model.handling.HandlingEventRepository;
import io.github.cargoshipping.domain.model.handling.HandlingHistory;
import org.springframework.stereotype.Repository;

/**
 * Hibernate implementation of HandlingEventRepository.
 */
@Repository
public class HandlingEventRepositoryHibernate extends HibernateRepository implements HandlingEventRepository {

    @Override
    public void store(final HandlingEvent event) {
        getSession().save(event);
    }

    @Override
    public HandlingHistory lookupHandlingHistoryOfCargo(final TrackingId trackingId) {
        System.out.println("------------------ 根据货物的 trackingId 查询 该货物的 处理历史。。。");
        return new HandlingHistory(getSession().createQuery(
                "from HandlingEvent where cargo.trackingId = :tid").
                setParameter("tid", trackingId).
                list()
        );
    }

}
