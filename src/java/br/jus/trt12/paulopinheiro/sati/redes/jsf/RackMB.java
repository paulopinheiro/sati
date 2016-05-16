package br.jus.trt12.paulopinheiro.sati.redes.jsf;

import br.jus.trt12.paulopinheiro.sati.geral.ejb.comum.AbstractFacade;
import br.jus.trt12.paulopinheiro.sati.geral.jsf.GeralMB;
import br.jus.trt12.paulopinheiro.sati.geral.jsf.comum.AbListaRestritaMB;
import br.jus.trt12.paulopinheiro.sati.geral.model.Municipio;
import br.jus.trt12.paulopinheiro.sati.redes.ejb.RackFacade;
import br.jus.trt12.paulopinheiro.sati.redes.model.Rack;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class RackMB extends AbListaRestritaMB<Rack> implements Serializable {
    @EJB private RackFacade rackFacade;
    @ManagedProperty(value="#{geralMB}")
    private GeralMB geralMB;

    public RackMB() {}

    public List<Rack> getListaRacks() {
        return this.getLista();
    }

    public void setListaRacks(List<Rack> listaRacks) {
        this.setLista(listaRacks);
    }

    public Rack getRack() {
        return this.getElemento();
    }

    public void setRack(Rack rack) {
        this.setElemento(rack);
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
        return this.rackFacade;
    }

    @Override
    public boolean isNovoElemento() {
        return this.getRack().getCodigo()==null || this.getRack().getCodigo()==0;
    }

    @Override
    protected Rack novainstanciaElemento() {
        return new Rack(this.getMunicipio());
    }

    @Override
    protected List<Rack> getListaRestrita() {
        return this.rackFacade.findByMunicipio(this.getMunicipio());
    }
}
