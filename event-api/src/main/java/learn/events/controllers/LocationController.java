package learn.events.controllers;


import learn.events.data.DataAccessException;
import learn.events.domain.LocationService;
import learn.events.domain.Result;
import learn.events.models.Location;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/api/location")
public class LocationController {

    private final LocationService service;

    public LocationController(LocationService service) {
        this.service = service;
    }

    @GetMapping
    public List<Location> getLocations() throws DataAccessException {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Location> getById(@PathVariable int id) throws DataAccessException {
        Location location = service.findById(id);
        if (location == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(location);
    }

    @GetMapping("/city/{city}")
    public ResponseEntity<List<Location>> findByCity(@PathVariable String city) throws DataAccessException {
        List<Location> locations = service.findByCity(city);
        if (locations == null || locations.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(locations);
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody @Valid Location location,
                                      BindingResult bindingResult) throws DataAccessException {

        if (bindingResult.hasErrors()) {

            List<String> errorMessages = bindingResult.getAllErrors().stream()
                    .map(oe -> oe.getDefaultMessage())
                    .collect(Collectors.toList());

            return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
        }

        Result result = service.add(location);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable int id,
                                         @RequestBody @Valid Location location, BindingResult bindingResult)
            throws DataAccessException {

        if (bindingResult.hasErrors()) {

            List<String> errorMessages = bindingResult.getAllErrors().stream()
                    .map(oe -> oe.getDefaultMessage())
                    .collect(Collectors.toList());

            return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
        }

        if (id != location.getId()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Result result = service.update(location);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

}
