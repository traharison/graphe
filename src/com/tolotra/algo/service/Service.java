package com.tolotra.algo.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Service {
	private static final Log log = LogFactory.getLog(Service.class);
	private BeanFactory factory;
	private ApplicationContext context;
	private static Service instance = null;
	private Service(){
		context=new ClassPathXmlApplicationContext(new String[]{"applicationContext.xml"});
		setFactory(context);
	}
	public static Service getInstance(){
		if(instance==null){
			instance = new Service();
			System.out.println("Instanciation");
			log.info("***************Instanciation********************");
		}
		return instance;
	}
	public Object getFactoryBean(String res) throws Exception{
		try{
			return getFactory().getBean(res);
		} catch(Exception e){
			throw e;
		}
	}
	public BeanFactory getFactory() {
		return factory;
	}
	public void setFactory(BeanFactory factory) {
		this.factory = factory;
	}
}
