package org.skillbill.dao;

import java.util.List;


/**
 * Interface für das EntityDao
 * @author Matthias van Dijk
 *
 */
public interface EntityDao<E> {
	
	E persist(E e) throws Exception;
	
	void merge(E e) throws Exception;

	void remove(Object id) throws Exception;
	
	E findById(Object id) throws Exception;
	
	List<E> findAll() throws Exception;
	
	List<E> findByProperty(String prop, Object val) throws Exception;
	
	List<E> findInRange(int firstResult, int maxResults) throws Exception;
	
	long count() throws Exception;
	
	
}
