package com.example.junhosung.aquagroupwalkingapp.model;

/**
 * Created by karti on 2018-03-22.
 */


public class Message {


    public Message() {
    }

    public Message(String text, boolean emergency) {
        this.text = text;
        this.emergency = emergency;
    }

    private Long id;
    private Long timestamp;
    private String text;
    private User fromUser;
    private Group toGroup;
    private boolean emergency;
    private String href;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getFromUser() {
        return fromUser;
    }

    public void setFromUser(User fromUser) {
        this.fromUser = fromUser;
    }

    public Group getToGroup() {
        return toGroup;
    }

    public void setToGroup(Group toGroup) {
        this.toGroup = toGroup;
    }

    public boolean isEmergency() {
        return emergency;
    }

    public void setEmergency(boolean emergency) {
        this.emergency = emergency;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }
}
