package br.jus.trt12.paulopinheiro.sati.calendario.jsf;

import br.jus.trt12.paulopinheiro.sati.calendario.ejb.ExcecaoTransferenciaFacade;
import br.jus.trt12.paulopinheiro.sati.calendario.model.ExcecaoTransferencia;
import br.jus.trt12.paulopinheiro.sati.calendario.model.Transferencia;
import br.jus.trt12.paulopinheiro.sati.geral.ejb.MunicipioFacade;
import br.jus.trt12.paulopinheiro.sati.geral.ejb.comum.AbstractFacade;
import br.jus.trt12.paulopinheiro.sati.geral.jsf.comum.AbListaRestritaMB;
import br.jus.trt12.paulopinheiro.sati.geral.model.Municipio;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class ExcecaoTransferenciaMB extends AbListaRestritaMB<ExcecaoTransferencia> implements Serializable {
    @EJB private ExcecaoTransferenciaFacade excecaoFacade;
    @EJB private MunicipioFacade municipioFacade;

    private Transferencia transferencia;

    private List<Municipio> municipios;

    public ExcecaoTransferenciaMB() {}

    public Transferencia getTransferencia() {
        return transferencia;
    }

    public void setTransferencia(Transferencia transferencia) {
        this.transferencia = transferencia;
    }

    public ExcecaoTransferencia getExcecaoTransferencia() {
        return this.getElemento();
    }

    public void setExcecaoTransferencia(ExcecaoTransferencia excecaoTransferencia) {
        this.setElemento(excecaoTransferencia);
    }

    public List<ExcecaoTransferencia> getListaExcecoes() {
        return this.getLista();
    }

    public void setListaExcecoes(List<ExcecaoTransferencia> listaExcecoes) {
        this.setLista(listaExcecoes);
    }

    public List<Municipio> getMunicipios() {
        if (this.municipios==null) this.municipios=municipioFacade.findAll();
        return municipios;
    }

    public void setMunicipios(List<Municipio> municipios) {
        this.municipios = municipios;
    }

    @Override
    protected List<ExcecaoTransferencia> getListaRestrita() {
        return this.excecaoFacade.findByTransferencia(getTransferencia());
    }

    @Override
    protected AbstractFacade getFacade() {
        return this.excecaoFacade;
    }

    @Override
    public boolean isNovoElemento() {
        return this.getExcecaoTransferencia().getCodigo()==null || this.getExcecaoTransferencia().getCodigo()==0;
    }

    @Override
    protected ExcecaoTransferencia novainstanciaElemento() {
        return new ExcecaoTransferencia(getTransferencia());
    }
}
