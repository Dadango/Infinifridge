package chris.infinifridge;


import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.SearchView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class AddActivity extends AppCompatActivity {
    public static String entry = null;                                                              //Initializes a new String variable called entry with the value null
    // final AssetManager assetManager = getBaseContext().getAssets();
    private static int fixinator2000 = 3;                                                           //int equals amount of icons after entries in res/mipmap

    private static Field[] ID_Fields = R.mipmap.class.getFields();                                  //Initializes a new Field array called ID_Fields with the values from R.mipmap
    private static int[] resArray = new int[ID_Fields.length];                                      //Initializes a new integer array called resArray with the length of ID_fields

    public static String[] entryNames = new String[ID_Fields.length - fixinator2000];               //Initializes a new string array called entryNames with the length of ID_fields minus fixinator(3)
    public static int[] entryImages = new int[ID_Fields.length - fixinator2000];                    //Initializes a new integear array called entryImages with the length of ID_fields minus fixinator(3)
    public static String please;
    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_activity);                                                      //Inflates the layout of addActivity to the xml file add_Activity
        gridView = findViewById(R.id.gridView);                                                      //Finds a certain gridview dependent on its ID which is found via "findViewById"
        gridView.setAdapter(new GridAdapter(this, entryNames, entryImages));              //Sets the gridview to Gridadapter with the values of entryNames and entryImages
        try {
            updateEntries();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        textWatchDog();
    }

    public void textWatchDog() {
        EditText stv = findViewById(R.id.search_field);
        final String[] testString = {""};
        final TextWatcher watchDogs2 = new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                EditText stv = findViewById(R.id.search_field);
                please = stv.getText().toString() + "";
                //please = testString[0];
                try {
                    updateEntries();
                    Log.i("Entries", "Updated");
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

            }
        };
        stv.addTextChangedListener(watchDogs2);
    }


    @Override
    protected void onResume() {
        super.onResume();
        please = null;
        //from line 52-67, unsure
        for (int i = 0; i < ID_Fields.length - fixinator2000; i++) {                                //For loop that keeps running till it reaches the length of ID_Fields minus fixinator(3)
            try {                                                                                   //Exception handling for Illegal argument and access exception
                resArray[i] = ID_Fields[i].getInt(null);                                        //Assigns the value of ID_fields to resArray for each iteration of i
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        if (entry != null) {                                                                        //Runs if statement if entry is not null
            entry = null;                                                                           //Sets entry variable to null
            descriptionOverlay();                                                                   //calls the descriptionOverlay method
        }
    }


    public void updateEntries() throws IllegalAccessException { //remember to call me after popup EDIT thing - Creating new method that throws IllegalAccessException as exception handling
        for (int i = 0; i < entryNames.length; i++) {
            String name = ID_Fields[i].getName().replace("entry_", "");
            entryNames[i] = name.substring(0, 1).toUpperCase() + name.substring(1);
            entryImages[i] = ID_Fields[i].getInt(null);
        }
        ArrayList<String> entryNamesTemp = new ArrayList();
        ArrayList<Integer> entryImagesTemp = new ArrayList();
        for (int i = 0; i < entryNames.length; i++) {
            if (filter(entryNames[i])) { //if entry should be filtered out
                entryNamesTemp.add(entryNames[i]);
                entryImagesTemp.add(entryImages[i]);
            }
        }
        String[] entryNameTemp = new String[entryNamesTemp.size()];
        int[] entryImageTemp = new int[entryImagesTemp.size()];
        for (int i = 0; i < entryImagesTemp.size(); i++) {
            entryNameTemp[i] = entryNamesTemp.get(i);
            entryImageTemp[i] = entryImagesTemp.get(i);
        }
        if (please != null) {
            gridView.setAdapter(new GridAdapter(this, entryNameTemp, entryImageTemp));              //Changes the display to the new temporary values, to only show the filtered results
        }
    }

    public boolean filter(String entryName) {
        if (please != null) {
            return entryName.toLowerCase().startsWith(please.toLowerCase());
        }
        return false;
    }


    public void descriptionOverlay() {                                                              //Method to start a new activity
        Intent intent = new Intent(this, DescriptionOverlay.class);                    //Creates a new intent with the intent going from this activity to DescriptionOverlay activity
        startActivity(intent);                                                                      //Starts the activity with the intent
    }

    @Override
    protected void onPause() {
        super.onPause();
        AddActivity.this.finish();                                                                  //When addActivity is put on pause it will finish its activity, which means the window will close
        //So you do not get multiple AddActivity activities open at the same time

    }
}
