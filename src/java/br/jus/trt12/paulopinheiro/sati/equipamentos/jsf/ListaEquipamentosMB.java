package br.jus.trt12.paulopinheiro.sati.equipamentos.jsf;

import br.jus.trt12.paulopinheiro.sati.equipamentos.ejb.EquipamentoFacade;
import br.jus.trt12.paulopinheiro.sati.equipamentos.ejb.LoteFacade;
import br.jus.trt12.paulopinheiro.sati.equipamentos.ejb.ModeloFacade;
import br.jus.trt12.paulopinheiro.sati.equipamentos.ejb.TipoEquipamentoFacade;
import br.jus.trt12.paulopinheiro.sati.equipamentos.model.Equipamento;
import br.jus.trt12.paulopinheiro.sati.equipamentos.model.Lote;
import br.jus.trt12.paulopinheiro.sati.equipamentos.model.Modelo;
import br.jus.trt12.paulopinheiro.sati.equipamentos.model.TipoEquipamento;
import br.jus.trt12.paulopinheiro.sati.geral.jsf.GeralMB;
import br.jus.trt12.paulopinheiro.sati.geral.model.Municipio;
import br.jus.trt12.paulopinheiro.sati.util.ContextoJSF;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ViewScoped
public class ListaEquipamentosMB implements Serializable {
    @EJB private EquipamentoFacade equipamentoFacade;
    @EJB private LoteFacade loteFacade;
    @EJB private ModeloFacade modeloFacade;
    @EJB private TipoEquipamentoFacade tipoEquipamentoFacade;
    @Inject private GeralMB geralMB;

    private TipoEquipamento tipoEquipamento;
    private List<Equipamento> listaEquipamentos;

    private Modelo filtroModelo;
    private Lote filtroLote;

    private String filtroTombo;

    private List<TipoEquipamento> listaTiposEquipamento;
    private List<Modelo> listaModelos;
    private List<Lote> listaLotes;


    public ListaEquipamentosMB() {}

        public void filtrar(ActionEvent evt) {
        try {
            this.listaEquipamentos = this.equipamentoFacade.findAtivosFiltro(tipoEquipamento, filtroModelo, filtroLote, filtroTombo, this.getMunicipioSessao());
        } catch (Exception ex) {
            mensagemErro(ex.getMessage());
        }
    }

    public int getQuantLista() {
        if (getListaEquipamentos() == null) return 0;
        return getListaEquipamentos().size();
    }

    public List<Equipamento> getListaEquipamentos() {
        if (this.listaEquipamentos==null) this.listaEquipamentos = this.equipamentoFacade.findAtivosByMunicipioTipoEquipamento(this.getMunicipioSessao(),this.getTipoEquipamento());
        return listaEquipamentos;
    }

    public void setListaEquipamentos(List<Equipamento> listaEquipamentos) {
        this.listaEquipamentos = listaEquipamentos;
    }

    public String getFiltroTombo() {
        return filtroTombo;
    }

    public void setFiltroTombo(String filtroTombo) {
        this.filtroTombo = filtroTombo;
    }

    public List<TipoEquipamento> getListaTiposEquipamento() {
        if (this.listaTiposEquipamento==null) this.listaTiposEquipamento = this.tipoEquipamentoFacade.findAll();
        return listaTiposEquipamento;
    }

    public void setListaTiposEquipamento(List<TipoEquipamento> listaTiposEquipamento) {
        this.listaTiposEquipamento = listaTiposEquipamento;
    }

    public List<Modelo> getListaModelos() {
        if (this.listaModelos==null) this.listaModelos = this.modeloFacade.findByTipoEquipamento(this.getTipoEquipamento());
        return listaModelos;
    }

    public void setListaModelos(List<Modelo> listaModelos) {
        this.listaModelos = listaModelos;
    }

    public List<Lote> getListaLotes() {
        if (this.listaLotes==null) this.listaLotes = this.loteFacade.findByModelo(this.getFiltroModelo());
        return listaLotes;
    }

    public void setListaLotes(List<Lote> listaLotes) {
        this.listaLotes = listaLotes;
    }

    public Modelo getFiltroModelo() {
        return filtroModelo;
    }

    public void setFiltroModelo(Modelo filtroModelo) {
        this.filtroModelo = filtroModelo;
    }

    public Lote getFiltroLote() {
        return filtroLote;
    }

    public void setFiltroLote(Lote filtroLote) {
        this.filtroLote = filtroLote;
    }
    
    public void alteracaoTipoEquipamento(AjaxBehaviorEvent evt) {
        this.filtroModelo=null;
        this.filtroLote=null;
        this.listaModelos=null;
        this.listaLotes=null;
    }

    public void alteracaoModelo(AjaxBehaviorEvent evt) {
        this.filtroLote=null;
        this.listaLotes=null;
    }

    public Municipio getMunicipioSessao() {
        if (getGeralMB().getMunicipioSessao()==null) return new Municipio();
        return getGeralMB().getMunicipioSessao();
    }

    private void mensagemErro(String mensagem) {
        ContextoJSF.getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,mensagem,null));
    }

    public GeralMB getGeralMB() {
        return geralMB;
    }

    public void setGeralMB(GeralMB geralMB) {
        this.geralMB = geralMB;
    }

    public TipoEquipamento getTipoEquipamento() {
        return tipoEquipamento;
    }

    public void setTipoEquipamento(TipoEquipamento tipoEquipamento) {
        this.tipoEquipamento = tipoEquipamento;
    }
}
