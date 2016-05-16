package br.jus.trt12.paulopinheiro.sati.calendario.jsf;

import br.jus.trt12.paulopinheiro.sati.calendario.ejb.FeriadoFacade;
import br.jus.trt12.paulopinheiro.sati.calendario.ejb.TransferenciaFacade;
import br.jus.trt12.paulopinheiro.sati.calendario.model.Feriado;
import br.jus.trt12.paulopinheiro.sati.calendario.model.Transferencia;
import br.jus.trt12.paulopinheiro.sati.geral.ejb.MunicipioFacade;
import br.jus.trt12.paulopinheiro.sati.geral.ejb.comum.AbstractFacade;
import br.jus.trt12.paulopinheiro.sati.geral.jsf.comum.AbListaRestritaMB;
import br.jus.trt12.paulopinheiro.sati.geral.model.Municipio;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

@ManagedBean
@ViewScoped
public class TransferenciaMB extends AbListaRestritaMB<Transferencia> implements Serializable {
    @EJB private FeriadoFacade feriadoFacade;
    @EJB private TransferenciaFacade transferenciaFacade;
    @EJB private MunicipioFacade municipioFacade;

    private Integer ano;
    private Date novaData;

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
        return this.getLista();
    }

    public void setListaTransferencias(List<Transferencia> listaTransferencias) {
        this.setLista(listaTransferencias);
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
        getTransferencia().setAno(getAno());
        getTransferencia().setNovoDia(this.getNovoDia());
        getTransferencia().setNovoMes(this.getNovoMes());
    }

    public Transferencia getTransferencia() {
        return this.getElemento();
    }

    public void setTransferencia(Transferencia transferencia) {
        this.setElemento(transferencia);
    }

    @Override
    protected List<Transferencia> getListaRestrita() {
        return this.transferenciaFacade.findByAno(this.getAno());
    }

    @Override
    protected AbstractFacade getFacade() {
        return this.transferenciaFacade;
    }

    @Override
    public boolean isNovoElemento() {
        return this.getTransferencia().getCodigo()==null || this.getTransferencia().getCodigo()==0;
    }

    @Override
    protected Transferencia novainstanciaElemento() {
        return new Transferencia(this.getAno());
    }
}
