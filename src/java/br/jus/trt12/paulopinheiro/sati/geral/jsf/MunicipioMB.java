package br.jus.trt12.paulopinheiro.sati.geral.jsf;

import br.jus.trt12.paulopinheiro.sati.geral.ejb.AreaTIFacade;
import br.jus.trt12.paulopinheiro.sati.geral.ejb.MunicipioFacade;
import br.jus.trt12.paulopinheiro.sati.geral.ejb.comum.AbstractFacade;
import br.jus.trt12.paulopinheiro.sati.geral.jsf.comum.AbListaMB;
import br.jus.trt12.paulopinheiro.sati.geral.model.AreaTI;
import br.jus.trt12.paulopinheiro.sati.geral.model.Municipio;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class MunicipioMB extends AbListaMB<Municipio> implements Serializable {
    @EJB private MunicipioFacade municipioFacade;
    @EJB private AreaTIFacade areaTIFacade;

    private List<AreaTI> areasTI;

    public MunicipioMB() {}

    public List<AreaTI> getAreasTI() {
        if (this.areasTI==null) this.areasTI = areaTIFacade.findAll();
        return areasTI;
    }

    public void setAreasTI(List<AreaTI> areasTI) {
        this.areasTI = areasTI;
    }

    public List<Municipio> getListaMunicipios() {
        return this.getLista();
    }

    public void setListaMunicipios(List<Municipio> listaMunicipios) {
        this.setLista(listaMunicipios);
    }

    public Municipio getMunicipio() {
        return this.getElemento();
    }

    public void setMunicipio(Municipio municipio) {
        this.setElemento(municipio);
    }

    @Override
    protected AbstractFacade getFacade() {
        return this.municipioFacade;
    }

    @Override
    public boolean isNovoElemento() {
        return (this.getMunicipio().getCodigo()==null)||(this.getMunicipio().getCodigo()==0);
    }

    @Override
    protected Municipio novainstanciaElemento() {
        return new Municipio();
    }

}
