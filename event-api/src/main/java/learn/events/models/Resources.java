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

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
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
        return resourceId == resources.resourceId && locationId == resources.locationId && Objects.equals(resource, resources.resource);
    }

    @Override
    public int hashCode() {
        return Objects.hash(resourceId, resource, locationId);
    }
}
