package chris.infinifridge;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DescriptionOverlay extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    static Entries ph = new Entries();                                                              //Initializes a new object ph with the type Entries
    static int imageId = 0;                                                                         //Initializes a new integer variable imageID with the value 0
    static String phName = "";                                                                      //Initializes a new String variable phName with the value blank
    static boolean addOrEdit2;                                                                      //Declaring a new boolean variable addOrEdit2
    static int position;                                                                            //Declaring a new integer variable position

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description_overlay);                                      //Inflates the layout of DescriptionOverlay to the activity_description_overlay xml file
        ph.name = phName;                                                                           //Assigns the value of ph.name to phName
        ph.imageId = imageId;                                                                       //Assigns the value of ph.imageID to imageId
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (addOrEdit2) {                                                                           //If statement dependent on the value of addOrEdit2
            findViewById(R.id.bt_Delete).setVisibility(View.GONE);                                  //Sets the delete button to GONE if the statement is true
        } else {
            findViewById(R.id.bt_Delete).setVisibility(View.VISIBLE);                               //Sets the delete button to VISIBLE if the statement is false
        }
        TextView name = findViewById(R.id.textView);                                                //Make a new TextView variable with the name "name" and set its ID to textView
        name.setText(ph.name);                                                                      //Sets the text of the name Textview to whatever the value of ph.name is
        TextView ename = findViewById(R.id.editTextName);                                           //Make a new TextView variable with the name "ename" and set its id to the editTextName id
        ename.setText(ph.name);                                                                     //Sets the text of the ename TextView to whatever the value of ph.name is
        TextView amount = findViewById(R.id.editTextAmount);                                        //Make a new TextView variable with the name "amount" and set its id to editTextAmount id
        amount.setText(ph.amount + "");                                                             //Sets the text of the amount TextView to whatever the value of ph.amount is
        ImageView image = findViewById(R.id.imageView);                                             //Make a new imageView variable with the name "image" and set its id to imageView id
        image.setImageResource(ph.imageId);                                                         //Sets the image of the imageView to the value of ph.imageId
        Spinner amountT = findViewById(R.id.spinner);                                               //Make a new spinner with the name amountT and set its id to spinner's id
        amountT.setOnItemSelectedListener(this);                                                    //Creates a listener for the spinner amountT to be able to see what the user has selected on the spinner
        List<String> list = new ArrayList<String>();                                                //Creates a new string Arraylist called list
        list.add("kg");                                                                             //line 51-55 add new items to the spinner
        list.add("pack(s)");
        list.add("g");
        list.add("litre");
        list.add("ml");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list); //Creates a new adapter with the type arrayAdapter with the layout of the simple_spinner_item and it contains "list" array as items on the spinner
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);             //Sets the spinner to be a drop down menu
        amountT.setAdapter(adapter);                                                                //Sets the list of item from arraylist "list"
        amountT.setSelection(ph.amountType);                                                        //Sets the value of ph.amountType dependent on the item the user selects


        Spinner sLocation = findViewById(R.id.spinner3);                                            //Makes a spinner with the name sLocation and sets it id to spinner3
        sLocation.setOnItemSelectedListener(this);                                                  //Creates a listener for the spinner sLocation to be able to see what the user has selected
        List<String> list3 = new ArrayList<String>();                                               //Creates a new string Arraylist called list3
        list3.add("Fridge");                                                                        //line 65-58 add new items to the spinner
        list3.add("Freezer");
        list3.add("Cupboards");
        list3.add("Other");

        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list3); //Creates a new adapter with the type arrayAdapter with the layout of the simple_spinner_item and it contains "list3" array as items on the spinner
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);            //Sets the spinner to be a drop down menu
        sLocation.setAdapter(adapter3);                                                             //Sets the list of item from arraylist "list3"
        sLocation.setSelection(ph.storage);                                                         //Sets the value of ph.storage dependent on the item the user selects

        TextView day = findViewById(R.id.editTextDay);                                              //Creates a TextView variable with the name "day" and set its id to editTextDay
        TextView month = findViewById(R.id.editTextMonth);                                          //Creates a TextView variable with the name "month" and set its id to editTextMonth
        TextView year = findViewById(R.id.editTextYear);                                            //Creates a TextView variable with the name "year" and set its id to editTextYear

        boolean ddmmyyyy = true;                                                                    //Initializes boolean variablae ddmmyyyy to true
        for (int i = 0; i < 3; i++) {                                                               //For loop that runs until i reaches 3
            if (ph.expirationDate[i] == 0) {                                                        //If statement that is true if the iteration of ph.expirationDate[i] is equal to 0
                ddmmyyyy = false;                                                                   //Then it sets the boolean variable ddmmyyyy to false
                Log.i("loop ran", i + " times");
            }
        }
        if (ddmmyyyy) {                                                                             //If the ddmmyyyy is true the following code will run
            Log.i("NONZERO", ddmmyyyy + "");

            day.setText(ph.expirationDate[0] + "");                                                 //Sets the TextView day to the value of ph.expirationDate[0]
            month.setText(ph.expirationDate[1] + "");                                               //Sets the TextView month to the value of ph.expirationDate[1]
            year.setText(ph.expirationDate[2] + "");                                                //Sets the TextView year to the value of ph.expirationDate[2]
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {           //Nothing happens here as there are no code in here

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {                                          //Nothing happens here as there are no code in heree

    }

    public void onClickCancel(View view) {                                                          //Method for what happens when you click on the cancel button
        ph = new Entries();                                                                         //Creates a new entries object to the variable ph
        super.onBackPressed();                                                                      //Method imported from the super class onBackPressed, that makes the user go back when clicked
        if (addOrEdit2) {                                                                           //If boolean variable addOrEdit true the following code will run
            Intent intent = new Intent(this, AddActivity.class);                       //Creates a new intent that goes from this(DescriptionOverlay) to AddActivity class
            startActivity(intent);                                                                  //Starts the activity from the intent
        }
    }

    public void onClickDelete(View view) {                                                          //Method for what happens when you click on the delete button
        ph = new Entries();                                                                         //Creates a new entries object to the variable ph
        Home.myEntries.remove(position);                                                            //Remove the Entry pressed from the home class. As it removes what was on the position that was clicked
        super.onBackPressed();                                                                      //Method imported from the super class onBackPressed, that makes the user go back when clicked
        if (addOrEdit2) {                                                                           //If addOrEdit2 is tru it will run the following code
            Intent intent = new Intent(this, AddActivity.class);                       //Creates a new intent that goes from this(DescriptionOverlay) to AddActivity class
            startActivity(intent);                                                                  //Starts the activity from the intent
        }
    }

    public void onClickSave(View view) {                                                            //Method for what happens when you click on the save button
        ph = new Entries();                                                                         //Creates a new entries object to the variable ph
        boolean iAlreadyDid = false;                                                                //Initializes a new boolean variable "IAlreadyDid" and set it to false
        TextView nameTV = findViewById(R.id.editTextName);                                          //Creates a new TextView variable with the name "nameTV" and sets it id to editTextName
        String iname = nameTV.getText().toString();                                                 //Initializes a new string variable called "iname" and it should have the value of nameTV converted to a string


        TextView amountTV = findViewById(R.id.editTextAmount);                                      //Creates a new TextView variable with the name "amountTV" and sets it id to editTextAmount
        int amount = Integer.parseInt(amountTV.getText().toString());                               //Initializes a new integer variable "amount" and set it to the value of amountTV convert it to a string and then parse the string to an integer

        Spinner amountTypeS = findViewById(R.id.spinner);                                           //Creates a new Spinner variable with the name "amountTypeS" and set its id to spinner
        int amountType = amountTypeS.getSelectedItemPosition();                                     //Initializes a new integer variable "amountType" and set it to the value of the selected item of the spinner amountTypeS

        Spinner storageS = findViewById(R.id.spinner3);                                             //Creates a new spinner variable with the name "storageS" and set its id to spinner3
        int storage = storageS.getSelectedItemPosition();                                           //Initializes a new integer variable "storage" and set it to the value of the selected item of the spinner3 storageS

        TextView expDTV = findViewById(R.id.editTextDay);                                           //Creates a new TextView variable expDTV and set its id to editTextDay
        TextView expMTV = findViewById(R.id.editTextMonth);                                         //Creates a new TextView variable expMTV and set its id to editTextMonth
        TextView expYTV = findViewById(R.id.editTextYear);                                          //Creates a new TextView variable expYTV and set its id to editTextYear
        int[] expirationDate = {0, 0, 0};                                                           //Initialize a new integer array expirationDate with pre-set values 0,0,0

        try {                                                                                       //Exception handling for NumberFormatException
            int d = Integer.parseInt(expDTV.getText().toString());                                  //Initializing a new integer variable d that is assigned the value of expDTV converted to a string then parsed to an integer
            int m = Integer.parseInt(expMTV.getText().toString());                                  //Initializing a new integer variable m that is assigned the value of expMTV converted to a string then parsed to an integer
            int y = Integer.parseInt(expYTV.getText().toString());                                  //Initializing a new integer variable y that is assigned the value of expYTV converted to a string then parsed to an integer

            expirationDate[0] = d;                                                                  //Assigning the value of d as the first element of the expirationDate array
            expirationDate[1] = m;                                                                  //Assigning the value of m as the second element of the expirationDate array
            expirationDate[2] = y;                                                                  //Assigning the value of y as the third element of the expirationDate array
        } catch (NumberFormatException n) {
//            Toast.makeText(this, "Please input a legible date", Toast.LENGTH_SHORT).show();
            iAlreadyDid = true;                                                                     //Sets the boolean variable iAlreadyDid to true
            if (addOrEdit2) {                                                                       //If addOrEdit2 is true the following code wil run, if the user are adding a new entry
                Home.myEntries.add(new Entries(iname, imageId, amount, amountType, storage));       //Will add a new Entries to myEntries in the home class with the values of iname, imageID, amount, amountType and storage
            } else {                                                                                //If addOrEdit2 is fale the following code wil run, if the user are editing an entry
                Home.myEntries.set(position, new Entries(iname, imageId, amount, amountType, storage)); //will set new values of iname, imageID, amount, amountType and storage in the selected entry in the home class if the user changes them
            }
        }
        super.onBackPressed();                                                                      //Imported method from super class onBackPressed, that makes it go back to previous activity when pressing back
        if (addOrEdit2 && iAlreadyDid == false) {                                                   //Unsure if line 156-160 or 163-168 are adding or editing
            Home.myEntries.add(new Entries(iname, imageId, amount, amountType, storage, expirationDate)); //Unsure about the rest actually
        } else if (addOrEdit2 == false && iAlreadyDid == false) {
            Log.i("Do you run you fat fuck", "Yes");
            Home.myEntries.set(position, new Entries(iname, imageId, amount, amountType, storage, expirationDate));
        }
        if (addOrEdit2) {
            Intent intent = new Intent(this, AddActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (addOrEdit2) {
            Intent intent = new Intent(this, AddActivity.class);
            startActivity(intent);
        }
    }
}
