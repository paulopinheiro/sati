package br.jus.trt12.paulopinheiro.sati.geral.ejb;

import br.jus.trt12.paulopinheiro.sati.exceptions.SatiLogicalException;
import br.jus.trt12.paulopinheiro.sati.geral.ejb.comum.AbstractFacade;
import br.jus.trt12.paulopinheiro.sati.geral.model.Municipio;
import br.jus.trt12.paulopinheiro.sati.geral.model.Unidade;
import br.jus.trt12.paulopinheiro.sati.geral.model.UsuarioFinal;
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
public class UsuarioFinalFacade extends AbstractFacade<UsuarioFinal> {
    @PersistenceContext(unitName = "satiPU")
    private EntityManager em;

    public UsuarioFinalFacade() {
        super(UsuarioFinal.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    @Override
    public void salvar(UsuarioFinal usuario) throws SatiLogicalException {
        if (usuario!=null) {
            if (usuario.getNome()==null||usuario.getNome().trim().isEmpty()) throw new SatiLogicalException("Informe o nome do usuário");
            if (usuario.getUnidade()==null) throw new SatiLogicalException("Informe a unidade onde o usuário está lotado");
            if (usuario.getId()==null) super.create(usuario);
            else super.edit(usuario);
        }
    }

    @Override
    public void excluir(UsuarioFinal usuario) throws SatiLogicalException {
        this.remove(usuario);
    }

    public List<UsuarioFinal> findByMunicipio(Municipio municipio) {
        List<UsuarioFinal> resposta;
        if (municipio==null) return null;
        Query query = getEntityManager().createNamedQuery("UsuarioFinal.findByMunicipio");
        query.setParameter("municipio", municipio);
        resposta = query.getResultList();
        Collections.sort(resposta);
        return resposta;
    }
 
    @Override
    protected CriteriaQuery<UsuarioFinal> getCq(UsuarioFinal filtro) {
        CriteriaQuery<UsuarioFinal> cq = this.getCb().createQuery(UsuarioFinal.class);
        Root<UsuarioFinal> root = cq.from(UsuarioFinal.class);
        cq.select(root);

        Predicate nome = getCb().conjunction();
        Predicate email = getCb().conjunction();
        Predicate matricula = getCb().conjunction();
        Predicate fonecontato = getCb().conjunction();
        Predicate ativo;
        Predicate observacao = getCb().conjunction();
        Predicate unidade = getCb().conjunction();
        Predicate municipio = getCb().conjunction();

        if ((filtro.getNome()!=null)&&(!filtro.getNome().isEmpty())) {
            Expression<String> a_nome = root.get("nome");
            nome = getCb().like(getCb().upper(a_nome), filtro.getNome().toUpperCase());
        }
        if ((filtro.getEmail()!=null)&&(!filtro.getEmail().isEmpty())) {
            Expression<String> a_email = root.get("email");
            email = getCb().like(getCb().upper(a_email), filtro.getEmail().toUpperCase());
        }
        if ((filtro.getMatricula()!=null)&&(!filtro.getMatricula().isEmpty())) {
            Expression<String> a_matricula = root.get("matricula");
            matricula = getCb().like(getCb().upper(a_matricula), filtro.getMatricula().toUpperCase());
        }

        if ((filtro.getFonecontato()!=null)&&(!filtro.getFonecontato().isEmpty())) {
            Expression<String> a_fonecontato = root.get("fonecontato");
            fonecontato = getCb().like(getCb().upper(a_fonecontato), filtro.getFonecontato().toUpperCase());
        }

        if (filtro.getUnidade()!=null) {
            if (filtro.getUnidade().getCodigo()!=null) {
                unidade = getCb().equal(root.get("unidade"), filtro.getUnidade());
            } else {
                municipio = getCb().equal(root.get("unidade").get("municipio"), filtro.getUnidade().getMunicipio());
            }
        }
        ativo = getCb().equal(root.get("ativo"), filtro.isAtivo());

        if ((filtro.getObservacao()!=null)&&(!filtro.getObservacao().isEmpty())) {
            Expression<String> a_observacao = root.get("observacao");
            observacao = getCb().like(getCb().upper(a_observacao), filtro.getObservacao().toUpperCase());
        }

        cq.where(nome,email,matricula,unidade,ativo,observacao,fonecontato,municipio);

        return cq;
    }

    public List<UsuarioFinal> findByUnidade(Unidade unidade) throws SatiLogicalException {
        List<UsuarioFinal> resposta;
        if (unidade==null) return null;
        Query query = getEntityManager().createNamedQuery("UsuarioFinal.findByUnidade");
        query.setParameter("unidade", unidade);
        resposta = query.getResultList();
        Collections.sort(resposta);
        return resposta;
    }
}
