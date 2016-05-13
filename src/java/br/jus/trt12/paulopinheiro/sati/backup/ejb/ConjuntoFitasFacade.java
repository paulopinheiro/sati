package br.jus.trt12.paulopinheiro.sati.backup.ejb;

import br.jus.trt12.paulopinheiro.sati.backup.model.ConjuntoFitas;
import br.jus.trt12.paulopinheiro.sati.backup.model.DesignacaoFita;
import br.jus.trt12.paulopinheiro.sati.exceptions.SatiLogicalException;
import br.jus.trt12.paulopinheiro.sati.geral.ejb.comum.AbstractFacade;
import br.jus.trt12.paulopinheiro.sati.geral.model.Municipio;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class ConjuntoFitasFacade extends AbstractFacade<ConjuntoFitas> {
    @PersistenceContext(unitName = "satiPU")
    private EntityManager em;

    @Override
    public void salvar(ConjuntoFitas conjunto) throws SatiLogicalException {
        if (conjunto!=null) {
            if (conjunto.getDesignacaoFita()==null) throw new SatiLogicalException("Informe a designação deste conjunto de fitas");
            if (conjunto.getMunicipio()==null) throw new SatiLogicalException("Informe o município ao qual pertence este conjunto");
            if ((conjunto.getQuantFitas()==null)||(conjunto.getQuantFitas()<1)) throw new SatiLogicalException("Informe uma quantidade de 1 ou mais fitas");
            if (conjunto.isDuplicidade(this.getConjuntoFitasByMunicipioDesignacao(conjunto.getMunicipio(), conjunto.getDesignacaoFita()))) throw new SatiLogicalException("Já existe um conjunto com essa designação no município");
            if (conjunto.getId()==null) super.create(conjunto);
            else super.edit(conjunto);
        }
    }

    public List<ConjuntoFitas> getConjuntosFitasByMunicipio(Municipio municipio) {
        List<ConjuntoFitas> resposta = null;
        Query query = getEntityManager().createNamedQuery("ConjuntoFitas.findByMunicipio");
        query.setParameter(":municipio", municipio);
        resposta = query.getResultList();
        return resposta;
    }

    private ConjuntoFitas getConjuntoFitasByMunicipioDesignacao(Municipio municipio, DesignacaoFita designacao) {
        ConjuntoFitas resposta = null;
        Query query = getEntityManager().createNamedQuery("ConjuntoFitas.findByMunicipioDesignacao");
        query.setParameter(":municipio", municipio);
        query.setParameter(":designacao", designacao);
        resposta = (ConjuntoFitas) query.getSingleResult();
        return resposta;
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ConjuntoFitasFacade() {
        super(ConjuntoFitas.class);
    }

    @Override
    public void excluir(ConjuntoFitas entity) throws SatiLogicalException {
        super.remove(entity);
    }

}
