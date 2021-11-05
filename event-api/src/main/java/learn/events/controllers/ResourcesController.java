package learn.events.controllers;

import learn.events.data.DataAccessException;
import learn.events.domain.ResourcesService;
import learn.events.domain.Result;
import learn.events.models.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/api/resources")
public class ResourcesController {

    private final ResourcesService service;

    public ResourcesController(ResourcesService service) {
        this.service = service;
    }

    @GetMapping
    public List<Resources> getResources() throws DataAccessException {
        return service.findAll();
    }

    @GetMapping("/{description}")
    public ResponseEntity<Resources> getById(@PathVariable String description) throws DataAccessException {
        List<Resources> resources = service.findByDescription(description);
        if (resources != null && resources.get(0) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(resources.get(0));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resources> getById(@PathVariable int id) throws DataAccessException {
        Resources resource = service.findById(id);
        if (resource == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(resource);
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody @Valid Resources resource,
                                      BindingResult bindingResult) throws DataAccessException {

        if (bindingResult.hasErrors()) {

            List<String> errorMessages = bindingResult.getAllErrors().stream()
                    .map(oe -> oe.getDefaultMessage())
                    .collect(Collectors.toList());

            return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
        }

        Result result = service.add(resource);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable int id) throws DataAccessException {
        boolean success = service.deleteById(id);
        if (success) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
