package br.jus.trt12.paulopinheiro.sati.equipamentos.jsf;

import br.jus.trt12.paulopinheiro.sati.equipamentos.ejb.HistoricoFacade;
import br.jus.trt12.paulopinheiro.sati.equipamentos.model.Equipamento;
import br.jus.trt12.paulopinheiro.sati.equipamentos.model.Historico;
import br.jus.trt12.paulopinheiro.sati.geral.ejb.comum.AbstractFacade;
import br.jus.trt12.paulopinheiro.sati.geral.jsf.comum.AbBasicoMB;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class HistoricoMB extends AbBasicoMB<Historico> implements Serializable {
    @EJB private HistoricoFacade historicoFacade;

    private Equipamento equipamento;

    public HistoricoMB() {}

    @Override
    protected AbstractFacade getFacade() {
        return this.historicoFacade;
    }

    @Override
    public boolean isNovoElemento() {
        return this.getHistorico().getCodigo()==null||this.getHistorico().getCodigo()==0;
    }

    @Override
    protected Historico novainstanciaElemento() {
        return new Historico(getEquipamento());
    }

    public Historico getHistorico() {
        return this.getElemento();
    }

    public void setHistorico(Historico historico) {
        this.setElemento(historico);
    }

    public Equipamento getEquipamento() {
        return equipamento;
    }

    public void setEquipamento(Equipamento equipamento) {
        this.equipamento = equipamento;
    }
}
