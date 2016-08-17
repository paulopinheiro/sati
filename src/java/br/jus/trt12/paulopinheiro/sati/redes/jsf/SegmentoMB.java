package br.jus.trt12.paulopinheiro.sati.redes.jsf;

import br.jus.trt12.paulopinheiro.sati.geral.ejb.UnidadeFacade;
import br.jus.trt12.paulopinheiro.sati.geral.ejb.comum.AbstractFacade;
import br.jus.trt12.paulopinheiro.sati.geral.jsf.GeralMB;
import br.jus.trt12.paulopinheiro.sati.geral.jsf.comum.AbBasicoMB;
import br.jus.trt12.paulopinheiro.sati.geral.model.Municipio;
import br.jus.trt12.paulopinheiro.sati.geral.model.Unidade;
import br.jus.trt12.paulopinheiro.sati.redes.ejb.ModuloFacade;
import br.jus.trt12.paulopinheiro.sati.redes.ejb.PanelFacade;
import br.jus.trt12.paulopinheiro.sati.redes.ejb.RackFacade;
import br.jus.trt12.paulopinheiro.sati.redes.ejb.SegmentoFacade;
import br.jus.trt12.paulopinheiro.sati.redes.ejb.TomadaRemotaFacade;
import br.jus.trt12.paulopinheiro.sati.redes.model.Modulo;
import br.jus.trt12.paulopinheiro.sati.redes.model.Panel;
import br.jus.trt12.paulopinheiro.sati.redes.model.Rack;
import br.jus.trt12.paulopinheiro.sati.redes.model.Segmento;
import br.jus.trt12.paulopinheiro.sati.redes.model.Tomada;
import br.jus.trt12.paulopinheiro.sati.redes.model.TomadaPanel;
import br.jus.trt12.paulopinheiro.sati.redes.model.TomadaRemota;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

@Named
@ViewScoped
public class SegmentoMB extends AbBasicoMB<Segmento> implements Serializable {
    @EJB private SegmentoFacade segmentoFacade;
    @EJB private ModuloFacade moduloFacade;
    @EJB private TomadaRemotaFacade tomadaRemotaFacade;
    @EJB private UnidadeFacade unidadeFacade;
    @EJB private RackFacade rackFacade;
    @EJB private PanelFacade panelFacade;

    @Inject private GeralMB geralMB;

    private Tomada tomada1;

    private Tomada tomada2;

    private Integer tipoTomada;

    private List<Unidade> unidades;
    private Unidade unidade;
    private List<Modulo> modulos;
    private Modulo modulo;
    private List<TomadaRemota> tomadasRemotas;
    private TomadaRemota tomadaRemota;

    private List<Rack> racks;
    private Rack rack;
    private List<Panel> panels;
    private Panel panel;
    private List<TomadaPanel> tomadasPanel;
    private TomadaPanel tomadaPanel;

    public SegmentoMB() {}

    public void alteraTipoTomada() {
        setTomada2(null);
    }

    public List<Unidade> getUnidades() {
        if (this.unidades==null) this.unidades = this.unidadeFacade.findByMunicipio(this.getMunicipio());
        return unidades;
    }

    public void setUnidades(List<Unidade> unidades) {
        this.unidades = unidades;
    }

    public Unidade getUnidade() {
        if (this.unidade==null) this.unidade = new Unidade();
        return unidade;
    }

    public void setUnidade(Unidade unidade) {
        this.unidade = unidade;
    }

    public void alteraUnidade() {
        setModulos(null);
    }

    public List<Modulo> getModulos() {
        if (this.modulos==null) this.modulos = this.moduloFacade.findByUnidade(this.getUnidade());
        return modulos;
    }

    public void setModulos(List<Modulo> modulos) {
        this.modulos = modulos;
    }

    public Modulo getModulo() {
        if (this.modulo==null) this.modulo = new Modulo();
        return modulo;
    }

