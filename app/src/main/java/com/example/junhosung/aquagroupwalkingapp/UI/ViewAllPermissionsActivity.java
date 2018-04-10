package com.example.junhosung.aquagroupwalkingapp.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.junhosung.aquagroupwalkingapp.R;
import com.example.junhosung.aquagroupwalkingapp.model.Model;
import com.example.junhosung.aquagroupwalkingapp.model.PermissionRequest;
import com.example.junhosung.aquagroupwalkingapp.model.User;

import java.util.ArrayList;
import java.util.List;

public class ViewAllPermissionsActivity extends AppCompatActivity {

    private Model model = Model.getInstance();
    private List<PermissionRequest> allRequests;
    private User currentUser = model.getCurrentUser();
    private String[] requestTxt;
    private List<ViewAllPermissionsActivity.Clicked> isItemClicked = new ArrayList<>();

    public class Clicked {
        public boolean clicked = false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(model.themeToApply(model.getCurrentUser()));
        setContentView(R.layout.activity_view_all_permission_requests);

        model.getPermissionByUserId(currentUser.getId(), this::responseForGetPermissionByUserIdPending);

    }

    private void responseForGetPermissionByUserIdPending(List<PermissionRequest> requests) {
        allRequests = requests;
        isItemClicked = new ArrayList<>();
        requestTxt = new String[allRequests.size()];

        for (int i = 0; i < allRequests.size(); i++) {

            if (allRequests.get(i).getMessage() != null && !allRequests.get(i).getMessage().equals("")) {
                requestTxt[i] = allRequests.get(i).getMessage() + "   " + allRequests.get(i).getStatus();
                isItemClicked.add(new ViewAllPermissionsActivity.Clicked());
            }

        }

        populateListView();
    }


    private void populateListView() {

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.see_monitoring,
                requestTxt);

        ListView list = (ListView) findViewById(R.id.listAllPermission);
        list.setAdapter(adapter);
    }
}