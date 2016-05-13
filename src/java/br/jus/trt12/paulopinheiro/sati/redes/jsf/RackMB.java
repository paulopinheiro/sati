package br.jus.trt12.paulopinheiro.sati.redes.jsf;

import br.jus.trt12.paulopinheiro.sati.geral.jsf.GeralMB;
import br.jus.trt12.paulopinheiro.sati.geral.model.Municipio;
import br.jus.trt12.paulopinheiro.sati.redes.ejb.RackFacade;
import br.jus.trt12.paulopinheiro.sati.redes.model.Rack;
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
public class RackMB implements Serializable {
    @EJB private RackFacade rackFacade;
    @ManagedProperty(value="#{geralMB}")
    private GeralMB geralMB;

    private List<Rack> listaRacks;
    private Rack rack;

    public RackMB() {}

    public void salvar(ActionEvent event) {
        try {
            rackFacade.salvar(getRack());
            setRack(null);
            setListaRacks(null);
        } catch (Exception ex) {
            mensagemErro(ex.getMessage());
        }
    }

    public void novo(ActionEvent event) {
        setRack(null);
    }

    public void excluir(ActionEvent event) {
        try {
            rackFacade.remove(getRack());
            setRack(null);
            setListaRacks(null);
        } catch (Exception ex) {
            mensagemErro(ex.getMessage());
        }
    }

    public int getQuantRacks() {
        return getListaRacks().size();
    }


    public List<Rack> getListaRacks() {
        if (this.listaRacks==null) this.listaRacks=rackFacade.findByMunicipio(getMunicipio());
        return listaRacks;
    }

    public void setListaRacks(List<Rack> listaRacks) {
        this.listaRacks = listaRacks;
    }

    public Rack getRack() {
        if (this.rack==null) this.rack=new Rack(getMunicipio());
        return rack;
    }

    public void setRack(Rack rack) {
        this.rack = rack;
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
