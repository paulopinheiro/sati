package br.jus.trt12.paulopinheiro.sati.geral.ejb;

import br.jus.trt12.paulopinheiro.sati.exceptions.SatiLogicalException;
import br.jus.trt12.paulopinheiro.sati.geral.ejb.comum.AbstractFacade;
import br.jus.trt12.paulopinheiro.sati.geral.model.Municipio;
import br.jus.trt12.paulopinheiro.sati.geral.model.Unidade;
import java.util.Collections;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class UnidadeFacade extends AbstractFacade<Unidade> {
    @PersistenceContext(unitName = "satiPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public void salvar(Unidade unidade) throws SatiLogicalException {
        if (unidade!= null) {
            if ((unidade.getNome()==null)||(unidade.getNome().trim().isEmpty())) throw new SatiLogicalException("Informe o nome da unidade");
            if ((unidade.getSigla()==null)||(unidade.getSigla().trim().isEmpty())) throw new SatiLogicalException("Informe a sigla da unidade");
            if ((unidade.getPrefixo()==null)||(unidade.getPrefixo().trim().isEmpty())) throw new SatiLogicalException("Informe o prefixo da unidade");
            if (unidade.getCodigo()==null) super.create(unidade);
            else super.edit(unidade);
        }
    }

    public UnidadeFacade() {
        super(Unidade.class);
    }

    public List<Unidade> findByMunicipio(Municipio municipio) {
        List<Unidade> resposta = null;
        Query query = getEntityManager().createNamedQuery("Unidade.findByMunicipio");
        query.setParameter("municipio", municipio);
        resposta = query.getResultList();
        Collections.sort(resposta);
        return resposta;
    }

    @Override
    public void excluir(Unidade entity) throws SatiLogicalException {
        super.remove(entity);
    }
}
