package com.tolotra.algo.model;

import java.util.*;

import com.tolotra.algo.dbobject.NoeudDB;

/**
 * Created by Tolotra on 01/04/2015.
 */
public class Graphe implements Comparator<Noeud>{
    private List<Arc> lArc;
    private List<Noeud> lNoeud;

    public Graphe() {
        this.lArc = new ArrayList<Arc>();
        this.lNoeud = new ArrayList<Noeud>();
    }

    public List<Noeud> getlNoeud() {
        return lNoeud;
    }

    public void setlNoeud(List<Noeud> lNoeud) {
        this.lNoeud = lNoeud;
    }

    public List<Arc> getlArc() {
        return lArc;
    }

    public void setlArc(List<Arc> lArc) {
        this.lArc = lArc;
    }

    public void addNoeud() {
        this.getlNoeud().add(new Noeud(this.getlNoeud().size()+1));
    }

    public void addArc(Arc a){
        if(getlNoeud().contains(a.getOrigine()) && getlNoeud().contains(a.getDestination())) {
            getlArc().add(a);
        }
    }

    public void addArc(Noeud origin,Noeud destination){
        Arc b = new Arc(this.getlArc().size()+1,origin,destination);
        this.addArc(b);
    }

    public void addArc(int i,int j){
        this.addArc(this.getNoeudByNum(i), this.getNoeudByNum(j));
    }

    public void addArcCapacite(Arc a,int cout){
        ArcACap b = (ArcACap)a;
        b.setCapacite(cout);
        if(getlNoeud().contains(a.getOrigine()) && getlNoeud().contains(a.getDestination())) {
            getlArc().add(b);
        }
    }

    public void addArcCapacite(Noeud origin,Noeud destination,int cout){
        ArcACap b = new ArcACap(this.getlArc().size()+1,origin,destination,cout);
        this.addArc(b);
    }

    public void addArcCapacite(int i,int j,int cout){
        this.addArcCapacite(this.getNoeudByNum(i), this.getNoeudByNum(j), cout);
    }

    public Noeud getNoeudByNum(int num)
    {
        for(Noeud n:getlNoeud())
            if(n.getNum() == num)
                return  n;
        return null;
    }

    public Arc getArcByNum(int num)
    {
        for(Arc a:getlArc())
            if(a.getNum() == num)
                return a;
        return null;
    }

    public int getOrdre()
    {
        return getlNoeud().size();
    }

    public boolean supprimerNoeud(Noeud n)
    {
        if(getlNoeud().remove(n))
        {
        	int nouveau = n.getNum();
            List<Arc>  arcs = getArcIncidentNoeud(n);
            getlArc().removeAll(arcs);
            if(getlNoeud().size() > 0){
            	if(getlNoeud().get(getlNoeud().size() - 1) != null && isMaxNoeud(nouveau) == false){
                	getlNoeud().get(getlNoeud().size() - 1).setNum(n.getNum());
                }
            }
            return true;
        }
        return false;
    }
    
    public boolean isMaxNoeud(int num)
	{
		for(Noeud n : getlNoeud()){
			if(n.getNum() > num)
				return false;
		}
		return true;
	}


    public List<Arc> getArcIncidentNoeud(Noeud n)
    {
        List<Arc> result = new ArrayList<Arc>();
        for(Arc a:getlArc())
            if(n.estIncident(a))
                result.add(a);
        return result;
    }

    public Integer[] listSucc(Noeud n){
        List<Integer> tab = new ArrayList<Integer>();
        List<Arc> incident = getArcIncidentNoeud(n);
        for(Arc a:incident) {
            if (n.isOrigine(a)) {
                tab.add(a.getDestination().getNum());
            }
        }
        Integer[] succ = tab.toArray(new Integer[tab.size()]);
        Arrays.sort(succ);
        return succ;
    }

    public Integer[] listPred(Noeud n){
        List<Integer> tab = new ArrayList<Integer>();
        List<Arc> incident = getArcIncidentNoeud(n);
        for(Arc a:incident) {
            if (!n.isOrigine(a)) {
                tab.add(a.getOrigine().getNum());
            }
        }
        Integer[] pred = tab.toArray(new Integer[tab.size()]);
        Arrays.sort(pred);
        return pred;
    }

