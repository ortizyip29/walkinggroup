package com.example.junhosung.aquagroupwalkingapp.model;

/**
 * Created by Junho Sung on 4/3/2018.
 */

import com.example.junhosung.aquagroupwalkingapp.proxy.WGServerProxy;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)

public class PermissionRequest{
    protected Long id;
    protected String href;
    private String action;
    private WGServerProxy.PermissionStatus status;
    private User userA;
    private User userB;
    private Group groupG;
    private User requestingUser;
    private Set<Authorizor> authorizors;
    private String message;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public WGServerProxy.PermissionStatus getStatus() {
        return status;
    }

    public void setStatus(WGServerProxy.PermissionStatus status) {
        this.status = status;
    }

    public User getUserA() {
        return userA;
    }

    public void setUserA(User userA) {
        this.userA = userA;
    }

    public User getUserB() {
        return userB;
    }

    public void setUserB(User userB) {
        this.userB = userB;
    }

    public Group getGroupG() {
        return groupG;
    }

    public void setGroupG(Group groupG) {
        this.groupG = groupG;
    }

    public User getRequestingUser() {
        return requestingUser;
    }

    public void setRequestingUser(User requestingUser) {
        this.requestingUser = requestingUser;
    }

    public Set<Authorizor> getAuthorizors() {
        return authorizors;
    }

    public void setAuthorizors(Set<Authorizor> authorizors) {
        this.authorizors = authorizors;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class Authorizor {
        private Long id;
        private Set<User> users;
        private WGServerProxy.PermissionStatus status;
        private User whoApprovedOrDenied;

        public void setId(Long id) {
            this.id = id;
        }

        public Long getId() {
            return id;
        }

        public void setUsers(Set<User> users) {
            this.users = users;
        }

        public Set<User> getUsers() {
            return users;
        }

        public void setStatus(WGServerProxy.PermissionStatus status) {
            this.status = status;
        }

        public WGServerProxy.PermissionStatus getStatus() {
            return status;
        }

        public void setWhoApprovedOrDenied(User user) {
            this.whoApprovedOrDenied = user;
        }

        public User getWhoApprovedOrDenied() {
            return whoApprovedOrDenied;
        }


    }


}