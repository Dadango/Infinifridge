package chris.infinifridge;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class GridAdapter extends BaseAdapter {

    String[] result;
    Context context;
    int[] imageId;
    boolean addOrEdit; //false = edit
    private static LayoutInflater inflater = null;

    public GridAdapter(AddActivity mainActivity, String[] entryName, int[] entryImages) {
        result = entryName;
        context = mainActivity;
        imageId = entryImages;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        addOrEdit = true;

    }

    public GridAdapter(Home mainActivity, String[] entryName, int[] entryImages) {
        result = entryName;
        context = mainActivity;
        imageId = entryImages;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        addOrEdit = false;
    }

    //    @Override
    public int getCount() {
        return result.length;
    }

    //@Override
    public Object getItem(int position) {
        return position;
    }

    //  @Override
    public long getItemId(int position) {
        return position;
    }

    public class Holder {
        TextView os_text;
        ImageView os_img;
    }

    //    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder = new Holder();
        View rowView;

        rowView = inflater.inflate(R.layout.grid_layout, null);
        holder.os_text = rowView.findViewById(R.id.os_texts);
        holder.os_img = rowView.findViewById(R.id.os_images);
        holder.os_text.setText(result[position]);
        holder.os_img.setImageResource(imageId[position]);

        rowView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
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
            DescriptionOverlay.ph = (Entries)Home.myEntries.get(position);
        }
        Intent intent = new Intent(context, AddActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
