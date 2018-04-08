package com.example.junhosung.aquagroupwalkingapp.UI;

import android.graphics.Color;
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


public class MyStuffActivity extends AppCompatActivity {
    private Model model = Model.getInstance();
    String[] listTheme = new String[model.getCurrentUser().getCurrThemeCount()];
    String[] listColor = new String[model.getCurrentUser().getColorCount()];
    String[] listTitles= new String[model.getCurrentUser().getTitleCount()];

    String[] l1 = new String[model.getCurrentUser().getCurrThemeCount()];
    String[] l2 = new String[model.getCurrentUser().getColorCount()];
    String[] l3= new String[model.getCurrentUser().getTitleCount()];
    int[] listThemeID;
    int[] listColorID;
    String tt;
    int buyPos;
    int buyC;


    private void voidCallback(Void aVoid) {
    }

    private List<Clicked> isItemClicked = new ArrayList<>();
    private List<Clicked> isItemClicked2 = new ArrayList<>();
    private List<Clicked> isItemClicked3 = new ArrayList<>();

    public class Clicked {
        public boolean clicked = false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(model.themeToApply(model.getCurrentUser()));
        setContentView(R.layout.activity_my_stuff);
        setTitle("My Stuff");
        User currentUser = model.getCurrentUser();
        //currentUser.addTitle("beginner");
        listTheme = model.purchasedThemes(model.getCurrentUser());
        int[] x = model.getCurrentUser().getThemes();
        int[] z = model.getCurrentUser().getButtonColors();

        int c1 = 0;
        for (int i = 0; i < model.getCurrentUser().getCurrThemeCount(); i++) {

                l1[c1] = model.convertThemes(x[i]);
                c1++;
        }
        listColor = model.purchasedColors(model.getCurrentUser());
        int c2 = 0;
        for (int i = 0; i < model.getCurrentUser().getColorCount(); i++) {

            l2[c2] = model.convertColors(z[i]);
            c2++;
        }
        int c3 = 0;
        listTitles = model.purchasedTitles(model.getCurrentUser());
        for (int i = 0; i < model.getCurrentUser().getTitleCount(); i++) {
                l3[c3] = listTitles[i];
                c3++;

        }

        l1[0] = "default";
        l2[0] = "default";
        l3[0] = "default";
        listColorID = model.getCurrentUser().getButtonColors();
        listThemeID = model.getCurrentUser().getThemes();
        // Log.i("I got to here", "uhh: " + listTheme[0]);
        populateListView(l1);
        populateListView2(l2);
        populateListView3(l3);
        setUpSetBtn();
        updateLists(currentUser);

    }

    private void setUpSetBtn() {

        Button btn = (Button) findViewById(R.id.btnSet);
        btn.setBackgroundResource(model.getButtonColor(model.getCurrentUser()));
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String temp = model.convertThemes(buyPos);
                if (buyC == 1) {
                    if (buyPos == 0) {
                        model.getCurrentUser().setTheme(listThemeID[buyPos]);
                        Toast.makeText(MyStuffActivity.this,"The Theme has been changed to " + tt, Toast.LENGTH_LONG).show();
                        Log.i("New Theme ", " " + String.valueOf(model.getCurrentUser().getCurrThemeID()));
                    } else if (buyPos == 1) {
                        model.getCurrentUser().setTheme(listThemeID[buyPos]);
                        Toast.makeText(MyStuffActivity.this,"The Theme has been changed to " +tt , Toast.LENGTH_LONG).show();
                        Log.i("New Theme ", " " + String.valueOf(model.getCurrentUser().getCurrThemeID()));
                    } else if (buyPos == 2) {
                        model.getCurrentUser().setTheme(listThemeID[buyPos]);
                        Toast.makeText(MyStuffActivity.this,"The Theme has been changed to " + tt, Toast.LENGTH_LONG).show();
                        Log.i("New Theme ", " " + String.valueOf(model.getCurrentUser().getCurrThemeID()));

                    } else if (buyPos == 3) {
                        model.getCurrentUser().setTheme(listThemeID[buyPos]);
                        Toast.makeText(MyStuffActivity.this,"The Theme has been changed to " + tt, Toast.LENGTH_LONG).show();
                        Log.i("New Theme ", " " + String.valueOf(model.getCurrentUser().getCurrThemeID()));
                    }
                } else if (buyC == 2) {
                    if (buyPos == 0) {
                        model.getCurrentUser().setColor(listColorID[buyPos]);
                    } else if (buyPos == 1) {
                        model.getCurrentUser().setColor(listColorID[buyPos]);
                    } else if (buyPos == 2) {
                        model.getCurrentUser().setColor(listColorID[buyPos]);
                    } else if (buyPos == 3) {
                        model.getCurrentUser().setColor(listColorID[buyPos]);
                    } else if (buyPos == 4) {
                        model.getCurrentUser().setColor(listColorID[buyPos]);
                    } else if (buyPos == 5) {
                        model.getCurrentUser().setColor(listColorID[buyPos]);
                    }
                } else if (buyC == 3) {
                    if (buyPos == 0) {
                       model.getCurrentUser().setTitle(tt);
                    } else if (buyPos == 1) {
                        model.getCurrentUser().setTitle(tt);
                    } else if (buyPos == 2) {
                        model.getCurrentUser().setTitle(tt);
                    } else {
                        return;
                    }
                }
            }

        });
        updateUI();
    }

    private void updateUI() {
        populateListView(l1);
        populateListView2(l2);
        populateListView3(l3);
        model.updateUser(model.getCurrentUser(), this::getUpdatedUserBack);

    }

    private void getUpdatedUserBack(User user){


    }

    private void populateListView3(String[] titleList) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.titlelist,
                titleList);

        ListView list = (ListView) findViewById(R.id.listViewTitles);
        list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                TextView textView = (TextView) view;
                //    Toast.makeText(ShopActivity.this, "You have selected theme " + textView.getText().toString(), Toast.LENGTH_LONG).show();
                if (!isItemClicked3.get(position).clicked) {
                    Log.i("innnnn", "got 1");
                    list.setItemChecked(position, true);
                    //      view.setBackgroundColor(Color.GRAY);//mark for deletion
                    Clicked click = new Clicked();
                    click.clicked = true;
                    if (textView.getText().toString().isEmpty()) {

                    } else {
                        buyPos = position;
                        buyC = 3;
                    }
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
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                TextView textView = (TextView) view;
                //           Toast.makeText(ShopActivity.this, "You have selected theme " + textView.getText().toString(), Toast.LENGTH_LONG).show();
                if (!isItemClicked2.get(position).clicked) {
                    Log.i("innnnn", "got 1");
                    list.setItemChecked(position, true);
                    //    view.setBackgroundColor(Color.GRAY);//mark for deletion
                    Clicked click = new Clicked();
                    click.clicked = true;
                    if (textView.getText().toString().isEmpty()) {

                    } else {
                        buyPos = position;
                        buyC = 2;
                    }
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
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                TextView textView = (TextView) view;
                //         Toast.makeText(ShopActivity.this, "You have selected theme " + textView.getText().toString(), Toast.LENGTH_LONG).show();

                if (!isItemClicked.get(position).clicked) {
                    Log.i("innnnn", "got 1");
                  //
                    //    view.setBackgroundColor(Color.GRAY);//mark for deletion
                    Clicked click = new Clicked();
                    click.clicked = true;

                        Log.i("EH"," "+String.valueOf(position));
                        buyPos = position;
                        buyC = 1;
                        tt = textView.getText().toString();


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

