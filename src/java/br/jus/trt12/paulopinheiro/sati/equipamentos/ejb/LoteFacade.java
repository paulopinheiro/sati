package br.jus.trt12.paulopinheiro.sati.equipamentos.ejb;

import br.jus.trt12.paulopinheiro.sati.equipamentos.model.Lote;
import br.jus.trt12.paulopinheiro.sati.equipamentos.model.Modelo;
import br.jus.trt12.paulopinheiro.sati.exceptions.SatiLogicalException;
import br.jus.trt12.paulopinheiro.sati.geral.ejb.comum.AbstractFacade;
import java.util.Collections;
import java.util.Date;
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
public class LoteFacade extends AbstractFacade<Lote> {

    @PersistenceContext(unitName = "satiPU")
    private EntityManager em;

    public LoteFacade() {
        super(Lote.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public void salvar(Lote lote) throws SatiLogicalException {
        if (lote==null) lote = new Lote();
        if ((lote.getNome() == null) || (lote.getNome().trim().isEmpty())) throw new SatiLogicalException("Informe um nome para o lote (sugestão seria o número da NF");
        if (lote.getDataFimGarantia() == null) throw new SatiLogicalException("Informe a data do fim da garantia para equipamentos deste lote");
        if (lote.getModelo() == null) throw new SatiLogicalException("Informe o modelo dos equipamentos que foram adquiridos neste lote");
        if (lote.getCodigo() == null) this.create(lote);
        else this.edit(lote);
    }

    @Override
    public void excluir(Lote lote) throws SatiLogicalException {
        if (lote != null) {
            if ((lote.getListaEquipamentos() != null) && lote.getListaEquipamentos().size() > 0) {
                throw new SatiLogicalException("Existe(m) + " + lote.getListaEquipamentos().size() + " equipamento(s) cadastrado(s) deste lote. Exclua primeiro");
            }
            this.remove(lote);
        }
    }

    public List<Lote> findByModelo(Modelo modelo) {
        List<Lote> resposta;
        Query query = this.getEntityManager().createNamedQuery("Lote.findByModelo");
        query.setParameter("modelo", modelo);
        resposta = query.getResultList();
        if (resposta!=null) Collections.sort(resposta);
        return resposta;
    }

    @Override
    protected CriteriaQuery<Lote> getCq(Lote filtro) {
        CriteriaQuery<Lote> cq = this.getCb().createQuery(Lote.class);
        Root<Lote> root = cq.from(Lote.class);
        cq.select(root);

        Predicate nome = getCb().conjunction();
        Predicate dataFimGarantia = getCb().conjunction();
        Predicate modelo = getCb().conjunction();

        if ((filtro.getNome()!=null)&&(!filtro.getNome().isEmpty())) {
            Expression<String> a_nome = root.get("nome");
            nome = getCb().like(getCb().upper(a_nome), filtro.getNome().toUpperCase());
        }
        if (filtro.getDataFimGarantia()!=null) {
            Expression<Date> a_dataFimGarantia = root.get("dataFimGarantia");
            dataFimGarantia = getCb().equal(a_dataFimGarantia, filtro.getDataFimGarantia());
        }

        if (filtro.getModelo()!=null) modelo = getCb().equal(root.get("modelo"), filtro.getModelo());

        cq.where(nome,dataFimGarantia,modelo);

        return cq;
    }
}
