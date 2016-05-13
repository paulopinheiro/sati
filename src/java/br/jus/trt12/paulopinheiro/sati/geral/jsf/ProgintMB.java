package br.jus.trt12.paulopinheiro.sati.geral.jsf;

import br.jus.trt12.paulopinheiro.sati.geral.ejb.ProgintFacade;
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
public class ProgintMB implements Serializable {
    @EJB private ProgintFacade progintFacade;

    private Progint progint;
    private List<Progint> progints;

    public ProgintMB() {}

    public void salvar(ActionEvent event) {
        try {
            progintFacade.salvar(getProgint());
            setProgint(null);
            setProgints(null);
        } catch (Exception ex) {
            mensagemErro(ex.getMessage());
        }
    }

    public void novo(ActionEvent event) {
        setProgint(null);
    }

    public void excluir(ActionEvent event) {
        try {
            progintFacade.remove(getProgint());
            setProgint(null);
            setProgints(null);
        } catch (Exception ex) {
            mensagemErro(ex.getMessage());
        }
    }

    public int getQuantProgints() {
        return getProgints().size();
    }

    public Progint getProgint() {
        if (this.progint==null) this.progint = new Progint();
        return progint;
    }

    public void setProgint(Progint progint) {
        this.progint = progint;
    }

    public List<Progint> getProgints() {
        if (this.progints==null) this.progints = progintFacade.findAll();
        return progints;
    }

    public void setProgints(List<Progint> progints) {
        this.progints = progints;
    }

    private void mensagemErro(String mensagem) {
        ContextoJSF.getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,mensagem,null));
    }
}
