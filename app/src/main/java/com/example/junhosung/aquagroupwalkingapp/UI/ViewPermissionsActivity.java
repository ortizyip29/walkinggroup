package com.example.junhosung.aquagroupwalkingapp.UI;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.junhosung.aquagroupwalkingapp.R;
import com.example.junhosung.aquagroupwalkingapp.model.Model;
import com.example.junhosung.aquagroupwalkingapp.model.PermissionRequest;
import com.example.junhosung.aquagroupwalkingapp.model.User;

import java.util.ArrayList;
import java.util.List;

import static com.example.junhosung.aquagroupwalkingapp.proxy.WGServerProxy.PermissionStatus.PENDING;

public class ViewPermissionsActivity extends AppCompatActivity {

    private Model model = Model.getInstance();
    private List<PermissionRequest> pendingRequests;
    private User currentUser = model.getCurrentUser();
    private String[] requestTxt;
    private List<Clicked> isItemClicked = new ArrayList<>();
    public class Clicked{
        public boolean clicked = false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(model.themeToApply(model.getCurrentUser()));
        setContentView(R.layout.activity_view_permissions);

        model.getPermissionByUserIdPending(currentUser.getId(),PENDING,this::responseForGetPermissionByUserIdPending);
    }

    private void responseForGetPermissionByUserIdPending(List<PermissionRequest> pending) {
        pendingRequests = pending;

        for (int i = 0; i < pendingRequests.size(); i++) {

            if (pendingRequests.get(i).getMessage() != null && !pendingRequests.get(i).getMessage().equals(""))

            requestTxt[i] = pendingRequests.get(i).getMessage();
        }

        populateListView();

    }

    private void populateListView() {

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.see_monitoring,
                requestTxt);

        ListView list = (ListView) findViewById(R.id.listViewPermission);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int itemNumber, long l) {
                if(!isItemClicked.get(itemNumber).clicked){
                    view.setBackgroundColor(Color.GRAY);//mark for deletion
                    ViewPermissionsActivity.Clicked click = new ViewPermissionsActivity.Clicked();
                    click.clicked = true;
                    isItemClicked.set(itemNumber,click); //mark for deletion
                } else{
                    view.setBackgroundColor(Color.WHITE); //change back to normal view
                    ViewPermissionsActivity.Clicked click = new ViewPermissionsActivity.Clicked();
                    click.clicked = false;
                    isItemClicked.set(itemNumber,click);//unmark for deletion
                }
            }

        });

    }

}
