package com.essths.tuniRest.profil;

public class Profil {
	String nom, prenom, image, email,idprofil;

	public Profil() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	public Profil(String nom, String prenom, String image, String email,
			String idprofil) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.image = image;
		this.email = email;
		this.idprofil = idprofil;
	}


	public String getIdprofil() {
		return idprofil;
	}


	public void setIdprofil(String idprofil) {
		this.idprofil = idprofil;
	}


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

}
