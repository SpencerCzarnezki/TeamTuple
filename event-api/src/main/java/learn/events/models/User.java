package learn.events.models;

import java.util.Objects;

public class User {

    private int userId;
    private String fname;
    private String lname;
    private String userName;
    private String email;
    private String passwordHash;
    private boolean diabled;


    public User() {
    }

    public User(int userId, String fname, String lname, String userName, String email, String passwordHash, boolean diabled) {
        this.userId = userId;
        this.fname = fname;
        this.lname = lname;
        this.userName = userName;
        this.email = email;
        this.passwordHash = passwordHash;
        this.diabled = diabled;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public boolean isDiabled() {
        return diabled;
    }

    public void setDiabled(boolean diabled) {
        this.diabled = diabled;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userId == user.userId && diabled == user.diabled && Objects.equals(fname, user.fname) && Objects.equals(lname, user.lname) && Objects.equals(userName, user.userName) && Objects.equals(email, user.email) && Objects.equals(passwordHash, user.passwordHash);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, fname, lname, userName, email, passwordHash, diabled);
    }
}
