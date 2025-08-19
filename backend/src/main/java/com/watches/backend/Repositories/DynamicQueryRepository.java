package com.watches.backend.Repositories;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class DynamicQueryRepository<T>{

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public T save(T entity){
        return em.merge(entity);
    }

    public void delete(T entity){em.remove(entity);}

    public List<T> findAll(Class<T> entityClass){
        String sql = "FROM "+entityClass.getSimpleName();
        return em.createQuery(sql, entityClass).getResultList();
    }

    public Set<String> getDistinctValues(Class<T> clazz, String column) {
        String sql = String.format(
                "SELECT DISTINCT e.%s FROM %s e WHERE e.%s IS NOT NULL",
                column, clazz.getSimpleName(), column
        );
        return new HashSet<>(em.createQuery(sql).getResultList());
    }

    public String findByProductId(String field, String column, Long productId) {
        String sql = String.format(
                "SELECT p.%s.%s FROM Product p WHERE p.id = %d",
                field, column, productId
        );
        return em.createQuery(sql).getSingleResult().toString();
    }

}
