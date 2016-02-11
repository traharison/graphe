package com.tolotra.algo.dbobject;

import com.tolotra.algo.model.Arc;

public class NoeudDB {
	
	int number;
	double x;
	double y;
	String couleur;
	
	public NoeudDB(int number, double x, double y, String couleur) {
		super();
		this.number = number;
		this.x = x;
		this.y = y;
		this.couleur = couleur;
	}
	
	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public String getCouleur() {
		return couleur;
	}

	public void setCouleur(String couleur) {
		this.couleur = couleur;
	}
	
	boolean estIncident(ArcDB a) // est l'origine ou l'arrivé d'un arc
    {
        if(a.getDestination() == this.getNumber() || a.getOrigine() == this.getNumber())
            return true;
        else
            return false;
    }

}
