package service;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bulutsoft.attendance.R;

import java.util.List;

public class CustomSpinnerAdapter extends ArrayAdapter<String> {

    public CustomSpinnerAdapter(Context context, int resource, List<String> items) {
        super(context, resource, items);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.spinner_item, parent, false);
        }

        TextView textView = convertView.findViewById(android.R.id.text1);
        textView.setText(getItem(position));

        /*ImageView iconImageView = convertView.findViewById(R.id.custom_icon_closed);
        iconImageView.setImageResource(R.drawable.ic_custom_icon);*/

        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Use the existing getCustomView method for dropdown items
        return getCustomView(position, convertView, parent);
    }

    private View getCustomView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.spinner_item, parent, false);
        }

        TextView textView = convertView.findViewById(android.R.id.text1);
        textView.setText(getItem(position));

        /*ImageView iconImageView = convertView.findViewById(R.id.custom_icon);
        iconImageView.setImageResource(R.drawable.ic_custom_icon);
*/
        return convertView;
    }
}
