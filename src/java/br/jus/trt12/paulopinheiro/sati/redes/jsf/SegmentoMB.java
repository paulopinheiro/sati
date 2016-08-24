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
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;


// CORRIGIR BUG: Não está carregando o cadSegmento quando o segmento não é nulo
@Named
@ViewScoped
public class SegmentoMB extends AbBasicoMB<Segmento> implements Serializable {
    private final static int TOMADA_REMOTA=0;
    private final static int TOMADA_PANEL=1;

    @EJB private SegmentoFacade segmentoFacade;
    @EJB private ModuloFacade moduloFacade;
    @EJB private TomadaRemotaFacade tomadaRemotaFacade;
    @EJB private UnidadeFacade unidadeFacade;
    @EJB private RackFacade rackFacade;
    @EJB private PanelFacade panelFacade;

    @Inject private GeralMB geralMB;

    private String linkVoltar;

    private Tomada tomada;
    private Tomada outraTomada;

    private Integer categoriaOutraTomada;

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

    public void setTomada(Tomada tomada) {
        this.tomada = tomada;
        if (tomada !=null) atualizarSegmento(tomada);
    }

    public Tomada getTomada() {
        if (this.tomada==null) this.tomada=new Tomada();
        return tomada;
    }

    private void atualizarSegmento(Tomada tomada) {
        if (getReferenciaSegmento(tomada)==1) {
            setSegmento(tomada.getSegmentoRef1());
            this.setOutraTomada(this.getSegmento().getTomada2());
        } else {
            setSegmento(tomada.getSegmentoRef2());
            this.setOutraTomada(this.getSegmento().getTomada1());
        }
    }

    private int getReferenciaSegmento(Tomada tomada) {
        if (tomada.getSegmentoRef2()==null) return 1;
        else return 2;
    }

    @Override
    public void salvar(ActionEvent evt) {
        if (this.isNovoElemento() || getReferenciaSegmento(getTomada())==1) {
            this.getSegmento().setTomada1(this.getTomada());
            this.getSegmento().setTomada2(this.getOutraTomada());
        } else {
            this.getSegmento().setTomada1(this.getOutraTomada());
            this.getSegmento().setTomada2(this.getTomada());
        }
        super.salvar(evt);
    }

    public Tomada getOutraTomada() {
        if (this.outraTomada==null) outraTomada = new Tomada();
        return outraTomada;
    }

    public void setOutraTomada(Tomada outraTomada) {
        this.outraTomada = outraTomada;
        atualizaDialogos(outraTomada);
    }

    public Integer getCategoriaOutraTomada() {
        if (this.categoriaOutraTomada==null) this.categoriaOutraTomada = categoriaTomada(this.getOutraTomada());
        return categoriaOutraTomada;
    }

    public void setCategoriaOutraTomada(Integer categoriaOutraTomada) {
        this.categoriaOutraTomada = categoriaOutraTomada;
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
        if (modulo!=null) setUnidade(modulo.getUnidade());
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
        if (tomadaRemota!=null) setModulo(tomadaRemota.getModulo());
    }

    public void alteraTomadaRemota() {
        this.setOutraTomada(this.getTomadaRemota());
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
        if (panel!=null) setRack(panel.getRack());
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
        if (tomadaPanel!=null) setPanel(tomadaPanel.getPanel());
    }

    public void alteraTomadaPanel() {
        this.setOutraTomada(this.getTomadaPanel());
    }

    public Segmento getSegmento() {
        return this.getElemento();
    }

    public void setSegmento(Segmento segmento) {
        this.setElemento(segmento);
    }

    private void atualizaDialogos(Tomada tomada) {
        if (tomada!=null) {
            if (categoriaTomada(tomada)==TOMADA_REMOTA) {
                setTomadaRemota((TomadaRemota) tomada);
                System.out.println("Tomada remota!");
            } else {
                if (categoriaTomada(tomada)==TOMADA_PANEL) {
                    setTomadaPanel((TomadaPanel) tomada);
                    System.out.println("Tomada panel!");
                }
            }
        }
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
        Segmento segmento = new Segmento();
        segmento.setTomada1(getTomada());
        return segmento;
    }

    public Municipio getMunicipio() {
        return this.geralMB.getMunicipioSessao();
    }

    public String getLinkVoltar() {
        return linkVoltar;
    }

    public void setLinkVoltar(String linkVoltar) {
        this.linkVoltar = linkVoltar;
    }

    private static Integer categoriaTomada(Tomada tomada) {
        Integer resposta = null;
        if (tomada!=null)
            if (tomada instanceof TomadaRemota) resposta = TOMADA_REMOTA;
            else if (tomada instanceof TomadaPanel) resposta = TOMADA_PANEL;
        return resposta;
    }
}
