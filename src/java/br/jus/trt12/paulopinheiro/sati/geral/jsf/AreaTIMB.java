package br.jus.trt12.paulopinheiro.sati.geral.jsf;
import br.jus.trt12.paulopinheiro.sati.geral.ejb.AreaTIFacade;
import br.jus.trt12.paulopinheiro.sati.geral.ejb.MunicipioFacade;
import br.jus.trt12.paulopinheiro.sati.geral.ejb.comum.AbstractFacade;
import br.jus.trt12.paulopinheiro.sati.geral.jsf.comum.AbListaMB;
import br.jus.trt12.paulopinheiro.sati.geral.model.AreaTI;
import br.jus.trt12.paulopinheiro.sati.geral.model.Municipio;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class AreaTIMB extends AbListaMB<AreaTI> implements Serializable {
    @EJB private AreaTIFacade areaTIFacade;
    @EJB private MunicipioFacade municipioFacade;

    private List<Municipio> municipiosArea;

    public AreaTIMB() {}

    public AreaTI getAreaTI() {
        return this.getElemento();
    }

    public void setAreaTI(AreaTI areaTI) {
        this.setElemento(areaTI);
    }

    public List<AreaTI> getListaAreasTI() {
        return this.getLista();
    }

    public void setListaAreasTI(List<AreaTI> listaAreasTI) {
        this.setLista(listaAreasTI);
    }

    public List<Municipio> getMunicipiosArea() {
        this.municipiosArea = municipioFacade.findByAreaTI(getAreaTI());
        if (this.municipiosArea==null) this.municipiosArea=new ArrayList<Municipio>();
        return municipiosArea;
    }

    public void setMunicipiosArea(List<Municipio> municipiosArea) {
        this.municipiosArea = municipiosArea;
    }

    @Override
    protected AbstractFacade getFacade() {
        return this.areaTIFacade;
    }

    @Override
    public boolean isNovoElemento() {
        return (this.getAreaTI().getCodigo()==null)||(this.getAreaTI().getCodigo()==0);
    }

    @Override
    protected AreaTI novainstanciaElemento() {
        return new AreaTI();
    }
}
