package chris.infinifridge;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DescriptionOverlay extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    static String phName = "placeholder";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description_overlay);
        Entries ph = new Entries();
        ph.name = phName;

    }

    @Override
    protected void onResume() {
        super.onResume();
        TextView  name = findViewById(R.id.textView);
        name.setText(phName);
        Spinner amountT = findViewById(R.id.spinner);
        amountT.setOnItemSelectedListener(this);
        List<String> list = new ArrayList<String>();
        list.add("kg");
        list.add("pack(s)");
        list.add("g");
        list.add("litre");
        list.add("ml");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        amountT.setAdapter(adapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
