package chris.infinifridge;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.GridView;

import java.lang.reflect.Field;

public class AddActivity extends AppCompatActivity {
    public static String entry = null;
    // final AssetManager assetManager = getBaseContext().getAssets();
    private static int fixinator2000 = 2; //int equals amount of icons after entries

    private static Field[] ID_Fields = R.mipmap.class.getFields();
    private static int[] resArray = new int[ID_Fields.length];

    public static String[] entryNames = new String[ID_Fields.length - fixinator2000];
    public static int[] entryImages = new int[ID_Fields.length - fixinator2000];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_activity);
        GridView gridView = findViewById(R.id.gridView);
        gridView.setAdapter(new GridAdapter(this, entryNames, entryImages));

        try {
            updateEntries();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
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
        for (int i = 0; i < ID_Fields.length - fixinator2000; i++) {
            String name = ID_Fields[i].getName().replace("entry_", "");
            entryNames[i] = name.substring(0, 1).toUpperCase() + name.substring(1);
            entryImages[i] = ID_Fields[i].getInt(null);
        }
    }

    public void descriptionOverlay() {

        Intent intent = new Intent(this, DescriptionOverlay.class);
        startActivity(intent);
    }
}