    public int[][] matAdj(){
        int taille = getOrdre();
        int[][] adj = createMatrice(taille);
        int  compteur=0;
        for(Noeud n:getlNoeud())
        {
            Integer[] succ = listSucc(n);
            for(Integer num:succ)
            {
                adj[compteur][num-1] = 1;
            }
            compteur++;
        }
        return adj;
    }

    public int[][] matInc(){
        int[][] inc = matAdj();
        int  compteur=0;
        for(Noeud n:getlNoeud())
        {
            Integer[] pred = listPred(n);
            for(Integer num:pred)
            {
                inc[compteur][num-1] = -1;
            }
            compteur++;
        }
        return  inc;
    }

    public int[] parcoursBFS(){
        List<Noeud> noeuds = getlNoeud();
        int[] arbre = new int[noeuds.size()];
        List<Integer> marquer = new ArrayList<Integer>();
        arbre[0] = 0;
        marquer.add(noeuds.get(0).getNum());
        List<Integer> file = new ArrayList<Integer>();
        file.add(noeuds.get(0).getNum());
        while(file.size()>0)
        {
            int fiavina = file.remove(0);
            Integer[] succ = listSucc(getNoeudByNum(fiavina));
            for(Integer intgr:succ)
            {
                if(!marquer.contains(intgr))
                {
                    marquer.add(intgr);
                    arbre[intgr-1] = fiavina;
                    file.add(intgr);
                }
            }
        }
        //System.out.println(marquer);
        return arbre;
    }

    public int[] parcoursDFS(){
        List<Noeud> noeuds = getlNoeud();
        int[] arbre = new int[noeuds.size()];
        List<Integer> marquer = new ArrayList<Integer>();
        List<Integer> pile = new ArrayList<Integer>();
        pile.add(noeuds.get(0).getNum());
        while(pile.size()>0)
        {
            int fiavina = pile.remove(0);
            Integer[] succ = listSucc(getNoeudByNum(fiavina));
            for(Integer intgr:succ)
            {
                if(!marquer.contains(intgr))
                {
                    if(arbre[intgr-1] == 0)
                        arbre[intgr-1] = fiavina;
                    if(!pile.contains(intgr))
                        pile.add(intgr);
                }
            }
            marquer.add(fiavina);
        }
        //System.out.println(marquer);
        return arbre;
    }


    public double[][] getMatriceDeCap(){
        int ordre = getOrdre();
        double[][] m = new double[ordre][ordre];
        for(int i=1;i<=ordre;i++)
        {
            for(int j = 1;j<=ordre;j++){
                m[i-1][j-1] = this.getCapaciteArc(i,j);
            }
        }
        return m;
    }

    public List<Integer>[] getVectPrecDuCheminMinIssuDe(Noeud n)
    {
        int ordre = this.getOrdre();
        int i = n.getNum();
        //initialisation 1
        double[] cmin = new double[ordre];
        for(int j=1;j<=ordre;j++)
        {
            cmin[j-1] = this.getCapaciteArc(i,j);
        }
        System.out.println("initialisation 1 : "+Arrays.toString(cmin));
        //initialisation 2
        List<Integer> noeudsATraiter = new ArrayList<Integer>();
        for (Noeud nd:getlNoeud())
        {
            noeudsATraiter.add(nd.getNum());
        }
        //Initialisation 3
        List<Integer>[] tabPred=new ArrayList[ordre];
        for(int z = 0;z<ordre;z++)
        {
            tabPred[z] = new ArrayList<Integer>();
        }
        tabPred[i-1].add(0);
        //noeudsATraiter.remove(new Integer(i));
        System.out.println("initialisation 2 : " + noeudsATraiter.toString());

        while (!noeudsATraiter.isEmpty()) {
            //recherche du noeud a traiter a cout min
            Integer indiceNoeud = noeudsATraiter.get(0);
            double min = cmin[indiceNoeud-1];
            for(int j=1;j<noeudsATraiter.size();j++) {
                if(cmin[noeudsATraiter.get(j)-1]<min)
                {
                    indiceNoeud = noeudsATraiter.get(j);
                    min = cmin[indiceNoeud-1];
                }
            }
            noeudsATraiter.remove(indiceNoeud);
            System.out.println(Arrays.toString(noeudsATraiter.toArray()));
            //Mise a jour des capacités
            Integer[] succ = this.listSucc(this.getNoeudByNum(indiceNoeud));
            for (Integer suc: succ)
            {
                if(noeudsATraiter.contains(suc))
                {
                    double ancienCap = cmin[suc-1];
                    double newCap = cmin[indiceNoeud.intValue()-1]+this.getCapaciteArc(indiceNoeud.intValue(),suc.intValue());
                    if(newCap<ancienCap)
                    {
                        cmin[suc-1] = newCap;
                        tabPred[suc-1] = clearList(tabPred[suc-1]);
                        tabPred[suc-1].add(indiceNoeud);
                    }
                    else if(newCap == ancienCap)
                    {
                        tabPred[suc-1].add(indiceNoeud);
                    }
                }
            }
        }

        return tabPred;
    }

