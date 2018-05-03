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
        holder.os_war = rowView.findViewById(R.id.os_warnings);

        holder.os_text.setText(result[position]);                                                   //Sets the text of the TextView "os_text" to the String at "position" in the "result" array

        holder.os_text.setTextSize(14);                                                             //Change the TextView text attributes (size, padding, etc.)
        holder.os_text.setPadding(8, 5, 0, 5);
        holder.os_text.setTypeface(null, Typeface.NORMAL);
        holder.os_text.setTextColor(Color.parseColor("#000000"));

        holder.os_img.setImageResource(imageId[position]);                                          //Set image resource with the ID from the array

        holder.os_war.setImageResource(R.mipmap.exclamation_mark);                                  //Set image resource with the ID from the array
        holder.os_war.setVisibility(View.INVISIBLE);                                                //Set the image view invisible


        if (imageWar != null) {                                                                     //if imageWar (boolean array) is not null (exists)
            if (imageWar[position]) {                                                               //if the current boolean in the array at "position" is true
                holder.os_war.setVisibility(View.VISIBLE);                                          //Sets the image view visible
            }
        }

        rowView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {                                                          //Make a click listener
                imageWar = null;                                                                    //Set imageWar to null
                AddActivity.entry = result[position];                                               //Set entry in AddActivity to the one from the array at position "position"
                onClickE(result[position], imageId[position], position);                            //Run the method using, the name and id on the current "position", and the "position" as an integer
                Toast.makeText(context, "You Clicked " + result[position], Toast.LENGTH_SHORT).show();  //Make a short toast, that tells the user what they clicked. Probably remove
            }
        });

        return rowView;                                                                             //Return the rowView
    }

    public void onClickE(String name, int imageId, int position) {                                  //Method that requires a name, id and position integer, returns nothing
        DescriptionOverlay.phName = name;                                                           //Sets the DescriptionOverlay variables to the selected's values
        DescriptionOverlay.imageId = imageId;
        DescriptionOverlay.position = position;
        DescriptionOverlay.addOrEdit2 = addOrEdit;
        if (addOrEdit == false) {                                                                   //If user is editing an existing entry
            DescriptionOverlay.ph = (Entries) Home.myEntries.get(position);                         //Set the placeholder Entry in DescriptionOverlay to the Entry from the home page on the same "position"
        }
        Intent intent = new Intent(context, AddActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);                                                              //Start the AddActivity
    }
}
