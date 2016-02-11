package com.tolotra.algo.dbobject;

public class ArcDB {
	
	int number;
	int origine;
	int destination;
	int capacite;
	double originex;
	double destinationx;
	double originey;
	double destinationy;
	String couleur;

	public ArcDB(int number, int origine, int destination, int capacite,
			double originex, double destinationx, double originey,
			double destinationy, String couleur) {
		super();
		this.number = number;
		this.origine = origine;
		this.destination = destination;
		this.capacite = capacite;
		this.originex = originex;
		this.destinationx = destinationx;
		this.originey = originey;
		this.destinationy = destinationy;
		this.couleur = couleur;
	}

	public int getCapacite() {
		return capacite;
	}

	public void setCapacite(int capacite) {
		this.capacite = capacite;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public double getOriginex() {
		return originex;
	}

	public void setOriginex(double originex) {
		this.originex = originex;
	}

	public double getDestinationx() {
		return destinationx;
	}

	public void setDestinationx(double destinationx) {
		this.destinationx = destinationx;
	}

	public double getOriginey() {
		return originey;
	}

	public void setOriginey(double originey) {
		this.originey = originey;
	}

	public double getDestinationy() {
		return destinationy;
	}

	public void setDestinationy(double destinationy) {
		this.destinationy = destinationy;
	}

	public String getCouleur() {
		return couleur;
	}

	public void setCouleur(String couleur) {
		this.couleur = couleur;
	}

	public int getOrigine() {
		return origine;
	}

	public void setOrigine(int origine) {
		this.origine = origine;
	}

	public int getDestination() {
		return destination;
	}

	public void setDestination(int destination) {
		this.destination = destination;
	}
	
}
