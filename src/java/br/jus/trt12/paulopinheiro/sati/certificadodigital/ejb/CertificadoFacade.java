package br.jus.trt12.paulopinheiro.sati.certificadodigital.ejb;

import br.jus.trt12.paulopinheiro.sati.certificadodigital.model.Certificado;
import br.jus.trt12.paulopinheiro.sati.certificadodigital.model.MarcaEtoken;
import br.jus.trt12.paulopinheiro.sati.certificadodigital.model.StatusCertificado;
import br.jus.trt12.paulopinheiro.sati.exceptions.SatiLogicalException;
import br.jus.trt12.paulopinheiro.sati.geral.ejb.comum.AbstractFacade;
import br.jus.trt12.paulopinheiro.sati.geral.model.Municipio;
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
public class CertificadoFacade extends AbstractFacade<Certificado> {
    @PersistenceContext(unitName = "satiPU")
    private EntityManager em;

    public CertificadoFacade() {
        super(Certificado.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public void salvar(Certificado certificado) throws SatiLogicalException {
        if (certificado!=null) {
            if (certificado.getUsuario()==null) throw new SatiLogicalException("Informe o usuário do certificado");
            if (certificado.getDataGravacao()==null) throw new SatiLogicalException("Informe a data de gravação");
            if (certificado.getDataValidade()==null) throw new SatiLogicalException("Informe a data de validade");
            if (certificado.getDataValidade().before(certificado.getDataGravacao())) throw new SatiLogicalException("Data de validade deve ser posterior à de gravação");
            if (certificado.getMarcaEtoken()==null) throw new SatiLogicalException("Informe a marca do e-token");
            if (certificado.getId()==null) super.create(certificado);
            else super.edit(certificado);
        }
    }

    @Override
    public void excluir(Certificado certificado) throws SatiLogicalException {
        super.remove(certificado);
    }

    public List<Certificado> findByMunicipio(Municipio municipio) {
        List<Certificado> resposta;
        if (municipio==null) return null;
        Query query = getEntityManager().createNamedQuery("Certificado.findByMunicipio");
        query.setParameter("municipio", municipio);
        resposta = query.getResultList();
        Collections.sort(resposta);
        return resposta;
    }

    public List<Certificado> findByMunicipioVencimentoDeAte(Municipio municipio, Date vencimentoMin, Date vencimentoMax) throws SatiLogicalException {
        if ((vencimentoMax==null)||(vencimentoMin==null)) throw new SatiLogicalException("Informe o intervalo de datas");
        if (vencimentoMax.before(vencimentoMin)) throw new SatiLogicalException("Data máxima deve ser maior que a mínima");
        List<Certificado> resposta;
        Query query;
        if ((municipio==null)||(municipio.getCodigo()==null)) query = getEntityManager().createNamedQuery("Certificado.findByDateInterval");
        else {
            query = getEntityManager().createNamedQuery("Certificado.findByMunicipioDateInterval");
            query.setParameter("municipio", municipio);
        }

        query.setParameter("minDate", vencimentoMin);
        query.setParameter("maxDate", vencimentoMax);
        resposta = query.getResultList();
        return resposta;
    }

    public List<Certificado> findByMunicipioVencimentoAte(Municipio municipio, Date vencimento) throws SatiLogicalException {
        return findByMunicipioVencimentoDeAte(municipio,new Date(), vencimento);
    }

    public List<Certificado> findVencimentoAte(Date vencimentoMax) throws SatiLogicalException {
        return findByMunicipioVencimentoDeAte(null,new Date(),vencimentoMax);
    }

    public List<Certificado> findVencimentoDeAte(Date vencimentoMin, Date vencimentoMax) throws SatiLogicalException {
        return findByMunicipioVencimentoDeAte(null,vencimentoMin,vencimentoMax);
    }

    public List<MarcaEtoken> getListaMarcaEtoken() {
        List<MarcaEtoken> resposta;
        Query query = getEntityManager().createNamedQuery("MarcaEtoken.findAll");
        resposta = query.getResultList();
        Collections.sort(resposta);
        return resposta;
    }

    public List<StatusCertificado> getListaStatusCertificado() {
        List<StatusCertificado> resposta;
        Query query = getEntityManager().createNamedQuery("StatusCertificado.findAll");
        resposta = query.getResultList();
        Collections.sort(resposta);
        return resposta;
    }

    public StatusCertificado getStatusDesconhecido() {
        return getEntityManager().find(StatusCertificado.class, 1);
    }

    @Override
    protected CriteriaQuery<Certificado> getCq(Certificado filtro) {
        CriteriaQuery<Certificado> cq = this.getCb().createQuery(Certificado.class);
        Root<Certificado> root = cq.from(Certificado.class);
        cq.select(root);

        Predicate usuario = getCb().conjunction();
        Predicate marcaetoken = getCb().conjunction();
        Predicate statuscertificado = getCb().conjunction();
        Predicate datagravacao = getCb().conjunction();
        Predicate datavalidade = getCb().conjunction();
        Predicate municipio = getCb().conjunction();

        if (filtro.getMarcaEtoken()!=null) marcaetoken = getCb().equal(root.get("marcaEtoken"), filtro.getMarcaEtoken());
        if (filtro.getStatus()!=null) statuscertificado = getCb().equal(root.get("status"), filtro.getStatus());

        if (filtro.getDataGravacao()!=null) {
            Expression<Date> a_dataGravacao = root.get("dataGravacao");
            datagravacao = getCb().equal(a_dataGravacao, filtro.getDataGravacao());
        }

        if (filtro.getDataValidade()!=null) {
            Expression<Date> a_dataValidade = root.get("dataValidade");
            datagravacao = getCb().equal(a_dataValidade, filtro.getDataValidade());
        }

        if (filtro.getUsuario()!=null) {
            if (filtro.getUsuario().getId()!=null) {
                usuario = getCb().equal(root.get("usuario"), filtro.getUsuario());
            } else {
                if ((filtro.getUsuario().getUnidade()!=null)) {
                    municipio = getCb().equal(root.get("usuario").get("unidade").get("municipio"), filtro.getUsuario().getUnidade().getMunicipio());
                }
            }
        }

        cq.where(usuario,marcaetoken,statuscertificado,datagravacao,datavalidade, municipio);

        return cq;
    }
}
