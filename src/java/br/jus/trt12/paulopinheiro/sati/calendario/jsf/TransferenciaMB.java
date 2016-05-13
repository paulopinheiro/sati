package br.jus.trt12.paulopinheiro.sati.calendario.jsf;

import br.jus.trt12.paulopinheiro.sati.calendario.ejb.FeriadoFacade;
import br.jus.trt12.paulopinheiro.sati.calendario.ejb.TransferenciaFacade;
import br.jus.trt12.paulopinheiro.sati.calendario.model.Feriado;
import br.jus.trt12.paulopinheiro.sati.calendario.model.Transferencia;
import br.jus.trt12.paulopinheiro.sati.geral.ejb.MunicipioFacade;
import br.jus.trt12.paulopinheiro.sati.geral.model.Municipio;
import br.jus.trt12.paulopinheiro.sati.util.ContextoJSF;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

@ManagedBean
@ViewScoped
public class TransferenciaMB implements Serializable {
    @EJB private FeriadoFacade feriadoFacade;
    @EJB private TransferenciaFacade transferenciaFacade;
    @EJB private MunicipioFacade municipioFacade;

    private Integer ano;
    private Date novaData;

    private Transferencia transferencia;
    private List<Transferencia> listaTransferencias;

    private List<Feriado> feriados;
    private List<Municipio> municipios;

    public TransferenciaMB() {}

    public void anoAnterior(ActionEvent event) {
        setAno(getAno()-1);
        setListaTransferencias(null);
        setTransferencia(null);
    }

    public void anoSeguinte(ActionEvent event) {
        setAno(getAno()+1);
        setListaTransferencias(null);
        setTransferencia(null);
    }

    public void pesquisar(ActionEvent event) {
        setTransferencia(null);
        setListaTransferencias(null);
    }

    public void salvar(ActionEvent event) {
        try {
            getTransferencia().setAno(getAno());
            getTransferencia().setNovoDia(this.getNovoDia());
            getTransferencia().setNovoMes(this.getNovoMes());
            transferenciaFacade.salvar(getTransferencia());
            setTransferencia(null);
            setListaTransferencias(null);
        } catch (Exception ex) {
            mensagemErro(ex.getMessage());
        }
    }

    public void novo(ActionEvent event) {
        setTransferencia(null);
        setListaTransferencias(null);
        setNovaData(null);
    }

    public void excluir(ActionEvent event) {
        try {
            transferenciaFacade.remove(getTransferencia());
            setTransferencia(null);
            setListaTransferencias(null);
            setNovaData(null);
        } catch (Exception ex) {
            mensagemErro(ex.getMessage());
        }
    }

    public int getQuantTransferencias() {
        return getListaTransferencias().size();
    }

    private Integer anoAtual() {
        Date hoje = new Date();
        SimpleDateFormat sdfAno = new SimpleDateFormat("yyyy");
        return Integer.valueOf(sdfAno.format(hoje));
    }

    public Integer getAno() {
        if (this.ano==null) this.ano = anoAtual();
        return ano;
    }

    private int getNovoDia() {
        if (this.novaData==null) return 0;
        SimpleDateFormat sdfAno = new SimpleDateFormat("dd");
        return Integer.valueOf(sdfAno.format(this.novaData));
    }

    private int getNovoMes() {
        if (this.novaData==null) return 0;
        SimpleDateFormat sdfAno = new SimpleDateFormat("MM");
        return Integer.valueOf(sdfAno.format(this.novaData));
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public List<Feriado> getFeriados() {
        if (this.feriados==null) this.feriados = feriadoFacade.findAll();
        return feriados;
    }

    public void setFeriados(List<Feriado> feriados) {
        this.feriados = feriados;
    }

    public List<Transferencia> getListaTransferencias() {
        if (this.listaTransferencias==null) this.listaTransferencias = transferenciaFacade.findByAno(getAno());
        return listaTransferencias;
    }

    public void setListaTransferencias(List<Transferencia> listaTransferencias) {
        this.listaTransferencias = listaTransferencias;
    }

    public List<Municipio> getMunicipios() {
        if (this.municipios==null) this.municipios = municipioFacade.findAll();
        return municipios;
    }

    public void setMunicipios(List<Municipio> municipios) {
        this.municipios = municipios;
    }

    public Date getNovaData() {
        this.novaData = getTransferencia().getNovaData();
        return novaData;
    }

    public void setNovaData(Date novaData) {
        this.novaData = novaData;
    }

    public Transferencia getTransferencia() {
        if (this.transferencia==null) this.transferencia = new Transferencia(getAno());
        return transferencia;
    }

    public void setTransferencia(Transferencia transferencia) {
        this.transferencia = transferencia;
    }

    private void mensagemErro(String mensagem) {
        ContextoJSF.getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,mensagem,null));
    }
}
