package br.jus.trt12.paulopinheiro.sati.equipamentos.jsf;

import br.jus.trt12.paulopinheiro.sati.equipamentos.ejb.LoteFacade;
import br.jus.trt12.paulopinheiro.sati.equipamentos.model.Lote;
import br.jus.trt12.paulopinheiro.sati.equipamentos.model.Modelo;
import br.jus.trt12.paulopinheiro.sati.geral.ejb.comum.AbstractFacade;
import br.jus.trt12.paulopinheiro.sati.geral.jsf.comum.AbListaRestritaMB;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class LoteMB extends AbListaRestritaMB<Lote> implements Serializable {
    private Modelo modelo;
    
    @EJB private LoteFacade loteFacade;

    public LoteMB() {}

    public Lote getLote() {
        return this.getElemento();
    }

    public void setLote(Lote lote) {
        this.setElemento(lote);
    }

    public List<Lote> getListaLotes() {
        return this.getLista();
    }

    public void setListaLotes(List<Lote> listaLotes) {
        this.setLista(listaLotes);
    }

    @Override
    protected AbstractFacade getFacade() {
        return this.loteFacade;
    }

    @Override
    public boolean isNovoElemento() {
        return this.getLote().getCodigo()==null||this.getLote().getCodigo()==0;
    }

    @Override
    protected Lote novainstanciaElemento() {
        return new Lote(getModelo());
    }

    public Modelo getModelo() {
        return modelo;
    }

    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
    }

    @Override
    protected List<Lote> getListaRestrita() {
        return this.loteFacade.findByModelo(this.getModelo());
    }
}
