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

public class AffichAdapter extends BaseAdapter {

	private ArrayList<Commentaire> listData;

	private LayoutInflater layoutInflater;

	public AffichAdapter(Context context, ArrayList<Commentaire> listData) {
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

			convertView = layoutInflater.inflate(R.layout.itemcom, null);

			holder = new ViewHolder();

			holder.nomcom = (TextView) convertView.findViewById(R.id.textView1);
			
			

			convertView.setTag(holder);
		} else {

			holder = (ViewHolder) convertView.getTag();
		}

		Commentaire newsItem = (Commentaire) listData.get(position);
		holder.nomcom.setText(newsItem.getCom());
		
		return convertView;
	}

	static class ViewHolder {
		TextView nomcom;
		
	}
}
