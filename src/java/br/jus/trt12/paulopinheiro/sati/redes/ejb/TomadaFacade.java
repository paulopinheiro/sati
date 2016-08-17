package br.jus.trt12.paulopinheiro.sati.redes.ejb;

import br.jus.trt12.paulopinheiro.sati.exceptions.SatiLogicalException;
import br.jus.trt12.paulopinheiro.sati.geral.ejb.comum.AbstractFacade;
import br.jus.trt12.paulopinheiro.sati.redes.model.Tomada;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class TomadaFacade extends AbstractFacade<Tomada> {
    @PersistenceContext(unitName = "satiPU")
    private EntityManager em;

    public TomadaFacade() {
        super(Tomada.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    @Override
    public void salvar(Tomada tomada) throws SatiLogicalException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void excluir(Tomada tomada) throws SatiLogicalException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
