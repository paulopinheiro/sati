package br.jus.trt12.paulopinheiro.sati.geral.jsf;

import br.jus.trt12.paulopinheiro.sati.geral.ejb.AreaTIFacade;
import br.jus.trt12.paulopinheiro.sati.geral.ejb.ProgintFacade;
import br.jus.trt12.paulopinheiro.sati.geral.ejb.UnidadeFacade;
import br.jus.trt12.paulopinheiro.sati.geral.ejb.comum.AbstractFacade;
import br.jus.trt12.paulopinheiro.sati.geral.jsf.comum.AbListaMB;
import br.jus.trt12.paulopinheiro.sati.geral.model.AreaTI;
import br.jus.trt12.paulopinheiro.sati.geral.model.Municipio;
import br.jus.trt12.paulopinheiro.sati.geral.model.Progint;
import br.jus.trt12.paulopinheiro.sati.geral.model.Unidade;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ViewScoped
public class ProgintMB extends AbListaMB<Progint> implements Serializable {
    @EJB private ProgintFacade progintFacade;
    @EJB private AreaTIFacade areatiFacade;
    @EJB private UnidadeFacade unidadeFacade;
    @Inject private GeralMB geralMB;

    private List<Unidade> unidades;
    private List<AreaTI> areasTI;

    public ProgintMB() {}

    public List<Unidade> getUnidades() {
        if (this.unidades==null) this.unidades = this.unidadeFacade.findAll();
        return unidades;
    }

    public List<AreaTI> getAreasTI() {
        if (this.areasTI==null) this.areasTI = this.areatiFacade.findAll();
        return areasTI;
    }

    private Municipio getMunicipio() {
        return this.geralMB.getMunicipioSessao();
    }

    public Progint getProgint() {
        return this.getElemento();
    }

    public void setProgint(Progint progint) {
        this.setElemento(progint);
    }

    public List<Progint> getProgints() {
        return this.getLista();
    }

    public void setProgints(List<Progint> progints) {
        this.setLista(progints);
    }

    @Override
    protected AbstractFacade getFacade() {
        return this.progintFacade;
    }

    @Override
    public boolean isNovoElemento() {
        return (this.getProgint().getCodigo()==null)||(this.getProgint().getCodigo()==0);
    }

    @Override
    protected Progint novainstanciaElemento() {
        return new Progint();
    }
}
