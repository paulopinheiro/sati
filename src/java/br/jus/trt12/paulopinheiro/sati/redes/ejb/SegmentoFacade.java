package br.jus.trt12.paulopinheiro.sati.redes.ejb;

import br.jus.trt12.paulopinheiro.sati.exceptions.SatiLogicalException;
import br.jus.trt12.paulopinheiro.sati.geral.ejb.comum.AbstractFacade;
import br.jus.trt12.paulopinheiro.sati.redes.model.Segmento;
import br.jus.trt12.paulopinheiro.sati.redes.model.Tomada;
import br.jus.trt12.paulopinheiro.sati.redes.model.TomadaPanel;
import br.jus.trt12.paulopinheiro.sati.redes.model.TomadaRemota;
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

            if (segmento.getCodigo()==null) {
                validarNovoSegmento(segmento);
                super.create(segmento);
            }
            else {
                validarAlteracaoSegmento(segmento);
                super.edit(segmento);
            }
            getEntityManager().getEntityManagerFactory().getCache().evict(Tomada.class);
        }
    }

    private void validacaoPreliminarDados(Segmento segmento) throws SatiLogicalException {
        if (segmento.getExtensao()!=null && segmento.getExtensao() < 0) throw new SatiLogicalException("A extensão do segmento deve ser positiva");

        if (segmento.getTomada1()==null||segmento.getTomada2()==null)  throw new SatiLogicalException("Informe as duas tomadas que são unidas pelo segmento");
        if (segmento.getTomada1().equals(segmento.getTomada2())) throw new SatiLogicalException("Informe tomadas diferentes para as duas pontas do segmento");
        if (segmento.getTomada1()!=null && segmento.getTomada2()!=null) {
            validacaoTomadaPanel(segmento);
            validacaoTomadaRemota(segmento);
        }
    }

    private void validacaoTomadaPanel(Segmento segmento) throws SatiLogicalException {
        if ((segmento.getTomada1() instanceof TomadaPanel)&&(segmento.getTomada2() instanceof TomadaPanel)) {
            TomadaPanel t1 = (TomadaPanel) segmento.getTomada1();
            TomadaPanel t2 = (TomadaPanel) segmento.getTomada2();

            if (t1.getPanel().equals(t2.getPanel())) throw new SatiLogicalException("Não pode haver um segmento entre tomadas do mesmo painel");
        }
    }

    private void validacaoTomadaRemota(Segmento segmento) throws SatiLogicalException {
        if ((segmento.getTomada1() instanceof TomadaRemota)&&(segmento.getTomada2() instanceof TomadaRemota)) {
            TomadaRemota t1 = (TomadaRemota) segmento.getTomada1();
            TomadaRemota t2 = (TomadaRemota) segmento.getTomada2();

            if (t1.getModulo().equals(t2.getModulo())) throw new SatiLogicalException("Não pode haver um segmento entre tomadas do mesmo módulo");
        }
    }

    private void validarNovoSegmento(Segmento segmento) throws SatiLogicalException {
        for (Tomada t: tomadasSegmentoAsList(segmento)) {
            Tomada outra = findOutraPontaTomada(t);
            if (outra != null) throw new SatiLogicalException("Tomada " + t + " já se encontra em outro segmento com a tomada " + outra);
        }
    }

    private void validarAlteracaoSegmento(Segmento segmento) throws SatiLogicalException {
        for (Tomada t:tomadasSegmentoAsList(segmento)) {
            Segmento s = findByTomada(t);
            if ((s!=null)&&(s.getCodigo()!=null)&&(!s.getCodigo().equals(segmento.getCodigo())))
                throw new SatiLogicalException("Tomada " + t + " já se encontra em outro segmento com a tomada " + findOutraPontaTomada(t));
        }
    }

    private List<Tomada> tomadasSegmentoAsList(Segmento segmento) {
        List<Tomada> resposta = new ArrayList<Tomada>();
        resposta.add(segmento.getTomada1());
        resposta.add(segmento.getTomada2());
        return resposta;
    }

    public Tomada findOutraPontaTomada(Tomada tomada) {
        List<Tomada> resposta;
        Query query = getEntityManager().createNamedQuery("Segmento.findOutraPontaTomada");
        query.setParameter("tomada", tomada);
        resposta = query.getResultList();
        if (resposta==null||resposta.isEmpty()) return null;
        return resposta.get(0);
    }

    public Segmento findByTomada(Tomada tomada) {
        List<Segmento> resposta;
        Query query = getEntityManager().createNamedQuery("Segmento.findByTomada");
        query.setParameter("tomada", tomada);
        resposta = query.getResultList();
        if (resposta==null||resposta.isEmpty()) return null;
        return resposta.get(0);
    }

    @Override
    public void excluir(Segmento segmento) throws SatiLogicalException {
        super.remove(segmento);
        getEntityManager().getEntityManagerFactory().getCache().evict(Tomada.class);
    }
}
