package br.jus.trt12.paulopinheiro.sati.redes.ejb;

import br.jus.trt12.paulopinheiro.sati.geral.ejb.comum.AbstractFacade;
import br.jus.trt12.paulopinheiro.sati.redes.model.Modulo;
import br.jus.trt12.paulopinheiro.sati.redes.model.TipoConector;
import br.jus.trt12.paulopinheiro.sati.redes.model.TomadaRemota;
import br.jus.trt12.paulopinheiro.sati.exceptions.SatiLogicalException;
import java.util.Collections;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class TomadaRemotaFacade extends AbstractFacade<TomadaRemota> {
    @PersistenceContext(unitName = "satiPU")
    private EntityManager em;

    public List<TomadaRemota> findByModulo(Modulo modulo) {
        List<TomadaRemota> resposta = null;
        Query query = getEntityManager().createNamedQuery("TomadaRemota.tomadasRemotasByModulo");
        query.setParameter("modulo", modulo);
        resposta = query.getResultList();
        Collections.sort(resposta);
        return resposta;
    }

    @Override
    public void salvar(TomadaRemota tomadaRemota) throws SatiLogicalException {
        if (tomadaRemota!= null) {
            if ((tomadaRemota.getNome()==null)||(tomadaRemota.getNome().trim().isEmpty())) throw new SatiLogicalException("Informe o nome da tomada");
            if (tomadaRemota.getModulo()==null) throw new SatiLogicalException("Informe o módulo da tomada");
            if (tomadaRemota.getTipoConector()==null) throw new SatiLogicalException("Informe o tipo de conector");
            if (tomadaRemota.getCodigo()==null) super.create(tomadaRemota);
            else super.edit(tomadaRemota);
        }
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TomadaRemotaFacade() {
        super(TomadaRemota.class);
    }

    public List<TipoConector> tiposConector() {
        List<TipoConector> resposta = null;
        Query query = getEntityManager().createNamedQuery("TipoConector.todosTiposConector");
        resposta = query.getResultList();
        return resposta;
    }

    @Override
    public void excluir(TomadaRemota entity) throws SatiLogicalException {
        super.remove(entity);
    }

}
