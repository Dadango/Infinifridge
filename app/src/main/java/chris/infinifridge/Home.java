package chris.infinifridge;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }
    @Override
    protected void onResume() {
        super.onResume();
        Entries shrimp = new Entries();
        Log.println(Log.INFO,"expirationD", shrimp.setExpirationDate());

    }

    public void AddNewButtonPressed(View v){
        Intent intent = new Intent(this, AddActivity.class);
        startActivity(intent);
    }

}
