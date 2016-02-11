package com.tolotra.algo.model;

import org.w3c.dom.TypeInfo;

/**
 * Created by Tolotra on 01/04/2015.
 */
public class Arc {
    private int num;
    private Noeud origine,destination;
    private TypeInfo info;

    public Arc(Noeud origine, Noeud destination) {
        this.origine = origine;
        this.destination = destination;
    }

    public Arc(int num, Noeud origine, Noeud destination) {
        this.num = num;
        this.origine = origine;
        this.destination = destination;
    }

    public Arc(int num, Noeud origine, Noeud destination, TypeInfo info) {
        this.num = num;
        this.origine = origine;
        this.destination = destination;
        this.info = info;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public TypeInfo getInfo() {
        return info;
    }

    public void setInfo(TypeInfo info) {
        this.info = info;
    }

    public Noeud getDestination() {
        return destination;
    }

    public void setDestination(Noeud destination) {
        this.destination = destination;
    }

    public Noeud getOrigine() {
        return origine;
    }

    public void setOrigine(Noeud origine) {
        this.origine = origine;
    }

    public void aff(){
        System.out.println(getNum()+" ( "+getOrigine().getNum()+" , "+getDestination().getNum()+" , "+getInfo()+" )");
    }
}
