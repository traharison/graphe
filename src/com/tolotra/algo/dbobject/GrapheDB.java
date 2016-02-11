package com.tolotra.algo.dbobject;

import java.util.ArrayList;
import java.util.List;

import com.tolotra.algo.model.Arc;
import com.tolotra.algo.model.Noeud;

public class GrapheDB {
	List<ArcDB> arc;
	List<NoeudDB> noeud;
	
	public GrapheDB() {
		super();
		this.arc = new ArrayList<ArcDB>();
		this.noeud = new ArrayList<NoeudDB>();
	}

	public List<ArcDB> getArc() {
		return arc;
	}

	public void setArc(List<ArcDB> arc) {
		this.arc = arc;
	}

	public List<NoeudDB> getNoeud() {
		return noeud;
	}

	public void setNoeud(List<NoeudDB> noeud) {
		this.noeud = noeud;
	}
	
	public ArcDB getArcByNum(int num)
	{
		for(ArcDB a : getArc())
		{
			if(a.getNumber() == num)
			{
				return a;
			}
		}
		return null;
	}
	
	public NoeudDB getNoeudByNum(int num)
	{
		for(NoeudDB n : getNoeud())
		{
			if(n.getNumber() == num)
			{
				return n;
			}
		}
		return null;
	}
	
	public boolean supprimerNoeud(int num)
	{
		return supprimerNoeud(getNoeudByNum(num));
	}
	
	public boolean supprimerNoeud(NoeudDB n)
    {
        if(getNoeud().remove(n))
        {
        	int nouveau = n.getNumber();
            List<ArcDB>  arcs = getArcIncidentNoeud(n);
            getArc().removeAll(arcs);
            if(getNoeud().size() > 0)
            {
	            if(getNoeud().get(getNoeud().size() - 1) != null && this.isMaxNoeud(nouveau) == false){
	            	int ancien = getNoeud().get(getNoeud().size() - 1).getNumber();
	            	getNoeud().get(getNoeud().size() - 1).setNumber(nouveau);
	            	for(ArcDB a : getArc()){
	                	if(a.getOrigine() == ancien){
	                		a.setOrigine(nouveau);
	                	}
	            		if(a.getDestination() == ancien){
	            			a.setDestination(nouveau);
	            		}
	                }
	            }
            }
            
            return true;
        }
        return false;
    }
	
	public boolean isMaxNoeud(int num)
	{
		for(NoeudDB n : getNoeud()){
			if(n.getNumber() > num)
				return false;
		}
		return true;
	}
	
	public List<ArcDB> getArcIncidentNoeud(NoeudDB n)
    {
        List<ArcDB> result = new ArrayList<ArcDB>();
        for(ArcDB a:getArc())
            if(n.estIncident(a))
                result.add(a);
        return result;
    }
	
}
