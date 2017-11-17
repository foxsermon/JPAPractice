package com.sermon.main;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.sermon.model.Orden;

public class JpaAlonePracticeMain {
    private final String PERSISTENCE_UNIT_NAME = "testPU";
    private EntityManagerFactory factory;
    private EntityManager entityManager;
    
	public static void main(String[] args) {
		JpaAlonePracticeMain jpa = new JpaAlonePracticeMain();
		//jpa.doInsert(jpa.newOrden(13));
		jpa.doBatch();
		jpa.doSelect();
	}
	
	public JpaAlonePracticeMain() {
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        entityManager = factory.createEntityManager();
	}
	
	public Orden newOrden(int id) {
		Orden orden = new Orden();
		orden.setId(id);
		orden.setDescription("Number " + orden.getId());
		orden.setCreated(new Timestamp(Calendar.getInstance().getTimeInMillis()));
		return orden;
	}

	public void doSelect() {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Orden> cq = builder.createQuery(Orden.class);
        Root<Orden> root = cq.from(Orden.class);
        cq.select(root);
        List<Orden> result = entityManager.createQuery(cq).getResultList();
        System.out.println(result.size());
	}
	
	public void doBatch() {
		for (int i=1; i<6; i++) {
			doInsert(newOrden(i));
		}
		for (int i=1; i<6; i++) {
			Orden ord = newOrden(i);
			ord.setDescription(ord.getDescription() + " again");
			doUpdate(ord);
		}
	}
	
	public void doInsert(Orden orden) {
		EntityTransaction tx = entityManager.getTransaction();
		tx.begin();
		entityManager.persist(orden);
		// No need to flush it, commit would do it
		// entityManager.flush();
		try {
			System.out.println("Sleeping " + orden.getId());
			Thread.sleep(1000 * 10);
		}catch(Exception exc) { 
			
		}
		if (orden.getId() == 3) {
			tx.rollback();
			return;
		}
		tx.commit();
	}
	
	public void doUpdate(Orden orden) {
		EntityTransaction tx = entityManager.getTransaction();
		tx.begin();
		entityManager.merge(orden);
		// No need to flush it, commit would do it
		// entityManager.flush();
		try {
			System.out.println("Sleeping again " + orden.getId());
			Thread.sleep(1000 * 10);
		}catch(Exception exc) {
			
		}
		if (orden.getId() == 2) {
			tx.rollback();
			return;
		}
		tx.commit();
	}
}
