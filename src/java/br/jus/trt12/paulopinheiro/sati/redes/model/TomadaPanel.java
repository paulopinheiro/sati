package br.jus.trt12.paulopinheiro.sati.redes.model;

import java.io.Serializable;
import java.text.Collator;
import java.util.Locale;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "tomadapanel", catalog = "sati", schema = "redes")
@DiscriminatorValue("P")
@PrimaryKeyJoinColumn(name="codigo")
@NamedQueries({
    @NamedQuery(name="TomadaPanel.tomadasPanelByPanel", query = "Select t from TomadaPanel t where t.panel = :panel order by t.nome")
})
public class TomadaPanel extends Tomada implements Serializable {
    private static final long serialVersionUID = 1L;
    @ManyToOne
    @JoinColumn(name="cod_panel", nullable=false)
    private Panel panel;
    private String ramal;

    public TomadaPanel() {}

    public TomadaPanel(Panel panel) {
        this.panel = panel;
    }

    public Panel getPanel() {
        return panel;
    }

    public void setPanel(Panel panel) {
        this.panel = panel;
    }

    public String getRamal() {
        return ramal;
    }

    public void setRamal(String ramal) {
        this.ramal = ramal;
    }

    public boolean isDesligada() {
        return ((this.ramal==null)||(this.ramal.isEmpty())) && (super.getSegmento()==null);
    }

    public boolean isVoice() {
        return ((this.ramal!=null)&&(!this.ramal.isEmpty())) && (super.getSegmento()==null);
    }

    public boolean isDados() {
        return ((this.ramal==null)||(this.ramal.isEmpty())) && (super.getSegmento()!=null);
    }

    public boolean isIrregular() {
        return ((this.ramal!=null)&&(!this.ramal.isEmpty())) && (super.getSegmento()!=null);
    }

    @Override
    public String getDescricao() {
        if ((super.getDescricao()==null)||super.getDescricao().trim().isEmpty()) {
            if ((this.getNome()==null)||this.getNome().trim().isEmpty()) return "";
            else return "Tomada " + this.getNome() + " do painel " + this.getPanel();
        } else return super.getDescricao();
    }

    @Override
    public String toString() {
        return this.getDescricao();
    }

    @Override
    public int compareTo(Object o) {
        TomadaPanel outra = (TomadaPanel) o;
        if (!this.getPanel().equals(outra.getPanel())) return this.getPanel().compareTo(outra.getPanel());
        Collator col = Collator.getInstance(Locale.getDefault());
        return super.compareTo(o);
    }

}