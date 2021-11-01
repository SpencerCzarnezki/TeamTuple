package learn.models;

import java.util.Objects;

public class Location {
    private int id;
    private String title;
    private String city;
    private String address;
    private int zipcode;
    private String state;

    public Location() {}

    public Location(int id, String title, String city, String address, int zipcode, String state) {
        this.id = id;
        this.title = title;
        this.city = city;
        this.address = address;
        this.zipcode = zipcode;
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getZipcode() {
        return zipcode;
    }

    public void setZipcode(int zipcode) {
        this.zipcode = zipcode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return id == location.id && zipcode == location.zipcode && Objects.equals(title, location.title) && Objects.equals(city, location.city) && Objects.equals(address, location.address) && Objects.equals(state, location.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, city, address, zipcode, state);
    }
}
