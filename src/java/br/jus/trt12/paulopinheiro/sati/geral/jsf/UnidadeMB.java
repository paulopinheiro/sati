package br.jus.trt12.paulopinheiro.sati.geral.jsf;

import br.jus.trt12.paulopinheiro.sati.geral.ejb.UnidadeFacade;
import br.jus.trt12.paulopinheiro.sati.geral.model.Municipio;
import br.jus.trt12.paulopinheiro.sati.geral.model.Unidade;
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
public class UnidadeMB implements Serializable {
    @EJB private UnidadeFacade unidadeFacade;

    private Municipio municipio;

    private List<Unidade> listaUnidades;
    private Unidade unidade;

    public UnidadeMB() {}

    public void salvar(ActionEvent event) {
        try {
            unidadeFacade.salvar(getUnidade());
            setUnidade(null);
            setListaUnidades(null);
        } catch (Exception ex) {
            mensagemErro(ex.getMessage());
        }
    }

    public void novo(ActionEvent event) {
        setUnidade(null);
    }

    public void excluir(ActionEvent event) {
        try {
            unidadeFacade.remove(getUnidade());
            setUnidade(null);
            setListaUnidades(null);
        } catch (Exception ex) {
            mensagemErro(ex.getMessage());
        }
    }

    public int getQuantUnidades() {
        return getListaUnidades().size();
    }


    public List<Unidade> getListaUnidades() {
        if (this.listaUnidades==null) this.listaUnidades=unidadeFacade.findByMunicipio(getMunicipio());
        return listaUnidades;
    }

    public void setListaUnidades(List<Unidade> listaUnidades) {
        this.listaUnidades = listaUnidades;
    }

    public Municipio getMunicipio() {
        if (this.municipio==null) this.municipio = new Municipio();
        return municipio;
    }

    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
    }

    public Unidade getUnidade() {
        if (this.unidade==null) this.unidade = new Unidade(getMunicipio());
        return unidade;
    }

    public void setUnidade(Unidade unidade) {
        this.unidade = unidade;
    }

    private void mensagemErro(String mensagem) {
        ContextoJSF.getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,mensagem,null));
    }
}
