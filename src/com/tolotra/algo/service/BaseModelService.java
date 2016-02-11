package com.tolotra.algo.service;

import com.tolotra.algo.dao.HibernateDao;
import com.tolotra.algo.dbobject.BaseModel;


public class BaseModelService {
	private HibernateDao dao = null;
	
	public BaseModelService(){}
	
	public HibernateDao getDao() {
		return dao;
	}
	public void setDao(HibernateDao dao) {
		this.dao = dao;
	}
	
	public void insert(BaseModel bm) throws Exception{
		
		try
		{
			getDao().ajout(bm);
		}
		catch(Exception e){
			throw e;
		}
	}
	
	public void modif(BaseModel bm) throws Exception{
		try
		{
			getDao().modifier(bm);
		}
		catch(Exception e){
			throw e;
		}
	}
	
	public void del(BaseModel bm) throws Exception{
		try
		{
			getDao().effacer(bm);
		}
		catch(Exception e){
			throw e;
		}
	}
	
	
}
