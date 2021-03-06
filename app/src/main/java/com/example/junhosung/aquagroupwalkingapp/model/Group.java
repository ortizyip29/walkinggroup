package com.example.junhosung.aquagroupwalkingapp.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Junho Sung on 3/9/2018.
 */

public class Group {
    private long id;
    private String groupDescription;
    private List<Double> routeLatArray =  new ArrayList<>();
    private List<Double> routeLngArray =  new ArrayList<>();
    private User leader;
    private List<User> memberUsers = new ArrayList<>();
  //  private List<User> leadsGroups = new ArrayList<>();
    private String href;
    private List<Message> messages = new ArrayList<>();
    private String customJson;

    public String getStartingPoint() {
        return startingPoint;
    }

    public void setStartingPoint(String startingPoint) {
        this.startingPoint = startingPoint;
    }

    private String startingPoint;

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    private String destination;

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setGroupDescription(String groupDescription) {
        this.groupDescription = groupDescription;
    }

    public void setRouteLatArray(List<Double> routeLatArray) {
        this.routeLatArray = routeLatArray;
    }

    public void setRouteLngArray(List<Double> routeLngArray) {
        this.routeLngArray = routeLngArray;
    }

    public void setLeader(User leader) {
        this.leader = leader;
    }

    public void setMemberUsers(List<User> memberUsers) {
        this.memberUsers = memberUsers;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public long getId() {
        return id;
    }

    public String getGroupDescription() {
        return groupDescription;
    }
    public List<Double> getRouteLatArray() {
        return routeLatArray;
    }

    public List<Double> getRouteLngArray() {
        return routeLngArray;
    }

    public User getLeader() {
        return leader;
    }

    public List<User> getMemberUsers() {
        return memberUsers;
    }

    public String getHref() {
        return href;
    }

    public String getCustomJson() {
        return customJson;
    }

    public void setCustomJson(String customJson) {
        this.customJson = customJson;
    }

    @Override
    public String toString() {
        return "Id: " + id +
                "Group: " + groupDescription;
    }
    @Override
    public boolean equals(Object obj) {
        //return super.equals(obj);
        if(obj.getClass() == this.getClass()) {
            Group group = (Group) obj;
            if(group.getId()== this.getId()) {
                return true;
            }
        }
        return false;
    }
}