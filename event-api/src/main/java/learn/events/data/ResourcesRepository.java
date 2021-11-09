package learn.events.data;

import learn.events.models.Resources;

import java.util.List;

public interface ResourcesRepository {
    List<Resources> findAll();

    List<Resources> findByDescription(String description);

    List<Resources> findResourcesByLocationId(int locationId);

    Resources findById(int id);

    Resources add(Resources resource);

    boolean deleteById(int resourceId);
}
