package br.jus.trt12.paulopinheiro.sati.calendario.jsf;

import br.jus.trt12.paulopinheiro.sati.calendario.ejb.ExcecaoFeriadoNacionalFacade;
import br.jus.trt12.paulopinheiro.sati.calendario.model.ExcecaoFeriadoNacional;
import br.jus.trt12.paulopinheiro.sati.calendario.model.Feriado;
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
public class ExcecaoFeriadoMB implements Serializable {
    @EJB private ExcecaoFeriadoNacionalFacade excecaoFeriadoFacade;
    @EJB private MunicipioFacade municipioFacade;

    private Feriado feriado;
    private String linkVoltar;

    private ExcecaoFeriadoNacional excecaoFeriadoNacional;
    private List<ExcecaoFeriadoNacional> listaExcecoesFeriados;

    private List<Municipio> municipios;

    public ExcecaoFeriadoMB() {}

    public void salvar(ActionEvent event) {
        try {
            excecaoFeriadoFacade.salvar(getExcecaoFeriadoNacional());
            setExcecaoFeriadoNacional(null);
            setListaExcecoesFeriados(null);
        } catch (Exception ex) {
            mensagemErro(ex.getMessage());
        }
    }

    public void novo(ActionEvent event) {
        setExcecaoFeriadoNacional(null);
        setListaExcecoesFeriados(null);
    }

    public void excluir(ActionEvent event) {
        try {
            excecaoFeriadoFacade.remove(getExcecaoFeriadoNacional());
            setExcecaoFeriadoNacional(null);
            setListaExcecoesFeriados(null);
        } catch (Exception ex) {
            mensagemErro(ex.getMessage());
        }
    }

    public String voltar() {
        return getLinkVoltar();
    }

    public int getQuantExcecoes() {
        return getListaExcecoesFeriados().size();
    }

    public Feriado getFeriado() {
        return feriado;
    }

    public void setFeriado(Feriado feriado) {
        this.feriado = feriado;
    }

    public ExcecaoFeriadoNacional getExcecaoFeriadoNacional() {
        if (this.excecaoFeriadoNacional==null) this.excecaoFeriadoNacional = new ExcecaoFeriadoNacional(getFeriado());
        return excecaoFeriadoNacional;
    }

    public void setExcecaoFeriadoNacional(ExcecaoFeriadoNacional excecaoFeriadoNacional) {
        this.excecaoFeriadoNacional = excecaoFeriadoNacional;
    }

    public List<ExcecaoFeriadoNacional> getListaExcecoesFeriados() {
        if (this.listaExcecoesFeriados==null) this.listaExcecoesFeriados = excecaoFeriadoFacade.findByFeriado(getFeriado());
        return listaExcecoesFeriados;
    }

    public void setListaExcecoesFeriados(List<ExcecaoFeriadoNacional> listaExcecoesFeriados) {
        this.listaExcecoesFeriados = listaExcecoesFeriados;
    }

    public List<Municipio> getMunicipios() {
        if (this.municipios==null) this.municipios=municipioFacade.findAll();
        return municipios;
    }

    public void setMunicipios(List<Municipio> municipios) {
        this.municipios = municipios;
    }

    public String getLinkVoltar() {
        return linkVoltar;
    }

    public void setLinkVoltar(String linkVoltar) {
        this.linkVoltar = linkVoltar;
    }

    private void mensagemErro(String mensagem) {
        ContextoJSF.getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,mensagem,null));
    }
}
