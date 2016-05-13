package br.jus.trt12.paulopinheiro.sati.redes.jsf;

import br.jus.trt12.paulopinheiro.sati.redes.ejb.PanelFacade;
import br.jus.trt12.paulopinheiro.sati.redes.model.Panel;
import br.jus.trt12.paulopinheiro.sati.redes.model.Rack;
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
public class PanelMB implements Serializable {
    @EJB private PanelFacade panelFacade;

    private Rack rack;

    private List<Panel> listaPanels;
    private Panel panel;

    public PanelMB() {}

    public void salvar(ActionEvent event) {
        try {
            panelFacade.salvar(getPanel());
            setPanel(null);
            setListaPanels(null);
        } catch (Exception ex) {
            mensagemErro(ex.getMessage());
        }
    }

    public void novo(ActionEvent event) {
        setRack(null);
    }

    public void excluir(ActionEvent event) {
        try {
            panelFacade.remove(getPanel());
            setPanel(null);
            setListaPanels(null);
        } catch (Exception ex) {
            mensagemErro(ex.getMessage());
        }
    }

    public int getQuantPanels() {
        return getListaPanels().size();
    }

    public List<Panel> getListaPanels() {
        if (this.listaPanels==null) this.listaPanels=panelFacade.findByRack(getRack());
        return listaPanels;
    }

    public void setListaPanels(List<Panel> listaPanels) {
        this.listaPanels = listaPanels;
    }

    public Panel getPanel() {
        if (this.panel==null) this.panel = new Panel(getRack());
        return panel;
    }

    public void setPanel(Panel panel) {
        this.panel = panel;
    }

    public Rack getRack() {
        if (this.rack==null) this.rack=new Rack();
        return rack;
    }

    public void setRack(Rack rack) {
        this.rack = rack;
    }

    private void mensagemErro(String mensagem) {
        ContextoJSF.getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,mensagem,null));
    }
}
