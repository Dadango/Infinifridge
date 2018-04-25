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
    static ArrayList myEntries = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        File readMe = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "/infinifridge" + File.separator + "Entries.json");
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(readMe.getPath()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String jasonA = "";
        if (br != null) {
            try {
                jasonA = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        JSONArray jason = null;
        try {
            jason = new JSONArray(jasonA);
            myEntries.clear();
            for (int i = 0; i < jasonA.length(); i++) {
                JSONObject jasonO = jason.getJSONObject(i);
                String name = jasonO.getString("Name");
               int imageId = Integer.parseInt(jasonO.getString("ImageID"));
                int amount = Integer.parseInt(jasonO.getString("Amount"));
                int amountType = Integer.parseInt(jasonO.getString("AmountType"));
                int storage = Integer.parseInt(jasonO.getString("Storage"));
                int[] expiryDate = {Integer.parseInt(jasonO.getString("ExpD")),Integer.parseInt(jasonO.getString("ExpM")),Integer.parseInt(jasonO.getString("ExpY"))};

                myEntries.add(new Entries(name, imageId, amount, amountType, storage, expiryDate));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        /*
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
        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED)

    {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
    }

    setContentView(R.layout.activity_home);

}

    @Override
    protected void onResume() {

        super.onResume();
        boolean[] imageWar = new boolean[myEntries.size()];
        GridView gridView = findViewById(R.id.gridViewHome);
        if (myEntries != null) {
            String[] homeNames = new String[myEntries.size()];
            int[] homeImageIds = new int[myEntries.size()];
            for (int i = 0; i < myEntries.size(); i++) {
                Entries ph = (Entries) myEntries.get(i);
                homeNames[i] = ph.name;
                homeImageIds[i] = ph.imageId;
                imageWar[i] = ph.amIExpiringSoon();
            }
            gridView.setAdapter(new GridAdapter(this, homeNames, homeImageIds, imageWar));
        }
    }

    public void AddNewButtonPressed(View v) {
        Intent intent = new Intent(this, AddActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();

        JSONArray jj = new JSONArray();
        for (int i = 0; i < myEntries.size(); i++) {
            JsonSaver j = new JsonSaver((Entries) myEntries.get(i));
            jj.put(j);
        }
        Writer output;

        File saveDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "/infinifridge");
        File saveFile = new File(saveDir, "Entries.json");
        try {
            FileOutputStream fos = new FileOutputStream(saveFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            output = new BufferedWriter(new FileWriter(saveFile));
            output.write(jj.toString());
            output.close();
            Toast.makeText(getApplicationContext(), "Entries saved", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
