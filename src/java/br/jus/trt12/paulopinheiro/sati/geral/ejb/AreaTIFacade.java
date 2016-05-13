package br.jus.trt12.paulopinheiro.sati.geral.ejb;

import br.jus.trt12.paulopinheiro.sati.geral.ejb.comum.AbstractFacade;
import br.jus.trt12.paulopinheiro.sati.geral.model.AreaTI;
import br.jus.trt12.paulopinheiro.sati.geral.model.Progint;
import br.jus.trt12.paulopinheiro.sati.exceptions.SatiLogicalException;
import java.util.Collections;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class AreaTIFacade extends AbstractFacade<AreaTI> {
    @PersistenceContext(unitName = "satiPU")
    private EntityManager em;

    @Override
    public void salvar(AreaTI areaTI) throws SatiLogicalException {
        if (areaTI!= null) {
            if ((areaTI.getNome()==null)||(areaTI.getNome().trim().isEmpty())) throw new SatiLogicalException("Informe o nome da área");
            if (areaTI.getProgint()==null) throw new SatiLogicalException("Informe o técnico responsável pela área");
            if (areaTI.getCodigo()==null) super.create(areaTI);
            else super.edit(areaTI);
        }
    }

    public AreaTI getByProgint(Progint progint) {
        AreaTI resposta = null;
        Query query = getEntityManager().createNamedQuery("AreaTI.areaTIByProgint");
        query.setParameter("progint", progint);
        resposta = (AreaTI) query.getSingleResult();
        return resposta;
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AreaTIFacade() {
        super(AreaTI.class);
    }

    @Override
    public List<AreaTI> findAll() {
        List<AreaTI> resposta = super.findAll();
        Collections.sort(resposta);
        return resposta;
    }

    @Override
    public void excluir(AreaTI entity) throws SatiLogicalException {
        super.remove(entity);
    }
}
