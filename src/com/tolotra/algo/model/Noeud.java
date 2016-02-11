package com.tolotra.algo.model;

import org.w3c.dom.TypeInfo;

/**
 * Created by Tolotra on 01/04/2015.
 */
public class Noeud {
    private Integer num;
    private TypeInfo info;

    public Noeud(Integer num, TypeInfo info) {
        super();
        this.num = num;
        this.info = info;
    }

    public Noeud(int num) {
        super();
        this.num = num;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public TypeInfo getInfo() {
        return info;
    }

    public void setInfo(TypeInfo info) {
        this.info = info;
    }

    boolean estIncident(Arc a) // est l'origine ou l'arrivé d'un arc
    {
        if(a.getDestination() == this || a.getOrigine() == this)
            return true;
        else
            return false;
    }

    boolean isOrigine(Arc a)
    {
        if(a.getOrigine() == this)
            return true;
        else
            return false;
    }

    public void aff(){
        System.out.println(getNum()+" : "+ getInfo());
    }
}
