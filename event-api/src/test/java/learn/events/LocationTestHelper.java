package learn.events;

import learn.events.domain.Result;
import learn.events.models.Location;

public class LocationTestHelper {

    public static final int VALID_ID = 3;

    public static Result makeResult(String... messages) {
        Result result = new Result();
        for (String message : messages) {
            result.addErrorMessage(message);
        }
        return result;
    }

    public static Location makeValidLocation(int id) {
        return new Location()
                .setId(id)
                .setTitle("Title")
                .setCity("City")
                .setAddress("Address")
                .setZipcode(12345)
                .setState("WI");
    }
}

