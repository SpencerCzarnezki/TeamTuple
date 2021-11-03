package learn.events.domain;

import learn.events.data.DataAccessException;
import learn.events.data.ResourcesRepository;
import learn.events.models.Resources;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourcesService {

    private final ResourcesRepository repository;


    public ResourcesService(ResourcesRepository repository) {
        this.repository = repository;
    }

    public List<Resources> findAll() throws DataAccessException {
        return repository.findAll();
    }

    public List<Resources> findByDescription(String description) throws DataAccessException {
        return repository.findByDescription(description);
    }

    public Resources findById(int resourceId) throws DataAccessException {
        if (resourceId < 0) {
            return null;
        }
        return repository.findById(resourceId);
    }

    public Result<Resources> add(Resources resource) throws DataAccessException {

        Result<Resources> result = validate(resource);
        if (!result.isSuccess()) {
            return result;
        }

        if (resource.getResourceId() != 0) {
            result.addErrorMessage("id must not be set for add");
            return result;
        }

        resource = repository.add(resource);
        result.setPayload(resource);
        return result;
    }

    public boolean deleteById(int resourceId) throws DataAccessException {
        if (resourceId < 0) {
            return false;
        }
        return repository.deleteById(resourceId);
    }

    private Result<Resources> validate(Resources resource) {
        Result<Resources> result= new Result<>();

        if (resource == null) {
            result.addErrorMessage("Resource cannot be null");
            return result;
        }

        if (resource.getResource() == null || resource.getResource().isBlank()) {
            result.addErrorMessage("Resource description is required");
            return result;
        }

        if (resource.getLocationId() < 0) {
            result.addErrorMessage("Location Id is invalid");
            return result;
        }

        return result;
    }
}
