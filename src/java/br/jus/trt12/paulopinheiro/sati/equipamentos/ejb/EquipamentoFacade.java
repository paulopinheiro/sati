package br.jus.trt12.paulopinheiro.sati.equipamentos.ejb;

import br.jus.trt12.paulopinheiro.sati.equipamentos.model.Equipamento;
import br.jus.trt12.paulopinheiro.sati.equipamentos.model.Lote;
import br.jus.trt12.paulopinheiro.sati.equipamentos.model.Modelo;
import br.jus.trt12.paulopinheiro.sati.equipamentos.model.TipoEquipamento;
import br.jus.trt12.paulopinheiro.sati.exceptions.SatiLogicalException;
import br.jus.trt12.paulopinheiro.sati.geral.ejb.comum.AbstractFacade;
import br.jus.trt12.paulopinheiro.sati.geral.model.Municipio;
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
public class EquipamentoFacade extends AbstractFacade<Equipamento> {
    @PersistenceContext(unitName = "satiPU")
    private EntityManager em;

    public EquipamentoFacade() {
        super(Equipamento.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public void salvar(Equipamento equipamento) throws SatiLogicalException {
        if (equipamento==null) equipamento = new Equipamento();
        if ((equipamento.getTombo()==null)||(equipamento.getTombo().trim().isEmpty())) throw new SatiLogicalException("Informe o tombo do equipamento (deve ser único no cadastro).");
        if ((equipamento.getLote()==null)) throw new SatiLogicalException("Informe o lote no qual este equipamento foi adquirido.");
        if ((equipamento.getUsuarioEquipamento()!=null)&&(equipamento.getUsuarioEquipamento().getUnidade()!=null)&&(!equipamento.getUsuarioEquipamento().getUnidade().equals(equipamento.getUnidade()))) {
            throw new SatiLogicalException("O usuário deve pertencer à unidade informada");
        }
        if (equipamento.getCodigo()==null) {
            equipamento.setAtivo(true);
            this.create(equipamento);
        }
        else this.edit(equipamento);
    }

    @Override
    public void excluir(Equipamento equipamento) throws SatiLogicalException {
        this.remove(equipamento);
    }

    public void baixar(Equipamento equipamento) throws SatiLogicalException {
        if ((equipamento==null)||(equipamento.getCodigo()==null)) throw new SatiLogicalException("Não é possível baixar um equipamento ainda não cadastrado");
        equipamento.setAtivo(false);
        this.edit(equipamento);
    }

    public void reativar(Equipamento equipamento) throws SatiLogicalException {
        if ((equipamento==null)||(equipamento.getCodigo()==null)) throw new SatiLogicalException("Não é possível reativar um equipamento ainda não cadastrado");
        if (equipamento.isAtivo()) throw new SatiLogicalException("Equipamento não está baixado");
        equipamento.setAtivo(true);
        this.edit(equipamento);
    }

    public List<Equipamento> findAtivos() {
        List<Equipamento> resposta;
        Query query = getEntityManager().createNamedQuery("Equipamento.findAtivos");
        resposta = query.getResultList();
        if (resposta!=null) Collections.sort(resposta);
        return resposta;
    }

    public List<Equipamento> findAtivosByMunicipio(Municipio municipio) {
        List<Equipamento> resposta;
        Query query = getEntityManager().createNamedQuery("Equipamento.findAtivosByMunicipio");
        query.setParameter("municipio", municipio);
        resposta = query.getResultList();
        if (resposta!=null) Collections.sort(resposta);
        return resposta;
    }

    public List<Equipamento> findAtivosByMunicipioTipoEquipamento(Municipio municipio, TipoEquipamento tipoEquipamento) {
        List<Equipamento> resposta;
        Query query = getEntityManager().createNamedQuery("Equipamento.findAtivosByMunicipioTipoEquipamento");
        query.setParameter("municipio", municipio);
        query.setParameter("tipoEquipamento", tipoEquipamento);
        resposta = query.getResultList();
        if (resposta!=null) Collections.sort(resposta);
        return resposta;
    }

    public List<Equipamento> findAtivosFiltro(TipoEquipamento tipoEquipamento, Modelo modelo, Lote lote, String tombo, Municipio municipio) {
        List<Equipamento> resposta;
        String p_tombo;
        Query pesquisa;

        if ((tombo==null)||(tombo.trim().isEmpty())) p_tombo="%";
        else p_tombo=tombo;

        if (lote!=null) {
            pesquisa = getEntityManager().createNamedQuery("Equipamento.findAtivosByLoteTomboMunicipio");
            pesquisa.setParameter("lote", lote);
        } else {
            if (modelo!=null) {
                pesquisa = getEntityManager().createNamedQuery("Equipamento.findAtivosByModeloTomboMunicipio");
                pesquisa.setParameter("modelo", modelo);
            } else {
                if (tipoEquipamento!=null) {
                    pesquisa = getEntityManager().createNamedQuery("Equipamento.findAtivosByTipoEquipamentoTomboMunicipio");
                    pesquisa.setParameter("tipoEquipamento", tipoEquipamento);
                } else {
                    pesquisa = getEntityManager().createNamedQuery("Equipamento.findAtivosByTomboMunicipio");
                }
            }
        }
        pesquisa.setParameter("tombo_maiusculo", p_tombo.toUpperCase());
        pesquisa.setParameter("municipio", municipio);
        resposta = pesquisa.getResultList();

        if (resposta!=null) Collections.sort(resposta);

        return resposta;
    }

    public List<Equipamento> findBaixadosByTipo(TipoEquipamento tipo) {
        List<Equipamento> resposta;
        Query query = getEntityManager().createNamedQuery("Equipamento.findBaixadosByTipo");
        query.setParameter("tipoEquipamento", tipo);
        resposta = query.getResultList();
        if (resposta!=null) Collections.sort(resposta);
        System.out.println(resposta.size() + " equipamentos baixados encontrados");
        return resposta;
    }

    public Equipamento findBaixadoByTombo(String tombo) throws SatiLogicalException {
        Equipamento resposta;
        Query query = getEntityManager().createNamedQuery("Equipamento.findBaixadoByTombo");
        query.setParameter("tombo", tombo);
        resposta = (Equipamento) query.getSingleResult();
        if (resposta==null) throw new SatiLogicalException("Não encontrado equipamento baixado de tombo " + tombo);
        return resposta;
    }

    @Override
    protected CriteriaQuery<Equipamento> getCq(Equipamento filtro) {
        CriteriaQuery<Equipamento> cq = this.getCb().createQuery(Equipamento.class);
        Root<Equipamento> root = cq.from(Equipamento.class);
        cq.select(root);

        Predicate tombo = getCb().conjunction();
        Predicate lote = getCb().conjunction();
        Predicate unidade = getCb().conjunction();
        Predicate ativo;
        Predicate observacao = getCb().conjunction();
        Predicate localizacao = getCb().conjunction();
        Predicate nroSerie = getCb().conjunction();
        Predicate usuarioEquipamento = getCb().conjunction();

        if ((filtro.getTombo()!=null)&&(!filtro.getTombo().isEmpty())) {
            Expression<String> a_tombo = root.get("tombo");
            tombo = getCb().like(getCb().upper(a_tombo), filtro.getTombo().toUpperCase());
        }
        if (filtro.getLote()!=null) lote = getCb().equal(root.get("lote"), filtro.getLote());
        if (filtro.getUnidade()!=null) unidade = getCb().equal(root.get("unidade"), filtro.getUnidade());
        ativo = getCb().equal(root.get("ativo"), filtro.isAtivo());
        if ((filtro.getObservacao()!=null)&&(!filtro.getObservacao().isEmpty())) {
            Expression<String> a_observacao = root.get("observacao");
            observacao = getCb().like(getCb().upper(a_observacao), filtro.getObservacao().toUpperCase());
        }
        if ((filtro.getLocalizacao()!=null)&&(!filtro.getLocalizacao().isEmpty())) {
            Expression<String> a_localizacao = root.get("localizacao");
            localizacao = getCb().like(getCb().upper(a_localizacao), filtro.getLocalizacao().toUpperCase());
        }
        if ((filtro.getNroSerie()!=null)&&(!filtro.getNroSerie().isEmpty())) {
            Expression<String> a_nroSerie = root.get("nroSerie");
            nroSerie = getCb().like(getCb().upper(a_nroSerie), filtro.getNroSerie().toUpperCase());
        }
        if (filtro.getUsuarioEquipamento()!=null) usuarioEquipamento = getCb().equal(root.get("usuarioEquipamento"), filtro.getUsuarioEquipamento());

        cq.where(tombo,lote,unidade,ativo,observacao,localizacao,nroSerie,usuarioEquipamento);

        return cq;
    }

     public void movimentar(List<Equipamento> equipamentos, boolean isCircular) throws SatiLogicalException {
        if ((equipamentos==null)||(equipamentos.size()<2)) throw new SatiLogicalException("Ao menos dois equipamentos devem ser informados");
        for (Equipamento e:equipamentos) if (!e.isAtivo()) throw new SatiLogicalException("Todos os equipamentos devem estar ativos no sistema");
                 
        Equipamento tmpEquipamento = new Equipamento();

        //Armazena em tmpEquipamento os dados do primeiro da lista
        transfereDadosEquipamento(equipamentos.get(0),tmpEquipamento);

        // Até o último, transfere-se os dados de cada equipamento para seu anterior
        for (int i = 1; i < equipamentos.size(); i++) {
            transfereDadosEquipamento(equipamentos.get(i),equipamentos.get(i-1));
        }
        //Se for uma lista circular o último recebe os dados do primeiro
        if (isCircular) transfereDadosEquipamento(tmpEquipamento,equipamentos.get(equipamentos.size()-1));
        //Senão setamos o campo ativo do último como falso, baixando o equipamento
        else equipamentos.get(equipamentos.size()-1).setAtivo(false);

        //Finalmente fazendo o merge no banco de dados
        for (Equipamento equipamento:equipamentos) em.merge(equipamento);
     }

    private static void transfereDadosEquipamento(Equipamento equipTransfere, Equipamento equipRecebe) throws SatiLogicalException {
        if ((equipTransfere==null)||equipRecebe==null) throw new SatiLogicalException("Erro ao transferir equipamento (dados nulos)");
        if (tiposIncompativeisEquipamento(equipTransfere,equipRecebe)) throw new SatiLogicalException("Equipamentos devem ser do mesmo tipo");

        equipRecebe.setUnidade(equipTransfere.getUnidade());
        equipRecebe.setUsuarioEquipamento(equipTransfere.getUsuarioEquipamento());
    }

    //Dois equipamentos são incompatíveis para troca se e somente seus tipos não forem nulos e não forem iguais
    private static boolean tiposIncompativeisEquipamento(Equipamento equipamento1, Equipamento equipamento2) {
        TipoEquipamento tipo1 = null;
        TipoEquipamento tipo2 = null;
        
        try {
            tipo1 = equipamento1.getLote().getModelo().getTipoEquipamento();
            tipo2 = equipamento2.getLote().getModelo().getTipoEquipamento();
        } catch (NullPointerException ex) {}

        return ((tipo1!=null)&&(tipo2!=null)&&(!tipo1.equals(tipo2)));
    }
}
