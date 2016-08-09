package br.jus.trt12.paulopinheiro.sati.equipamentos.jsf;

import br.jus.trt12.paulopinheiro.sati.equipamentos.ejb.EquipamentoFacade;
import br.jus.trt12.paulopinheiro.sati.equipamentos.ejb.TipoEquipamentoFacade;
import br.jus.trt12.paulopinheiro.sati.equipamentos.model.Equipamento;
import br.jus.trt12.paulopinheiro.sati.equipamentos.model.TipoEquipamento;
import br.jus.trt12.paulopinheiro.sati.geral.jsf.GeralMB;
import br.jus.trt12.paulopinheiro.sati.geral.model.Municipio;
import br.jus.trt12.paulopinheiro.sati.util.ContextoJSF;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.model.DualListModel;

@Named
@ViewScoped
public class MovEquipamentoMB implements Serializable {
    private DualListModel<Equipamento> dlmEquipamentos;

    private TipoEquipamento tipoEquipamento;
    private List<TipoEquipamento> tiposEquipamento;
    private List<Equipamento> listaEquipamentos;
    private boolean circular;

    @EJB private TipoEquipamentoFacade tipoEquipamentoFacade;
    @EJB private EquipamentoFacade equipamentoFacade;
    @Inject private GeralMB geralMB;

    public MovEquipamentoMB() {}

    public boolean isCircular() {
        return circular;
    }

    public boolean isListaLiberada() {
        return this.tipoEquipamento!=null;
    }

    public void setCircular(boolean circular) {
        this.circular = circular;
    }

    public void filtrar() {
        try {
            this.setListaEquipamentos(this.equipamentoFacade.findAtivosByMunicipioTipoEquipamento(this.getMunicipioSessao(),this.getTipoEquipamento()));
            setDlmEquipamentos(null);
        } catch (Exception ex) {
            mensagemErro(ex.getMessage());
        }
    }

    public void movimentar() {
        try {
            this.equipamentoFacade.movimentar(this.getDlmEquipamentos().getTarget(), this.isCircular());
            filtrar();
            mensagemSucesso("Equipamentos movimentados com sucesso.");
        } catch (Exception ex) {
            mensagemErro(ex.getMessage());
        }
    }

    public DualListModel<Equipamento> getDlmEquipamentos() {
        if (this.dlmEquipamentos==null) this.dlmEquipamentos=new DualListModel<Equipamento>(this.getListaEquipamentos(), new ArrayList<Equipamento>());
        return dlmEquipamentos;
    }

    public void setDlmEquipamentos(DualListModel<Equipamento> dlmEquipamentos) {
        this.dlmEquipamentos = dlmEquipamentos;
    }

    /**
     * @return the tipoEquipamento
     */
    public TipoEquipamento getTipoEquipamento() {
        return tipoEquipamento;
    }

    /**
     * @param tipoEquipamento the tipoEquipamento to set
     */
    public void setTipoEquipamento(TipoEquipamento tipoEquipamento) {
        this.tipoEquipamento = tipoEquipamento;
    }

    public List<TipoEquipamento> getTiposEquipamento() {
        if (this.tiposEquipamento==null) this.tiposEquipamento = tipoEquipamentoFacade.findAll();
        return tiposEquipamento;
    }

    public void setTiposEquipamento(List<TipoEquipamento> tiposEquipamento) {
        this.tiposEquipamento = tiposEquipamento;
    }

    private List<Equipamento> getListaEquipamentos() {
        if (this.listaEquipamentos==null) this.listaEquipamentos = new ArrayList<Equipamento>();
        return listaEquipamentos;
    }

    private void setListaEquipamentos(List<Equipamento> listaEquipamentos) {
        this.listaEquipamentos = listaEquipamentos;
    }

    private Municipio getMunicipioSessao() {
        return getGeralMB().getMunicipioSessao();
    }

    private void mensagemErro(String mensagem) {
        ContextoJSF.getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,mensagem,null));
    }

    protected void mensagemSucesso(String mensagem) {
        ContextoJSF.getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,mensagem,null));
    }

    public boolean isProntoMovimentar() {
        return this.getDlmEquipamentos()!=null &&
               this.getDlmEquipamentos().getTarget()!=null &&
               this.getDlmEquipamentos().getTarget().size()>1;
    }

    public GeralMB getGeralMB() {
        return geralMB;
    }

    public void setGeralMB(GeralMB geralMB) {
        this.geralMB = geralMB;
    }
}
