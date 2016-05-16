package br.jus.trt12.paulopinheiro.sati.geral.jsf;

import br.jus.trt12.paulopinheiro.sati.geral.ejb.ProgintFacade;
import br.jus.trt12.paulopinheiro.sati.geral.ejb.comum.AbstractFacade;
import br.jus.trt12.paulopinheiro.sati.geral.jsf.comum.AbListaMB;
import br.jus.trt12.paulopinheiro.sati.geral.model.Progint;
import br.jus.trt12.paulopinheiro.sati.util.ContextoJSF;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

@ManagedBean
@ViewScoped
public class ProgintMB extends AbListaMB<Progint> implements Serializable {
    @EJB private ProgintFacade progintFacade;

    public ProgintMB() {}

    public Progint getProgint() {
        return this.getElemento();
    }

    public void setProgint(Progint progint) {
        this.setElemento(progint);
    }

    public List<Progint> getProgints() {
        return this.getLista();
    }

    public void setProgints(List<Progint> progints) {
        this.setLista(progints);
    }

    @Override
    protected AbstractFacade getFacade() {
        return this.progintFacade;
    }

    @Override
    public boolean isNovoElemento() {
        return (this.getProgint().getCodigo()==null)||(this.getProgint().getCodigo()==0);
    }

    @Override
    protected Progint novainstanciaElemento() {
        return new Progint();
    }
}
