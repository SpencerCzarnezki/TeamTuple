package learn.events.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class User implements UserDetails {

    private int userId;
    private String fname;
    private String lname;
    private String userName;
    private String email;

    private boolean disabled;
    private String password;
    private List<String> authorities = new ArrayList<>();


    public User() {
    }

    public User(int userId, String fname, String lname, String userName, String email, String passwordHash, boolean diabled) {
        this.userId = userId;
        this.fname = fname;
        this.lname = lname;
        this.userName = userName;
        this.email = email;

        this.disabled = diabled;
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

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public boolean isAccountNonExpired() {
        return true;
    }


    public boolean isAccountNonLocked() {
        return true;
    }


    public boolean isCredentialsNonExpired() {
        return true;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return !disabled;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities.stream()
                .map(a -> new SimpleGrantedAuthority(a))
                .collect(Collectors.toList());
    }

    public List<String> getAuthorityNames() {
        return new ArrayList<>(authorities);
    }

    public void setAuthorityNames(List<String> authorities) {
        this.authorities = authorities;
    }

    public boolean hasAuthority(String authority) {
        return authorities.stream()
                .anyMatch(a -> a.equals(authority));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userId == user.userId && disabled == user.disabled && Objects.equals(fname, user.fname) && Objects.equals(lname, user.lname) && Objects.equals(userName, user.userName) && Objects.equals(email, user.email) && Objects.equals(password, user.password) && Objects.equals(authorities, user.authorities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, fname, lname, userName, email, disabled, password, authorities);
    }
}
