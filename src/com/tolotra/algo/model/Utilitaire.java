package com.tolotra.algo.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Tolotra on 13/04/2015.
 */
public class Utilitaire {

    public static void initialiseVectCap(int indNoeud, double[] vect)
    {
        for(int i=1;i<=vect.length;i++){
            vect[i-1]=Double.POSITIVE_INFINITY;
        }
        vect[indNoeud-1]=0.0;
    }
    public static List<Integer> getNumDesNoeud(Set<Noeud> noeuds)
    {
        List<Integer> liste=new ArrayList<Integer>();
        for(Noeud noeud: noeuds)
            liste.add(noeud.getNum());
        return liste;
    }
    public static void initialiseTabPred(int indNoeud, List<Integer>[] tab)
    {
        for(int i=1;i<=tab.length;i++){
            tab[i-1]=new ArrayList<Integer>();
        }
        tab[indNoeud-1].add(0);
    }

    public static Integer choisirSommetACapMin(List<Integer> atraite, double[] cmin)
    {
        Integer indiceNoeud=atraite.get(0);
        double min=cmin[indiceNoeud-1];
        for(int j=1; j<atraite.size(); j++){
            if(cmin[atraite.get(j)-1]<min){
                indiceNoeud=atraite.get(j);
                min=cmin[indiceNoeud-1];
            }
        }
        atraite.remove(indiceNoeud);
        return indiceNoeud;
    }
    public static void afficheTabListe(List<Integer>[] tab)
    {
        String str="Vect pred issu de 1: [";
        for(int i=0;i<tab.length;i++){
            str+=tab[i].toString()+", ";
        }
        str+="]";
        System.out.println(str);
    }

    public List<List<Integer>[]> getDecompDePredIssuDe(List<Integer>[] pred)
    {
        int ordre=pred.length;
        List<List<Integer>[]> ret=new ArrayList<List<Integer>[]>();
        ret.add(pred);
        /*for(int k=0; k<pred[1].size();k++)
            ret.add(new ArrayList(pred[1].get(k)));*/
        for(int i=0;i<pred.length;i++){
            List<List<Integer>[]> newret=new ArrayList<List<Integer>[]>();
            for(List<Integer>[] elem: ret){
                List<Integer>[] newelem;
                if(pred[i].isEmpty()){
                    newelem=new ArrayList[ordre];
                    System.arraycopy(elem, 0, newelem, 0, ordre);
                    newret.add(newelem);
                }else{
                    for(Integer noeud:pred[i]){
                        newelem=new ArrayList[ordre];
                        System.arraycopy(elem, 0, newelem, 0, ordre);
                        newelem[i].clear();
                        newelem[i].add(noeud);
                        newret.add(newelem);
                    }
                }
            }
            ret=newret;
        }
        return ret;
    }
    public static void affiche(List<List<Integer>[]> liste){
        for(int i=0; i<liste.size();i++){
            System.out.println(liste.get(i).toString());
        }
    }
    public static List<Integer> getChemin(int i, int j, List<Integer>[] liste)
    {

        if(liste[j].isEmpty())
            return null;
        else{
            List<Integer> ret=new ArrayList<Integer>();
            while(!liste[j].contains(i)){
                ret.add(liste[j].get(0));
            }
            return ret;
        }
    }

    public static List<Chemin> getListChemin(int i, int j, List<Integer>[] pred)    {
        List<Chemin> listeChemin=new ArrayList<Chemin> ();
        if(pred[j-1].isEmpty() || pred[j-1].contains(0)){
            Chemin chemin=new Chemin();
            chemin.addBefore(i);
            listeChemin.add(chemin);
        }else{
            //System.out.println( j+" -> "+ pred[j-1].toString());
            for(Integer noeud: pred[j-1]){
                List<Chemin> listec=getListChemin(i, noeud.intValue(), pred);
                for(Chemin chem:listec){
                    chem.addLast(j);
                }
                listeChemin.addAll(listec);
            }
        }
        return listeChemin;
    }

    public static void afficheListeC(List<Chemin> liste)
    {
        for(Chemin chem:liste)
            chem.affiche();
    }

}
