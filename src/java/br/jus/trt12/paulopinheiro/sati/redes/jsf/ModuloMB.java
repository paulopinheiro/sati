package br.jus.trt12.paulopinheiro.sati.redes.jsf;

import br.jus.trt12.paulopinheiro.sati.geral.ejb.UnidadeFacade;
import br.jus.trt12.paulopinheiro.sati.geral.jsf.GeralMB;
import br.jus.trt12.paulopinheiro.sati.geral.model.Municipio;
import br.jus.trt12.paulopinheiro.sati.geral.model.Unidade;
import br.jus.trt12.paulopinheiro.sati.redes.ejb.ModuloFacade;
import br.jus.trt12.paulopinheiro.sati.redes.model.Modulo;
import br.jus.trt12.paulopinheiro.sati.redes.model.TipoModulo;
import br.jus.trt12.paulopinheiro.sati.util.ContextoJSF;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

@ManagedBean
@ViewScoped
public class ModuloMB implements Serializable {
    @EJB private ModuloFacade moduloFacade;
    @EJB private UnidadeFacade unidadeFacade;
    @ManagedProperty(value="#{geralMB}")
    private GeralMB geralMB;

    private Modulo modulo;
    private List<Modulo> listaModulos;

    private List<TipoModulo> tiposModulo;
    private List<Unidade> unidades;

    public ModuloMB() {}

    public void salvar(ActionEvent event) {
        try {
            moduloFacade.salvar(getModulo());
            setModulo(null);
            setListaModulos(null);
        } catch (Exception ex) {
            mensagemErro(ex.getMessage());
        }
    }

    public void novo(ActionEvent event) {
        setModulo(null);
    }

    public void excluir(ActionEvent event) {
        try {
            moduloFacade.remove(getModulo());
            setModulo(null);
            setListaModulos(null);
        } catch (Exception ex) {
            mensagemErro(ex.getMessage());
        }
    }

    public int getQuantModulos() {
        return getListaModulos().size();
    }

    public List<Modulo> getListaModulos() {
        if (this.listaModulos==null) this.listaModulos = moduloFacade.findByMunicipio(getMunicipio());
        return listaModulos;
    }

    public void setListaModulos(List<Modulo> listaModulos) {
        this.listaModulos = listaModulos;
    }

    public Modulo getModulo() {
        if (this.modulo==null) this.modulo = new Modulo();
        return modulo;
    }

    public void setModulo(Modulo modulo) {
        this.modulo = modulo;
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

    private void mensagemErro(String mensagem) {
        ContextoJSF.getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,mensagem,null));
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
