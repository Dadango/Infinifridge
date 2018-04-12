package chris.infinifridge;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

public class AddActivity extends AppCompatActivity {
    public static String[] entryNames = {
            "egg",
            "shrimp",
            "bacon",
            "orange",
    };
    public static int[] entryImages = {
            R.mipmap.ic_egg,
            R.mipmap.ic_shrimp,
            R.mipmap.ic_bacon,
            R.mipmap.ic_orange
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_activity);
        GridView gridView = findViewById(R.id.gridView);
        gridView.setAdapter(new GridAdapter(this, entryNames, entryImages));

    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
