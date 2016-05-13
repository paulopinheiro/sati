package br.jus.trt12.paulopinheiro.sati.calendario.ejb;

import br.jus.trt12.paulopinheiro.sati.calendario.model.ExcecaoFeriadoNacional;
import br.jus.trt12.paulopinheiro.sati.calendario.model.Feriado;
import br.jus.trt12.paulopinheiro.sati.exceptions.SatiLogicalException;
import br.jus.trt12.paulopinheiro.sati.geral.ejb.comum.AbstractFacade;
import br.jus.trt12.paulopinheiro.sati.geral.model.Municipio;
import br.jus.trt12.paulopinheiro.sati.util.Util;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class ExcecaoFeriadoNacionalFacade extends AbstractFacade<ExcecaoFeriadoNacional> {
    @PersistenceContext(unitName = "satiPU")
    private EntityManager em;

    @Override
    public void salvar(ExcecaoFeriadoNacional excecao) throws SatiLogicalException {
        if (excecao!= null) {
            if (excecao.getFeriado()==null) throw new SatiLogicalException("Informe o feriado");
            if (excecao.getMunicipio()==null) throw new SatiLogicalException("Informe o município que é exceção para o feriado");
            if (!Util.isIntervaloDatasValido(excecao.getDesde(),excecao.getAte())) throw new SatiLogicalException("Data final não pode ser menor que a inicial");
            if (isCoincidenciaDatas(excecao)) throw new SatiLogicalException("Já existe exceção para este feriado neste município com datas que contêm ou estão contidas no intervalo informado");
            if (excecao.getCodigo()==null) super.create(excecao);
            else super.edit(excecao);
        }
    }

    private boolean isCoincidenciaDatas(ExcecaoFeriadoNacional excecao) throws SatiLogicalException {
        List<ExcecaoFeriadoNacional> listaTeste = findByFeriadoMunicipio(excecao.getFeriado(), excecao.getMunicipio());
        if (!(listaTeste==null)) {
            for (ExcecaoFeriadoNacional e:listaTeste) {
                if (!(e.equals(excecao))) {
                    if (Util.coincidenciaDatas(e.getDesde(),e.getAte(),excecao.getDesde(),excecao.getAte()))
                        return true;
                }
            }
        }
        return false;
    }

    public List<ExcecaoFeriadoNacional> findByFeriado(Feriado feriado) {
        List<ExcecaoFeriadoNacional> resposta = null;
        Query query = getEntityManager().createNamedQuery("ExcecaoFeriadoNacional.findByFeriado");
        query.setParameter("feriado",feriado);
        resposta = query.getResultList();
        return resposta;
    }

    private List<ExcecaoFeriadoNacional> findByFeriadoMunicipio(Feriado feriado, Municipio municipio) {
        List<ExcecaoFeriadoNacional> resposta = null;
        Query query = this.em.createNamedQuery("ExcecaoFeriadoNacional.findByFeriadoMunicipio");
        query.setParameter("feriado", feriado);
        query.setParameter("municipio", municipio);
        resposta = (List<ExcecaoFeriadoNacional>) query.getResultList();
        return resposta;
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ExcecaoFeriadoNacionalFacade() {
        super(ExcecaoFeriadoNacional.class);
    }

    @Override
    public void excluir(ExcecaoFeriadoNacional entity) throws SatiLogicalException {
        super.remove(entity);
    }

}
