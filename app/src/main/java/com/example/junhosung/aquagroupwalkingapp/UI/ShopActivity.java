package com.example.junhosung.aquagroupwalkingapp.UI;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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

    private void voidCallback(Void aVoid) {

    }

    private List<Clicked> isItemClicked = new ArrayList<>();

    public class Clicked {
        public boolean clicked = false;
    }

    List<String> listViewThemes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        List<Boolean> isItemClicked = new ArrayList<>();
        listTheme = model.buyableThemes(model.getCurrentUser(), this::voidCallback);
        listColor = model.buyableColors(model.getCurrentUser(), this::voidCallback);
        listTitles = model.buyableTitles(model.getCurrentUser(), this::voidCallback);
        // Log.i("I got to here", "uhh: " + listTheme[0]);
        populateListView();
        populateListView2();
        populateListView3();
    }

    private void populateListView3() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.titlelist,
                listTitles);

        ListView list = (ListView) findViewById(R.id.listViewTitles);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int itemNumber, long l) {
                try {
                    if (!isItemClicked.get(itemNumber).clicked) {
                        view.setBackgroundColor(Color.GRAY);//mark for deletion
                        Clicked click = new Clicked();
                        click.clicked = true;
                        isItemClicked.set(itemNumber, click);
                    } else {
                        view.setBackgroundColor(Color.WHITE); //change back to normal view
                        Clicked click = new Clicked();
                        click.clicked = true;
                        isItemClicked.set(itemNumber, click);
                    }
                } catch (IndexOutOfBoundsException a) {
                }

            }
        });
    }

    private void populateListView2() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.buttoncolors,
                listColor);

        ListView list = (ListView) findViewById(R.id.listViewColors);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int itemNumber, long l) {
                try {
                    if (!isItemClicked.get(itemNumber).clicked) {
                        view.setBackgroundColor(Color.GRAY);//mark for deletion
                        Clicked click = new Clicked();
                        click.clicked = true;
                        isItemClicked.set(itemNumber, click);
                    } else {
                        view.setBackgroundColor(Color.WHITE); //change back to normal view
                        Clicked click = new Clicked();
                        click.clicked = true;
                        isItemClicked.set(itemNumber, click);
                    }
                } catch (IndexOutOfBoundsException a) {
                }

            }
        });
    }




    private void populateListView() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.themelist,
                listTheme);

        ListView list = (ListView) findViewById(R.id.listViewThemes);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int itemNumber, long l) {
                try {
                    if (!isItemClicked.get(itemNumber).clicked) {
                        view.setBackgroundColor(Color.GRAY);//mark for deletion
                        Clicked click = new Clicked();
                        click.clicked = true;
                        isItemClicked.set(itemNumber, click);
                    } else {
                        view.setBackgroundColor(Color.WHITE); //change back to normal view
                        Clicked click = new Clicked();
                        click.clicked = true;
                        isItemClicked.set(itemNumber, click);
                    }
                } catch (IndexOutOfBoundsException a) {
                }

            }
        });
    }


}
