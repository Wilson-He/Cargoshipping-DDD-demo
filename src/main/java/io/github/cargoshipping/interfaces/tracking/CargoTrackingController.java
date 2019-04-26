package io.github.cargoshipping.interfaces.tracking;

import io.github.cargoshipping.domain.model.cargo.Cargo;
import io.github.cargoshipping.domain.model.cargo.CargoRepository;
import io.github.cargoshipping.domain.model.cargo.TrackingId;
import io.github.cargoshipping.domain.model.handling.HandlingEvent;
import io.github.cargoshipping.domain.model.handling.HandlingEventRepository;
import io.github.cargoshipping.interfaces.booking.web.CargoAdminController;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller for tracking cargo. This interface sits immediately on top of the
 * domain layer, unlike the booking interface which has a a remote facade and supporting
 * DTOs in between.
 * <p/>
 * An adapter class, designed for the tracking use case, is used to wrap the domain model
 * to make it easier to work with in a web page rendering context. We do not want to apply
 * view rendering constraints to the design of our domain model, and the adapter
 * helps us shield the domain model classes.
 * <p/>
 *
 * @eee io.github.cargoshipping.application.web.CargoTrackingViewAdapter
 * @see CargoAdminController
 */
@RestController
@RequestMapping("/")
public final class CargoTrackingController {

    private CargoRepository cargoRepository;
    private HandlingEventRepository handlingEventRepository;

    @GetMapping("/submit")
    public Map<String, CargoTrackingViewAdapter> onSubmit(final Object command, final BindException errors) throws Exception {
        final TrackCommand trackCommand = (TrackCommand) command;
        final String trackingIdString = trackCommand.getTrackingId();

        System.out.println("====================================================== 追踪 货物：" + trackingIdString);

        final TrackingId trackingId = new TrackingId(trackingIdString);
        final Cargo cargo = cargoRepository.find(trackingId);

        final Map<String, CargoTrackingViewAdapter> model = new HashMap<>();
        if (cargo != null) {
            final List<HandlingEvent> handlingEvents = handlingEventRepository.lookupHandlingHistoryOfCargo(trackingId).distinctEventsByCompletionTime();
            model.put("cargo", new CargoTrackingViewAdapter(cargo, handlingEvents));
        } else {
            errors.rejectValue("trackingId", "cargo.unknown_id", new Object[]{trackCommand.getTrackingId()}, "Unknown tracking id");
        }
        return model;
    }

    public void setCargoRepository(CargoRepository cargoRepository) {
        this.cargoRepository = cargoRepository;
    }

    public void setHandlingEventRepository(HandlingEventRepository handlingEventRepository) {
        this.handlingEventRepository = handlingEventRepository;
    }

}
