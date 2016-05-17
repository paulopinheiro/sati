package br.jus.trt12.paulopinheiro.sati.redes.jsf;

import br.jus.trt12.paulopinheiro.sati.geral.ejb.comum.AbstractFacade;
import br.jus.trt12.paulopinheiro.sati.geral.jsf.comum.AbListaRestritaMB;
import br.jus.trt12.paulopinheiro.sati.redes.ejb.TomadaRemotaFacade;
import br.jus.trt12.paulopinheiro.sati.redes.model.Modulo;
import br.jus.trt12.paulopinheiro.sati.redes.model.TipoConector;
import br.jus.trt12.paulopinheiro.sati.redes.model.TomadaRemota;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class TomadaRemotaMB extends AbListaRestritaMB<TomadaRemota> implements Serializable {
    @EJB private TomadaRemotaFacade tomadaRemotaFacade;

    private Modulo modulo;

    private List<TipoConector> tiposConector;

    public TomadaRemotaMB() {}

    public List<TomadaRemota> getListaTomadasRemotas() {
        return this.getLista();
    }

    public void setListaTomadasRemotas(List<TomadaRemota> listaTomadasRemotas) {
        this.setLista(listaTomadasRemotas);
    }

    public Modulo getModulo() {
        if (this.modulo==null) this.modulo=new Modulo();
        return modulo;
    }

    public void setModulo(Modulo modulo) {
        this.modulo = modulo;
    }

    public List<TipoConector> getTiposConector() {
        if (this.tiposConector==null) this.tiposConector=tomadaRemotaFacade.tiposConector();
        return tiposConector;
    }

    public void setTiposConector(List<TipoConector> tiposConector) {
        this.tiposConector = tiposConector;
    }

    public TomadaRemota getTomadaRemota() {
        return this.getElemento();
    }

    public void setTomadaRemota(TomadaRemota tomadaRemota) {
        this.setElemento(tomadaRemota);
    }

    @Override
    protected List<TomadaRemota> getListaRestrita() {
        return this.tomadaRemotaFacade.findByModulo(this.getModulo());
    }

    @Override
    protected AbstractFacade getFacade() {
        return this.tomadaRemotaFacade;
    }

    @Override
    public boolean isNovoElemento() {
        return this.getTomadaRemota().getCodigo()==null || this.getTomadaRemota().getCodigo()==0;
    }

    @Override
    protected TomadaRemota novainstanciaElemento() {
        return new TomadaRemota(this.getModulo());
    }
}
