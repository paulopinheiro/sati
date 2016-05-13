package br.jus.trt12.paulopinheiro.sati.equipamentos.ejb;

import br.jus.trt12.paulopinheiro.sati.equipamentos.model.Modelo;
import br.jus.trt12.paulopinheiro.sati.equipamentos.model.TipoEquipamento;
import br.jus.trt12.paulopinheiro.sati.exceptions.SatiLogicalException;
import br.jus.trt12.paulopinheiro.sati.geral.ejb.comum.AbstractFacade;
import java.util.Collections;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Stateless
public class ModeloFacade extends AbstractFacade<Modelo> {
    @PersistenceContext(unitName = "satiPU")
    private EntityManager em;

    public ModeloFacade() {
        super(Modelo.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public void salvar(Modelo modelo) throws SatiLogicalException {
        if (modelo==null) modelo = new Modelo();
        if ((modelo.getNome()==null)||(modelo.getNome().trim().isEmpty())) throw new SatiLogicalException("Informe o nome do modelo");
        if ((modelo.getDescricao()==null)||(modelo.getDescricao().trim().isEmpty())) throw new SatiLogicalException("Informe uma descrição do modelo");
        if (modelo.getTipoEquipamento()==null) throw new SatiLogicalException("Informe de que tipo de equipamento é o modelo");
        if (modelo.getCodigo()==null) this.create(modelo);
        else this.edit(modelo);
    }

    @Override
    public void excluir(Modelo modelo) throws SatiLogicalException {
        if ((modelo.getListaLotes()!=null)&&(modelo.getListaLotes().size()>0)) throw new SatiLogicalException("Existe(m) " + modelo.getListaLotes().size() + " lote(s) de equipamentos deste modelo cadastrado(s). Alterar ou excluir primeiro");
        this.remove(modelo);
    }

    @Override
    public List<Modelo> findAll() {
        List<Modelo> resposta = super.findAll();
        Collections.sort(resposta);
        return resposta;
    }

    public List<Modelo> findByTipoEquipamento(TipoEquipamento tipoEquipamento) {
        List<Modelo> resposta = null;
        Query query = this.getEntityManager().createNamedQuery("Modelo.findByTipoEquipamento");
        query.setParameter("tipoEquipamento", tipoEquipamento);
        resposta = query.getResultList();
        if (resposta!=null) Collections.sort(resposta);
        return resposta;
    }

    @Override
    protected CriteriaQuery<Modelo> getCq(Modelo filtro) {
        CriteriaQuery<Modelo> cq = this.getCb().createQuery(Modelo.class);
        Root<Modelo> root = cq.from(Modelo.class);
        cq.select(root);

        Predicate nome = getCb().conjunction();
        Predicate descricao = getCb().conjunction();
        Predicate tipoEquipamento = getCb().conjunction();

        if ((filtro.getNome()!=null)&&(!filtro.getNome().isEmpty())) {
            Expression<String> a_nome = root.get("nome");
            nome = getCb().like(getCb().upper(a_nome), filtro.getNome().toUpperCase());
        }
        if ((filtro.getDescricao()!=null)&&(!filtro.getDescricao().isEmpty())) {
            Expression<String> a_descricao = root.get("descricao");
            descricao = getCb().like(getCb().upper(a_descricao), filtro.getDescricao().toUpperCase());
        }

        if (filtro.getTipoEquipamento()!=null) tipoEquipamento = getCb().equal(root.get("tipoEquipamento"), filtro.getTipoEquipamento());

        cq.where(nome,descricao,tipoEquipamento);

        return cq;
    }
}
