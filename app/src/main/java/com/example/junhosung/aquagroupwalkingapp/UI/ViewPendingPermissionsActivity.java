package com.example.junhosung.aquagroupwalkingapp.UI;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.junhosung.aquagroupwalkingapp.R;
import com.example.junhosung.aquagroupwalkingapp.model.Model;
import com.example.junhosung.aquagroupwalkingapp.model.PermissionRequest;
import com.example.junhosung.aquagroupwalkingapp.model.User;

import java.util.ArrayList;
import java.util.List;

import static com.example.junhosung.aquagroupwalkingapp.proxy.WGServerProxy.PermissionStatus.APPROVED;
import static com.example.junhosung.aquagroupwalkingapp.proxy.WGServerProxy.PermissionStatus.DENIED;
import static com.example.junhosung.aquagroupwalkingapp.proxy.WGServerProxy.PermissionStatus.PENDING;

public class ViewPendingPermissionsActivity extends AppCompatActivity {

    private Model model = Model.getInstance();
    private List<PermissionRequest> pendingRequests;
    private User currentUser = model.getCurrentUser();
    private String[] requestTxt;
    private List<Clicked> isItemClicked = new ArrayList<>();
    public class Clicked{
        public boolean clicked = false;
    }
    private Button btnApprove;
    private Button btnDeny;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_permissions);

        model.getPermissionByUserIdPending(currentUser.getId(),PENDING,this::responseForGetPermissionByUserIdPending);
        //model.getPermissionByUserId(currentUser.getId(),this::responseForGetPermissionByUserIdPending);
        //model.getPermission(this::responseForGetPermissionByUserIdPending);
    }

    private void responseForGetPermissionByUserIdPending(List<PermissionRequest> pending) {
        pendingRequests = pending;
        isItemClicked = new ArrayList<>();
        requestTxt = new String[pendingRequests.size()];

        for (int i = 0; i < pendingRequests.size(); i++) {

            if (pendingRequests.get(i).getMessage() != null && !pendingRequests.get(i).getMessage().equals("")) {
                requestTxt[i] = pendingRequests.get(i).getMessage() ;
                isItemClicked.add(new ViewPendingPermissionsActivity.Clicked());
                Toast.makeText(ViewPendingPermissionsActivity.this,""+pendingRequests.size(), Toast.LENGTH_LONG).show();
            }

        }

        setupApproveBtn();
        setupDenyBtn();
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
                    ViewPendingPermissionsActivity.Clicked click = new ViewPendingPermissionsActivity.Clicked();
                    click.clicked = true;
                    isItemClicked.set(itemNumber,click); //mark for deletion
                } else{
                    view.setBackgroundColor(Color.WHITE); //change back to normal view
                    ViewPendingPermissionsActivity.Clicked click = new ViewPendingPermissionsActivity.Clicked();
                    click.clicked = false;
                    isItemClicked.set(itemNumber,click);//unmark for deletion
                }
            }

        });

    }

    private void setupApproveBtn() {
     btnApprove = (Button) findViewById(R.id.btnApprove);
     btnApprove.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             int counter = 0;
             for(ViewPendingPermissionsActivity.Clicked thisItem: isItemClicked){
                 if(thisItem.clicked){
                     model.approveDenyPermission(pendingRequests.get(counter).getId(),APPROVED,this::voidCallback);
                 }
                 counter++;
             }
         }
         private void voidCallback(Void aVoid) {
             Toast.makeText(ViewPendingPermissionsActivity.this,"Success! Approved!",Toast.LENGTH_LONG).show();
             model.getPermissionByUserIdPending(currentUser.getId(),PENDING,this::responseForGetPermissionByUserIdPending);
         }

         private void responseForGetPermissionByUserIdPending(List<PermissionRequest> pending) {
             pendingRequests = pending;
             isItemClicked = new ArrayList<>();
             requestTxt = new String[pendingRequests.size()];

             for (int i = 0; i < pendingRequests.size(); i++) {

                 if (pendingRequests.get(i).getMessage() != null && !pendingRequests.get(i).getMessage().equals("")) {
                     requestTxt[i] = pendingRequests.get(i).getMessage();
                     isItemClicked.add(new ViewPendingPermissionsActivity.Clicked());
                     Toast.makeText(ViewPendingPermissionsActivity.this, "" + pendingRequests.size(), Toast.LENGTH_LONG).show();
                 }

             }

             populateListView();

         }

     });

    }

    private void setupDenyBtn() {
        btnDeny = (Button) findViewById(R.id.btnDeny);
        btnDeny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int counter = 0;
                for(ViewPendingPermissionsActivity.Clicked thisItem: isItemClicked){
                    if(thisItem.clicked){
                        model.approveDenyPermission(pendingRequests.get(counter).getId(),DENIED,this::voidCallback);
                    }
                    counter++;
                }
            }
            private void voidCallback(Void aVoid) {
                model.getPermissionByUserIdPending(currentUser.getId(),PENDING,this::responseForGetPermissionByUserIdPending);
            }

            private void responseForGetPermissionByUserIdPending(List<PermissionRequest> pending) {
                pendingRequests = pending;
                isItemClicked = new ArrayList<>();
                requestTxt = new String[pendingRequests.size()];

                for (int i = 0; i < pendingRequests.size(); i++) {

                    if (pendingRequests.get(i).getMessage() != null && !pendingRequests.get(i).getMessage().equals("")) {
                        requestTxt[i] = pendingRequests.get(i).getMessage();
                        isItemClicked.add(new ViewPendingPermissionsActivity.Clicked());
                        Toast.makeText(ViewPendingPermissionsActivity.this, "" + pendingRequests.size(), Toast.LENGTH_LONG).show();
                    }

                }

                populateListView();

            }


        });

    }



}
