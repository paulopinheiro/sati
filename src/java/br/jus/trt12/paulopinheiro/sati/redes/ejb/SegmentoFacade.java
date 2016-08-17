package br.jus.trt12.paulopinheiro.sati.redes.ejb;

import br.jus.trt12.paulopinheiro.sati.exceptions.SatiLogicalException;
import br.jus.trt12.paulopinheiro.sati.geral.ejb.comum.AbstractFacade;
import br.jus.trt12.paulopinheiro.sati.redes.model.Segmento;
import br.jus.trt12.paulopinheiro.sati.redes.model.Tomada;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class SegmentoFacade extends AbstractFacade<Segmento> {
    @PersistenceContext(unitName = "satiPU")
    private EntityManager em;

    public SegmentoFacade() {
        super(Segmento.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    @Override
    public void salvar(Segmento segmento) throws SatiLogicalException {
        if (segmento != null) {
            if (segmento.getNome()==null||segmento.getNome().trim().isEmpty()) throw new SatiLogicalException("Informe um nome para o segmento");
            if (segmento.getExtensao()!=null && segmento.getExtensao() < 0) throw new SatiLogicalException("A extensão do segmento não pode ser negativa");

            // Segmento deve possuir duas e somente duas tomadas
            if (segmento.getTomadas()==null)  throw new SatiLogicalException("Informe as duas tomadas que são unidas pelo segmento");
            if (segmento.getTomadas().size()!=2) throw new SatiLogicalException("O segmento deve unir duas e somente duas tomadas");

            if (segmento.getCodigo()==null) super.create(segmento);
            else super.edit(segmento);
        }
    }

    public Tomada tomadaOutraPontaSegmento(Tomada tomada) throws SatiLogicalException {
        if (tomada==null) throw new SatiLogicalException("Informe a tomada");

        Segmento segmento = tomada.getSegmento();
        if (segmento==null) return null;
        if (segmento.getTomadas()==null||segmento.getTomadas().size()!=2) throw new SatiLogicalException("Há um problema com o segmento de código " + segmento.getCodigo()+ ". Contatar administrador do sistema");

        if (segmento.getTomadas().get(0).equals(tomada))
            return segmento.getTomadas().get(1);
        else
            return segmento.getTomadas().get(0);
    }

    @Override
    public void excluir(Segmento segmento) throws SatiLogicalException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
