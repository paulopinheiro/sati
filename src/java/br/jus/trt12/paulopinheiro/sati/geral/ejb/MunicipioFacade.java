package br.jus.trt12.paulopinheiro.sati.geral.ejb;

import br.jus.trt12.paulopinheiro.sati.geral.ejb.comum.AbstractFacade;
import br.jus.trt12.paulopinheiro.sati.geral.model.AreaTI;
import br.jus.trt12.paulopinheiro.sati.geral.model.Municipio;
import br.jus.trt12.paulopinheiro.sati.exceptions.SatiLogicalException;
import java.util.Collections;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class MunicipioFacade extends AbstractFacade<Municipio> {
    @PersistenceContext(unitName = "satiPU")
    private EntityManager em;

    @Override
    public void salvar(Municipio municipio) throws SatiLogicalException {
        if (municipio!= null) {
            if ((municipio.getNome()==null)||(municipio.getNome().trim().isEmpty())) throw new SatiLogicalException("Informe o nome do município");
            if (municipio.getAreaTI()==null) throw new SatiLogicalException("Informe qual a área do TI na qual está inserido este município");
            if (municipio.getCodigo()==null) super.create(municipio);
            else super.edit(municipio);
        }
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MunicipioFacade() {
        super(Municipio.class);
    }

    @Override
    public List<Municipio> findAll() {
        List<Municipio> resposta = super.findAll();
        Collections.sort(resposta);
        return resposta;
    }

    public List<Municipio> findByAreaTI(AreaTI areaTI) {
        List<Municipio> resposta;
        if (areaTI==null) return null;
        Query query = getEntityManager().createNamedQuery("Municipio.municipiosByAreaTI");
        query.setParameter("areaTI", areaTI);
        resposta = query.getResultList();
        Collections.sort(resposta);
        return resposta;
    }

    @Override
    public void excluir(Municipio entity) throws SatiLogicalException {
        super.remove(entity);
    }
}
