package chris.infinifridge;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class AddActivity extends AppCompatActivity {
    public static String entry = null;
    // final AssetManager assetManager = getBaseContext().getAssets();
    private static int fixinator2000 = 3; //int equals amount of icons after entries

    private static Field[] ID_Fields = R.mipmap.class.getFields();
    private static int[] resArray = new int[ID_Fields.length];

    public static String[] entryNames = new String[ID_Fields.length - fixinator2000];
    public static int[] entryImages = new int[ID_Fields.length - fixinator2000];

    EditText stv;
    TextView tv;
    String testString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_activity);
        GridView gridView = findViewById(R.id.gridView);

        try {
            updateEntries();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        gridView.setAdapter(new GridAdapter(this, entryNames, entryImages));

    }


    @Override
    protected void onResume() {
        super.onResume();

        for (int i = 0; i < ID_Fields.length - fixinator2000; i++) {
            try {
                resArray[i] = ID_Fields[i].getInt(null);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        if (entry != null) {
            entry = null;
            Log.i("fuck", "entry is now null");
            descriptionOverlay();
        }
    }


    public void updateEntries() throws IllegalAccessException { //remember to call me after popup EDIT thing
        for (int i = 0; i < entryNames.length; i++) {
            String name = ID_Fields[i].getName().replace("entry_", "");
            entryNames[i] = name.substring(0, 1).toUpperCase() + name.substring(1);
            entryImages[i] = ID_Fields[i].getInt(null);
        }
        ArrayList entryNamesTemp = new ArrayList();
        ArrayList entryImageTemp = new ArrayList();
        for (int i = 0; i < entryNames.length; i++) {
            if (filter(entryNames[i])) { //if entry should be filtered out
                entryNamesTemp.add(entryNames[i]);
                entryImageTemp.add(entryImages[i]);
            }
        }
        String[] ent = new String[entryNamesTemp.size()];
        int[] eit = new int[entryImageTemp.size()];
        for (int i = 0; i < entryNamesTemp.size(); i++) {
            ent[i] = (String) entryNamesTemp.get(i);
            eit[i] = (int) entryImageTemp.get(i);
        }
        entryNames = ent;
        entryImages = eit;
    }

    public boolean filter(String entryName) {
        stv = findViewById(R.id.search_field);
        tv = findViewById(R.id.tV);
        tv.setVisibility(View.GONE);
        final TextWatcher watchDogs2 = new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tv.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
                    tv.setVisibility(View.GONE);
                }else {
                    tv.setText(stv.getText());
                    testString = tv.getText().toString();
                }
            }
        };
        stv.addTextChangedListener(watchDogs2);
        Log.i("please", testString +"");


        return entryName.equalsIgnoreCase(testString + "");

    }

    public void descriptionOverlay() {
        Intent intent = new Intent(this, DescriptionOverlay.class);
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        AddActivity.this.finish();

    }
}
