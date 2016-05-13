package br.jus.trt12.paulopinheiro.sati.geral.jsf;
import br.jus.trt12.paulopinheiro.sati.geral.ejb.AreaTIFacade;
import br.jus.trt12.paulopinheiro.sati.geral.ejb.MunicipioFacade;
import br.jus.trt12.paulopinheiro.sati.geral.ejb.ProgintFacade;
import br.jus.trt12.paulopinheiro.sati.geral.model.AreaTI;
import br.jus.trt12.paulopinheiro.sati.geral.model.Municipio;
import br.jus.trt12.paulopinheiro.sati.geral.model.Progint;
import br.jus.trt12.paulopinheiro.sati.util.ContextoJSF;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

@ManagedBean
@ViewScoped
public class AreaTIMB implements Serializable {
    @EJB private AreaTIFacade areaTIFacade;
    @EJB private ProgintFacade progintFacade;
    @EJB private MunicipioFacade municipioFacade;

    private List<AreaTI> listaAreasTI;
    private AreaTI areaTI;

    private List<Progint> progints;
    private List<Municipio> municipiosArea;

    public AreaTIMB() {}

    public void salvar(ActionEvent event) {
        try {
            areaTIFacade.salvar(getAreaTI());
            setAreaTI(null);
            setListaAreasTI(null);
        } catch (Exception ex) {
            mensagemErro(ex.getMessage());
        }
    }

    public void novo(ActionEvent event) {
        setAreaTI(null);
    }

    public void excluir(ActionEvent event) {
        try {
            areaTIFacade.remove(getAreaTI());
            setAreaTI(null);
            setListaAreasTI(null);
        } catch (Exception ex) {
            mensagemErro(ex.getMessage());
        }
    }

    public int getQuantAreasTI() {
        return getListaAreasTI().size();
    }

    public AreaTI getAreaTI() {
        if (this.areaTI==null) this.areaTI = new AreaTI();
        return areaTI;
    }

    public void setAreaTI(AreaTI areaTI) {
        this.areaTI = areaTI;
    }

    public List<AreaTI> getListaAreasTI() {
        if (this.listaAreasTI==null) this.listaAreasTI = areaTIFacade.findAll();
        return listaAreasTI;
    }

    public void setListaAreasTI(List<AreaTI> listaAreasTI) {
        this.listaAreasTI = listaAreasTI;
    }

    public List<Progint> getProgints() {
        if (this.progints==null) this.progints = progintFacade.findAll();
        return progints;
    }

    public void setProgints(List<Progint> progints) {
        this.progints = progints;
    }

    public List<Municipio> getMunicipiosArea() {
        this.municipiosArea = municipioFacade.findByAreaTI(getAreaTI());
        if (this.municipiosArea==null) this.municipiosArea=new ArrayList<Municipio>();
        return municipiosArea;
    }

    public void setMunicipiosArea(List<Municipio> municipiosArea) {
        this.municipiosArea = municipiosArea;
    }

    private void mensagemErro(String mensagem) {
        ContextoJSF.getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,mensagem,null));
    }
}
