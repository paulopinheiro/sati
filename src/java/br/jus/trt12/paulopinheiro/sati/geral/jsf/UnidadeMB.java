package br.jus.trt12.paulopinheiro.sati.geral.jsf;

import br.jus.trt12.paulopinheiro.sati.geral.ejb.UnidadeFacade;
import br.jus.trt12.paulopinheiro.sati.geral.ejb.comum.AbstractFacade;
import br.jus.trt12.paulopinheiro.sati.geral.jsf.comum.AbListaRestritaMB;
import br.jus.trt12.paulopinheiro.sati.geral.model.Municipio;
import br.jus.trt12.paulopinheiro.sati.geral.model.Unidade;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class UnidadeMB extends AbListaRestritaMB<Unidade> implements Serializable {
    @EJB private UnidadeFacade unidadeFacade;

    private Municipio municipio;

    public UnidadeMB() {}

    public List<Unidade> getListaUnidades() {
        return this.getLista();
    }

    public void setListaUnidades(List<Unidade> listaUnidades) {
        setLista(listaUnidades);
    }

    public Municipio getMunicipio() {
        if (this.municipio==null) this.municipio = new Municipio();
        return municipio;
    }

    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
    }

    public Unidade getUnidade() {
        return this.getElemento();
    }

    public void setUnidade(Unidade unidade) {
        this.setElemento(unidade);
    }

    @Override
    protected List<Unidade> getListaRestrita() {
        return this.unidadeFacade.findByMunicipio(this.getMunicipio());
    }

    @Override
    protected AbstractFacade getFacade() {
        return this.unidadeFacade;
    }

    @Override
    public boolean isNovoElemento() {
        return this.getUnidade().getCodigo()==null||this.getUnidade().getCodigo()==0;
    }

    @Override
    protected Unidade novainstanciaElemento() {
        return new Unidade(this.getMunicipio());
    }
}
