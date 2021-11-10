package learn.events.domain;

import learn.events.data.LocationRepository;
import learn.events.data.DataAccessException;
import learn.events.models.Location;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationService {

    private final LocationRepository repository;

    public LocationService(LocationRepository repository) {
        this.repository = repository;
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

    public List<Location> findByCity(String city) throws DataAccessException {
        if (city.isBlank()) {
            return null;
        }
        return repository.findByCity(city);
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





    private Result<Location> validate(Location location) {
        Result<Location> result = validateNullsAndBlanks(location);
        if (!result.isSuccess()) {
            return result;
        }

        if(!validateNoDuplicates(location)) {
            result.addErrorMessage("cannot have duplicate location");
            return result;
        }

        validateFields(location, result);

        return result;
    }

    private boolean validateNoDuplicates(Location location) {
        try {
            List<Location> locations = repository.findAll();


            boolean holder1 = locations.stream()
                    .noneMatch(l -> l.getTitle().equalsIgnoreCase(location.getTitle()));
            boolean holder2 = locations.stream()
                    .noneMatch(l -> l.getAddress().equalsIgnoreCase(location.getAddress()));

            if(!holder1 && !holder2) {
                return false;
            }

            if((!holder1 && holder2) || (holder1 && !holder2) || (holder1 && holder2)) {
                return true;
            }


        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void validateFields(Location location, Result<Location> result) {
        if (location.getState().length() != 2) {
            result.addErrorMessage("State must be a 2 letter abbreviation");
        }

        if (Integer.toString(location.getZipcode()).length() != 5) {
            result.addErrorMessage("Invalid US zipcode, must be 5 digits");
        }

        if (location.getTitle().length() > 100) {
            result.addErrorMessage("title is too large");
        }
    }

    private Result<Location> validateNullsAndBlanks(Location location) {
        Result<Location> result = new Result<>();

        if (location == null) {
            result.addErrorMessage("location cannot be null");
            return result;
        }

        if (location.getCity() == null || location.getCity().isBlank()) {
            result.addErrorMessage("Location city is required.");
        }

        if (location.getAddress() == null || location.getAddress().isBlank()) {
            result.addErrorMessage("Location address is required.");
        }

        if (location.getState() == null || location.getState().isBlank()) {
            result.addErrorMessage("Location state is required.");
        }
        return result;
    }
}
