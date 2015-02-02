package com.essths.tuniRest.CustomAdapter;

public class Restaurant {
	String nom_rest;
	String info;
	String adresse;
	String image;
	String idrest;
	String typecuisine;

	public Restaurant() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Restaurant(String nom_rest, String info, String adresse,
			String image, String idrest, String typecuisine) {
		super();
		this.nom_rest = nom_rest;
		this.info = info;
		this.adresse = adresse;
		this.image = image;
		this.idrest = idrest;
		this.typecuisine = typecuisine;
	}

	public String getTypecuisine() {
		return typecuisine;
	}

	public void setTypecuisine(String typecuisine) {
		this.typecuisine = typecuisine;
	}

	public String getNom_rest() {
		return nom_rest;
	}

	public void setNom_rest(String nom_rest) {
		this.nom_rest = nom_rest;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getIdrest() {
		return idrest;
	}

	public void setIdrest(String idrest) {
		this.idrest = idrest;
	}

}