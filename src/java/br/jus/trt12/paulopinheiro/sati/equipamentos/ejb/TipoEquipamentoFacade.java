package br.jus.trt12.paulopinheiro.sati.equipamentos.ejb;

import br.jus.trt12.paulopinheiro.sati.equipamentos.model.TipoEquipamento;
import br.jus.trt12.paulopinheiro.sati.exceptions.SatiLogicalException;
import br.jus.trt12.paulopinheiro.sati.geral.ejb.comum.AbstractFacade;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Stateless
public class TipoEquipamentoFacade extends AbstractFacade<TipoEquipamento> {
    @PersistenceContext(unitName = "satiPU")
    private EntityManager em;

    public TipoEquipamentoFacade() {
        super(TipoEquipamento.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public void salvar(TipoEquipamento tipoEquipamento) throws SatiLogicalException {
        if (tipoEquipamento==null) tipoEquipamento = new TipoEquipamento();
        if ((tipoEquipamento.getNome()==null)||tipoEquipamento.getNome().trim().isEmpty()) throw new SatiLogicalException("Forneça o nome do tipo");
        if (tipoEquipamento.getCodigo()==null) super.create(tipoEquipamento);
            else super.edit(tipoEquipamento);
    }

    @Override
    public void excluir(TipoEquipamento tipoEquipamento) throws SatiLogicalException {
        this.remove(tipoEquipamento);
    }

    @Override
    protected CriteriaQuery<TipoEquipamento> getCq(TipoEquipamento filtro) {
        CriteriaQuery<TipoEquipamento> cq = this.getCb().createQuery(TipoEquipamento.class);
        Root<TipoEquipamento> root = cq.from(TipoEquipamento.class);
        cq.select(root);

        Predicate nome = getCb().conjunction();

        if ((filtro.getNome()!=null)&&(!filtro.getNome().isEmpty())) {
            Expression<String> a_nome = root.get("nome");
            nome = getCb().like(getCb().upper(a_nome), filtro.getNome().toUpperCase());
        }

        cq.where(nome);

        return cq;
    }
}
