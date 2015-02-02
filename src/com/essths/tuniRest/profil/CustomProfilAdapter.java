package com.essths.tuniRest.profil;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.carouseldemo.main.R;

public class CustomProfilAdapter extends BaseAdapter {

	private ArrayList<Profil> listData;

	private LayoutInflater layoutInflater;

	public CustomProfilAdapter(Context context, ArrayList<Profil> listData) {
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

			convertView = layoutInflater.inflate(R.layout.itemprofil, null);

			holder = new ViewHolder();

			holder.nom = (TextView) convertView.findViewById(R.id.id_nom);
			holder.prenom = (TextView) convertView.findViewById(R.id.id_prenom);
			holder.image = (ImageView) convertView
					.findViewById(R.id.id_imgprofil);

			convertView.setTag(holder);
		} else {

			holder = (ViewHolder) convertView.getTag();
		}

		Profil news = (Profil) listData.get(position);
		holder.nom.setText(news.getNom());
		holder.prenom.setText(String.valueOf(news.getPrenom()));
		holder.image.setImageBitmap(ConvertImageFromStringToBitmap.convert(news
			.getImage()));
		return convertView;
	}

	static class ViewHolder {
		TextView nom;
		TextView prenom;
		ImageView image;

	}
}
