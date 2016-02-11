package com.tolotra.algo.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tolotra on 13/04/2015.
 */
public class Chemin {

    List<Integer> liste;

    public Chemin()
    {
        liste=new ArrayList<Integer>();
    }

    public void addBefore(Integer i)
    {
        this.liste.add(0,i);
    }
    public void addLast(Integer i)
    {
        this.liste.add(i);
    }
    public List<Integer> getChemin()
    {
        return this.liste;
    }

    public void affiche()
    {
        System.out.println(liste.toString());
    }
}
