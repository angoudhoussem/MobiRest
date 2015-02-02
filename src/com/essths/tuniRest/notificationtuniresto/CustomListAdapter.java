package com.essths.tuniRest.notificationtuniresto;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.carouseldemo.main.R;
import com.essths.tuniRest.CustomAdapter.Restaurant;
import com.essths.tuniRest.Restaurant.Constant;

public class CustomListAdapter extends BaseAdapter {
Restaurant restaurant=Constant.r;
	private ArrayList<Notification> listData;

	private LayoutInflater layoutInflater;

	public CustomListAdapter(Context context, ArrayList<Notification> listData) {
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

			convertView = layoutInflater.inflate(R.layout.item_notification, null);

			holder = new ViewHolder();

			holder.contenu = (TextView) convertView.findViewById(R.id.id_contenu);
			holder.date = (TextView) convertView.findViewById(R.id.id_date);
			holder.nomRest=(TextView)convertView.findViewById(R.id.id_nomrest);

			convertView.setTag(holder);
		} else {

			holder = (ViewHolder) convertView.getTag();
		}

		Notification newsItem = (Notification) listData.get(position);
		holder.contenu.setText(newsItem.getContenu());
		holder.date.setText(String.valueOf(newsItem.getDate()));
		holder.nomRest.setText(String.valueOf(newsItem.getNomRest()));
		return convertView;
	}

	static class ViewHolder {
		TextView nomRest;
		TextView contenu;
		TextView date;

	}
}
