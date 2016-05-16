package br.jus.trt12.paulopinheiro.sati.redes.jsf;

import br.jus.trt12.paulopinheiro.sati.geral.ejb.comum.AbstractFacade;
import br.jus.trt12.paulopinheiro.sati.geral.jsf.comum.AbListaRestritaMB;
import br.jus.trt12.paulopinheiro.sati.redes.ejb.PanelFacade;
import br.jus.trt12.paulopinheiro.sati.redes.model.Panel;
import br.jus.trt12.paulopinheiro.sati.redes.model.Rack;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class PanelMB extends AbListaRestritaMB<Panel> implements Serializable {
    @EJB private PanelFacade panelFacade;

    private Rack rack;

    public PanelMB() {}

    public List<Panel> getListaPanels() {
        return this.getLista();
    }

    public void setListaPanels(List<Panel> listaPanels) {
        this.setLista(listaPanels);
    }

    public Panel getPanel() {
        return this.getElemento();
    }

    public void setPanel(Panel panel) {
        this.setElemento(panel);
    }

    public Rack getRack() {
        if (this.rack==null) this.rack=new Rack();
        return rack;
    }

    public void setRack(Rack rack) {
        this.rack = rack;
    }

    @Override
    protected List<Panel> getListaRestrita() {
        return this.panelFacade.findByRack(this.getRack());
    }

    @Override
    protected AbstractFacade getFacade() {
        return this.panelFacade;
    }

    @Override
    public boolean isNovoElemento() {
        return this.getPanel().getCodigo()==null || this.getPanel().getCodigo()==0;
    }

    @Override
    protected Panel novainstanciaElemento() {
        return new Panel(this.getRack());
    }
}
