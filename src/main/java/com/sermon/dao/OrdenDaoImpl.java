package com.sermon.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.sermon.model.Orden;

@Repository
public class OrdenDaoImpl implements OrdenDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public List<Orden> getOrden() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Orden> cq = builder.createQuery(Orden.class);
        Root<Orden> root = cq.from(Orden.class);
        cq.select(root);
        return entityManager.createQuery(cq).getResultList();
    }
}
