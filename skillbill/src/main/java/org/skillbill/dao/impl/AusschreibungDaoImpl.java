package org.skillbill.dao.impl;


import org.skillbill.common.Ausschreibung;
import org.skillbill.dao.AusschreibungDao;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
@Transactional
public class AusschreibungDaoImpl extends EntityDaoImpl<Ausschreibung> implements AusschreibungDao {

	
}

