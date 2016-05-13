package br.jus.trt12.paulopinheiro.sati.redes.ejb;

import br.jus.trt12.paulopinheiro.sati.geral.ejb.comum.AbstractFacade;
import br.jus.trt12.paulopinheiro.sati.redes.model.Panel;
import br.jus.trt12.paulopinheiro.sati.redes.model.Rack;
import br.jus.trt12.paulopinheiro.sati.redes.model.TomadaPanel;
import br.jus.trt12.paulopinheiro.sati.exceptions.SatiLogicalException;
import br.jus.trt12.paulopinheiro.sati.exceptions.InfraEstruturaException;
import br.jus.trt12.paulopinheiro.sati.util.Util;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
@LocalBean
public class PanelFacade extends AbstractFacade<Panel> {
    @PersistenceContext(unitName = "satiPU")
    private EntityManager em;

    //Após conferir campos obrigatórios e forçar o nome para maiúsculas,
    //o método pesquisa se já existe um panel com este nome para este rack
    //referenciamos este panel em "pesq"
    //Este pesq vai ser útil em dois momentos:
    //  1. Pra evitar problemas com a constraint UNIQUE de nome do panel no rack
    //  2. Para evitar alteração no campo quantPortas
    @Override
    public void salvar(Panel panel) throws SatiLogicalException {
        if (panel!=null) {
            if ((panel.getNome() == null) || panel.getNome().trim().isEmpty()) throw new SatiLogicalException("Informe o nome do panel (deve ser um nome único no rack)");
            if ((panel.getQuantPortas() == null) || panel.getQuantPortas() < 1) throw new SatiLogicalException("Informe um número válido de portas do panel");
            if (panel.getRack() == null) throw new SatiLogicalException("Informe o rack onde está instalado o panel");

            //Forçando nome do panel para maiúsculas
            panel.setNome(panel.getNome().trim().toUpperCase());

            //Pesquisa se existe um panel com esse nome no rack
            Panel pesq = findByRackNomePanel(panel.getNome(), panel.getRack());

            //Caso exista e não seja o mesmo panel que está sendo salvo, retorna erro: violação de UNIQUE
            if (!isUnicoRackNomePanel(panel, pesq)) throw new SatiLogicalException("Já existe um panel com esse nome no rack");

            if (panel.getCodigo()==null) { //novo panel: gerar tomadas
                panel.setTomadas(gerarTomadas(panel));
                super.create(panel);
            } else { //atualização de panel: Qtde portas não pode ser alterada
                if (isAlteracaoQuantPortasPanel(panel,pesq)) throw new SatiLogicalException("Não é permitida a alteração no número de tomadas do panel. Se for necessário, elimine este e crie outro.");
                super.edit(panel);
            }
        }
    }

    //Se não existir um panel com mesmo nome no rack, ou se a pesquisa retornar
    //o mesmo está tudo ok. Senão é violação de UNIQUE constraint
    private boolean isUnicoRackNomePanel(Panel panel, Panel pesq) {
        if ((pesq==null) || (pesq.equals(panel))) return true;
        return false;
    }

    // Se codigo não for null, pesquisar o original e comparar a quantidade de portas
    private boolean isAlteracaoQuantPortasPanel(Panel panel, Panel pesq) {
        return ((pesq!=null) &&
            (pesq.getCodigo()!=null) &&
            (panel.getCodigo()!=null) &&
            (pesq.equals(panel)) &&
            (!panel.getQuantPortas().equals(pesq.getQuantPortas()))); //Objeto Integer
    }

    //ATENÇÃO: se for um panel com mais de 99 portas vai dar erro
    private static List<TomadaPanel> gerarTomadas(Panel panel) {
        List<TomadaPanel> tomadas = new ArrayList<TomadaPanel>();
        DecimalFormat df = new DecimalFormat("00");
        for (int i = 0;i<panel.getQuantPortas();i++) {
            TomadaPanel tomada = new TomadaPanel(panel);
            tomada.setNome("T"+df.format(i+1));
            tomadas.add(tomada);
        }
        return tomadas;
    }

    public List<Panel> findByRack(Rack rack) {
        List<Panel> resposta = null;
        Query query = getEntityManager().createNamedQuery("Panel.panelsByRack");
        query.setParameter("rack", rack);
        resposta = query.getResultList();
        Collections.sort(resposta);
        return resposta;
    }

    private Panel findByRackNomePanel(String nome, Rack rack) {
        Panel result = null;
        Query query = getEntityManager().createNamedQuery("Panel.panelByRackNomePanel");
        query.setParameter("nome", nome);
        query.setParameter("rack", rack);
        try {
            result = (Panel) query.getSingleResult();
        } catch (NoResultException ex) {
            // não existe painel com esse nome no rack
        } finally {
            return result;
        }
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PanelFacade() {
        super(Panel.class);
    }

    public List<TomadaPanel> getListaTomadas(Panel panel) {
        List<TomadaPanel> resposta = null;
        Query query = getEntityManager().createNamedQuery("TomadaPanel.tomadasPanelByPanel");
        query.setParameter("panel", panel);
        resposta = query.getResultList();
        Collections.sort(resposta);
        return resposta;
    }


    public void salvaTomadaPanel(TomadaPanel tomadaPanel) throws SatiLogicalException {
        if (!(tomadaPanel==null)) {
            //Validações
            if (tomadaPanel.getCodigo()==null) throw new SatiLogicalException("Não é permitido criar individualmente tomadas de painel");
            if ((tomadaPanel.getNome()==null)||tomadaPanel.getNome().trim().isEmpty()) throw new SatiLogicalException("Tomada de painel sem nome");
            // Implementar verificação: ramal (voice) e segmento são mutuamente exclusivos
            // ou uma tomada é voice ou está ligada a outra remota (ou ainda por extensão)
            if (tomadaPanel.getRamal()!=null) tomadaPanel.setRamal(tomadaPanel.getRamal().trim());
            if ((tomadaPanel.getSegmento()!=null)&&(tomadaPanel.getRamal()!=null)) throw new SatiLogicalException("Uma tomada de painel não pode estar ao mesmo tempo ligada a um segmento e ativada por um ramal");

            try {
                getEntityManager().merge(tomadaPanel);
            } catch (Exception ex) {throw new InfraEstruturaException("Erro ao salvar Tomada: " + Util.limpaException(ex.getMessage()));}
        }
    }

    @Override
    public void excluir(Panel entity) throws SatiLogicalException {
        super.remove(entity);
    }
}
