package com.example.junhosung.aquagroupwalkingapp.model;

/**
 * Created by Junho Sung on 3/21/2018.
 */


public class Message {

    private Long id;
    private Long timestamp;
    private String text;
    private User fromUser;
    private Group toGroup;
    private boolean emergency;
    private String href;

}
