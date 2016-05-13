package br.jus.trt12.paulopinheiro.sati.calendario.ejb;

import br.jus.trt12.paulopinheiro.sati.calendario.model.Transferencia;
import br.jus.trt12.paulopinheiro.sati.exceptions.SatiLogicalException;
import br.jus.trt12.paulopinheiro.sati.geral.ejb.comum.AbstractFacade;
import br.jus.trt12.paulopinheiro.sati.util.Util;
import java.util.Collections;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class TransferenciaFacade extends AbstractFacade<Transferencia> {
    @PersistenceContext(unitName = "satiPU")
    private EntityManager em;

    @Override
    public void salvar(Transferencia transferencia) throws SatiLogicalException {
        if (transferencia!= null) {
            if (transferencia.getFeriado()==null) throw new SatiLogicalException("Informe o feriado a ser transferido");
            if (Util.dataPura(transferencia.getAno(), transferencia.getNovoMes(), transferencia.getNovoDia())==null)
                throw new SatiLogicalException("Escolha uma data válida");
            //crítica candidata a trigger
            if ((transferencia.getMunicipio()!=null)&&(transferencia.getFeriado().isMunicipal())) throw new SatiLogicalException("É inconsistente dar exclusividade a transferência de feriados municipais");
            if (transferencia.getCodigo()==null) super.create(transferencia);
            else super.edit(transferencia);
        }
    }

    public List<Transferencia> findByAno(int ano) {
        List<Transferencia> resposta = null;
        Query query = getEntityManager().createNamedQuery("Transferencia.transferenciasByAno");
        query.setParameter("ano", ano);
        resposta = query.getResultList();
        Collections.sort(resposta);
        return resposta;
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TransferenciaFacade() {
        super(Transferencia.class);
    }

    @Override
    public void excluir(Transferencia entity) throws SatiLogicalException {
        super.remove(entity);
    }
}
