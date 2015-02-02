package com.essths.tuniRest.maps;

public class Position {
	
    String Adresse;
	String  laltitude;
	 String longitude;
	 String nom;
	public Position() {
		super();
	}
	
	public Position(String adresse, String laltitude, String longitude,String nom) {
		super();
		this.Adresse = adresse;
		this.laltitude = laltitude;
		this.longitude = longitude;
		this.nom=nom;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getLaltitude() {
		return laltitude;
	}
	public void setLaltitude(String laltitude) {
		this.laltitude = laltitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getAdresse() {
		return Adresse;
	}
	public void setAdresse(String adresse) {
		Adresse = adresse;
	}
	

}
