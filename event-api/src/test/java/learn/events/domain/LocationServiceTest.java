package learn.events.domain;

import learn.events.data.DataAccessException;
import learn.events.data.LocationRepository;
import learn.events.models.Location;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static learn.events.TestHelper.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class LocationServiceTest {

    @MockBean
    LocationRepository repository;

    @Autowired
    LocationService service;


    @Test
    void shouldFindAll() throws DataAccessException {
        List<Location> expected = List.of(makeValidLocation(1).setTitle("Title 1"),
                makeValidLocation(2).setTitle("Title 2"));
        when(repository.findAll()).thenReturn(expected);
        List<Location> actual = service.findAll();
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindValidId() throws DataAccessException {
        Location expected = makeValidLocation(VALID_ID);
        when(repository.findById(VALID_ID)).thenReturn(expected);
        Location actual = service.findById(VALID_ID);
        assertEquals(expected, actual);
    }



    @Test
    void addShouldRejectNullLocation() throws DataAccessException {
        Result expected = makeResult("location cannot be null");
        Result actual = service.add(null);
        assertEquals(expected, actual);
    }

    @Test
    void addShouldRejectHugeTitle() throws DataAccessException {
        Location location = makeValidLocation(0);
        location.setTitle("title".repeat(25));
        Result expected = makeResult("title is too large");
        Result actual = service.add(location);
        assertEquals(expected, actual);
    }

    @Test
    void addShouldRejectBlankCity() throws DataAccessException {
        Location location = makeValidLocation(0);
        location.setCity(" ");
        Result expected = makeResult("Location city is required.");
        Result actual = service.add(location);
        assertEquals(expected, actual);
    }

    @Test
    void addShouldRejectNullCity() throws DataAccessException {
        Location location = makeValidLocation(0);
        location.setCity(null);
        Result expected = makeResult("Location city is required.");
        Result actual = service.add(location);
        assertEquals(expected, actual);
    }

    @Test
    void addShouldRejectBlankAddress() throws DataAccessException {
        Location location = makeValidLocation(0);
        location.setAddress(" ");
        Result expected = makeResult("Location address is required.");
        Result actual = service.add(location);
        assertEquals(expected, actual);
    }

    @Test
    void addShouldRejectNullAddress() throws DataAccessException {
        Location location = makeValidLocation(0);
        location.setAddress(null);
        Result expected = makeResult("Location address is required.");
        Result actual = service.add(location);
        assertEquals(expected, actual);
    }

    @Test
    void addShouldRejectEmptyState() throws DataAccessException {
        Location location = makeValidLocation(0);
        location.setState(" ");
        Result expected = makeResult("Location state is required.");
        Result actual = service.add(location);
        assertEquals(expected, actual);
    }

    @Test
    void addShouldRejectNullState() throws DataAccessException {
        Location location = makeValidLocation(0);
        location.setState(null);
        Result expected = makeResult("Location state is required.");
        Result actual = service.add(location);
        assertEquals(expected, actual);
    }

    @Test
    void addShouldRejectInvalidZipcodes() throws DataAccessException {
        Location location = makeValidLocation(0);
        location.setZipcode(1092734);
        Result expected = makeResult("Invalid US zipcode, must be 5 digits");
        Result actual = service.add(location);
        assertEquals(expected, actual);
    }

    @Test
    void addShouldRejectNonStateAbbreviations() throws DataAccessException {
        Location location = makeValidLocation(0);
        location.setState("A");      // this test will fail
        Result expected = makeResult("State must be a 2 letter abbreviation");
        Result actual = service.add(location);
        assertEquals(expected, actual);
    }

    @Test
    void addShouldRejectDuplicates() throws DataAccessException {

        List<Location> existingGames = List.of(makeValidLocation(1));
        Location location = makeValidLocation(0);
        when(repository.findAll()).thenReturn(existingGames);

        Result expected = makeResult("cannot have duplicate location");
        Result actual = service.add(location);
        assertEquals(expected, actual);
    }

    @Test
    void shouldAdd() throws DataAccessException {
        Result expected = makeResult();
        expected.setPayload(makeValidLocation(VALID_ID));

        when(repository.add(any())).thenReturn(makeValidLocation(VALID_ID));

        Result actual = service.add(makeValidLocation(0));
        assertEquals(expected, actual);
    }



    @Test
    void updateShouldRejectNullLocation() throws DataAccessException {
        Result expected = makeResult("location cannot be null");
        Result actual = service.update(null);
        assertEquals(expected, actual);
    }

    @Test
    void updateShouldRejectHugeTitle() throws DataAccessException {
        Location location = makeValidLocation(0);
        location.setTitle("title".repeat(25));
        Result expected = makeResult("title is too large");
        Result actual = service.update(location);
        assertEquals(expected, actual);
    }

    @Test
    void updateShouldRejectBlankCity() throws DataAccessException {
        Location location = makeValidLocation(VALID_ID);
        location.setCity(" ");
        Result expected = makeResult("Location city is required.");
        Result actual = service.update(location);
        assertEquals(expected, actual);
    }

    @Test
    void updateShouldRejectNullCity() throws DataAccessException {
        Location location = makeValidLocation(VALID_ID);
        location.setCity(null);
        Result expected = makeResult("Location city is required.");
        Result actual = service.update(location);
        assertEquals(expected, actual);
    }

    @Test
    void updateShouldRejectBlankAddress() throws DataAccessException {
        Location location = makeValidLocation(VALID_ID);
        location.setAddress(" ");
        Result expected = makeResult("Location address is required.");
        Result actual = service.update(location);
        assertEquals(expected, actual);
    }

    @Test
    void updateShouldRejectNullAddress() throws DataAccessException {
        Location location = makeValidLocation(VALID_ID);
        location.setAddress(null);
        Result expected = makeResult("Location address is required.");
        Result actual = service.update(location);
        assertEquals(expected, actual);
    }

    @Test
    void updateShouldRejectBlankState() throws DataAccessException {
        Location location = makeValidLocation(VALID_ID);
        location.setState(" ");
        Result expected = makeResult("Location state is required.");
        Result actual = service.update(location);
        assertEquals(expected, actual);
    }

    @Test
    void updateShouldRejectInvalidZipcodes() throws DataAccessException {
        Location location = makeValidLocation(0);
        location.setZipcode(1092734);
        Result expected = makeResult("Invalid US zipcode, must be 5 digits");
        Result actual = service.update(location);
        assertEquals(expected, actual);
    }

    @Test
    void updateShouldRejectNullState() throws DataAccessException {
        Location location = makeValidLocation(VALID_ID);
        location.setState(null);
        Result expected = makeResult("Location state is required.");
        Result actual = service.update(location);
        assertEquals(expected, actual);
    }

    @Test
    void shouldUpdateExisting() throws DataAccessException {
        Location valid = makeValidLocation(VALID_ID);
        when(repository.findById(VALID_ID)).thenReturn(valid);
        when(repository.update(any())).thenReturn(true);

        Result expected = makeResult();
        Result actual = service.update(valid);
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotUpdateMissing() throws DataAccessException {
        Result expected = makeResult("could not update location id 127");
        Location location = makeValidLocation(127).setTitle("non-duplicate title");
        Result actual = service.update(location);
        assertEquals(expected, actual);
    }


}
