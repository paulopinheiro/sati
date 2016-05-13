package br.jus.trt12.paulopinheiro.sati.calendario.model;

import br.jus.trt12.paulopinheiro.sati.geral.model.Municipio;
import java.io.Serializable;
import java.text.Collator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;

@Entity
@Inheritance(strategy=InheritanceType.JOINED)
@Table(name = "feriado", catalog = "sati", schema = "calendario")
@DiscriminatorColumn(name="tipo")
@NamedQueries(value={
    @NamedQuery(name="Feriado.findAll", query="Select f from Feriado f order by f.descricao")
})
public abstract class Feriado implements Serializable, Comparable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name="Feriado_Gen",sequenceName="calendario.feriado_codigo_seq",allocationSize=1)
    @GeneratedValue(generator="Feriado_Gen")
    private Integer codigo;

    private String descricao;

    @ManyToOne
    @JoinColumn(name="cod_municipio")
    private Municipio municipio;

    @Temporal(javax.persistence.TemporalType.DATE)
    @Column(name = "data_instituicao")
    private Date dataInstituicao;

    @Temporal(javax.persistence.TemporalType.DATE)
    @Column(name = "data_revogacao")
    private Date dataRevogacao;

    @OneToMany(mappedBy = "feriado", cascade=CascadeType.ALL)
    private List<Transferencia> transferencias;

    @OneToMany(mappedBy = "feriado", cascade=CascadeType.ALL)
    private List<ExcecaoFeriadoNacional> excecoes;

    protected abstract Date dataAno(int ano);

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Date getDataInstituicao() {
        return dataInstituicao;
    }

    public void setDataInstituicao(Date dataInstituicao) {
        this.dataInstituicao = dataInstituicao;
    }

    public Date getDataRevogacao() {
        return dataRevogacao;
    }

    public void setDataRevogacao(Date dataRevogacao) {
        this.dataRevogacao = dataRevogacao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Municipio getMunicipio() {
        return municipio;
    }

    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigo != null ? codigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the codigo fields are not set
        if (!(object instanceof Feriado)) {
            return false;
        }
        Feriado other = (Feriado) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.getDescricao();
    }

    @Override
    public int compareTo(Object o) {
        Feriado outro = (Feriado) o;
        Collator col = Collator.getInstance(Locale.getDefault());
        return col.compare(this.getDescricao().toUpperCase(), outro.getDescricao().toUpperCase());
    }

    public Date dataAno(int ano, Municipio municipio) {
        Date resposta = this.dataAno(ano);

        if (!(this.transferencias==null)) {
            for (Transferencia t:this.transferencias) {
                if ((t.getAno()==ano)&&(t.validaPara(municipio))) {
                    resposta = t.getNovaData();
                }
            }
        }

        return resposta;
    }

    public boolean isMunicipal() {
        if(!(getMunicipio()==null)) return true;
        return false;
    }

    public boolean isValido(int ano, Municipio municipio) {
        return isVigente(ano) && isSemExcecaoParaAnoMunicipio(ano,municipio) &&
               isValidoParaMunicipio(municipio);
    }

    private boolean isVigente(int ano) {
        boolean vigencia;
        boolean aposInstituicao = true;
        boolean antesRevogacao = true;

        Date dataFeriado = dataAno(ano);

        if (!(dataInstituicao==null)) aposInstituicao = !dataFeriado.before(dataInstituicao);
        if (!(dataRevogacao== null)) antesRevogacao = !dataFeriado.after(dataRevogacao);
        vigencia = aposInstituicao && antesRevogacao;

        return vigencia;                
    }

    private boolean isSemExcecaoParaAnoMunicipio(int ano,Municipio municipio) {
        if (this.excecoes == null) return true;
        for (ExcecaoFeriadoNacional e:this.excecoes) {
            if (!e.isValida(ano)) return true;
            if (e.getMunicipio().equals(municipio)) return false;
        }
        return true;
    }

    private boolean isValidoParaMunicipio(Municipio municipio) {
        if (this.municipio==null) return true;
        if (this.municipio.equals(municipio)) return true;
        return false;
    }
}
