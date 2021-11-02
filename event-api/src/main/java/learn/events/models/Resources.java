package learn.events.models;

import java.util.Objects;

public class Resources {
    private String resource;
    private int locationId;

    public Resources() {
    }

    public Resources(String resource, int locationId) {
        this.resource = resource;
        this.locationId = locationId;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resources resources = (Resources) o;
        return locationId == resources.locationId && Objects.equals(resource, resources.resource);
    }

    @Override
    public int hashCode() {
        return Objects.hash(resource, locationId);
    }
}
