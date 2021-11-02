package learn.models;

import java.util.Objects;

public class Role {

    private int roleId;
    private String title;

    public Role() {
    }

    public Role(int roleId, String title) {
        this.roleId = roleId;
        this.title = title;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return roleId == role.roleId && Objects.equals(title, role.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleId, title);
    }
}
