package unl.edu.ec.fieldPal.service;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import java.util.List;
import java.util.Map;

/**
 * Servicio CRUD genérico compartido por todos los Repository de FieldPal.
 * Basado en el patrón "Generic DAO" de Adam Bien, igual al usado en el
 * proyecto de referencia del curso (jbrew-web), simplificado a lo que
 * FieldPal realmente necesita.
 */
@Stateless
public class CrudGenericService {

    @PersistenceContext(unitName = "fieldPalPU")
    private EntityManager em;

    public <T> T create(T entity) {
        em.persist(entity);
        em.flush();
        return entity;
    }

    public <T> T update(T entity) {
        T merged = em.merge(entity);
        em.flush();
        return merged;
    }

    public <T> T find(Class<T> type, Object id) {
        return em.find(type, id);
    }

    public <T> void delete(Class<T> type, Object id) {
        T ref = em.find(type, id);
        if (ref != null) {
            em.remove(ref);
        }
    }

    public <T> List<T> findWithQuery(String jpql, Class<T> type) {
        return em.createQuery(jpql, type).getResultList();
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> findWithQuery(String jpql, Class<T> type, Map<String, Object> parameters) {
        Query query = em.createQuery(jpql, type);
        parameters.forEach(query::setParameter);
        return query.getResultList();
    }

    public <T> T findSingleResultOrNull(String jpql, Class<T> type, Map<String, Object> parameters) {
        List<T> results = findWithQuery(jpql, type, parameters);
        if (results.isEmpty()) return null;
        if (results.size() == 1) return results.get(0);
        throw new NonUniqueResultException("Se esperaba un único resultado para: " + jpql);
    }

    public long count(String countJpql) {
        return em.createQuery(countJpql, Long.class).getSingleResult();
    }

    public EntityManager getEntityManager() {
        return em;
    }
}
