package br.jus.trt12.paulopinheiro.sati.redes.jsf;

import br.jus.trt12.paulopinheiro.sati.exceptions.SatiLogicalException;
import br.jus.trt12.paulopinheiro.sati.geral.ejb.comum.AbstractFacade;
import br.jus.trt12.paulopinheiro.sati.geral.jsf.comum.AbListaRestritaMB;
import br.jus.trt12.paulopinheiro.sati.redes.ejb.PanelFacade;
import br.jus.trt12.paulopinheiro.sati.redes.ejb.SegmentoFacade;
import br.jus.trt12.paulopinheiro.sati.redes.model.Panel;
import br.jus.trt12.paulopinheiro.sati.redes.model.Tomada;
import br.jus.trt12.paulopinheiro.sati.redes.model.TomadaPanel;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Named;

@Named
@ViewScoped
public class TomadaPanelMB extends AbListaRestritaMB<TomadaPanel> implements Serializable {
    @EJB private PanelFacade panelFacade;
    @EJB private SegmentoFacade segmentoFacade;

    private Panel panel;

    private TomadaPanel tomadaPanel;
    private List<TomadaPanel> listaTomadas;


    public TomadaPanelMB() {}

    @Override
    public void salvar(ActionEvent evt) {
        try {
            this.panelFacade.salvaTomadaPanel(this.getTomadaPanel());
            setElemento(null);
            setLista(null);
            mensagemSucesso("Registro salvo com sucesso");
        } catch (SatiLogicalException ex) {
            mensagemErro(ex.getMessage());
        } catch (Exception ex) {
            mensagemErro(ex.getMessage());
            Logger.getLogger("TomadaPanelMB.java").log(Level.SEVERE, "Erro ao salvar", ex);
        }
    }

    public List<TomadaPanel> getListaTomadas() {
        return this.getLista();
    }

    public void setListaTomadas(List<TomadaPanel> listaTomadas) {
        this.setLista(listaTomadas);
    }

    public Panel getPanel() {
        if (this.panel==null) this.panel = new Panel();
        return panel;
    }

    public void setPanel(Panel panel) {
        this.panel = panel;
    }

    public TomadaPanel getTomadaPanel() {
        return this.getElemento();
    }

    public void setTomadaPanel(TomadaPanel tomadaPanel) {
        this.setElemento(tomadaPanel);
    }

    @Override
    protected List<TomadaPanel> getListaRestrita() {
        return this.panelFacade.getListaTomadas(this.getPanel());
    }

    @Override
    protected AbstractFacade getFacade() {
        return this.panelFacade;
    }

    @Override
    public boolean isNovoElemento() {
        return this.getTomadaPanel().getCodigo()==null || this.getTomadaPanel().getCodigo()==0;
    }

    @Override
    protected TomadaPanel novainstanciaElemento() {
        return new TomadaPanel(this.getPanel());
    }

    public Tomada tomadaOutraPonta(Tomada tomada) {
        if (tomada==null) return null;
        return segmentoFacade.findOutraPontaTomada(tomada); 
    }
}
