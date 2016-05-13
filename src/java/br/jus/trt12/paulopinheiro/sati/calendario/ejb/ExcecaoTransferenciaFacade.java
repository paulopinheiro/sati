package br.jus.trt12.paulopinheiro.sati.calendario.ejb;

import br.jus.trt12.paulopinheiro.sati.calendario.model.ExcecaoTransferencia;
import br.jus.trt12.paulopinheiro.sati.calendario.model.Transferencia;
import br.jus.trt12.paulopinheiro.sati.exceptions.SatiLogicalException;
import br.jus.trt12.paulopinheiro.sati.geral.ejb.comum.AbstractFacade;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class ExcecaoTransferenciaFacade extends AbstractFacade<ExcecaoTransferencia> {
    @PersistenceContext(unitName = "satiPU")
    private EntityManager em;

    @Override
    public void salvar(ExcecaoTransferencia excecao) throws SatiLogicalException {
        if (excecao!= null) {
            if (excecao.getTransferencia()==null) throw new SatiLogicalException("Informe a transferência");
            if (excecao.getMunicipio()==null) throw new SatiLogicalException("Informe o muncípio que é exceção da transferência");
            System.out.println("Vou salvar agora");
            if (excecao.getCodigo()==null) super.create(excecao);
            else super.edit(excecao);
        }
    }

    public List<ExcecaoTransferencia> findByTransferencia(Transferencia transferencia) {
        List<ExcecaoTransferencia> resposta = null;
        Query query = getEntityManager().createNamedQuery("ExcecaoTransferencia.findByTransferencia");
        query.setParameter("transferencia",transferencia);
        resposta = query.getResultList();
        return resposta;
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ExcecaoTransferenciaFacade() {
        super(ExcecaoTransferencia.class);
    }

    @Override
    public void excluir(ExcecaoTransferencia entity) throws SatiLogicalException {
        super.remove(entity);
    }
}
