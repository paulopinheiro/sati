package br.jus.trt12.paulopinheiro.sati.backup.ejb;

import br.jus.trt12.paulopinheiro.sati.backup.model.Fita;
import br.jus.trt12.paulopinheiro.sati.backup.model.FitaDados;
import br.jus.trt12.paulopinheiro.sati.backup.model.FitaLimpeza;
import br.jus.trt12.paulopinheiro.sati.exceptions.SatiLogicalException;
import br.jus.trt12.paulopinheiro.sati.geral.ejb.comum.AbstractFacade;
import br.jus.trt12.paulopinheiro.sati.geral.model.Municipio;
import java.util.Collections;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class FitaFacade extends AbstractFacade<Fita> {
    @PersistenceContext(unitName = "satiPU")
    private EntityManager em;

    @Override
    public void salvar(Fita fita) throws SatiLogicalException {
        if (fita!=null) {
            if ((fita.getSerie()==null)||(fita.getSerie().isEmpty())) throw new SatiLogicalException("Informe o número de série da fita");
            if ((fita.getMarcaModelo()==null)||fita.getMarcaModelo().isEmpty()) throw new SatiLogicalException("Informe a marca/modelo da fita");
            if (!fita.isDatasConsistentes()) throw new SatiLogicalException("Data da baixa não pode ser menor que a data de início de uso");
            if (fita.getId()==null) super.create(fita);
            else super.edit(fita);
        }
    }

    public void salvarFitaLimpeza(FitaLimpeza fitaLimpeza) throws SatiLogicalException {
        if (fitaLimpeza!=null) {
            if (fitaLimpeza.getMunicipio()==null) throw new SatiLogicalException("Informe o município onde está a fita de limpeza");
            if ((fitaLimpeza.getMaximoLimpezas()==null)||(fitaLimpeza.getMaximoLimpezas()<0)) throw new SatiLogicalException("Informe um valor válido para o máximo de limpezas que podem ser realizadas pela fita");
            salvar(fitaLimpeza);
        }
    }

    public void salvarFitaDados(FitaDados fitaDados) throws SatiLogicalException {
        if (fitaDados !=null) {
            if ((fitaDados.getMaximoGravacoes()==null)||(fitaDados.getMaximoGravacoes()<0)) throw new SatiLogicalException("Informe um valor válido para o máximo de gravações que podem ser realizadas na fita");
            if ((fitaDados.getCapacidade()==null)||(fitaDados.getCapacidade().isEmpty())) throw new SatiLogicalException("Informe a capacidade de gravação da fita");
            if (fitaDados.getCategoriaFitaDados()==null) throw new SatiLogicalException("Informe a categoria da fita de dados");
            if (!fitaDados.isConsistente()) throw new SatiLogicalException("Informe o conjunto ao qual pertence esta fita de dados ou informe a data da baixa");
            salvar(fitaDados);
            //TODO: Atualizar quantidade no conjunto!!!
            //Repensar a arquitetura dos dados
        }
    }

    public List<FitaDados> findFitasDadosAtivasByMunicipio(Municipio municipio) {
        List<FitaDados> resposta = null;
        Query query = getEntityManager().createNamedQuery("FitaDados.findAtivasByMunicipio");
        query.setParameter(":municipio", municipio);
        resposta = query.getResultList();
        if (resposta!=null) Collections.sort(resposta);
        return resposta;
    }

    public List<FitaLimpeza> findFitasLimpezaAtivasByMunicipio(Municipio municipio) {
        List<FitaLimpeza> resposta = null;
        Query query = getEntityManager().createNamedQuery("FitaLimpeza.findAtivasByMunicipio");
        query.setParameter(":municipio", municipio);
        resposta = query.getResultList();
        if (resposta!=null) Collections.sort(resposta);
        return resposta;
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FitaFacade() {
        super(Fita.class);
    }

    @Override
    public void excluir(Fita entity) throws SatiLogicalException {
        super.remove(entity);
    }
}
