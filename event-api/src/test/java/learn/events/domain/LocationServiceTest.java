package learn.events.domain;

import learn.events.data.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class LocationServiceTest {

    @MockBean
    LocationRepository repository;

    @Autowired
    LocationService service;


}