    public List<Integer> clearList(List<Integer> list)
    {
        for(int i=0;i<list.size();i++)
        {
            list.remove(i);
        }
        return list;
    }

    public double[] getVectCoutMinIssuDe(int j)
    {
        double[] resultat = getVectCoutMinIssuDe(getNoeudByNum(j));
        return resultat;
    }

    public double[] getVectCoutMinIssuDe(Noeud n)
    {
        int ordre = this.getOrdre();
        int i = n.getNum();
        double[] cmin = new double[ordre];
        //initialisation 1
        for(int j=1;j<=ordre;j++)
        {
            cmin[j-1] = this.getCapaciteArc(i,j);
        }

        System.out.println(Arrays.toString(cmin));
        //initialisation 2
        List<Integer> noeudsATraiter = new ArrayList<Integer>();
        for (Noeud nd:getlNoeud())
        {
            noeudsATraiter.add(nd.getNum());
        }
        noeudsATraiter.remove(new Integer(i));
        System.out.println(Arrays.toString(noeudsATraiter.toArray()));

        while (!noeudsATraiter.isEmpty()) {
            //recherche du noeud a traiter a cout min
            Integer indiceNoeud = noeudsATraiter.get(0);
            double min = cmin[indiceNoeud-1];
            for(int j=1;j<noeudsATraiter.size();j++) {
                if(cmin[noeudsATraiter.get(j)-1]<min)
                {
                    indiceNoeud = noeudsATraiter.get(j);
                    min = cmin[indiceNoeud-1];
                }
            }
            noeudsATraiter.remove(indiceNoeud);
            System.out.println(Arrays.toString(noeudsATraiter.toArray()));
            //Mise a jour des capacités
            Integer[] succ = this.listSucc(this.getNoeudByNum(indiceNoeud));
            for (Integer suc: succ)
            {
                if(noeudsATraiter.contains(suc))
                {
                    double ancienCap = cmin[suc-1];
                    double newCap = cmin[indiceNoeud.intValue()-1]+this.getCapaciteArc(indiceNoeud.intValue(),suc.intValue());
                    if(newCap<ancienCap)
                    {
                        cmin[suc-1] = newCap;
                    }
                }
            }
        }

        return cmin;
    }

    public double getCapMinEntre(Noeud n1,Noeud n2)
    {
        double[] vetapMin = getVectCoutMinIssuDe(n1);
        return vetapMin[n2.getNum()-1];
    }

    public List<Integer> getUnCheminMinEntre(Noeud n1,Noeud n2)
    {
        int num = n2.getNum();
        List<Integer>[] vetPredMin = getVectPrecDuCheminMinIssuDe(n1);
        List<Integer> unChemin = new ArrayList<Integer>();
        unChemin.add(num);
        if(vetPredMin != null)
        {
	        while(!vetPredMin[num-1].contains(n1.getNum())) {
	        	if(vetPredMin[num-1].size()!=0)
	        	{
		            num = vetPredMin[num-1].get(0);
		            unChemin.add(0,num);
	        	}
	        	else
	        		break;
	        }
	        unChemin.add(0, n1.getNum());
	        System.out.println("************Chemin ************** "+unChemin);
	        return unChemin;
        }
        else
        	return null;
    }

    //region TestIfChangeArray

    //endregion

