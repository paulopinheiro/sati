package br.jus.trt12.paulopinheiro.sati.calendario.jsf;

import br.jus.trt12.paulopinheiro.sati.calendario.ejb.ExcecaoTransferenciaFacade;
import br.jus.trt12.paulopinheiro.sati.calendario.model.ExcecaoTransferencia;
import br.jus.trt12.paulopinheiro.sati.calendario.model.Transferencia;
import br.jus.trt12.paulopinheiro.sati.geral.ejb.MunicipioFacade;
import br.jus.trt12.paulopinheiro.sati.geral.model.Municipio;
import br.jus.trt12.paulopinheiro.sati.util.ContextoJSF;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

@ManagedBean
@ViewScoped
public class ExcecaoTransferenciaMB implements Serializable {
    @EJB private ExcecaoTransferenciaFacade excecaoFacade;
    @EJB private MunicipioFacade municipioFacade;

    private Transferencia transferencia;

    private ExcecaoTransferencia excecaoTransferencia;
    private List<ExcecaoTransferencia> listaExcecoes;

    private List<Municipio> municipios;

    public ExcecaoTransferenciaMB() {}

    public void salvar(ActionEvent event) {
        try {
            excecaoFacade.salvar(getExcecaoTransferencia());
            setExcecaoTransferencia(null);
            setListaExcecoes(null);
        } catch (Exception ex) {
            mensagemErro(ex.getMessage());
        }
    }

    public void novo(ActionEvent event) {
        setExcecaoTransferencia(null);
        setListaExcecoes(null);
    }

    public void excluir(ActionEvent event) {
        try {
            excecaoFacade.remove(getExcecaoTransferencia());
            setExcecaoTransferencia(null);
            setListaExcecoes(null);
        } catch (Exception ex) {
            mensagemErro(ex.getMessage());
        }
    }

    public int getQuantExcecoes() {
        return getListaExcecoes().size();
    }

    public Transferencia getTransferencia() {
        return transferencia;
    }

    public void setTransferencia(Transferencia transferencia) {
        this.transferencia = transferencia;
    }

    public ExcecaoTransferencia getExcecaoTransferencia() {
        if (this.excecaoTransferencia==null) this.excecaoTransferencia = new ExcecaoTransferencia(getTransferencia());
        return excecaoTransferencia;
    }

    public void setExcecaoTransferencia(ExcecaoTransferencia excecaoTransferencia) {
        this.excecaoTransferencia = excecaoTransferencia;
    }

    public List<ExcecaoTransferencia> getListaExcecoes() {
        if (this.listaExcecoes==null) this.listaExcecoes = excecaoFacade.findByTransferencia(getTransferencia());
        return listaExcecoes;
    }

    public void setListaExcecoes(List<ExcecaoTransferencia> listaExcecoes) {
        this.listaExcecoes = listaExcecoes;
    }

    public List<Municipio> getMunicipios() {
        if (this.municipios==null) this.municipios=municipioFacade.findAll();
        return municipios;
    }

    public void setMunicipios(List<Municipio> municipios) {
        this.municipios = municipios;
    }

    private void mensagemErro(String mensagem) {
        ContextoJSF.getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,mensagem,null));
    }
}
