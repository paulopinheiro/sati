package br.jus.trt12.paulopinheiro.sati.viagem.jsf;

import br.jus.trt12.paulopinheiro.sati.geral.ejb.MunicipioFacade;
import br.jus.trt12.paulopinheiro.sati.geral.model.Municipio;
import br.jus.trt12.paulopinheiro.sati.geral.model.Progint;
import br.jus.trt12.paulopinheiro.sati.exceptions.SatiLogicalException;
import br.jus.trt12.paulopinheiro.sati.geral.jsf.GeralMB;
import br.jus.trt12.paulopinheiro.sati.util.ContextoJSF;
import br.jus.trt12.paulopinheiro.sati.viagem.ejb.ViagemFacade;
import br.jus.trt12.paulopinheiro.sati.viagem.model.Viagem;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ViewScoped
public class ListaViagensMB implements Serializable {
    @EJB private ViagemFacade viagemFacade;
    @EJB private MunicipioFacade municipioFacade;
    @Inject private GeralMB geralMB;

    private List<Viagem> listaViagens;

    private Progint progint;

    private Municipio municipioFiltro;
    private List<Municipio> municipios;

    public ListaViagensMB() {}

    public void filtrar(ActionEvent event) {
        setListaViagens(null);
    }

    public int getQuantViagens() {
        return getListaViagens().size();
    }

    public List<Viagem> getListaViagens() {
        try {
            if (this.listaViagens == null) {
                this.listaViagens = viagemFacade.findByProgintDestino(getProgint(),getMunicipioFiltro());
            }
        } catch (SatiLogicalException ex) {
            mensagemErro(ex.getMessage());
        }
        return listaViagens;
    }

    public void setListaViagens(List<Viagem> listaViagens) {
        this.listaViagens = listaViagens;
    }

    public Municipio getMunicipioFiltro() {
        return municipioFiltro;
    }

    public void setMunicipioFiltro(Municipio municipioFiltro) {
        this.municipioFiltro = municipioFiltro;
    }

    public List<Municipio> getMunicipios() {
        if (this.municipios==null) this.municipios = municipioFacade.findAll();
        return municipios;
    }

    public void setMunicipios(List<Municipio> municipios) {
        this.municipios = municipios;
    }

    public Progint getProgint() {
        if (this.progint==null) this.progint=getGeralMB().getProgint();
        return progint;
    }

    public void setProgint(Progint progint) {
        this.progint = progint;
    }

    private void mensagemErro(String mensagem) {
        ContextoJSF.getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,mensagem,null));
    }

    public GeralMB getGeralMB() {
        return geralMB;
    }

    public void setGeralMB(GeralMB geralMB) {
        this.geralMB = geralMB;
    }
}
