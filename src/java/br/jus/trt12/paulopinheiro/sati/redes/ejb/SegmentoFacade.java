package br.jus.trt12.paulopinheiro.sati.redes.ejb;

import br.jus.trt12.paulopinheiro.sati.exceptions.InfraEstruturaException;
import br.jus.trt12.paulopinheiro.sati.exceptions.SatiLogicalException;
import br.jus.trt12.paulopinheiro.sati.geral.ejb.comum.AbstractFacade;
import br.jus.trt12.paulopinheiro.sati.redes.model.Segmento;
import br.jus.trt12.paulopinheiro.sati.redes.model.Tomada;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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
            validacaoPreliminarDados(segmento);

            if (segmento.getCodigo()==null) criarSegmento(segmento);
            else editarSegmento(segmento);
        }
    }

    private void validacaoPreliminarDados(Segmento segmento) throws SatiLogicalException {
        if (segmento.getExtensao()!=null && segmento.getExtensao() <= 0) throw new SatiLogicalException("A extensão do segmento deve ser maior do que zero");

        if (segmento.getTomada1()==null||segmento.getTomada2()==null)  throw new SatiLogicalException("Informe as duas tomadas que são unidas pelo segmento");
        if (segmento.getTomada1().equals(segmento.getTomada2())) throw new SatiLogicalException("Informe tomadas diferentes para as duas pontas do segmento");
    }

    private void criarSegmento(Segmento segmento) throws SatiLogicalException {
        for (Tomada t: tomadasSegmentoAsList(segmento)) {
            Tomada outra = findOutraPontaTomada(t);
            if (outra != null) throw new SatiLogicalException("Tomada " + t + " já se encontra em outro segmento com a tomada " + outra);
        }
        super.create(segmento);
    }

    private void editarSegmento(Segmento segmento) throws SatiLogicalException {
        Segmento original = super.find(segmento.getCodigo());
        if (original == null) throw new InfraEstruturaException("Ocorreu um erro ao alterar dados do segmento. Ele pode ter sido excluído. Pesquise-o novamente.");

        if (!segmento.getTomada1().equals(original.getTomada1())||!segmento.getTomada2().equals(original.getTomada2()))
            throw new SatiLogicalException("Não é permitido alterar as tomadas de um segmento. Por favor, remova e crie outro segmento.");

        super.edit(segmento);
    }

    private List<Tomada> tomadasSegmentoAsList(Segmento segmento) {
        List<Tomada> resposta = new ArrayList<Tomada>();
        resposta.add(segmento.getTomada1());
        resposta.add(segmento.getTomada2());
        return resposta;
    }

    private Tomada findOutraPontaTomada(Tomada tomada) {
        List<Tomada> resposta;
        Query query = getEntityManager().createNamedQuery("Segmento.findOutraPontaTomada");
        query.setParameter("tomada", tomada);
        resposta = query.getResultList();
        if (resposta==null||resposta.size()==0) return null;
        return resposta.get(0);
    }

    @Override
    public void excluir(Segmento segmento) throws SatiLogicalException {
        super.remove(segmento);
    }
}
