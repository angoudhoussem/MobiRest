package com.essths.tuniRest.Favoris;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.carouseldemo.main.R;

public class CustomlistAdapter extends BaseAdapter {

	private ArrayList<Reservation> listData;

	private LayoutInflater layoutInflater;

	public CustomlistAdapter(Context context, ArrayList<Reservation> listData) {
		this.listData = listData;
		layoutInflater = LayoutInflater.from(context);
	}

	public int getCount() {
		return listData.size();
	}

	public Object getItem(int position) {
		return listData.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {

			convertView = layoutInflater.inflate(R.layout.itemfavoris, null);

			holder = new ViewHolder();

			holder.date = (TextView) convertView.findViewById(R.id.id_datereservation);
			holder.nbreplace = (TextView) convertView.findViewById(R.id.id_nbreplace);

			convertView.setTag(holder);
		} else {

			holder = (ViewHolder) convertView.getTag();
		}

		Reservation newsItem = (Reservation) listData.get(position);
		holder.date.setText(String.valueOf(newsItem.getDate()));
		holder.nbreplace.setText(newsItem.getNbreplace());
		return convertView;
	}

	static class ViewHolder {
		TextView date;
		TextView nbreplace;

	}
}
