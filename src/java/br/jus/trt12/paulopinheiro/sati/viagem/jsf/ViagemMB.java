package br.jus.trt12.paulopinheiro.sati.viagem.jsf;

import br.jus.trt12.paulopinheiro.sati.geral.ejb.MunicipioFacade;
import br.jus.trt12.paulopinheiro.sati.geral.jsf.GeralMB;
import br.jus.trt12.paulopinheiro.sati.geral.model.Municipio;
import br.jus.trt12.paulopinheiro.sati.geral.model.Progint;
import br.jus.trt12.paulopinheiro.sati.util.ContextoJSF;
import br.jus.trt12.paulopinheiro.sati.viagem.ejb.ViagemFacade;
import br.jus.trt12.paulopinheiro.sati.viagem.model.Tarefa;
import br.jus.trt12.paulopinheiro.sati.viagem.model.Viagem;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.event.ActionEvent;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class ViagemMB implements Serializable {
    @EJB ViagemFacade viagemFacade;
    @EJB MunicipioFacade municipioFacade;
    @ManagedProperty(value="#{geralMB}")
    private GeralMB geralMB;

    private Progint progint;

    private Viagem viagem;
    private List<Municipio> municipios;

    private Tarefa tarefa;

    public ViagemMB() {}

    public boolean isNovaViagem() {
        if (getViagem().getCodigo()== null) System.out.println("Código nulo");
        return (getViagem().getCodigo()==null)||(getViagem().getCodigo()==0);
    }

    public void salvar(ActionEvent event) {
        try {
            viagemFacade.salvar(getViagem());
        } catch (Exception ex) {
            mensagemErro(ex.getMessage());
        }
    }

    public void nova(ActionEvent event) {
        setViagem(null);
        setTarefa(null);
    }

    public void excluir(ActionEvent event) {
        try {
            viagemFacade.remove(getViagem());
            setViagem(null);
            setTarefa(null);
        } catch (Exception ex) {
            mensagemErro(ex.getMessage());
        }
    }

    public void salvarTarefa(ActionEvent event) {
        try {
            viagemFacade.salvarTarefa(getTarefa());
            setViagem(viagemFacade.find(getViagem().getCodigo()));
            setTarefa(null);
        } catch (Exception ex) {
            mensagemErro(ex.getMessage());
        }
    }

    public void novaTarefa(ActionEvent event) {
        setTarefa(null);
    }

    public void excluirTarefa(ActionEvent event) {
        try {
            viagemFacade.excluirTarefa(getTarefa());
            setViagem(viagemFacade.find(getViagem().getCodigo()));
            setTarefa(null);
        } catch (Exception ex) {
            mensagemErro(ex.getMessage());
        }
    }

    public int getQuantTarefas() {
        if (this.getViagem().getTarefas()==null) return 0;
        return getViagem().getTarefas().size();
    }

    public List<Municipio> getMunicipios() {
        if (this.municipios==null) this.municipios=municipioFacade.findAll();
        return municipios;
    }

    public void setMunicipios(List<Municipio> municipios) {
        this.municipios = municipios;
    }

    public Viagem getViagem() {
        if (this.viagem==null) this.viagem=new Viagem(getProgint());
        if (this.viagem.getTarefas()!=null) Collections.sort(this.viagem.getTarefas());
        return viagem;
    }

    public void setViagem(Viagem viagem) {
        this.viagem = viagem;
    }

    public Tarefa getTarefa() {
        if (this.tarefa==null) this.tarefa = new Tarefa(getViagem());
        return tarefa;
    }

    public void setTarefa(Tarefa tarefa) {
        this.tarefa = tarefa;
    }

    private void mensagemErro(String mensagem) {
        ContextoJSF.getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,mensagem,null));
    }

    private Progint getProgint() {
        if (this.progint==null) this.progint=getGeralMB().getProgint();
        return this.progint;
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
