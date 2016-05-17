package br.jus.trt12.paulopinheiro.sati.viagem.jsf;

import br.jus.trt12.paulopinheiro.sati.geral.ejb.comum.AbstractFacade;
import br.jus.trt12.paulopinheiro.sati.geral.jsf.comum.AbListaRestritaMB;
import br.jus.trt12.paulopinheiro.sati.viagem.ejb.EventoReqViagemFacade;
import br.jus.trt12.paulopinheiro.sati.viagem.model.EventoReqViagem;
import br.jus.trt12.paulopinheiro.sati.viagem.model.TipoEventoReqViagem;
import br.jus.trt12.paulopinheiro.sati.viagem.model.Viagem;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class EventoReqViagemMB extends AbListaRestritaMB<EventoReqViagem> implements Serializable {
    @EJB private EventoReqViagemFacade eventoReqViagemFacade;

    private Viagem viagem;

    private List<TipoEventoReqViagem> tiposEvento;

    public EventoReqViagemMB() {}

    public EventoReqViagem getEventoReqViagem() {
        return this.getElemento();
    }

    public void setEventoReqViagem(EventoReqViagem eventoReqViagem) {
        this.setElemento(eventoReqViagem);
    }

    public List<EventoReqViagem> getListaEventos() {
        return this.getLista();
    }

    public void setListaEventos(List<EventoReqViagem> listaEventos) {
        this.setLista(listaEventos);
    }

    public List<TipoEventoReqViagem> getTiposEvento() {
        if (this.tiposEvento==null) this.tiposEvento = eventoReqViagemFacade.tiposEvento();
        return tiposEvento;
    }

    public void setTiposEvento(List<TipoEventoReqViagem> tiposEvento) {
        this.tiposEvento = tiposEvento;
    }

    public Viagem getViagem() {
        if (this.viagem==null) this.viagem = new Viagem();
        return viagem;
    }

    public void setViagem(Viagem viagem) {
        this.viagem = viagem;
    }

    @Override
    protected List<EventoReqViagem> getListaRestrita() {
        return this.eventoReqViagemFacade.findByViagem(this.getViagem());
    }

    @Override
    protected AbstractFacade getFacade() {
        return this.eventoReqViagemFacade;
    }

    @Override
    public boolean isNovoElemento() {
        return this.getEventoReqViagem().getCodigo()==null || this.getEventoReqViagem().getCodigo()==0;
    }

    @Override
    protected EventoReqViagem novainstanciaElemento() {
        return new EventoReqViagem(this.getViagem());
    }
}
