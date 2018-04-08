package com.example.junhosung.aquagroupwalkingapp.UI;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.junhosung.aquagroupwalkingapp.R;
import com.example.junhosung.aquagroupwalkingapp.model.Model;
import com.example.junhosung.aquagroupwalkingapp.model.User;

import java.util.ArrayList;
import java.util.List;

public class ShopActivity extends AppCompatActivity {
    private Model model = Model.getInstance();
    String[] listTheme;
    String[] listColor;
    String[] listTitles;
    int buyPos;
    int buyC;
    TextView temp;
    TextView pts;
    private void voidCallback(Void aVoid) {}

    private List<Clicked> isItemClicked = new ArrayList<>();
    private List<Clicked> isItemClicked2 = new ArrayList<>();
    private List<Clicked> isItemClicked3 = new ArrayList<>();

    public class Clicked {
        public boolean clicked = false;
    }

    List<String> listViewThemes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(model.themeToApply(model.getCurrentUser()));
        setContentView(R.layout.activity_shop);
        setTitle("Shop");
       // List<Boolean> isItemClicked = new ArrayList<>();
        listTheme = model.buyableThemes(model.getCurrentUser(), this::voidCallback);
        listColor = model.buyableColors(model.getCurrentUser(), this::voidCallback);
        listTitles = model.buyableTitles(model.getCurrentUser(), this::voidCallback);
        User currentUser = model.getCurrentUser();
        currentUser.setCurrentPoints(100);
        pts = (TextView) findViewById(R.id.points);
        pts.setText(String.valueOf(currentUser.getCurrentPoints()));
        temp = (TextView) findViewById(R.id.txtSelect);
        // Log.i("I got to here", "uhh: " + listTheme[0]);
        populateListView(listTheme);
        populateListView2(listColor);
        populateListView3(listTitles);
        updateLists(currentUser);
        setUpbtnBuy();
    }

    private void buyTheme(User user, int theme){
        int temp = user.getCurrentPoints();
        if (temp > 15){
            temp = temp -15;
            user.setCurrentPoints(temp);
            user.addTheme(theme);
            Toast.makeText(this, "You have successfully purchased the Theme!", Toast.LENGTH_LONG).show();
            updateUI();
        }else{
            Toast.makeText(this, "You do not have enough points",Toast.LENGTH_LONG).show();
        }
    }

    private void buyTitle(User user, String title){
        int temp = user.getCurrentPoints();
        if (temp > 10){
            temp = temp -10;
            user.setCurrentPoints(temp);
            user.addTitle(title);
            Toast.makeText(this, "You have successfully purchased the Title!", Toast.LENGTH_LONG).show();
            updateUI();
        }else{
            Toast.makeText(this, "You do not have enough points",Toast.LENGTH_LONG).show();

        }
    }

    private void buyColor(User user, int color) {
        int temp = user.getCurrentPoints();
        if (temp > 5) {
            temp = temp - 5;
            user.setCurrentPoints(temp);
            user.addColor(color);
            Toast.makeText(this, "You have successfully purchased the Color!", Toast.LENGTH_LONG).show();
            updateUI();
        } else {
            Toast.makeText(this, "You do not have enough points", Toast.LENGTH_LONG).show();
        }
    }
    private void updateUI(){
        Log.i("update", "happened");
        listTheme = model.buyableThemes(model.getCurrentUser(), this::voidCallback);
        listColor = model.buyableColors(model.getCurrentUser(), this::voidCallback);
        listTitles = model.buyableTitles(model.getCurrentUser(), this::voidCallback);
        populateListView(listTheme);
        Log.i("Update", "happened2");
        populateListView2(listColor);
        populateListView3(listTitles);
        //updateLists(model.getCurrentUser());
        Log.i("Update", "happened3");
        buyPos = 0;
        buyC = 0;
        pts.setText(String.valueOf(model.getCurrentUser().getCurrentPoints()));
        model.updateUser(model.getCurrentUser(), this::getUpdatedUserBack);

    }
    private void getUpdatedUserBack(User user){

    }


    private void setUpbtnBuy() {
        Button btn = (Button) findViewById(R.id.btnBuy);
        btn.setBackgroundResource(model.getButtonColor(model.getCurrentUser()));
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(buyC == 1){
                    if(buyPos == 0){
                        buyTheme(model.getCurrentUser(), 1);
                    }else if(buyPos == 1){
                        buyTheme(model.getCurrentUser(), 2);
                    }else if(buyPos == 2) {
                        buyTheme(model.getCurrentUser(), 3);
                    }else if(buyPos == 3){
                        buyTheme(model.getCurrentUser(), 4);
                    }
                }else if(buyC == 2){
                    if(buyPos == 0){
                        buyColor(model.getCurrentUser(), 1);
                    }else if(buyPos == 1){
                        buyColor(model.getCurrentUser(), 2);
                    }else if (buyPos == 2){
                        buyColor(model.getCurrentUser(), 3);
                    }else if (buyPos == 3){
                        buyColor(model.getCurrentUser(), 4);
                    }else if (buyPos == 4){
                        buyColor(model.getCurrentUser(), 5);
                    }else if (buyPos == 5){
                        buyColor(model.getCurrentUser(), 6);
                    }
                }else if(buyC == 3){
                    if(buyPos ==0){
                        String t = "Walks too much, someone give me a ride";
                        buyTitle(model.getCurrentUser(), t);
                    }else if(buyPos==1){
                        String t = "King";
                        buyTitle(model.getCurrentUser(), t);
                    }else if(buyPos==2) {
                        String t = "Iron Man";
                        buyTitle(model.getCurrentUser(), t);
                    }else{
                        return;
                    }
                }
            }

        });
    }



    private void populateListView3(String[] titleList) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.titlelist,
                titleList);

        ListView list = (ListView) findViewById(R.id.listViewTitles);
        list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                TextView textView = (TextView) view;
            //    Toast.makeText(ShopActivity.this, "You have selected theme " + textView.getText().toString(), Toast.LENGTH_LONG).show();
                if(!isItemClicked3.get(position).clicked){
                    Log.i("innnnn", "got 1");
                    list.setItemChecked(position,true);
              //      view.setBackgroundColor(Color.GRAY);//mark for deletion
                    Clicked click = new Clicked();
                    click.clicked = true;
                    isItemClicked3.set(position,click); //mark for deletion
                    buyPos = position;
                    buyC=3;
                    temp.setText(textView.getText().toString());
                }

            }
        });
    }

    private void populateListView2(String[] colorList) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.buttoncolors,
                colorList);

        ListView list = (ListView) findViewById(R.id.listViewColors);
        list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                TextView textView = (TextView) view;
     //           Toast.makeText(ShopActivity.this, "You have selected theme " + textView.getText().toString(), Toast.LENGTH_LONG).show();
                if(!isItemClicked2.get(position).clicked){
                    Log.i("innnnn", "got 1");
                    list.setItemChecked(position,true);
                //    view.setBackgroundColor(Color.GRAY);//mark for deletion
                    Clicked click = new Clicked();
                    click.clicked = true;
                    isItemClicked2.set(position,click); //mark for deletion
                    buyPos = position;
                    buyC = 2;
                    temp.setText(textView.getText().toString());
                }

            }
        });
    }


    private void populateListView(String[] themeList) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.themelist,
                themeList);

        ListView list = (ListView) findViewById(R.id.listViewThemes);
        list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                TextView textView = (TextView) view;
       //         Toast.makeText(ShopActivity.this, "You have selected theme " + textView.getText().toString(), Toast.LENGTH_LONG).show();

                if(!isItemClicked.get(position).clicked){
                    Log.i("innnnn", "got 1");
                    list.setItemChecked(position,true);
                //    view.setBackgroundColor(Color.GRAY);//mark for deletion
                    Clicked click = new Clicked();
                    click.clicked = true;
                    isItemClicked.set(position,click); //mark for deletion
                    buyPos = position;
                    buyC = 1;
                    temp.setText(textView.getText().toString());
                }
            }
        });

    }


    private void updateLists(User user){
        listTheme = model.buyableThemes(model.getCurrentUser(), this::voidCallback);
        listColor = model.buyableColors(model.getCurrentUser(), this::voidCallback);
        listTitles = model.buyableTitles(model.getCurrentUser(), this::voidCallback);
        isItemClicked = new ArrayList<>();
        isItemClicked2 = new ArrayList<>();
        isItemClicked3 = new ArrayList<>();
        for (int i = 0; i < 10;i++) {
            isItemClicked.add(new Clicked());
            isItemClicked2.add(new Clicked());
            isItemClicked3.add(new Clicked());
        }
    }

}


