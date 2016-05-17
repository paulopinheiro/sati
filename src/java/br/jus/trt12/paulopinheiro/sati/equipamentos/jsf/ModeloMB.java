package br.jus.trt12.paulopinheiro.sati.equipamentos.jsf;

import br.jus.trt12.paulopinheiro.sati.equipamentos.ejb.ModeloFacade;
import br.jus.trt12.paulopinheiro.sati.equipamentos.ejb.TipoEquipamentoFacade;
import br.jus.trt12.paulopinheiro.sati.equipamentos.model.Modelo;
import br.jus.trt12.paulopinheiro.sati.equipamentos.model.TipoEquipamento;
import br.jus.trt12.paulopinheiro.sati.geral.ejb.comum.AbstractFacade;
import br.jus.trt12.paulopinheiro.sati.geral.jsf.comum.AbListaMB;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class ModeloMB extends AbListaMB<Modelo> implements Serializable{
    @EJB private ModeloFacade modeloFacade;
    @EJB private TipoEquipamentoFacade tipoEquipamentoFacade;
    private List<TipoEquipamento> tiposEquipamento;

    public ModeloMB() {}

    public List<Modelo> getListaModelos() {
        return this.getLista();
    }

    public void setListaModelos(List<Modelo> listaModelos) {
        this.setLista(listaModelos);
    }

    public Modelo getModelo() {
        return this.getElemento();
    }

    public void setModelo(Modelo modelo) {
        this.setElemento(modelo);
    }

    @Override
    protected AbstractFacade getFacade() {
        return this.modeloFacade;
    }

    @Override
    public boolean isNovoElemento() {
        return (this.getModelo().getCodigo()==null)||(this.getModelo().getCodigo()==0);
    }

    @Override
    protected Modelo novainstanciaElemento() {
        return new Modelo();
    }

    public List<TipoEquipamento> getTiposEquipamento() {
        if (this.tiposEquipamento==null) this.tiposEquipamento = this.tipoEquipamentoFacade.findAll();
        return tiposEquipamento;
    }

    public void setTiposEquipamento(List<TipoEquipamento> tiposEquipamento) {
        this.tiposEquipamento = tiposEquipamento;
    }

}
