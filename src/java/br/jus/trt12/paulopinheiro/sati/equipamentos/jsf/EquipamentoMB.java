package br.jus.trt12.paulopinheiro.sati.equipamentos.jsf;

import br.jus.trt12.paulopinheiro.sati.equipamentos.ejb.EquipamentoFacade;
import br.jus.trt12.paulopinheiro.sati.equipamentos.ejb.LoteFacade;
import br.jus.trt12.paulopinheiro.sati.equipamentos.ejb.ModeloFacade;
import br.jus.trt12.paulopinheiro.sati.equipamentos.model.Equipamento;
import br.jus.trt12.paulopinheiro.sati.equipamentos.model.Historico;
import br.jus.trt12.paulopinheiro.sati.equipamentos.model.Lote;
import br.jus.trt12.paulopinheiro.sati.equipamentos.model.Modelo;
import br.jus.trt12.paulopinheiro.sati.equipamentos.model.TipoEquipamento;
import br.jus.trt12.paulopinheiro.sati.exceptions.SatiLogicalException;
import br.jus.trt12.paulopinheiro.sati.geral.ejb.UnidadeFacade;
import br.jus.trt12.paulopinheiro.sati.geral.ejb.UsuarioFinalFacade;
import br.jus.trt12.paulopinheiro.sati.geral.ejb.comum.AbstractFacade;
import br.jus.trt12.paulopinheiro.sati.geral.jsf.GeralMB;
import br.jus.trt12.paulopinheiro.sati.geral.jsf.comum.AbBasicoMB;
import br.jus.trt12.paulopinheiro.sati.geral.model.Unidade;
import br.jus.trt12.paulopinheiro.sati.geral.model.UsuarioFinal;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ViewScoped
public class EquipamentoMB extends AbBasicoMB<Equipamento> implements Serializable {
    @EJB private EquipamentoFacade equipamentoFacade;
    @EJB private UnidadeFacade unidadeFacade;
    @EJB private LoteFacade loteFacade;
    @EJB private ModeloFacade modeloFacade;
    @EJB private UsuarioFinalFacade usuarioFacade;
    @Inject private GeralMB geralMB;

    private List<Unidade> listaUnidades;
    private List<Modelo> listaModelos;
    private List<Lote> listaLotes;
    private List<UsuarioFinal> listaUsuarios;

    private String linkVoltar;
    private TipoEquipamento tipoEquipamento;
    private Modelo filtroModelo;

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
        if (this.listaLotes==null) {
            this.listaLotes = this.loteFacade.findByModelo(getFiltroModelo());
        }
        return listaLotes;
    }

    public void setListaLotes(List<Lote> listaLotes) {
        this.listaLotes = listaLotes;
    }

    public List<UsuarioFinal> getListaUsuarios() {
        if (listaUsuarios==null) {
            if (getEquipamento().getUnidade()==null)
                setListaUsuarios(new ArrayList<UsuarioFinal>());
            else {
                try {
                    setListaUsuarios(usuarioFacade.findByUnidade(getEquipamento().getUnidade()));
                } catch (SatiLogicalException ex) {
                    mensagemErro(ex.getMessage());
                }
            }
        }
        return listaUsuarios;
    }

    public void atualizalistaUsuarios() {
        setListaUsuarios(null);
    }

    public void setListaUsuarios(List<UsuarioFinal> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
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

    public GeralMB getGeralMB() {
        return geralMB;
    }

    public void setGeralMB(GeralMB geralMB) {
        this.geralMB = geralMB;
    }

    public String getLinkVoltar() {
        return linkVoltar;
    }

    public void setLinkVoltar(String linkVoltar) {
        this.linkVoltar = linkVoltar;
    }

    public TipoEquipamento getTipoEquipamento() {
        return tipoEquipamento;
    }

    public void setTipoEquipamento(TipoEquipamento tipoEquipamento) {
        this.tipoEquipamento = tipoEquipamento;
    }

    public Modelo getFiltroModelo() {
        if ((!this.isNovoElemento())&&filtroModelo==null) filtroModelo=getElemento().getLote().getModelo();
        return filtroModelo;
    }

    public void setFiltroModelo(Modelo filtroModelo) {
        this.filtroModelo = filtroModelo;
    }

    public List<Modelo> getListaModelos() {
        if (listaModelos==null) return modeloFacade.findByTipoEquipamento(getTipoEquipamento());
        return listaModelos;
    }

    public void setListaModelos(List<Modelo> listaModelos) {
        this.listaModelos = listaModelos;
    }

    public void atualizaListaLotes(AjaxBehaviorEvent evt) {
        this.setListaLotes(null);
    }
}
