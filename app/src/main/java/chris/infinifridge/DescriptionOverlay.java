package chris.infinifridge;

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
    static Entries ph = new Entries();
    static int imageId = 0;
    static String phName = "";
    static boolean addOrEdit2;
    static int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description_overlay);
        ph.name = phName;
        ph.imageId = imageId;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (addOrEdit2) {
            findViewById(R.id.bt_Delete).setVisibility(View.GONE);
        }else{
            findViewById(R.id.bt_Delete).setVisibility(View.VISIBLE);
        }
        TextView name = findViewById(R.id.textView);
        name.setText(ph.name);
        TextView ename = findViewById(R.id.editTextName);
        ename.setText(ph.name);
        TextView amount = findViewById(R.id.editTextAmount);
        amount.setText(ph.amount + "");
        ImageView image = findViewById(R.id.imageView);
        image.setImageResource(ph.imageId);
        Spinner amountT = findViewById(R.id.spinner);
        amountT.setOnItemSelectedListener(this);
        List<String> list = new ArrayList<String>();
        list.add("kg");
        list.add("pack(s)");
        list.add("g");
        list.add("litre");
        list.add("ml");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        amountT.setAdapter(adapter);
        amountT.setSelection(ph.amountType);


        Spinner sLocation = findViewById(R.id.spinner3);
        sLocation.setOnItemSelectedListener(this);
        List<String> list3 = new ArrayList<String>();
        list3.add("Fridge");
        list3.add("Freezer");
        list3.add("Cupboards");
        list3.add("Other");

        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list3);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sLocation.setAdapter(adapter3);
        sLocation.setSelection(ph.storage);

        TextView day = findViewById(R.id.editTextDay);
        TextView month = findViewById(R.id.editTextMonth);
        TextView year = findViewById(R.id.editTextYear);

        boolean ddmmyyyy = true;
        for (int i = 0; i < 3; i++) {
            if (ph.expirationDate[i] == 0) {
                ddmmyyyy = false;
                Log.i("loop ran", i + " times");
            }
        }
        if (ddmmyyyy) {
            Log.i("NONZERO", ddmmyyyy + "");

            day.setText(ph.expirationDate[0] + "");
            month.setText(ph.expirationDate[1] + "");
            year.setText(ph.expirationDate[2] + "");
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void onClickCancel(View view) {
        ph = new Entries();
        super.onBackPressed();
    }

    public void onClickDelete(View view) {
        ph = new Entries();
        Home.myEntries.remove(position);
        super.onBackPressed();
    }

    public void onClickSave(View view) {
        ph = new Entries();
        boolean iALreadyDid = false;
        TextView nameTV = findViewById(R.id.editTextName);
        String iname = nameTV.getText().toString();


        TextView amountTV = findViewById(R.id.editTextAmount);
        int amount = Integer.parseInt(amountTV.getText().toString());

        Spinner amountTypeS = findViewById(R.id.spinner);
        int amountType = amountTypeS.getSelectedItemPosition();

        Spinner storageS = findViewById(R.id.spinner3);
        int storage = storageS.getSelectedItemPosition();

        TextView expDTV = findViewById(R.id.editTextDay);
        TextView expMTV = findViewById(R.id.editTextMonth);
        TextView expYTV = findViewById(R.id.editTextYear);
        int[] expirationDate = {0, 0, 0};

        try {
            int d = Integer.parseInt(expDTV.getText().toString());
            int m = Integer.parseInt(expMTV.getText().toString());
            int y = Integer.parseInt(expYTV.getText().toString());

            expirationDate[0] = d;
            expirationDate[1] = m;
            expirationDate[2] = y;
        } catch (NumberFormatException n) {
//            Toast.makeText(this, "Please input a legible date", Toast.LENGTH_SHORT).show();
            iALreadyDid = true;
            if (addOrEdit2) {
                Home.myEntries.add(new Entries(iname, imageId, amount, amountType, storage));
            } else {
                Home.myEntries.set(position, new Entries(iname, imageId, amount, amountType, storage));
            }
        }
        super.onBackPressed();
        if (addOrEdit2 && iALreadyDid == false) {
            Home.myEntries.add(new Entries(iname, imageId, amount, amountType, storage, expirationDate));
        } else if(addOrEdit2 == false && iALreadyDid == false) {
            Log.i("Do you run you fat fuck","Yes");
            Home.myEntries.set(position, new Entries(iname, imageId, amount, amountType, storage, expirationDate));
        }
    }
}
