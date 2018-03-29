package com.example.junhosung.aquagroupwalkingapp.model.get_me_data_from_server;

import android.util.Log;

import com.example.junhosung.aquagroupwalkingapp.SimpleCallback;
import com.example.junhosung.aquagroupwalkingapp.model.Group;
import com.example.junhosung.aquagroupwalkingapp.model.Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by karti on 2018-03-28.
 */

public class GetListOfGroupFromListOfGroupWithIDOnly {
    private final String TAG = "GetListOfGroupFromListO";
    private List<Group> listOfGroupsWithIdOnlyReadOnly  = new ArrayList<>();
    private List<Group> listOfGroupsWithIdOnlyChangeable = new ArrayList<>();
    private List<Group> listOfGroupsAllInformation = new ArrayList<>();
    private boolean sortAndIgnoreDuplicates;
    Model model = Model.getInstance();
    SimpleCallback simpleCallback;

    public GetListOfGroupFromListOfGroupWithIDOnly(List<Group> listOfGroupsWithIdOnly , SimpleCallback<List<Group>> callback, boolean sortAndIgnoreDuplicates) {

        Log.w(TAG,"GetListOfGroupFromListOfID called");
        if (listOfGroupsWithIdOnly == null || callback == null) {
            Log.w(TAG, "Got a null object so not doing anything!!");
        } else {
            this.sortAndIgnoreDuplicates = sortAndIgnoreDuplicates;
            listOfGroupsWithIdOnlyReadOnly = new ArrayList<>();
            listOfGroupsWithIdOnlyChangeable = new ArrayList<>();
            listOfGroupsAllInformation = new ArrayList<>();

            this.listOfGroupsWithIdOnlyReadOnly = listOfGroupsWithIdOnly;
            this.simpleCallback = callback;
            startGettingInformationFromServer();
        }
    }

    private void startGettingInformationFromServer() {
        Log.w(TAG,"call this method called");
        removeDuplicates();
        copyReadOnlyList();
        getGroupsInformation();
    }
    private void copyReadOnlyList(){
        Log.w(TAG,"copyReadOnlyList called");
        for(Group group : listOfGroupsWithIdOnlyReadOnly) {
            Group newGroup = new Group();
            newGroup.setId(group.getId());
            listOfGroupsWithIdOnlyChangeable.add(newGroup);
        }
    }


    private void getGroupsInformation(){
        Log.w(TAG,"getGroupsInformation called");
        if(!listOfGroupsWithIdOnlyChangeable.isEmpty()){

            model.getGroupDetailsById(listOfGroupsWithIdOnlyChangeable.get(0).getId(),this::responseWithGroupDetails);
            listOfGroupsWithIdOnlyChangeable.remove(0);
        }
    }

    private void responseWithGroupDetails(Group group) {
        Log.w(TAG,"responseWithGroupDetails called");
        if(group!=null){
            if(listOfGroupsAllInformation.isEmpty()){
                listOfGroupsAllInformation.add(group);
            } else {
                if(listOfGroupsAllInformation.get(listOfGroupsAllInformation.size()-1).getId()!=group.getId()) {
                    listOfGroupsAllInformation.add(group);
                }
            }
        }
        if(! listOfGroupsWithIdOnlyChangeable.isEmpty()) {
            getGroupsInformation();
        } else {
            fillInReturnList();
        }
    }

    private void fillInReturnList() {
        if( !sortAndIgnoreDuplicates) {
            List<Group> matchedList = new ArrayList<>();
            for(Group groupWithIDOnly:listOfGroupsWithIdOnlyReadOnly) {
                Group newGroup = new Group();
                for(Group groupWithAllInformation:listOfGroupsAllInformation){
                    if(groupWithIDOnly.getId()== groupWithAllInformation.getId()){
                        newGroup = groupWithAllInformation;
                    }
                }
                matchedList.add(newGroup);
            }
            simpleCallback.callback(matchedList);

        } else {
            simpleCallback.callback(listOfGroupsAllInformation);
        }
    }

    private int compareTo1(Group a,Group b) {
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
        if ( !listOfGroupsWithIdOnlyChangeable.isEmpty()) {
            Collections.sort(listOfGroupsWithIdOnlyChangeable, (a, b) -> compareTo1(a, b));
            Log.i(TAG,"Sorted List");
        }
    }

    private void removeDuplicates() {
        if(!listOfGroupsWithIdOnlyChangeable.isEmpty()) {
            Log.i(TAG,"removeDuplicatesFunctionCalled");
            sortInComingList();
            for(Group group: listOfGroupsWithIdOnlyChangeable){
                Log.i(TAG,"group ID in sorted order" + group.getId());
            }
            int checkUpperIndexForDuplicate = 1;
            int checkLowerIndexForDuplicate = 0;
            boolean duplicateFound = false;
            if(listOfGroupsWithIdOnlyChangeable.size()>=2){
                while(checkUpperIndexForDuplicate<listOfGroupsWithIdOnlyChangeable.size()) {
                    if(listOfGroupsWithIdOnlyChangeable.get(checkLowerIndexForDuplicate).getId()==listOfGroupsWithIdOnlyChangeable.get(checkUpperIndexForDuplicate).getId()){
                        Log.i(TAG,"duplicate Found: " + listOfGroupsWithIdOnlyChangeable.get(checkLowerIndexForDuplicate).getId()
                                + " and " +listOfGroupsWithIdOnlyChangeable.get(checkUpperIndexForDuplicate).getId());
                        listOfGroupsWithIdOnlyChangeable.remove(checkUpperIndexForDuplicate);
                        duplicateFound = true;
                    } else {
                        Log.i(TAG,"duplicate Not  Found: " + listOfGroupsWithIdOnlyChangeable.get(checkLowerIndexForDuplicate).getId()
                                + " and " +listOfGroupsWithIdOnlyChangeable.get(checkUpperIndexForDuplicate).getId());
                        duplicateFound = false;
                    }
                    if(!duplicateFound){
                        checkUpperIndexForDuplicate++;
                        checkLowerIndexForDuplicate++;
                        Log.i(TAG,"updating index upper: " + checkUpperIndexForDuplicate +" and lower " +checkLowerIndexForDuplicate);

                    }
                }
            }
            for(Group group: listOfGroupsWithIdOnlyChangeable){
                Log.i(TAG,"Group ID in sorted order after removing duplicates: " + group.getId());
            }

        }
    }

}
