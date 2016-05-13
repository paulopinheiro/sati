package br.jus.trt12.paulopinheiro.sati.redes.jsf;

import br.jus.trt12.paulopinheiro.sati.redes.ejb.PanelFacade;
import br.jus.trt12.paulopinheiro.sati.redes.model.Panel;
import br.jus.trt12.paulopinheiro.sati.redes.model.TomadaPanel;
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
public class TomadaPanelMB implements Serializable {
    @EJB private PanelFacade panelFacade;

    private Panel panel;

    private TomadaPanel tomadaPanel;
    private List<TomadaPanel> listaTomadas;


    public TomadaPanelMB() {}

    public void salvar(ActionEvent event) {
        try {
            panelFacade.salvaTomadaPanel(getTomadaPanel());
            setTomadaPanel(null);
            setListaTomadas(null);
        } catch (Exception ex) {
            mensagemErro(ex.getMessage());
        }
    }

    public List<TomadaPanel> getListaTomadas() {
        if (this.listaTomadas==null) this.listaTomadas=panelFacade.getListaTomadas(getPanel());
        return listaTomadas;
    }

    public void setListaTomadas(List<TomadaPanel> listaTomadas) {
        this.listaTomadas = listaTomadas;
    }

    public Panel getPanel() {
        if (this.panel==null) this.panel = new Panel();
        return panel;
    }

    public void setPanel(Panel panel) {
        this.panel = panel;
    }

    public TomadaPanel getTomadaPanel() {
        if (this.tomadaPanel==null) this.tomadaPanel = new TomadaPanel(getPanel());
        return tomadaPanel;
    }

    public void setTomadaPanel(TomadaPanel tomadaPanel) {
        this.tomadaPanel = tomadaPanel;
    }

    private void mensagemErro(String mensagem) {
        ContextoJSF.getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,mensagem,null));
    }
}
