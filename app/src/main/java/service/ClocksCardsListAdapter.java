package service;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bulutsoft.attendance.R;

import java.time.Clock;
import java.util.List;

public class ClocksCardsListAdapter extends BaseAdapter {

    private Context context;
    private List<ClockData> dataList;

    public ClocksCardsListAdapter(Context context, List<ClockData> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Implement your logic to create the view for each item
        // Use convertView for recycling views for better performance

        LayoutInflater inflater = LayoutInflater.from(context);
        View cardView = inflater.inflate(R.layout.list_item_card_layout, parent, false);

        TextView titleTextView = cardView.findViewById(R.id.cardTitle);
        TextView contentTextView = cardView.findViewById(R.id.cardContent);

        ClockData data = dataList.get(position);
        titleTextView.setText(data.getTitle());
        contentTextView.setText(data.getContent());

        return cardView;
    }
}