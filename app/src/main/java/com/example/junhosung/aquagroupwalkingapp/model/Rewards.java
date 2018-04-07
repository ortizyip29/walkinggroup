package com.example.junhosung.aquagroupwalkingapp.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by David-Yoga on 2018-04-06.
 */

public class Rewards {
    int[] userThemes = new int[10];
    int[] userButtonColors = new int[10]; //W,R,B,G,Y,P,P
    String[] userTitles = new String[10];

    int currTheme = 0;
    String currTitle = "Beginner";
    int currButtonColor = 0;

    int colorCount = 1;
    int themeCount = 1;
    int titleCount = 1;


    public Rewards(){
    }

    public int[] getListThemes(){
            return userThemes;

    }

    public String[] getListTitles(){
            return userTitles;
    }


    public void addTheme(int themeID) {
        userThemes[0] = 0;
        userThemes[themeCount] = themeID;
        themeCount++;
    }

    public void addTitle(String newTitle){
        userTitles[titleCount] = newTitle;
        titleCount++;
    }

    public void setcurrButtonColor(int color) {
        currButtonColor = color;
    }

    public void addColor(int color) {
        userButtonColors[colorCount] = color;
        colorCount++;
    }

}
