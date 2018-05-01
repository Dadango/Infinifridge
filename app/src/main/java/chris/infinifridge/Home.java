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
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;


public class Home extends AppCompatActivity {
    static ArrayList myEntries = new ArrayList();                                                   // initializing a new array list called myEntries

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //from line 36-69, loads all information about entries from the save file, entries.json
        File readMe = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "/infinifridge" + File.separator + "Entries.json"); //Creating a new file at the location infinifridge in local storage with the name entries.json
        BufferedReader br = null;                                                                   //Sets the variable br to null
        try {                                                                                       //Exception handling for FileNotFoundException
            br = new BufferedReader(new FileReader(readMe.getPath()));                              //Assigns value to the variable br, that it should read the file readMe and find its path
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String jasonA = "";                                                                         //Initializes the variable jasonA to a blank string
        if (br != null) {                                                                           //If-statement that only runs if br is not null
            try {                                                                                   //Exception handling for IOException
                jasonA = br.readLine();                                                             //Assigns the value readline() to jasonA
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        JSONArray jason = null;                                                                     //Initializes a JSONArray called jason and set it to the value null
        try {                                                                                       //Exception handling for JSONException
            jason = new JSONArray(jasonA);                                                          //Assigns a new JSONArray with the value jasonA to jason
            myEntries.clear();                                                                      //Clears all elements in the myEntries array
            for (int i = 0; i < jasonA.length(); i++) {                                             //Creates a for loop that runs from 0 to the length of the jasonA array
                JSONObject jasonO = jason.getJSONObject(i);                                         //Initializes a new JSON object called jasonO, that is assigns the value of jason for each iteration of i
                String name = jasonO.getString("Name");                                       //Initializes a new string called Name that is assigned the value name from JasonO (jason object)
                int imageId = Integer.parseInt(jasonO.getString("ImageID"));                  //Initializes a new integer called imageID that is assigned the value imageID from jasonO (jason object)
                int amount = Integer.parseInt(jasonO.getString("Amount"));                    //Initializes a new integer called amount that is assigned the value Amount from jasonO (jason object)
                int amountType = Integer.parseInt(jasonO.getString("AmountType"));            //Initializes a new integer called amountType that is assigned the value amountType frmo jasonO (jason object)
                int storage = Integer.parseInt(jasonO.getString("Storage"));                  //Initializes a new integer called storage that is assigned the value Storage from jasonO (jason object)
                int[] expiryDate = {Integer.parseInt(jasonO.getString("ExpD")), Integer.parseInt(jasonO.getString("ExpM")), Integer.parseInt(jasonO.getString("ExpY"))};  //Initializes a new integer array called expiryDate that is assigned three values expD, expM and expY from jasonO (jason object)

                myEntries.add(new Entries(name, imageId, amount, amountType, storage, expiryDate)); //Add a new element to the myEntries arraylist with the values name, imageId, amount, amountType, storage and expiryDate
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        /* another way to load files that did not work
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "/infinifridge/Entries.json"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (br != null) {

        }
            try {
                String entryArrayS = br.readLine();
                Log.i("Test", entryArrayS);
                for (int i = 0; i < entryArrayS.length(); i++) {
                    if (entryArrayS.substring(0, i).contains("@n")) {
                        String nameFinder = entryArrayS.substring(0, i);
                        String name = nameFinder.substring(0, i - 2).replaceAll("\"", "").replaceFirst("Name", "").replaceAll("@n", "").replace("[{:", "");
                        i = entryArrayS.length();
                        Log.i("TestName", name);
                    }
                    if (entryArrayS.substring(0, i).contains("@n")) {
                        String nameFinder = entryArrayS.substring(0, i);
                        String name = nameFinder.substring(0, i - 2).replaceAll("\"", "").replaceFirst("Name", "").replaceAll("@n", "").replace("[{:", "");
                        i = entryArrayS.length();
                        Log.i("TestName", name);

                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
*/
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
        boolean[] imageWar = new boolean[myEntries.size()];                                         //Initializes a new boolean array called imageWar with the size of the myEntries arraylist
        GridView gridView = findViewById(R.id.gridViewHome);                                        //Initializes a gridview and find its id via the R.java file
        if (myEntries != null) {                                                                    //If statement that only runs if myEntries is not null
            String[] homeNames = new String[myEntries.size()];                                      //Initializes a new string array called homeNames with the length of the size of myEntries
            int[] homeImageIds = new int[myEntries.size()];                                         //Initializes a new integer array called homeImageIds with the lengeth of the size of myEntries
            for (int i = 0; i < myEntries.size(); i++) {                                            //A for loop that keeps running until i reaches the length of myEntries
                Entries ph = (Entries) myEntries.get(i);                                            //Initializes a variable called ph with the type Entries, that gets the information from myEntries for each iteration of i
                homeNames[i] = ph.name;                                                             //Assigns the value of ph.name for each iteration of i for the homeNames array
                homeImageIds[i] = ph.imageId;                                                       //Assigns the value of ph.imageID for each iteration of i for the homeImageIds array
                imageWar[i] = ph.amIExpiringSoon();                                                 //Calls the amIExpiringSoon method for each iteration of i for the boolean array imageWar
            }
            gridView.setAdapter(new GridAdapter(this, homeNames, homeImageIds, imageWar));//Sets the gridview dependent on the values of homeNames, homeImageIds and imageWawr
        }
    }

    public void AddNewButtonPressed(View v) {
        Intent intent = new Intent(this, AddActivity.class);                           //Clicking the add new button will start a new activity(addactivity) from the home activity
        startActivity(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //from line 144-165, Save the information from myEntries into a saveFile called entries.json
        JSONArray jj = new JSONArray();                                                             //Initializes a new JSONarray with the name jj
        for (int i = 0; i < myEntries.size(); i++) {                                                //For loop that keeps running until it reaches the size of myEntries
            JsonSaver j = new JsonSaver((Entries) myEntries.get(i));                                //Initializes a new variable with the name j that is a new object of the jsonSaser that gets all the values of the arraylist myEntries
            jj.put(j);                                                                              //Put the values of j into the JSON array jj
        }
        Writer output;                                                                              //Declares the variable output with the type Writer

        File saveDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "/infinifridge"); //Creates a new file at the external storage directory plus the location infinifridge
        File saveFile = new File(saveDir, "Entries.json");                                     //Creates a new File called saveFile that is to be saved at the saveDir location with the name Entries.json
        try {                                                                                       //Exception handling for FileNotFoundException
            FileOutputStream fos = new FileOutputStream(saveFile);                                  //Unsure as the variable is not used
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {                                                                                       //Exception handling for IOException
            output = new BufferedWriter(new FileWriter(saveFile));                                  //Assigns the value of saveFile to the variable output
            output.write(jj.toString());                                                            //Writes the information of the JSONArray jj and converts it to a string
            output.close();                                                                         //Closes the outputStream
            Toast.makeText(getApplicationContext(), "Entries saved", Toast.LENGTH_LONG).show();//Makes a toast when the application saves
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
