package learn.events.models;

import java.util.Objects;

public class Resources {
    private int resourceId;
    private String resource;
    private int locationId;

    public Resources() {
    }

    public Resources(int resourceId, String resource, int locationId) {
        this.resourceId = resourceId;
        this.resource = resource;
        this.locationId = locationId;
    }

    public int getResourceId() {
        return resourceId;
    }

    public Resources setResourceId(int resourceId) {
        this.resourceId = resourceId;
        return this;
    }

    public String getResource() {
        return resource;
    }

    public Resources setResource(String resource) {
        this.resource = resource;
        return this;
    }

    public int getLocationId() {
        return locationId;
    }

    public Resources setLocationId(int locationId) {
        this.locationId = locationId;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resources resources = (Resources) o;
        return resourceId == resources.resourceId && locationId == resources.locationId && Objects.equals(resource, resources.resource);
    }

    @Override
    public int hashCode() {
        return Objects.hash(resourceId, resource, locationId);
    }
}
