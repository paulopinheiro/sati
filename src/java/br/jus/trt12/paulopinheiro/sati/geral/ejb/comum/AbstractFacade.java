package br.jus.trt12.paulopinheiro.sati.geral.ejb.comum;

import br.jus.trt12.paulopinheiro.sati.exceptions.SatiLogicalException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public abstract class AbstractFacade<T> {
    private final Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public abstract void salvar(T entity) throws SatiLogicalException;
    public abstract void excluir(T entity) throws SatiLogicalException;

    public void create(T entity) {
        getEntityManager().persist(entity);
    }

    public void edit(T entity) {
        getEntityManager().merge(entity);
    }

    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<T> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

    public CriteriaBuilder getCb() {
        return this.getEntityManager().getCriteriaBuilder();
    }

    protected CriteriaQuery<T> getCq(T filtro) {
        CriteriaQuery<T> cq = this.getCb().createQuery(entityClass);
        Root<T> r = cq.from(entityClass);
        cq.select(r);
        return cq;
    }

    public List<T> findFiltro(T filtro) throws SatiLogicalException {
        if (filtro==null) throw new SatiLogicalException("Filtro não pode ser nulo");
        List<T> resposta;

        Query pesquisa = getEntityManager().createQuery(getCq(filtro));

        resposta = pesquisa.getResultList();

        return resposta;
    }
}
