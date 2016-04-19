package org.skillbill.dao.impl;



import org.skillbill.common.Mitarbeiter;
import org.skillbill.dao.MitarbeiterDao;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class MitarbeiterDaoImpl extends EntityDaoImpl<Mitarbeiter> implements MitarbeiterDao {

	
}

