package learn.events.data;

import learn.events.models.Location;

import java.util.List;

public interface LocationRepository {
    List<Location> findAll() throws DataAccessException;

    Location findById(int id) throws DataAccessException;

    Location add(Location location) throws DataAccessException;

    boolean update(Location location) throws DataAccessException;

}