    public void setModulo(Modulo modulo) {
        this.modulo = modulo;
    }

    public void alteraModulo() {
        setTomadasRemotas(null);
    }

    public List<TomadaRemota> getTomadasRemotas() {
        if (this.tomadasRemotas==null) this.tomadasRemotas = this.tomadaRemotaFacade.findByModulo(getModulo());
        return tomadasRemotas;
    }

    public void setTomadasRemotas(List<TomadaRemota> tomadasRemotas) {
        this.tomadasRemotas = tomadasRemotas;
    }

    public TomadaRemota getTomadaRemota() {
        if (this.tomadaRemota==null) this.tomadaRemota = new TomadaRemota();
        return tomadaRemota;
    }

    public void setTomadaRemota(TomadaRemota tomadaRemota) {
        this.tomadaRemota = tomadaRemota;
    }

    public void alteraTomadaRemota() {
        setTomada2(getTomadaRemota());
    }

    public List<Rack> getRacks() {
        if (this.racks==null) this.racks = this.rackFacade.findByMunicipio(this.getMunicipio());
        return racks;
    }

    public void setRacks(List<Rack> racks) {
        this.racks = racks;
    }

    public void alteraRack() {
        setPanels(null);
    }

    public Rack getRack() {
        if (this.rack==null) this.rack = new Rack();
        return rack;
    }

    public void setRack(Rack rack) {
        this.rack = rack;
    }

    public List<Panel> getPanels() {
        if (this.panels==null) this.panels = this.panelFacade.findByRack(this.getRack());
        return panels;
    }

    public void setPanels(List<Panel> panels) {
        this.panels = panels;
    }

    public Panel getPanel() {
        if (this.panel==null) this.panel = new Panel();
        return panel;
    }

    public void setPanel(Panel panel) {
        this.panel = panel;
    }

    public void alteraPanel() {
        setTomadasPanel(null);
    }

    public List<TomadaPanel> getTomadasPanel() {
        if (this.tomadasPanel==null) this.tomadasPanel = this.panelFacade.getListaTomadas(this.getPanel());
        return tomadasPanel;
    }

    public void setTomadasPanel(List<TomadaPanel> tomadasPanel) {
        this.tomadasPanel = tomadasPanel;
    }

    public TomadaPanel getTomadaPanel() {
        if (this.tomadaPanel==null) this.tomadaPanel = new TomadaPanel();
        return tomadaPanel;
    }

    public void setTomadaPanel(TomadaPanel tomadaPanel) {
        this.tomadaPanel = tomadaPanel;
    }

    public void alteraTomadaPanel() {
        this.setTomada2(this.getTomadaPanel());
    }

    public Integer getTipoTomada() {
        return tipoTomada;
    }

    public void setTipoTomada(Integer tipoTomada) {
        this.tipoTomada = tipoTomada;
    }

    public Tomada getTomada1() {
        if (this.tomada1==null) this.tomada1=new Tomada();
        return tomada1;
    }

    public void setTomada1(Tomada tomada1) {
        this.tomada1 = tomada1;
    }

    public Tomada getTomada2() {
        if (this.tomada2==null) this.tomada2 = new Tomada();
        return tomada2;
    }

    public void setTomada2(Tomada tomada2) {
        this.tomada2 = tomada2;
    }

    public Segmento getSegmento() {
        return this.getElemento();
    }

    public void setSegmento(Segmento segmento) {
        this.setElemento(segmento);
    }

    @Override
    protected AbstractFacade getFacade() {
        return this.segmentoFacade;
    }

    @Override
    public boolean isNovoElemento() {
        return (this.getSegmento().getCodigo()==null||this.getSegmento().getCodigo()==0);
    }

    @Override
    protected Segmento novainstanciaElemento() {
        return new Segmento();
    }

    public Municipio getMunicipio() {
        return this.geralMB.getMunicipioSessao();
    }

}
