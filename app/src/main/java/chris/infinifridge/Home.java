package chris.infinifridge;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

    }

    public void AddNewButtonPressed(View v){
        Intent intent = new Intent(this, AddActivity.class);
        startActivity(intent);
    }

}
