package br.jus.trt12.paulopinheiro.sati.equipamentos.jsf;

import br.jus.trt12.paulopinheiro.sati.equipamentos.ejb.EquipamentoFacade;
import br.jus.trt12.paulopinheiro.sati.equipamentos.ejb.TipoEquipamentoFacade;
import br.jus.trt12.paulopinheiro.sati.equipamentos.model.Equipamento;
import br.jus.trt12.paulopinheiro.sati.equipamentos.model.TipoEquipamento;
import br.jus.trt12.paulopinheiro.sati.exceptions.SatiLogicalException;
import br.jus.trt12.paulopinheiro.sati.geral.ejb.UnidadeFacade;
import br.jus.trt12.paulopinheiro.sati.geral.jsf.GeralMB;
import br.jus.trt12.paulopinheiro.sati.geral.model.Unidade;
import br.jus.trt12.paulopinheiro.sati.util.ContextoJSF;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

@Named
@ViewScoped
public class RecuperaEquipamentosMB implements Serializable {
    @EJB private EquipamentoFacade equipamentoFacade;
    @EJB private TipoEquipamentoFacade tipoEquipamentoFacade;
    @EJB private UnidadeFacade unidadeFacade;
    @Inject private GeralMB geralMB;

    private TipoEquipamento tipo;
    private List<Equipamento> listaBaixados;
    private Equipamento equipamento;
    private String filtroTombo;

    public RecuperaEquipamentosMB() {}

    public void limpar(ActionEvent evt) {
        setEquipamento(null);
        setListaBaixados(null);
        setFiltroTombo("");
    }

    public void buscarPorTombo(ActionEvent evt) {
        try {
            setEquipamento(equipamentoFacade.findBaixadoByTombo(getFiltroTombo()));
        } catch (SatiLogicalException ex) {
            mensagemErro(ex.getMessage());
        }
    }

    public void recuperar(ActionEvent evt) {
        try {
            equipamentoFacade.reativar(this.getEquipamento());
            mensagemSucesso("Equipamento de tombo " + this.getEquipamento().getTombo() + " recuperado com sucesso");
            setEquipamento(null);
            setListaBaixados(null);
        } catch (SatiLogicalException ex) {
            mensagemErro(ex.getMessage());
        }
    }

    public void atualizaTipo(ActionEvent evt) {
        System.out.println("Buscando baixados do tipo " + tipo.getNome());
        setFiltroTombo("");
        setListaBaixados(null);
    }

    public List<TipoEquipamento> getListaTiposEquipamento() {
        return tipoEquipamentoFacade.findAll();
    }

    public List<Unidade> getListaUnidades() {
        return unidadeFacade.findByMunicipio(geralMB.getMunicipioSessao());
    }

    public List<Equipamento> getListaBaixados() {
        if (this.listaBaixados==null) this.listaBaixados = equipamentoFacade.findBaixadosByTipo(this.getTipo());
        return listaBaixados;
    }

    public void setListaBaixados(List<Equipamento> listaBaixados) {
        this.listaBaixados = listaBaixados;
    }

    public Equipamento getEquipamento() {
        if (equipamento==null) this.equipamento= new Equipamento();
        return equipamento;
    }

    public void setEquipamento(Equipamento equipamento) {
        this.equipamento = equipamento;
    }

    public String getFiltroTombo() {
        if (this.filtroTombo==null) this.filtroTombo = "";
        return filtroTombo;
    }

    public void setFiltroTombo(String filtroTombo) {
        this.filtroTombo = filtroTombo;
    }

    public TipoEquipamento getTipo() {
        if (tipo==null) return new TipoEquipamento();
        return tipo;
    }

    public void setTipo(TipoEquipamento tipo) {
        this.tipo = tipo;
    }

    public boolean isVazioEquip() {
        return ((this.getEquipamento().getCodigo()==null)||(this.getEquipamento().getCodigo()==0));
    }

    protected void mensagemErro(String mensagem) {
        ContextoJSF.getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,mensagem,null));
    }

    protected void mensagemSucesso(String mensagem) {
        ContextoJSF.getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,mensagem,null));
    }

    protected void mensagemAviso(String mensagem) {
        ContextoJSF.getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,mensagem,null));
    }
}
