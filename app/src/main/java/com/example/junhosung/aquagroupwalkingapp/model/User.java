package com.example.junhosung.aquagroupwalkingapp.model;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.valueOf;

/**
 * Simple User class to store the data the server expects and returns.
 * (Incomplete: Needs support for monitoring and groups).
 */
public class User {
    private Rewards rewards = new Rewards();
    private Long id;
    private String name;
    private String email;
    private String password;
    private int birthYear;
    private int birthMonth;
    private String address;
    private String cellPhone;
    private String homePhone;
    private String grade;
    private String teacherName;
    private String emergencyContactInfo;
    private GpsLocation lastGpsLocation;
    private List<Message> unreadMessages;
    private List<Message> readMessages;
    private String href;
    private List<User> monitoredByUsers = new ArrayList<>();
    private List<User> monitorsUsers = new ArrayList<>();
    private List<Group> memberOfGroups = new ArrayList<>();
    private List<Group> leadsGroups = new ArrayList<>();
    private int currentPoints;
    private int totalPointsEarned;
    private String customJson;
    private List<PermissionRequest> pendingPermissionRequests;


    public User(){
    }

    public int getBirthYear() {
        return birthYear;
    }

    public int getBirthMonth() {
        return birthMonth;
    }

    public List<Message> getUnreadMessages() {
        return unreadMessages;
    }

    public void setUnreadMessages(List<Message> unreadMessages) {
        this.unreadMessages = unreadMessages;
    }

    public List<Message> getReadMessages() {
        return readMessages;
    }

    public void setReadMessages(List<Message> readMessages) {
        this.readMessages = readMessages;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    public void setBirthMonth(int birthMonth) {
        this.birthMonth = birthMonth;
    }

    public String getName() {
        return name;

    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<User> getMonitoredByUsers() {
        return monitoredByUsers;
    }

    public void setMonitoredByUsers(List<User> monitoredByUsers) {
        this.monitoredByUsers = monitoredByUsers;
    }

    public List<User> getMonitorsUsers() {
        return monitorsUsers;
    }

    public void setMonitorsUsers(List<User> monitorsUsers) {
        this.monitorsUsers = monitorsUsers;
    }

    public List<Group> getMemberOfGroups() {
        return memberOfGroups;
    }

    public void setMemberOfGroups(List<Group> memberOfGroups) {
        this.memberOfGroups = memberOfGroups;
    }

    public List<Group> getLeadsGroups() {
        return leadsGroups;
    }

    public void setLeadsGroups(List<Group> leadsGroups) {
        this.leadsGroups = leadsGroups;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getEmergencyContactInfo() {
        return emergencyContactInfo;
    }

    public void setEmergencyContactInfo(String emergencyContactInfo) {
        this.emergencyContactInfo = emergencyContactInfo;
    }

    public GpsLocation getLastGpsLocation() {
        return lastGpsLocation;
    }

    public void setLastGpsLocation(GpsLocation lastGpsLocation) {
        this.lastGpsLocation = lastGpsLocation;
    }

    public int getCurrentPoints() {
        return currentPoints;
    }

    public void setCurrentPoints(int currentPoints) {
        this.currentPoints = currentPoints;
    }

    public int getTotalPointsEarned() {
        return totalPointsEarned;
    }

    public void setTotalPointsEarned(int totalPointsEarned) {
        this.totalPointsEarned = totalPointsEarned;
    }

    public String getCustomJson() {
        return customJson;
    }

    public void setCustomJson(String customJson) {
        this.customJson = customJson;
    }

    public List<PermissionRequest> getPendingPermissionRequests() {
        return pendingPermissionRequests;
    }

    public void setPendingPermissionRequests(List<PermissionRequest> pendingPermissionRequests) {
        this.pendingPermissionRequests = pendingPermissionRequests;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", monitoredByUsers=" + monitoredByUsers +
                ", monitorsUsers=" + monitorsUsers +
                ", memberOfGroups=" + memberOfGroups +
                ", leadsGroups=" + leadsGroups +
                ", rewards=" + valueOf(this.rewards.currTheme)+
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        //return super.equals(obj);
        if(obj.getClass() == this.getClass()) {
            User user = (User) obj;
            if(user.getId()== this.getId()) {
                return true;
            }
        }
        return false;
    }


    public String setTitle(String title){
        this.rewards.currTitle = title;
        return this.rewards.currTitle;

    }
    public void setColor(int color){
        this.rewards.currButtonColor = color;
    }

    public int setTheme(int theme){
        this.rewards.currTheme = theme;
        return this.rewards.currTheme;
    }

    public String[] getTitles(){
        return this.rewards.getListTitles();

    }

    public int[] getThemes(){
        return this.rewards.getListThemes();

    }

    public String getCurrTitle(){
        return this.rewards.currTitle;
    }

    public int getCurrThemeID(){
        return this.rewards.currTheme;
    }

    public int getCurrColor(){
        return this.rewards.currButtonColor;
    }
    public void addTheme(int themeID){
        this.rewards.addTheme(themeID);
    }
    public void addTitle(String titleID){
        this.rewards.addTitle(titleID);
    }
    public void addColor(int colorID){
        this.rewards.addColor(colorID);
    }

    public int[] getButtonColors() {
        return this.rewards.userButtonColors;
    }

    public int getCurrThemeCount(){
        return this.rewards.themeCount;
    }

    public int getColorCount(){
        return this.rewards.colorCount;
    }
    public int getTitleCount(){
        return this.rewards.titleCount;
    }
}


