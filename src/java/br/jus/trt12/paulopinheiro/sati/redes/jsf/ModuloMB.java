package br.jus.trt12.paulopinheiro.sati.redes.jsf;

import br.jus.trt12.paulopinheiro.sati.geral.ejb.UnidadeFacade;
import br.jus.trt12.paulopinheiro.sati.geral.ejb.comum.AbstractFacade;
import br.jus.trt12.paulopinheiro.sati.geral.jsf.GeralMB;
import br.jus.trt12.paulopinheiro.sati.geral.jsf.comum.AbListaRestritaMB;
import br.jus.trt12.paulopinheiro.sati.geral.model.Municipio;
import br.jus.trt12.paulopinheiro.sati.geral.model.Unidade;
import br.jus.trt12.paulopinheiro.sati.redes.ejb.ModuloFacade;
import br.jus.trt12.paulopinheiro.sati.redes.model.Modulo;
import br.jus.trt12.paulopinheiro.sati.redes.model.TipoModulo;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ViewScoped
public class ModuloMB extends AbListaRestritaMB<Modulo> implements Serializable {
    @EJB private ModuloFacade moduloFacade;
    @EJB private UnidadeFacade unidadeFacade;
    @Inject private GeralMB geralMB;

    private List<TipoModulo> tiposModulo;
    private List<Unidade> unidades;

    public ModuloMB() {}

    public List<Modulo> getListaModulos() {
        return this.getLista();
    }

    public void setListaModulos(List<Modulo> listaModulos) {
        this.setLista(listaModulos);
    }

    public Modulo getModulo() {
        return this.getElemento();
    }

    public void setModulo(Modulo modulo) {
        this.setElemento(modulo);
    }

    public List<TipoModulo> getTiposModulo() {
        if (this.tiposModulo==null) this.tiposModulo = moduloFacade.tiposModulo();
        return tiposModulo;
    }

    public void setTiposModulo(List<TipoModulo> tiposModulo) {
        this.tiposModulo = tiposModulo;
    }

    public List<Unidade> getUnidades() {
        if (this.unidades==null) this.unidades = unidadeFacade.findByMunicipio(getMunicipio());
        return unidades;
    }

    public void setUnidades(List<Unidade> unidades) {
        this.unidades = unidades;
    }

    private Municipio getMunicipio() {
        return getGeralMB().getMunicipioSessao();
    }

    public GeralMB getGeralMB() {
        return geralMB;
    }

    public void setGeralMB(GeralMB geralMB) {
        this.geralMB = geralMB;
    }

    @Override
    protected AbstractFacade getFacade() {
        return this.moduloFacade;
    }

    @Override
    public boolean isNovoElemento() {
        return this.getModulo().getCodigo()==null || this.getModulo().getCodigo()==0;
    }

    @Override
    protected Modulo novainstanciaElemento() {
        return new Modulo();
    }

    @Override
    protected List<Modulo> getListaRestrita() {
        return this.moduloFacade.findByMunicipio(this.getMunicipio());
    }
}
