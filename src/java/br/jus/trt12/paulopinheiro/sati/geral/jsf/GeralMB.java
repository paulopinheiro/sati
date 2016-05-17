package br.jus.trt12.paulopinheiro.sati.geral.jsf;

import br.jus.trt12.paulopinheiro.ldaputil.usuarios.Usuario;
import br.jus.trt12.paulopinheiro.sati.geral.ejb.MunicipioFacade;
import br.jus.trt12.paulopinheiro.sati.geral.ejb.ProgintFacade;
import br.jus.trt12.paulopinheiro.sati.geral.model.Municipio;
import br.jus.trt12.paulopinheiro.sati.geral.model.Progint;
import br.jus.trt12.paulopinheiro.sati.util.ContextoJSF;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Named;

@Named
@SessionScoped
public class GeralMB implements Serializable {
    @EJB ProgintFacade progintFacade;
    @EJB private MunicipioFacade municipioFacade;

    private List<Progint> listaProgints;
    private Progint progint;
    private String senha;

    private List<Municipio> listaMunicipios;
    private Municipio municipioSessao;

    public GeralMB() {}

    public String conectar() {
        try {
            if (getProgint().getCodigo() == null) throw new Exception("Escolha o usuário");
            if (getSenha().trim().isEmpty()) throw new Exception("Digite a senha");

            Usuario usuario = new Usuario();
            usuario.autenticar(getProgint().getMatricula(), getSenha());
            if (usuario.isAutenticado()) {
                return "/home.xhtml";
            }
            else throw new Exception("Credenciais inválidas!");
        } catch (Exception ex) {
            mensagemErro(ex.getMessage());
        }
        return "index.xhtml";
    }

    public String desconectar() {
        ContextoJSF.getFacesSession().invalidate();
        return "/index.xhtml";
    }

    public List<Progint> getListaProgints() {
        if (this.listaProgints==null) this.listaProgints = progintFacade.findAtivos();
        return listaProgints;
    }

    public void setListaProgints(List<Progint> listaProgints) {
        this.listaProgints = listaProgints;
    }

    public Progint getProgint() {
        if (this.progint==null) this.progint= new Progint();
        return progint;
    }

    public void setProgint(Progint progint) {
        this.progint = progint;
    }

    public String getSenha() {
        if (this.senha==null) this.senha="";
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    private void mensagemErro(String mensagem) {
        ContextoJSF.getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,mensagem,null));
    }

    public List<Municipio> getListaMunicipios() {
        if (this.listaMunicipios==null) this.listaMunicipios = municipioFacade.findAll();
        return listaMunicipios;
    }

    public void setListaMunicipios(List<Municipio> listaMunicipios) {
        this.listaMunicipios = listaMunicipios;
    }

    public Municipio getMunicipioSessao() {
        if (this.municipioSessao==null) {
            if ((this.getProgint().getCodigo()!=null)&&(this.getProgint().getCodigo()>0)) {
                setMunicipioSessao(this.getProgint().getAreaAtuacao().getMunicipioSede());
            } else {
                setMunicipioSessao(new Municipio());
            }
        }                
        return municipioSessao;
    }

    public void setMunicipioSessao(Municipio municipioSessao) {
        this.municipioSessao = municipioSessao;
    }
}
