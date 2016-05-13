package br.jus.trt12.paulopinheiro.sati.viagem.jsf;

import br.jus.trt12.paulopinheiro.sati.util.ContextoJSF;
import br.jus.trt12.paulopinheiro.sati.viagem.ejb.EventoReqViagemFacade;
import br.jus.trt12.paulopinheiro.sati.viagem.model.EventoReqViagem;
import br.jus.trt12.paulopinheiro.sati.viagem.model.TipoEventoReqViagem;
import br.jus.trt12.paulopinheiro.sati.viagem.model.Viagem;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

@ManagedBean
@ViewScoped
public class EventoReqViagemMB implements Serializable {
    @EJB private EventoReqViagemFacade eventoReqViagemFacade;

    private Viagem viagem;

    private List<EventoReqViagem> listaEventos;
    private EventoReqViagem eventoReqViagem;

    private List<TipoEventoReqViagem> tiposEvento;

    public EventoReqViagemMB() {}

    public void salvar(ActionEvent event) {
        try {
            eventoReqViagemFacade.salvar(getEventoReqViagem());
            setEventoReqViagem(null);
            setListaEventos(null);
        } catch (Exception ex) {
            mensagemErro(ex.getMessage());
        }
    }

    public void novo(ActionEvent event) {
        setEventoReqViagem(null);
    }

    public void excluir(ActionEvent event) {
        try {
            eventoReqViagemFacade.remove(getEventoReqViagem());
            setEventoReqViagem(null);
            setListaEventos(null);
        } catch (Exception ex) {
            mensagemErro(ex.getMessage());
        }
    }

    public EventoReqViagem getEventoReqViagem() {
        if (this.eventoReqViagem==null) this.eventoReqViagem = new EventoReqViagem(getViagem());
        return eventoReqViagem;
    }

    public void setEventoReqViagem(EventoReqViagem eventoReqViagem) {
        this.eventoReqViagem = eventoReqViagem;
    }

    public List<EventoReqViagem> getListaEventos() {
        if (this.listaEventos==null) this.listaEventos = eventoReqViagemFacade.findByViagem(getViagem());
        return listaEventos;
    }

    public void setListaEventos(List<EventoReqViagem> listaEventos) {
        this.listaEventos = listaEventos;
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

    private void mensagemErro(String mensagem) {
        ContextoJSF.getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,mensagem,null));
    }
}
