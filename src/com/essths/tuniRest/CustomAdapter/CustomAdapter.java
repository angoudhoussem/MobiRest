package com.essths.tuniRest.CustomAdapter;

import java.util.ArrayList;

import com.carouseldemo.main.R;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter {

	private ArrayList<Restaurant> listData;

	private LayoutInflater layoutInflater;

	public CustomAdapter(Context context, ArrayList<Restaurant> listData) {
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

			convertView = layoutInflater.inflate(R.layout.affichageitem, null);

			holder = new ViewHolder();

			holder.nom_rest = (TextView) convertView.findViewById(R.id.id_t1);
			holder.adresse = (TextView) convertView.findViewById(R.id.id_t2);
			//holder.info=(TextView)convertView.findViewById(R.id.id_info1);
			holder.image = (ImageView) convertView.findViewById(R.id.img);
			

			convertView.setTag(holder);
		} else {

			holder = (ViewHolder) convertView.getTag();
		}

		Restaurant newsItem = (Restaurant) listData.get(position);
		holder.nom_rest.setText(newsItem.getNom_rest());
		//holder.info.setText(String.valueOf(newsItem.getInfo()));
		holder.adresse.setText(String.valueOf(newsItem.getAdresse()));
		holder.image.setImageBitmap(ConvertImageFromStringToBitmap.convert(newsItem.getImage()));
		return convertView;
	}

	static class ViewHolder {
		TextView nom_rest;
		TextView adresse;
		ImageView image;

	}
}
