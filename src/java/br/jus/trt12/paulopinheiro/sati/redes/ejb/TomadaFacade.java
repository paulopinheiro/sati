package br.jus.trt12.paulopinheiro.sati.redes.ejb;

import br.jus.trt12.paulopinheiro.sati.redes.model.Tomada;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class TomadaFacade {
    @PersistenceContext(unitName = "satiPU")
    private EntityManager em;

    public Tomada find(Object id) {
        return getEntityManager().find(Tomada.class, id);
    }

    protected EntityManager getEntityManager() {
        return this.em;
    }
}
