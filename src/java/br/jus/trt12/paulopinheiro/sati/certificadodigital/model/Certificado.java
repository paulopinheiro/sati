package br.jus.trt12.paulopinheiro.sati.certificadodigital.model;

import br.jus.trt12.paulopinheiro.sati.geral.model.UsuarioFinal;
import br.jus.trt12.paulopinheiro.sati.util.Util;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;

@Entity
@Table(name = "certificado", catalog = "sati", schema = "certificacao")
public class Certificado implements Serializable, Comparable {
    @Id
    @SequenceGenerator(name = "Certificado_Gen", sequenceName = "certificado_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "Certificado_Gen")
    private Integer id;
    @Column(name="data_gravacao", nullable=false)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataGravacao;
    @Column(name="data_validade", nullable=false)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataValidade;
    @JoinColumn(name="id_usuario")
    @ManyToOne(optional=false)
    private UsuarioFinal usuario;
    @JoinColumn(name="id_status")
    @ManyToOne(optional=false)
    private StatusCertificado status;
    @JoinColumn(name="id_marca_etoken")
    @ManyToOne(optional=false)
    private MarcaEtoken marcaEtoken;

    public Certificado() {}

    public Certificado(Date dataGravacao, Date dataValidade, UsuarioFinal usuario, StatusCertificado status, MarcaEtoken marcaEtoken) {
        this.dataGravacao = dataGravacao;
        this.dataValidade = dataValidade;
        this.usuario = usuario;
        this.status = status;
        this.marcaEtoken = marcaEtoken;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDataGravacao() {
        return dataGravacao;
    }

    public void setDataGravacao(Date dataGravacao) {
        this.dataGravacao = dataGravacao;
    }

    public Date getDataValidade() {
        return dataValidade;
    }

    public void setDataValidade(Date dataValidade) {
        this.dataValidade = dataValidade;
    }

    public UsuarioFinal getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioFinal usuario) {
        this.usuario = usuario;
    }

    public StatusCertificado getStatus() {
        return status;
    }

    public void setStatus(StatusCertificado status) {
        this.status = status;
    }

    public MarcaEtoken getMarcaEtoken() {
        return marcaEtoken;
    }

    public void setMarcaEtoken(MarcaEtoken marcaEtoken) {
        this.marcaEtoken = marcaEtoken;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Certificado other = (Certificado) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Certificado usuário " + usuario + "(data de validade: " + Util.dataString(dataValidade) + ")";
    }

    @Override
    public int compareTo(Object o) {
        Certificado outro = (Certificado) o;
        if ((this.getUsuario()==null)||(outro.getUsuario()==null)) return 0;
        if (!this.getUsuario().equals(outro.getUsuario())) return this.getUsuario().compareTo(outro.getUsuario());
        if ((this.getDataValidade()==null)||(outro.getDataValidade()==null)) return 0;
        return this.getDataValidade().compareTo(outro.getDataValidade());
    }

}
