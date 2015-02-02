package com.essths.tuniRest.Restaurant.VisiteVirtuelle;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;

import com.carouseldemo.main.R;

public class VisiteVirtuelleAdapter extends BaseAdapter {

	private ArrayList<VisiteVirtuelle> listData;

	private LayoutInflater layoutInflater;

	public VisiteVirtuelleAdapter(Context context,
			ArrayList<VisiteVirtuelle> listData) {
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

			holder.image = (Gallery) convertView.findViewById(R.id.Gallery);
			// holder.nbreplace = (TextView)
			// convertView.findViewById(R.id.id_t2);

			convertView.setTag(holder);
		} else {

			holder = (ViewHolder) convertView.getTag();
		}

		VisiteVirtuelle newsItem = (VisiteVirtuelle) listData.get(position);
		// holder.image.se
		 holder.image.setContentDescription(String.valueOf(newsItem.getImage()));
		return convertView;
	}

	static class ViewHolder {
		Gallery image;
	}
}
