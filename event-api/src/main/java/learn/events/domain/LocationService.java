package learn.events.domain;

import learn.events.data.LocationRepository;
import learn.events.data.DataAccessException;
import learn.events.models.Location;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;

@Service
public class LocationService {

    private final LocationRepository repository;
    private final Validator validator;

    public LocationService(LocationRepository repository, Validator validator) {
        this.repository = repository;
        this.validator = validator;
    }

    public List<Location> findAll() throws DataAccessException {
        return repository.findAll();
    }

    public Location findById(int locationId) throws DataAccessException {
        if (locationId < 0) {
            return null;
        }
        return repository.findById(locationId);
    }

    public Result<Location> add(Location location) throws DataAccessException {

        Result<Location> result = validate(location);
        if (!result.isSuccess()) {
            return result;
        }

        if (location.getId() != 0) {
            result.addErrorMessage("id must not be set for add");
            return result;
        }

        location = repository.add(location);
        result.setPayload(location);
        return result;
    }

    public Result<Location> update(Location location) throws DataAccessException {

        Result<Location> result = validate(location);
        if (!result.isSuccess()) {
            return result;
        }

        if (location.getId() < 0) {
            result.addErrorMessage("id is required");
            return result;
        }

        boolean success = repository.update(location);
        if (!success) {
            result.addErrorMessage("could not update location id " + location.getId());
        }

        return result;
    }

    public boolean deleteById(int locationId) throws DataAccessException {
        if (locationId < 0) {
            return false;
        }
        return repository.deleteById(locationId);
    }

    private Result<Location> validate(Location location) {
        Result<Location> result = new Result();
        if (location == null) {
            result.addErrorMessage("location cannot be null");
            return result;
        }
        Set<ConstraintViolation<Location>> violations = validator.validate(location);
        for (ConstraintViolation<Location> violation : violations) {
            result.addErrorMessage(violation.getMessage());
        }
        return result;
    }
}
