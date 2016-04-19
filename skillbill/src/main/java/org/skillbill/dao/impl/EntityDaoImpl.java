package org.skillbill.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javassist.bytecode.SignatureAttribute.TypeVariable;

import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.HibernateException;
import org.skillbill.dao.EntityDao;
import org.springframework.transaction.annotation.Transactional;


public class EntityDaoImpl<E>  implements EntityDao<E> {

	

	@PersistenceContext(unitName="persistenceUnit")
	protected EntityManager entityManager;
	
	protected E instance;
	private Class<E> entityClass;
	
	
	@Transactional
	public E persist(E e) throws HibernateException{     
	    getEntityManager().persist(e);
	    return e;
	}
	@Transactional
	public void merge(E e) throws HibernateException{     
	    getEntityManager().merge(e);
	}
	@Transactional
	public void remove(Object id) throws Exception{     
	    getEntityManager().remove((E)getEntityManager().find(getEntityClass(), id));
	}
	
	
	@Transactional(readOnly = true)
	public E findById(Object id) throws Exception {     
	    return (E)getEntityManager().find(getEntityClass(), id);    
	}
	
	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public List<E> findAll() throws Exception{
		String query = "Select t from " + getEntityClass().getSimpleName() + " t";
		EntityManager em = getEntityManager();
        Query q = em.createQuery(query);
        return q.getResultList();
	}

	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public List<E> findByProperty(String prop, Object val) throws Exception {
        return (List<E>) getEntityManager().createQuery("select x from " + getEntityClass().getSimpleName() + " x where x." + prop + " = ?1").setParameter(1, val).getResultList();
    }
	
	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public List<E> findInRange(int firstResult, int maxResults) throws Exception {
        return getEntityManager().createQuery("Select t from " + getEntityClass().getSimpleName() + " t").setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
	}

	@Transactional(readOnly = true)
	public long count() throws Exception {
        return (Long) getEntityManager().createQuery("Select count(t) from " + getEntityClass().getSimpleName() + " t").getSingleResult();
	}
	
	
	public EntityManager getEntityManager() {
	    return entityManager;
	}
	    
	    
	    
	public void setEntityManager(EntityManager entityManager) throws Exception{
	    this.entityManager = entityManager;
	}

	    @SuppressWarnings("unchecked")
		public Class<E> getEntityClass() throws Exception{      

	    	if (entityClass == null) {
	            Type type = getClass().getGenericSuperclass();
	          if (type instanceof  ParameterizedType) 
	          {
	              ParameterizedType paramType = (ParameterizedType) type;
	              if (paramType.getActualTypeArguments().length == 2) {
	                    if (paramType.getActualTypeArguments()[1] instanceof  TypeVariable) {
	                       throw new IllegalArgumentException(
	                          "Can't find class using reflection");
	                   }
	                    else {
	                       entityClass = (Class<E>) paramType.getActualTypeArguments()[1];
	                  }
	               } else {
	                  entityClass = (Class<E>) paramType.getActualTypeArguments()[0];
	                }
	           } else {
	              throw new Exception("Can't find class using reflection");
	          }
	        }
	       return entityClass;
	   }

	

}
