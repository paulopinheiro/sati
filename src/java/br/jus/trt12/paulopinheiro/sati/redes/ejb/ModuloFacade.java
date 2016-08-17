package br.jus.trt12.paulopinheiro.sati.redes.ejb;

import br.jus.trt12.paulopinheiro.sati.geral.ejb.comum.AbstractFacade;
import br.jus.trt12.paulopinheiro.sati.geral.model.Municipio;
import br.jus.trt12.paulopinheiro.sati.redes.model.Modulo;
import br.jus.trt12.paulopinheiro.sati.redes.model.TipoModulo;
import br.jus.trt12.paulopinheiro.sati.exceptions.SatiLogicalException;
import br.jus.trt12.paulopinheiro.sati.geral.model.Unidade;
import java.util.Collections;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class ModuloFacade extends AbstractFacade<Modulo> {
    @PersistenceContext(unitName = "satiPU")
    private EntityManager em;

    @Override
    public void salvar(Modulo modulo) throws SatiLogicalException {
        if (modulo!= null) {
            if ((modulo.getIdentificacao()==null)||(modulo.getIdentificacao().trim().isEmpty())) throw new SatiLogicalException("Informe a identificação do módulo");
            if ((modulo.getLocalizacao()==null)||(modulo.getLocalizacao().trim().isEmpty())) throw new SatiLogicalException("Informe a localização do módulo");
            if (modulo.getUnidade()==null) throw new SatiLogicalException("Informe a unidade judiciária onde está instalado o módulo");
            if (modulo.getTipoModulo()==null) throw new SatiLogicalException("Informe o tipo de módulo");
            if (modulo.getCodigo()==null) super.create(modulo);
            else super.edit(modulo);
        }
    }

    public List<Modulo> findByMunicipio(Municipio municipio) {
        List<Modulo> resposta = null;
        Query query = getEntityManager().createNamedQuery("Modulo.modulosByMunicipio");
        query.setParameter("municipio", municipio);
        resposta = query.getResultList();
        Collections.sort(resposta);
        return resposta;
    }

    public List<TipoModulo> tiposModulo() {
        List<TipoModulo> resposta = null;
        Query query = getEntityManager().createNamedQuery("TipoModulo.findAll");
        resposta = query.getResultList();
        return resposta;
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ModuloFacade() {
        super(Modulo.class);
    }

    @Override
    public void excluir(Modulo entity) throws SatiLogicalException {
        super.remove(entity);
    }

    public List<Modulo> findByUnidade(Unidade unidade) {
        List<Modulo> resposta = null;
        Query query = getEntityManager().createNamedQuery("Modulo.modulosByUnidade");
        query.setParameter("unidade", unidade);
        resposta = query.getResultList();
        Collections.sort(resposta);
        return resposta;
    }
}
