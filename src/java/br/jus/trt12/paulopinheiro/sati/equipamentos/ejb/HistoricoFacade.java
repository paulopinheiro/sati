package br.jus.trt12.paulopinheiro.sati.equipamentos.ejb;

import br.jus.trt12.paulopinheiro.sati.equipamentos.model.Equipamento;
import br.jus.trt12.paulopinheiro.sati.equipamentos.model.Historico;
import br.jus.trt12.paulopinheiro.sati.exceptions.SatiLogicalException;
import br.jus.trt12.paulopinheiro.sati.geral.ejb.comum.AbstractFacade;
import java.util.Date;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Stateless
public class HistoricoFacade extends AbstractFacade<Historico> {
    @PersistenceContext(unitName = "satiPU")
    private EntityManager em;

    public HistoricoFacade() {
        super(Historico.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public void salvar(Historico historico) throws SatiLogicalException {
        if (historico==null) historico = new Historico();
        if (historico.getDataHistorico()==null) throw new SatiLogicalException("Informe a data do histórico");
        if ((historico.getDescricao()==null)||(historico.getDescricao().trim().isEmpty())) throw new SatiLogicalException("Informe a descrição do histórico");
        if (historico.getEquipamento()==null) throw new SatiLogicalException("Informe o equipamento ao qual este histórico se refere");
        if (historico.getCodigo()==null) this.create(historico);
        else this.edit(historico);
        getEntityManager().getEntityManagerFactory().getCache().evict(Equipamento.class);
    }

    @Override
    public void excluir(Historico historico) throws SatiLogicalException {
        this.remove(historico);
        getEntityManager().getEntityManagerFactory().getCache().evict(Equipamento.class);
    }

    @Override
    protected CriteriaQuery<Historico> getCq(Historico filtro) {
        CriteriaQuery<Historico> cq = this.getCb().createQuery(Historico.class);
        Root<Historico> root = cq.from(Historico.class);
        cq.select(root);

        Predicate dataHistorico = getCb().conjunction();
        Predicate descricao = getCb().conjunction();
        Predicate observacao = getCb().conjunction();
        Predicate incidenteRITM = getCb().conjunction();
        Predicate equipamento = getCb().conjunction();

        if (filtro.getDataHistorico()!=null) {
            Expression<Date> a_dataHistorico = root.get("dataHistorico");
            dataHistorico = getCb().equal(a_dataHistorico, filtro.getDataHistorico());
        }
        if ((filtro.getDescricao()!=null)&&(!filtro.getDescricao().isEmpty())) {
            Expression<String> a_descricao = root.get("descricao");
            descricao = getCb().like(getCb().upper(a_descricao), filtro.getDescricao().toUpperCase());
        }
        if ((filtro.getObservacao()!=null)&&(!filtro.getObservacao().isEmpty())) {
            Expression<String> a_observacao = root.get("observacao");
            observacao = getCb().like(getCb().upper(a_observacao), filtro.getObservacao().toUpperCase());
        }
        if (filtro.getEquipamento()!=null) equipamento = getCb().equal(root.get("equipamento"), filtro.getEquipamento());

        cq.where(dataHistorico,descricao,observacao,incidenteRITM,equipamento);

        return cq;
    }
}
