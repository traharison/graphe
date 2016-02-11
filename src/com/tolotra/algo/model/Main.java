package com.tolotra.algo.model;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Tolotra on 01/04/2015.
 */
public class Main {

    public static void main(String[] arg)
    {
        Graphe grph = new Graphe();
        for(int i=0;i<6;i++)
        {
            grph.addNoeud();
        }
        grph.addArcCapacite(1,2,4);
        grph.addArcCapacite(1,3,1);
        grph.addArcCapacite(2,4,4);
        grph.addArcCapacite(2,5,5);
        grph.addArcCapacite(3,2,2);
        grph.addArcCapacite(3,4,1);
        grph.addArcCapacite(3,6,5);
        grph.addArcCapacite(4,5,3);
        grph.addArcCapacite(5,6,1);
        /*grph.addArc(1,3);
        grph.addArc(2,1);
        grph.addArc(2,4);
        grph.addArc(2,5);
        grph.addArc(3,6);
        grph.addArc(3,2);
        grph.addArc(4,5);
        grph.addArc(6, 5);*/
        grph.aff();

        int[] bfs = grph.parcoursBFS();
        System.out.println("Parcours en largeur BFS: "+Arrays.toString(bfs));
        int[] dfs = grph.parcoursDFS();
        System.out.println("Parcours en profondeur DFS: " + Arrays.toString(dfs));

        double[] coutmin = grph.getVectCoutMinIssuDe(grph.getNoeudByNum(1));
        System.out.println("Cout min: "+Arrays.toString(coutmin));
        List<Integer>[] pred = grph.getVectPrecDuCheminMinIssuDe(grph.getNoeudByNum(1));
        System.out.println("Pred: ");
        for(List<Integer> list:pred)
        {
            System.out.print(Arrays.toString(list.toArray())+"\t");
        }
        System.out.println();

        List<Integer> chemin = grph.getUnCheminMinEntre(grph.getNoeudByNum(1),grph.getNoeudByNum(5));
        double[][] matcoutmin = grph.matriceCoutMin();

        List<Integer>[] predMax = grph.getVectPrecDuCheminMaxIssuDe(grph.getNoeudByNum(1));
        System.out.println("Pred Max: ");
        for(List<Integer> list:predMax)
        {
            System.out.print(Arrays.toString(list.toArray())+"\t");
        }

        /*Noeud n = grph.getNoeudByNum(4);
        Integer[] succ = grph.listSucc(n);
        grph.affTab(succ,"successeur");
        Integer[] pred = grph.listPred(n);
        grph.affTab(pred,"predecesseur");*/
        grph.affMat(grph.matAdj(), "adjacence");
        grph.affMat(grph.matInc(),"incidence");

        double[][] m = new double[4][4];
        for(int i=0;i<m.length;i++)
        {
            for(int j=0;j<m.length;j++)
            {
                m[i][j] = 0;
            }
        }
        String test = Arrays.toString(m[1]);
        System.out.print(test);
        //grph.supprimerNoeud(grph.getNoeudByNum(2));
        //grph.aff();
    }
}
