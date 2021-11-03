package learn.events.domain;


import learn.events.data.DataAccessException;
import learn.events.data.ResourcesRepository;
import learn.events.models.Resources;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static learn.events.TestHelper.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class ResourcesServiceTest {

    @MockBean
    ResourcesRepository repository;

    @Autowired
    ResourcesService service;

    @Test
    void shouldFindAll() throws DataAccessException {
        List<Resources> expected = List.of(makeValidResource(1).setResource("Resource 1"),
                makeValidResource(2).setResource("Resource 2"));
        when(repository.findAll()).thenReturn(expected);
        List<Resources> actual = service.findAll();
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindValidId() throws DataAccessException {
        Resources expected = makeValidResource(VALID_ID);
        when(repository.findById(VALID_ID)).thenReturn(expected);
        Resources actual = service.findById(VALID_ID);
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindValidResourceDescription() throws DataAccessException {
        List<Resources> expected = new ArrayList<>();
        expected.add(makeValidResource(VALID_ID));
        when(repository.findByDescription("Description")).thenReturn(expected);
        List<Resources> actual = service.findByDescription("Description");
        assertEquals(expected.get(0), actual.get(0));
    }

    @Test
    void addShouldRejectNullResource() throws DataAccessException {
        Result expected = makeResult("Resource cannot be null");
        Result actual = service.add(null);
        assertEquals(expected, actual);
    }

    @Test
    void addShouldRejectBlankDescription() throws DataAccessException {
        Resources resource = makeValidResource(0);
        resource.setResource(" ");
        Result expected = makeResult("Resource description is required");
        Result actual = service.add(resource);
        assertEquals(expected, actual);
    }

    @Test
    void addShouldRejectNullDescription() throws DataAccessException {
        Resources resource = makeValidResource(0);
        resource.setResource(null);
        Result expected = makeResult("Resource description is required");
        Result actual = service.add(resource);
        assertEquals(expected, actual);
    }

    @Test
    void addShouldRejectInvalidLocationId() throws DataAccessException {
        Resources resource = makeValidResource(0);
        resource.setLocationId(-2);
        Result expected = makeResult("Location Id is invalid");
        Result actual = service.add(resource);
        assertEquals(expected, actual);
    }

    @Test
    void shouldAdd() throws DataAccessException {
        Result expected = makeResult();
        expected.setPayload(makeValidResource(VALID_ID));

        when(repository.add(any())).thenReturn(makeValidResource(VALID_ID));

        Result actual = service.add(makeValidResource(0));
        assertEquals(expected, actual);
    }

    @Test
    void shouldDeleteExisting() throws DataAccessException {
        when(repository.deleteById(VALID_ID)).thenReturn(true);
        boolean success = service.deleteById(VALID_ID);
        assertTrue(success);
    }

    @Test
    void shouldNotDeleteMissing() throws DataAccessException {
        boolean success = service.deleteById(VALID_ID + 100);
        assertFalse(success);
    }

}
