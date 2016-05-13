package br.jus.trt12.paulopinheiro.sati.redes.ejb;

import br.jus.trt12.paulopinheiro.sati.geral.ejb.comum.AbstractFacade;
import br.jus.trt12.paulopinheiro.sati.geral.model.Municipio;
import br.jus.trt12.paulopinheiro.sati.redes.model.Rack;
import br.jus.trt12.paulopinheiro.sati.exceptions.SatiLogicalException;
import java.util.Collections;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class RackFacade extends AbstractFacade<Rack> {
    @PersistenceContext(unitName = "satiPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public void salvar(Rack rack) throws SatiLogicalException {
        if (rack!= null) {
            if ((rack.getIdentificacao()==null)||(rack.getIdentificacao().trim().isEmpty())) throw new SatiLogicalException("Informe a identificação do rack");
            if ((rack.getLocalizacao()==null)||(rack.getLocalizacao().trim().isEmpty())) throw new SatiLogicalException("Informe a localização do rack");
            if (rack.getMunicipio()==null) throw new SatiLogicalException("Informe o município do rack");
            if (rack.getCodigo()==null) super.create(rack);
            else super.edit(rack);
        }
    }

    public RackFacade() {
        super(Rack.class);
    }

    public List<Rack> findByMunicipio(Municipio municipio) {
        List<Rack> resposta = null;
        Query query = getEntityManager().createNamedQuery("Rack.racksByMunicipio");
        query.setParameter("municipio", municipio);
        resposta = query.getResultList();
        Collections.sort(resposta);
        return resposta;
    }

    @Override
    public List<Rack> findAll() {
        List<Rack> resposta = super.findAll();
        Collections.sort(resposta);
        return resposta;
    }

    @Override
    public void excluir(Rack entity) throws SatiLogicalException {
        super.remove(entity);
    }
}
