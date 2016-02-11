package com.tolotra.algo.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.tolotra.algo.dbobject.ArcDB;
import com.tolotra.algo.dbobject.GrapheDB;
import com.tolotra.algo.dbobject.NoeudDB;
import com.tolotra.algo.model.Arc;
import com.tolotra.algo.model.ArcACap;
import com.tolotra.algo.model.Graphe;
import com.tolotra.algo.model.Noeud;

@Controller
@SessionAttributes({"grapheDB","graphe","coloration"})
public class MainController {
	@RequestMapping(value="/noeud", method=RequestMethod.GET)
	@ResponseBody 
	public List<NoeudDB> getNoeud(HttpServletResponse response,HttpServletRequest request) throws Exception{
		
		HttpSession session = request.getSession();
		response.setContentType("application/json");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "GET");
		GrapheDB grph;
		if(session.getAttribute("grapheDB")==null)
		{
			grph = new GrapheDB();
			return grph.getNoeud();
		}
		else
		{
			grph = (GrapheDB)session.getAttribute("grapheDB");
			return grph.getNoeud();
		}
	}
	
	@RequestMapping(value="/noeud/add", method=RequestMethod.POST)
	@ResponseBody 
	public List<NoeudDB> addNoeud(HttpServletResponse response,HttpServletRequest request,
							@RequestParam(value="number") String number,
							@RequestParam(value="x") String x,
							@RequestParam(value="y") String y,
							@RequestParam(value="couleur") String couleur) throws Exception{
		HttpSession session = request.getSession();
		response.setContentType("application/json");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST");
		Noeud nd = new Noeud(Integer.parseInt(number));
		NoeudDB noeud = new NoeudDB(Integer.parseInt(number),Double.parseDouble(x),Double.parseDouble(y),couleur);
		GrapheDB grph;
		Graphe graphe;
		if(session.getAttribute("grapheDB")==null)
		{
			grph = new GrapheDB();
			grph.getNoeud().add(noeud);
			session.setAttribute("grapheDB", grph);
			graphe = new Graphe();
			graphe.getlNoeud().add(nd);
			session.setAttribute("graphe", graphe);
		}
		else
		{
			grph = (GrapheDB)session.getAttribute("grapheDB");
			grph.getNoeud().add(noeud);
			session.setAttribute("grapheDB", grph);
			graphe = (Graphe)session.getAttribute("graphe");
			graphe.getlNoeud().add(nd);
			session.setAttribute("graphe", graphe);
		}
		return grph.getNoeud();
	}
	
	@RequestMapping(value="/noeud/del/{num}", method=RequestMethod.DELETE)
	@ResponseBody
	public String delNoeud(HttpServletResponse response,HttpServletRequest request,@PathVariable String num){
		HttpSession session = request.getSession();
		if(session.getAttribute("grapheDB")==null)
		{
			return null;
		}else{
			Graphe graphe = (Graphe)session.getAttribute("graphe");
			boolean result = graphe.supprimerNoeud(graphe.getNoeudByNum(Integer.parseInt(num)));
			if(result)
			{
				session.setAttribute("graphe", graphe);
				GrapheDB grph = (GrapheDB)session.getAttribute("grapheDB");
				grph.supprimerNoeud(Integer.parseInt(num));
				session.setAttribute("grapheDB", grph);
				return "ok";
			}
			return "not ok";
		}
	}
	
	@RequestMapping(value="/arc", method=RequestMethod.GET)
	@ResponseBody 
	public List<ArcDB> getArc(HttpServletResponse response,HttpServletRequest request) throws Exception{
		
		HttpSession session = request.getSession();
		response.setContentType("application/json");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "GET");
		GrapheDB grph;
		if(session.getAttribute("grapheDB")==null)
		{
			grph = new GrapheDB();
			return grph.getArc();
		}
		else
		{
			grph = (GrapheDB)session.getAttribute("grapheDB");
			return grph.getArc();
		}
	}
	
	@RequestMapping(value="/arc/add", method=RequestMethod.POST)
	@ResponseBody 
	public List<ArcDB> addArc(HttpServletResponse response,HttpServletRequest request,
							@RequestParam(value="number") String number,
							@RequestParam(value="origine") String origine,
							@RequestParam(value="destination") String destination,
							@RequestParam(value="originex") String originex,
							@RequestParam(value="originey") String originey,
							@RequestParam(value="destinationx") String destinationx,
							@RequestParam(value="destinationy") String destinationy,
							@RequestParam(value="couleur") String couleur) throws Exception{
		HttpSession session = request.getSession();
		response.setContentType("application/json");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST");
		GrapheDB grph;
		ArcDB arcdb = new ArcDB(Integer.parseInt(number), Integer.parseInt(origine), Integer.parseInt(destination), 0, Double.parseDouble(originex), Double.parseDouble(destinationx), Double.parseDouble(originey), Double.parseDouble(destinationy), couleur);	
		if(session.getAttribute("grapheDB")==null)
		{
			grph = new GrapheDB();
			grph.getArc().add(arcdb);
			session.setAttribute("grapheDB", grph);
		}
		else
		{
			grph = (GrapheDB)session.getAttribute("grapheDB");
			grph.getArc().add(arcdb);
			session.setAttribute("grapheDB", grph);
			Graphe graphe = (Graphe)session.getAttribute("graphe");
			ArcACap arc = new ArcACap(Integer.parseInt(number),graphe.getNoeudByNum(Integer.parseInt(origine)),graphe.getNoeudByNum(Integer.parseInt(destination)),0);
			graphe.addArc(arc);
			session.setAttribute("graphe", graphe);
		}
		return grph.getArc();
	}
	
	@RequestMapping(value="/arc/del/{num}", method=RequestMethod.DELETE)
	@ResponseBody
	public String delArc(HttpServletResponse response,HttpServletRequest request,@PathVariable String num){
		HttpSession session = request.getSession();
		String result = "ok";
		if(session.getAttribute("grapheDB")==null)
		{
			return null;
		}else{
			Graphe graphe = (Graphe)session.getAttribute("graphe");
			Arc a = graphe.getArcByNum(Integer.parseInt(num));
			if(a!=null)
			{
				graphe.getlArc().remove(a);
				session.setAttribute("graphe", graphe);
				GrapheDB grph = (GrapheDB)session.getAttribute("grapheDB");
				ArcDB adb = grph.getArcByNum(Integer.parseInt(num));
				grph.getArc().remove(adb);
				session.setAttribute("grapheDB", grph);
				return result;
			}
			return "not ok";
		}
	}
	
	@RequestMapping(value="/matrice/adjacence", method=RequestMethod.GET)
	@ResponseBody
	public int[][] matriceAdj(HttpServletResponse response,HttpServletRequest request){
		HttpSession session = request.getSession();
		int[][] adj;
		if(session.getAttribute("grapheDB")==null)
		{
			return null;
		}else{
			Graphe graphe = (Graphe)session.getAttribute("graphe");
			adj = graphe.matAdj();
		}
		return adj;
	}
	
	@RequestMapping(value="/matrice/incidence", method=RequestMethod.GET)
	@ResponseBody
	public int[][] matriceIncidence(HttpServletResponse response,HttpServletRequest request){
		HttpSession session = request.getSession();
		int[][] incidence;
		if(session.getAttribute("grapheDB")==null)
		{
			return null;
		}else{
			Graphe graphe = (Graphe)session.getAttribute("graphe");
			incidence = graphe.matInc();
		}
		return incidence;
	}
	
	@RequestMapping(value="/matrice/cout", method=RequestMethod.GET)
	@ResponseBody
	public double[][] matriceCout(HttpServletResponse response,HttpServletRequest request){
		HttpSession session = request.getSession();
		double[][] cout;
		if(session.getAttribute("grapheDB")==null)
		{
			return null;
		}else{
			Graphe graphe = (Graphe)session.getAttribute("graphe");
			cout = graphe.getMatriceDeCap();
		}
		return cout;
	}
	
	@RequestMapping(value="/matrice/cout/change", method=RequestMethod.GET)
	@ResponseBody
	public String changeCout(HttpServletResponse response,HttpServletRequest request,
			@RequestParam(value="cap") String cap,
			@RequestParam(value="origine") String origine,
			@RequestParam(value="destination") String destination){
		HttpSession session = request.getSession();
		if(session.getAttribute("grapheDB")==null)
		{
			return null;
		}else{
			Graphe graphe = (Graphe)session.getAttribute("graphe");
			int num = graphe.getArc(Integer.parseInt(origine), Integer.parseInt(destination)).getNum();
			ArcACap acp = (ArcACap)graphe.getArc(Integer.parseInt(origine), Integer.parseInt(destination));
			acp.setCapacite(Integer.parseInt(cap));
			session.setAttribute("graphe", graphe);
			GrapheDB grph = (GrapheDB)session.getAttribute("grapheDB");
			grph.getArcByNum(num).setCapacite(Integer.parseInt(cap));
			session.setAttribute("grapheDB", grph);
		}
		return "ok";
	}
	
	@RequestMapping(value="/coloration", method=RequestMethod.GET)
	@ResponseBody
	public List<NoeudDB> coloration(HttpServletResponse response,HttpServletRequest request){
		HttpSession session = request.getSession();
		if(session.getAttribute("grapheDB")==null)
		{
			return null;
		}else{
			String[] couleur = {"blue","green","yellow","pink","red","orange","purple","brown"};
			/*Colotartion*/
			Graphe graphe = (Graphe)session.getAttribute("graphe");
			Graphe temp = graphe;
			Map<Integer,Integer> mapcolor = Graphe.algoDeWelshPowel(temp);
			GrapheDB grph = (GrapheDB)session.getAttribute("grapheDB");
			for(NoeudDB ndb : grph.getNoeud())
			{
				int color = mapcolor.get(ndb.getNumber());
				ndb.setCouleur(couleur[color-1]);
			}
			return grph.getNoeud();
		}
	}
	
	@RequestMapping(value="/chemin/min", method=RequestMethod.POST)
	@ResponseBody
	public List<ArcDB> cheminMinEntre(HttpServletResponse response,HttpServletRequest request,
			@RequestParam(value="origine") String origine,
			@RequestParam(value="destination") String destination){
		HttpSession session = request.getSession();
		if(session.getAttribute("grapheDB")==null)
		{
			return null;
		}else{
			Graphe graphe = (Graphe)session.getAttribute("graphe");
			List<Integer> chemin_min = graphe.getUnCheminMinEntre(graphe.getNoeudByNum(Integer.parseInt(origine)),graphe.getNoeudByNum(Integer.parseInt(destination)));
			GrapheDB grph = (GrapheDB)session.getAttribute("grapheDB");
			List<ArcDB> list = new ArrayList<ArcDB>();
			for(ArcDB a:grph.getArc())
			{
				ArcDB temp = new ArcDB(a.getNumber(),a.getOrigine(),a.getDestination(),a.getCapacite(),a.getOriginex(),a.getDestinationx(),a.getOriginey(),a.getDestinationy(),a.getCouleur());
				list.add(temp);
			}
			int i=0;
			for(ArcDB adb : list)
			{
				if(i < chemin_min.size()-1)
				{
					if(adb.getOrigine() == chemin_min.get(i) && adb.getDestination() == chemin_min.get(i+1)){
						adb.setCouleur("white");
						i++;
					}
				}
			}
			return list;
		}
	}
	
}
