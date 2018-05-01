package chris.infinifridge;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class GridAdapter extends BaseAdapter {
    //Declare a bunch of variables
    String[] result;
    Context context;
    int[] imageId;
    boolean[] imageWar;
    boolean addOrEdit; //false = edit
    private static LayoutInflater inflater = null;                                                  //Declare and initialize a new LayoutInflater reference named "inflater"

    public GridAdapter(AddActivity mainActivity, String[] entryName, int[] entryImages) {           //Constructor for the GridAdapter, uses the AddActivity activity
        result = entryName;                                                                         //Set the object variable values to the arguments
        context = mainActivity;
        imageId = entryImages;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        addOrEdit = true;                                                                           //Sets addOrEdit to true, as it is constructed from the AddActivity, meaning the user is adding an entry

    }

    public GridAdapter(Home mainActivity, String[] entryName, int[] entryImages, boolean[] entryWar) {  //Constructor for the GridAdapter, uses the Home activity
        result = entryName;                                                                         //Set the object variable values to the arguments
        context = mainActivity;
        imageId = entryImages;
        imageWar = entryWar;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        addOrEdit = false;                                                                          //Sets addOrEdit to false, as it is constructed from the AddActivity, meaning the user is adding an entry
    }

    //    @Override
    public int getCount() {                                                                         //Accessor method, unused but required
        return result.length;
    }

    //@Override
    public Object getItem(int position) {                                                           //Accessor method, unused but required
        return position;
    }

    //  @Override
    public long getItemId(int position) {                                                           //Accessor method, unused but required
            return position;
    }

    public class Holder {                                                                           //The Holder class and its variable declarations
        TextView os_text;
        ImageView os_img;
        ImageView os_war;
    }

    //    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {                   //Method that returns a View
        Holder holder = new Holder();                                                               //Declare and initialize a new variable of type Holder, named "holder"
        View rowView;                                                                               //Declare a new view, "rowView"
        rowView = inflater.inflate(R.layout.grid_layout, null);                                //Initializes rowVew with inflated "inflater"


        holder.os_text = rowView.findViewById(R.id.os_texts);                                       //Initializes the "holder" variable with the corresponding View (TextView, ImageView)
        holder.os_img = rowView.findViewById(R.id.os_images);
        holder.os_text.setText(result[position]);                                                   //Sets the text of the TextView "os_text" to the String at "position" in the "result" array

        holder.os_text.setTextSize(14);                                                             //Change the TextView text attributes (size, padding, etc.)
        holder.os_text.setPadding(8, 5, 0, 5);
        holder.os_text.setTypeface(null, Typeface.NORMAL);
        holder.os_text.setTextColor(Color.parseColor("#000000"));

        holder.os_img.setImageResource(imageId[position]);                                          //Set image resource with the ID

        holder.os_war = rowView.findViewById(R.id.os_warnings);
        holder.os_war.setImageResource(R.mipmap.exclamation_mark);
        holder.os_war.setVisibility(View.INVISIBLE);


        if (imageWar != null) {
            if (imageWar[position]) {
                holder.os_war.setVisibility(View.VISIBLE);
            }
        }

        rowView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                imageWar = null;
                AddActivity.entry = result[position];
                onClickE(result[position], imageId[position], position);
                Toast.makeText(context, "You Clicked " + result[position], Toast.LENGTH_SHORT).show();
            }
        });

        return rowView;
    }

    public void onClickE(String name, int imageId, int position) {
        DescriptionOverlay.phName = name;
        DescriptionOverlay.imageId = imageId;
        DescriptionOverlay.position = position;
        DescriptionOverlay.addOrEdit2 = addOrEdit;
        if (addOrEdit == false) {
            DescriptionOverlay.ph = (Entries) Home.myEntries.get(position);
        }
        Intent intent = new Intent(context, AddActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
