package com.adalbert.JSOZ.model;

import java.io.Serializable;
import java.util.List;
import java.util.Random;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;

public class DataAccess {

	private SessionFactory sessionFactory;
	
	public void setup() {
	   	Configuration configuration = new Configuration();
	   	configuration.configure("hibernate.cfg.xml");
	   	sessionFactory = configuration.buildSessionFactory();
	}
	
	public void exit() {
    	sessionFactory.close();
    }
	
	public void save(List<ModelPersistable> objects) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		for (ModelPersistable object : objects)
			session.save(object);
		session.getTransaction().commit();
		session.close();
	}
	
	public void saveOrUpdate(List<ModelPersistable> objects) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		for (ModelPersistable object : objects)
			session.saveOrUpdate(object);
		session.getTransaction().commit();
		session.close();
	}
	 
	public ModelPersistable read(Class<? extends ModelPersistable> clazz, Serializable id) {
		Object outcome;
		Session session = sessionFactory.openSession();
		outcome = session.get(clazz, id);
	    session.close();
	    return (ModelPersistable) outcome;
	}
	
	public <T extends ModelPersistable> List<T> loadAllData(Class<T> type) {
		Session session = sessionFactory.openSession();
	    CriteriaBuilder builder = session.getCriteriaBuilder();
	    CriteriaQuery<T> criteria = builder.createQuery(type);
	    criteria.from(type);
	    List<T> data = session.createQuery(criteria).getResultList();
	    session.close();
	    return data;
	}
	
	@SuppressWarnings("deprecation")
	public <T extends ModelPersistable> List<T> loadRandomData(Class<T> type, int amount) {
		Session session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(type);
		criteria.add(Restrictions.sqlRestriction("1=1 order by rand()"));
		criteria.setMaxResults(amount);
		List<T> data = criteria.list();
		session.close();
		return data;
	}
	
	public Session getSession() {
		Session session = sessionFactory.openSession();
		return session;
	}
	
}