    public List<Integer>[] getVectPrecDuCheminMaxIssuDe(Noeud n)
    {
        int ordre = this.getOrdre();
        int i = n.getNum();
        //initialisation 1
        double[] cmax = new double[ordre];
        for(int j=1;j<=ordre;j++)
        {
            cmax[j-1] = Double.NEGATIVE_INFINITY;
        }
        System.out.println("initialisation 1 : "+Arrays.toString(cmax));
        //initialisation 2
        List<Integer> noeudsATraiter = new ArrayList<Integer>();
        for (Noeud nd:getlNoeud())
        {
            noeudsATraiter.add(nd.getNum());
        }
        //Initialisation 3
        List<Integer>[] tabPred=new ArrayList[ordre];
        for(int z = 0;z<ordre;z++)
        {
            tabPred[z] = new ArrayList<Integer>();
        }
        tabPred[i-1].add(0);
        //noeudsATraiter.remove(new Integer(i));
        System.out.println("initialisation 2 : " + noeudsATraiter.toString());
        cmax[i-1] = 0;
        String ko = "change";
        List<Arc> arcList = getlArc();
        while(ko == "change")
        {
            ko="nochange";
            for(Arc a: arcList)
            {
                double maximum = cmax[a.getOrigine().getNum()-1];
                double somme = (maximum + getCapaciteArc(a.getOrigine(),a.getDestination()));
                if(maximum < somme)
                {
                    if(cmax[a.getDestination().getNum()-1]<somme){
                        cmax[a.getDestination().getNum()-1] = somme;
                        tabPred[a.getDestination().getNum()-1] = clearList(tabPred[a.getDestination().getNum()-1]);
                        tabPred[a.getDestination().getNum()-1].add(new Integer(a.getOrigine().getNum()));
                        ko = "change";
                    }
                }
            }
        }
        System.out.println("CMAX: \n"+Arrays.toString(cmax));

        return tabPred;
    }

    public Integer getNoeudSpeciale(int s,List<Integer> aTraiter,double[] cmax)
    {
        if(aTraiter.size()==cmax.length)
            return s;
        else{
            //recuperer la liste des sommets à distance finie non encore traité
            List<Integer> l=new ArrayList<Integer>();
            for(int i=0;i<cmax.length;i++)
            {

            }
            //prenez ceux qui n'on plus de prédécesseur non triaté
            //prenez le plus loin d'eux
        }
        return null;
    }

    public double[][] matriceCoutMin()
    {
        int ordre = getOrdre();
        double[][] matrice = new double[ordre][ordre];
        int i=0;
        for(Noeud n:getlNoeud()) {
            matrice[i] = getVectCoutMinIssuDe(n);
            i++;
        }
        System.out.println("Matrice cout min");
        for(int k=0;k<matrice.length;k++)
            System.out.println( Arrays.toString(matrice[k]));
        System.out.println("Fin Matrice cout min");
        return matrice;
    }

    public static List<Noeud> triCroissantNoeud(Graphe grph)
    {
        //Sorting
        Collections.sort(grph.getlNoeud(), Collections.reverseOrder(grph));
        return grph.getlNoeud();
    }
    
    @Override
	public int compare(Noeud o1, Noeud o2) {
		// TODO Auto-generated method stub
    	 return degre(o1) - degre(o2);
	}
    
    public int degre(Noeud noeud){
        return degreMoins(noeud)+degrePlus(noeud);
    }
    
    public int degrePlus(Noeud noeud){
        int num =0;
        for(Arc a : this.getlArc()){
            if (a.getOrigine()==noeud){
                num++;
            }
        }
        return num;
    }

    public int degreMoins(Noeud noeud){
        int num =0;
        for(Arc a : this.getlArc()){
            if (a.getDestination()==noeud){
                num++;
            }
        }
        return num;
    }
    
    public static List<Noeud> voisins(Graphe grph,Noeud noeud){

        List<Noeud> noeuds = new ArrayList<Noeud>();
        for (Arc a : grph.getlArc()){
            if(a.getOrigine()==noeud)
                noeuds.add(a.getDestination());
            else if (a.getDestination()==noeud)
                noeuds.add(a.getOrigine());
        }
        return noeuds;
    }
    
