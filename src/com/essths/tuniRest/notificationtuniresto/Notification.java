package com.essths.tuniRest.notificationtuniresto;

public class Notification {
	String contenu;
	String date;
	String nomRest;
	String idrest;

	public Notification(String contenu, String date, String nomRest, String idrest) {
		super();
		this.contenu = contenu;
		this.date = date;
		this.nomRest=nomRest;
		this.idrest=idrest;
	}

	public Notification() {
		super();
		// TODO Auto-generated constructor stub
	}


	public String getIdrest() {
		return idrest;
	}

	public void setIdrest(String idrest) {
		this.idrest = idrest;
	}

	

	public String getNomRest() {
		return nomRest;
	}

	public void setNomRest(String nomRest) {
		this.nomRest = nomRest;
	}

	public String getContenu() {
		return contenu;
	}

	public void setContenu(String contenu) {
		this.contenu = contenu;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}
