package br.jus.trt12.paulopinheiro.sati.viagem.ejb;

import br.jus.trt12.paulopinheiro.sati.exceptions.SatiLogicalException;
import br.jus.trt12.paulopinheiro.sati.exceptions.InfraEstruturaException;
import br.jus.trt12.paulopinheiro.sati.geral.ejb.AreaTIFacade;
import br.jus.trt12.paulopinheiro.sati.geral.ejb.comum.AbstractFacade;
import br.jus.trt12.paulopinheiro.sati.geral.model.AreaTI;
import br.jus.trt12.paulopinheiro.sati.geral.model.Municipio;
import br.jus.trt12.paulopinheiro.sati.geral.model.Progint;
import br.jus.trt12.paulopinheiro.sati.util.Util;
import br.jus.trt12.paulopinheiro.sati.viagem.model.Tarefa;
import br.jus.trt12.paulopinheiro.sati.viagem.model.Viagem;
import java.util.Collections;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class ViagemFacade extends AbstractFacade<Viagem> {
    @EJB AreaTIFacade areaTIFacade;

    @PersistenceContext(unitName = "satiPU")
    private EntityManager em;

    @Override
    public void salvar(Viagem viagem) throws SatiLogicalException, InfraEstruturaException {
        if (!(viagem==null)) {
            //Validações
            if (viagem.getDataProgramada()==null) throw new SatiLogicalException("Informe a data programada para a viagem");
            if (viagem.getProgint()==null) throw new SatiLogicalException("Informe o técnico");
            if (viagem.getMunicipioDestino()== null) throw new SatiLogicalException("Informe o município de destino");
            //preenche município de origem, baseado no município sede do Técnico
            viagem.setMunicipioOrigem(viagem.getProgint().getUnidade().getMunicipio());
            if (viagem.getMunicipioOrigem()==null) throw new SatiLogicalException("Erro ao tentar obter o município de lotação do técnico");
            if (viagem.getMunicipioDestino().equals(viagem.getMunicipioOrigem())) throw new SatiLogicalException("Não pode haver viagem para o município onde está lotado o técnico");
            if (viagem.getCodigo() != null) { //Não é uma viagem nova
                if (viagem.getDataViagem() == null) {
                    //construir uma trigger
                    Viagem pesq = super.find(viagem.getCodigo());
                    if ((pesq != null) && (pesq.getDataViagem() != null)) throw new SatiLogicalException("A informação da data da realização da viagem não pode ser anulada/cancelada");
                }
                try {super.edit(viagem);} catch (Exception ex) {throw new InfraEstruturaException("Erro ao salvar Viagem: " + Util.limpaException(ex.getMessage()));}
            } else try {super.create(viagem);} catch (Exception ex) {throw new InfraEstruturaException("Erro ao salvar Viagem: " + Util.limpaException(ex.getMessage()));}
        }
    }

    public List<Viagem> findByProgint(Progint progint) throws SatiLogicalException {
        if (progint==null) throw new SatiLogicalException("Informe o técnico");
        List<Viagem> resposta = null;

        Query query = getEntityManager().createNamedQuery("Viagem.viagensByProgint");
        query.setParameter("progint", progint);

        resposta = query.getResultList();
        if (resposta!=null) Collections.sort(resposta);

        return resposta;
    }

    public List<Viagem> findByProgintDestino(Progint progint, Municipio destino) throws SatiLogicalException {
        List<Viagem> resposta = null;
        if (progint==null) throw new SatiLogicalException("Informe o técnico");
        if (destino==null) return findByProgint(progint);

        Query query = getEntityManager().createNamedQuery("Viagem.viagensByProgintDestino");
        query.setParameter("progint", progint);
        query.setParameter("municipioDestino", destino);
        resposta = query.getResultList();

        if (resposta!=null) Collections.sort(resposta);
        return resposta;
    }

    @Override
    public List<Viagem> findAll() {
        List<Viagem> resposta = super.findAll();
        if (resposta!=null) Collections.sort(resposta);

        return resposta;
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ViagemFacade() {
        super(Viagem.class);
    }

    public void salvarTarefa(Tarefa tarefa) throws SatiLogicalException {
        if (!(tarefa==null)) {
            if ((tarefa.getDescricao()==null)||(tarefa.getDescricao().trim().isEmpty())) throw new SatiLogicalException("Informe a descrição da tarefa");
            if (tarefa.getViagem()==null) throw new SatiLogicalException("Informe a viagem da tarefa");
            
        }
        if (tarefa.getCodigo()==null) {
            getEntityManager().persist(tarefa);
            getEntityManager().getEntityManagerFactory().getCache().evict(Viagem.class);
        }
        else getEntityManager().merge(tarefa);
    }

    public void excluirTarefa(Tarefa tarefa) {
        if (!(tarefa==null)) {
            getEntityManager().remove(getEntityManager().merge(tarefa));
            getEntityManager().getEntityManagerFactory().getCache().evict(Viagem.class);
        }
    }

    @Override
    public void excluir(Viagem entity) throws SatiLogicalException {
        super.remove(entity);
    }
}
