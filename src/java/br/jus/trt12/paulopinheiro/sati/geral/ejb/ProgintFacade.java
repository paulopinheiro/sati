package br.jus.trt12.paulopinheiro.sati.geral.ejb;

import br.jus.trt12.paulopinheiro.sati.geral.ejb.comum.AbstractFacade;
import br.jus.trt12.paulopinheiro.sati.geral.model.Progint;
import br.jus.trt12.paulopinheiro.sati.exceptions.SatiLogicalException;
import java.util.Collections;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class ProgintFacade extends AbstractFacade<Progint> {
    @PersistenceContext(unitName = "satiPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public void salvar(Progint progint) throws SatiLogicalException {
        if (progint!= null) {
            if ((progint.getNome()==null)||(progint.getNome().trim().isEmpty())) throw new SatiLogicalException("Informe o nome do técnico");
            if ((progint.getMatricula()==null)||(progint.getMatricula().trim().isEmpty())) throw new SatiLogicalException("Informe a matrícula do técnico");
            if ((progint.getEmail()==null)||(progint.getEmail().trim().isEmpty())) throw new SatiLogicalException("Informe o e-mail do técnico");
            if (progint.getCodigo()==null) super.create(progint);
            else super.edit(progint);
        }
    }

    public ProgintFacade() {
        super(Progint.class);
    }

    @Override
    public List<Progint> findAll() {
        List<Progint> resposta = super.findAll();
        Collections.sort(resposta);
        return resposta;
    }

    public List<Progint> findAtivos() {
        List<Progint> resposta;
        Query pesquisa = em.createNamedQuery("Progint.findAtivos");
        resposta = pesquisa.getResultList();
        Collections.sort(resposta);
        return resposta;
    }

    @Override
    public void excluir(Progint entity) throws SatiLogicalException {
        super.remove(entity);
    }
}
