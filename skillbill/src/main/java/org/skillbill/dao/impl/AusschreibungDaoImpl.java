package org.skillbill.dao.service;


import org.skillbill.common.Ausschreibung;
import org.skillbill.dao.AusschreibungDao;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
@Transactional
public class AusschreibungService extends EntityService<Ausschreibung> implements AusschreibungDao {

	
}

