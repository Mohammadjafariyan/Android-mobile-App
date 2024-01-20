package clock.aut;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bulutsoft.attendance.R;
import com.bulutsoft.attendance.R;

import service.models.PersonnelClockStatusViewModel;

public class DashboardItemAdapter extends ArrayAdapter<String> {
    private final Context context;
    private PersonnelClockStatusViewModel[] values = new PersonnelClockStatusViewModel[0];

    public DashboardItemAdapter(Context context, PersonnelClockStatusViewModel[] values) {
        super(context, -1);


        this.context = context;
        this.values = values;
       /* List<String> stringList=new LinkedList<>();
        for (PersonnelClockStatusViewModel vm:values){
            stringList.add(vm.getName());
        }
        String[] str=stringList.toArray(new String[0]);*/

    }

    @Override
    public int getCount() {
        return values.length;
    }

    @Override
    public String getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.dashboard_list_view_layout, parent, false);

        TextView statusTextView = (TextView) rowView.findViewById(R.id.statusTextView);
        TextView lastInTextView = (TextView) rowView.findViewById(R.id.lastInTextView);
        TextView lastOutTextView = (TextView) rowView.findViewById(R.id.lastOutTextView);
        TextView personNameTextView = (TextView) rowView.findViewById(R.id.personNameTextView);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);

        statusTextView.setText(values[position].getStatus());
        lastInTextView.setText(values[position].getLastClockIn() + " ورود ");
        lastOutTextView.setText(values[position].getLastClockOut() + " خروج ");
        personNameTextView.setText(values[position].getName());


        if (values[position].getColor() == 2) {

            statusTextView.setTextColor(Color.GREEN);
        } else {
            statusTextView.setTextColor(Color.RED);
        }

        // change the icon for Windows and iPhone
        // String s = values[position];
       /* if (s.startsWith("iPhone")) {
            imageView.setImageResource(R.drawable.no);
        } else {
            imageView.setImageResource(R.drawable.ok);
        }*/

        if (values[position].getImage() != null) {
            Bitmap bmp = BitmapFactory.decodeByteArray
                    (values[position].getImage(), 0, values[position].getImage().length);
            // Set the Bitmap data to the ImageView
            imageView.setImageBitmap(bmp);
        } else {
            //Default
            imageView.setImageResource(R.drawable.ic_person_default_image);
        }


        return rowView;
    }
}
