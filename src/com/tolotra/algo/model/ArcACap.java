package com.tolotra.algo.model;

import org.w3c.dom.TypeInfo;

/**
 * Created by Tolotra on 03/04/2015.
 */
public class ArcACap extends Arc {

    private double capacite;

    public ArcACap (Noeud origine,Noeud destination,double cap)
    {
        super(origine,destination);
        this.capacite = cap;
    }

    public ArcACap(int num,Noeud origine,Noeud destination,double cap) {
        super(num,origine,destination);
        this.capacite = cap;
    }

    public ArcACap(int num, Noeud origine, Noeud destination, TypeInfo info,double cap) {
        super(num, origine, destination, info);
        this.capacite = cap;
    }

    public double getCapacite() {
        return capacite;
    }

    public void setCapacite(int capacite) {
        this.capacite = capacite;
    }

    public void aff()
    {
        System.out.println(getNum()+" ( "+getOrigine().getNum()+" , "+getDestination().getNum()+" , "+getCapacite()+" )");
    }
}
