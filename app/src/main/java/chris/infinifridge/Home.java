package chris.infinifridge;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.GridView;

import java.util.ArrayList;


public class Home extends AppCompatActivity {

    static ArrayList myEntries = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }
    @Override
    protected void onResume() {
        super.onResume();
        GridView gridView = findViewById(R.id.gridViewHome);
        if (myEntries !=  null){
            String[] homeNames = new String[myEntries.size()];
            int[] homeImageIds = new int[myEntries.size()];
            for (int i = 0; i < myEntries.size(); i++){
                Entries ph = (Entries)myEntries.get(i);
                homeNames[i] = ph.name;
                homeImageIds[i] = ph.imageId;
            }
            gridView.setAdapter(new GridAdapter(this, homeNames, homeImageIds));
        }
    }

    public void AddNewButtonPressed(View v){
        Intent intent = new Intent(this, AddActivity.class);
        startActivity(intent);
    }

}
