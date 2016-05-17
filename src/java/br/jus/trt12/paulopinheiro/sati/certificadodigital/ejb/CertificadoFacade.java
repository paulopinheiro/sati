package br.jus.trt12.paulopinheiro.sati.certificadodigital.ejb;

import br.jus.trt12.paulopinheiro.sati.certificadodigital.model.Certificado;
import br.jus.trt12.paulopinheiro.sati.exceptions.SatiLogicalException;
import br.jus.trt12.paulopinheiro.sati.geral.ejb.comum.AbstractFacade;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
}
