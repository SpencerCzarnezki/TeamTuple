package learn.events.models;

import java.util.Objects;

public class UserRole {

    private int appUserId;
    private int appRoleId;

    public UserRole() {
    }

    public UserRole(int appUserId, int appRoleId) {
        this.appUserId = appUserId;
        this.appRoleId = appRoleId;
    }

    public int getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(int appUserId) {
        this.appUserId = appUserId;
    }

    public int getAppRoleId() {
        return appRoleId;
    }

    public void setAppRoleId(int appRoleId) {
        this.appRoleId = appRoleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRole userRole = (UserRole) o;
        return appUserId == userRole.appUserId && appRoleId == userRole.appRoleId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(appUserId, appRoleId);
    }
}
