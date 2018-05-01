package chris.infinifridge;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.GridView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;


public class Home extends AppCompatActivity {
    static ArrayList myEntries = new ArrayList();                                                   // initializing a new ArrayList called myEntries

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
                                                                                                    //from line 36-69, loads all information about entries from the save file, Entries.json
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "/infinifridge" + File.separator + "Entries.json"; //saves the path as a String for readability
        BufferedReader br = null;                                                                   //Declares the variable "br" and initializes it to 'null', because the variable is declared in a method
        try {                                                                                       //Exception handling for FileNotFoundException
            br = new BufferedReader(new FileReader(path));                                          //Assigns br to a new BufferedReader, to buffer the input from the FileReader, with the save file being the file to read from
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String jasonA = "";                                                                         //Declares and initializes the variable jasonA to a blank string
        if (br != null) {                                                                           //If-statement to avoid a nullPointException, running only if the BufferedReader exists (is not null)
            try {                                                                                   //Exception handling for IOException
                jasonA = br.readLine();                                                             //Assigns the buffered input from the FileReader as a String to jasonA.
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        JSONArray jason = null;                                                                     //Declares and initializes a JSONArray called "jason" to 'null'
        try {                                                                                       //Exception handling for JSONException
            jason = new JSONArray(jasonA);                                                          //jasonA is the save file converted into a String, which lets us import that into a JSONArray
                                                                                                    // directly (as this is how it is saved in the first place), an assign "jason" to this new JSONArray.
            myEntries.clear();                                                                      //Clears all elements in the myEntries array, effectively removing them and resizing the Array to 0
            for (int i = 0; i < jasonA.length(); i++) {                                             //Creates a for loop that runs from 0 to the length of the jasonA array (actually it goes the length of the String rn?
                JSONObject jasonO = jason.getJSONObject(i);                                         //Declares and initializes a new JSONObject called jasonO, that is assigns the value of jason for each iteration of i
                String name = jasonO.getString("Name");                                       //Declares and initializes a new string called name that is assigned the value "Name" from JasonO (jason object)
                int imageId = Integer.parseInt(jasonO.getString("ImageID"));                  //Declares and initializes a new integer called imageID that is assigned the value "ImageID" from jasonO (jason object)
                int amount = Integer.parseInt(jasonO.getString("Amount"));                    //Declares and initializes a new integer called amount that is assigned the value "Amount" from jasonO (jason object)
                int amountType = Integer.parseInt(jasonO.getString("AmountType"));            //Declares and initializes a new integer called amountType that is assigned the value "AmountType" frmo jasonO (jason object)
                int storage = Integer.parseInt(jasonO.getString("Storage"));                  //Declares and initializes a new integer called storage that is assigned the value "Storage" from jasonO (jason object)
                int[] expiryDate = {Integer.parseInt(jasonO.getString("ExpD")), Integer.parseInt(jasonO.getString("ExpM")), Integer.parseInt(jasonO.getString("ExpY"))};
                //^Declares and initializes a new integer array called expiryDate that is assigned three integer values: "ExpD", "ExpM" and "ExpY" from jasonO (jason object)

                myEntries.add(new Entries(name, imageId, amount, amountType, storage, expiryDate)); //Add a new element to the myEntries arraylist with the values name, imageId, amount, amountType, storage and expiryDate, from above
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) //If-statement that checks if the application has permission to write to external storage
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1); // if it does not have permission it request permission from the user
        }

        setContentView(R.layout.activity_home);                                                         //Sets the contentView to the layout of the activity_home xml file

    }

    @Override
    protected void onResume() {

        super.onResume();
        //line 120-133, makes a warning icon dependent on expiration date
        boolean[] imageWar = new boolean[myEntries.size()];                                         //Declares and initializes a new boolean array called imageWar with the same size as the myEntries arraylist
        GridView gridView = findViewById(R.id.gridViewHome);                                        //Declares and initializes a Gridview, found via the id of "gridViewHome" from the Content view
        if (myEntries != null) {                                                                    //If statement that only runs its block if myEntries is not null
            String[] homeNames = new String[myEntries.size()];                                      //Declares and initializes a new string array called homeNames with the same size/length as the myEntries arraylist
            int[] homeImageIds = new int[myEntries.size()];                                         //Initializes a new integer array called homeImageIds with the same size as the myEntries arraylist
            for (int i = 0; i < myEntries.size(); i++) {                                            //A for loop that keeps running until 'i' reaches the length of myEntries
                Entries ph = (Entries) myEntries.get(i);                                            //Declares and initializes a variable called ph with the type Entries, that gets its information from the 'i'th object of the meEntries array
                homeNames[i] = ph.name;                                                             //Assigns the value of ph.name to the homeNames array for each iteration of i
                homeImageIds[i] = ph.imageId;                                                       //Assigns the value of ph.imageID to the homeImageIds array for each iteration of i
                imageWar[i] = ph.amIExpiringSoon();                                                 //Assigns the returned value of amIExpiringSoon (running the method returns a boolean) to the boolean array imageWar for each iteration of i
            }
            gridView.setAdapter(new GridAdapter(this, homeNames, homeImageIds, imageWar));//Sets the gridview dependent on the values of homeNames, homeImageIds and imageWar (technically true, but functionally it uses the arrays to build a new grid adapter object from our GridAdapter Class)
        }
    }

    public void AddNewButtonPressed(View v) {
        Intent intent = new Intent(this, AddActivity.class);                           //Clicking the add new button will start a new activity(AddActivity) from the home activity
        startActivity(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
                                                                                                    //from line 144-165, Save the information from myEntries into a saveFile called entries.json
        JSONArray jj = new JSONArray();                                                             //Declares and initializes a new JSONArray with the name jj
        for (int i = 0; i < myEntries.size(); i++) {                                                //For loop that keeps running until 'i' reaches the size of myEntries (runs for each element in the myEntries array)
            JsonSaver j = new JsonSaver((Entries) myEntries.get(i));                                //Declares and initializes a new variable with the name j that is a new object of the jsonSaver Class that gets the Entries object from the 'i'th spot of the ArrayList myEntries
            jj.put(j);                                                                              //Put the JSONObject j into the JSONArray jj
        }                                                                                           //This loop has effectively converted the Entries objects in the myEntries ArrayList into a JSONArray named jj
        Writer output;                                                                              //Declares the variable "output" with the type Writer

        File saveDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "/infinifridge"); //Creates a new directory named infinifridge in the external storage directory
        File saveFile = new File(saveDir, "Entries.json");                                     //Creates a new File called saveFile that is to be saved in the save directory infinifridge(saveDir) with the name Entries.json
        try {                                                                                       //Exception handling for IOException
            output = new BufferedWriter(new FileWriter(saveFile));                                  //Assigns "output" to a new BufferedReader, to buffer the output from the FileReader, with the save file (saveFile) being written to
            output.write(jj.toString());                                                            //Converts jj to a string and writes the String onto the file using "output"
            output.close();                                                                         //Flushes the characters from the stream and then closes it
            Toast.makeText(getApplicationContext(), "Entries saved", Toast.LENGTH_LONG).show();//Makes a 'toast' (the lil popup bastard) when the application saves
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
