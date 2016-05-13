package br.jus.trt12.paulopinheiro.sati.equipamentos.jsf;

import br.jus.trt12.paulopinheiro.sati.equipamentos.ejb.EquipamentoFacade;
import br.jus.trt12.paulopinheiro.sati.equipamentos.ejb.LoteFacade;
import br.jus.trt12.paulopinheiro.sati.equipamentos.model.Equipamento;
import br.jus.trt12.paulopinheiro.sati.equipamentos.model.Historico;
import br.jus.trt12.paulopinheiro.sati.equipamentos.model.Lote;
import br.jus.trt12.paulopinheiro.sati.geral.ejb.UnidadeFacade;
import br.jus.trt12.paulopinheiro.sati.geral.ejb.comum.AbstractFacade;
import br.jus.trt12.paulopinheiro.sati.geral.jsf.GeralMB;
import br.jus.trt12.paulopinheiro.sati.geral.jsf.comum.AbBasicoMB;
import br.jus.trt12.paulopinheiro.sati.geral.model.Unidade;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;

@ManagedBean
@ViewScoped
public class EquipamentoMB extends AbBasicoMB<Equipamento> implements Serializable {
    @EJB private EquipamentoFacade equipamentoFacade;
    @EJB private UnidadeFacade unidadeFacade;
    @EJB private LoteFacade loteFacade;
    @ManagedProperty(value="#{geralMB}")
    private GeralMB geralMB;

    private List<Unidade> listaUnidades;
    private List<Lote> listaLotes;

    private Historico historico;

    public EquipamentoMB() {}

    public void baixar(ActionEvent evt) {
        try {
            this.equipamentoFacade.baixar(this.getElemento());
            mensagemSucesso("Equipamento baixado com sucesso");
            setElemento(null);
        } catch (Exception ex) {
            mensagemErro(ex.getLocalizedMessage());
        }
    }

    public Historico getHistorico() {
        if (this.historico==null) this.historico = new Historico(getEquipamento());
        return historico;
    }

    public void setHistorico(Historico historico) {
        this.historico = historico;
    }

    public boolean isNovoHistoricoB() {
        return this.getHistorico().getCodigo()==null || this.getHistorico().getCodigo() == 0;
    }

    public Equipamento getEquipamento() {
        return this.getElemento();
    }

    public void setEquipamento(Equipamento equipamento) {
        this.setElemento(equipamento);
    }

    public List<Unidade> getListaUnidades() {
        if (this.listaUnidades==null) this.listaUnidades = this.unidadeFacade.findByMunicipio(getGeralMB().getMunicipioSessao());
        return this.listaUnidades;
    }

    public void setListaUnidades(List<Unidade> listaUnidades) {
        this.listaUnidades = listaUnidades;
    }

    public List<Lote> getListaLotes() {
        if (this.listaLotes==null) this.listaLotes = this.loteFacade.findAll();
        return listaLotes;
    }

    public void setListaLotes(List<Lote> listaLotes) {
        this.listaLotes = listaLotes;
    }

    @Override
    protected AbstractFacade getFacade() {
        return this.equipamentoFacade;
    }

    @Override
    public boolean isNovoElemento() {
        return this.getEquipamento().getCodigo()==null||this.getEquipamento().getCodigo()==0;
    }

    @Override
    protected Equipamento novainstanciaElemento() {
        return new Equipamento();
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
