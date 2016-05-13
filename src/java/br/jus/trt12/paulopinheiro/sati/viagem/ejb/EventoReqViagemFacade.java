package br.jus.trt12.paulopinheiro.sati.viagem.ejb;

import br.jus.trt12.paulopinheiro.sati.geral.ejb.comum.AbstractFacade;
import br.jus.trt12.paulopinheiro.sati.exceptions.SatiLogicalException;
import br.jus.trt12.paulopinheiro.sati.viagem.model.EventoReqViagem;
import br.jus.trt12.paulopinheiro.sati.viagem.model.TipoEventoReqViagem;
import br.jus.trt12.paulopinheiro.sati.viagem.model.Viagem;
import java.util.Collections;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class EventoReqViagemFacade extends AbstractFacade<EventoReqViagem> {
    @PersistenceContext(unitName = "satiPU")
    private EntityManager em;

    @Override
    public void salvar(EventoReqViagem eventoReqViagem) throws SatiLogicalException {
        if (eventoReqViagem!= null) {
            //TODO: Implementar cuidado contra inconsistência de eventos.
            //Ordem deve ser Pediro - Homologação - Aprovação - Pagamento
            if (eventoReqViagem.getDataEvento()==null) throw new SatiLogicalException("Informe a data do evento");
            if (eventoReqViagem.getTipoEvento()==null) throw new SatiLogicalException("Informe o tipo de evento");
            if (eventoReqViagem.getViagem()==null) throw new SatiLogicalException("Informe a viagem");
            if (eventoReqViagem.getCodigo()==null) {
                super.create(eventoReqViagem);
                getEntityManager().getEntityManagerFactory().getCache().evict(Viagem.class);
            }
            else super.edit(eventoReqViagem);
        }
    }

    public List<EventoReqViagem> findByViagem(Viagem viagem) {
        List<EventoReqViagem> resposta = null;
        Query query = getEntityManager().createNamedQuery("EventoReqViagem.eventosReqViagemByViagem");
        query.setParameter("viagem", viagem);
        resposta = query.getResultList();
        Collections.sort(resposta);
        return resposta;
    }

    @Override
    public List<EventoReqViagem> findAll() {
        List<EventoReqViagem> resposta = super.findAll();
        Collections.sort(resposta);
        return resposta;
    }

    public List<TipoEventoReqViagem> tiposEvento() {
        List<TipoEventoReqViagem> resposta = null;
        Query query = getEntityManager().createNamedQuery("TipoEventoReqViagem.todosTiposEventoReqViagem");
        resposta = query.getResultList();
        return resposta;
    }

    @Override
    public void remove(EventoReqViagem entity) {
        super.remove(entity);
        getEntityManager().getEntityManagerFactory().getCache().evict(Viagem.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EventoReqViagemFacade() {
        super(EventoReqViagem.class);
    }

    @Override
    public void excluir(EventoReqViagem entity) throws SatiLogicalException {
        super.remove(entity);
    }
}
