package com.essths.tuniRest.Favoris;

public class Reservation {
	
	String date;
	String nbreplace;
	public Reservation() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Reservation(String date, String nbreplace) {
		super();
		this.date = date;
		this.nbreplace = nbreplace;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getNbreplace() {
		return nbreplace;
	}
	public void setNbreplace(String nbreplace) {
		this.nbreplace = nbreplace;
	}
	
}
