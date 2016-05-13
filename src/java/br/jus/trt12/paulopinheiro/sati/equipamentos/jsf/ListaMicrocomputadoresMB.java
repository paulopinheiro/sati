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
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;

@ManagedBean
@RequestScoped
public class ListaMicrocomputadoresMB implements Serializable {
    @EJB private EquipamentoFacade equipamentoFacade;
    @EJB private TipoEquipamentoFacade tipoEquipamentoFacade;
    @EJB private LoteFacade loteFacade;
    @EJB private ModeloFacade modeloFacade;
    @ManagedProperty(value="#{geralMB}")
    private GeralMB geralMB;
    private TipoEquipamento tipoEquipamento;

    private List<Equipamento> listaMicrocomputadores;
    private Modelo filtroModelo;
    private Lote filtroLote;
    private String filtroTombo;
    
    private List<Modelo> listaModelos;
    private List<Lote> listaLotes;
    

    public ListaMicrocomputadoresMB() {}

    public void filtrar(ActionEvent evt) {
        try {
            this.listaMicrocomputadores = this.equipamentoFacade.findAtivosFiltro(this.getTipoEquipamento(),this.getFiltroModelo(),this.getFiltroLote(),this.getFiltroTombo(),this.getMunicipioSessao());
        } catch (Exception ex) {
            mensagemErro(ex.getMessage());
        }
    }

    public int getQuantLista() {
        if (getListaMicrocomputadores() == null) return 0;
        return getListaMicrocomputadores().size();
    }

    public List<Equipamento> getListaMicrocomputadores() {
        if (this.listaMicrocomputadores==null) this.listaMicrocomputadores = equipamentoFacade.findAtivosByMunicipioTipoEquipamento(this.getMunicipioSessao(), this.getTipoEquipamento());
        return listaMicrocomputadores;
    }

    public void setListaMicrocomputadores(List<Equipamento> listaMicrocomputadores) {
        this.listaMicrocomputadores = listaMicrocomputadores;
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

    public String getFiltroTombo() {
        return filtroTombo;
    }

    public void setFiltroTombo(String filtroTombo) {
        this.filtroTombo = filtroTombo;
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

    public TipoEquipamento getTipoEquipamento() {
        if (this.tipoEquipamento == null) this.tipoEquipamento = tipoEquipamentoFacade.find(1);
        return tipoEquipamento;
    }

    /**
     * @return the geralMB
     */
    public GeralMB getGeralMB() {
        return geralMB;
    }

    /**
     * @param geralMB the geralMB to set
     */
    public void setGeralMB(GeralMB geralMB) {
        this.geralMB = geralMB;
    }
}
