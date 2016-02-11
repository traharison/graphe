package com.tolotra.algo.dao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Example;
import org.springframework.cache.annotation.Cacheable;

import com.tolotra.algo.dbobject.*;

public class HibernateDao {
	private SessionFactory sessionFactory;
	
	public HibernateDao(){
		sessionFactory = new Configuration().configure().buildSessionFactory();
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public void ajout(BaseModel m) throws Exception{
		Session sess = null;
		Transaction tr =null;
		try {
			sess = getSessionFactory().openSession();
			tr= sess.beginTransaction();		
			sess.save(m);		
			tr.commit();
		} catch (Exception e) {
			if(tr!=null)
				tr.rollback();
			throw e;
		}finally{
			if(sess!=null)
				sess.close();
		}
	}
	
	public void effacer(BaseModel m) throws Exception{
		Session sess = null;
		Transaction tr =null;
		try {
			System.out.println("delete");
			sess = getSessionFactory().openSession();
			tr= sess.beginTransaction();		
			sess.delete(m);		
			tr.commit();
			System.out.println("fin delete");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			if(tr!=null)
				tr.rollback();
			throw e;
		}finally{
			if(sess!=null)
				sess.close();
		}
	}
	
	public void modifier(BaseModel m) throws Exception{
		Session sess = null;
		Transaction tr =null;
		try {
			sess = getSessionFactory().openSession();
			tr= sess.beginTransaction();		
			sess.update(m);		
			tr.commit();
		} catch (Exception e) {
			if(tr!=null)
				tr.rollback();
			throw e;
		}finally{
			if(sess!=null)
				sess.close();
		}
	}
	
	public List rechercher(BaseModel e) throws Exception {
		Session sess = null;
		List<BaseModel> lst = null;
		try
		{
			sess = getSessionFactory().openSession();
		    if(e.getId()!=null && e.getId().intValue()>0)
		    {
		        e= (BaseModel) sess.get(e.getClass(),e.getId());//(BaseModel) sess.load(e.getClass(),e.getId());
		        lst = new ArrayList<BaseModel>();
		        lst.add(e);
		        return lst;
		    }else
		    {
		        Example example = Example.create(e)
		                .excludeZeroes() //exclude null- or zero-valued properties
		                .ignoreCase() //perform case insensitive string comparisons
		                .enableLike(); //use like for string comparisons
		        Criteria criteria = sess.createCriteria(e.getClass());
		        criteria.add(example);
		        return criteria.list();
		    }
		}
		catch(Exception ex)
		{
			throw ex;
		}
		finally{
			if(sess!=null)
				sess.close();
		}
	}
	
}


