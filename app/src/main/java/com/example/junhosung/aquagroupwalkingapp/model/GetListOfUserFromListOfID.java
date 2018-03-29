package com.example.junhosung.aquagroupwalkingapp.model;

import android.util.Log;

import com.example.junhosung.aquagroupwalkingapp.SimpleCallback;
import com.example.junhosung.aquagroupwalkingapp.model.Model;
import com.example.junhosung.aquagroupwalkingapp.model.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by karti on 2018-03-26.
 */

public class GetListOfUserFromListOfID {
    private final String TAG = "GetListOfUserFromListO";
    private List<User> listOfUsersWithIdOnlyReadOnly  = new ArrayList<>();
    private List<User> listOfUsersWithIdOnlyChangeable = new ArrayList<>();
    private List<User> listOfUsersAllInformation = new ArrayList<>();
    private boolean sortAndIgnoreDuplicates;
    private boolean compensateForMultipleResponse;
    Model model = Model.getInstance();
    SimpleCallback simpleCallback;
    public GetListOfUserFromListOfID(List<User> listOfUsersWithIdOnly , SimpleCallback<List<User>> callback, boolean sortAndIgnoreDuplicates, boolean compensateForMultipleResponse) {

        Log.w(TAG,"GetListOfUserFromListOfID called");
        if (listOfUsersWithIdOnly == null || callback == null) {
            Log.w(TAG, "Got a null object so not doing anything!!");
        } else {
            this.sortAndIgnoreDuplicates = sortAndIgnoreDuplicates;
            this.compensateForMultipleResponse = compensateForMultipleResponse;
            listOfUsersWithIdOnlyReadOnly = new ArrayList<>();
            listOfUsersWithIdOnlyChangeable = new ArrayList<>();
            listOfUsersAllInformation = new ArrayList<>();

            this.listOfUsersWithIdOnlyReadOnly = listOfUsersWithIdOnly;
            this.simpleCallback = callback;
            startGettingInformationFromServer();
        }
    }

    private void startGettingInformationFromServer() {
        Log.w(TAG,"call this method called");
        if(this.sortAndIgnoreDuplicates) {
            removeDuplicates();
        }
        copyReadOnlyList();
        getUsersInformation();
    }
    private void copyReadOnlyList(){
        Log.w(TAG,"copyReadOnlyList called");
        for(User user : listOfUsersWithIdOnlyReadOnly) {
            User newUser = new User();
            newUser.setId(user.getId());
            listOfUsersWithIdOnlyChangeable.add(newUser);
        }
    }


    private void getUsersInformation(){
        Log.w(TAG,"getUsersInformation called");
        if(!listOfUsersWithIdOnlyChangeable.isEmpty()){
            model.getUserById(listOfUsersWithIdOnlyChangeable.get(0).getId(),this::responseWithUserDetails);
            listOfUsersWithIdOnlyChangeable.remove(0);
        }
    }

    private void responseWithUserDetails(User user) {
        Log.w(TAG,"responseWithUserDetails called");
        if(user!=null){

            if(compensateForMultipleResponse) {
                if(listOfUsersAllInformation.isEmpty()){
                    listOfUsersAllInformation.add(user);
                } else {
                    if(listOfUsersAllInformation.get(listOfUsersAllInformation.size()-1).getId()!=user.getId()) {
                        listOfUsersAllInformation.add(user);
                    }
                }
            } else {
                listOfUsersAllInformation.add(user);
            }
        }
        if(! listOfUsersWithIdOnlyChangeable.isEmpty()) {
            getUsersInformation();
        } else {
            fillInReturnList();
        }
    }

    private void fillInReturnList() {
        List<User> matchedList = new ArrayList<>();
        int i = 0;

        for(User userWithIDOnly:listOfUsersWithIdOnlyReadOnly) {
            User newUser = new User();
            for(User userWithAllInformation:listOfUsersAllInformation){
                if(userWithIDOnly.getId()==userWithAllInformation.getId()){
                    newUser = userWithAllInformation;
                }
            }

            Log.w("TAG",i++ +"  /  " + newUser.getId() + newUser.getEmail());
            matchedList.add(newUser);

        }
        simpleCallback.callback(matchedList);

    }

    private int compareTo1(User a,User b) {
        if(a.getId() > b.getId()){
            return 1;
        } else if(a.getId() == b.getId()) {
            return 0;
        }
        else {
            return -1;
        }
    }

    private void sortInComingList() {
        if ( sortAndIgnoreDuplicates && !listOfUsersWithIdOnlyChangeable.isEmpty()) {
            Collections.sort(listOfUsersWithIdOnlyChangeable, (a, b) -> compareTo1(a, b));
            Log.i(TAG,"Sorted List");
        }
    }

    private void removeDuplicates() {
        if(!listOfUsersWithIdOnlyChangeable.isEmpty()) {
            Log.i(TAG,"removeDuplicatesFunctionCalled");
            sortInComingList();
            for(User user: listOfUsersWithIdOnlyChangeable){
                Log.i(TAG,"group ID in sorted order" + user.getId());
            }
            int checkUpperIndexForDuplicate = 1;
            int checkLowerIndexForDuplicate = 0;
            boolean duplicateFound = false;
            if(listOfUsersWithIdOnlyChangeable.size()>=2){
                while(checkUpperIndexForDuplicate<listOfUsersWithIdOnlyChangeable.size()) {
                    if(listOfUsersWithIdOnlyChangeable.get(checkLowerIndexForDuplicate).getId()==listOfUsersWithIdOnlyChangeable.get(checkUpperIndexForDuplicate).getId()){
                        Log.i(TAG,"duplicate Found: " + listOfUsersWithIdOnlyChangeable.get(checkLowerIndexForDuplicate).getId()
                                + " and " +listOfUsersWithIdOnlyChangeable.get(checkUpperIndexForDuplicate).getId());
                        listOfUsersWithIdOnlyChangeable.remove(checkUpperIndexForDuplicate);
                        duplicateFound = true;
                    } else {
                        Log.i(TAG,"duplicate Not  Found: " + listOfUsersWithIdOnlyChangeable.get(checkLowerIndexForDuplicate).getId()
                                + " and " +listOfUsersWithIdOnlyChangeable.get(checkUpperIndexForDuplicate).getId());
                        duplicateFound = false;
                    }
                    if(!duplicateFound){
                        checkUpperIndexForDuplicate++;
                        checkLowerIndexForDuplicate++;
                        Log.i(TAG,"updating index upper: " + checkUpperIndexForDuplicate +" and lower " +checkLowerIndexForDuplicate);

                    }
                }
            }
            for(User user: listOfUsersWithIdOnlyChangeable){
                Log.i(TAG,"User ID in sorted order after removing duplicates: " + user.getId());
            }

        }
    }

}