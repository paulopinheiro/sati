package br.jus.trt12.paulopinheiro.sati.geral.jsf;

import br.jus.trt12.paulopinheiro.sati.geral.ejb.UnidadeFacade;
import br.jus.trt12.paulopinheiro.sati.geral.ejb.UsuarioFinalFacade;
import br.jus.trt12.paulopinheiro.sati.geral.ejb.comum.AbstractFacade;
import br.jus.trt12.paulopinheiro.sati.geral.jsf.comum.AbListaRestritaMB;
import br.jus.trt12.paulopinheiro.sati.geral.model.Municipio;
import br.jus.trt12.paulopinheiro.sati.geral.model.Unidade;
import br.jus.trt12.paulopinheiro.sati.geral.model.UsuarioFinal;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

@Named(value = "usuarioFinalMB")
@ViewScoped
public class UsuarioFinalMB extends AbListaRestritaMB<UsuarioFinal> implements Serializable {
    @EJB private UsuarioFinalFacade usuarioFacade;
    @EJB private UnidadeFacade unidadeFacade;
    @Inject private GeralMB geralMB;

    private List<Unidade> unidades;

    public UsuarioFinalMB() {}

    public List<Unidade> getUnidades() {
        if (this.unidades==null) this.unidades=unidadeFacade.findByMunicipio(this.getMunicipio());
        return unidades;
    }

    public void setUnidades(List<Unidade> unidades) {
        this.unidades = unidades;
    }

    public Municipio getMunicipio() {
        return geralMB.getMunicipioSessao();
    }

    public UsuarioFinal getUsuarioFinal() {
        return this.getElemento();
    }

    public void setUsuarioFinal(UsuarioFinal usuarioFinal) {
        this.setElemento(usuarioFinal);
    }

    public List<UsuarioFinal> getListaUsuarios() {
        return this.getLista();
    }

    public void setListaUsuarios(List<UsuarioFinal> usuarios) {
        this.setLista(usuarios);
    }

    @Override
    protected AbstractFacade getFacade() {
        return this.usuarioFacade;
    }

    @Override
    public boolean isNovoElemento() {
        return ((this.getUsuarioFinal().getId()==null)||(this.getUsuarioFinal().getId()==0));
    }

    @Override
    protected UsuarioFinal novainstanciaElemento() {
        return new UsuarioFinal();
    }

    @Override
    protected List<UsuarioFinal> getListaRestrita() {
        return this.usuarioFacade.findByMunicipio(this.getMunicipio());
    }

    @Override
    public void filtrar(ActionEvent evt) {
        if (this.getUsuarioFinal().getUnidade()==null)this.getUsuarioFinal().setUnidade(new Unidade(this.getMunicipio()));
        super.filtrar(evt);
    }
}