    //Coloration
    public static Map<Integer,Integer> algoDeWelshPowel(Graphe graphe){
    	Graphe grph = new Graphe();
    	List<Noeud> list = new ArrayList<Noeud>();
    	for(Noeud n:graphe.getlNoeud())
        {
        	list.add(new Noeud(n.getNum()));
        }
        grph.setlNoeud(list);
    	List<Arc> arc = new ArrayList<Arc>();
    	for(Arc a:graphe.getlArc())
    	{
    		if(a instanceof ArcACap)
    		{
    			ArcACap temp = (ArcACap)a;
    			arc.add(new ArcACap(grph.getNoeudByNum(temp.getOrigine().getNum()), grph.getNoeudByNum(temp.getDestination().getNum()),temp.getCapacite()));
    		}
    	}
    	grph.setlArc(arc);

        //Initialisation de la variable de résultat
        Map<Integer,Integer> map =new HashMap<Integer, Integer>();
        //Liste des sommets dans l'ordre décroissant de leur degré
        list=Graphe.triCroissantNoeud(grph);
        
        //Couleur Courante
        int couleur = 0 ;
        int i = 0;
        while (!list.isEmpty()){
            //Sommet colorier
            List<Noeud> colorier = new ArrayList<Noeud>();
            //incrémente la couleur
            couleur++;
            //Colorier s premier sommet de L avec la couleur courante
            Noeud premier = list.get(0);
            map.put(premier.getNum(), couleur);
            colorier.add(premier);
            list.remove(premier);
            //liste voisin de s
            List<Noeud> voisins = grph.voisins(grph, premier);
            for (Noeud x : list){
                if(!voisins.contains(x)) {
                    map.put(x.getNum(), couleur);
                    colorier.add(x);
                    List<Noeud> VoisinX =grph.voisins(grph,x);
                    voisins.addAll(VoisinX);
                }
            }
            // fin Pour

            //éliminer les sommet coloriés de L
            list.removeAll(colorier);
        }
        return map;
    }

    public double getCapaciteArc(Noeud n1,Noeud n2)
    {
        for(Arc a : this.getlArc())
        {
            if(a instanceof ArcACap) {

                if (n1.getNum() == a.getOrigine().getNum() && n2.getNum() == a.getDestination().getNum())
                    return ((ArcACap)a).getCapacite();
                else if(n1.getNum() == n2.getNum())
                    return 0;
            }
        }
        return Double.POSITIVE_INFINITY;
    }

    public double getCapaciteArc(int i,int j)
    {
        Noeud n1 = getNoeudByNum(i);
        Noeud n2 = getNoeudByNum(j);
        for(Arc a : this.getlArc())
        {
            if(a instanceof ArcACap) {
                if (n1.getNum() == a.getOrigine().getNum() && n2.getNum() == a.getDestination().getNum())
                    return ((ArcACap)a).getCapacite();
                else if(n1.getNum() == n2.getNum())
                    return 0;
            }
        }
        return Double.POSITIVE_INFINITY;
    }
    
    public Arc getArc(int i,int j)
    {
        Noeud n1 = getNoeudByNum(i);
        Noeud n2 = getNoeudByNum(j);
        for(Arc a : this.getlArc())
        {
            if(a instanceof ArcACap) {
                if (n1.getNum() == a.getOrigine().getNum() && n2.getNum() == a.getDestination().getNum())
                    return a;
            }
        }
        return null;
    }

    public void aff()
    {
        System.out.println("Liste Noeud:");
        for(Noeud n:getlNoeud())
        {
            n.aff();
        }
        System.out.println("Liste Arc:");
        for(Arc a:getlArc())
        {
            a.aff();
        }
    }

    private static int[][] createMatrice(int taille)
    {
        int[][] mat = new int[taille][taille];
        for(int i=0;i<taille;i++)
            for(int j=0;j<taille;j++)
                mat[i][j] = 0;
        return mat;
    }

    public void affMat(int[][] mat,String name) {
        System.out.println("********************************************");
        System.out.println("Matrice "+name+" : ");
        for(int i = 0;i<mat.length;i++)
        {
            if(i==0)
                System.out.print("     "+(i+1)+"\t");
            else
                System.out.print((i+1)+"\t");

        }
        System.out.println();
        for(int i = 0;i<mat.length;i++)
        {
            if(i==0)
                System.out.print("     -\t");
            else
                System.out.print("-\t");

        }
        System.out.println();
        for(int i=0;i<mat.length;i++)
        {
            for(int j=0;j<mat[i].length;j++)
            {
                if(j == 0)
                    System.out.print((i+1)+" | "+mat[i][j]+"\t");
                else if( j == mat[i].length-1)
                    System.out.print(mat[i][j]+" |");
                else
                    System.out.print(mat[i][j]+"\t");
            }
            System.out.println();
        }
        for(int i = 0;i<mat.length;i++)
        {
            if(i==0)
                System.out.print("     -\t");
            else
                System.out.print("-\t");

        }
        System.out.println();
        System.out.println("********************************************");
    }

    public void affTab(Integer[] list,String type)
    {
        System.out.print("Liste  "+ type +" : [");
        for (int i = 0; i < list.length; i++)
            System.out.print(" " + list[i] + " ");
        System.out.println("]");
    }

}
