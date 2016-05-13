package br.jus.trt12.paulopinheiro.sati.calendario.ejb;

import br.jus.trt12.paulopinheiro.sati.calendario.model.Feriado;
import br.jus.trt12.paulopinheiro.sati.calendario.model.FeriadoFixo;
import br.jus.trt12.paulopinheiro.sati.calendario.model.FeriadoMovel;
import br.jus.trt12.paulopinheiro.sati.exceptions.SatiLogicalException;
import br.jus.trt12.paulopinheiro.sati.geral.ejb.comum.AbstractFacade;
import br.jus.trt12.paulopinheiro.sati.util.Util;
import java.util.Collections;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
@LocalBean
public class FeriadoFacade extends AbstractFacade<Feriado> {
    @PersistenceContext(unitName = "satiPU")
    private EntityManager em;

    @Override
    public void salvar(Feriado feriado) throws SatiLogicalException {
        if (feriado!= null) {
            if ((feriado.getDescricao()==null)||(feriado.getDescricao().isEmpty())) throw new SatiLogicalException("Informe a descrição (nome) do feriado");
            if (!(feriado.getDataInstituicao() == null) && !(feriado.getDataRevogacao() == null)) {
                if (feriado.getDataInstituicao().after(feriado.getDataRevogacao())) {
                    throw new SatiLogicalException("Intervalo de datas instituição/revogação inválido");
                }
            }
            if (feriado.getCodigo()==null) super.create(feriado);
            else super.edit(feriado);
        }
    }

    public void salvarFeriadoFixo(FeriadoFixo feriadoFixo) throws SatiLogicalException {
        if (feriadoFixo != null) {
            if (!Util.diaMesValido(feriadoFixo.getDia(),feriadoFixo.getMes())) throw new SatiLogicalException("Dia/mês inválido");
            salvar(feriadoFixo);
        }
    }

    public void salvarFeriadoMovel(FeriadoMovel feriadoMovel) throws SatiLogicalException {
        salvar(feriadoMovel);
    }

    public List<FeriadoFixo> findFeriadosFixos() {
        List<FeriadoFixo> resposta = null;
        Query query = getEntityManager().createNamedQuery("FeriadoFixo.findAll");
        resposta = query.getResultList();
        if (resposta!=null) Collections.sort(resposta);
        return resposta;
    }

    public List<FeriadoMovel> findFeriadosMoveis() {
        List<FeriadoMovel> resposta = null;
        Query query = getEntityManager().createNamedQuery("FeriadoMovel.findAll");
        resposta = query.getResultList();
        if (resposta!=null) Collections.sort(resposta);
        return resposta;
    }

    @Override
    public List<Feriado> findAll() {
        List<Feriado> resposta = null;
        Query query = getEntityManager().createNamedQuery("Feriado.findAll");
        resposta = query.getResultList();
        //if (resposta!=null) Collections.sort(resposta);
        return resposta;
    }

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    public FeriadoFacade() {
        super(Feriado.class);
    }

    @Override
    public void excluir(Feriado entity) throws SatiLogicalException {
        super.remove(entity);
    }
}
