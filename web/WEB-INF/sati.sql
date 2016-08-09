--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- Name: sati; Type: COMMENT; Schema: -; Owner: sati
--

COMMENT ON DATABASE sati IS 'Banco de dados do Sistema de Apoio ao Técnico de Informática';


--
-- Name: backup; Type: SCHEMA; Schema: -; Owner: sati
--

CREATE SCHEMA backup;


ALTER SCHEMA backup OWNER TO sati;

--
-- Name: calendario; Type: SCHEMA; Schema: -; Owner: sati
--

CREATE SCHEMA calendario;


ALTER SCHEMA calendario OWNER TO sati;

--
-- Name: certificacao; Type: SCHEMA; Schema: -; Owner: sati
--

CREATE SCHEMA certificacao;


ALTER SCHEMA certificacao OWNER TO sati;

--
-- Name: equipamentos; Type: SCHEMA; Schema: -; Owner: sati
--

CREATE SCHEMA equipamentos;


ALTER SCHEMA equipamentos OWNER TO sati;

--
-- Name: redes; Type: SCHEMA; Schema: -; Owner: sati
--

CREATE SCHEMA redes;


ALTER SCHEMA redes OWNER TO sati;

--
-- Name: viagem; Type: SCHEMA; Schema: -; Owner: sati
--

CREATE SCHEMA viagem;


ALTER SCHEMA viagem OWNER TO sati;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

--
-- Name: trg_portas_panel(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION trg_portas_panel() RETURNS trigger
    LANGUAGE plpgsql
    AS $$BEGIN
	IF NEW.quantportas IS NULL THEN
		RAISE EXCEPTION 'Quantidade inválida de portas';
	END IF;
	IF NEW.quantportas < 1 THEN
		RAISE EXCEPTION 'Quantidade inválida de portas';
	END IF;
	IF NOT NEW.quantportas = OLD.quantportas THEN
		RAISE EXCEPTION 'A quantidade de portas não pode ser alterada';
	END IF;
        RETURN NEW;
END;$$;


ALTER FUNCTION public.trg_portas_panel() OWNER TO postgres;

SET search_path = backup, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: backup_estacao; Type: TABLE; Schema: backup; Owner: sati; Tablespace: 
--

CREATE TABLE backup_estacao (
    id bigint NOT NULL,
    detalhes character varying(255) NOT NULL
);


ALTER TABLE backup.backup_estacao OWNER TO sati;

--
-- Name: backup_fita; Type: TABLE; Schema: backup; Owner: sati; Tablespace: 
--

CREATE TABLE backup_fita (
    id bigint NOT NULL,
    tamanho integer DEFAULT 0 NOT NULL,
    tempo integer DEFAULT 1 NOT NULL
);


ALTER TABLE backup.backup_fita OWNER TO sati;

--
-- Name: backup_fita_fita_dados; Type: TABLE; Schema: backup; Owner: sati; Tablespace: 
--

CREATE TABLE backup_fita_fita_dados (
    backup_fita_id bigint NOT NULL,
    fita_dados_id bigint NOT NULL
);


ALTER TABLE backup.backup_fita_fita_dados OWNER TO sati;

--
-- Name: TABLE backup_fita_fita_dados; Type: COMMENT; Schema: backup; Owner: sati
--

COMMENT ON TABLE backup_fita_fita_dados IS 'Many to many dos backups em fita com as fitas utilizadas';


--
-- Name: categoria_fita_dados; Type: TABLE; Schema: backup; Owner: sati; Tablespace: 
--

CREATE TABLE categoria_fita_dados (
    id integer NOT NULL,
    nome character varying(10) NOT NULL
);


ALTER TABLE backup.categoria_fita_dados OWNER TO sati;

--
-- Name: conjunto_fita; Type: TABLE; Schema: backup; Owner: sati; Tablespace: 
--

CREATE TABLE conjunto_fita (
    id bigint NOT NULL,
    quant_fitas integer,
    designacao_fita_id bigint,
    municipio_id integer
);


ALTER TABLE backup.conjunto_fita OWNER TO sati;

--
-- Name: conjunto_fitas; Type: TABLE; Schema: backup; Owner: sati; Tablespace: 
--

CREATE TABLE conjunto_fitas (
    id integer NOT NULL,
    quant_fitas integer NOT NULL,
    designacao_fita_id integer NOT NULL,
    municipio_id integer NOT NULL
);


ALTER TABLE backup.conjunto_fitas OWNER TO sati;

--
-- Name: conjunto_fitas_id_seq; Type: SEQUENCE; Schema: backup; Owner: sati
--

CREATE SEQUENCE conjunto_fitas_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE backup.conjunto_fitas_id_seq OWNER TO sati;

--
-- Name: conjunto_fitas_id_seq; Type: SEQUENCE OWNED BY; Schema: backup; Owner: sati
--

ALTER SEQUENCE conjunto_fitas_id_seq OWNED BY conjunto_fitas.id;


--
-- Name: designacao_fita; Type: TABLE; Schema: backup; Owner: sati; Tablespace: 
--

CREATE TABLE designacao_fita (
    id integer NOT NULL,
    nome character(7) NOT NULL
);


ALTER TABLE backup.designacao_fita OWNER TO sati;

--
-- Name: falha_hardware; Type: TABLE; Schema: backup; Owner: sati; Tablespace: 
--

CREATE TABLE falha_hardware (
    id bigint NOT NULL,
    detalhes_falha character varying(255) NOT NULL
);


ALTER TABLE backup.falha_hardware OWNER TO sati;

--
-- Name: fita; Type: TABLE; Schema: backup; Owner: sati; Tablespace: 
--

CREATE TABLE fita (
    id bigint NOT NULL,
    marca_modelo character varying(50) NOT NULL,
    data_baixa date,
    dtype character(1) DEFAULT 'D'::bpchar NOT NULL,
    data_inicio_uso date,
    serie character(10) NOT NULL
);


ALTER TABLE backup.fita OWNER TO sati;

--
-- Name: fita_dados; Type: TABLE; Schema: backup; Owner: sati; Tablespace: 
--

CREATE TABLE fita_dados (
    id bigint NOT NULL,
    capacidade character varying(7) NOT NULL,
    categoria_id integer NOT NULL,
    conjunto_fitas_id integer,
    maximo_gravacoes integer DEFAULT 100 NOT NULL
);


ALTER TABLE backup.fita_dados OWNER TO sati;

--
-- Name: fita_id_seq; Type: SEQUENCE; Schema: backup; Owner: sati
--

CREATE SEQUENCE fita_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE backup.fita_id_seq OWNER TO sati;

--
-- Name: fita_id_seq; Type: SEQUENCE OWNED BY; Schema: backup; Owner: sati
--

ALTER SEQUENCE fita_id_seq OWNED BY fita.id;


--
-- Name: fita_limpeza; Type: TABLE; Schema: backup; Owner: sati; Tablespace: 
--

CREATE TABLE fita_limpeza (
    id bigint NOT NULL,
    maximo_limpezas integer NOT NULL,
    municipio_id integer NOT NULL
);


ALTER TABLE backup.fita_limpeza OWNER TO sati;

--
-- Name: limpeza; Type: TABLE; Schema: backup; Owner: sati; Tablespace: 
--

CREATE TABLE limpeza (
    id bigint NOT NULL,
    fita_limpeza_id bigint NOT NULL
);


ALTER TABLE backup.limpeza OWNER TO sati;

--
-- Name: TABLE limpeza; Type: COMMENT; Schema: backup; Owner: sati
--

COMMENT ON TABLE limpeza IS 'Limpeza do drive';


--
-- Name: local_backup_estacao; Type: TABLE; Schema: backup; Owner: sati; Tablespace: 
--

CREATE TABLE local_backup_estacao (
    id integer NOT NULL,
    diretorio character varying(255) NOT NULL,
    backup_estacao_id bigint NOT NULL,
    equipamento_id bigint NOT NULL
);


ALTER TABLE backup.local_backup_estacao OWNER TO sati;

--
-- Name: local_backup_estacao_id_seq; Type: SEQUENCE; Schema: backup; Owner: sati
--

CREATE SEQUENCE local_backup_estacao_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE backup.local_backup_estacao_id_seq OWNER TO sati;

--
-- Name: local_backup_estacao_id_seq; Type: SEQUENCE OWNED BY; Schema: backup; Owner: sati
--

ALTER SEQUENCE local_backup_estacao_id_seq OWNED BY local_backup_estacao.id;


--
-- Name: ocorrencia; Type: TABLE; Schema: backup; Owner: sati; Tablespace: 
--

CREATE TABLE ocorrencia (
    id bigint NOT NULL,
    data_ocorrencia date NOT NULL,
    observacao character varying(255),
    dtype character(1) DEFAULT 'B'::bpchar NOT NULL
);


ALTER TABLE backup.ocorrencia OWNER TO sati;

--
-- Name: ocorrencia_id_seq; Type: SEQUENCE; Schema: backup; Owner: sati
--

CREATE SEQUENCE ocorrencia_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE backup.ocorrencia_id_seq OWNER TO sati;

--
-- Name: ocorrencia_id_seq; Type: SEQUENCE OWNED BY; Schema: backup; Owner: sati
--

ALTER SEQUENCE ocorrencia_id_seq OWNED BY ocorrencia.id;


--
-- Name: outra_ocorrencia; Type: TABLE; Schema: backup; Owner: sati; Tablespace: 
--

CREATE TABLE outra_ocorrencia (
    id bigint NOT NULL,
    descricao character varying(255)
);


ALTER TABLE backup.outra_ocorrencia OWNER TO sati;

--
-- Name: teste_backup; Type: TABLE; Schema: backup; Owner: sati; Tablespace: 
--

CREATE TABLE teste_backup (
    id bigint NOT NULL,
    detalhes character varying(255) NOT NULL,
    sucesso boolean NOT NULL,
    tipo_teste_backup_id integer NOT NULL,
    backup_fita_id bigint NOT NULL
);


ALTER TABLE backup.teste_backup OWNER TO sati;

--
-- Name: tipo_teste_backup; Type: TABLE; Schema: backup; Owner: sati; Tablespace: 
--

CREATE TABLE tipo_teste_backup (
    id integer NOT NULL,
    nome character(8) NOT NULL
);


ALTER TABLE backup.tipo_teste_backup OWNER TO sati;

SET search_path = calendario, pg_catalog;

--
-- Name: excecao_feriado_nacional; Type: TABLE; Schema: calendario; Owner: sati; Tablespace: 
--

CREATE TABLE excecao_feriado_nacional (
    codigo bigint NOT NULL,
    desde date,
    ate date,
    cod_feriado integer NOT NULL,
    cod_municipio integer NOT NULL
);


ALTER TABLE calendario.excecao_feriado_nacional OWNER TO sati;

--
-- Name: excecao_feriado_nacional_codigo_seq; Type: SEQUENCE; Schema: calendario; Owner: sati
--

CREATE SEQUENCE excecao_feriado_nacional_codigo_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE calendario.excecao_feriado_nacional_codigo_seq OWNER TO sati;

--
-- Name: excecao_feriado_nacional_codigo_seq; Type: SEQUENCE OWNED BY; Schema: calendario; Owner: sati
--

ALTER SEQUENCE excecao_feriado_nacional_codigo_seq OWNED BY excecao_feriado_nacional.codigo;


--
-- Name: excecao_transferencia; Type: TABLE; Schema: calendario; Owner: sati; Tablespace: 
--

CREATE TABLE excecao_transferencia (
    codigo bigint NOT NULL,
    observacao character varying(255),
    cod_transferencia bigint NOT NULL,
    cod_municipio integer NOT NULL
);


ALTER TABLE calendario.excecao_transferencia OWNER TO sati;

--
-- Name: excecao_transferencia_codigo_seq; Type: SEQUENCE; Schema: calendario; Owner: sati
--

CREATE SEQUENCE excecao_transferencia_codigo_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE calendario.excecao_transferencia_codigo_seq OWNER TO sati;

--
-- Name: excecao_transferencia_codigo_seq; Type: SEQUENCE OWNED BY; Schema: calendario; Owner: sati
--

ALTER SEQUENCE excecao_transferencia_codigo_seq OWNED BY excecao_transferencia.codigo;


--
-- Name: feriado; Type: TABLE; Schema: calendario; Owner: sati; Tablespace: 
--

CREATE TABLE feriado (
    codigo integer NOT NULL,
    descricao character varying(150) NOT NULL,
    data_instituicao date,
    data_revogacao date,
    tipo character(1) NOT NULL,
    cod_municipio integer
);


ALTER TABLE calendario.feriado OWNER TO sati;

--
-- Name: feriado_codigo_seq; Type: SEQUENCE; Schema: calendario; Owner: sati
--

CREATE SEQUENCE feriado_codigo_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE calendario.feriado_codigo_seq OWNER TO sati;

--
-- Name: feriado_codigo_seq; Type: SEQUENCE OWNED BY; Schema: calendario; Owner: sati
--

ALTER SEQUENCE feriado_codigo_seq OWNED BY feriado.codigo;


--
-- Name: feriado_fixo; Type: TABLE; Schema: calendario; Owner: sati; Tablespace: 
--

CREATE TABLE feriado_fixo (
    codigo integer NOT NULL,
    dia integer NOT NULL,
    mes integer NOT NULL
);


ALTER TABLE calendario.feriado_fixo OWNER TO sati;

--
-- Name: feriado_movel; Type: TABLE; Schema: calendario; Owner: sati; Tablespace: 
--

CREATE TABLE feriado_movel (
    codigo integer NOT NULL,
    dias_pascoa integer NOT NULL
);


ALTER TABLE calendario.feriado_movel OWNER TO sati;

--
-- Name: transferencia; Type: TABLE; Schema: calendario; Owner: sati; Tablespace: 
--

CREATE TABLE transferencia (
    codigo bigint NOT NULL,
    cod_feriado integer NOT NULL,
    ano integer NOT NULL,
    novo_dia integer NOT NULL,
    novo_mes integer NOT NULL,
    legislacao character varying(255),
    cod_municipio integer
);


ALTER TABLE calendario.transferencia OWNER TO sati;

--
-- Name: transferencia_codigo_seq; Type: SEQUENCE; Schema: calendario; Owner: sati
--

CREATE SEQUENCE transferencia_codigo_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE calendario.transferencia_codigo_seq OWNER TO sati;

--
-- Name: transferencia_codigo_seq; Type: SEQUENCE OWNED BY; Schema: calendario; Owner: sati
--

ALTER SEQUENCE transferencia_codigo_seq OWNED BY transferencia.codigo;


SET search_path = certificacao, pg_catalog;

--
-- Name: certificado; Type: TABLE; Schema: certificacao; Owner: sati; Tablespace: 
--

CREATE TABLE certificado (
    id integer NOT NULL,
    data_gravacao date NOT NULL,
    data_validade date NOT NULL,
    id_usuario integer NOT NULL,
    id_status integer DEFAULT 1 NOT NULL,
    id_marca_etoken integer NOT NULL
);


ALTER TABLE certificacao.certificado OWNER TO sati;

--
-- Name: certificado_id_seq; Type: SEQUENCE; Schema: certificacao; Owner: sati
--

CREATE SEQUENCE certificado_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE certificacao.certificado_id_seq OWNER TO sati;

--
-- Name: certificado_id_seq; Type: SEQUENCE OWNED BY; Schema: certificacao; Owner: sati
--

ALTER SEQUENCE certificado_id_seq OWNED BY certificado.id;


--
-- Name: marca_etoken; Type: TABLE; Schema: certificacao; Owner: sati; Tablespace: 
--

CREATE TABLE marca_etoken (
    id integer NOT NULL,
    nome character varying(15)
);


ALTER TABLE certificacao.marca_etoken OWNER TO sati;

--
-- Name: status_certificado; Type: TABLE; Schema: certificacao; Owner: sati; Tablespace: 
--

CREATE TABLE status_certificado (
    id integer NOT NULL,
    descricao character varying(20) NOT NULL
);


ALTER TABLE certificacao.status_certificado OWNER TO sati;

SET search_path = equipamentos, pg_catalog;

--
-- Name: equipamento; Type: TABLE; Schema: equipamentos; Owner: sati; Tablespace: 
--

CREATE TABLE equipamento (
    codigo bigint NOT NULL,
    tombo character(6) NOT NULL,
    cod_lote integer NOT NULL,
    b_ativo boolean DEFAULT true NOT NULL,
    observacao character varying(255),
    localizacao character varying(255),
    nro_serie character varying(50)
);


ALTER TABLE equipamentos.equipamento OWNER TO sati;

--
-- Name: TABLE equipamento; Type: COMMENT; Schema: equipamentos; Owner: sati
--

COMMENT ON TABLE equipamento IS 'Equipamentos de informatica do TRT12';


--
-- Name: equipamento_codigo_seq; Type: SEQUENCE; Schema: equipamentos; Owner: sati
--

CREATE SEQUENCE equipamento_codigo_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE equipamentos.equipamento_codigo_seq OWNER TO sati;

--
-- Name: equipamento_codigo_seq; Type: SEQUENCE OWNED BY; Schema: equipamentos; Owner: sati
--

ALTER SEQUENCE equipamento_codigo_seq OWNED BY equipamento.codigo;


--
-- Name: equipamento_unidade; Type: TABLE; Schema: equipamentos; Owner: sati; Tablespace: 
--

CREATE TABLE equipamento_unidade (
    cod_equipamento bigint NOT NULL,
    cod_unidade integer NOT NULL
);


ALTER TABLE equipamentos.equipamento_unidade OWNER TO sati;

--
-- Name: TABLE equipamento_unidade; Type: COMMENT; Schema: equipamentos; Owner: sati
--

COMMENT ON TABLE equipamento_unidade IS 'Desacoplamento: join-table de equipamento com unidade. Dono é equipamento.';


--
-- Name: equipamento_usuario; Type: TABLE; Schema: equipamentos; Owner: postgres; Tablespace: 
--

CREATE TABLE equipamento_usuario (
    equipamento_cod bigint NOT NULL,
    usuario_id integer NOT NULL
);


ALTER TABLE equipamentos.equipamento_usuario OWNER TO postgres;

--
-- Name: historico; Type: TABLE; Schema: equipamentos; Owner: sati; Tablespace: 
--

CREATE TABLE historico (
    codigo bigint NOT NULL,
    data_historico date NOT NULL,
    descricao character varying(600) NOT NULL,
    observacao character varying(255),
    indicente_ritm character varying(10),
    cod_equipamento bigint NOT NULL
);


ALTER TABLE equipamentos.historico OWNER TO sati;

--
-- Name: TABLE historico; Type: COMMENT; Schema: equipamentos; Owner: sati
--

COMMENT ON TABLE historico IS 'Historico de fatos relevantes sobre o equipamento';


--
-- Name: historico_codigo_seq; Type: SEQUENCE; Schema: equipamentos; Owner: sati
--

CREATE SEQUENCE historico_codigo_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE equipamentos.historico_codigo_seq OWNER TO sati;

--
-- Name: historico_codigo_seq; Type: SEQUENCE OWNED BY; Schema: equipamentos; Owner: sati
--

ALTER SEQUENCE historico_codigo_seq OWNED BY historico.codigo;


--
-- Name: lote; Type: TABLE; Schema: equipamentos; Owner: sati; Tablespace: 
--

CREATE TABLE lote (
    codigo integer NOT NULL,
    nome character varying(100) NOT NULL,
    datafimgarantia date NOT NULL,
    cod_modelo integer NOT NULL
);


ALTER TABLE equipamentos.lote OWNER TO sati;

--
-- Name: TABLE lote; Type: COMMENT; Schema: equipamentos; Owner: sati
--

COMMENT ON TABLE lote IS 'Lotes de equipamentos de um determinado modelo adquiridos';


--
-- Name: lote_codigo_seq; Type: SEQUENCE; Schema: equipamentos; Owner: sati
--

CREATE SEQUENCE lote_codigo_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE equipamentos.lote_codigo_seq OWNER TO sati;

--
-- Name: lote_codigo_seq; Type: SEQUENCE OWNED BY; Schema: equipamentos; Owner: sati
--

ALTER SEQUENCE lote_codigo_seq OWNED BY lote.codigo;


--
-- Name: modelo; Type: TABLE; Schema: equipamentos; Owner: sati; Tablespace: 
--

CREATE TABLE modelo (
    codigo integer NOT NULL,
    nome character varying(150) NOT NULL,
    descricao character varying(256) NOT NULL,
    cod_tipo_equipamento integer DEFAULT 1 NOT NULL
);


ALTER TABLE equipamentos.modelo OWNER TO sati;

--
-- Name: TABLE modelo; Type: COMMENT; Schema: equipamentos; Owner: sati
--

COMMENT ON TABLE modelo IS 'Modelos de equipamentos de informatica do trt12';


--
-- Name: modelo_codigo_seq; Type: SEQUENCE; Schema: equipamentos; Owner: sati
--

CREATE SEQUENCE modelo_codigo_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE equipamentos.modelo_codigo_seq OWNER TO sati;

--
-- Name: modelo_codigo_seq; Type: SEQUENCE OWNED BY; Schema: equipamentos; Owner: sati
--

ALTER SEQUENCE modelo_codigo_seq OWNED BY modelo.codigo;


--
-- Name: tipo_equipamento; Type: TABLE; Schema: equipamentos; Owner: sati; Tablespace: 
--

CREATE TABLE tipo_equipamento (
    codigo integer NOT NULL,
    nome character varying(25) NOT NULL
);


ALTER TABLE equipamentos.tipo_equipamento OWNER TO sati;

--
-- Name: TABLE tipo_equipamento; Type: COMMENT; Schema: equipamentos; Owner: sati
--

COMMENT ON TABLE tipo_equipamento IS 'Tipos de equipamento (Microcomputador, Impressora/Multifuncional, Scanner, Roteador, Hub, Switch, No-break, Monitor)';


--
-- Name: tipo_equipamento_codigo_seq; Type: SEQUENCE; Schema: equipamentos; Owner: sati
--

CREATE SEQUENCE tipo_equipamento_codigo_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE equipamentos.tipo_equipamento_codigo_seq OWNER TO sati;

--
-- Name: tipo_equipamento_codigo_seq; Type: SEQUENCE OWNED BY; Schema: equipamentos; Owner: sati
--

ALTER SEQUENCE tipo_equipamento_codigo_seq OWNED BY tipo_equipamento.codigo;


SET search_path = public, pg_catalog;

--
-- Name: areati; Type: TABLE; Schema: public; Owner: sati; Tablespace: 
--

CREATE TABLE areati (
    codigo integer NOT NULL,
    nome character varying(15) NOT NULL,
    cod_municipiosede integer
);


ALTER TABLE public.areati OWNER TO sati;

--
-- Name: TABLE areati; Type: COMMENT; Schema: public; Owner: sati
--

COMMENT ON TABLE areati IS 'Relação de áreas de atuação dos técnicos de informática no TRT SC';


--
-- Name: areati_codigo_seq; Type: SEQUENCE; Schema: public; Owner: sati
--

CREATE SEQUENCE areati_codigo_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.areati_codigo_seq OWNER TO sati;

--
-- Name: areati_codigo_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: sati
--

ALTER SEQUENCE areati_codigo_seq OWNED BY areati.codigo;


--
-- Name: municipio; Type: TABLE; Schema: public; Owner: sati; Tablespace: 
--

CREATE TABLE municipio (
    codigo integer NOT NULL,
    nome character varying(100) NOT NULL,
    dtfundacaounidade date,
    observacao character varying(255),
    cod_areati integer NOT NULL
);


ALTER TABLE public.municipio OWNER TO sati;

--
-- Name: TABLE municipio; Type: COMMENT; Schema: public; Owner: sati
--

COMMENT ON TABLE municipio IS 'Relação dos Municípios de SC onde existem unidades judiciárias trabalhistas';


--
-- Name: municipio_codigo_seq; Type: SEQUENCE; Schema: public; Owner: sati
--

CREATE SEQUENCE municipio_codigo_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.municipio_codigo_seq OWNER TO sati;

--
-- Name: municipio_codigo_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: sati
--

ALTER SEQUENCE municipio_codigo_seq OWNED BY municipio.codigo;


--
-- Name: progint; Type: TABLE; Schema: public; Owner: sati; Tablespace: 
--

CREATE TABLE progint (
    codigo integer NOT NULL,
    nome character varying(100) NOT NULL,
    email character varying(60) NOT NULL,
    matricula character(10) NOT NULL,
    fonecontato character varying(20),
    observacao character varying(255),
    ativo boolean DEFAULT true NOT NULL,
    cod_areati integer NOT NULL,
    cod_unidade integer
);


ALTER TABLE public.progint OWNER TO sati;

--
-- Name: TABLE progint; Type: COMMENT; Schema: public; Owner: sati
--

COMMENT ON TABLE progint IS 'Relação de Técnicos de Informática do interior no TRT SC';


--
-- Name: progint_codigo_seq; Type: SEQUENCE; Schema: public; Owner: sati
--

CREATE SEQUENCE progint_codigo_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.progint_codigo_seq OWNER TO sati;

--
-- Name: progint_codigo_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: sati
--

ALTER SEQUENCE progint_codigo_seq OWNED BY progint.codigo;


--
-- Name: setor; Type: TABLE; Schema: public; Owner: sati; Tablespace: 
--

CREATE TABLE setor (
    codigo integer NOT NULL,
    nome character varying(255),
    sigla character varying(255)
);


ALTER TABLE public.setor OWNER TO sati;

--
-- Name: setor_codigo_seq; Type: SEQUENCE; Schema: public; Owner: sati
--

CREATE SEQUENCE setor_codigo_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.setor_codigo_seq OWNER TO sati;

--
-- Name: unidade; Type: TABLE; Schema: public; Owner: sati; Tablespace: 
--

CREATE TABLE unidade (
    codigo integer NOT NULL,
    nome character varying(50) NOT NULL,
    sigla character(10) NOT NULL,
    prefixo character(3) NOT NULL,
    localizacao character varying(255),
    observacao character varying(255),
    cod_municipio integer NOT NULL
);


ALTER TABLE public.unidade OWNER TO sati;

--
-- Name: TABLE unidade; Type: COMMENT; Schema: public; Owner: sati
--

COMMENT ON TABLE unidade IS 'Tabela de unidades judiciárias trabalhistas do Estado de SC';


--
-- Name: unidade_codigo_seq; Type: SEQUENCE; Schema: public; Owner: sati
--

CREATE SEQUENCE unidade_codigo_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.unidade_codigo_seq OWNER TO sati;

--
-- Name: unidade_codigo_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: sati
--

ALTER SEQUENCE unidade_codigo_seq OWNED BY unidade.codigo;


--
-- Name: usuario_final; Type: TABLE; Schema: public; Owner: sati; Tablespace: 
--

CREATE TABLE usuario_final (
    id integer NOT NULL,
    nome character varying(100) NOT NULL,
    matricula character(10),
    fonecontato character varying(20),
    observacao character varying(255),
    cod_unidade integer NOT NULL,
    ativo boolean DEFAULT true NOT NULL,
    email character varying(60)
);


ALTER TABLE public.usuario_final OWNER TO sati;

--
-- Name: usuario_final_id_seq; Type: SEQUENCE; Schema: public; Owner: sati
--

CREATE SEQUENCE usuario_final_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.usuario_final_id_seq OWNER TO sati;

--
-- Name: usuario_final_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: sati
--

ALTER SEQUENCE usuario_final_id_seq OWNED BY usuario_final.id;


SET search_path = redes, pg_catalog;

--
-- Name: categoriaelementorede; Type: TABLE; Schema: redes; Owner: sati; Tablespace: 
--

CREATE TABLE categoriaelementorede (
    codigo integer NOT NULL,
    nome character(15) NOT NULL
);


ALTER TABLE redes.categoriaelementorede OWNER TO sati;

--
-- Name: TABLE categoriaelementorede; Type: COMMENT; Schema: redes; Owner: sati
--

COMMENT ON TABLE categoriaelementorede IS 'Tabela de categorias de um elemento de rede: servidor, impressora ou estação';


--
-- Name: categoriaelementorede_codigo_seq; Type: SEQUENCE; Schema: redes; Owner: sati
--

CREATE SEQUENCE categoriaelementorede_codigo_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE redes.categoriaelementorede_codigo_seq OWNER TO sati;

--
-- Name: categoriaelementorede_codigo_seq; Type: SEQUENCE OWNED BY; Schema: redes; Owner: sati
--

ALTER SEQUENCE categoriaelementorede_codigo_seq OWNED BY categoriaelementorede.codigo;


--
-- Name: elementorede; Type: TABLE; Schema: redes; Owner: sati; Tablespace: 
--

CREATE TABLE elementorede (
    codigo bigint NOT NULL,
    nome character varying(10) NOT NULL,
    descricao character varying(40) NOT NULL,
    mac character(17) DEFAULT '00-00-00-00-00-00'::bpchar NOT NULL,
    tombo character(6),
    ip character(15),
    observacao character varying(255),
    cod_categoria integer NOT NULL,
    cod_unidade integer
);


ALTER TABLE redes.elementorede OWNER TO sati;

--
-- Name: TABLE elementorede; Type: COMMENT; Schema: redes; Owner: sati
--

COMMENT ON TABLE elementorede IS 'Elementos de uma rede ou sub-rede de informática do TRT SC';


--
-- Name: elementorede_codigo_seq; Type: SEQUENCE; Schema: redes; Owner: sati
--

CREATE SEQUENCE elementorede_codigo_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE redes.elementorede_codigo_seq OWNER TO sati;

--
-- Name: elementorede_codigo_seq; Type: SEQUENCE OWNED BY; Schema: redes; Owner: sati
--

ALTER SEQUENCE elementorede_codigo_seq OWNED BY elementorede.codigo;


--
-- Name: estacao; Type: TABLE; Schema: redes; Owner: sati; Tablespace: 
--

CREATE TABLE estacao (
)
INHERITS (elementorede);


ALTER TABLE redes.estacao OWNER TO sati;

--
-- Name: TABLE estacao; Type: COMMENT; Schema: redes; Owner: sati
--

COMMENT ON TABLE estacao IS 'Herda de elementorede - especifica elementos de rede que são estações';


--
-- Name: modulo; Type: TABLE; Schema: redes; Owner: sati; Tablespace: 
--

CREATE TABLE modulo (
    codigo bigint NOT NULL,
    identificacao character varying(10) NOT NULL,
    localizacao character varying(255) NOT NULL,
    descricao character varying(255),
    observacao character varying(255),
    cod_tipomodulo integer NOT NULL,
    cod_unidade integer NOT NULL
);


ALTER TABLE redes.modulo OWNER TO sati;

--
-- Name: modulotelematica_codigo_seq; Type: SEQUENCE; Schema: redes; Owner: sati
--

CREATE SEQUENCE modulotelematica_codigo_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE redes.modulotelematica_codigo_seq OWNER TO sati;

--
-- Name: modulotelematica_codigo_seq; Type: SEQUENCE OWNED BY; Schema: redes; Owner: sati
--

ALTER SEQUENCE modulotelematica_codigo_seq OWNED BY modulo.codigo;


--
-- Name: panel; Type: TABLE; Schema: redes; Owner: sati; Tablespace: 
--

CREATE TABLE panel (
    codigo integer NOT NULL,
    nome character(15) NOT NULL,
    quantportas integer NOT NULL,
    descricao character varying(255),
    observacao character varying(255),
    cod_rack integer NOT NULL
);


ALTER TABLE redes.panel OWNER TO sati;

--
-- Name: panel_codigo_seq; Type: SEQUENCE; Schema: redes; Owner: sati
--

CREATE SEQUENCE panel_codigo_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE redes.panel_codigo_seq OWNER TO sati;

--
-- Name: panel_codigo_seq; Type: SEQUENCE OWNED BY; Schema: redes; Owner: sati
--

ALTER SEQUENCE panel_codigo_seq OWNED BY panel.codigo;


--
-- Name: rack; Type: TABLE; Schema: redes; Owner: sati; Tablespace: 
--

CREATE TABLE rack (
    codigo integer NOT NULL,
    identificacao character varying(50) NOT NULL,
    localizacao character varying(255) NOT NULL,
    descricao character varying(255),
    observacao character varying(255),
    cod_municipio integer NOT NULL
);


ALTER TABLE redes.rack OWNER TO sati;

--
-- Name: rack_codigo_seq; Type: SEQUENCE; Schema: redes; Owner: sati
--

CREATE SEQUENCE rack_codigo_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE redes.rack_codigo_seq OWNER TO sati;

--
-- Name: rack_codigo_seq; Type: SEQUENCE OWNED BY; Schema: redes; Owner: sati
--

ALTER SEQUENCE rack_codigo_seq OWNED BY rack.codigo;


--
-- Name: rangeip; Type: TABLE; Schema: redes; Owner: sati; Tablespace: 
--

CREATE TABLE rangeip (
    codigo bigint NOT NULL,
    inferior character(15) NOT NULL,
    superior character(15) NOT NULL,
    observacao character varying(255),
    cod_rede integer NOT NULL,
    cod_categoria integer NOT NULL
);


ALTER TABLE redes.rangeip OWNER TO sati;

--
-- Name: TABLE rangeip; Type: COMMENT; Schema: redes; Owner: sati
--

COMMENT ON TABLE rangeip IS 'Relação de ranges de IP de uma sub-rede para as diferentes categorias';


--
-- Name: rangeip_codigo_seq; Type: SEQUENCE; Schema: redes; Owner: sati
--

CREATE SEQUENCE rangeip_codigo_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE redes.rangeip_codigo_seq OWNER TO sati;

--
-- Name: rangeip_codigo_seq; Type: SEQUENCE OWNED BY; Schema: redes; Owner: sati
--

ALTER SEQUENCE rangeip_codigo_seq OWNED BY rangeip.codigo;


--
-- Name: rede; Type: TABLE; Schema: redes; Owner: sati; Tablespace: 
--

CREATE TABLE rede (
    codigo integer NOT NULL,
    nome character varying(15) NOT NULL,
    endereco character varying(20) NOT NULL,
    loopback character varying(20),
    peprincipal character(15),
    ceprincipal character varying(15),
    pebackup character varying(15),
    cebackup character varying(15),
    observacao character varying(255),
    nrocircuito character varying(25)
);


ALTER TABLE redes.rede OWNER TO sati;

--
-- Name: TABLE rede; Type: COMMENT; Schema: redes; Owner: sati
--

COMMENT ON TABLE rede IS 'Sub-rede da WAN do TRT de SC, que recebe ranges de IP próprios.';


--
-- Name: rede_codigo_seq; Type: SEQUENCE; Schema: redes; Owner: sati
--

CREATE SEQUENCE rede_codigo_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE redes.rede_codigo_seq OWNER TO sati;

--
-- Name: rede_codigo_seq; Type: SEQUENCE OWNED BY; Schema: redes; Owner: sati
--

ALTER SEQUENCE rede_codigo_seq OWNED BY rede.codigo;


--
-- Name: rede_municipio; Type: TABLE; Schema: redes; Owner: sati; Tablespace: 
--

CREATE TABLE rede_municipio (
    cod_rede integer NOT NULL,
    cod_municipio integer NOT NULL
);


ALTER TABLE redes.rede_municipio OWNER TO sati;

--
-- Name: TABLE rede_municipio; Type: COMMENT; Schema: redes; Owner: sati
--

COMMENT ON TABLE rede_municipio IS 'JOIN: Município ao qual a rede pertence.';


--
-- Name: segmento; Type: TABLE; Schema: redes; Owner: sati; Tablespace: 
--

CREATE TABLE segmento (
    codigo bigint NOT NULL,
    extensao integer,
    observacao character varying(255),
    descricao character varying(255),
    nome character varying(10) NOT NULL
);


ALTER TABLE redes.segmento OWNER TO sati;

--
-- Name: segmento_codigo_seq; Type: SEQUENCE; Schema: redes; Owner: sati
--

CREATE SEQUENCE segmento_codigo_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE redes.segmento_codigo_seq OWNER TO sati;

--
-- Name: segmento_codigo_seq; Type: SEQUENCE OWNED BY; Schema: redes; Owner: sati
--

ALTER SEQUENCE segmento_codigo_seq OWNED BY segmento.codigo;


--
-- Name: servidor; Type: TABLE; Schema: redes; Owner: sati; Tablespace: 
--

CREATE TABLE servidor (
    cod_categoria integer DEFAULT 1
)
INHERITS (elementorede);


ALTER TABLE redes.servidor OWNER TO sati;

--
-- Name: TABLE servidor; Type: COMMENT; Schema: redes; Owner: sati
--

COMMENT ON TABLE servidor IS 'Herda de elementorede - especifica elementos de rede que são servidores';


--
-- Name: tipoconector; Type: TABLE; Schema: redes; Owner: sati; Tablespace: 
--

CREATE TABLE tipoconector (
    codigo integer NOT NULL,
    nome character varying(10) NOT NULL
);


ALTER TABLE redes.tipoconector OWNER TO sati;

--
-- Name: tipoconector_codigo_seq; Type: SEQUENCE; Schema: redes; Owner: sati
--

CREATE SEQUENCE tipoconector_codigo_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE redes.tipoconector_codigo_seq OWNER TO sati;

--
-- Name: tipoconector_codigo_seq; Type: SEQUENCE OWNED BY; Schema: redes; Owner: sati
--

ALTER SEQUENCE tipoconector_codigo_seq OWNED BY tipoconector.codigo;


--
-- Name: tipomodulo; Type: TABLE; Schema: redes; Owner: sati; Tablespace: 
--

CREATE TABLE tipomodulo (
    codigo integer NOT NULL,
    nome character varying(25) NOT NULL
);


ALTER TABLE redes.tipomodulo OWNER TO sati;

--
-- Name: tipomodulo_codigo_seq; Type: SEQUENCE; Schema: redes; Owner: sati
--

CREATE SEQUENCE tipomodulo_codigo_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE redes.tipomodulo_codigo_seq OWNER TO sati;

--
-- Name: tipomodulo_codigo_seq; Type: SEQUENCE OWNED BY; Schema: redes; Owner: sati
--

ALTER SEQUENCE tipomodulo_codigo_seq OWNED BY tipomodulo.codigo;


--
-- Name: tomada; Type: TABLE; Schema: redes; Owner: sati; Tablespace: 
--

CREATE TABLE tomada (
    codigo bigint NOT NULL,
    nome character varying(10) NOT NULL,
    descricao character varying(255),
    observacao character varying(255),
    cod_segmento bigint,
    tipo character(1) DEFAULT 'R'::bpchar,
    CONSTRAINT chktipo_tomada CHECK ((tipo = ANY (ARRAY['R'::bpchar, 'P'::bpchar, 'V'::bpchar])))
);


ALTER TABLE redes.tomada OWNER TO sati;

--
-- Name: tomada_codigo_seq; Type: SEQUENCE; Schema: redes; Owner: sati
--

CREATE SEQUENCE tomada_codigo_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE redes.tomada_codigo_seq OWNER TO sati;

--
-- Name: tomada_codigo_seq; Type: SEQUENCE OWNED BY; Schema: redes; Owner: sati
--

ALTER SEQUENCE tomada_codigo_seq OWNED BY tomada.codigo;


--
-- Name: tomadapanel; Type: TABLE; Schema: redes; Owner: sati; Tablespace: 
--

CREATE TABLE tomadapanel (
    codigo bigint NOT NULL,
    cod_panel bigint NOT NULL,
    ramal character(8)
);


ALTER TABLE redes.tomadapanel OWNER TO sati;

--
-- Name: tomadaremota; Type: TABLE; Schema: redes; Owner: sati; Tablespace: 
--

CREATE TABLE tomadaremota (
    codigo bigint NOT NULL,
    cod_tipoconector integer DEFAULT 1 NOT NULL,
    cod_modulo bigint NOT NULL
);


ALTER TABLE redes.tomadaremota OWNER TO sati;

SET search_path = viagem, pg_catalog;

SET default_with_oids = true;

--
-- Name: eventoreqviagem; Type: TABLE; Schema: viagem; Owner: sati; Tablespace: 
--

CREATE TABLE eventoreqviagem (
    codigo bigint NOT NULL,
    dataevento date NOT NULL,
    cod_tipoeventoreqviagem integer NOT NULL,
    cod_viagem bigint NOT NULL,
    observacao character varying(255)
);


ALTER TABLE viagem.eventoreqviagem OWNER TO sati;

--
-- Name: eventoreqviagem_codigo_seq; Type: SEQUENCE; Schema: viagem; Owner: sati
--

CREATE SEQUENCE eventoreqviagem_codigo_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE viagem.eventoreqviagem_codigo_seq OWNER TO sati;

--
-- Name: eventoreqviagem_codigo_seq; Type: SEQUENCE OWNED BY; Schema: viagem; Owner: sati
--

ALTER SEQUENCE eventoreqviagem_codigo_seq OWNED BY eventoreqviagem.codigo;


SET default_with_oids = false;

--
-- Name: tarefa; Type: TABLE; Schema: viagem; Owner: sati; Tablespace: 
--

CREATE TABLE tarefa (
    codigo bigint NOT NULL,
    tomboequip character(6),
    descricao character varying(255) NOT NULL,
    nrotarefaritm character(9),
    cod_viagem bigint NOT NULL
);


ALTER TABLE viagem.tarefa OWNER TO sati;

--
-- Name: TABLE tarefa; Type: COMMENT; Schema: viagem; Owner: sati
--

COMMENT ON TABLE tarefa IS 'Relação de tarefas executadas ou a serem executadas em uma viagem';


--
-- Name: tarefa_codigo_seq; Type: SEQUENCE; Schema: viagem; Owner: sati
--

CREATE SEQUENCE tarefa_codigo_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE viagem.tarefa_codigo_seq OWNER TO sati;

--
-- Name: tarefa_codigo_seq; Type: SEQUENCE OWNED BY; Schema: viagem; Owner: sati
--

ALTER SEQUENCE tarefa_codigo_seq OWNED BY tarefa.codigo;


--
-- Name: tipoeventoreqviagem; Type: TABLE; Schema: viagem; Owner: sati; Tablespace: 
--

CREATE TABLE tipoeventoreqviagem (
    codigo integer NOT NULL,
    descricao character(12) NOT NULL
);


ALTER TABLE viagem.tipoeventoreqviagem OWNER TO sati;

--
-- Name: viagem; Type: TABLE; Schema: viagem; Owner: sati; Tablespace: 
--

CREATE TABLE viagem (
    codigo bigint NOT NULL,
    dataprogramada date NOT NULL,
    dataviagem date,
    cod_progint integer NOT NULL,
    cod_municipio_origem integer NOT NULL,
    cod_municipio_destino integer NOT NULL
);


ALTER TABLE viagem.viagem OWNER TO sati;

--
-- Name: TABLE viagem; Type: COMMENT; Schema: viagem; Owner: sati
--

COMMENT ON TABLE viagem IS 'Relação de viagens efetuadas pelos progints';


--
-- Name: viagem_codigo_seq; Type: SEQUENCE; Schema: viagem; Owner: sati
--

CREATE SEQUENCE viagem_codigo_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE viagem.viagem_codigo_seq OWNER TO sati;

--
-- Name: viagem_codigo_seq; Type: SEQUENCE OWNED BY; Schema: viagem; Owner: sati
--

ALTER SEQUENCE viagem_codigo_seq OWNED BY viagem.codigo;


SET search_path = backup, pg_catalog;

--
-- Name: id; Type: DEFAULT; Schema: backup; Owner: sati
--

ALTER TABLE ONLY conjunto_fitas ALTER COLUMN id SET DEFAULT nextval('conjunto_fitas_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: backup; Owner: sati
--

ALTER TABLE ONLY fita ALTER COLUMN id SET DEFAULT nextval('fita_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: backup; Owner: sati
--

ALTER TABLE ONLY local_backup_estacao ALTER COLUMN id SET DEFAULT nextval('local_backup_estacao_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: backup; Owner: sati
--

ALTER TABLE ONLY ocorrencia ALTER COLUMN id SET DEFAULT nextval('ocorrencia_id_seq'::regclass);


SET search_path = calendario, pg_catalog;

--
-- Name: codigo; Type: DEFAULT; Schema: calendario; Owner: sati
--

ALTER TABLE ONLY excecao_feriado_nacional ALTER COLUMN codigo SET DEFAULT nextval('excecao_feriado_nacional_codigo_seq'::regclass);


--
-- Name: codigo; Type: DEFAULT; Schema: calendario; Owner: sati
--

ALTER TABLE ONLY excecao_transferencia ALTER COLUMN codigo SET DEFAULT nextval('excecao_transferencia_codigo_seq'::regclass);


--
-- Name: codigo; Type: DEFAULT; Schema: calendario; Owner: sati
--

ALTER TABLE ONLY feriado ALTER COLUMN codigo SET DEFAULT nextval('feriado_codigo_seq'::regclass);


--
-- Name: codigo; Type: DEFAULT; Schema: calendario; Owner: sati
--

ALTER TABLE ONLY transferencia ALTER COLUMN codigo SET DEFAULT nextval('transferencia_codigo_seq'::regclass);


SET search_path = certificacao, pg_catalog;

--
-- Name: id; Type: DEFAULT; Schema: certificacao; Owner: sati
--

ALTER TABLE ONLY certificado ALTER COLUMN id SET DEFAULT nextval('certificado_id_seq'::regclass);


SET search_path = equipamentos, pg_catalog;

--
-- Name: codigo; Type: DEFAULT; Schema: equipamentos; Owner: sati
--

ALTER TABLE ONLY equipamento ALTER COLUMN codigo SET DEFAULT nextval('equipamento_codigo_seq'::regclass);


--
-- Name: codigo; Type: DEFAULT; Schema: equipamentos; Owner: sati
--

ALTER TABLE ONLY historico ALTER COLUMN codigo SET DEFAULT nextval('historico_codigo_seq'::regclass);


--
-- Name: codigo; Type: DEFAULT; Schema: equipamentos; Owner: sati
--

ALTER TABLE ONLY lote ALTER COLUMN codigo SET DEFAULT nextval('lote_codigo_seq'::regclass);


--
-- Name: codigo; Type: DEFAULT; Schema: equipamentos; Owner: sati
--

ALTER TABLE ONLY modelo ALTER COLUMN codigo SET DEFAULT nextval('modelo_codigo_seq'::regclass);


--
-- Name: codigo; Type: DEFAULT; Schema: equipamentos; Owner: sati
--

ALTER TABLE ONLY tipo_equipamento ALTER COLUMN codigo SET DEFAULT nextval('tipo_equipamento_codigo_seq'::regclass);


SET search_path = public, pg_catalog;

--
-- Name: codigo; Type: DEFAULT; Schema: public; Owner: sati
--

ALTER TABLE ONLY areati ALTER COLUMN codigo SET DEFAULT nextval('areati_codigo_seq'::regclass);


--
-- Name: codigo; Type: DEFAULT; Schema: public; Owner: sati
--

ALTER TABLE ONLY municipio ALTER COLUMN codigo SET DEFAULT nextval('municipio_codigo_seq'::regclass);


--
-- Name: codigo; Type: DEFAULT; Schema: public; Owner: sati
--

ALTER TABLE ONLY progint ALTER COLUMN codigo SET DEFAULT nextval('progint_codigo_seq'::regclass);


--
-- Name: codigo; Type: DEFAULT; Schema: public; Owner: sati
--

ALTER TABLE ONLY unidade ALTER COLUMN codigo SET DEFAULT nextval('unidade_codigo_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: sati
--

ALTER TABLE ONLY usuario_final ALTER COLUMN id SET DEFAULT nextval('usuario_final_id_seq'::regclass);


SET search_path = redes, pg_catalog;

--
-- Name: codigo; Type: DEFAULT; Schema: redes; Owner: sati
--

ALTER TABLE ONLY categoriaelementorede ALTER COLUMN codigo SET DEFAULT nextval('categoriaelementorede_codigo_seq'::regclass);


--
-- Name: codigo; Type: DEFAULT; Schema: redes; Owner: sati
--

ALTER TABLE ONLY elementorede ALTER COLUMN codigo SET DEFAULT nextval('elementorede_codigo_seq'::regclass);


--
-- Name: codigo; Type: DEFAULT; Schema: redes; Owner: sati
--

ALTER TABLE ONLY estacao ALTER COLUMN codigo SET DEFAULT nextval('elementorede_codigo_seq'::regclass);


--
-- Name: mac; Type: DEFAULT; Schema: redes; Owner: sati
--

ALTER TABLE ONLY estacao ALTER COLUMN mac SET DEFAULT '00-00-00-00-00-00'::bpchar;


--
-- Name: codigo; Type: DEFAULT; Schema: redes; Owner: sati
--

ALTER TABLE ONLY modulo ALTER COLUMN codigo SET DEFAULT nextval('modulotelematica_codigo_seq'::regclass);


--
-- Name: codigo; Type: DEFAULT; Schema: redes; Owner: sati
--

ALTER TABLE ONLY panel ALTER COLUMN codigo SET DEFAULT nextval('panel_codigo_seq'::regclass);


--
-- Name: codigo; Type: DEFAULT; Schema: redes; Owner: sati
--

ALTER TABLE ONLY rack ALTER COLUMN codigo SET DEFAULT nextval('rack_codigo_seq'::regclass);


--
-- Name: codigo; Type: DEFAULT; Schema: redes; Owner: sati
--

ALTER TABLE ONLY rangeip ALTER COLUMN codigo SET DEFAULT nextval('rangeip_codigo_seq'::regclass);


--
-- Name: codigo; Type: DEFAULT; Schema: redes; Owner: sati
--

ALTER TABLE ONLY rede ALTER COLUMN codigo SET DEFAULT nextval('rede_codigo_seq'::regclass);


--
-- Name: codigo; Type: DEFAULT; Schema: redes; Owner: sati
--

ALTER TABLE ONLY segmento ALTER COLUMN codigo SET DEFAULT nextval('segmento_codigo_seq'::regclass);


--
-- Name: codigo; Type: DEFAULT; Schema: redes; Owner: sati
--

ALTER TABLE ONLY servidor ALTER COLUMN codigo SET DEFAULT nextval('elementorede_codigo_seq'::regclass);


--
-- Name: mac; Type: DEFAULT; Schema: redes; Owner: sati
--

ALTER TABLE ONLY servidor ALTER COLUMN mac SET DEFAULT '00-00-00-00-00-00'::bpchar;


--
-- Name: codigo; Type: DEFAULT; Schema: redes; Owner: sati
--

ALTER TABLE ONLY tipoconector ALTER COLUMN codigo SET DEFAULT nextval('tipoconector_codigo_seq'::regclass);


--
-- Name: codigo; Type: DEFAULT; Schema: redes; Owner: sati
--

ALTER TABLE ONLY tipomodulo ALTER COLUMN codigo SET DEFAULT nextval('tipomodulo_codigo_seq'::regclass);


--
-- Name: codigo; Type: DEFAULT; Schema: redes; Owner: sati
--

ALTER TABLE ONLY tomada ALTER COLUMN codigo SET DEFAULT nextval('tomada_codigo_seq'::regclass);


SET search_path = viagem, pg_catalog;

--
-- Name: codigo; Type: DEFAULT; Schema: viagem; Owner: sati
--

ALTER TABLE ONLY eventoreqviagem ALTER COLUMN codigo SET DEFAULT nextval('eventoreqviagem_codigo_seq'::regclass);


--
-- Name: codigo; Type: DEFAULT; Schema: viagem; Owner: sati
--

ALTER TABLE ONLY tarefa ALTER COLUMN codigo SET DEFAULT nextval('tarefa_codigo_seq'::regclass);


--
-- Name: codigo; Type: DEFAULT; Schema: viagem; Owner: sati
--

ALTER TABLE ONLY viagem ALTER COLUMN codigo SET DEFAULT nextval('viagem_codigo_seq'::regclass);


SET search_path = backup, pg_catalog;

--
-- Data for Name: backup_estacao; Type: TABLE DATA; Schema: backup; Owner: sati
--

COPY backup_estacao (id, detalhes) FROM stdin;
\.


--
-- Data for Name: backup_fita; Type: TABLE DATA; Schema: backup; Owner: sati
--

COPY backup_fita (id, tamanho, tempo) FROM stdin;
\.


--
-- Data for Name: backup_fita_fita_dados; Type: TABLE DATA; Schema: backup; Owner: sati
--

COPY backup_fita_fita_dados (backup_fita_id, fita_dados_id) FROM stdin;
\.


--
-- Data for Name: categoria_fita_dados; Type: TABLE DATA; Schema: backup; Owner: sati
--

COPY categoria_fita_dados (id, nome) FROM stdin;
0	DDS-1
1	DDS-2
2	DDS-3
3	DDS-4
4	DAT72
5	DAT160
6	DAT320
\.


--
-- Data for Name: conjunto_fita; Type: TABLE DATA; Schema: backup; Owner: sati
--

COPY conjunto_fita (id, quant_fitas, designacao_fita_id, municipio_id) FROM stdin;
\.


--
-- Data for Name: conjunto_fitas; Type: TABLE DATA; Schema: backup; Owner: sati
--

COPY conjunto_fitas (id, quant_fitas, designacao_fita_id, municipio_id) FROM stdin;
\.


--
-- Name: conjunto_fitas_id_seq; Type: SEQUENCE SET; Schema: backup; Owner: sati
--

SELECT pg_catalog.setval('conjunto_fitas_id_seq', 14, true);


--
-- Data for Name: designacao_fita; Type: TABLE DATA; Schema: backup; Owner: sati
--

COPY designacao_fita (id, nome) FROM stdin;
0	SEGUNDA
1	TERCA  
2	QUARTA 
3	QUINTA 
4	SEXTA1 
5	SEXTA2 
6	SEXTA3 
7	MES1   
8	MES2   
\.


--
-- Data for Name: falha_hardware; Type: TABLE DATA; Schema: backup; Owner: sati
--

COPY falha_hardware (id, detalhes_falha) FROM stdin;
\.


--
-- Data for Name: fita; Type: TABLE DATA; Schema: backup; Owner: sati
--

COPY fita (id, marca_modelo, data_baixa, dtype, data_inicio_uso, serie) FROM stdin;
\.


--
-- Data for Name: fita_dados; Type: TABLE DATA; Schema: backup; Owner: sati
--

COPY fita_dados (id, capacidade, categoria_id, conjunto_fitas_id, maximo_gravacoes) FROM stdin;
\.


--
-- Name: fita_id_seq; Type: SEQUENCE SET; Schema: backup; Owner: sati
--

SELECT pg_catalog.setval('fita_id_seq', 14, true);


--
-- Data for Name: fita_limpeza; Type: TABLE DATA; Schema: backup; Owner: sati
--

COPY fita_limpeza (id, maximo_limpezas, municipio_id) FROM stdin;
\.


--
-- Data for Name: limpeza; Type: TABLE DATA; Schema: backup; Owner: sati
--

COPY limpeza (id, fita_limpeza_id) FROM stdin;
\.


--
-- Data for Name: local_backup_estacao; Type: TABLE DATA; Schema: backup; Owner: sati
--

COPY local_backup_estacao (id, diretorio, backup_estacao_id, equipamento_id) FROM stdin;
\.


--
-- Name: local_backup_estacao_id_seq; Type: SEQUENCE SET; Schema: backup; Owner: sati
--

SELECT pg_catalog.setval('local_backup_estacao_id_seq', 14, true);


--
-- Data for Name: ocorrencia; Type: TABLE DATA; Schema: backup; Owner: sati
--

COPY ocorrencia (id, data_ocorrencia, observacao, dtype) FROM stdin;
\.


--
-- Name: ocorrencia_id_seq; Type: SEQUENCE SET; Schema: backup; Owner: sati
--

SELECT pg_catalog.setval('ocorrencia_id_seq', 14, true);


--
-- Data for Name: outra_ocorrencia; Type: TABLE DATA; Schema: backup; Owner: sati
--

COPY outra_ocorrencia (id, descricao) FROM stdin;
\.


--
-- Data for Name: teste_backup; Type: TABLE DATA; Schema: backup; Owner: sati
--

COPY teste_backup (id, detalhes, sucesso, tipo_teste_backup_id, backup_fita_id) FROM stdin;
\.


--
-- Data for Name: tipo_teste_backup; Type: TABLE DATA; Schema: backup; Owner: sati
--

COPY tipo_teste_backup (id, nome) FROM stdin;
0	Listagem
1	Restore 
\.


SET search_path = calendario, pg_catalog;

--
-- Data for Name: excecao_feriado_nacional; Type: TABLE DATA; Schema: calendario; Owner: sati
--

COPY excecao_feriado_nacional (codigo, desde, ate, cod_feriado, cod_municipio) FROM stdin;
\.


--
-- Name: excecao_feriado_nacional_codigo_seq; Type: SEQUENCE SET; Schema: calendario; Owner: sati
--

SELECT pg_catalog.setval('excecao_feriado_nacional_codigo_seq', 19, true);


--
-- Data for Name: excecao_transferencia; Type: TABLE DATA; Schema: calendario; Owner: sati
--

COPY excecao_transferencia (codigo, observacao, cod_transferencia, cod_municipio) FROM stdin;
\.


--
-- Name: excecao_transferencia_codigo_seq; Type: SEQUENCE SET; Schema: calendario; Owner: sati
--

SELECT pg_catalog.setval('excecao_transferencia_codigo_seq', 19, true);


--
-- Data for Name: feriado; Type: TABLE DATA; Schema: calendario; Owner: sati
--

COPY feriado (codigo, descricao, data_instituicao, data_revogacao, tipo, cod_municipio) FROM stdin;
1	Recesso Forense	\N	\N	F	\N
2	Recesso Forense	\N	\N	F	\N
3	Recesso Forense	\N	\N	F	\N
4	Recesso Forense	\N	\N	F	\N
5	Recesso Forense	\N	\N	F	\N
6	Recesso Forense	\N	\N	F	\N
7	Fundação da Cidade de São Miguel do Oeste	\N	\N	F	24
8	Dia do Município de Videira	2004-02-18	\N	F	27
9	Fundação da Cidade de Joinville	\N	\N	F	18
10	Fundação da Cidade de Caçador	\N	\N	F	5
12	Fundação da Cidade de Araranguá	\N	\N	F	1
13	Fundação da Cidade de Xanxerê	\N	\N	F	28
14	Fundação da Cidade de Rio do Sul	\N	\N	F	21
15	Tiradentes	\N	\N	F	\N
16	Aniversário da Cidade de Palhoça	\N	\N	F	29
17	Dia do Trabalhador	\N	\N	F	\N
18	Dia de Santa Cruz	2004-04-26	\N	F	6
19	Padroeira da Cidade de Araranguá	\N	\N	F	1
20	Fundação da Cidade de Curitibanos	\N	\N	F	10
21	Fundação da Cidade de Itajaí	\N	\N	F	15
22	Fundação da Cidade de Balneário Camboriú	\N	\N	F	2
23	Fundação da Cidade de Jaraguá do Sul	\N	\N	F	16
24	Fundação da Cidade de Concórdia	\N	\N	F	8
25	Fundação da Cidade de Brusque	\N	\N	F	4
26	Padroeiro da Cidade de Palhoça	\N	\N	F	29
27	Padroeiro da Cidade de Xanxerê	\N	\N	F	28
28	Dia do Advogado	\N	\N	F	\N
29	Padroeira da Cidade de Lages	\N	\N	F	19
30	Fundação da Cidade de Chapecó	\N	\N	F	7
31	Fundação da Cidade de Joaçaba	\N	\N	F	17
32	Fundação da Cidade de Blumenau	\N	\N	F	3
34	Independência do Brasil	\N	\N	F	\N
35	Fundação da Cidade de Mafra	\N	\N	F	20
36	Fundação da Cidade de Canoinhas	\N	\N	F	6
37	Padroeira da Cidade de Tubarão	\N	\N	F	26
38	Fundação da Cidade de São Bento do Sul	\N	\N	F	22
39	Padroeiro da Cidade de São Miguel do Oeste	\N	\N	F	24
41	Padroeira do Brasil	\N	\N	F	\N
42	Dia do Servidor Público	\N	\N	F	\N
43	Dia de Todos os Santos	\N	\N	F	\N
44	Finados	\N	\N	F	\N
45	Proclamação da República	\N	\N	F	\N
46	Padroeira da Cidade de Criciúma	\N	\N	F	9
47	Dia Internacional da Justiça	\N	\N	F	\N
48	Recesso Forense	\N	\N	F	\N
49	Recesso Forense	\N	\N	F	\N
50	Recesso Forense	\N	\N	F	\N
51	Recesso Forense	\N	\N	F	\N
52	Recesso Forense	\N	\N	F	\N
53	Natal	\N	\N	F	\N
54	Recesso Forense	\N	\N	F	\N
55	Recesso Forense	\N	\N	F	\N
56	Recesso Forense	\N	\N	F	\N
57	Recesso Forense	\N	\N	F	\N
58	Recesso Forense	\N	\N	F	\N
59	Recesso Forense	\N	\N	F	\N
67	Padroeiro da Cidade de São José	\N	\N	F	23
68	Fundação da Cidade de Indaial	\N	\N	F	14
69	Fundação da Cidade de Florianópolis	\N	\N	F	11
71	Fundação da Cidade de Criciúma	\N	\N	F	9
72	Fundação da Cidade de Fraiburgo	\N	\N	F	12
76	Padroeira da Cidade de Joaçaba	1999-03-05	1999-10-21	F	17
79	Suspensão prazos (mudança)	2008-06-23	2008-06-30	F	15
80	Suspensão prazos (mudança)	2008-06-23	2008-06-30	F	15
81	Suspensão prazos (mudança)	2008-06-23	2008-06-30	F	15
82	Suspensão prazos (mudança)	2008-06-23	2008-06-30	F	15
83	Suspensão prazos (mudança)	2008-06-23	2008-06-30	F	15
84	Suspensão prazos (mudança)	2008-06-23	2008-06-30	F	15
86	Padroeira da Cidade de Curitibanos	\N	\N	F	10
87	Padroeira da Cidade de Imbituba	\N	\N	F	13
88	Padroeira da Cidade de Videira	\N	\N	F	27
60	Páscoa	\N	\N	M	\N
61	Sexta-feira Santa	\N	\N	M	\N
62	Segunda-feira de Carnaval	\N	\N	M	\N
63	Carnaval	\N	\N	M	\N
64	Quarta-feira de Cinzas	\N	\N	M	\N
65	Corpus Christi	\N	\N	M	\N
66	Ascenção do Senhor	1973-06-29	2006-02-22	M	4
74	Aniversário da Cidade de Timbó	1980-08-11	\N	M	25
77	Endoenças	\N	\N	M	\N
78	Quarta-feira semana santa	\N	\N	M	\N
90	Padroeira da cidade de Navegantes	\N	\N	F	31
91	Aniversário do Município de Navegantes	\N	\N	F	31
109	Padroeira da Cidade de Fraiburgo	\N	\N	F	12
\.


--
-- Name: feriado_codigo_seq; Type: SEQUENCE SET; Schema: calendario; Owner: sati
--

SELECT pg_catalog.setval('feriado_codigo_seq', 109, true);


--
-- Data for Name: feriado_fixo; Type: TABLE DATA; Schema: calendario; Owner: sati
--

COPY feriado_fixo (codigo, dia, mes) FROM stdin;
1	1	1
2	2	1
3	3	1
4	4	1
5	5	1
6	6	1
7	15	2
8	1	3
9	9	3
10	25	3
12	3	4
13	27	2
14	15	4
15	21	4
16	24	4
17	1	5
18	3	5
19	4	5
20	11	6
21	15	6
22	20	7
23	25	7
24	29	7
25	4	8
26	6	8
27	6	8
28	11	8
29	15	8
30	25	8
31	25	8
32	2	9
34	7	9
35	8	9
36	12	9
37	15	9
38	23	9
39	29	9
41	12	10
42	28	10
43	1	11
44	2	11
45	15	11
46	4	12
47	8	12
48	20	12
49	21	12
50	22	12
51	23	12
52	24	12
53	25	12
54	26	12
55	27	12
56	28	12
57	29	12
58	30	12
59	31	12
67	19	3
68	21	3
69	23	3
71	6	1
72	31	12
76	10	10
79	23	6
80	24	6
81	25	6
82	26	6
83	27	6
84	30	6
86	8	12
87	8	12
88	8	12
90	2	2
91	26	8
109	8	12
\.


--
-- Data for Name: feriado_movel; Type: TABLE DATA; Schema: calendario; Owner: sati
--

COPY feriado_movel (codigo, dias_pascoa) FROM stdin;
61	-2
62	-48
63	-47
64	-46
65	60
66	39
74	1
77	-3
78	-4
60	0
\.


--
-- Data for Name: transferencia; Type: TABLE DATA; Schema: calendario; Owner: sati
--

COPY transferencia (codigo, cod_feriado, ano, novo_dia, novo_mes, legislacao, cod_municipio) FROM stdin;
1	13	2008	11	4	Lei ordinária 3013/07	\N
2	10	2002	22	3	Decreto 2480, de 29 de janeiro de 2002	\N
3	10	2003	24	3	Decreto 2695, de 30 de janeiro de 2003	\N
4	10	1999	26	3	Decreto 1907, de 18 de março de 1999	\N
6	14	2003	14	4	DECRETO Nº 36, de 03 de fevereiro de 2003.	\N
8	13	2006	24	3	Decreto AM 003/2006 de 05 de Janeiro de 2006	\N
9	8	2000	7	3	Decreto Municipal 6769/2000	\N
10	23	2000	24	7	Lei Municipal 2716/2000	\N
11	36	2000	8	9	Decreto Municipal 078/2000	\N
12	27	2002	5	8	Decreto AM 208/2002	\N
13	13	2003	3	3	Decreto AM 002/2003, de 28/01/2003	\N
14	27	2003	6	8	Decreto AM 174/2003	\N
15	13	2004	26	3	Decreto AM 004/2004, de 19/01/2004	\N
16	28	2004	6	9	Portaria GP 0325, de 15/07/2004	\N
17	47	2004	29	10	GP 0325, de 15/07/2004	\N
18	8	2005	28	2	Decreto 8.215/05, de 31 de Janeiro de 2005	\N
19	28	2005	27	5	Portaria GP 0624, de 24/11/2004	\N
20	42	2005	31	10	Portaria GP 0624, de 24/11/2004	\N
21	47	2005	14	11	Portaria GP 0624, de 24/11/2004	\N
22	43	2006	3	11	Portaria 0758, de 20/09/2005	\N
23	42	2009	30	10		\N
24	47	2009	7	12		\N
26	28	2010	13	8	PORTARIA Nº GP/029, de 03 de fevereiro de 2009.	\N
27	42	2010	11	10	PORTARIA Nº GP/059, de 19 de fevereiro de 2010	\N
28	47	2010	10	12	PORTARIA Nº GP/029, de 03 de fevereiro de 2009.	\N
29	31	2009	24	8	Decreto Municipal 3.408 de 06.08.09	\N
31	42	2011	31	10	Portaria GP 16/2010	\N
32	47	2011	19	12	Portaria GP 16/2010 (alterado pela Portaria 317/2010)	\N
33	10	2010	22	3	Decreto 4.418, de 20-1-2010, da Prefeitura Municipal de Caçador	\N
7	14	2008	14	4		\N
5	42	2008	31	10	Portaria GP/535 de 6 de agosto de 2007	\N
30	28	2011	12	8	Portaria GP 16/2010	\N
25	10	2009	27	3	Decreto 4.165, de 20-1-2009, da Prefeitura Municipal de Caçador	\N
36	42	2013	31	10	PORTARIA Nº GP 18, de 10 de fevereiro de 2012.	\N
38	13	2014	12	3	Lei nº 3.601, de 09 de dezembro de 2013	\N
39	28	2015	10	8	PORTARIA Nº GP 27, de 4 de fevereiro de 2014.	\N
40	42	2015	30	10	PORTARIA Nº GP 27, de 4 de fevereiro de 2014.	\N
41	47	2015	7	12		\N
42	10	2014	24	3	Decreto nº 5.887, de 23 de janeiro de 2014, do Município de Caçador	\N
37	42	2014	27	10	Portaria nº GP 30, de 5 de fevereiro de 2013	\N
43	28	2016	12	8	PORTARIA No GP 12, de 4 de fevereiro de 2015.	\N
44	47	2016	9	12	PORTARIA No GP 12, de 4 de fevereiro de 2015.	\N
45	42	2016	31	10		\N
\.


--
-- Name: transferencia_codigo_seq; Type: SEQUENCE SET; Schema: calendario; Owner: sati
--

SELECT pg_catalog.setval('transferencia_codigo_seq', 61, true);


SET search_path = certificacao, pg_catalog;

--
-- Data for Name: certificado; Type: TABLE DATA; Schema: certificacao; Owner: sati
--

COPY certificado (id, data_gravacao, data_validade, id_usuario, id_status, id_marca_etoken) FROM stdin;
3	2016-01-19	2019-01-18	7	2	4
4	2015-12-08	2018-12-07	5	2	4
5	2015-12-17	2018-12-16	5	2	4
1	2015-12-14	2018-12-13	56	1	4
7	2015-11-30	2018-11-29	30	1	4
8	2015-12-15	2018-12-14	35	2	3
9	2016-05-19	2019-05-19	35	3	3
11	2013-10-15	2016-10-14	25	2	4
13	2015-12-15	2018-12-14	39	2	4
15	2016-04-14	2019-04-14	28	2	4
19	2015-12-14	2018-12-13	9	1	4
21	2015-09-17	2018-09-16	31	3	2
22	2014-09-04	2017-09-03	36	2	4
23	2015-11-30	2018-11-29	33	2	4
26	2015-06-03	2018-06-02	42	2	3
27	2013-10-21	2016-10-20	42	2	4
31	2014-01-23	2017-01-22	49	2	3
32	2016-04-01	2019-04-01	43	2	2
39	2015-11-30	2018-11-29	45	2	3
42	2016-01-07	2019-07-06	11	2	4
45	2015-04-28	2018-04-27	19	2	3
47	2015-04-24	2018-04-23	22	2	2
48	2015-04-24	2018-04-23	15	2	3
49	2015-04-24	2018-04-23	14	2	3
43	2016-05-23	2019-05-23	23	3	3
58	2016-01-07	2019-01-06	8	2	3
60	2013-10-21	2016-10-20	70	1	4
61	2015-03-04	2018-03-03	65	1	1
62	2015-08-13	2018-08-12	61	1	2
63	2013-10-23	2016-10-22	61	1	4
64	2015-07-31	2018-07-30	67	1	4
65	2014-04-13	2017-04-12	69	1	4
66	2015-12-04	2018-12-03	75	2	4
67	2014-10-02	2017-10-01	64	1	3
68	2014-08-07	2017-08-06	71	1	3
69	2015-07-22	2018-07-21	73	1	3
70	2015-11-25	2018-11-24	74	1	4
71	2015-08-07	2018-08-06	66	1	2
72	2013-10-21	2016-10-20	68	1	4
73	2015-07-17	2018-07-16	72	1	3
57	2015-04-10	2018-04-09	12	2	3
12	2015-08-03	2018-08-02	32	3	1
17	2015-08-03	2018-08-02	29	3	1
74	2016-08-01	2019-08-01	29	2	4
75	2016-07-30	2019-07-30	50	3	2
76	2016-07-28	2019-07-28	145	2	4
54	2015-12-18	2018-12-17	4	2	4
46	2015-03-06	2018-03-05	13	3	2
50	2015-11-30	2018-11-29	18	2	3
53	2015-11-30	2018-11-29	57	3	4
44	2015-06-10	2018-06-09	20	2	3
51	2015-08-03	2018-08-02	21	3	2
59	2016-04-01	2019-04-01	16	3	4
52	2015-03-06	2018-03-05	16	2	1
24	2015-12-03	2018-12-02	37	2	4
20	2015-12-16	2018-12-15	38	2	4
14	2015-08-03	2018-08-02	34	3	1
25	2016-03-09	2019-03-09	27	2	4
18	2015-08-03	2018-08-02	26	3	1
56	2015-07-21	2018-07-20	40	2	3
16	2013-10-15	2016-10-14	40	2	4
77	2016-07-21	2019-07-21	40	2	4
10	2016-04-04	2019-04-04	25	2	4
38	2015-05-25	2018-05-24	47	3	2
41	2015-09-11	2018-09-10	44	2	4
34	2015-09-11	2018-09-10	51	2	3
36	2015-08-03	2018-08-02	46	3	2
30	2015-08-03	2018-08-02	48	3	2
35	2013-10-14	2016-10-13	55	2	4
28	2015-04-24	2018-04-23	54	2	3
33	2015-09-11	2018-09-10	53	2	3
29	2015-09-11	2018-09-10	52	2	3
40	2013-10-14	2016-10-13	41	2	4
37	2015-12-17	2018-12-16	41	2	4
6	2015-12-15	2018-12-14	10	2	4
55	2015-03-03	2018-03-02	1	3	1
\.


--
-- Name: certificado_id_seq; Type: SEQUENCE SET; Schema: certificacao; Owner: sati
--

SELECT pg_catalog.setval('certificado_id_seq', 77, true);


--
-- Data for Name: marca_etoken; Type: TABLE DATA; Schema: certificacao; Owner: sati
--

COPY marca_etoken (id, nome) FROM stdin;
1	Aladdin
2	Safenet
3	Morpho
4	G & D
\.


--
-- Data for Name: status_certificado; Type: TABLE DATA; Schema: certificacao; Owner: sati
--

COPY status_certificado (id, descricao) FROM stdin;
1	Desconhecido
2	Novo
3	Renovado
\.


SET search_path = equipamentos, pg_catalog;

--
-- Data for Name: equipamento; Type: TABLE DATA; Schema: equipamentos; Owner: sati
--

COPY equipamento (codigo, tombo, cod_lote, b_ativo, observacao, localizacao, nro_serie) FROM stdin;
134	71767 	11	f		Contadoria	4002184900026
23	48546 	1	f	\N	\N	\N
2	47526 	3	f	\N	\N	\N
3	47527 	3	f	\N	\N	\N
4	47528 	3	f	\N	\N	\N
5	47529 	3	f	\N	\N	\N
6	47530 	3	f	\N	\N	\N
7	47532 	3	f	\N	\N	\N
8	47533 	3	f	\N	\N	\N
9	47534 	3	f	\N	\N	\N
10	47535 	3	f	\N	\N	\N
11	47536 	3	f	\N	\N	\N
12	47537 	3	f	\N	\N	\N
13	47538 	3	f	\N	\N	\N
14	47540 	3	f	\N	\N	\N
15	47641 	4	f	\N	\N	\N
16	47665 	4	f	\N	\N	\N
17	47666 	4	f	\N	\N	\N
18	47667 	4	f	\N	\N	\N
19	47668 	4	f	\N	\N	\N
55	63002 	12	f			
46	58381 	6	f			
90	84461 	18	t		Assistentes dos juízes	1A7639G7V
91	84460 	18	t			1A7627018
21	53773 	2	f			
25	54603 	7	f			
47	58432 	6	f			
63	58939 	14	f			
28	59561 	5	f			
58	63088 	12	f			
33	63892 	9	f			
29	59562 	5	f			
32	63886 	9	f			
22	53774 	2	f			
26	54738 	8	f			
27	54839 	8	f			
30	59563 	5	f			
64	58419 	6	f			
36	63952 	9	f			
38	64000 	9	f			
39	64001 	9	f			
24	54602 	7	f		teste	
42	64050 	9	f			
20	53772 	2	f			
49	58434 	6	f			
44	64089 	9	f			K931401203901
93	84458 	18	t		Assistentes juiz	1A7626F05
94	84309 	18	t			1A7639B2R
97	78279 	19	t			L1C7V18
56	63048 	12	f		Balcão	
53	71599 	11	t	(Estagiários)		4002184900143
88	73897 	20	t		Secretaria	4002688600171
61	63140 	12	f			
43	64068 	9	f			
50	66781 	10	f			
87	73898 	20	f	Micro para os advogados acompanharem a audiência	Sala de audiências	4002739800062
57	63049 	12	t	Servidor Debian	CPD	
65	52403 	15	f		CDM	C5J137596
85	73900 	20	f		Balcão de auto-atendimento	4002688900301
84	73901 	20	t	Juiz de audiências	Sala de audiências	4002688900269
83	73903 	20	f		Balcão de auto-atendimento	4003010000002
77	73904 	20	t			4002688500013
68	62762 	17	f	Monitor do micro do TI	CPD	V631669
81	73907 	20	t	Partes - sala de audiência		4003010000076
80	73908 	20	t			4003010000117
79	73909 	20	f			4002614600142
89	73910 	20	t	(ocioso)		4002685200233
78	73912 	20	t			4003010000068
76	73913 	20	t			4003010000099
75	74032 	20	t			4002688500232
74	74033 	20	f		Balcão de auto-atendimento	4002614600153
73	74115 	20	t	(indefinido)		4002688100007
72	78371 	19	t	Assistente direção		L1C7VXN
71	84361 	18	t			1A7626Q5R
70	84362 	18	t	(indefinido)		1A7626Z92
69	84615 	18	t	Contadoria		1A7639C3Z
60	63107 	12	t	Partes - sala de audiência		L1AC3ZR
45	64139 	9	f			K931401204035
52	71586 	11	t			4002184900120
101	73817 	20	t	(indefinido)		4002688500244
100	74025 	20	t	(indefinido)		4003010000077
99	78237 	19	t		Sala da contadoria/oficiais de justiça	L1C7VAN
98	78278 	19	t		Balcão	L1C7V8W
59	63105 	12	t	Partes - sala de audiência	Secretaria	L1AC3VT
41	64013 	9	f			K931401201141
86	73899 	20	t	(indefinido)	.	4002688900283
95	84308 	18	t	(indefinido)		1A763B240
92	84459 	18	t			1A7626G62
82	73905 	20	t	Ofiiciais de Justiça	CDM	4002688900084
112	76729 	21	t	Sala de audiências	Sala de audiências	Z7FRBDABB02899A
48	58433 	6	f			
113	71770 	11	f	Uso da assistente	Gabinete juiz titular	4002184900195
117	89039 	22	t		Gabinete juiz titular	1A964T380
126	73994 	20	t		Secretaria (canto da janela)	4002688900048
135	71768 	11	t		Secretaria (próximo à janela)	4002184900023
139	73984 	20	t		Gabinete juiz substituto	4002688100135
140	84436 	18	t		Diretor	1A763984S
148	73864 	20	t		Secretaria (centro)	4002688900120
149	73972 	20	t		Balcão	4002611900028
150	73974 	20	t		Secretaria	4003010000139
166	78372 	19	t		Próxima da janela	L1C7RZY
167	78386 	19	t		Direção	L1C7TYZ
174	74040 	20	t		Contadoria	4003010000022
118	63893 	9	f		Secretaria (ao fundo)	K931401201631
171	76155 	23	f		Gabinete juiz titular	BRG131FRXL
172	76156 	23	t		Notebook OJ	BRG131FRN8
119	71761 	11	f		Secretaria (próximo à direção)	400218490024
121	71763 	11	t	Micro auxiliar gabinete juiz titular		4002184900219
120	71762 	11	t	Apoio sala de audiências		4002184900229
128	89038 	22	t	Balcão		1A964TB3Z
132	71765 	11	t	Partes - sala de audiência	Sala de audiência	4002184900227
123	73829 	20	t	Partes - sala de audiência	Sala de audiência	4002688700328
144	71772 	11	t			4002184900033
54	71600 	11	t	Sem usuário definido		
131	63047 	12	f		Secretaria (Assistente de diretor)	L1AC1DF
115	63089 	12	f		Gabinete Juiz titular (assistente)	L1AC1VX
130	71603 	11	f		Gabinete juiz titular	4002184900157
136	73348 	16	t	Partes - sala de audiência	Secretaria (diretor)	4002739000328
141	73349 	16	t	Partes - sala de audiência	Sala de audiências	4002739000270
129	73347 	16	t	Partes - sala de audiência	Sala de audiências	4002739000162
137	73834 	20	t			4002688900166
138	73962 	20	t		Secretaria (fundo)	4003010000127
170	58930 	14	f		Secretaria (próximo à janela)	L1AB4UX
215	89111 	22	t		Audiências	1A956BN40
145	73350 	16	t	Partes - sala de audiência	Sala de audiências	4002739000160
142	71771 	11	f		Contadoria	4002184900169
146	73351 	16	t	(indefinido)	Secretaria	4002739000115
176	63001 	12	t	Partes - sala de audiência		L1AC3HF
177	63104 	12	f		Secretaria	L1AC3LZ
156	71759 	11	t			4002184900130
147	73352 	16	t		Secretaria (fundo)	4002738900210
151	73985 	20	t		Secretaria (ilha)	4003010000015
152	73993 	20	t		Secretaria (ilha próxima à janela)	4003010000112
153	73995 	20	t		Gabinete Juiz Substituto	4002688500252
154	74005 	20	t		Secretaria	4003010000009
155	63999 	9	f		CDM	K931401202152
163	74162 	20	t	Micro do scanner	Fundo	4002688900207
157	71773 	11	f		CDM	4002184900234
158	73353 	16	t	CDM		4002739000057
159	74006 	20	f		CDM	4002685200123
161	74159 	20	t		Secretaria (Fundo)	4002688400312
160	74160 	20	t		Balcão	4002688500144
116	73357 	16	f		Mesa do diretor	4002739000345
106	63904 	9	t	OAB		K931401201552
164	74163 	20	t		Balcão	4002688500211
165	74164 	20	t		Gabinete Juiz Titular	4002688900299
114	84623 	18	t		Sala de audiências	1A7639Y1J
31	63881 	9	f			K931401201540
178	63882 	9	f		Secretaria	K931401202145
109	62954 	12	t	OAB		L1AC1XX
108	63003 	12	f		Contadoria/Oficiais	L1AC1DL
105	66793 	10	t	oabsc (IN 30)	Sala da OAB	L615101801495
104	73354 	16	t	(Estagiários)		4002739000059
111	73355 	16	t	micro da contadoria	Contadoria/Oficiais	4002739000278
103	73356 	16	t	(indefinido)		4002739000255
102	73816 	20	t			4002688600142
96	84307 	18	t			1A7639X2L
173	73379 	16	f		Sala de audiências	4002739000213
162	74161 	20	t		Audiências	4002688100369
122	73805 	20	t	Micro reserva (CPD)		4003010000043
127	84409 	18	t	Micro juiz audiências	Sala de audiências	1A7627H3X
183	74070 	20	t		Gabinete juiz substituto	4003010000138
184	74091 	20	t			4003010000086
198	73359 	16	t		Secretaria	4002739000215
200	74037 	20	t		Secretaria	4002685200030
203	84306 	18	t		Direção	1A7639D6H
199	74036 	20	t			4002685200380
34	63894 	9	f		Assessores do juiz	
37	63953 	9	f			
67	73346 	16	f		Direção da secretaria	4002739000117
124	73973 	20	t		Secretaria (centro)	4003010000008
218	91378 	22	t			1AB67WR0U
40	64002 	9	f		Balcão	
133	71766 	11	t		Secretaria (centro)	4002184900186
214	89107 	22	t		Sala de audiências	1A956BP79
168	78427 	19	t		Micro TI	L1C7TTF
169	84463 	18	t		Fundo	1A763BK7L
125	73983 	20	t	Partes - sala de audiência	Sala de audiência	4002688500248
143	71769 	11	t	Juiz - sala de audiências	Sala de audiências	4002184900231
66	71760 	11	t	Agentes de Segurança	Saguão	400218490067
179	64113 	9	f		Secretaria	K931401201595
175	71694 	11	f		Secretaria, próximo aos banheiros	4002184900064
180	73882 	20	t		Balcão	4002688500161
254	96761 	42	t		Gabinete juiz substituto	1AF99LG0I
182	74042 	20	t		Assistentes de juiz	4002688000072
211	76190 	23	f		Notebook de juiz	BRG131FRB3
213	76215 	23	f		Notebook de juiz	BRG131FRFX
185	84388 	18	t	(indefinido)	Secretaria	1A7627X1Z
186	84462 	18	t		Secretaria	1A763B204
255	96762 	42	t			1AF99L542
210	58949 	14	t	(indefinido)	Notebook de juiz	L1AB4VR
190	63106 	12	t	Partes - sala de audiência		L1AC1CZ
192	63884 	9	f		Secretaria?	K931401201331
195	63885 	9	t	Partes - sala de audiência	Secretaria	K931401201307
196	64014 	9	f		Secretaria	K931401204160
197	71598 	11	t	(indefinido)	Secretaria	4002184900115
189	71702 	11	t	Micro reserva gabinete		4002184900072
188	73360 	16	t		Sala de audiências	4002739000186
201	74038 	20	t	(indefinido)	Secretaria	4002688600160
202	74086 	20	t	(indefinido)		4002688500271
212	76192 	23	t	Notebook de juiz: era do Dr. Irno		BRG131FRQ7
204	84332 	18	t		Gabinete juiz titular	1A7637F4W
51	66787 	10	t	OAB Balneário Camboriú		L615101805881
208	73358 	16	t	CDM	CDM	4002739000065
209	74039 	20	t	CDM		4002688500257
181	74041 	20	t	(indefinido)		4002688900136
187	84490 	18	t	(indefinido)		1A7639K2I
233	94376 	39	t		Contadoria	ZEQYBQAF90025BN
234	76030 	40	t		Secretaria	6816BAKZC00056Z
235	72405 	41	t		Sala dos assessores de gabinete	4F99BDAZ804478
236	96176 	42	t		Centro secretaria	1AF99LP9I
237	96763 	42	t			1AF99LG7H
238	96764 	42	t		Gabinete Juiz Titular	1AF99L525
239	91433 	43	t		Sala Assessores	1AB61PF83
240	96765 	42	t	(indefinido)		1AF99LG25
241	73870 	20	t	Balcão - sem usuário definido	Balcão	4002685200336
242	89065 	22	t		Direção	1A956BM9A
243	89173 	22	t		Gabinete juiz substituto	1A956CC20
244	89178 	22	t		Centro Secretaria	1A956B93D
245	91377 	43	t			1AB67WD5D
246	91440 	43	t	Micro juiz audiências		1AB61PF6T
247	96177 	42	t		Gabinete Juiz Substituto	1AF99MJ28
248	96779 	42	t		Contadoria	1AF96WC5R
249	96780 	42	t			1AF96VR73
250	96781 	42	t			1AF99M63A
251	91441 	43	t		Direção	1AB59930F
252	96178 	42	t		Gabinete Juiz titular	1AF99LV7Q
253	96760 	42	t		Contadoria	1AF96W19E
256	64097 	9	t	Micro OAB	OAB	K931401204040
257	91429 	43	t	CDM	CDM	1AB57VM4S
258	91430 	43	t	CDM	CDM	1AB51P74V
259	94635 	44	t	CDM	CDM	1AF03HK97
260	96256 	42	t	CDM	CDM	1AF96WD6Z
265	71728 	11	t	(indefinido)		4002184900113
268	91432 	43	t	Auto-atendimento		1AB67W760
269	91473 	43	t			1AB57WH4K
270	96211 	42	t		Direção	1AF99M89A
271	96850 	42	t	(indefinido)		1AF99MM61
272	96851 	42	t	(indefinido)		1AF99MT22
262	86756 	46	t	Geral para a secretaria	Secretaria	7463369902T3T
261	94300 	45	t	Geral para a secretaria	Secretaria	ZESKBQBF6000ZWD
\.


--
-- Name: equipamento_codigo_seq; Type: SEQUENCE SET; Schema: equipamentos; Owner: sati
--

SELECT pg_catalog.setval('equipamento_codigo_seq', 272, true);


--
-- Data for Name: equipamento_unidade; Type: TABLE DATA; Schema: equipamentos; Owner: sati
--

COPY equipamento_unidade (cod_equipamento, cod_unidade) FROM stdin;
2	2
3	3
4	3
5	3
6	2
7	4
8	4
9	4
10	4
11	4
12	4
13	4
14	3
15	8
16	2
17	1
18	2
19	3
20	8
21	2
22	4
23	1
24	7
25	2
26	4
27	4
28	2
29	3
30	4
32	3
33	2
34	2
36	1
37	2
38	1
39	1
41	8
42	7
43	1
44	4
45	7
46	7
47	2
48	3
49	4
50	1
51	6
52	5
53	3
54	1
55	8
56	3
57	1
58	2
59	8
60	7
61	1
63	2
64	1
65	1
66	1
67	2
68	1
69	70
70	70
71	70
72	70
73	70
74	70
75	70
76	70
77	70
78	70
79	70
80	70
81	70
82	70
83	70
84	70
85	70
87	70
88	70
89	70
90	5
91	5
93	5
94	5
95	5
97	5
98	5
100	5
101	5
103	5
104	5
105	5
106	5
108	5
109	5
111	5
112	8
113	4
114	2
31	7
115	3
116	6
117	4
118	2
119	2
120	2
121	2
123	2
124	2
125	2
126	2
127	2
128	2
129	3
130	3
131	3
133	3
134	3
135	3
136	3
138	3
139	3
140	3
141	4
142	4
143	4
144	4
145	4
146	4
147	4
148	4
149	4
150	4
151	4
152	4
153	4
154	4
155	1
157	1
158	1
159	1
163	1
166	1
167	1
168	1
169	1
170	4
171	2
173	7
174	7
175	7
176	7
177	7
178	7
179	7
180	7
181	7
182	7
183	7
184	7
185	7
186	7
187	7
188	8
189	8
192	8
195	8
196	8
197	8
198	8
199	8
200	8
201	8
202	8
203	8
208	6
209	6
210	8
211	7
212	8
214	4
215	3
40	3
218	2
172	1
137	71
161	3
160	3
162	7
164	3
165	3
86	2
102	71
99	72
96	71
213	6
204	3
92	71
233	2
234	70
235	70
236	2
237	2
238	2
239	2
240	2
122	1
241	2
242	2
243	2
244	2
245	2
246	3
247	3
248	3
249	3
250	3
251	4
252	4
253	4
254	4
255	4
256	1
257	1
258	1
259	1
260	1
261	4
262	4
190	70
265	70
156	70
132	70
268	70
269	70
270	70
271	70
272	70
\.


--
-- Data for Name: equipamento_usuario; Type: TABLE DATA; Schema: equipamentos; Owner: postgres
--

COPY equipamento_usuario (equipamento_cod, usuario_id) FROM stdin;
124	14
126	18
114	19
218	23
133	39
135	26
138	38
139	28
161	32
160	37
164	33
165	35
140	29
215	34
147	53
148	47
149	43
150	52
151	54
152	42
153	51
154	52
117	46
214	45
172	10
167	5
168	1
169	7
88	61
77	73
80	75
78	71
76	74
75	68
72	69
71	66
69	70
52	120
102	132
99	138
98	119
97	115
96	137
180	88
174	84
182	80
183	143
184	79
162	77
186	87
189	91
198	103
188	104
199	102
200	92
203	3
204	30
94	114
93	116
91	121
90	117
137	135
92	144
86	4
233	4
236	22
237	21
238	12
239	20
242	16
243	24
244	13
245	15
247	40
248	27
249	36
250	31
144	49
251	44
252	41
253	48
254	55
255	50
166	6
156	65
269	64
270	67
\.


--
-- Data for Name: historico; Type: TABLE DATA; Schema: equipamentos; Owner: sati
--

COPY historico (codigo, data_historico, descricao, observacao, indicente_ritm, cod_equipamento) FROM stdin;
7	2013-11-20	Sacada a placa de vídeo para observação do comportamento do micro	Tentando descobrir problema de reboots não solicitados	81249	113
8	2013-12-06	Substituído temporariamente	Problemas no HD. Micro foi recolhido para ter HD substituído pela garantia. Problemas de setores ruins.	82535	84
9	2013-12-06	Instalação de placa de vídeo	Placa de vídeo retirada de um Infoway.		115
10	2013-12-10	Substituição de placa-mãe	Em resposta a problema de não reconhecimento de elementos USB	82486	114
12	2014-01-09	Substituição da fonte	Realizada pela garantia	83244	83
13	2014-01-27	Formatação/encaminhamento para baixa no TRT			113
14	2014-01-29	Troca da fonte interna	Via garantia Itautec	83974	80
11	2014-01-08	Limpeza de contatos de memória eliminou problema para ligar	Realizado pela garantia.	83132	116
15	2014-02-07	Limpeza de contatos de memória	Não foi criado incidente		151
16	2014-03-21	Substituição de HD	Foi colocado um de 160GB (usado) para superar problemas de paralisação constante do sistema.	85970	143
17	2014-03-28	Baixado: placa-mãe defeituosa (conserto inviável)		85694	44
18	2014-03-28	Instalação física do microcomputador		85694	214
19	2014-04-01	Substituição da fonte	Para resolver problemas de travamento	86626	175
20	2014-04-01	Baixa/devolução do equipamento: reparo anti-econômico	Placa-mãe pifada	85363	134
21	2014-04-03	Instalação (substituição)		85363	215
22	2014-04-08	Nova substituição da fonte	Em garantia de serviço	86920	83
23	2014-10-29	Substituída a fonte	Defeito na anterior: micro fazia vários bips e não ligava	96002	81
24	2014-10-29	Substituída a fonte	Substituída pela garantia (fonte queimada)	95663 	96
26	2016-05-27	Formatação com nova imagem devido a problemas de travamento			86
27	2016-05-31	Chamado de garantia por problema no fusor		9882	233
29	2011-01-12	Limpeza interna com pincel e aspirador	\N	35681	23
30	2011-02-04	Limpeza com pincel e aspirador e limpeza de contatos da memória	Utilizado spray limpa contatos	36740	22
31	2011-02-11	Limpeza com pincel e aspirador	Spray limpa contatos nos slots e nas memórias.\nDesfragmentador de disco.	37140	4
33	2011-02-18	Limpeza com pincel e aspirador	Limpeza de contatos de memória\nDesframgmentação do disco	37616	2
34	2011-02-22	Apenas desfragmentação	\N	37755	63
35	2011-02-22	Limpeza com pincel e aspirador	Limpeza de contatos da memória\ndesfragmentador	37756	33
36	2011-02-23	Limpeza com pincel e aspirador	Limpeza dos contatos de memória.\nMicro será formatado devido a problemas apresentados antes.	37889	64
37	2011-03-03	Limpeza com pincel e aspirador	Limpeza de contatos da memória e desfragmentação de disco	38366	5
38	2011-03-17	Limpeza com pincel e aspirador	Limpeza dos contatos de memória. Desfragmentador	38848	10
\.


--
-- Name: historico_codigo_seq; Type: SEQUENCE SET; Schema: equipamentos; Owner: sati
--

SELECT pg_catalog.setval('historico_codigo_seq', 28, true);


--
-- Data for Name: lote; Type: TABLE DATA; Schema: equipamentos; Owner: sati
--

COPY lote (codigo, nome, datafimgarantia, cod_modelo) FROM stdin;
1	NF 44409	2010-05-05	2
2	NF 209593	2006-02-10	6
3	NF 199742	2008-03-18	1
4	NF 199744	2008-03-20	1
5	NF 601995	2010-03-07	8
6	NF 70712	2009-12-29	5
7	NF 54819	2009-03-17	7
8	NF 56393	2009-03-31	7
9	NF 687999	2012-03-20	4
10	NF 748142	2012-12-14	4
11	NF 89974	2013-11-11	9
12	NF 93154	2012-03-25	3
13	NF 147353	2009-08-24	10
14	NF 48228	2010-01-13	11
15	NF 54905	2008-10-17	13
16	NF 108021,107983	2015-03-02	15
17	NF 173616	2011-02-07	16
19	NF 8510/8511	2015-08-21	17
20	NF 112551	2014-03-31	9
21	NF 49754	2015-12-26	18
22	NF 7400159	2017-11-09	14
23	NF 299169	2014-08-29	19
18	NF 564667, 564488	2017-03-19	14
40	NF 41884	2014-08-15	35
41	NF 15943	2014-01-11	36
39	NF 98846	2018-03-25	34
42	NF 1236263 e 1236264 (tombos 96126 a 97333)	2019-09-28	37
43	NF 831731	2018-04-06	14
44	NF 1105156 (tombos 94502 a 94646)	2019-04-13	37
45	NF 98846 (tombos 94295 a 94444	2018-03-25	38
46	NF 16465	2016-05-20	39
\.


--
-- Name: lote_codigo_seq; Type: SEQUENCE SET; Schema: equipamentos; Owner: sati
--

SELECT pg_catalog.setval('lote_codigo_seq', 46, true);


--
-- Data for Name: modelo; Type: TABLE DATA; Schema: equipamentos; Owner: sati
--

COPY modelo (codigo, nome, descricao, cod_tipo_equipamento) FROM stdin;
1	IBM 8188-QPR	CELERON, 2.4 GHZ, 256 MB, 40 GB	1
2	Cobra UPD MEGA	CELERON-D-2.4GHZ -  256MB-DDR333 -  40GB-IDE-7200	1
3	Lenovo THINKCENTRE AA7	PENTIUM IV DUOCORE 2,33GHZ, 2GB, HD 160GB	1
5	Itautec Infoway ST4250 (Pentium D)	Pentium D 3.4GHz 1GB-2x512 HD 80GB-SATA	1
7	Itautec Infoway ST4250 (Pentium IV)	PENTIUM IV, 3.2GHZ, 1GB-2x512, 80G-SATA, XP-PRO-SP2	1
8	Itautec Infoway ST4150	Pentium D 2.8GHz, 1GB-2x512, 80GB-SATA, XP-PRO SP2	1
9	Itautec Infoway ST4271	Intel Core I5 VPro 4GB HD 300GB Windows 7 Professional 64	1
10	HP PROLIANT ML 150	XEON 3.0 - 2GB - 2X36+2X72	1
11	IBM THINKPAD T60	NOTEBOOK CENTRINO CORE DUO 2.4 HD 80 DVDRW	1
13	Brother HL7050	Laser Monocromático 30 PPM	2
14	Positivo MASTER D570	INTEL CORE I5 VPRO 8GB 120GB SSD	1
15	Itautec Infoway SM3330	AMD PHENON II X2 550 - 3100MHZ 4 GB HD 300GB	1
16	Lenovo L-194W ThinkVision	19" Policromático LCD	6
17	Lenovo 4518NZ2	M91P SFF / I5-2400 / 2X2GB / 500GB / DVDRW	1
18	Samsung ML-3710ND	LASER MONOCROMÁTICA DUPLEX	2
19	HP ELITEBOOK 8460P	Notebook dos juízes	1
6	IBM 8142-KPM	Pentium IV 3.0 GHz, 512MB, 80GB, CD-RW	1
4	Itautec Infoway ST4260	PENTIUM CORE 2 DUO 2GHz 2GB HD160GB	1
34	Samsung SL-M4020ND	Laser Monocromática	2
35	Samsung SCX 5835	MULTIFUNCIONAL EQUIPAMENTO MULTIFUNÇÃO	2
36	Samsung ML-2850	Impressora Laser	2
37	Positivo Master D580	MICROCOMPUTADOR DESKTOP	1
38	Samsung SCX-6555NX	MULTIFUNCIONAL EQUIPAMENTO MULTIFUNÇÃO	2
39	Lexmark MX711	Equipamento multifunção	2
\.


--
-- Name: modelo_codigo_seq; Type: SEQUENCE SET; Schema: equipamentos; Owner: sati
--

SELECT pg_catalog.setval('modelo_codigo_seq', 39, true);


--
-- Data for Name: tipo_equipamento; Type: TABLE DATA; Schema: equipamentos; Owner: sati
--

COPY tipo_equipamento (codigo, nome) FROM stdin;
1	Microcomputador
2	Impressora/Multifuncional
3	Roteador
4	Switch
5	Hub
6	Monitor
7	No-break
8	Scanner
\.


--
-- Name: tipo_equipamento_codigo_seq; Type: SEQUENCE SET; Schema: equipamentos; Owner: sati
--

SELECT pg_catalog.setval('tipo_equipamento_codigo_seq', 22, true);


SET search_path = public, pg_catalog;

--
-- Data for Name: areati; Type: TABLE DATA; Schema: public; Owner: sati
--

COPY areati (codigo, nome, cod_municipiosede) FROM stdin;
6	Chapecó	7
3	Criciúma	9
7	Florianópolis	11
1	Itajaí	15
4	Joinville	18
2	Blumenau	3
5	Lages	19
\.


--
-- Name: areati_codigo_seq; Type: SEQUENCE SET; Schema: public; Owner: sati
--

SELECT pg_catalog.setval('areati_codigo_seq', 30, true);


--
-- Data for Name: municipio; Type: TABLE DATA; Schema: public; Owner: sati
--

COPY municipio (codigo, nome, dtfundacaounidade, observacao, cod_areati) FROM stdin;
2	Balneário Camboriú	1993-05-28		1
3	Blumenau	1959-06-26		2
5	Caçador	1979-07-27		5
6	Canoinhas	1989-05-19		4
7	Chapecó	1968-04-23		6
8	Concórdia	1968-04-22		6
9	Criciúma	1960-01-27		3
10	Curitibanos	1993-08-20		5
11	Florianópolis	1934-06-05		7
12	Fraiburgo	2005-01-07		5
13	Imbituba	1993-10-01		3
14	Indaial	1992-10-09		2
15	Itajaí	1963-09-09		1
16	Jaraguá do Sul	1989-03-27		4
17	Joaçaba	1979-01-25		5
18	Joinville	1960-01-29		4
19	Lages	1965-10-16		5
20	Mafra	1986-07-18		4
21	Rio do Sul	1971-03-27		2
22	São Bento do Sul	1989-06-30		4
23	São José	1992-09-03		7
24	São Miguel do Oeste	1986-07-04		6
25	Timbó	2005-12-16		2
26	Tubarão	1965-06-01		3
27	Videira	1989-04-14		5
28	Xanxerê	1989-07-10		6
31	Navegantes	2011-12-05		1
1	Araranguá	1989-06-26		3
29	Palhoça	2013-08-20		7
4	Brusque	1971-03-26		1
\.


--
-- Name: municipio_codigo_seq; Type: SEQUENCE SET; Schema: public; Owner: sati
--

SELECT pg_catalog.setval('municipio_codigo_seq', 56, true);


--
-- Data for Name: progint; Type: TABLE DATA; Schema: public; Owner: sati
--

COPY progint (codigo, nome, email, matricula, fonecontato, observacao, ativo, cod_areati, cod_unidade) FROM stdin;
1	Paulo Sérgio Pinheiro	paulo.pinheiro@trt12.jus.br	2360      	47 3241 1299	teste	t	1	1
2	Alexandre Strelow Fagundes	alexandre.fagundes@trt12.jus.br	2766      			t	6	19
3	Mauricio Martinez Mota	mauricio.mota@trt12.jus.br	2960      			f	5	48
4	Robson Nestor Bandeira	robson.bandeira@trt12.jus.br	2349      			t	4	26
6	Valmor Mrotskoski Madeira	valmor.madeira@trt12.jus.br	2436      			t	3	32
7	Giovani Carelli	giovani.carelli@trt12.jus.br	2886      			t	7	38
10	Marcos Paulo Zimmermann	marcos.zimmermann@trt12.jus.br	2702      			t	2	13
26	Marcio Cancian	marciocancian73@gmail.com	apoio63   			t	1	6
\.


--
-- Name: progint_codigo_seq; Type: SEQUENCE SET; Schema: public; Owner: sati
--

SELECT pg_catalog.setval('progint_codigo_seq', 26, true);


--
-- Data for Name: setor; Type: TABLE DATA; Schema: public; Owner: sati
--

COPY setor (codigo, nome, sigla) FROM stdin;
\.


--
-- Name: setor_codigo_seq; Type: SEQUENCE SET; Schema: public; Owner: sati
--

SELECT pg_catalog.setval('setor_codigo_seq', 13, true);


--
-- Data for Name: unidade; Type: TABLE DATA; Schema: public; Owner: sati
--

COPY unidade (codigo, nome, sigla, prefixo, localizacao, observacao, cod_municipio) FROM stdin;
2	1ª Vara do Trabalho de Itajaí/SC	1vara_iai 	05 			15
3	2ª Vara do Trabalho de Itajaí/SC	2vara_iai 	22 			15
10	Vara do Trabalho de Canoinhas/SC	vara_cni  	21 			6
11	Vara do Trabalho de Concórdia/SC	vara_cda  	08 			8
12	Vara do Trabalho de Imbituba/SC	vara_ima  	43 			13
7	1ª Vara do Trabalho de Balneário Camboriú/SC	1vara_bcu 	40 	Quarta Avenida, 740\r\nCentro - Balneário Camboriú/SC\r\n\r\nDefronte ao Supermercado Xande - sobreloja do Banco do Brasil da Quarta Avenida		2
8	2ª Vara do Trabalho de Balneário Camboriú/SC	2vara_bcu 	45 	Quarta Avenida, 740\r\nCentro - Balneário Camboriú/SC\r\n\r\nDefronte ao Supermercado Xande - sobreloja do Banco do Brasil da Quarta Avenida		2
14	1ª Vara do Trabalho de Blumenau/SC	1vara_bnu 	02 			3
15	2ª Vara do Trabalho de Blumenau/SC	2vara_bnu 	18 			3
17	4ª Vara do Trabalho de Blumenau/SC	4vara_bnu 	51 			3
16	3ª Vara do Trabalho de Blumenau/SC	3vara_bnu 	39 			3
4	3ª Vara do Trabalho de Itajaí/SC	3vara_iai 	47 			15
18	Vara do Trabalho de Caçador/SC	vara_cdr  	13 			5
20	1ª Vara do Trabalho de Chapecó/SC	1vara_cco 	09 			7
21	2ª Vara do Trabalho de Chapecó/SC	2vara_cco 	38 			7
61	3ª Vara do Trabalho de São José/SC	3vara_soo 	54 			23
23	1ª Vara do Trabalho de Jaraguá do Sul/SC	1vara_jgs 	19 			16
24	2ª Vara do Trabalho de Jaraguá do Sul/SC	2vara_jgs 	46 			16
25	Vara do Trabalho de Joaçaba/SC	vara_jca  	12 	Rua: Francisco Lindner, nº 434 - 1º andar CEP: 89.600-000 - Joaçaba - SC		17
27	1ª Vara do Trabalho de Joinville/SC	1vara_jve 	04 			18
28	2ª Vara do Trabalho de Joinville/SC	2vara_jve 	16 			18
29	3ª Vara do Trabalho de Joinville/SC	3vara_jve 	28 			18
30	4ª Vara do Trabalho de Joinville/SC	4vara_jve 	30 			18
31	5ª Vara do Trabalho de Joinville/SC	5vara_jve 	50 			18
33	1ª Vara do Trabalho de Criciúma/SC	1vara_cua 	03 			9
34	2ª Vara do Trabalho de Criciúma/SC	2vara_cua 	27 			9
35	3ª Vara do Trabalho de Criciúma/SC	3vara_cua 	53 			9
36	4ª Vara do Trabalho de Criciúma/SC	4vara_cua 	55 			9
37	Vara do Trabalho de Curitibanos/SC	vara_cbs  	42 			10
39	1ª Vara do Trabalho de Florianópolis/SC	1vara_fns 	01 			11
40	2ª Vara do Trabalho de Florianópolis/SC	2vara_fns 	14 			11
41	3ª Vara do Trabalho de Florianópolis/SC	3vara_fns 	26 			11
42	4ª Vara do Trabalho de Florianópolis/SC	4vara_fns 	34 			11
43	5ª Vara do Trabalho de Florianópolis/SC	5vara_fns 	35 			11
44	6ª Vara do Trabalho de Florianópolis/SC	6vara_fns 	36 			11
45	7ª Vara do Trabalho de Florianópolis/SC	7vara_fns 	37 			11
47	Vara do Trabalho de Indaial/SC	vara_idl  	33 			14
46	Vara do Trabalho de Fraiburgo/SC	vara_fgo  	49 			12
49	1ª Vara do Trabalho de Lages/SC	1vara_lgs 	07 			19
50	2ª Vara do Trabalho de Lages/SC	2vara_lgs 	29 			19
51	Vara do Trabalho de Mafra/SC	vara_mfa  	17 			20
54	1ª Vara do Trabalho de Rio do Sul/SC	1vara_rsl 	11 			21
55	2ª Vara do Trabalho de Rio do Sul/SC	2vara_rsl 	48 			21
56	Vara do Trabalho de São Bento do Sul/SC	vara_sbs  	24 			22
57	Serviço de Distribuição São José/SC	sedis_soo 	SOO			23
58	1ª Vara do Trabalho de São José/SC	1vara_soo 	31 			23
59	2ª Vara do Trabalho de São José/SC	2vara_soo 	32 			23
63	Vara do Trabalho de São Miguel do Oeste/SC	vara_sge  	15 			24
64	Vara do Trabalho de Timbó/SC	vara_tio  	52 			25
65	Serviço de Distribuição Tubarão/SC	sedis_tro 	TRO			26
66	1ª Vara do Trabalho de Tubarão/SC	1vara_tro 	06 			26
67	2ª Vara do Trabalho de Tubarão/SC	2vara_tro 	41 			26
68	Vara do Trabalho de Videira/SC	vara_vii  	20 			27
69	Vara do Trabalho de Xanxerê/SC	vara_xxe  	25 			28
70	Vara do Trabalho de Navegantes/SC	vara_nvg  	56 	Av. Prefeito José Juvenal Mafra, 31, térreo, esquina com Avenida João Sacavem - Centro - Navegantes/SC		31
6	NUGECEM Balneário Camboriú/SC	sedis_bcu 	BCU	Quarta Avenida, 740\nCentro - Balneário Camboriú/SC\nEntrada ao lado do Banco do Brasil da Quarta Avenida		2
53	NUGECEM Rio do Sul/SC	sedis_rsl 	RSL			21
71	2ª Vara do Trabalho de Brusque/SC	2vara_bqe 	61 			4
13	NUGECEM Blumenau/SC	sedis_bnu 	BNU			3
5	1ª Vara do Trabalho de Brusque/SC	1vara_bqe 	10 			4
72	NUGECEM Brusque/SC	semaf_bqe 	BQE			4
19	NUGECEM Chapecó/SC	sedis_cco 	CCO			7
73	3ª Vara do Trabalho de Chapecó/SC	3vara_cco 	57 			7
74	4ª Vara do Trabalho de Chapecó/SC	4vara_cco 	58 			7
32	NUGECEM Criciúma/SC	sedis_cua 	CUA			9
38	NUGECEM Florianópolis/SC	sedis_fns 	FNS			11
1	NUGECEM Itajaí/SC	sedis_iai 	IAI			15
22	NUGECEM Jaraguá do Sul/SC	sedis_jgs 	JGS			16
26	NUGECEM Joinville/SC	sedis_jve 	JVE			18
48	NUGECEM Lages/SC	sedis_lgs 	LGS			19
75	3ª Vara do Trabalho de Lages/SC	3vara_lgs 	60 			19
62	Vara do Trabalho de Palhoça/SC	vara_pca  	59 	Av. Atílio Pedro Pagani, nº 855 - 1º andar - CEP 88.132-060 – Passa Vinte (Pagani)– Palhoça - SC		29
9	Vara do Trabalho de Araranguá/SC	vara_aru  	23 	Rua Presidente João Goulart nº 273 - térreo - Edifício Giácomo Mazzuco - Cidade Alta - CEP 88 900.000 - ARARANGUÁ/SC		1
\.


--
-- Name: unidade_codigo_seq; Type: SEQUENCE SET; Schema: public; Owner: sati
--

SELECT pg_catalog.setval('unidade_codigo_seq', 90, true);


--
-- Data for Name: usuario_final; Type: TABLE DATA; Schema: public; Owner: sati
--

COPY usuario_final (id, nome, matricula, fonecontato, observacao, cod_unidade, ativo, email) FROM stdin;
1	Paulo Sérgio Pinheiro	2360      	47 3241 1239		1	t	paulo.pinheiro@trt12.jus.br
3	Fábio Gil Beal	2933      			8	t	
5	Roberto Jasper Neto	2628      			1	t	
6	Alexandre Couto Ferreira	2912      			1	t	
7	Elias dos Santos	1927      			1	t	
8	Rozane Bedin	932       			1	t	
11	Ana Rosa Leduc	1764      			1	t	
12	Luiz Carlos Roveda	2019      			2	t	
13	Carla Zappelini Roncatto	2423      			2	t	
10	Álvaro Alcides Pereira	2506      			1	t	
9	Osmar Aguiar	960       			1	t	
4	Aldo Sergio Santos Silva Ramos	2728      			2	t	
14	Marceli Ines Beuron	4241      			2	t	
15	Fábia Willert da Rocha	2476      			2	t	
16	Rogério Jorge Rosa	1261      			2	t	
17	Tânia Maria da Conceição Gonçalves	2453      			2	t	
18	Elizabeth Abreu Pereira Bernardes	1773      			2	t	
19	Lúcia Maria Andrade de Oliveira	2592      			2	t	
20	Larissa Sampaio de Pinho Pessoa	3637      			2	t	
21	Norberto Hauer Júnior	3656      			2	t	
22	Paula Tiemi Itakura	3782      			2	t	
23	Harley Sadraque Amaral da Silva	3794      			2	t	
24	Daniel Lisboa	2946      			2	t	
25	Ubiratan Alberto Pereira	1936      			3	t	
26	João da Cruz Ramos Filho	259       			3	t	
27	Humberto Luiz Silva	370       			3	t	
28	Yeda Maria Rodolfo Mafra	705       			3	t	
29	Willian Paulo Pereira	1450      			3	t	
30	Luiz José Pinto	1824      			3	t	
31	Eveline Manfio Montai	3355      			3	t	
32	Juliana de Bittencourt Vailati	3507      			3	t	
33	Lucas Broering Correa	3556      			3	t	
34	Daniella Cristina Vitorino	3670      			3	t	
35	Luciana Neves Bohnert	3686      			3	t	
36	Andressa Roman Teixeira	4155      			3	t	
37	Carlos Henrique Gutz Leite de Castro	4227      			3	t	
38	Cleusa Maria Ricardo Kinaipp	4274      			3	t	
39	Julio Cesar Martins Verfe	4352      			3	t	
40	Ozéas de Castro	2853      			3	t	
41	Ricardo Córdova Diniz	2015      			4	t	
42	Luiz Alberto da Silva	1202      			4	t	
43	Shirley Cruz de Oliveira dos Santos	1774      			4	t	
44	Adriana Sampaio Russi	2470      			4	t	
45	Vera Beatriz Azevedo Ramos	1865      			4	t	
46	Elisangela Martins Fornari	2879      			4	t	
47	Adriana Martovicz Lauth dos Santos	2930      			4	t	
48	Fernanda Santos Greff	3117      			4	t	
49	Murilo Oliveira Schmitt	3265      			4	t	
50	Kasunori Seida	3383      			4	t	
51	Carla Schneider Bicalho	3761      			4	t	
52	Michelle Chedid	3894      			4	t	
53	Ligia Janke	4092      			4	t	
54	Ivone Jaworski	4291      			4	t	
55	Fabricio Zanatta	2815      			4	t	
56	Eloísa de Pontes Poltronieri	947       			1	t	
59	Norival Provesi	629       			1	t	norival.provesi@trt12.jus.br
60	Sandra Silva dos Santos	2102      			70	t	
61	Elistelma Leonardo Domingos	867       			70	t	
62	Luis Irapuan Campelo Bessa Filho	1947      			70	t	
63	Delmir Schwambach	2495      			70	t	
64	Rogerio Ruel	2893      			70	t	
65	Sergio Joubert da Silva	2621      			70	t	
66	Thais Amanda Pereira Padua	2998      			70	t	
67	Debora Cristina Bastianick	3174      			70	t	
68	Andre Belomo Castanho	3784      			70	t	
69	Daiane Patricio	3851      			70	t	
70	Sabrina Gonzaga	3889      			70	t	
71	Lucas Augusto de Souza Sobreira Silva	4129      			70	t	
72	Silvia Mari Okuyama	4368      			70	t	
73	Carlos Alberto Cortellete Filho	4377      			70	t	
74	Rafael Pena de Carvalho	4423      			70	t	
75	Matheus de Souza Araujo	4437      			70	t	
76	Ilma Vinha	2344      			7	t	
77	Marcos Alfredo Paul	797       			7	t	
78	Leonardi Lourdes Welter	884       			7	t	
79	Ivo Haendchen	1658      			7	t	
80	Pericles Adonis Morastoni	2822      			7	t	
81	Valdir Colauto Rodrigues Júnior	2993      			7	t	
82	Taíse Marques Teixeira	3010      			7	t	
83	Marli Regina Lise Zamprogna	1852      			7	t	
84	Protásio Cardozo	2897      			7	t	
85	Ricardo Augusto Lucas Vaz	3097      			6	t	
86	Maria Antônia de Souza dos Santos	3398      			7	t	
87	Milene Morandi Alves	3789      			7	t	
88	Henrique Castro Guimarães	3796      			7	t	
89	Giuliano Motta	4000      			7	t	
90	Telma de Castro Morisson	4450      			7	t	
91	Irno Ilmar Resener	1938      			8	t	
92	Francisco Afonso Rosa	888       			8	t	
93	Marcelo Andriani Ouriques	1815      			8	t	
94	Antônio Ricardo de Sousa	2229      			8	t	
95	Paulo Sérgio Teixeira Brandão	2258      			8	t	
96	Maria Cláudia Mandelli	2413      			8	t	
97	Leocádia Bruhmuller	2577      			8	t	
98	Milena Letícia Anesi	2599      			8	t	
99	Marli Primon	2634      			8	t	
100	André Zampieri Alves	2889      			6	t	
101	Nazira Santos Schead Tavares	2771      			8	t	
102	Patrícia Helena Schulter	3059      			8	t	
103	Maysa Rufini Guimarães	3828      			8	t	
104	Heloísa Favero Rodrigues	4260      			8	t	
105	Alexandre Mussi Brandão	575       			6	t	
106	Kátia Maria Martins Ribeiro	904       			6	t	
107	Adão Soares Pinto	1572      			6	t	
108	Valter Alberto Nitz	1605      			6	t	
109	Irineu Kühl	2240      			6	t	
110	Marinês Rosane Mistura	2371      			6	t	
111	Gilson Correia	2464      			6	t	
112	Hélio Henrique Garcia Romero	2018      			5	t	
113	Sônia Weingartner Besser	996       			5	t	
114	Francisco Fernando Fuck	2483      			5	t	
115	Fátima Maria de Souza Araújo	2865      			5	t	
116	Greice Weitgenant	2901      			5	t	
117	Juliany Martins Grams	3120      			5	t	
118	Gabriela de Araújo Albuquerque	3536      			5	t	
119	Sara Javaroni Veiga	3795      			5	t	
120	Rubiane Rita Gamba	3809      			5	t	
121	Bruna Cristina Poffo de Azevedo	3926      			5	t	
122	Aneliya Konstantinova Toneva	3997      			5	t	
123	Faustus Gomes Fonseca	4207      			5	t	
124	Cynthia Santini	4217      			5	t	
125	Lilian Oliveira de Moraes	4255      			5	t	
126	Sônia Maria Ferreira Roberts	2011      			71	t	
127	Robert Staloch	2727      			71	t	
128	Sandro Daniel Sanchez	2896      			71	t	
129	Narciso Gonçalves	2641      			71	t	
130	Paola Karina Marchioro Sokoloski	3616      			71	t	
131	Carolina Grieco Rodrigues Dias	3931      			71	t	
132	Roberto Carlos Raposo	3982      			71	t	
133	Renan Portela tito	3998      			71	t	
134	Elisa Wildemberg Campos	4120      			71	t	
135	Marina Roque Thompson	4275      			71	t	
136	Daniel Jacinto Castriola	4399      			71	t	
137	Carlos Eduardo Cavalieri Brandão	4401      			71	t	
138	Rubi Righetto Júnior	961       			72	t	
139	Ione Brautigam	1948      			72	t	
140	Sérgio Murilo dos Anjos	2932      			72	t	
141	Uilson Ronaldo Ferreira	3437      			72	t	
142	Daiane Virgínia Alves Rosolen	4161      			72	t	
143	Fábio Tosetto	3083      			7	t	
144	Izabel Maria Amorim Lisboa	3985      			71	t	
145	Juliano Praça	2423      			2	t	
57	Emília da Costa Chaves	4457      			2	f	
\.


--
-- Name: usuario_final_id_seq; Type: SEQUENCE SET; Schema: public; Owner: sati
--

SELECT pg_catalog.setval('usuario_final_id_seq', 145, true);


SET search_path = redes, pg_catalog;

--
-- Data for Name: categoriaelementorede; Type: TABLE DATA; Schema: redes; Owner: sati
--

COPY categoriaelementorede (codigo, nome) FROM stdin;
1	Servidores     
2	Impressoras    
3	Estações       
\.


--
-- Name: categoriaelementorede_codigo_seq; Type: SEQUENCE SET; Schema: redes; Owner: sati
--

SELECT pg_catalog.setval('categoriaelementorede_codigo_seq', 3, true);


--
-- Data for Name: elementorede; Type: TABLE DATA; Schema: redes; Owner: sati
--

COPY elementorede (codigo, nome, descricao, mac, tombo, ip, observacao, cod_categoria, cod_unidade) FROM stdin;
\.


--
-- Name: elementorede_codigo_seq; Type: SEQUENCE SET; Schema: redes; Owner: sati
--

SELECT pg_catalog.setval('elementorede_codigo_seq', 4, true);


--
-- Data for Name: estacao; Type: TABLE DATA; Schema: redes; Owner: sati
--

COPY estacao (codigo, nome, descricao, mac, tombo, ip, observacao, cod_categoria, cod_unidade) FROM stdin;
3	IAI63049	Lenovo TI	00-1A-6B-65-31-77	63049 	\N	\N	3	\N
\.


--
-- Data for Name: modulo; Type: TABLE DATA; Schema: redes; Owner: sati
--

COPY modulo (codigo, identificacao, localizacao, descricao, observacao, cod_tipomodulo, cod_unidade) FROM stdin;
4	MCPD01	CPD, na parede oposta à entrada	Tomada única	Servidor de rede.	1	1
3	MLab02	Laboratório, à direita de quem entra	Tomada única	Mesa de testes	1	1
5	MDIS01	SEDIS, defronte à copa	Duas caixas justapostas: será considerado um módulo	Brother MFC-8820D e telefone	1	1
7	MDIS03	SEDIS, último da parede à esquerda de quem entra	Duas caixas justapostas: será considerado um módulo	Microcomputador Elias	1	1
9	MDIS05	SEDIS, na parede que divide com a Central de Mandados	Duas caixas justapostas: será considerado um módulo	Micro e fone da Eliete	1	1
8	MDIS04	SEDIS, defronte ao balcão	Só duas tomadas ocupadas	Microcomputador Moacir\nFalta identificação das tomadas	3	1
10	MDIS06	SEDIS, na parede da direita de quem entra	Duas caixas justapostas: será considerado um módulo	Microcomputador e fone do diretor	1	1
11	MCDM01	CDM, parede da esquerda de quem entra, próximo da divisão com a SEDIS	Duas caixas justapostas: será considerado um módulo		1	1
13	MCDM03	CDM, parede do fundo, primeiro da esquerda	Duas caixas justapostas: será considerado um módulo		1	1
14	MCDM04	CDM, parede do fundo: centro	Duas caixas justapostas: será considerado um módulo		1	1
15	MCDM05	CDM, parede do fundo, próximo à porta	Duas caixas justapostas: será considerado um módulo		1	1
12	MCDM02	CDM, parede da esquerda de quem entra, canto com o fundo	Duas caixas justapostas: será considerado um módulo		1	1
1	MLab01	Laboratório, na parede que divide com o CPD	Duas tomadas RJ-45	Telefone e micro do TI	1	1
6	MDIS02	SEDIS, no meio da parede à esquerda de quem entra	Duas caixas justapostas: será considerado um módulo	Samsung ML-4550 e microcomputador Norival.	1	1
\.


--
-- Name: modulotelematica_codigo_seq; Type: SEQUENCE SET; Schema: redes; Owner: sati
--

SELECT pg_catalog.setval('modulotelematica_codigo_seq', 30, true);


--
-- Data for Name: panel; Type: TABLE DATA; Schema: redes; Owner: sati
--

COPY panel (codigo, nome, quantportas, descricao, observacao, cod_rack) FROM stdin;
3	P01            	24			1
7	P03            	24			1
8	P04            	24			1
9	P05            	24			1
10	P06            	24			1
11	P07            	24			1
12	P08            	24			2
14	V01            	24	Voice panel		1
15	V02            	24			1
16	V03            	24			1
18	V05            	24			1
13	P09            	24		Painel instalado como extensão de 6 portas que partem do CPD (do P02 do Rack01). Algumas dessas portas são voice e por isso serão ligadas a portas do P08 do mesmo Rack02	2
17	V04            	24		Assim que possível desativar todas as crimpagens e transformá-lo em novo panel de pontos de telemática (P09)	1
19	P1             	48			3
20	P2             	48			3
21	V1             	24			3
23	V2             	24			3
24	P00            	24			1
6	P02            	24			1
\.


--
-- Name: panel_codigo_seq; Type: SEQUENCE SET; Schema: redes; Owner: sati
--

SELECT pg_catalog.setval('panel_codigo_seq', 38, true);


--
-- Data for Name: rack; Type: TABLE DATA; Schema: redes; Owner: sati
--

COPY rack (codigo, identificacao, localizacao, descricao, observacao, cod_municipio) FROM stdin;
2	Rack02	Serviço de Distribuição	10 baias	Antigo rack da 3ª VT	15
3	Rack01	2ª Vara	20 baias	1 switch e 3 hubs	2
1	Rack01	CPD	40 baias		15
\.


--
-- Name: rack_codigo_seq; Type: SEQUENCE SET; Schema: redes; Owner: sati
--

SELECT pg_catalog.setval('rack_codigo_seq', 19, true);


--
-- Data for Name: rangeip; Type: TABLE DATA; Schema: redes; Owner: sati
--

COPY rangeip (codigo, inferior, superior, observacao, cod_rede, cod_categoria) FROM stdin;
1	10.12.56.12    	10.12.56.254   		1	1
2	10.12.58.12    	10.12.59.254   		1	3
3	10.12.57.12    	10.12.57.254   		1	2
4	10.12.48.12    	10.12.48.254   		10	1
5	10.12.49.12    	10.12.49.254   		10	2
6	10.12.50.12    	10.12.51.254   		10	3
7	10.12.52.12    	10.12.52.254   		11	1
8	10.12.53.12    	10.12.53.254   		11	2
9	10.12.54.12    	10.12.55.254   		11	3
11	10.12.4.12     	10.12.4.254    		3	2
12	10.12.5.12     	10.12.5.254    		3	3
13	10.12.8.12     	10.12.8.254    		2	1
14	10.12.9.12     	10.12.9.254    		2	2
15	10.12.10.12    	10.12.11.254   		2	3
16	10.12.12.12    	10.12.12.254   		4	1
17	10.12.13.12    	10.12.13.254   		4	2
18	10.12.14.12    	10.12.15.254   		4	3
19	10.12.16.12    	10.12.16.254   		5	1
20	10.12.17.12    	10.12.17.254   		5	2
21	10.12.18.12    	10.12.19.254   		5	3
22	10.12.32.12    	10.12.32.254   		6	1
23	10.12.33.12    	10.12.33.254   		6	2
24	10.12.34.12    	10.12.35.254   		6	3
25	10.12.36.12    	10.12.36.254   		7	1
26	10.12.37.12    	10.12.37.254   		7	2
28	10.12.40.12    	10.12.40.254   		8	1
29	10.12.41.12    	10.12.41.254   		8	2
30	10.12.42.12    	10.12.43.254   		8	3
32	10.12.45.12    	10.12.45.254   		9	2
33	10.12.46.12    	10.12.46.254   		9	3
31	10.12.44.12    	10.12.44.254   		9	1
34	10.12.60.12    	10.12.60.254   		12	1
35	10.12.61.12    	10.12.61.254   		12	2
36	10.12.62.12    	10.12.62.254   		12	3
37	10.12.64.12    	10.12.64.254   		13	1
38	10.12.65.12    	10.12.65.254   		13	2
39	10.12.66.12    	10.12.67.254   		13	3
40	10.12.68.12    	10.12.68.254   		14	1
41	10.12.69.12    	10.12.69.254   		14	2
42	10.12.70.12    	10.12.70.254   		14	3
43	10.12.72.12    	10.12.72.254   		15	1
44	10.12.73.12    	10.12.73.254   		15	2
45	10.12.74.12    	10.12.75.254   		15	3
46	10.12.76.12    	10.12.76.254   		16	1
47	10.12.77.12    	10.12.77.254   		16	2
48	10.12.78.12    	10.12.79.254   		16	3
49	10.12.80.12    	10.12.80.254   		17	1
50	10.12.81.12    	10.12.81.254   		17	2
52	10.12.84.12    	10.12.84.254   		18	1
53	10.12.85.12    	10.12.85.254   		18	2
54	10.12.86.12    	10.12.87.254   		18	3
55	10.12.88.12    	10.12.88.254   		19	1
56	10.12.89.12    	10.12.89.254   		19	2
57	10.12.90.12    	10.12.91.254   		19	3
58	10.12.92.12    	10.12.92.254   		20	1
59	10.12.93.12    	10.12.93.254   		20	2
60	10.12.94.12    	10.12.95.254   		20	3
61	10.12.96.12    	10.12.96.254   		21	1
62	10.12.97.12    	10.12.97.254   		21	2
63	10.12.98.12    	10.12.99.254   		21	3
64	10.12.100.12   	10.12.100.254  		22	1
65	10.12.101.12   	10.12.101.254  		22	2
66	10.12.102.12   	10.12.103.254  		22	3
67	10.12.104.12   	10.12.104.254  		23	1
68	10.12.105.12   	10.12.105.254  		23	2
69	10.12.106.12   	10.12.107.254  		23	3
51	10.12.82.12    	10.12.83.254   		17	3
70	10.12.108.12   	10.12.108.254  		24	1
71	10.12.109.12   	10.12.109.254  		24	2
72	10.12.110.12   	10.12.111.254  		24	3
73	10.12.112.12   	10.12.112.254  		25	1
74	10.12.113.12   	10.12.113.254  		25	2
75	10.12.114.12   	10.12.115.254  		25	3
76	10.12.116.12   	10.12.116.254  		26	1
77	10.12.117.12   	10.12.117.254  		26	2
78	10.12.118.12   	10.12.119.254  		26	3
79	10.12.120.12   	10.12.120.254  		27	1
80	10.12.121.12   	10.12.121.254  		27	2
81	10.12.122.12   	10.12.123.254  		27	3
82	10.12.124.12   	10.12.124.254  		28	1
83	10.12.125.12   	10.12.125.254  		28	2
84	10.12.126.12   	10.12.127.254  		28	3
85	10.12.128.12   	10.12.128.254  		29	1
86	10.12.129.12   	10.12.129.254  		29	2
87	10.12.130.12   	10.12.131.254  		29	3
88	10.12.132.12   	10.12.132.254  		30	1
89	10.12.133.12   	10.12.133.254  		30	2
90	10.12.134.12   	10.12.135.254  		30	3
91	10.12.136.12   	10.12.136.254  		31	1
92	10.12.137.12   	10.12.137.254  		31	2
93	10.12.138.12   	10.12.139.254  		31	3
94	10.12.140.12   	10.12.140.254  		32	1
95	10.12.141.12   	10.12.141.254  		32	2
96	10.12.142.12   	10.12.143.254  		32	3
97	10.12.144.12   	10.12.144.254  		33	1
98	10.12.145.12   	10.12.145.254  		33	2
99	10.12.146.12   	10.12.147.254  		33	3
100	10.12.148.12   	10.12.148.254  		34	1
101	10.12.149.12   	10.12.149.254  		34	2
102	10.12.150.12   	10.12.151.254  		34	3
103	10.12.152.12   	10.12.152.254  		35	1
104	10.12.153.12   	10.12.153.254  		35	2
105	10.12.154.12   	10.12.155.254  		35	3
27	10.12.38.12    	10.12.39.254   		7	3
106	10.12.156.12   	10.12.156.254  		36	1
107	10.12.157.12   	10.12.157.254  		36	2
108	10.12.158.12   	10.12.159.254  		36	3
\.


--
-- Name: rangeip_codigo_seq; Type: SEQUENCE SET; Schema: redes; Owner: sati
--

SELECT pg_catalog.setval('rangeip_codigo_seq', 108, true);


--
-- Data for Name: rede; Type: TABLE DATA; Schema: redes; Owner: sati
--

COPY rede (codigo, nome, endereco, loopback, peprincipal, ceprincipal, pebackup, cebackup, observacao, nrocircuito) FROM stdin;
1	ITAJAI	10.12.56.0/22	10.12.56.0/30	10.12.56.4     	10.12.56.5	10.12.56.8	10.12.56.9		TST IAI/IP/00165 256K
3	SEINFO	10.12.6.0/22		               					
4	FPOLISsede	10.12.12.0/22	10.12.12.0/30	10.12.12.4     	10.12.12.5	10.12.12.8	10.12.12.9		
2	FPOLISforo	10.12.8.0/22		               					
5	FPOLISadm	10.12.16.0/22		               					
6	SEDIG	10.12.32.0/22	10.12.32.0/30	10.12.32.4     	10.12.32.5	10.12.32.8	10.12.32.9		
7	ALMOX	10.12.36.0/22	10.12.36.0/30	10.12.36.4     	10.12.36.5	10.12.36.8	10.12.36.9		
8	SASER	10.12.40.0/22	10.12.40.0/30	10.12.40.4     	10.12.40.5	10.12.40.8	10.12.40.9		
9	SAOJOSE	10.12.44.0/22	10.12.44.0/30	10.12.44.4     	10.12.44.5	10.12.44.8	10.12.44.9		
10	JOINVILLE	10.12.48.0/22	10.12.48.0/30	10.12.48.4     	10.12.48.5	10.12.48.8	10.12.48.9		
11	BLUMENAU	10.12.52.0/22	10.12.52.0/30	10.12.52.4     	10.12.52.5	10.12.52.8	10.12.52.9		
12	CRICIUMA	10.12.60.0/22	10.12.60.0/30	10.12.60.4     	10.12.60.5	10.12.60.8	10.12.60.9		
13	LAGES	10.12.64.0/22	10.12.64.0/30	10.12.64.4     	10.12.64.5	10.12.64.8	10.12.64.9		
14	CHAPECO	10.12.68.0/22	10.12.68.0/30	10.12.68.4     	10.12.68.5	10.12.68.8	10.12.68.9		
15	CAMBORIU	10.12.72.0/22	10.12.72.0/30	10.12.72.4     	10.12.72.5	10.12.72.8	10.12.72.9		
16	JARAGUA	10.12.76.0/22	10.12.76.0/30	10.12.76.4     	10.12.76.5	10.12.76.8	10.12.76.9		
17	RIODOSUL	10.12.80.0/22	10.12.80.0/30	10.12.80.4     	10.12.80.5	10.12.80.8	10.12.80.9		
18	BRUSQUE	10.12.84.0/22	10.12.84.0/30	10.12.84.4     	10.12.84.5	10.12.84.8	10.12.84.9		
19	TIMBO	10.12.88.0/22	10.12.88.0/30	10.12.88.4     	10.12.88.5	10.12.88.8	10.12.88.9		
20	INDAIAL	10.12.92.0/22	10.12.92.0/30	10.12.92.4     	10.12.92.5	10.12.92.8	10.12.92.9		
21	SAOBENTO	10.12.96.0/22	10.12.96.0/30	10.12.96.4     	10.12.96.5	10.12.96.8	10.12.96.9		
22	MAFRA	10.12.100.0/22	10.12.100.0/30	10.12.100.4    	10.12.100.5	10.12.100.8	10.12.100.9		
23	CANOINHAS	10.12.104.0/22	10.12.104.0/30	10.12.104.4    	10.12.104.5	10.12.104.8	10.12.104.9		
24	PORTOUNIAO	10.12.108.0/22	10.12.108.0/30	10.12.108.4    	10.12.108.5	10.12.108.8	10.12.108.9		
25	TUBARAO	10.12.112.0/22	10.12.112.0/30	10.12.112.4    	10.12.112.5	10.12.112.8	10.12.112.9		
26	IMBITUBA	10.12.116.0/22	10.12.116.0/30	10.12.116.4    	10.12.116.5	10.12.116.8	10.12.116.9		
27	ARARANGUA	10.12.120.0/22	10.12.120.0/30	10.12.120.4    	10.12.120.5	10.12.120.8	10.12.120.9		
28	CONCORDIA	10.12.124.0/22	10.12.124.0/30	10.12.124.4    	10.12.124.5	10.12.124.8	10.12.124.9		
29	XANXERE	10.12.128.0/22	10.12.128.0/30	10.12.128.4    	10.12.128.5	10.12.128.8	10.12.128.9		
30	JOACABA	10.12.132.0/22	10.12.132.0/30	10.12.132.4    	10.12.132.5	10.12.132.8	10.12.132.9		
31	CURITIBANOS	10.12.136.0/22	10.12.136.0/30	10.12.136.4    	10.12.136.5	10.12.136.8	10.12.136.9		
32	FRAIBURGO	10.12.140.0/22	10.12.140.0/30	10.12.140.4    	10.12.140.5	10.12.140.8	10.12.140.9		
33	VIDEIRA	10.12.144.0/22	10.12.144.0/30	10.12.144.4    	10.12.144.5	10.12.144.8	10.12.144.9		
34	CACADOR	10.12.148.0/22	10.12.148.0/30	10.12.148.4    	10.12.148.5	10.12.148.8	10.12.148.9		
35	SAOMIGUEL	10.12.152.0/22	10.12.152.0/30	10.12.152.4    	10.12.152.5	10.12.152.8	10.12.152.9		
36	PALHOCA	10.12.156.0/22	10.12.156.0/30	10.12.156.4    	10.12.156.5	10.12.156.9	10.12.156.9		
\.


--
-- Name: rede_codigo_seq; Type: SEQUENCE SET; Schema: redes; Owner: sati
--

SELECT pg_catalog.setval('rede_codigo_seq', 36, true);


--
-- Data for Name: rede_municipio; Type: TABLE DATA; Schema: redes; Owner: sati
--

COPY rede_municipio (cod_rede, cod_municipio) FROM stdin;
\.


--
-- Data for Name: segmento; Type: TABLE DATA; Schema: redes; Owner: sati
--

COPY segmento (codigo, extensao, observacao, descricao, nome) FROM stdin;
\.


--
-- Name: segmento_codigo_seq; Type: SEQUENCE SET; Schema: redes; Owner: sati
--

SELECT pg_catalog.setval('segmento_codigo_seq', 14, true);


--
-- Data for Name: servidor; Type: TABLE DATA; Schema: redes; Owner: sati
--

COPY servidor (codigo, nome, descricao, mac, tombo, ip, observacao, cod_categoria, cod_unidade) FROM stdin;
1	ITAJAI	Servidor Itajaí	00-13-21-B4-3A-3A	50746 	10.12.56.20    	\N	1	\N
\.


--
-- Data for Name: tipoconector; Type: TABLE DATA; Schema: redes; Owner: sati
--

COPY tipoconector (codigo, nome) FROM stdin;
1	Fêmea
2	Jack
\.


--
-- Name: tipoconector_codigo_seq; Type: SEQUENCE SET; Schema: redes; Owner: sati
--

SELECT pg_catalog.setval('tipoconector_codigo_seq', 16, true);


--
-- Data for Name: tipomodulo; Type: TABLE DATA; Schema: redes; Owner: sati
--

COPY tipomodulo (codigo, nome) FROM stdin;
2	Caixa de chão
3	Totem
1	Caixa de parede
\.


--
-- Name: tipomodulo_codigo_seq; Type: SEQUENCE SET; Schema: redes; Owner: sati
--

SELECT pg_catalog.setval('tipomodulo_codigo_seq', 18, true);


--
-- Data for Name: tomada; Type: TABLE DATA; Schema: redes; Owner: sati
--

COPY tomada (codigo, nome, descricao, observacao, cod_segmento, tipo) FROM stdin;
28	T16	\N	\N	\N	P
29	T10	\N	\N	\N	P
30	T17	\N	\N	\N	P
31	T02	\N	\N	\N	P
32	T20	\N	\N	\N	P
33	T11	\N	\N	\N	P
34	T19	\N	\N	\N	P
122	T03	\N	\N	\N	P
123	T18	\N	\N	\N	P
246	T21	\N	\N	\N	P
252	T24	\N	\N	\N	P
253	T23	\N	\N	\N	P
260	T22	\N	\N	\N	P
268	T16	\N	\N	\N	P
269	T19	\N	\N	\N	P
270	T18	\N	\N	\N	P
272	T12	\N	\N	\N	P
273	T20	\N	\N	\N	P
274	T23	\N	\N	\N	P
275	T15	\N	\N	\N	P
280	T10	\N	\N	\N	P
282	T09	\N	\N	\N	P
283	T24	\N	\N	\N	P
285	T17	\N	\N	\N	P
286	T13	\N	\N	\N	P
287	T14	\N	\N	\N	P
288	T21	\N	\N	\N	P
289	T22	\N	\N	\N	P
291	T11	\N	\N	\N	P
241	T02	\N		\N	P
243	T09	\N		\N	P
242	T19	\N		\N	P
251	T01	\N		\N	P
247	T02	\N		\N	P
249	T03	\N		\N	P
262	T04	\N		\N	P
258	T05	\N		\N	P
264	T06	\N		\N	P
250	T07	\N		\N	P
259	T08	\N		\N	P
256	T09	\N		\N	P
263	T10	\N		\N	P
265	T11	\N		\N	P
261	T12	\N		\N	P
267	T13	\N		\N	P
245	T14	\N		\N	P
266	T15	\N		\N	P
248	T16	\N		\N	P
244	T17	\N		\N	P
254	T18	\N		\N	P
255	T19	\N		\N	P
284	T01	\N		\N	P
290	T02	\N		\N	P
281	T03	\N		\N	P
278	T04	\N		\N	P
279	T05	\N		\N	P
276	T06	\N		\N	P
277	T07	\N		\N	P
323	T20	\N	Antiga linha de dados	\N	P
293	T11	\N	\N	\N	P
294	T12	\N	\N	\N	P
10	T03	\N	\N	\N	P
11	T11	\N	\N	\N	P
12	T22	\N	\N	\N	P
13	T23	\N	\N	\N	P
14	T16	\N	\N	\N	P
15	T14	\N	\N	\N	P
16	T04	\N	\N	\N	P
17	T15	\N	\N	\N	P
19	T18	\N	\N	\N	P
20	T07	\N	\N	\N	P
21	T05	\N	\N	\N	P
22	T21	\N	\N	\N	P
23	T10	\N	\N	\N	P
24	T17	\N	\N	\N	P
25	T12	\N	\N	\N	P
35	T13	\N	\N	\N	P
36	T21	\N	\N	\N	P
37	T07	\N	\N	\N	P
38	T18	\N	\N	\N	P
39	T04	\N	\N	\N	P
40	T08	\N	\N	\N	P
41	T15	\N	\N	\N	P
42	T01	\N	\N	\N	P
43	T14	\N	\N	\N	P
44	T09	\N	\N	\N	P
45	T22	\N	\N	\N	P
46	T05	\N	\N	\N	P
47	T03	\N	\N	\N	P
48	T06	\N	\N	\N	P
50	T23	\N	\N	\N	P
51	T12	\N	\N	\N	P
52	T20	\N	\N	\N	P
53	T13	\N	\N	\N	P
54	T21	\N	\N	\N	P
55	T07	\N	\N	\N	P
56	T23	\N	\N	\N	P
57	T22	\N	\N	\N	P
58	T19	\N	\N	\N	P
59	T11	\N	\N	\N	P
60	T12	\N	\N	\N	P
61	T18	\N	\N	\N	P
62	T17	\N	\N	\N	P
63	T02	\N	\N	\N	P
64	T04	\N	\N	\N	P
65	T08	\N	\N	\N	P
66	T05	\N	\N	\N	P
67	T24	\N	\N	\N	P
68	T16	\N	\N	\N	P
69	T10	\N	\N	\N	P
70	T01	\N	\N	\N	P
71	T03	\N	\N	\N	P
72	T06	\N	\N	\N	P
73	T09	\N	\N	\N	P
74	T15	\N	\N	\N	P
75	T14	\N	\N	\N	P
76	T19	\N	\N	\N	P
77	T24	\N	\N	\N	P
78	T16	\N	\N	\N	P
79	T12	\N	\N	\N	P
80	T23	\N	\N	\N	P
81	T20	\N	\N	\N	P
82	T22	\N	\N	\N	P
83	T03	\N	\N	\N	P
84	T04	\N	\N	\N	P
85	T08	\N	\N	\N	P
86	T21	\N	\N	\N	P
87	T11	\N	\N	\N	P
88	T07	\N	\N	\N	P
89	T05	\N	\N	\N	P
90	T06	\N	\N	\N	P
295	T14	\N	\N	\N	P
299	T15	\N	\N	\N	P
300	T06	\N	\N	\N	P
301	T23	\N	\N	\N	P
303	T07	\N	\N	\N	P
304	T24	\N	\N	\N	P
305	T21	\N	\N	\N	P
306	T22	\N	\N	\N	P
309	T13	\N	\N	\N	P
310	T04	\N	\N	\N	P
315	T16	\N	\N	\N	P
316	T05	\N	\N	\N	P
318	T14	\N	\N	\N	P
320	T08	\N	\N	\N	P
321	T07	\N	\N	\N	P
322	T04	\N	\N	\N	P
325	T15	\N	\N	\N	P
326	T16	\N	\N	\N	P
327	T24	\N	\N	\N	P
330	T21	\N	\N	\N	P
331	T13	\N	\N	\N	P
332	T09	\N	\N	\N	P
333	T23	\N	\N	\N	P
334	T03	\N	\N	\N	P
335	T22	\N	\N	\N	P
336	T12	\N	\N	\N	P
339	T06	\N	\N	\N	P
91	T10	\N	\N	\N	P
92	T09	\N	\N	\N	P
93	T17	\N	\N	\N	P
94	T15	\N	\N	\N	P
95	T18	\N	\N	\N	P
96	T01	\N	\N	\N	P
97	T02	\N	\N	\N	P
98	T14	\N	\N	\N	P
99	T13	\N	\N	\N	P
100	T06	\N	\N	\N	P
101	T05	\N	\N	\N	P
102	T17	\N	\N	\N	P
103	T23	\N	\N	\N	P
104	T14	\N	\N	\N	P
105	T20	\N	\N	\N	P
106	T01	\N	\N	\N	P
107	T08	\N	\N	\N	P
108	T09	\N	\N	\N	P
109	T16	\N	\N	\N	P
110	T11	\N	\N	\N	P
111	T13	\N	\N	\N	P
112	T22	\N	\N	\N	P
113	T10	\N	\N	\N	P
114	T24	\N	\N	\N	P
115	T02	\N	\N	\N	P
116	T12	\N	\N	\N	P
117	T21	\N	\N	\N	P
118	T19	\N	\N	\N	P
119	T04	\N	\N	\N	P
120	T15	\N	\N	\N	P
121	T07	\N	\N	\N	P
2	T09	\N	\N	\N	P
18	T24	\N		\N	P
49	T24	\N		\N	P
3	T19	\N	\N	\N	P
4	T06	\N	\N	\N	P
6	T08	\N	\N	\N	P
7	T20	\N	\N	\N	P
8	T13	\N	\N	\N	P
9	T02	\N	\N	\N	P
124	T11	\N	\N	\N	P
125	T20	\N	\N	\N	P
126	T13	\N	\N	\N	P
127	T15	\N	\N	\N	P
128	T08	\N	\N	\N	P
129	T01	\N	\N	\N	P
130	T03	\N	\N	\N	P
131	T17	\N	\N	\N	P
132	T06	\N	\N	\N	P
133	T21	\N	\N	\N	P
134	T22	\N	\N	\N	P
135	T09	\N	\N	\N	P
136	T14	\N	\N	\N	P
137	T02	\N	\N	\N	P
138	T24	\N	\N	\N	P
139	T07	\N	\N	\N	P
140	T10	\N	\N	\N	P
141	T18	\N	\N	\N	P
142	T05	\N	\N	\N	P
143	T16	\N	\N	\N	P
144	T23	\N	\N	\N	P
145	T19	\N	\N	\N	P
146	T04	\N	\N	\N	P
147	T12	\N	\N	\N	P
148	T21	\N	\N	\N	P
149	T16	\N	\N	\N	P
150	T14	\N	\N	\N	P
151	T20	\N	\N	\N	P
152	T24	\N	\N	\N	P
153	T07	\N	\N	\N	P
154	T11	\N	\N	\N	P
155	T23	\N	\N	\N	P
156	T15	\N	\N	\N	P
157	T06	\N	\N	\N	P
158	T05	\N	\N	\N	P
159	T08	\N	\N	\N	P
160	T13	\N	\N	\N	P
161	T02	\N	\N	\N	P
162	T22	\N	\N	\N	P
163	T12	\N	\N	\N	P
164	T03	\N	\N	\N	P
165	T19	\N	\N	\N	P
166	T10	\N	\N	\N	P
167	T09	\N	\N	\N	P
168	T18	\N	\N	\N	P
169	T01	\N	\N	\N	P
170	T04	\N	\N	\N	P
171	T17	\N	\N	\N	P
172	T07	\N	\N	\N	P
173	T09	\N	\N	\N	P
174	T02	\N	\N	\N	P
175	T06	\N	\N	\N	P
176	T22	\N	\N	\N	P
177	T04	\N	\N	\N	P
178	T20	\N	\N	\N	P
179	T21	\N	\N	\N	P
180	T08	\N	\N	\N	P
181	T14	\N	\N	\N	P
182	T18	\N	\N	\N	P
183	T13	\N	\N	\N	P
184	T03	\N	\N	\N	P
185	T01	\N	\N	\N	P
186	T05	\N	\N	\N	P
187	T10	\N	\N	\N	P
188	T15	\N	\N	\N	P
189	T12	\N	\N	\N	P
190	T23	\N	\N	\N	P
191	T11	\N	\N	\N	P
192	T19	\N	\N	\N	P
193	T16	\N	\N	\N	P
194	T17	\N	\N	\N	P
195	T24	\N	\N	\N	P
196	T07	\N	\N	\N	P
197	T06	\N	\N	\N	P
198	T04	\N	\N	\N	P
199	T09	\N	\N	\N	P
200	T23	\N	\N	\N	P
201	T20	\N	\N	\N	P
202	T15	\N	\N	\N	P
203	T08	\N	\N	\N	P
204	T11	\N	\N	\N	P
205	T05	\N	\N	\N	P
206	T14	\N	\N	\N	P
207	T18	\N	\N	\N	P
208	T21	\N	\N	\N	P
209	T03	\N	\N	\N	P
211	T13	\N	\N	\N	P
212	T22	\N	\N	\N	P
213	T17	\N	\N	\N	P
214	T16	\N	\N	\N	P
215	T02	\N	\N	\N	P
216	T10	\N	\N	\N	P
217	T24	\N	\N	\N	P
218	T12	\N	\N	\N	P
219	T19	\N	\N	\N	P
223	T24	\N	\N	\N	P
228	T21	\N	\N	\N	P
231	T23	\N	\N	\N	P
240	T22	\N	\N	\N	P
239	T04	\N		\N	P
234	T05	\N		\N	P
220	T06	\N		\N	P
225	T07	\N		\N	P
238	T08	\N		\N	P
221	T10	\N		\N	P
226	T11	\N		\N	P
233	T12	\N		\N	P
232	T13	\N		\N	P
236	T14	\N		\N	P
235	T15	\N		\N	P
230	T16	\N		\N	P
237	T17	\N		\N	P
224	T18	\N		\N	P
227	T20	\N		\N	P
210	T01	\N		\N	P
222	T03	\N		\N	P
298	T18	\N	A ser desativado	\N	P
312	T17	\N	A ser desativado	\N	P
308	T19	\N	A ser desativado	\N	P
296	T20	\N	A ser desativado	\N	P
317	T01	\N	A ser desativado	\N	P
337	T02	\N	A ser desativado	\N	P
311	T09	\N	A ser desativado	\N	P
302	T10	\N	A ser desativado	\N	P
324	T11	\N	A ser desativado	\N	P
329	T19	\N	A ser desativado	\N	P
297	T02	\N	A ser desativado	\N	P
313	T03	\N	A ser desativado	\N	P
314	T05	\N	A ser desativado	\N	P
338	T18	\N	A ser desativado	\N	P
292	T08	\N	A ser desativado	\N	P
307	T01	\N	A ser desativado	\N	P
328	T17	\N	A ser desativado	\N	P
319	T10	\N	Linha telefone da OAB	\N	P
5	T01	\N		\N	P
348	T19	Tomada 19 do panel P1	\N	\N	P
352	T09	Tomada 9 do panel P1	\N	\N	P
360	T10	Tomada 10 do panel P1	\N	\N	P
365	T11	Tomada 11 do panel P1	\N	\N	P
397	T43	Tomada 43 do panel P2	\N	\N	P
398	T44	Tomada 44 do panel P2	\N	\N	P
401	T47	Tomada 47 do panel P2	\N	\N	P
405	T42	Tomada 42 do panel P2	\N	\N	P
406	T46	Tomada 46 do panel P2	\N	\N	P
416	T45	Tomada 45 do panel P2	\N	\N	P
418	T48	Tomada 48 do panel P2	\N	\N	P
436	T24	Tomada 24 do panel V1	\N	\N	P
437	T04	Tomada 4 do panel V1	\N	\N	P
438	T17	Tomada 17 do panel V1	\N	\N	P
439	T02	Tomada 2 do panel V1	\N	\N	P
440	T19	Tomada 19 do panel V1	\N	\N	P
441	T06	Tomada 6 do panel V1	\N	\N	P
442	T09	Tomada 9 do panel V1	\N	\N	P
443	T08	Tomada 8 do panel V1	\N	\N	P
444	T22	Tomada 22 do panel V1	\N	\N	P
445	T21	Tomada 21 do panel V1	\N	\N	P
446	T07	Tomada 7 do panel V1	\N	\N	P
447	T16	Tomada 16 do panel V1	\N	\N	P
448	T01	Tomada 1 do panel V1	\N	\N	P
449	T20	Tomada 20 do panel V1	\N	\N	P
450	T05	Tomada 5 do panel V1	\N	\N	P
451	T11	Tomada 11 do panel V1	\N	\N	P
452	T03	Tomada 3 do panel V1	\N	\N	P
453	T18	Tomada 18 do panel V1	\N	\N	P
454	T13	Tomada 13 do panel V1	\N	\N	P
455	T23	Tomada 23 do panel V1	\N	\N	P
456	T12	Tomada 12 do panel V1	\N	\N	P
457	T14	Tomada 14 do panel V1	\N	\N	P
458	T15	Tomada 15 do panel V1	\N	\N	P
459	T10	Tomada 10 do panel V1	\N	\N	P
460	T05	Tomada 5 do panel V2	\N	\N	P
344	T04	Tomada 4 do panel P1	V2-08 (pto 04)	\N	P
347	T05	Tomada 5 do panel P1	A1-19 (pto 05)	\N	P
368	T06	Tomada 6 do panel P1	(desativado)	\N	P
359	T08	Tomada 8 do panel P1	A1-15 (pto 08)	\N	P
380	T12	Tomada 12 do panel P1	A2-06 (pto 12)	\N	P
370	T07	Tomada 7 do panel P1	V1-08 (pto 07)	\N	P
374	T13	Tomada 13 do panel P1	V1-07 (pto 13)	\N	P
355	T15	Tomada 15 do panel P1	A1-20 (pto 15)	\N	P
351	T16	Tomada 16 do panel P1	(pto 16)	\N	P
383	T18	Tomada 18 do panel P1	A2-03 (pto 18)	\N	P
363	T21	Tomada 21 do panel P1	A2-08 (pto 21)	\N	P
369	T22	Tomada 22 do panel P1	A3-06 (pto 22)	\N	P
386	T23	Tomada 23 do panel P1	A2-14 (pto 23)	\N	P
357	T24	Tomada 24 do panel P1	V1-03 (pto 24)	\N	P
341	T26	Tomada 26 do panel P1	A) A3-02\r\nB) A4-03\r\n(pto 26)	\N	P
353	T27	Tomada 27 do panel P1	A1-18 (pto 27)	\N	P
385	T29	Tomada 29 do panel P1	V2-09 (pto 29)	\N	P
381	T30	Tomada 30 do panel P1	A1-05 (pto 30)	\N	P
378	T31	Tomada 31 do panel P1	V2-07 (pto 31)	\N	P
371	T33	Tomada 33 do panel P1	A2-15 (pto 33)	\N	P
384	T34	Tomada 34 do panel P1	V1-10 (pto 34)	\N	P
367	T36	Tomada 36 do panel P1	A2-05 (pto 36)	\N	P
354	T37	Tomada 37 do panel P1	A2-04 (pto 37)	\N	P
350	T38	Tomada 38 do panel P1	A1-12 (pto 38)	\N	P
387	T39	Tomada 39 do panel P1	(pto 39)	\N	P
379	T40	Tomada 40 do panel P1	A2-18 (pto 40)	\N	P
346	T42	Tomada 42 do panel P1	A3-11 (pto 42) (patch cable 43)	\N	P
364	T43	Tomada 43 do panel P1	(pto 43)	\N	P
349	T44	Tomada 44 do panel P1	A3-05 (pto 44)	\N	P
375	T46	Tomada 46 do panel P1	V1-06 (pto 46)	\N	P
373	T47	Tomada 47 do panel P1	A1-10 (pto 47)	\N	P
377	T48	Tomada 48 do panel P1	A1-21 (pto 48)	\N	P
425	T02	Tomada 2 do panel P2	A1-06 (pto 50)	\N	P
393	T03	Tomada 3 do panel P2	V2-05 (pto 51)	\N	P
432	T04	Tomada 4 do panel P2	A4-01 (pto 52)	\N	P
424	T06	Tomada 6 do panel P2	A3-07 (pto 54)	\N	P
391	T07	Tomada 7 do panel P2	A1-13 (pto 55)	\N	P
422	T09	Tomada 9 do panel P2	A1-11 (pto 57)	\N	P
421	T11	Tomada 11 do panel P2	A1-14 (pto 59)	\N	P
388	T10	Tomada 10 do panel P2	V2-04 (pto 58)	\N	P
415	T12	Tomada 12 do panel P2	(pto 60)	\N	P
430	T13	Tomada 13 do panel P2	A2-20 (pto 61)	\N	P
408	T15	Tomada 15 do panel P2	A2-17 (pto 63)	\N	P
399	T16	Tomada 16 do panel P2	A) A2-10\r\nB) A3-10\r\n(pto 64)	\N	P
426	T18	Tomada 18 do panel P2	A3-08 (pto 66)	\N	P
427	T19	Tomada 19 do panel P2	A2-07 (pto 67)	\N	P
417	T20	Tomada 20 do panel P2	(pto 68)	\N	P
404	T21	Tomada 21 do panel P2	A2-16 (pto 69)	\N	P
429	T23	Tomada 23 do panel P2	(pto 70)	\N	P
410	T24	Tomada 24 do panel P2	V2-01 (pto 72)	\N	P
392	T25	Tomada 25 do panel P2	A1-09 (pto 73)	\N	P
435	T27	Tomada 27 do panel P2	A3-04 (pto 75)	\N	P
396	T28	Tomada 28 do panel P2	V2-06 (pto 76)	\N	P
402	T29	Tomada 29 do panel P2	A1-03 (pto 77)	\N	P
412	T30	Tomada 30 do panel P2	(pto 78)	\N	P
407	T32	Tomada 32 do panel P2	V2-11 (pto 79)	\N	P
413	T33	Tomada 33 do panel P2	A1-02 (pto 81)	\N	P
411	T34	Tomada 34 do panel P2	(pto 81?)	\N	P
389	T35	Tomada 35 do panel P2	A1-08 (pto 82)	\N	P
400	T37	Tomada 37 do panel P2	(pto 84?)	\N	P
428	T38	Tomada 38 do panel P2	(pto 87)	\N	P
414	T39	Tomada 39 do panel P2	(pto 86)	\N	P
390	T40	Tomada 40 do panel P2	A3-09 (pto 89)	\N	P
461	T14	Tomada 14 do panel V2	\N	\N	P
462	T09	Tomada 9 do panel V2	\N	\N	P
463	T22	Tomada 22 do panel V2	\N	\N	P
464	T37	Tomada 37 do panel V2	\N	\N	P
465	T25	Tomada 25 do panel V2	\N	\N	P
466	T23	Tomada 23 do panel V2	\N	\N	P
467	T41	Tomada 41 do panel V2	\N	\N	P
468	T01	Tomada 1 do panel V2	\N	\N	P
469	T24	Tomada 24 do panel V2	\N	\N	P
470	T28	Tomada 28 do panel V2	\N	\N	P
471	T13	Tomada 13 do panel V2	\N	\N	P
472	T39	Tomada 39 do panel V2	\N	\N	P
473	T46	Tomada 46 do panel V2	\N	\N	P
474	T32	Tomada 32 do panel V2	\N	\N	P
475	T20	Tomada 20 do panel V2	\N	\N	P
476	T45	Tomada 45 do panel V2	\N	\N	P
477	T36	Tomada 36 do panel V2	\N	\N	P
478	T29	Tomada 29 do panel V2	\N	\N	P
479	T31	Tomada 31 do panel V2	\N	\N	P
480	T21	Tomada 21 do panel V2	\N	\N	P
481	T11	Tomada 11 do panel V2	\N	\N	P
482	T27	Tomada 27 do panel V2	\N	\N	P
483	T15	Tomada 15 do panel V2	\N	\N	P
484	T12	Tomada 12 do panel V2	\N	\N	P
485	T06	Tomada 6 do panel V2	\N	\N	P
486	T16	Tomada 16 do panel V2	\N	\N	P
487	T47	Tomada 47 do panel V2	\N	\N	P
488	T44	Tomada 44 do panel V2	\N	\N	P
489	T18	Tomada 18 do panel V2	\N	\N	P
490	T07	Tomada 7 do panel V2	\N	\N	P
491	T43	Tomada 43 do panel V2	\N	\N	P
492	T34	Tomada 34 do panel V2	\N	\N	P
493	T35	Tomada 35 do panel V2	\N	\N	P
494	T38	Tomada 38 do panel V2	\N	\N	P
495	T10	Tomada 10 do panel V2	\N	\N	P
496	T02	Tomada 2 do panel V2	\N	\N	P
497	T48	Tomada 48 do panel V2	\N	\N	P
498	T40	Tomada 40 do panel V2	\N	\N	P
499	T08	Tomada 8 do panel V2	\N	\N	P
500	T03	Tomada 3 do panel V2	\N	\N	P
501	T26	Tomada 26 do panel V2	\N	\N	P
502	T17	Tomada 17 do panel V2	\N	\N	P
503	T30	Tomada 30 do panel V2	\N	\N	P
504	T42	Tomada 42 do panel V2	\N	\N	P
505	T33	Tomada 33 do panel V2	\N	\N	P
506	T04	Tomada 4 do panel V2	\N	\N	P
507	T19	Tomada 19 do panel V2	\N	\N	P
508	T07	Tomada 7 do panel V2	\N	\N	P
509	T06	Tomada 6 do panel V2	\N	\N	P
510	T21	Tomada 21 do panel V2	\N	\N	P
511	T24	Tomada 24 do panel V2	\N	\N	P
512	T04	Tomada 4 do panel V2	\N	\N	P
513	T11	Tomada 11 do panel V2	\N	\N	P
514	T02	Tomada 2 do panel V2	\N	\N	P
515	T10	Tomada 10 do panel V2	\N	\N	P
516	T08	Tomada 8 do panel V2	\N	\N	P
517	T17	Tomada 17 do panel V2	\N	\N	P
518	T14	Tomada 14 do panel V2	\N	\N	P
519	T05	Tomada 5 do panel V2	\N	\N	P
520	T01	Tomada 1 do panel V2	\N	\N	P
521	T16	Tomada 16 do panel V2	\N	\N	P
522	T03	Tomada 3 do panel V2	\N	\N	P
523	T18	Tomada 18 do panel V2	\N	\N	P
524	T09	Tomada 9 do panel V2	\N	\N	P
525	T19	Tomada 19 do panel V2	\N	\N	P
526	T23	Tomada 23 do panel V2	\N	\N	P
527	T22	Tomada 22 do panel V2	\N	\N	P
528	T12	Tomada 12 do panel V2	\N	\N	P
529	T13	Tomada 13 do panel V2	\N	\N	P
530	T20	Tomada 20 do panel V2	\N	\N	P
531	T15	Tomada 15 do panel V2	\N	\N	P
382	T01	Tomada 1 do panel P1	A1-23 (pto 01)	\N	P
366	T02	Tomada 2 do panel P1	A1-22 (pto 02)	\N	P
362	T03	Tomada 3 do panel P1	A1-04 (pto 03)	\N	P
345	T14	Tomada 14 do panel P1	A) A3-12\r\nB) A2-09\r\n(pto 14)	\N	P
340	T17	Tomada 17 do panel P1	(pto 19)	\N	P
342	T20	Tomada 20 do panel P1	A3-03 (pto 20)	\N	P
376	T25	Tomada 25 do panel P1	V1-05 (pto 25)	\N	P
356	T28	Tomada 28 do panel P1	V2-08 (pto 28)	\N	P
372	T32	Tomada 32 do panel P1	A1-16 (pto 32)	\N	P
361	T35	Tomada 35 do panel P1	A2-13 (pto 35)	\N	P
343	T41	Tomada 41 do panel P1	V1-02 (pto 41)	\N	P
358	T45	Tomada 45 do panel P1	A2-19 (pto 45)	\N	P
395	T01	Tomada 1 do panel P2	(pto 49)	\N	P
423	T05	Tomada 5 do panel P2	V1-04 (pto 53)	\N	P
434	T08	Tomada 8 do panel P2	? (algum voice) (pto 56)	\N	P
409	T14	Tomada 14 do panel P2	A1-17 (pto 62)	\N	P
420	T17	Tomada 17 do panel P2	V1-01 (pto 65)	\N	P
403	T22	Tomada 22 do panel P2	A1-07 (pto 71)	\N	P
419	T26	Tomada 26 do panel P2	A) A2-22\r\nB) A1-01\r\n(pto 74)	\N	P
394	T31	Tomada 31 do panel P2	V2-03 (pto 80)	\N	P
433	T36	Tomada 36 do panel P2	V2-12 (pto 83) (copa)	\N	P
431	T41	Tomada 41 do panel P2	A) A4-02\r\nB) A2-21\r\n(pto 88)	\N	P
533	P02-T01	Mesa de testes		\N	R
534	P02-T16	Micro TI	Ponto dividido - micro do TI no 16A\nO 16B é para equipamentos em teste	\N	R
535	Lab01	Fone TI	A outra ponta do segmento não foi crimpada. O cabo segue até o rack com jack RJ-45. No momento está ativado em ramal telefônico.	\N	R
536	P08-T01	Telefone Norival		\N	R
537	P08-T02	Brother MFC-8820D		\N	R
538	P08-T03	Microcomputador Norival		\N	R
539	P08-T04	Samsung ML-4550		\N	R
540	P08-T07	Microcomputador Elias		\N	R
541	P08-T08	Desligado: ao lado do P08-T07		\N	R
542	P08-T09	Microcomputador Moacir		\N	R
543	P08-T10	Desligado: no totem do P08-T09		\N	R
544	P08-T05	Microcomputador Eliete		\N	R
545	P08-T06	Telefone Eliete		\N	R
546	P08-T11	Telefone diretor		\N	R
547	P08-T12	Microcomputador diretor		\N	R
549	P08-T22			\N	R
550	P08-T19			\N	R
551	P08-T20			\N	R
552	P08-T13			\N	R
553	P08-T14			\N	R
554	P08-T15			\N	R
555	P08-T16			\N	R
556	P08-T17			\N	R
271	T08	\N	Ramal do alarme	\N	P
229	T01	\N		\N	P
557	P08-T18		Fone Central de Mandados	\N	R
257	T20	\N	Ligada para os agentes de segurança	\N	P
577	T20	\N	\N	\N	P
560	T03	\N	\N	\N	P
566	T09	\N	\N	\N	P
563	T06	\N	\N	\N	P
567	T10	\N	\N	\N	P
578	T21	\N	\N	\N	P
564	T07	\N	\N	\N	P
559	T02	\N	\N	\N	P
572	T15	\N	\N	\N	P
576	T19	\N	\N	\N	P
569	T12	\N	\N	\N	P
568	T11	\N	\N	\N	P
561	T04	\N	\N	\N	P
579	T22	\N	\N	\N	P
570	T13	\N	\N	\N	P
562	T05	\N	\N	\N	P
580	T23	\N	\N	\N	P
575	T18	\N	\N	\N	P
571	T14	\N	\N	\N	P
574	T17	\N	\N	\N	P
581	T24	\N	\N	\N	P
565	T08	\N	\N	\N	P
573	T16	\N	\N	\N	P
548	P08-T21	Tomada P08-T21 do módulo MCDM01		\N	R
558	T01	\N		\N	P
532	P02-T18	Servidor de rede		\N	R
\.


--
-- Name: tomada_codigo_seq; Type: SEQUENCE SET; Schema: redes; Owner: sati
--

SELECT pg_catalog.setval('tomada_codigo_seq', 596, true);


--
-- Data for Name: tomadapanel; Type: TABLE DATA; Schema: redes; Owner: sati
--

COPY tomadapanel (codigo, cod_panel, ramal) FROM stdin;
2	3	\N
3	3	\N
4	3	\N
6	3	\N
7	3	\N
8	3	\N
9	3	\N
10	3	\N
11	3	\N
12	3	\N
13	3	\N
14	3	\N
15	3	\N
16	3	\N
17	3	\N
18	3	\N
19	3	\N
20	3	\N
21	3	\N
22	3	\N
23	3	\N
24	3	\N
25	3	\N
28	6	\N
29	6	\N
30	6	\N
31	6	\N
32	6	\N
33	6	\N
34	6	\N
35	6	\N
36	6	\N
37	6	\N
38	6	\N
39	6	\N
40	6	\N
41	6	\N
42	6	\N
43	6	\N
44	6	\N
45	6	\N
46	6	\N
47	6	\N
48	6	\N
49	6	\N
50	6	\N
51	6	\N
52	7	\N
53	7	\N
54	7	\N
55	7	\N
56	7	\N
57	7	\N
58	7	\N
59	7	\N
60	7	\N
61	7	\N
62	7	\N
63	7	\N
64	7	\N
65	7	\N
66	7	\N
67	7	\N
68	7	\N
69	7	\N
70	7	\N
71	7	\N
72	7	\N
73	7	\N
74	7	\N
75	7	\N
76	8	\N
77	8	\N
78	8	\N
79	8	\N
80	8	\N
81	8	\N
82	8	\N
83	8	\N
84	8	\N
85	8	\N
86	8	\N
87	8	\N
88	8	\N
89	8	\N
90	8	\N
91	8	\N
92	8	\N
93	8	\N
94	8	\N
95	8	\N
96	8	\N
97	8	\N
98	8	\N
99	8	\N
100	9	\N
101	9	\N
102	9	\N
103	9	\N
104	9	\N
105	9	\N
106	9	\N
107	9	\N
108	9	\N
109	9	\N
110	9	\N
111	9	\N
112	9	\N
113	9	\N
114	9	\N
115	9	\N
116	9	\N
117	9	\N
118	9	\N
119	9	\N
120	9	\N
121	9	\N
122	9	\N
123	9	\N
124	10	\N
125	10	\N
126	10	\N
127	10	\N
128	10	\N
129	10	\N
130	10	\N
131	10	\N
132	10	\N
133	10	\N
134	10	\N
135	10	\N
136	10	\N
137	10	\N
138	10	\N
139	10	\N
140	10	\N
141	10	\N
142	10	\N
143	10	\N
144	10	\N
145	10	\N
146	10	\N
147	10	\N
148	11	\N
149	11	\N
150	11	\N
151	11	\N
152	11	\N
153	11	\N
154	11	\N
155	11	\N
156	11	\N
157	11	\N
158	11	\N
159	11	\N
160	11	\N
161	11	\N
162	11	\N
163	11	\N
164	11	\N
165	11	\N
166	11	\N
167	11	\N
168	11	\N
169	11	\N
170	11	\N
171	11	\N
172	12	\N
173	12	\N
174	12	\N
175	12	\N
176	12	\N
177	12	\N
178	12	\N
179	12	\N
180	12	\N
181	12	\N
182	12	\N
183	12	\N
184	12	\N
185	12	\N
186	12	\N
187	12	\N
188	12	\N
189	12	\N
190	12	\N
191	12	\N
192	12	\N
193	12	\N
194	12	\N
195	12	\N
196	13	\N
197	13	\N
198	13	\N
199	13	\N
200	13	\N
201	13	\N
202	13	\N
203	13	\N
204	13	\N
205	13	\N
206	13	\N
207	13	\N
208	13	\N
209	13	\N
210	13	\N
211	13	\N
212	13	\N
213	13	\N
214	13	\N
215	13	\N
216	13	\N
217	13	\N
218	13	\N
219	13	\N
223	14	\N
228	14	\N
231	14	\N
240	14	\N
246	15	\N
252	15	\N
253	15	\N
260	15	\N
268	16	\N
269	16	\N
270	16	\N
272	16	\N
273	16	\N
274	16	\N
275	16	\N
280	16	\N
282	16	\N
283	16	\N
285	16	\N
286	16	\N
287	16	\N
288	16	\N
289	16	\N
291	16	\N
293	17	\N
294	17	\N
295	17	\N
299	17	\N
300	17	\N
301	17	\N
303	17	\N
304	17	\N
305	17	\N
306	17	\N
309	17	\N
310	17	\N
315	17	\N
316	18	\N
318	18	\N
320	18	\N
321	18	\N
322	18	\N
325	18	\N
326	18	\N
327	18	\N
330	18	\N
331	18	\N
332	18	\N
333	18	\N
334	18	\N
335	18	\N
336	18	\N
339	18	\N
229	14	1200    
241	14	1201    
222	14	1202    
239	14	1203    
234	14	1204    
220	14	1205    
225	14	1206    
238	14	1207    
243	14	1208    
221	14	1209    
226	14	1210    
233	14	1220    
232	14	1221    
236	14	1222    
235	14	1223    
242	14	1227    
227	14	1228    
230	14	1224    
237	14	1225    
224	14	1226    
251	15	1229    
247	15	1230    
249	15	1240    
262	15	1241    
258	15	1242    
264	15	1243    
250	15	1244    
259	15	1245    
256	15	1246    
263	15	1247    
265	15	1296    
261	15	1297    
267	15	1298    
245	15	1299    
266	15	1239    
248	15	1248    
244	15	1249    
254	15	1250    
255	15	1294    
257	15	1295    
284	16	1251    
290	16	1252    
281	16	1253    
278	16	1254    
279	16	1255    
276	16	1256    
277	16	1257    
271	16	1258    
312	17	33446719
298	17	33446719
308	17	33442915
296	17	33442915
317	18	33484111
337	18	33484111
311	17	33481837
302	17	33481837
324	18	33484102
329	18	33481837
319	18	33481845
297	17	33480015
313	17	33480015
314	17	33480015
338	18	33480015
292	17	33481837
307	17	33480015
328	18	33442915
323	18	        
341	19	        
343	19	        
348	19	\N
352	19	\N
360	19	\N
365	19	\N
397	20	\N
398	20	\N
401	20	\N
405	20	\N
406	20	\N
416	20	\N
418	20	\N
436	21	\N
437	21	\N
438	21	\N
439	21	\N
440	21	\N
441	21	\N
442	21	\N
443	21	\N
444	21	\N
445	21	\N
446	21	\N
447	21	\N
448	21	\N
449	21	\N
450	21	\N
451	21	\N
452	21	\N
453	21	\N
454	21	\N
455	21	\N
456	21	\N
457	21	\N
458	21	\N
459	21	\N
508	23	\N
509	23	\N
510	23	\N
511	23	\N
512	23	\N
513	23	\N
514	23	\N
515	23	\N
516	23	\N
517	23	\N
518	23	\N
519	23	\N
520	23	\N
521	23	\N
522	23	\N
523	23	\N
524	23	\N
525	23	\N
526	23	\N
527	23	\N
528	23	\N
382	19	        
366	19	        
362	19	        
344	19	        
347	19	        
370	19	        
359	19	        
368	19	        
380	19	        
374	19	        
345	19	        
355	19	        
351	19	        
383	19	        
363	19	        
369	19	        
386	19	        
357	19	        
376	19	        
353	19	        
356	19	        
385	19	        
381	19	        
378	19	        
372	19	        
371	19	        
384	19	        
361	19	        
367	19	        
354	19	        
350	19	        
387	19	        
379	19	        
346	19	        
364	19	        
349	19	        
358	19	        
375	19	        
373	19	        
377	19	        
395	20	        
425	20	        
393	20	        
432	20	        
423	20	        
424	20	        
391	20	        
434	20	        
422	20	        
388	20	        
421	20	        
415	20	        
430	20	        
409	20	        
408	20	        
399	20	        
420	20	        
426	20	        
427	20	        
417	20	        
404	20	        
403	20	        
429	20	        
410	20	        
392	20	        
419	20	        
435	20	        
396	20	        
402	20	        
412	20	        
394	20	        
407	20	        
413	20	        
411	20	        
389	20	        
433	20	        
400	20	        
428	20	        
414	20	        
390	20	        
431	20	        
529	23	\N
530	23	\N
531	23	\N
340	19	        
342	19	        
5	3	\N
577	24	\N
560	24	\N
566	24	\N
563	24	\N
567	24	\N
578	24	\N
564	24	\N
559	24	\N
572	24	\N
576	24	\N
569	24	\N
568	24	\N
561	24	\N
579	24	\N
570	24	\N
562	24	\N
580	24	\N
575	24	\N
571	24	\N
574	24	\N
581	24	\N
565	24	\N
573	24	\N
558	24	        
\.


--
-- Data for Name: tomadaremota; Type: TABLE DATA; Schema: redes; Owner: sati
--

COPY tomadaremota (codigo, cod_tipoconector, cod_modulo) FROM stdin;
532	1	4
533	1	3
534	1	1
535	1	1
536	1	5
537	1	5
538	1	6
539	1	6
540	1	7
541	1	7
542	1	8
543	1	8
544	1	9
545	1	9
546	1	10
547	1	10
548	1	11
549	1	11
550	1	12
551	1	12
552	1	13
553	1	13
554	1	14
555	1	14
556	1	15
557	1	15
\.


SET search_path = viagem, pg_catalog;

--
-- Data for Name: eventoreqviagem; Type: TABLE DATA; Schema: viagem; Owner: sati
--

COPY eventoreqviagem (codigo, dataevento, cod_tipoeventoreqviagem, cod_viagem, observacao) FROM stdin;
1	2008-10-20	1	1	Registrado no sistema SUP sob número 28038
2	2008-10-20	1	3	Registrado no sistema SUP sob número 28043
3	2008-11-13	1	2	Registrado no sistema SUP sob número 30880
4	2008-11-18	1	4	Registrado no sistema SUP sob número 30881
5	2008-11-19	1	5	Registrado no sistema SUP sob número 31198
6	2008-11-20	1	6	Registrado no sistema SUP sob número 31332
7	2008-11-28	1	7	Registrado no sistema SUP sob número 32114
8	2008-12-17	1	9	Registrado no sistema SUP sob número 34001
9	2008-12-17	1	8	Registrado no sistema SUP sob número 33975
10	2009-01-13	1	10	Registrado no sistema SUP sob número 688
11	2009-03-23	1	12	Registrado no sistema SUP sob número 5971
12	2009-03-31	1	14	Registrado no sistema SUP sob número 6692
13	2009-04-02	1	15	Registrado no sistema SUP sob número 6915
14	2009-04-13	1	18	Registrado no sistema SUP sob número 8294
15	2009-04-17	1	19	Registrado no sistema SUP sob número 8138
16	2009-05-13	1	24	Registrado no sistema SUP sob número 10224
17	2009-05-26	1	25	Registrado no sistema SUP sob número 11297
18	2009-06-08	1	26	Registrado no sistema SUP sob número 12460
19	2009-06-16	1	27	Registrado no sistema SUP sob número 13040
20	2009-06-23	1	28	Registrado no sistema SUP sob número 13576
21	2009-06-30	1	29	Registrado no sistema SUP sob número 14029
22	2009-08-12	1	30	Registrado no sistema SUP sob número 17324
23	2009-08-12	1	31	Registrado no sistema SUP sob número 17326
24	2009-08-26	1	32	Registrado no sistema SUP sob número 18222
25	2009-09-21	1	34	Registrado no sistema SUP sob número 20180
26	2009-09-29	1	35	Registrado no sistema SUP sob número 20721
27	2009-09-29	1	36	Registrado no sistema SUP sob número 20723
28	2009-10-26	1	37	Registrado no sistema SUP sob número 22566
29	2009-11-11	1	38	Registrado no sistema SUP sob número 23622
30	2009-11-11	1	40	Registrado no sistema SUP sob número 23627
31	2009-11-11	1	39	Registrado no sistema SUP sob número 23624
32	2009-12-01	1	41	Registrado no sistema PROAD sob número 40966
33	2009-12-03	1	42	Registrado no sistema PROAD sob número 41150
34	2009-12-18	1	43	Registrado no sistema PROAD sob número 41836
37	2010-02-22	1	48	Registrado no sistema AASEFIN
35	2010-01-13	1	44	Registrado no sistema AASEFIN
36	2010-01-20	1	46	Registrado no sistema AASEFIN
66	2010-04-22	3	58	
39	2010-02-25	1	47	Registrado no sistema AASEFIN
40	2010-03-01	1	50	Registrado no sistema AASEFIN
41	2010-03-02	2	50	
42	2010-03-02	3	50	
43	2010-03-08	4	50	
44	2010-03-09	1	51	Registrado no sistema AASEFIN
45	2010-03-09	2	51	
46	2010-03-09	3	51	
47	2010-03-15	4	51	
48	2010-03-16	1	52	Registrado no sistema AASEFIN
70	2010-04-26	2	56	
71	2010-04-26	2	59	
51	2010-03-17	3	52	
72	2010-04-26	2	60	
49	2010-03-17	2	52	
52	2010-03-18	4	52	
53	2010-03-23	1	53	Registrado no sistema AASEFIN
54	2010-03-23	2	53	
55	2010-03-24	3	53	
56	2010-03-25	4	53	
38	2010-03-25	1	49	Registrado no sistema AASEFIN
57	2010-03-25	2	49	
58	2010-03-25	3	49	
59	2010-03-26	4	49	
60	2010-04-06	1	54	Cadastrado no sistema AASEFIN
61	2010-04-06	2	54	
62	2010-04-07	3	54	
63	2010-04-07	4	54	
65	2010-04-20	2	58	
64	2010-04-20	1	58	Cadastrado no sistema AASEFIN
73	2010-04-27	4	58	
74	2010-04-27	3	56	
75	2010-04-27	3	59	
76	2010-04-27	3	60	
67	2010-04-26	1	56	Cadastrado no sistema AASEFIN
68	2010-04-26	1	59	Cadastrado no sistema AASEFIN
69	2010-04-26	1	60	Cadastrado no sistema AASEFIN
77	2010-04-28	1	57	Cadastrado no sistema AASEFIN
78	2010-04-28	1	61	Cadastrado no sistema AASEFIN
79	2010-04-28	2	61	
80	2010-04-28	2	57	
81	2010-04-28	4	59	
82	2010-04-28	4	60	
83	2010-04-28	4	56	
84	2010-04-30	3	57	
85	2010-04-30	3	61	
86	2010-05-03	4	57	
87	2010-05-03	4	61	
88	2010-05-11	1	62	Cadastrado no sistema AASEFIN
89	2010-05-14	1	63	Cadastrado no sistema AASEFIN
90	2010-05-14	2	62	
91	2010-05-14	2	63	
92	2010-05-17	3	63	
93	2010-05-18	4	63	
94	2010-05-17	3	62	
95	2010-05-18	4	62	
96	2010-05-18	1	65	Cadastrado no sistema AASEFIN
159	2010-09-20	2	79	
161	2010-09-22	4	79	
162	2010-10-04	1	80	Cadastrado no sistema AASEFIN
164	2010-10-04	1	83	Cadastrado no sistema AASEFIN
97	2010-05-18	1	66	Cadastrado no sistema AASEFIN
98	2010-05-27	2	65	
99	2010-05-27	2	66	
100	2010-05-27	3	66	
101	2010-05-27	3	65	
102	2010-05-27	4	65	
103	2010-05-27	4	66	
104	2010-06-08	1	64	Cadastrado no sistema AASEFIN
105	2010-06-14	2	64	
106	2010-06-14	1	67	Cadastrado no sistema AASEFIN
107	2010-06-14	3	64	
108	2010-06-15	4	64	
109	2010-06-28	1	68	Cadastrado no sistema AASEFIN
110	2010-06-28	1	69	Cadastrado no sistema AASEFIN
111	2010-06-29	2	67	
112	2010-06-29	2	68	
113	2010-06-29	2	69	
114	2010-06-29	3	67	
115	2010-06-29	3	68	
116	2010-06-29	3	69	
117	2010-06-30	4	67	
118	2010-06-30	4	68	Diárias e indenização
119	2010-06-30	4	69	
120	2010-07-06	1	70	Cadastrado no sistema AASEFIN
121	2010-07-06	1	71	Cadastrado no sistema AASEFIN
122	2010-07-06	2	70	
123	2010-07-06	2	71	
124	2010-07-06	3	70	
125	2010-07-06	3	71	
126	2010-07-06	4	71	
127	2010-07-06	4	70	
128	2010-08-17	1	72	Cadastrado no sistema AASEFIN
129	2010-08-17	1	73	Cadastrado no sistema AASEFIN
130	2010-08-17	2	72	
131	2010-08-17	2	73	
132	2010-08-17	3	72	
133	2010-08-17	3	73	
134	2010-08-18	4	72	
135	2010-08-23	4	73	
136	2010-09-02	1	75	Cadastrada no sistema AASEFIN
137	2010-09-02	1	74	Cadastrada no sistema AASEFIN  (obs: cadastrado como dia 20)
138	2010-09-02	1	76	Cadastrado no sistema AASEFIN
139	2010-09-02	1	77	Cadastrado no sistema AASEFIN
140	2010-09-02	1	78	Cadastrado no sistema AASEFIN
141	2010-09-02	2	76	
142	2010-09-02	2	77	
143	2010-09-02	2	74	
144	2010-09-02	2	75	
145	2010-09-02	2	78	
146	2010-09-02	3	75	
147	2010-09-02	3	74	
148	2010-09-02	3	76	
149	2010-09-02	3	77	
150	2010-09-02	3	78	
151	2010-09-06	4	75	
152	2010-09-06	4	74	
153	2010-09-06	4	76	
154	2010-09-06	4	77	
155	2010-09-06	4	78	
156	2010-09-20	1	79	Cadastrado no sistema AASEFIN
160	2010-09-20	3	79	
163	2010-10-04	1	82	Cadastrado no sistema AASEFIN
165	2010-10-04	1	81	Cadastrado no sistema AASEFIN
166	2010-10-05	2	80	
167	2010-10-05	2	82	
168	2010-10-05	2	83	
169	2010-10-05	2	81	
170	2010-10-06	1	84	Cadastrado no sistema AASEFIN
171	2010-10-06	3	81	
172	2010-10-06	3	83	
173	2010-10-06	3	80	
174	2010-10-06	3	82	
175	2010-10-07	4	80	
176	2010-10-07	4	82	
177	2010-10-07	4	83	
178	2010-10-07	4	81	
179	2010-10-08	2	84	
180	2010-10-08	3	84	
181	2010-10-13	4	84	
184	2010-10-26	1	85	Cadastrado no sistema AASEFIN
190	2010-10-29	1	86	Cadastrado no sistema AASEFIN
189	2010-10-29	2	85	
191	2010-10-29	2	86	
192	2010-10-29	3	85	
193	2010-10-29	3	86	
204	2010-11-18	4	87	
196	2010-11-03	4	85	
197	2010-11-03	4	86	
198	2010-11-08	1	87	Cadastrado no sistema AASEFIN
199	2010-11-16	1	89	Cadastrado no sistema AASEFIN
200	2010-11-16	2	87	
201	2010-11-16	2	89	
202	2010-11-16	3	87	
203	2010-11-16	3	89	
205	2010-11-18	4	89	
206	2010-11-25	1	90	Cadastrado no sistema AASEFIN
207	2010-11-26	2	90	
208	2010-11-26	3	90	
209	2010-12-01	4	90	
210	2010-12-13	1	91	Cadastrado no sistema AASEFIN
211	2010-12-16	1	92	Cadastrado no sistema AASEFIN
212	2010-12-17	2	91	
213	2010-12-17	2	92	
214	2010-12-17	3	91	
215	2010-12-17	3	92	
216	2010-12-20	4	91	
217	2010-12-20	4	92	
218	2011-01-28	1	93	Cadastrado no sistema AASEFIN
219	2011-01-28	2	93	
220	2011-01-28	3	93	
221	2011-02-01	1	94	Cadastrado no sistema AASEFIN
222	2011-02-01	4	93	
223	2011-02-02	2	94	
224	2011-02-03	3	94	
225	2011-02-07	4	94	
226	2011-02-17	1	95	Cadastrado no sistema AASEFIN
228	2011-02-18	3	95	
229	2011-02-21	4	95	
227	2011-02-18	2	95	
232	2011-03-14	1	97	Cadastrado no sistema AASEFIN
233	2011-03-14	2	97	
234	2011-03-14	3	97	
235	2011-03-21	1	96	Cadastrado no sistema AASEFIN
236	2011-03-21	4	97	
237	2011-03-21	2	96	
238	2011-03-22	1	98	Cadastrado no sistema AARH-Diárias
239	2011-03-22	2	98	
240	2011-03-22	3	96	
241	2011-03-22	3	98	
242	2011-03-24	4	96	
243	2011-03-29	1	99	Cadastrado no sistema AARH-Diárias
244	2011-03-29	4	98	
245	2011-04-01	2	99	
246	2011-04-01	3	99	
247	2011-04-04	4	99	
248	2011-04-05	1	100	Cadastrado no sistema AARH-Diárias
249	2011-04-05	2	100	
250	2011-04-05	3	100	
251	2011-04-12	4	100	
253	2011-04-15	2	101	
252	2011-04-15	1	101	Cadastrado no sistema AARH-Diárias
254	2011-04-18	3	101	
255	2011-04-18	4	101	
257	2011-04-27	2	102	
256	2011-04-27	1	102	Cadastrado no sistema AARH-Diárias
258	2011-04-27	3	102	
259	2011-04-29	1	103	Cadastrado no sistema AARH-Diárias
260	2011-04-29	2	103	
261	2011-05-02	4	102	
262	2011-05-03	3	103	
263	2011-05-05	4	103	
264	2011-05-06	1	104	Cadastrado no sistema AARH-Diárias
265	2011-05-09	1	105	Cadastrado no sistema AARH-Diárias
266	2011-05-09	2	104	
267	2011-05-09	2	105	
268	2011-05-09	3	104	
269	2011-05-09	3	105	
270	2011-05-10	1	106	Cadastrado no sistema AARH-Diárias
271	2011-05-11	4	104	
272	2011-05-11	4	105	
273	2011-05-17	2	106	
274	2011-05-17	3	106	
275	2011-05-20	1	107	Cadastrado no sistema AARH-Diárias
276	2011-05-23	4	106	
277	2011-05-27	1	108	Cadastrado no sistema AARH-Diárias
278	2011-05-30	2	107	
279	2011-05-30	3	107	
280	2011-05-30	2	108	
281	2011-05-30	3	108	
282	2011-05-30	4	107	
283	2011-05-30	4	108	
285	2011-06-08	2	109	
284	2011-06-08	1	109	Cadastrado no sistema AARH-Diárias
286	2011-06-08	3	109	
288	2011-06-13	2	110	
310	2011-08-31	2	115	
311	2011-08-31	2	116	
289	2011-06-13	3	110	
287	2011-06-13	1	110	Cadastrado no sistema AARH-Diárias
290	2011-06-15	4	109	
291	2011-06-15	4	110	
292	2011-07-06	1	111	Cadastrado no sistema AARH-Diárias
293	2011-07-06	2	111	
294	2011-07-07	3	111	
295	2011-07-08	4	111	
296	2011-07-12	1	113	Cadastrado no sistema AARH-Diárias
297	2011-07-12	1	112	Cadastrado no sistema AARH-Diárias
298	2011-07-12	2	113	
299	2011-07-12	2	112	
300	2011-07-12	3	113	
301	2011-07-12	3	112	
302	2011-07-13	4	113	
303	2011-07-18	4	112	
304	2011-08-22	1	114	Cadastrado no sistema AARH-Diárias
305	2011-08-22	2	114	
306	2011-08-23	3	114	
307	2011-08-23	4	114	
308	2011-08-29	1	116	Cadastrado no sistema AARH-Diárias
309	2011-08-29	1	115	Cadastrado no sistema AARH-Diárias
312	2011-08-31	3	115	
313	2011-08-31	3	116	
314	2011-09-01	4	116	
315	2011-09-01	4	115	
316	2011-09-02	1	117	Cadastrado no sistema AARH-Diárias
317	2011-09-02	2	117	
318	2011-09-05	3	117	
319	2011-09-06	4	117	
320	2011-09-06	1	118	Cadastrado no sistema AARH-Diárias
321	2011-09-16	1	119	Cadastrado no sistema  AARH-Diárias
322	2011-09-20	1	120	Cadastrado no sistema AARH-Diárias
323	2011-09-20	2	118	
324	2011-09-20	2	120	
325	2011-09-20	2	119	
326	2011-09-20	3	118	
327	2011-09-20	3	120	
328	2011-09-20	3	119	
329	2011-09-26	4	118	
330	2011-09-26	4	119	
331	2011-09-26	4	120	
332	2011-10-18	1	121	Cadastrado no sistema AARH-Diárias
333	2011-10-18	1	122	Cadastrado no sistema AARH-Diárias
334	2011-10-18	2	121	
335	2011-10-18	2	122	
336	2011-10-18	3	121	
337	2011-10-18	3	122	
338	2011-10-19	4	121	
339	2011-10-26	4	122	
340	2011-11-11	1	123	Cadastrado no sistema AARH-Diárias
341	2011-11-11	1	124	Cadastrado no sistema AARH-Diárias
342	2011-11-14	1	125	Cadastrado no sistema AARH-Diárias
343	2011-11-14	2	123	
344	2011-11-14	2	124	
345	2011-11-14	2	125	
346	2011-11-14	3	124	
347	2011-11-14	3	125	
348	2011-11-14	3	123	
349	2011-11-16	4	123	
350	2011-11-16	4	124	
351	2011-11-16	4	125	
352	2011-11-22	1	126	Cadastrado no sistema AARH-Diárias
353	2011-11-22	1	127	Cadastrado no sistema AARH-Diárias
354	2011-11-22	2	126	
355	2011-11-23	3	126	
356	2011-11-22	2	127	
357	2011-11-23	3	127	
358	2011-11-28	4	126	
359	2011-11-28	4	127	
360	2011-12-14	1	128	Cadastrado no sistema AARH-Diárias
361	2011-12-14	1	129	Cadastrado no sistema AARH-Diárias
362	2011-12-14	1	130	Cadastrado no sistema AARH-Diárias
363	2011-12-16	1	131	Cadastrado no sistema AARH-Diárias
364	2011-12-16	2	128	
365	2011-12-16	2	129	
366	2011-12-16	2	130	
367	2011-12-16	2	131	
368	2011-12-16	3	128	
369	2011-12-16	3	129	
370	2011-12-16	3	130	
371	2011-12-16	3	131	
372	2011-12-19	4	128	
373	2011-12-19	4	129	
374	2011-12-19	4	130	
375	2011-12-19	4	131	
376	2012-01-19	1	132	Cadastrado no sistema AARH-Diárias
377	2012-01-19	1	133	Cadastrado no sistema AARH-Diárias
378	2012-01-19	1	134	Cadastrado no sistema AARH-Diárias
379	2012-01-19	2	132	
380	2012-01-19	2	133	
381	2012-01-19	2	134	
382	2012-01-19	3	132	
383	2012-01-19	3	133	
384	2012-01-19	3	134	
385	2012-01-23	4	132	
386	2012-01-23	4	133	
387	2012-01-23	4	134	
388	2012-01-30	1	135	Cadastrado no sistema AARH-Diárias
389	2012-01-31	1	136	Cadastrado no sistema AARH-Diárias
390	2012-02-01	2	135	
391	2012-02-01	2	136	
392	2012-02-01	3	135	
393	2012-02-01	3	136	
394	2012-02-01	4	135	
395	2012-02-06	4	136	
396	2012-02-27	1	138	Cadastrado no sistema AARH-Diárias
397	2012-02-29	1	137	Cadastrado no sistema AARH-Diárias
398	2012-02-29	1	139	Cadastrado no sistema AARH-Diárias
399	2012-02-29	2	138	
400	2012-02-29	2	137	
401	2012-02-29	2	139	
402	2012-03-01	3	138	
403	2012-03-01	3	137	
404	2012-03-01	3	139	
405	2012-03-05	4	138	
406	2012-03-05	4	137	
407	2012-03-05	4	139	
408	2012-03-21	1	140	Cadastrado no sistema AARH-Diárias
409	2012-03-22	2	140	
410	2012-03-22	3	140	
411	2012-03-26	4	140	
412	2012-03-30	1	141	Cadastrado no sistema AARH-Diárias
413	2012-04-02	2	141	
414	2012-04-02	3	141	
415	2012-04-02	4	141	
416	2012-04-03	1	142	Cadastrado no sistema AARH-Diárias
417	2012-04-10	2	142	
418	2012-04-10	3	142	
419	2012-04-10	4	142	
420	2012-04-13	1	143	Cadastrado no sistema AARH-Diárias
421	2012-04-16	2	143	
422	2012-04-16	3	143	
423	2012-04-30	4	143	
424	2012-05-03	1	144	Cadastrado no sistema AARH-Diárias
425	2012-05-07	1	145	Cadastrado no sistema AARH-Diárias
426	2012-05-07	2	144	
428	2012-05-07	2	145	
429	2012-05-08	3	144	
430	2012-05-08	4	144	
431	2012-05-08	3	145	
432	2012-05-08	4	145	
433	2012-05-21	1	146	Cadastrado no sistema AARH-Diárias
434	2012-05-21	1	147	Cadastrado no sistema AARH-Diárias
435	2012-05-21	1	148	Cadastrado no sistema AARH-Diárias
436	2012-05-21	2	146	
437	2012-05-21	2	147	
438	2012-05-21	2	148	
439	2012-05-22	3	146	
440	2012-05-22	3	147	
441	2012-05-22	3	148	
442	2012-05-22	4	146	
443	2012-05-22	4	147	
444	2012-05-22	4	148	
445	2012-05-25	1	149	Cadastrado no sistema AARH-Diárias
446	2012-05-25	1	150	Cadastrado no sistema AARH-Diárias
447	2012-05-25	2	149	
448	2012-05-25	3	149	
449	2012-05-25	2	150	
450	2012-05-25	3	150	
451	2012-05-28	4	149	
452	2012-05-28	4	150	
453	2012-06-12	1	151	Cadastrado no sistema AARH-Diárias
454	2012-06-21	2	151	
455	2012-06-22	3	151	
456	2012-06-26	4	151	
457	2012-08-07	1	152	Cadastrado no sistema AARH-Diárias
458	2012-08-07	1	153	Cadastrado no sistema AARH-Diárias
461	2012-08-07	2	153	
459	2012-08-07	1	154	Cadastrado no sistema AARH-Diárias
460	2012-08-07	2	152	
462	2012-08-07	2	154	
463	2012-08-07	3	152	
464	2012-08-07	3	153	
465	2012-08-07	3	154	
466	2012-08-09	4	152	
467	2012-08-09	4	153	
468	2012-08-09	4	154	
469	2012-09-19	1	155	Cadastrado no sistema AARH-Diárias
470	2012-09-19	1	156	Cadastrado no sistema AARH-Diárias
471	2012-09-19	1	157	Cadastrado no sistema AARH-Diárias
472	2012-09-19	1	158	Cadastrado no sistema AARH-Diárias
473	2012-09-19	1	159	Cadastrado no sistema AARH-Diárias
474	2012-09-19	1	160	Cadastrado no sistema AARH-Diárias
475	2012-09-19	2	155	
476	2012-09-19	2	156	
477	2012-09-19	2	157	
478	2012-09-19	2	158	
479	2012-09-19	2	159	
480	2012-09-19	2	160	
481	2012-09-20	3	155	
482	2012-09-20	3	156	
483	2012-09-20	3	157	
484	2012-09-20	3	158	
485	2012-09-20	3	159	
486	2012-09-20	3	160	
487	2012-09-20	4	155	
488	2012-09-20	4	156	
489	2012-09-20	4	157	
490	2012-09-20	4	158	
491	2012-09-20	4	159	
492	2012-09-20	4	160	
493	2012-11-19	1	161	Cadastrado no sistema AARH-Diárias
494	2012-11-19	1	162	Cadastrado no sistema AARH-Diárias
495	2012-11-19	1	163	Cadastrado no sistema AARH-Diárias
496	2012-11-19	1	164	Cadastrado no sistema AARH-Diárias
497	2012-11-19	1	165	Cadastrado no sistema AARH-Diárias
498	2012-11-19	1	166	Cadastrado no sistema AARH-Diárias
499	2012-11-19	1	167	Cadastrado no sistema AARH-Diárias
500	2012-11-19	1	168	Cadastrado no sistema AARH-Diárias
501	2012-11-19	2	161	
502	2012-11-19	2	162	
503	2012-11-19	2	163	
504	2012-11-19	2	164	
505	2012-11-19	2	165	
506	2012-11-19	2	166	
507	2012-11-19	2	167	
508	2012-11-19	2	168	
509	2012-11-19	3	161	
510	2012-11-19	4	161	
511	2012-11-19	3	162	
512	2012-11-19	4	162	
513	2012-11-19	3	163	
514	2012-11-19	4	163	
515	2012-11-19	3	164	
516	2012-11-19	4	164	
517	2012-11-19	3	165	
518	2012-11-19	4	165	
519	2012-11-19	3	166	
520	2012-11-19	4	166	
521	2012-11-19	3	167	
522	2012-11-19	4	167	
523	2012-11-19	3	168	
524	2012-11-19	4	168	
525	2013-01-15	1	175	Cadastrado no sistema AARH-Diárias.
526	2013-01-17	1	176	Cadastrado no sistema AARH-Diárias
527	2013-01-17	1	169	Cadastrado no sistema AARH-Diárias
528	2013-01-17	1	170	Cadastrado no sistema AARH-Diárias
529	2013-01-17	1	171	Cadastrado no sistema AARH-Diárias
530	2013-01-17	1	172	Cadastrado no sistema AARH-Diárias
531	2013-01-17	1	174	Cadastrado no sistema AARH-Diárias
532	2013-01-18	2	169	
533	2013-01-18	3	169	
534	2013-01-18	2	171	
535	2013-01-18	3	171	
536	2013-01-18	2	172	
537	2013-01-18	3	172	
538	2013-01-18	2	174	
539	2013-01-18	3	174	
540	2013-01-18	2	170	
541	2013-01-18	3	170	
542	2013-01-18	2	175	
543	2013-01-18	3	175	
544	2013-01-18	2	176	
545	2013-01-18	3	176	
546	2013-01-21	4	169	
547	2013-01-21	4	170	
548	2013-01-21	4	171	
549	2013-01-21	4	172	
550	2013-01-21	4	174	
551	2013-01-21	4	175	
552	2013-01-21	4	176	
553	2013-04-18	1	177	Cadastrado no sistema AARH-Diárias
554	2013-04-18	1	178	Cadastrado no sistema AARH-Diárias
555	2013-04-18	1	179	Cadastrado no sistema AARH-diárias
556	2013-04-18	1	180	Cadastrado no sistema AARH-Diárias
557	2013-04-18	1	181	Cadastrado no sistema AARH-Diárias
558	2013-04-18	1	182	Cadastrado no sistema AARH-Diárias
559	2013-04-18	1	183	Cadastrado no sistema AARH-Diárias
560	2013-04-18	1	184	Cadastrado no sistema AARH-Diárias
561	2013-04-18	1	185	Cadastrado no sistema AARH-Diárias
562	2013-04-18	1	186	Cadastrado no sistema AARH-Diárias
563	2013-04-18	1	187	Cadastrado no sistema AARH-Diárias
564	2013-04-22	2	177	
565	2013-04-22	2	178	
566	2013-04-22	2	179	
567	2013-04-22	2	180	
568	2013-04-22	2	181	
569	2013-04-22	2	182	
570	2013-04-22	2	183	
571	2013-04-22	2	184	
572	2013-04-22	2	185	
573	2013-04-22	2	186	
574	2013-04-22	2	187	
575	2013-04-23	3	177	
577	2013-04-23	3	178	
578	2013-04-23	3	179	
579	2013-04-23	3	180	
580	2013-04-23	3	181	
581	2013-04-23	3	182	
582	2013-04-23	3	183	
583	2013-04-23	3	184	
584	2013-04-23	3	185	
585	2013-04-23	3	186	
586	2013-04-23	3	187	
587	2013-04-23	4	186	
588	2013-04-26	4	177	
589	2013-04-26	4	178	
590	2013-04-26	4	179	
591	2013-04-26	4	180	
592	2013-04-26	4	181	
593	2013-04-26	4	182	
594	2013-04-26	4	183	
595	2013-04-26	4	184	
596	2013-04-26	4	185	
597	2013-04-26	4	187	
600	2013-09-25	1	203	Cadastrado no sistema AARH-Diárias
598	2013-09-25	1	201	Cadastrado no sistema AARH-Diárias
599	2013-09-25	1	202	Cadastrado no sistema AARH-Diárias
601	2013-09-25	1	204	Cadastrado no sistema AARH-Diárias
602	2013-09-25	1	205	Cadastrado no sistema AARH-Diárias
603	2013-09-25	1	206	Cadastrado no sistema AARH-Diárias
604	2013-09-25	2	201	
605	2013-09-25	2	202	
606	2013-09-25	2	204	
607	2013-09-25	2	205	
608	2013-09-25	2	206	
609	2013-09-25	2	203	
610	2013-09-25	3	204	
611	2013-09-25	3	202	
612	2013-09-25	3	201	
613	2013-09-25	3	205	
614	2013-09-25	3	206	
615	2013-09-25	3	203	
616	2013-09-30	4	201	
617	2013-09-30	4	202	
618	2013-09-30	4	204	
619	2013-09-30	4	205	
620	2013-09-30	4	206	
621	2013-09-30	4	203	
622	2014-01-09	1	211	Cadastrado no sistema AARH-Diárias
623	2014-01-09	1	212	Cadastrado no sistema AARH-Diárias
624	2014-01-09	1	213	Cadastrado no sistema AARH-Diárias
625	2014-01-15	2	211	
626	2014-01-15	2	212	
627	2014-01-15	2	213	
628	2014-01-15	3	211	
629	2014-01-15	3	212	
630	2014-01-15	3	213	
631	2014-01-16	4	211	
632	2014-01-16	4	212	
633	2014-01-16	4	213	
634	2014-04-01	1	214	Cadastrado no sistema AARH-Diárias
635	2014-04-01	1	215	Cadastrado no sistema AARH-Diárias
636	2014-04-01	1	216	Cadastrado no sistema AARH-Diárias
637	2014-04-01	1	217	Cadastrado no sistema AARH-Diárias
638	2014-04-01	1	218	Cadastrado no sistema AARH-Diárias
639	2014-04-01	1	219	Cadastrado no sistema AARH-Diárias
640	2014-04-01	1	220	Cadastrado no sistema AARH-Diárias
641	2014-04-01	2	214	
642	2014-04-01	2	215	
643	2014-04-01	2	216	
644	2014-04-01	2	217	
645	2014-04-01	2	218	
646	2014-04-01	2	219	
647	2014-04-01	2	220	
648	2014-04-01	3	214	
649	2014-04-01	3	215	
650	2014-04-01	3	216	
651	2014-04-01	3	217	
652	2014-04-01	3	218	
653	2014-04-01	3	219	
654	2014-04-01	3	220	
655	2014-04-07	4	214	
656	2014-04-07	4	215	
657	2014-04-07	4	216	
658	2014-04-07	4	217	
659	2014-04-07	4	218	
660	2014-04-07	4	219	
661	2014-04-07	4	220	
662	2014-07-10	1	226	Cadastrado no sistema AARH-Diárias
663	2014-08-18	2	226	
664	2014-08-19	3	226	
665	2014-08-19	4	226	
666	2014-09-19	1	227	Cadastrado no sistema AARH-Diárias
667	2014-09-19	1	228	Cadastrado no sistema AARH-Diárias
668	2014-09-19	1	229	Cadastrado no sistema AARH-Diárias
669	2014-09-19	1	230	Cadastrado no sistema AARH-Diárias
670	2014-09-19	1	231	Cadastrado no sistema AARH-Diárias
671	2014-09-19	1	233	Cadastrado no sistema AARH-Diárias.
672	2014-09-19	1	232	Cadastrado no sistema AARH-Diárias
673	2014-09-19	1	234	Cadastrado no sistema AARH-Diárias
674	2014-09-19	1	235	
675	2014-09-19	1	236	Cadastrado no sistema AARH-Diárias
676	2014-09-19	1	237	
677	2014-09-19	1	238	
678	2014-09-19	1	239	Cadastrado no sistema AARH-Diárias
679	2014-09-30	2	231	
680	2014-09-30	2	239	
681	2014-09-30	2	237	
683	2014-09-30	2	228	
684	2014-09-30	2	227	
685	2014-09-30	2	238	
686	2014-09-30	2	230	
687	2014-09-30	2	232	
688	2014-09-30	2	236	
689	2014-09-30	2	234	
690	2014-09-30	2	229	
691	2014-09-30	3	228	
692	2014-09-30	3	229	
693	2014-09-30	3	227	
694	2014-09-30	3	230	
695	2014-09-30	3	231	
696	2014-09-30	3	232	
697	2014-09-30	3	234	
698	2014-09-30	3	236	
699	2014-09-30	3	237	
700	2014-09-30	3	238	
701	2014-09-30	3	239	
702	2014-09-30	2	233	
703	2014-09-30	3	233	
704	2014-09-30	2	235	
705	2014-09-30	3	235	
706	2014-10-06	4	227	
707	2014-10-06	4	228	
708	2014-10-06	4	229	
709	2014-10-06	4	230	
710	2014-10-06	4	231	
711	2014-10-06	4	232	
712	2014-10-06	4	234	
713	2014-10-06	4	233	
714	2014-10-06	4	235	
715	2014-10-06	4	236	
716	2014-10-06	4	237	
717	2014-10-06	4	238	
718	2014-10-06	4	239	
719	2014-10-20	1	242	Cadastrado no sistema AARH-Diárias
720	2014-10-20	2	242	
721	2014-10-22	3	242	
722	2014-10-23	4	242	
723	2015-01-16	1	243	Cadastrado no sistema AARH-Diárias
724	2015-01-20	2	243	
725	2015-01-20	3	243	
726	2015-01-20	4	243	
727	2015-01-27	1	244	Cadastrado no sistema AARH-Diárias
728	2015-01-27	1	245	Cadastrado no sistema AARH-Diárias
729	2015-02-02	2	244	
730	2015-02-02	2	245	
731	2015-02-04	3	244	
733	2015-02-09	4	244	
734	2015-02-09	4	245	
735	2015-06-24	1	250	Cadastrado no sistema AARH-Diárias
736	2015-06-24	2	250	
737	2015-06-25	3	250	
739	2016-02-03	1	251	Cadastrado no sistema AARH-Diárias
738	2015-06-25	4	250	
732	2015-02-04	3	245	
\.


--
-- Name: eventoreqviagem_codigo_seq; Type: SEQUENCE SET; Schema: viagem; Owner: sati
--

SELECT pg_catalog.setval('eventoreqviagem_codigo_seq', 759, true);


--
-- Data for Name: tarefa; Type: TABLE DATA; Schema: viagem; Owner: sati
--

COPY tarefa (codigo, tomboequip, descricao, nrotarefaritm, cod_viagem) FROM stdin;
1	63147 	Instalação de microcomputador	         	1
2	63420 	Instalação de monitor	         	1
3	48555 	Reparo de cooler de microcomputador	         	1
4	44746 	Reinstalação de microcomputador	         	1
5	54739 	Atualização de Java microcomputador	         	1
6	44752 	Instalação de microcomputador	         	3
7	64323 	Instalação de monitor	         	3
8	47314 	Troca de monitor	         	3
9	63104 	Instalação software SICALCP	         	3
10	44418 	Instalação software SICALCP	         	3
11	54739 	Instalação software SICALCP	         	3
12	63001 	Instalação software SICALCP	         	3
13	47639 	Instalação software SICALCP	         	3
14	63883 	Instalação de microcomputador	         	2
15	63884 	Instalação de microcomputador	         	2
16	63885 	Instalação de microcomputador	         	2
17	63880 	Instalação de microcomputador	         	2
18	63881 	Instalação de microcomputador	         	2
19	63882 	Instalação de microcomputador	         	2
24	vários	Desinstalação do software Microsoft Office	         	2
25	44745 	Recolhimento de microcomputador	         	2
26	44752 	Recolhimento de microcomputador	         	2
27	44753 	Recolhimento de microcomputador	         	2
28	44467 	Recolhimento de microcomputador	         	2
29	44746 	Recolhimento de microcomputador	         	2
30	44747 	Recolhimento de microcomputador	         	2
31	63904 	Instalação de microcomputador	         	4
32	vários	Desinstalação do software Microsoft Office	         	4
33	44420 	Recolhimento de microcomputador	         	4
34	45907 	Devolução conserto impressora	         	5
35	25833 	Devolução conserto impressora	         	5
36	63881 	Configuração de permissões no microcomputador	         	5
37	63002 	Compactação de arquivos	         	5
38	63882 	Alteração do registro do Windows	         	5
39	53771 	Configuração de macro Extenso e suporte a auto-texto	         	5
40	54457 	Retirada de peça de multifuncional para envio ao SEMEI	         	6
41	48555 	Divisão de ponto de rede	         	6
42	33911 	Configuração de impressora para rede	         	6
43	53771 	Suporte BrOffice.org: formatação de estilos	         	6
44	63105 	Instalação do software PDFCreator	         	6
45	59571 	Instalação do software PDFCreator	         	6
46	54457 	Reparos na multifuncional Samsung	         	7
47	48555 	Reinstalação do BrOffice.org	         	7
48	54602 	Configuração de software de digitalização	         	7
49	54457 	Reparos na multifuncional Samsung	         	8
50	25833 	Reinstalação de driver da HP Deskjet 600	         	8
51	54458 	Solução de problemas com multifuncional Samsung	         	8
52	63949 	Instalação de microcomputador Lenovo	         	8
53	64329 	Instalação de monitor	         	9
54	54859 	Remoção do Microsoft Office	         	9
55	59096 	Remoção do Microsoft Office	         	9
56	59096 	Atualização de software	         	9
57	47672 	Atualização de software	         	9
58	54457 	Problema de ruídos na Multifuncional Samsung	         	10
59	54458 	Problema de ruídos na Multifuncional Samsung	         	10
60	47644 	Instalação do Microsoft Office	         	10
66	45282 	Reinstalação de Brother do SEDIS	         	12
65	25376 	Substituição de tampa do pivot Deskjet 600 (Marli)	23537    	12
68	63949 	Instalação driver leitora de cartão (Irineu)	24603    	12
69	62972 	Instalação driver leitora de cartão (Luciano)	24603    	12
71	53771 	Ao enviar e-mails não grava cópia na pasta Enviadas	24916    	12
70	33911 	Reparo de ponto de rede (Ricardo)	24978    	12
76	54466 	Problema com sensor de cilindro Samsung	26385    	15
77	48683 	Trocar impressora de lugar	         	15
73	47643 	Formatação/reinstalação microcomputador	         	14
74	47643 	Trocar microcomputador de lugar	         	14
72	64004 	Instalação do microcomputador (SEDIS) - cabo de rede/filtro de linha	25941    	14
75	63882 	Instalação do programa de Declaração da Receita Federal	         	14
78	47672 	Problema SAP1 microcomputador dos Oficiais de Justiça	         	15
79	31148 	Trocar impressora de lugar	         	15
92	67219 	Instalação de monitor	27254    	18
84	67201 	Instalação de monitor LCD	27255    	19
83	67200 	Instalação de monitor LCD	27255    	19
88	67202 	Instalação de monitor LCD	27255    	19
89	67165 	Instalação de monitor LCD	27293    	19
90	67166 	Instalação de monitor LCD	27293    	19
91	25376 	Reparo de HP Deskjet 600	         	19
82	67220 	Instalação de monitor	27254    	18
93	67218 	Instalação de monitor	27254    	18
95	59596 	Troca de placa de vídeo	29623    	24
96	42739 	Conserto dobradiça multifuncional Brother MFC-8820D	29684    	24
97	vários	Instalação de software para digitalização na Samsung	         	24
99	vários	Instalação/configuração de cartões de certificação digital Caixa	         	25
98	47642 	Formatação/reinstalação de microcomputador IBM	30981    	25
100	67364 	Instalação de monitor	30882    	25
101	67365 	Instalação de monitor	30856    	25
102	64013 	Instalação de microcomputador	30853    	25
103	64014 	Instalação de microcomputador	30855    	25
104	63884 	Instalação do SICALCP	         	25
105	63105 	Trocar microcoputador de lugar	         	25
106	63885 	Trocar microcomputador de lugar	         	25
107	54466 	Troca de Multifuncional Samsung	30047    	26
108	48683 	Verificação dos problemas com a Laserjet 1320	         	26
109	58916 	Verificação de problemas com teclado e mouse na doc-station do notebook	         	26
110	53834 	Configuração Thunderbird	         	26
111	31148 	Instalação impressora deskjet	         	26
112	59543 	Instalação programa cálculo rápido	         	26
113	59520 	Conferência de funcionamento de doc-station	         	26
114	48714 	Teste impressão duplex Laserjet 1320 (está com defeito)	         	26
115	59571 	Reorganização estrutura sala de audiências	33064    	27
117	52410 	Instalação de Brother HL-7050	33127    	28
116	Vários	Atualização de antivírus em todas as estações	         	27
149	45282 	Reparos multifuncional Brother MFC-8840D	45343    	37
119	54457 	Limpeza dos contatos do tonner	34831    	29
150	25376 	Reinstalação impressora Deskjet 600	44513    	37
120	62102 	Recolocação de peça de bandeja	         	29
121	63106 	Configuração para digitalização na Samsung	         	29
118	54457 	Acerto de ponto de rede Samsung	34608    	29
123	server	Backup do Banco de Dados para manutenção	         	30
122	Vários	Gravação de token nos cartões 2VT	36843    	30
124	      	Alterações no Banco de Dados Baln. Camboriú/SC	         	31
125	      	Apoio à Corregedoria	         	32
126	      	Instalação programa SEDIG	         	30
127	      	Instalação de doc-station juiz titular	         	31
130	server	Acrescentar unidade de HD para /var/backup	         	33
131	server	Trocar unidade de fita de backup	         	33
132	      	Gravação de token de cartão e-CPF	41169    	32
133	Vários	Instalação de SicalcP	40926    	32
134	Vários	Atualização BrOffice.org	         	34
136	      	Limpeza tracionadores Samsung	         	34
135	59096 	Configuração para impressora Laserjet 1320	43697    	34
169	      	Trocar impressora de lugar	         	46
170	      	Recolhimento de impressora	         	46
151	58935 	Instalação de bateria de notebook	46137    	37
152	47640 	Microcomputador não liga	47615    	40
141	67421 	Instalação Monitor 2ª Vara	44209    	35
143	Vários	Acompanhamento atualização BrOffice.org	         	35
153	47642 	Instalação de software	47626    	40
140	54457 	Problemas com multifuncional Samsung 1ª Vara	44171    	35
154	64050 	Instalação de microcomputador	         	40
155	44752 	Conserto/recolhimento de microcomputador	         	40
144	66787 	Instalação de microcomputador e scanner na sala da OAB	44221    	36
145	68109 	Instalação de impressora Samsung SEDIS	44220    	36
146	25376 	Problemas com impressora Deskjet	44513    	36
147	      	Instalação impressora Deskjet 670C	44580    	36
148	68113 	Instalação de impressora Samsung	46107    	37
156	      	Configuração notebook Silvio R. Barchechen	         	39
157	      	Instalação do OOFind nos micros dos assistentes	         	39
158	      	Configuração cliente digitalização Samsung em dois micros	         	39
159	54589 	Impressora laser Oki contadoria	         	38
160	63003 	Configuração impressão micro contadoria	         	38
161	Vários	Migração da sede da Vara do Trabalho daquela cidade	         	41
162	46050 	Brother com mensagem de erro "DEFEITO NO MFC78"	48839    	42
163	      	Verificação de ponto de rede onde se encontra um ramal telefônico (Gab. Substituto)	         	42
164	48686 	Instalação de Laserjet 1320 - 2VT	45435    	42
165	68121 	Instalação de impressora Laser Samsung e configuração das estações	         	43
166	46050 	Instalação de fusor da Brother 2VT	48839    	44
167	45299 	Verificação de problema com fusor da Brother 1VT	50996    	44
173	25833 	Impressora deskjet 600 não liga	54547    	48
172	35139 	Troca de HUB defeituoso	54552    	48
171	69554 	Instalação de impressora laser Samsung de mesa	54455    	47
174	50743 	Troca de cabo IDE do server	         	49
176	25833 	Reinstalação de impressora deskjet	54547    	50
175	50745 	Instalação de HD no servidor	         	50
177	50745 	Substituição de cabo IDE do servidor	         	51
178	      	Alteração de cabeamento de rede para mudança de microcomputador	         	51
179	56863 	Instalação de HD no servidor	         	52
180	64013 	Substituição provisória do microcomputador	52386    	51
181	56863 	Instalação de unidade de fita DAT no server	         	53
182	50745 	Instalação/configuração de HD do server, conforme padrão	         	54
183	64013 	Reinstalação de microcomputador anteriormente trazido a laboratório	52386    	54
184	54739 	Troca de drive de CD-ROM	56055    	54
187	58592 	Impressora Deskjet danificada (caiu no chão)	58508    	56
188	54741 	Resolver com OJ a questão de instalação do software de imagens	47812    	56
190	63885 	Resolver problema de acesso Depósitos Recursais	42765    	56
191	Vários	Instalação de leitoras de cartão	         	56
192	56863 	Trocar unidade de fita DAT	58650    	57
193	      	Teste de memória micro Magu	         	56
194	63003 	Microcomputador da contadoria não inicia	59128    	58
196	      	Backup, reinstalação e configuração do Microcomputador marca Lenovo, modelo M6078 Thinkcentre, utilizado pelo Dr. Nelson H. Leiria	         	59
197	      	Backup, reinstalação e configuração do Microcomputador marca IBM, Modelo Celerom, utilizado pela usuário Yuska.	         	59
198	      	Micro da Leocádia travando	         	56
199	58907 	Backup, reinstalação e configuração do Notebook lenovo, modelo T-60, utilizado pelo Dr. Silvio R. Barchechen	         	60
201	28592 	Verificar estado interno da Deskjet 660C que caiu no chão	58508    	61
202	Vários	Atualização de anti-vírus nas estações	         	57
200	62972 	Problema ao ligar o microcomputador (contatos de memória)	59861    	61
203	      	Instalação de equipamento IN30	         	57
204	      	Organização da sala do servidor	         	62
205	      	Limpeza sala do servidor (equipamentos)	         	63
207	      	Retriada de caixas e materiais desnecessários	         	63
206	      	Organização cabeamento elétrico	         	63
208	      	Nova política de backup	         	63
209	      	Organização cabeamentos de dados	         	63
210	      	Organização de cabeamento dentro rack 	         	63
211	      	Fechamento do rack	         	63
212	      	Fechamento de eletrocalhas: quantificar para fazer pedido	         	63
234	      	Recolher notebook para pré-instalação	         	73
216	28592 	Substituição de impressora danificada (depende de conversa com Willian)	58508    	63
215	58935 	Novos testes em notebook Dra. Sônia: teste de memória	56839    	63
217	47640 	Instalação de programa do SEDIG	60314    	63
218	69182 	Instalação de monitor	         	63
219	58935 	Notebook Sônia: substituir por micro IBM	56839    	65
220	      	Queda de rede - servidor de dados	         	66
213	      	Acompanhamento de obra de passagem do Switch para a sala do servidor	         	64
214	      	Problema de conexão com a INTERNET: MODEM pifado	         	64
221	Vários	Instalação de pontos de rede faltantes	         	67
223	      	Conserto de multifuncional (3VT)	         	68
222	      	Instalação de impressora (SEDIS)	         	68
224	      	Verificação de problema com multifuncional (2VT)	         	68
225	31139 	Troca de painel HP	64113    	69
226	      	Instalação Switch	         	69
227	      	Conserto de ponto de rede	         	69
228	63107 	Microcomputador não liga	65129    	70
229	46119 	Instalação de microcomputador	         	71
230	Vários	Instalação de novos pontos de rede	         	71
231	Vários	Conclusão de instalações de rede	         	72
232	47639 	Recolher microcomputador para reparos em laboratório e consequentes trocas	67090    	73
235	54873 	Instalação de microcomputador	66725    	74
236	42147 	Reparos GENICOM	66722    	74
237	      	Troca de no-break	         	75
238	47639 	Reinstalação de microcomputador	67090    	75
239	      	Substituição de notebook	         	75
233	58877 	Recolher notebook para reparos	68771    	73
240	71085 	No-break APC com defeito	         	76
241	Vários	Acompanhamento trabalhos Correição	         	77
242	54873 	Instalação de microcomputador	66725    	77
243	Vários	Acompanhamento dos trabalhos da Correição	         	78
244	58877 	Troca de HD do Notebook	69120    	79
245	54457 	Limpeza de contatos do tonner da Samsung	         	79
246	47639 	Retorno de microcomputador para que seja remetido ao TRT	67090    	79
247	      	Instalação de no-break provisório	         	80
248	30054 	Instalação de impressora HP 600	71867    	81
249	Vários	Verificação dos mouses defeituosos	71529    	82
250	47639 	Substituição de micro da contadoria	67090    	82
251	58877 	Consolidar situação de no-break que teve HD substituído	69120    	82
252	      	Troca de fonte queimada de microcomputador	         	82
253	      	Recolher micro molhado para Laboratório	         	82
254	68109 	Investigar Samsung SEDIS trancando papel	72165    	82
255	54457 	Instalação de fusor da Samsung 1VT	72016    	83
256	47255 	Instalação de microcomputador de backup para Paulo Brandão	72388    	83
257	59570 	Problemas com microcomputador da sala de audiências	73236    	84
258	62899 	Notebook Sônia fora da rede	73176    	84
259	47255 	Recolher microcomputador reserva	72388    	84
260	47641 	Restaurar microcomputador ao local de origem	73204    	84
261	48714 	Laserjet 1320 atolando papel	74279    	85
262	59543 	Verificação de problema em mouses audi	74599    	85
263	48552 	Microcomputador desligando sozinho	74672    	85
264	54457 	Multifuncional Samsung 1VT: encravamento de papel	74563    	86
265	54428 	Multifuncional Samsung 2VT: erro no fusor	74812    	86
266	62899 	Notebook Sônia (1VT) imprimir em rede na Oki	         	86
268	59570 	Reinstalar placa de vídeo audi 1VT	73318    	87
267	71085 	Recolocação de no-break APC	75698    	87
269	62899 	Consolidar solução para notebook que não imprime na Oki	75484    	87
270	54596 	Instalação de microcomputador	75738    	89
271	58916 	Instalação notebook Roveda	76157    	89
272	47684 	Instalação de computador para Semana Nacional da Conciliação.	77059    	90
273	59560 	Instalação de mouse do juiz na sala de audiências	77433    	90
275	50067 	Configuração de correio eletrônico	77324    	90
276	54438 	Impressão de Samsung com manchas	77325    	90
274	28312 	Instalação de HP Deskjet	77333    	90
277	58877 	Conserto de ponto de rede	78826    	91
278	Vários	Preparação para atualização automática Adobe Reader 9.3	         	91
279	Vários	Atualização para Service Pack 3 do Windows XP	         	91
280	todos 	Instalação de Service Pack 3	         	92
281	71586 	Instalação de microcomputador	78890    	93
306	Vários	Instalação de drivers para imprimir na Samsung ML-3561	         	98
307	44847 	Troca de switch	87618    	98
282	48683 	Instalação de impressora	81867    	93
288	59571 	Conferir mouse de juiz na sala de audiências	81800    	94
305	59543 	Imagem de monitor muito clara	87504    	98
285	vários	Levantamento monitores CRT	81915    	93
283	56885 	Teste de backup: substituição de drive de fita DAT	81916    	93
287	71598 	Instalação de microcomputador	82054    	94
286	vários	Levantamento de monitores CRT	82256    	94
290	63105 	Trocar microcomputador de lugar	82260    	94
289	68109 	Samsung ML-3561ND da SEDIS com defeito	82005    	94
291	48555 	Recolher microcomputador	82262    	94
304	54596 	Microcomputador não acessa a rede	87506    	98
293	59520 	Recolocar doc-station no lugar de micro que não liga	84304    	95
308	40611 	Teclado não funciona	88279    	99
292	56863 	Troca de drive de fita DAT servidor	84320    	95
295	56863 	Manutenção preventiva servidor	84322    	95
294	63003 	Problemas para acesso ao Hotmail (Dora)	84325    	95
309	54583 	Recolher impressora Oki B4350 para laboratório	88286    	99
310	54583 	Substituir impressora por Laserjet 1320 da 2VT	88288    	99
300	48685 	Recolher impressora laserjet 1320	86619    	97
301	54583 	Movimentar Oki B4350 do gabinete para a sala de audiências	86620    	97
311	54457 	Problema com multifuncional: bandeja ADF	88290    	99
302	62101 	Instalação de impressora Samsung	87062    	96
312	59571 	Passar monitor do juiz para o divisor	88475    	100
296	59543 	Troca de cabos de extensão VGA e divisor de sinal de vídeo	84245    	96
298	56863 	Troca de HD do servidor	87264    	96
303	56863 	Balanceamento das partições do HD do servidor	87303    	96
313	54457 	Verificar problema com multifuncional	88290    	100
315	54583 	Reinstalar impressora Oki na sala de audiências	88567    	100
314	48686 	Retornar impressora Laserjet 1320 à 2ª Vara	88568    	100
316	71694 	Instalação de microcomputador	87855    	100
317	vários	Investigar problemas de lentidão	88708    	100
323	54583 	Movimentar impressora Oki 1VT	89897    	101
320	45282 	Verificar problema com Brother SEDIS	89335    	101
324	54741 	Conserto de ponto de rede - CDM	89841    	101
319	72446 	Instalação de impressora Audi 1VT	89790    	101
318	71702 	Instalação de microcomputador Contadoria 2VT	89791    	101
321	59571 	Alteração nos cabos de extensão VGA Audi 2VT	88475    	101
322	54692 	Instalação multifuncional Samsung CDM	89839    	101
326	64050 	Instalação/atualização driver da Brother para digitalização	90571    	102
328	63880 	Instalação/atualização driver da Brother para digitalização	90573    	102
329	58381 	Instalação/atualização driver da Brother para digitalização	90577    	102
330	53771 	Instalação/atualização driver da Brother para digitalização	90578    	102
331	63882 	Instalação/atualização driver da Brother para digitalização	90581    	102
327	63001 	Correção de problema com ponto de rede	90564    	102
325	63880 	Instalação de microcomputador	90511    	102
334	63001 	Mudar micro de lugar	90563    	102
335	71694 	Instalação de software: OOOFind	90565    	102
332	47640 	Instalação/atualização driver da Brother para digitalização	90584    	102
333	63104 	Instalação/atualização driver da Brother para digitalização	 90585   	102
337	54602 	Atualização driver Brother	90801    	103
339	71694 	Atualização driver Brother	90809    	103
336	54739 	Atualização driver Brother	90799    	103
338	63881 	Atualização driver Brother	90804    	103
340	64139 	Atualização driver Brother	90808    	103
341	63001 	Atualização driver Brother	90807    	103
342	61633 	Instalação de impressora Samsung	90811    	103
344	63107 	Instalação driver impressão Samsung	90829    	103
343	47640 	Instalação driver impressão Samsung	90813    	103
345	54457 	Recolher multifuncional Samsung	90847    	103
346	45299 	Trocar multifuncional Brother de lugar	90854    	103
347	54457 	Reinstalação de multifuncional Samsung	88290    	104
348	54741 	Verificar problema de desligamento de microcomputador	91276    	104
349	      	Levantar problemas de lentidão no SAP1	91063    	104
350	      	Continuação análise de problemas lentidão SAP1	91063    	105
351	45908 	Recolher impressora Xerox para limpeza	91270    	105
352	      	Instalação/configuração Thunderbird	91055    	105
354	71702 	Suporte no uso do PDF Creator	91404    	104
355	64013 	Suporte para envio de declaração de IRPF	91402    	104
356	59510 	Suporte para envio de declaração de IRPF	91399    	104
357	59570 	Instalar software digitalização Samsung	91397    	104
358	62101 	Troca de fusor de impressora Samsung	91028    	106
359	54739 	Dúvida no SAP1	91512    	105
360	47644 	Dúvidas na utilização do digitalizador	91515    	105
361	48686 	Movimentação impressora	91517    	105
362	45908 	Recolher impressora	91520    	105
363	54740 	Instalação PROAD/Firefox	         	105
364	      	Levantamento de equipamentos a serem substituídos	         	106
365	54741 	Trocar cooler de microcomputador	92387    	107
366	45908 	Instalar Xerox Phaser 3130 na sala de audiências	91607    	107
367	61633 	Instalação impressora Samsung	92439    	107
368	      	Verificar problema com multifuncional CDM	93484    	108
369	62102 	Encravamento de papel Samsung 1VT	94333    	109
370	      	Analisar lentidão de rede e SAP1	91063    	109
371	72356 	Instalação de monitor	93964    	109
372	62102 	Trocar fusor da impressora Samsung - 1VT	94805    	110
373	      	Instalar driver de impressão	94803    	110
374	73360 	Instalação de microcomputador	96494    	111
375	73359 	Instalação de microcomputador	96501    	111
376	73357 	Instalação de microcomputador	96503    	111
377	73358 	Instalação de microcomputador	96523    	111
378	47641 	Recolher microcomputador	96665    	111
379	47642 	Recolher microcomputador	96666    	111
380	59571 	Trocar microcomputador de lugar	96668    	111
381	64004 	Trocar microcomputador de lugar	96672    	111
382	47643 	Recolher microcomputador	96673    	111
383	63949 	Trocar microcomputador de lugar	96674    	111
384	73355 	Instalação de microcomputador	96814    	112
385	73354 	Instalação de microcomputador	99450    	112
386	73356 	Instalação de microcomputador	99472    	112
387	73379 	Instalação de microcomputador	96813    	113
388	server	Remanejamento de partições	         	113
389	47673 	Recolher microcomputador	398093   	112
390	46119 	Recolher microcomputador	398123   	112
391	59543 	Trocar microcomputador de lugar	         	112
392	54954 	Recolher monitor	1279708  	114
393	44770 	Recolher monitor	1279712  	114
395	64226 	Trocar monitor de lugar	1279709  	114
394	64324 	Trocar monitor de lugar	1279711  	114
396	73434 	Instalar monitor	1279713  	114
397	73433 	Instalar monitor	1279717  	114
400	66787 	Configurações de segurança micro OAB	1279734  	114
399	73360 	Instalação convênio DETRANNET	1279738  	114
401	64004 	Instalação leitora de cartão Nonus	1279739  	114
402	73358 	Instalação leitora de cartão Nonus	1279742  	114
403	63949 	Instalação leitora de cartão Nonus	1279743  	114
404	61633 	Problema com impressora	1279747  	114
405	72499 	Instalação no-break	1279750  	114
406	58877 	Conserto de ponto de rede	1279751  	114
398	46050 	Problema com multifuncional Brother	1279727  	114
407	45909 	Instalação Xerox Phaser 3130	1279541  	115
408	46050 	Troca de fusão da Brother	1279727  	116
409	72173 	Trocar monitor de lugar	1280122  	116
412	63884 	Alterar configurações de energia	1280126  	116
411	63105 	Alterar configurações de energia	1280129  	116
410	53406 	Trocar monitor de lugar	1280140  	116
413	54466 	Problemas com multifuncional	1280227  	115
414	62101 	Problema para imprimir em duplex	1280236  	115
415	66793 	Configuração máquina OAB	1280241  	115
416	59543 	Instalação notificador malote digital	1280246  	115
417	54742 	Reinstalar notificador malote digital	1280248  	115
418	58384 	Instalação PDFCreator	1280254  	115
419	73437 	Instalação de monitor	1280313  	115
420	73438 	Instalação de monitor	1280315  	115
421	73436 	Instalação de monitor	1280317  	115
422	59146 	Trocar monitor de lugar	1280319  	115
423	63271 	Trocar monitor de lugar	1280329  	115
424	64195 	Trocar monitor de lugar	1280337  	115
425	53400 	Recolher monitor	1280333  	115
426	53935 	Recolher monitor	1280335  	115
427	53398 	Recolher monitor	1280336  	115
428	73817 	Instalação de microcomputador	1280404  	117
429	73816 	Instalação de microcomputador	1280552  	117
430	62101 	Problema para imprimir em duplex	1280236  	117
431	54742 	Trocar microcomputador de lugar	1280815  	117
432	47672 	Recolher microcomputador	1280816  	117
433	53770 	Recolher microcomputador	1280819  	117
434	58876 	Travamentos constantes notebook	1280808  	118
435	63106 	Atualização Internet Explorer	1281087  	118
436	58419 	Instalação microcomputador provisório	1281095  	118
437	63880 	Recolher microcomputador para laboratório	1281093  	118
438	54602 	Instalação driver multifuncional Samsung	1281105  	118
439	63880 	Reinstalar microcomputador	154453   	119
440	63107 	Recolher microcomputador	1703194  	119
441	58419 	Trocar microcomputador de lugar	1703247  	119
442	58419 	Configuração de driver para impressão bandeja 1	1704482  	119
443	73373 	Cópia de dados do micro anterior	1705120  	119
488	76190 	Configuração Mozilla Thunderbird	6844090  	132
474	73882 	Alterar cabeamento balcão	5872317  	128
445	47672 	Formatação e instalação de Ubuntu Linux	1850423  	120
444	62101 	Impressora amassa papel em impressão duplex	1850411  	120
477	73358 	Instalação plugin Banco do Brasil	5872676  	129
446	73816 	Travamentos constantes de microcomputador	2942839  	121
447	73816 	Reinstalar microcomputador no local	3064559  	122
448	73816 	Instalação do módulo de segurança Caixa Econômica	3193436  	122
449	73816 	Instalação do módulo de segurança do Banco do Brasil	3193456  	122
450	73817 	Instalação de Safesign	3193768  	122
451	73817 	Configuração de computador para uso do convênio INFOSEG	3193816  	122
452	63003 	Alteração de resolução de tela	3196523  	122
453	76091 	Instalação de nova impressora na sala de audiências	3248435  	123
454	73882 	Instalação de microcomputador	3029916  	124
455	74660 	Instalação de monitor	3341172  	124
456	45911 	Trocar impressora de lugar	3544123  	123
457	58384 	Configuração de cliente de correio eletrônico	3544185  	123
458	54448 	Problema com multifuncional	3545826  	123
460	58419 	Trocar micro de lugar	4433412  	124
459	59570 	Trocar micro de lugar	4433431  	124
461	58381 	Formatação/instalação Ubuntu Linux	4433484  	124
463	58876 	Problema com Adobe Reader	4433631  	124
462	58876 	Instalação Microsoft Excel	4433783  	124
464	67165 	Problema com monitor	4286655  	125
465	50745 	Troca de drive de fita DAT	4559082  	125
466	63107 	BacenJUD no Internet Explorer	4715602  	126
467	54692 	Impressora apresenta mensagem "Erro LSU"	4938951  	126
468	61633 	Impressora acende luz vermelha ao imprimir em bandeja manual	4770390  	126
469	63001 	Instalação DETRANNET	         	126
470	54742 	Forte ruído no cooler	4993720  	127
471	54742 	Atualização Internet Explorer	4994257  	127
472	63106 	Módulo Banco do Brasil	5871897  	128
475	73357 	Instalação Paperport	5867565  	129
476	63147 	Configuração equipamento para impressão em deskjet em rede	5872176  	129
473	73379 	Alterar cabeamento audi	5872054  	128
480	63003 	Configuração Mozilla Thunderbird	6127490  	130
478	76198 	Instalação notebook	6127560  	130
479	71586 	Trocar microcomputador de lugar	6126996  	130
481	76190 	Instalação de notebook	6142485  	131
482	76191 	Instalação de notebook	6142125  	131
483	76192 	Instalação de notebook	6142000  	131
484	76193 	Instalação de notebook	6142034  	131
485	Vários	Revisão urgente de computadores e impressora	6230368  	132
486	76193 	Instalação do SAP1	6843875  	132
487	76190 	Cópia de arquivos de notebook anterior	6843863  	132
489	56885 	Troca de drive de fita DAT	6911295  	133
490	68109 	Papel preso na impressora	6998127  	134
491	31139 	Reinstalação da impressora	6866816  	134
492	54739 	Recolher microcomputador para laboratório	7023613  	134
494	68109 	Encravamento de papel	7580270  	135
495	45282 	Encravamento de papel	7580271  	135
497	62102 	Problema para puxar papel	7580279  	135
496	64139 	Instalação de driver de impressão	7580289  	135
498	59520 	Instalação doc-station	7594543  	136
501	71586 	Atualização de antivírus	7594428  	136
500	54596 	Atualização de antivírus	7594463  	136
502	71586 	Instalação calculadora Windows	7594586  	136
499	59096 	Atualização de antivírus	7594444  	136
493	62899 	Recolher notebook	7023803  	134
503	54692 	Trocar LSU da Multifuncional Samsung	4938951  	137
504	68113 	Problema com Samsung ML 4551 balcão	8340660  	137
505	      	Problemas mapeamento (prejudicando SAP1)	8502801  	137
506	63147 	Problemas para gerar relatório	8508048  	137
507	48686 	Limpeza de Laserjet 1320	8512861  	137
509	74769 	Instalação de monitor	9028560  	138
508	23098 	Problema com mini-hub	9028591  	138
513	74766 	Instalação de monitor	9028808  	138
511	74765 	Instalação de monitor	9028828  	138
512	74768 	Instalação de monitor	9028884  	138
515	64231 	Trocar monitor de lugar	9028861  	138
516	59194 	Trocar monitor de lugar	9028916  	138
517	59146 	Trocar monitor de lugar	9028951  	138
518	63269 	Trocar monitor de lugar	9028983  	138
514	64195 	Trocar monitor de lugar	9029015  	138
519	47802 	Recolher monitor	9029410  	138
520	50182 	Recolher monitor	9029631  	138
510	74767 	Instalação de monitor	9028533  	138
521	76380 	Instalação de no-break	9029504  	138
522	73882 	Travamento de micro ao gerar GRU	8866184  	137
523	54596 	Instalação de microcomputador	9142224  	139
570	62954 	Configuração de impressão	14986213 	151
524	59096 	Recuperação de auto-textos	9142441  	139
525	58934 	Configuração de mapeamento de rede	9142506  	139
526	76380 	Configuração do no-break	9143033  	139
527	58384 	Recolher microcomputador para laboratório	10316784 	140
528	58419 	Instalar microcomputador reserva provisoriamente	10316801 	140
529	59096 	Trocar microcomputador de lugar	10317078 	140
530	54859 	Trocar microcomputador de lugar	10317059 	140
531	58384 	Reinstalação de microcomputador	10203005 	141
532	71586 	Micro não liga corretamente	10758882 	141
533	59543 	Microcomputador reinicia sozinho	10773320 	141
534	53771 	Movimentar microcomputador	10839318 	142
535	64050 	Movimentar microcomputador	10839333 	142
536	63880 	Recolher microcomputador para laboratório	10839347 	142
537	63880 	Reinstalar microcomputador	11399813 	143
538	64050 	Reinstalar microcomputador	11399883 	143
539	76729 	Instalação de impressora nova	11790869 	144
540	76191 	Problema na tela notebook	11390411 	144
541	68121 	Impressora com problemas	11791041 	145
542	59543 	Microcomputador reiniciando sozinho	10773320 	145
543	73360 	Instalação de driver de impressão	12281503 	144
544	48686 	Trocar impressora de lugar	12282386 	144
545	45908 	Recolher impressora	12283113 	144
546	58949 	Instalar driver de impressão	12538248 	144
547	54740 	Instalar driver de impressão	12538313 	144
551	54448 	Problemas com multifuncional	12402896 	145
552	59543 	Instalação de driver de impressão	 12559250	145
548	22704 	Reparar e recolher estabilizador	12559394 	145
550	59543 	Trocar microcomputador de lugar	12560401 	145
553	76190 	Problema com notebook	13020819 	146
556	76193 	Problemas para assinar autorização consulta IRPF	13033150 	146
554	76738 	Instalação de impressora	13033199 	146
555	76193 	Instalação de driver de impressão	13033340 	146
557	31139 	Conserto de impressora	12279172 	146
558	73359 	Atualização de antivírus	13036473 	146
571	73817 	Configuração de impressão	14986304 	151
559	62972 	Instalação PDFCreator	13038994 	146
560	54661 	Instalação de impressora	13373313 	147
561	73816 	Problemas para impressão	13373737 	147
562	54742 	Limpeza/desfragmentação Windows XP	13375930 	147
563	54428 	Troca de unidade óptica	12830051 	148
565	      	Problemas com impressora Marli	13856979 	149
566	28592 	Problemas com impressora Margarete	         	149
564	72193 	Testes de notebook para o PJe	11790784 	149
567	      	Recolher microcomputador para pré-instalação	13917581 	150
568	74025 	Instalação de microcomputador	13444223 	151
569	68121 	Instalação de impressora	14985612 	151
572	54873 	Configuração de impressão	14986483 	151
573	58384 	Configuração de impressão	14986579 	151
574	71586 	Configuração de impressão	14986707 	151
575	73354 	Configuração de impressão	14986757 	151
576	73356 	Configuração de impressão	14987090 	151
577	63003 	Configuração de impressão	14987147 	151
579	54661 	Trocar multifuncional de lugar	14987207 	151
578	54466 	Recolher multifuncional	14987250 	151
580	63076 	Configuração de impressão	14987614 	151
581	63904 	Configuração de impressão	14987718 	151
582	54742 	Configuração de impressão	14987808 	151
589	76746 	Instalação de impressora	14238744 	152
583	74039 	Instalação de microcomputador	15067874 	152
598	54838 	Recolher microcomputador	15615180 	153
584	74042 	Instalação de microcomputador	15072686 	152
586	74041 	Instalação de microcomputador	15121011 	152
587	74037 	Instalação de microcomputador	15140282 	152
588	74040 	Instalação de microcomputador	15399152 	152
590	68109 	Trocar fusor de impressora	13992258 	152
594	74036 	Instalação de microcomputador	15069561 	153
593	53771 	Configuração do Mozilla Thunderbird	15414586 	152
591	73358 	Trocar microcomputador de lugar	15414529 	152
603	64139 	Trocar microcomputador de lugar	15615182 	152
595	74037 	Instalação de microcomputador	15140282 	153
596	68121 	Troca de fusor de impressora	15121527 	154
597	54661 	Troca de unidade óptica de multifuncional	15474861 	154
599	63885 	Recolher impressora	15615098 	152
600	53771 	Recolher microcomputador	15615376 	152
601	54602 	Recolher microcomputador	15616817 	152
592	54741 	Recolher microcomputador	15414558 	152
602	54739 	Recolher microcomputador	15615080 	152
604	63104 	Trocar microcomputador de lugar	15615143 	152
605	63881 	Trocar microcomputador de lugar	15613877 	152
606	74091 	Instalação de microcomputador	18567778 	155
607	74892 	Instalação de monitor	19053825 	155
608	74893 	Instalação de monitor	19053850 	155
609	74895 	Instalação de monitor	19053873 	155
610	47314 	Recolher monitor	19053900 	155
611	47347 	Recolher monitor	19053926 	155
612	53401 	Recolher monitor	19053969 	155
613	53403 	Recolher monitor	19053993 	155
614	63383 	Trocar monitor de lugar	19054022 	155
615	59570 	Recolher microcomputador	19054303 	155
616	63384 	Trocar monitor de lugar	19054159 	155
617	74947 	Instalação de monitor	19054258 	155
618	74946 	Instalação de monitor	19054225 	155
619	64191 	Recolher monitor	19054189 	155
620	63385 	Trocar monitor de lugar	19054111 	155
621	50745 	Troca de HDs de servidor de rede	19673741 	156
622	63885 	Problema na inicialização do Windows XP	19676016 	156
623	48686 	Problema com impressora	19675803 	156
624	73359 	Instalação de convênio DETRANNET	19675883 	156
625	54457 	Multifuncional com problema	19383715 	157
626	62102 	Impressora com problema	19606585 	157
627	71702 	Problemas para entrar no domínio trtsc	19867201 	157
628	74091 	Trocar microcomputador de lugar	20016133 	157
629	45911 	Problema com impressora	20082796 	158
666	74025 	Trocar microcomputador de lugar	28741564 	174
630	63885 	Reinstalar microcomputador	20513558 	159
631	63076 	Reinstalar microcomputador	20512994 	160
632	63904 	Instalação de ponto de rede na AUDI	21058597 	160
633	54742 	Troca de fonte de microcomputador	20777614 	160
636	54742 	Limpeza de contatos de memória	21332014 	161
637	74038 	Problema com microcomputador	21748315 	162
638	62102 	Problema com impressora	21531895 	162
639	68109 	Problema com fusor	21893041 	162
640	74091 	Instalar adaptador USB/paralelo	20016211 	162
641	74038 	Reinstalação de microcomputador	22059120 	163
642	63884 	Reinstalação de microcomputador	21918228 	163
643	68109 	Troca de fusor Samsung ML-4550	21893041 	163
644	74040 	Substituição de microcomputador	22828279 	164
645	74086 	Recolher microcomputador para laboratório	22962351 	165
667	63904 	Trocar microcomputador de lugar	28741648 	174
668	72284 	Sala de audiência- impossibilidade de visualização.	29084054 	175
669	64121 	Microcomputador não liga	26860393 	176
670	54428 	Problemas de impressão	29642332 	177
671	74042 	Teste de conexão de rede	29695279 	177
679	54692 	Problema com scanner	33415400 	182
635	63904 	Trocar microcomputador de lugar	22964419 	161
634	63904 	Instalação de ponto de rede	22964366 	161
647	67766 	Limpeza de lentes do scanner	22964288 	165
646	76193 	Instalação de driver para token Safenet	22964230 	165
648	74040 	Reinstalar microcomputador	23094679 	166
649	74086 	Reinstalar microcomputador	23371349 	166
650	79646 	Instalar multifuncional	22557004 	166
651	79646 	Instalação dos drivers de impressão da nova multifuncional	23502055 	167
652	54596 	Recolher microcomputador	I64607   	168
653	54742 	Recolher microcomputador	I64608   	168
654	54873 	Recolher microcomputador	I64609   	168
655	78278 	Instalação de microcomputador	23793433 	168
656	78237 	Instalação de microcomputador	23098064 	168
657	78279 	Instalação de microcomputador	23847400 	168
658	28592 	Reinstalar impressora	25229965 	169
659	74041 	Recolher microcomputador para laboratório	25230212 	169
660	63140 	Instalar micro reserva provisoriamente	25230202 	169
661	68116 	Instalação de impressora	25976274 	170
662	54859 	Configuração Mozilla Thunderbird	25778503 	170
663	74041 	Instalação de microcomputador	26194557 	171
664	63881 	Substituição de fonte de microcomputador	27144762 	172
665	73354 	Problemas no Firefox e no BrOffice	28601676 	174
673	63885 	Instalação de e-Token/gravação de certificado	30583934 	178
672	77272 	Acompanhamento de instalação de link de dados	30581315 	178
674	54428 	Teste de fusão	31642947 	179
675	54428 	Desencravamento de papel	31685119 	179
676	      	Acompanhar configuração roteador da Oi Telecom	         	180
677	63106 	Microcomputador sem som	33636740 	182
678	71702 	Microcomputador desligando sozinho	33220779 	182
680	      	Instalação de multifuncional	         	181
681	71702 	Reinstalar microcomputador	34521854 	183
682	63949 	Microcomputador não liga	33971813 	183
683	63147 	Problema pra visualizar relatórios no SAP1	33911058 	183
684	64014 	Problemas para alterar página inicial do IE	34651647 	183
685	68109 	Papel preso	34865569 	184
686	79678 	Instalação de multifuncional	34939565 	185
687	50747 	Troca de HD do servidor	         	186
688	63949 	Instalação de microcomputador	35018885 	187
689	63140 	Instalação de microcomputador	35419501 	187
690	84306 	Instalação de microcomputador	35624030 	188
691	84332 	Instalação de microcomputador	         	188
693	74091 	Instalação de certificado digital	35900755 	189
694	      	Impressora fora da rede	         	190
692	73379 	Microcomputador AUDI com problemas	36113178 	189
695	73379 	Instalação de microcomputador	36791020 	191
696	84388 	Instalação de microcomputador	36801021 	192
697	84309 	Instalação de microcomputador	36936058 	193
698	84308 	Instalação de microcomputador	36936092 	193
699	84307 	Instalação de microcomputador	36936151 	193
700	73355 	Trocar microcomputador de lugar	36936205 	193
701	56863 	Troca de drive de DAT (servidor)	37548568 	194
702	73356 	Configuração de digitalização em rede	37555017 	194
703	68113 	Substituir fusor	37746111 	195
704	73817 	Gravação de certificado digital	38378926 	196
705	73817 	Problemas com placa de vídeo	         	197
706	71586 	Problema com placa de vídeo	         	197
707	84308 	Instalação de mouse para juiz de audiência	38923495 	197
708	84308 	Configuração auto-textos Word	         	197
709	74025 	Configuração de impressão	         	197
710	63003 	Desinstalação psi	         	197
711	63003 	Problema com Microsoft Excel	         	197
712	67766 	Scanner desconfigurado	39807006 	198
742	      	Migração server Balneário Camboriú	         	205
743	      	Migração server Brusque	         	206
713	77272 	INTERNET fora do ar	39804126 	198
714	74039 	Instalação do PaperPort	39811841 	198
744	48683 	Impressora com problemas pra puxar papel	46365334 	207
745	54457 	Problemas na digitalização na multifuncional	46379348 	208
746	87315 	Instalação de monitor e suporte	48341822 	209
747	48683 	Instalação de impressora	48341920 	209
748	74042 	Configuração de monitores em dual view	48664459 	210
725	78821 	Instalação de monitor	         	199
727	53399 	Recolher monitor	40213570 	199
723	78819 	Instalação de monitor	40216120 	199
726	53398 	Recolher monitor	40213610 	199
724	78820 	Instalação de monitor	40216178 	199
728	53400 	Recolher monitor	40213647 	199
720	58384 	Recolher microcomputador	40213685 	199
715	84458 	Instalação de microcomputador	40216319 	199
719	54859 	Recolher microcomputador	40213724 	199
716	84459 	Instalação de microcomputador	40216432 	199
721	59096 	Recolher microcomputador	40213759 	199
722	59543 	Recolher microcomputador	40213825 	199
718	84461 	Instalação de microcomputador	40216633 	199
717	84460 	Instalação de microcomputador	40216544 	199
729	53405 	Recolher monitor	42651822 	200
730	53406 	Recolher Monitor	42651841 	200
731	78823 	Instalação de monitor	42651897 	200
732	78824 	Instalação de monitor	42651922 	200
733	84462 	Instalação de microcomputador	39797194 	200
734	73356 	Problema na inicialização do Windows	42305120 	201
735	74025 	Problemas para ligar o computador	43212866 	201
736	84490 	Instalação de microcomputador	43684848 	202
737	      	Acompanhamento de instalação de novo servidor de rede	         	202
738	59570 	Recolher microcomputador	43695919 	202
739	62102 	Remanejar instalação elétrica da impressora	45885769 	203
740	71694 	Instalação Windows Search	46165584 	203
741	      	Montagem da estrutura para migração do SERVER	         	204
749	54457 	Substituição de bandeja de digitalização	48667110 	210
750	54692 	Conserto de fusão	48667160 	210
751	84462 	Verificar problemas na geração de intimações	48210071 	210
752	74070 	Instalação drivers token G&D (Tosetto)	         	210
753	61633 	Problema com tampa de impressora	49189045 	211
754	63107 	Instalar driver de impressora	49249703 	211
755	      	Instalação de impressora	50549723 	212
756	80352 	Instalação Samsung ML-3750ND	53239228 	213
757	45911 	Recolher impressora Xerox Phaser	53239260 	213
758	86711 	Instalação de multifuncional Lexmark MX711de	53239293 	213
759	52414 	Recolher Multifuncional Brother MFC-8840D 	53239331 	213
760	76729 	Problemas com impressora audiências	53487477 	214
761	76192 	Configuração suporte para uso do e-jus (Juiz Irno)	53296917 	214
762	76215 	Atualizar notebook	53755949 	216
763	73379 	Acompanhar troca de mobiliário sala audiências	53755909 	216
764	71586 	Travamentos constantes	54454252 	217
765	73816 	Travamentos constantes	55269834 	217
766	63885 	Instalar monitor	56353803 	218
768	64014 	Gravação de certificado digital	56354273 	218
767	84306 	Gravação de certificado digital	56354158 	218
770	      	Configuração tablet Sônia Roberts	         	218
769	      	Configuração tablet Luiz Alcântara	         	218
771	      	Configuração tablet Hélio Romero	         	219
772	      	Configuração Tablet Karin Negreiros	         	219
773	54457 	Troca de unidade óptica da SCX-6320F	57892262 	220
774	71694 	Microcomputador não inicializa	58078203 	221
775	71598 	Microcomputador não inicializa	57950358 	221
776	54692 	Substituição de fusor	57691594 	222
777	84309 	Microcomputador não liga	60269170 	223
778	89103 	Reconfigurar servidor de CFTV	60673565 	224
779	84306 	Problemas com complemento Java no Firefox	60673634 	224
782	63885 	Suporte sobre despacho eletrônico no SAP1	61711068 	225
780	58949 	Problema na inicialização de notebook	61711111 	225
781	74037 	Problema para entrar no SERPRO	61711361 	225
783	      	Pane no microcomputador da sala de audiências	64563137 	226
784	71735 	Travamento PJe	64633568 	226
785	      	Obras criação 2VT	         	227
786	      	Obras criação 2VT	         	228
787	      	Obras criação 2VT	         	229
788	      	Obras criação 2VT	         	230
789	      	Obras criação 2VT	         	231
790	      	Obras criação 2VT	         	232
791	      	Obras criação 2VT	         	234
792	45282 	Substituição multifuncional	65160610 	233
793	91379 	Instalação de microcomputador	65967430 	233
794	54713 	Substituição de multifuncional	66222774 	233
795	64013 	Não acessa a rede	67313014 	235
796	76192 	Não abre documentos do PROAD	67259295 	235
797	73379 	Microcomputador não inicializa	67705884 	235
798	      	Instalação de microcomputador	67778592 	236
799	64013 	Substituição de tomadas de rede	67778119 	236
800	91391 	Instalação de microcomputador	68547949 	237
801	73379 	Instalação de microcomputador	68418072 	237
802	68113 	Problema com impressora (balcão)	68155318 	237
803	64014 	Instabilidade microcomputador	68035589 	237
804	46050 	Substituição de multifuncional	67596316 	237
805	54457 	Problema com multifuncional	67255146 	237
806	73816 	Instalação de monitor	68102387 	238
807	46050 	Substituição de multifuncional	69118055 	239
808	      	Integração do terceirizado com as unidades judiciárias	         	241
809	      	Integração do terceirizado com as unidades judiciárias	         	240
810	86759 	Instalação de multifuncional	69508941 	240
811	73975 	Microcomputador com travamentos frequentes	70924919 	242
812	71737 	Microcomputador com travamentos frequentes	70924860 	242
814	73798 	Microcomputador com travamentos frequentes	70925010 	242
813	71736 	Microcomputador com travamentos frequentes	70924968 	242
815	71753 	Microcomputador não liga	75820351 	243
816	74105 	Microcomputador não liga	75818592 	243
817	73822 	Microcomputador desliga sozinho	75821255 	243
818	vários	Instalação de suportes de monitor	         	244
820	78237 	Problemas com plug-in Java	77815114 	246
821	91358 	Instalação plugin PJe Gestão Auxiliar	77816934 	246
822	      	Problemas com no-break central	77876350 	246
823	      	Substituição de no-break	         	247
824	      	Preparação laboratório UNIFEBE para curso PJe	77808437 	248
825	      	Acompanhamento curso PJe UNIFEBE	78781625 	249
827	73816 	Microcomputador de audiência não liga	5495     	251
828	91365 	Instalação de módulo de segurança da Caixa Econômica Federal	R3801    	251
829	74025 	Compartilhamento de pasta micro audiência	R3817    	251
830	74025 	Criar atalho para "Reduzir PJe"	R3818    	251
831	79678 	Impressora com defeito	5517     	251
832	91369 	Problema com suporte do monitor	R3819    	251
826	73793 	Sem acesso ao computador	86129154 	250
819	vários	Instalação de suportes de monitor.	         	245
\.


--
-- Name: tarefa_codigo_seq; Type: SEQUENCE SET; Schema: viagem; Owner: sati
--

SELECT pg_catalog.setval('tarefa_codigo_seq', 853, true);


--
-- Data for Name: tipoeventoreqviagem; Type: TABLE DATA; Schema: viagem; Owner: sati
--

COPY tipoeventoreqviagem (codigo, descricao) FROM stdin;
1	Pedido      
4	Pagamento   
3	Autorização 
2	Aprovação   
\.


--
-- Data for Name: viagem; Type: TABLE DATA; Schema: viagem; Owner: sati
--

COPY viagem (codigo, dataprogramada, dataviagem, cod_progint, cod_municipio_origem, cod_municipio_destino) FROM stdin;
2	2008-11-06	2008-11-06	1	15	2
3	2008-10-17	2008-10-17	1	15	2
4	2008-11-07	2008-11-07	1	15	4
5	2008-11-10	2008-11-10	1	15	2
6	2008-11-19	2008-11-19	1	15	2
8	2008-12-15	2008-12-15	1	15	2
10	2009-01-12	2009-01-12	1	15	2
76	2010-08-23	2010-08-23	1	15	2
7	2008-11-28	2008-11-28	1	15	2
44	2010-01-12	2010-01-12	1	15	2
9	2008-12-16	2008-12-16	1	15	4
77	2010-08-30	2010-08-30	1	15	4
1	2008-10-10	2008-10-10	1	15	2
46	2010-01-19	2010-01-19	1	15	3
12	2009-03-20	2009-03-20	1	15	2
14	2009-03-30	2009-03-30	1	15	2
15	2009-04-01	2009-04-01	1	15	4
48	2010-02-22	2010-02-22	1	15	2
78	2010-08-31	2010-08-31	1	15	2
49	2010-02-24	2010-02-24	1	15	18
47	2010-02-23	2010-02-23	1	15	4
50	2010-03-01	2010-03-01	1	15	2
51	2010-03-09	2010-03-09	1	15	2
52	2010-03-12	2010-03-12	1	15	4
53	2010-03-19	2010-03-19	1	15	4
19	2009-04-16	2009-04-16	1	15	2
79	2010-09-17	2010-09-17	1	15	2
54	2010-03-26	2010-03-26	1	15	2
18	2009-04-17	2009-04-17	1	15	4
24	2009-05-11	2009-05-11	1	15	20
25	2009-05-22	2009-05-22	1	15	2
26	2009-06-04	2009-06-04	1	15	4
27	2009-06-12	2009-06-12	1	15	2
28	2009-06-22	2009-06-22	1	15	2
29	2009-06-29	2009-06-29	1	15	2
30	2009-08-10	2009-08-10	1	15	2
31	2009-08-11	2009-08-11	1	15	2
33	2009-08-14	2009-08-14	1	15	2
32	2009-08-24	2009-08-25	1	15	2
34	2009-09-18	2009-09-18	1	15	4
35	2009-09-25	2009-09-25	1	15	2
36	2009-09-28	2009-09-28	1	15	2
37	2009-10-23	2009-10-23	1	15	2
38	2009-11-06	2009-11-06	1	15	4
39	2009-11-09	2009-11-09	1	15	3
40	2009-11-10	2009-11-10	1	15	2
41	2009-11-26	2009-11-26	1	15	22
42	2009-12-01	2009-12-02	1	15	2
43	2009-12-17	2009-12-17	1	15	4
80	2010-09-24	2010-09-24	1	15	2
58	2010-04-19	2010-04-19	1	15	4
56	2010-04-22	2010-04-22	1	15	2
82	2010-09-28	2010-09-28	1	15	2
59	2010-04-23	2010-04-23	1	15	3
60	2010-04-26	2010-04-26	1	15	3
57	2010-04-27	2010-04-27	1	15	4
61	2010-04-28	2010-04-28	1	15	2
62	2010-05-07	2010-05-07	1	15	4
63	2010-05-13	2010-05-13	1	15	2
65	2010-05-17	2010-05-17	1	15	2
66	2010-05-18	2010-05-18	1	15	2
64	2010-06-04	2010-06-04	1	15	4
67	2010-06-11	2010-06-11	1	15	4
68	2010-06-23	2010-06-23	1	15	3
69	2010-06-24	2010-06-24	1	15	2
70	2010-06-30	2010-06-30	1	15	2
71	2010-07-03	2010-07-03	1	15	4
72	2010-07-07	2010-07-07	1	15	4
73	2010-08-12	2010-08-12	1	15	2
74	2010-08-19	2010-08-19	1	15	4
75	2010-08-19	2010-08-19	1	15	2
81	2010-10-01	2010-10-01	1	15	4
83	2010-09-30	2010-09-30	1	15	2
84	2010-10-05	2010-10-05	1	15	2
85	2010-10-25	2010-10-25	1	15	4
86	2010-10-28	2010-10-28	1	15	2
87	2010-11-05	2010-11-05	1	15	2
89	2010-11-12	2010-11-12	1	15	4
90	2010-11-24	2010-11-24	1	15	3
91	2010-12-13	2010-12-13	1	15	2
92	2010-12-16	2010-12-16	1	15	4
93	2011-01-27	2011-01-27	1	15	4
104	2011-05-05	2011-05-05	1	15	2
94	2011-02-01	2011-01-31	1	15	2
105	2011-05-06	2011-05-06	1	15	2
95	2011-02-17	2011-02-17	1	15	4
106	2011-05-10	2011-05-10	1	15	4
97	2011-03-11	2011-03-11	1	15	2
96	2011-03-18	2011-03-18	1	15	4
98	2011-03-21	2011-03-21	1	15	4
99	2011-03-29	2011-03-29	1	15	2
100	2011-04-04	2011-04-04	1	15	2
101	2011-04-14	2011-04-14	1	15	2
102	2011-04-26	2011-04-26	1	15	2
103	2011-04-28	2011-04-28	1	15	2
107	2011-05-17	2011-05-17	1	15	2
108	2011-05-27	2011-05-27	1	15	2
109	2011-06-06	2011-06-06	1	15	2
110	2011-06-10	2011-06-10	1	15	2
111	2011-07-01	2011-07-01	1	15	2
122	2011-10-18	2011-10-18	1	15	4
113	2011-07-08	2011-07-08	1	15	2
112	2011-07-11	2011-07-11	1	15	4
114	2011-08-19	2011-08-19	1	15	2
116	2011-08-25	2011-08-25	1	15	2
115	2011-08-26	2011-08-26	1	15	4
117	2011-09-01	2011-09-01	1	15	4
118	2011-09-05	2011-09-06	1	15	2
119	2011-09-15	2011-09-15	1	15	2
120	2011-09-19	2011-09-19	1	15	4
121	2011-10-11	2011-10-11	1	15	4
123	2011-10-24	2011-10-24	1	15	4
124	2011-11-07	2011-11-07	1	15	2
125	2011-11-11	2011-11-11	1	15	2
126	2011-11-21	2011-11-21	1	15	2
127	2011-11-22	2011-11-22	1	15	4
129	2011-12-09	2011-12-09	1	15	2
130	2011-12-13	2011-12-13	1	15	4
128	2011-12-07	2011-12-07	1	15	2
131	2011-12-15	2011-12-15	1	15	2
133	2012-01-12	2012-01-12	1	15	4
132	2012-01-09	2012-01-09	1	15	2
134	2012-01-17	2012-01-17	1	15	2
135	2012-01-27	2012-01-27	1	15	2
136	2012-01-30	2012-01-30	1	15	4
138	2012-02-24	2012-02-24	1	15	4
137	2012-02-27	2012-02-27	1	15	2
139	2012-02-28	2012-02-28	1	15	4
140	2012-03-20	2012-03-20	1	15	4
141	2012-03-29	2012-03-29	1	15	4
142	2012-04-03	2012-04-03	1	15	2
143	2012-04-12	2012-04-12	1	15	2
144	2012-04-30	2012-04-30	1	15	2
145	2012-05-04	2012-05-04	1	15	4
146	2012-05-11	2012-05-11	1	15	2
147	2012-05-16	2012-05-16	1	15	4
148	2012-05-18	2012-05-18	1	15	2
150	2012-05-24	2012-05-24	1	15	4
149	2012-05-23	2012-05-23	1	15	2
151	2012-06-11	2012-06-11	1	15	4
152	2012-06-18	2012-06-18	1	15	2
154	2012-06-21	2012-06-21	1	15	4
153	2012-06-21	2012-06-21	1	15	2
155	2012-08-10	2012-08-10	1	15	2
156	2012-08-24	2012-08-24	1	15	2
157	2012-08-28	2012-08-28	1	15	2
158	2012-09-03	2012-09-03	1	15	4
159	2012-09-05	2012-09-05	1	15	2
160	2012-09-14	2012-09-14	1	15	4
209	2013-10-23	2013-10-23	1	15	4
162	2012-09-28	2012-09-28	1	15	2
161	2012-09-21	2012-09-21	1	15	4
163	2012-10-04	2012-10-04	1	15	2
164	2012-10-15	2012-10-15	1	15	2
165	2012-10-17	2012-10-17	1	15	2
210	2013-10-28	2013-10-29	1	15	2
166	2012-10-23	2012-10-23	1	15	2
167	2012-10-24	2012-10-24	1	15	2
168	2012-10-31	2012-10-31	1	15	4
169	2012-11-19	2012-11-19	1	15	2
170	2012-11-27	2012-11-27	1	15	4
171	2012-11-29	2012-11-29	1	15	2
172	2012-12-14	2012-12-14	1	15	2
174	2013-01-09	2013-01-09	1	15	4
175	2013-01-14	2013-01-14	1	15	18
176	2013-01-16	2013-01-16	1	15	18
177	2013-01-22	2013-01-22	1	15	2
178	2013-02-04	2013-02-04	1	15	2
179	2013-02-20	2013-02-20	1	15	2
180	2013-02-26	2013-02-26	1	15	2
182	2013-03-25	2013-03-25	1	15	2
183	2013-04-03	2013-04-03	1	15	2
184	2013-04-08	2013-04-08	1	15	2
185	2013-04-11	2013-04-11	1	15	4
186	2013-04-12	2013-04-12	1	15	25
187	2013-04-15	2013-04-15	1	15	2
181	2013-03-15	2013-03-15	1	15	2
188	2013-04-19	2013-04-19	1	15	2
189	2013-04-23	2013-04-23	1	15	2
190	2013-04-26	2013-04-29	1	15	4
191	2013-05-03	2013-05-03	1	15	2
192	2013-05-06	2013-05-06	1	15	2
193	2013-05-07	2013-05-07	1	15	4
194	2013-05-17	2013-05-20	1	15	4
195	2013-05-21	2013-05-21	1	15	2
196	2013-05-29	2013-05-29	1	15	4
197	2013-06-06	2013-06-06	1	15	4
198	2013-06-19	2013-06-19	1	15	2
199	2013-06-21	2013-06-21	1	15	4
200	2013-06-28	2013-06-28	1	15	2
201	2013-08-06	2013-08-06	1	15	4
202	2013-08-15	2013-08-15	1	15	2
203	2013-09-19	2013-09-19	1	15	2
204	2013-08-28	2013-08-28	1	15	4
205	2013-08-29	2013-08-29	1	15	2
206	2013-09-12	2013-09-12	1	15	4
207	2013-09-26	2013-09-26	1	15	4
208	2013-09-30	2013-09-30	1	15	2
213	2014-01-07	2014-01-07	1	15	4
212	2013-11-27	2013-11-27	1	15	2
211	2013-11-11	2013-11-11	1	15	2
214	2014-01-13	2014-01-13	1	15	2
216	2014-01-17	2014-01-17	1	15	2
215	2014-01-14	2014-01-14	1	15	4
217	2014-02-11	2014-02-11	1	15	4
218	2014-02-26	2014-02-26	1	15	2
219	2014-02-27	2014-02-27	1	15	4
220	2014-03-24	2014-03-24	1	15	2
221	2014-04-01	2014-04-01	1	15	2
222	2014-04-14	2014-04-14	1	15	2
223	2014-05-02	2014-05-02	1	15	4
224	2014-05-06	\N	1	15	2
225	2014-05-22	\N	1	15	2
226	2014-07-09	2014-07-09	1	15	18
227	2014-08-04	2014-08-04	1	15	4
228	2014-08-05	2014-08-05	1	15	4
229	2014-08-06	2014-08-06	1	15	4
230	2014-08-07	2014-08-07	1	15	4
231	2014-08-08	2014-08-08	1	15	4
232	2014-08-14	2014-08-14	1	15	4
233	2014-08-15	2014-08-15	1	15	2
234	2014-08-15	2014-08-15	1	15	4
236	2014-09-01	2014-09-01	1	15	2
237	2014-09-11	2014-09-11	1	15	2
238	2014-09-15	2014-09-15	1	15	4
239	2014-09-19	2014-09-19	1	15	2
241	2014-09-23	2014-09-23	1	15	4
240	2014-09-24	2014-09-24	1	15	2
235	2014-08-25	2014-08-25	1	15	2
242	2014-10-17	2014-10-17	1	15	18
243	2015-01-13	2015-01-13	1	15	18
244	2015-01-21	2015-01-21	1	15	4
245	2015-01-22	2015-01-22	1	15	4
246	2015-02-09	2015-02-09	1	15	4
247	2015-02-19	\N	1	15	4
248	2015-02-20	\N	1	15	4
249	2015-02-23	\N	1	15	4
250	2015-06-23	2015-06-23	1	15	18
251	2016-02-03	2016-02-03	1	15	4
\.


--
-- Name: viagem_codigo_seq; Type: SEQUENCE SET; Schema: viagem; Owner: sati
--

SELECT pg_catalog.setval('viagem_codigo_seq', 270, true);


SET search_path = backup, pg_catalog;

--
-- Name: conjunto_fita_pkey; Type: CONSTRAINT; Schema: backup; Owner: sati; Tablespace: 
--

ALTER TABLE ONLY conjunto_fita
    ADD CONSTRAINT conjunto_fita_pkey PRIMARY KEY (id);


--
-- Name: pk_backup_estacao; Type: CONSTRAINT; Schema: backup; Owner: sati; Tablespace: 
--

ALTER TABLE ONLY backup_estacao
    ADD CONSTRAINT pk_backup_estacao PRIMARY KEY (id);


--
-- Name: pk_backup_fita; Type: CONSTRAINT; Schema: backup; Owner: sati; Tablespace: 
--

ALTER TABLE ONLY backup_fita
    ADD CONSTRAINT pk_backup_fita PRIMARY KEY (id);


--
-- Name: pk_backup_fita_fita_dados; Type: CONSTRAINT; Schema: backup; Owner: sati; Tablespace: 
--

ALTER TABLE ONLY backup_fita_fita_dados
    ADD CONSTRAINT pk_backup_fita_fita_dados PRIMARY KEY (backup_fita_id, fita_dados_id);


--
-- Name: pk_categoria_fita_dados; Type: CONSTRAINT; Schema: backup; Owner: sati; Tablespace: 
--

ALTER TABLE ONLY categoria_fita_dados
    ADD CONSTRAINT pk_categoria_fita_dados PRIMARY KEY (id);


--
-- Name: pk_conjunto_fitas; Type: CONSTRAINT; Schema: backup; Owner: sati; Tablespace: 
--

ALTER TABLE ONLY conjunto_fitas
    ADD CONSTRAINT pk_conjunto_fitas PRIMARY KEY (id);


--
-- Name: pk_designacao_fita; Type: CONSTRAINT; Schema: backup; Owner: sati; Tablespace: 
--

ALTER TABLE ONLY designacao_fita
    ADD CONSTRAINT pk_designacao_fita PRIMARY KEY (id);


--
-- Name: pk_falha_hardware; Type: CONSTRAINT; Schema: backup; Owner: sati; Tablespace: 
--

ALTER TABLE ONLY falha_hardware
    ADD CONSTRAINT pk_falha_hardware PRIMARY KEY (id);


--
-- Name: pk_fita; Type: CONSTRAINT; Schema: backup; Owner: sati; Tablespace: 
--

ALTER TABLE ONLY fita
    ADD CONSTRAINT pk_fita PRIMARY KEY (id);


--
-- Name: pk_fita_dados; Type: CONSTRAINT; Schema: backup; Owner: sati; Tablespace: 
--

ALTER TABLE ONLY fita_dados
    ADD CONSTRAINT pk_fita_dados PRIMARY KEY (id);


--
-- Name: pk_fita_limpeza; Type: CONSTRAINT; Schema: backup; Owner: sati; Tablespace: 
--

ALTER TABLE ONLY fita_limpeza
    ADD CONSTRAINT pk_fita_limpeza PRIMARY KEY (id);


--
-- Name: pk_limpeza; Type: CONSTRAINT; Schema: backup; Owner: sati; Tablespace: 
--

ALTER TABLE ONLY limpeza
    ADD CONSTRAINT pk_limpeza PRIMARY KEY (id);


--
-- Name: pk_local_backup_estacao; Type: CONSTRAINT; Schema: backup; Owner: sati; Tablespace: 
--

ALTER TABLE ONLY local_backup_estacao
    ADD CONSTRAINT pk_local_backup_estacao PRIMARY KEY (id);


--
-- Name: pk_ocorrencia; Type: CONSTRAINT; Schema: backup; Owner: sati; Tablespace: 
--

ALTER TABLE ONLY ocorrencia
    ADD CONSTRAINT pk_ocorrencia PRIMARY KEY (id);


--
-- Name: pk_outras_ocorrencias; Type: CONSTRAINT; Schema: backup; Owner: sati; Tablespace: 
--

ALTER TABLE ONLY outra_ocorrencia
    ADD CONSTRAINT pk_outras_ocorrencias PRIMARY KEY (id);


--
-- Name: pk_teste_backup; Type: CONSTRAINT; Schema: backup; Owner: sati; Tablespace: 
--

ALTER TABLE ONLY teste_backup
    ADD CONSTRAINT pk_teste_backup PRIMARY KEY (id);


--
-- Name: pk_tipo_teste_backup; Type: CONSTRAINT; Schema: backup; Owner: sati; Tablespace: 
--

ALTER TABLE ONLY tipo_teste_backup
    ADD CONSTRAINT pk_tipo_teste_backup PRIMARY KEY (id);


--
-- Name: unq_backup_fita_tipo_teste_backup; Type: CONSTRAINT; Schema: backup; Owner: sati; Tablespace: 
--

ALTER TABLE ONLY teste_backup
    ADD CONSTRAINT unq_backup_fita_tipo_teste_backup UNIQUE (tipo_teste_backup_id, backup_fita_id);


--
-- Name: unq_equipamento_backup_estacao; Type: CONSTRAINT; Schema: backup; Owner: sati; Tablespace: 
--

ALTER TABLE ONLY local_backup_estacao
    ADD CONSTRAINT unq_equipamento_backup_estacao UNIQUE (backup_estacao_id, equipamento_id);


--
-- Name: unq_municipio_designacao_conjunto_fitas; Type: CONSTRAINT; Schema: backup; Owner: sati; Tablespace: 
--

ALTER TABLE ONLY conjunto_fitas
    ADD CONSTRAINT unq_municipio_designacao_conjunto_fitas UNIQUE (designacao_fita_id, municipio_id);


SET search_path = calendario, pg_catalog;

--
-- Name: pk_excecao_feriado_nacional; Type: CONSTRAINT; Schema: calendario; Owner: sati; Tablespace: 
--

ALTER TABLE ONLY excecao_feriado_nacional
    ADD CONSTRAINT pk_excecao_feriado_nacional PRIMARY KEY (codigo);


--
-- Name: pk_excecao_transferencia; Type: CONSTRAINT; Schema: calendario; Owner: sati; Tablespace: 
--

ALTER TABLE ONLY excecao_transferencia
    ADD CONSTRAINT pk_excecao_transferencia PRIMARY KEY (codigo);


--
-- Name: pk_feriado; Type: CONSTRAINT; Schema: calendario; Owner: sati; Tablespace: 
--

ALTER TABLE ONLY feriado
    ADD CONSTRAINT pk_feriado PRIMARY KEY (codigo);


--
-- Name: pk_feriado_fixo; Type: CONSTRAINT; Schema: calendario; Owner: sati; Tablespace: 
--

ALTER TABLE ONLY feriado_fixo
    ADD CONSTRAINT pk_feriado_fixo PRIMARY KEY (codigo);


--
-- Name: pk_feriado_movel; Type: CONSTRAINT; Schema: calendario; Owner: sati; Tablespace: 
--

ALTER TABLE ONLY feriado_movel
    ADD CONSTRAINT pk_feriado_movel PRIMARY KEY (codigo);


--
-- Name: pk_transferencia; Type: CONSTRAINT; Schema: calendario; Owner: sati; Tablespace: 
--

ALTER TABLE ONLY transferencia
    ADD CONSTRAINT pk_transferencia PRIMARY KEY (codigo);


--
-- Name: unq_excecao_transferencia_municipio_transferencia; Type: CONSTRAINT; Schema: calendario; Owner: sati; Tablespace: 
--

ALTER TABLE ONLY excecao_transferencia
    ADD CONSTRAINT unq_excecao_transferencia_municipio_transferencia UNIQUE (cod_transferencia, cod_municipio);


SET search_path = certificacao, pg_catalog;

--
-- Name: fk_certificado; Type: CONSTRAINT; Schema: certificacao; Owner: sati; Tablespace: 
--

ALTER TABLE ONLY certificado
    ADD CONSTRAINT fk_certificado PRIMARY KEY (id);


--
-- Name: fk_marca_etoken; Type: CONSTRAINT; Schema: certificacao; Owner: sati; Tablespace: 
--

ALTER TABLE ONLY marca_etoken
    ADD CONSTRAINT fk_marca_etoken PRIMARY KEY (id);


--
-- Name: fk_status_certificado; Type: CONSTRAINT; Schema: certificacao; Owner: sati; Tablespace: 
--

ALTER TABLE ONLY status_certificado
    ADD CONSTRAINT fk_status_certificado PRIMARY KEY (id);


--
-- Name: unq_marca_etoken_nome; Type: CONSTRAINT; Schema: certificacao; Owner: sati; Tablespace: 
--

ALTER TABLE ONLY marca_etoken
    ADD CONSTRAINT unq_marca_etoken_nome UNIQUE (nome);


--
-- Name: unq_status_certificado_descricao; Type: CONSTRAINT; Schema: certificacao; Owner: sati; Tablespace: 
--

ALTER TABLE ONLY status_certificado
    ADD CONSTRAINT unq_status_certificado_descricao UNIQUE (descricao);


SET search_path = equipamentos, pg_catalog;

--
-- Name: pk_equipamento_unidade; Type: CONSTRAINT; Schema: equipamentos; Owner: sati; Tablespace: 
--

ALTER TABLE ONLY equipamento_unidade
    ADD CONSTRAINT pk_equipamento_unidade PRIMARY KEY (cod_equipamento);


--
-- Name: pk_equipamento_usuario; Type: CONSTRAINT; Schema: equipamentos; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY equipamento_usuario
    ADD CONSTRAINT pk_equipamento_usuario PRIMARY KEY (equipamento_cod);


--
-- Name: pk_historico; Type: CONSTRAINT; Schema: equipamentos; Owner: sati; Tablespace: 
--

ALTER TABLE ONLY historico
    ADD CONSTRAINT pk_historico PRIMARY KEY (codigo);


--
-- Name: pk_tipo_equipamento; Type: CONSTRAINT; Schema: equipamentos; Owner: sati; Tablespace: 
--

ALTER TABLE ONLY tipo_equipamento
    ADD CONSTRAINT pk_tipo_equipamento PRIMARY KEY (codigo);


--
-- Name: pkequipamento; Type: CONSTRAINT; Schema: equipamentos; Owner: sati; Tablespace: 
--

ALTER TABLE ONLY equipamento
    ADD CONSTRAINT pkequipamento PRIMARY KEY (codigo);


--
-- Name: pklote; Type: CONSTRAINT; Schema: equipamentos; Owner: sati; Tablespace: 
--

ALTER TABLE ONLY lote
    ADD CONSTRAINT pklote PRIMARY KEY (codigo);


--
-- Name: pkmodelo; Type: CONSTRAINT; Schema: equipamentos; Owner: sati; Tablespace: 
--

ALTER TABLE ONLY modelo
    ADD CONSTRAINT pkmodelo PRIMARY KEY (codigo);


--
-- Name: unq_nome_tipo_equipamento; Type: CONSTRAINT; Schema: equipamentos; Owner: sati; Tablespace: 
--

ALTER TABLE ONLY tipo_equipamento
    ADD CONSTRAINT unq_nome_tipo_equipamento UNIQUE (nome);


--
-- Name: unq_tombo_equipamento; Type: CONSTRAINT; Schema: equipamentos; Owner: sati; Tablespace: 
--

ALTER TABLE ONLY equipamento
    ADD CONSTRAINT unq_tombo_equipamento UNIQUE (tombo);


SET search_path = public, pg_catalog;

--
-- Name: pk_usuario_final; Type: CONSTRAINT; Schema: public; Owner: sati; Tablespace: 
--

ALTER TABLE ONLY usuario_final
    ADD CONSTRAINT pk_usuario_final PRIMARY KEY (id);


--
-- Name: pkareati; Type: CONSTRAINT; Schema: public; Owner: sati; Tablespace: 
--

ALTER TABLE ONLY areati
    ADD CONSTRAINT pkareati PRIMARY KEY (codigo);


--
-- Name: pkmunicipio; Type: CONSTRAINT; Schema: public; Owner: sati; Tablespace: 
--

ALTER TABLE ONLY municipio
    ADD CONSTRAINT pkmunicipio PRIMARY KEY (codigo);


--
-- Name: pkprogint; Type: CONSTRAINT; Schema: public; Owner: sati; Tablespace: 
--

ALTER TABLE ONLY progint
    ADD CONSTRAINT pkprogint PRIMARY KEY (codigo);


--
-- Name: pkunidade; Type: CONSTRAINT; Schema: public; Owner: sati; Tablespace: 
--

ALTER TABLE ONLY unidade
    ADD CONSTRAINT pkunidade PRIMARY KEY (codigo);


--
-- Name: setor_pkey; Type: CONSTRAINT; Schema: public; Owner: sati; Tablespace: 
--

ALTER TABLE ONLY setor
    ADD CONSTRAINT setor_pkey PRIMARY KEY (codigo);


--
-- Name: unq_municipio_areati; Type: CONSTRAINT; Schema: public; Owner: sati; Tablespace: 
--

ALTER TABLE ONLY areati
    ADD CONSTRAINT unq_municipio_areati UNIQUE (cod_municipiosede);


--
-- Name: unq_nome_areati; Type: CONSTRAINT; Schema: public; Owner: sati; Tablespace: 
--

ALTER TABLE ONLY areati
    ADD CONSTRAINT unq_nome_areati UNIQUE (nome);


--
-- Name: unq_nome_municipio; Type: CONSTRAINT; Schema: public; Owner: sati; Tablespace: 
--

ALTER TABLE ONLY municipio
    ADD CONSTRAINT unq_nome_municipio UNIQUE (nome);


--
-- Name: unq_nome_progint; Type: CONSTRAINT; Schema: public; Owner: sati; Tablespace: 
--

ALTER TABLE ONLY progint
    ADD CONSTRAINT unq_nome_progint UNIQUE (nome);


SET search_path = redes, pg_catalog;

--
-- Name: pk_rede_municipipo; Type: CONSTRAINT; Schema: redes; Owner: sati; Tablespace: 
--

ALTER TABLE ONLY rede_municipio
    ADD CONSTRAINT pk_rede_municipipo PRIMARY KEY (cod_rede);


--
-- Name: pkcategoriaelementorede; Type: CONSTRAINT; Schema: redes; Owner: sati; Tablespace: 
--

ALTER TABLE ONLY categoriaelementorede
    ADD CONSTRAINT pkcategoriaelementorede PRIMARY KEY (codigo);


--
-- Name: pkelementorede; Type: CONSTRAINT; Schema: redes; Owner: sati; Tablespace: 
--

ALTER TABLE ONLY elementorede
    ADD CONSTRAINT pkelementorede PRIMARY KEY (codigo);


--
-- Name: pkestacao; Type: CONSTRAINT; Schema: redes; Owner: sati; Tablespace: 
--

ALTER TABLE ONLY estacao
    ADD CONSTRAINT pkestacao PRIMARY KEY (codigo);


--
-- Name: pkmodulo; Type: CONSTRAINT; Schema: redes; Owner: sati; Tablespace: 
--

ALTER TABLE ONLY modulo
    ADD CONSTRAINT pkmodulo PRIMARY KEY (codigo);


--
-- Name: pkpanel; Type: CONSTRAINT; Schema: redes; Owner: sati; Tablespace: 
--

ALTER TABLE ONLY panel
    ADD CONSTRAINT pkpanel PRIMARY KEY (codigo);


--
-- Name: pkrack; Type: CONSTRAINT; Schema: redes; Owner: sati; Tablespace: 
--

ALTER TABLE ONLY rack
    ADD CONSTRAINT pkrack PRIMARY KEY (codigo);


--
-- Name: pkrangeip; Type: CONSTRAINT; Schema: redes; Owner: sati; Tablespace: 
--

ALTER TABLE ONLY rangeip
    ADD CONSTRAINT pkrangeip PRIMARY KEY (codigo);


--
-- Name: pkrede; Type: CONSTRAINT; Schema: redes; Owner: sati; Tablespace: 
--

ALTER TABLE ONLY rede
    ADD CONSTRAINT pkrede PRIMARY KEY (codigo);


--
-- Name: pksegmento; Type: CONSTRAINT; Schema: redes; Owner: sati; Tablespace: 
--

ALTER TABLE ONLY segmento
    ADD CONSTRAINT pksegmento PRIMARY KEY (codigo);


--
-- Name: pkservidor; Type: CONSTRAINT; Schema: redes; Owner: sati; Tablespace: 
--

ALTER TABLE ONLY servidor
    ADD CONSTRAINT pkservidor PRIMARY KEY (codigo);


--
-- Name: pktipoconector; Type: CONSTRAINT; Schema: redes; Owner: sati; Tablespace: 
--

ALTER TABLE ONLY tipoconector
    ADD CONSTRAINT pktipoconector PRIMARY KEY (codigo);


--
-- Name: pktipomodulo; Type: CONSTRAINT; Schema: redes; Owner: sati; Tablespace: 
--

ALTER TABLE ONLY tipomodulo
    ADD CONSTRAINT pktipomodulo PRIMARY KEY (codigo);


--
-- Name: pktomada; Type: CONSTRAINT; Schema: redes; Owner: sati; Tablespace: 
--

ALTER TABLE ONLY tomada
    ADD CONSTRAINT pktomada PRIMARY KEY (codigo);


--
-- Name: pktomadapanel; Type: CONSTRAINT; Schema: redes; Owner: sati; Tablespace: 
--

ALTER TABLE ONLY tomadapanel
    ADD CONSTRAINT pktomadapanel PRIMARY KEY (codigo);


--
-- Name: pktomadaremota; Type: CONSTRAINT; Schema: redes; Owner: sati; Tablespace: 
--

ALTER TABLE ONLY tomadaremota
    ADD CONSTRAINT pktomadaremota PRIMARY KEY (codigo);


--
-- Name: unq_elementorede_ip; Type: CONSTRAINT; Schema: redes; Owner: sati; Tablespace: 
--

ALTER TABLE ONLY elementorede
    ADD CONSTRAINT unq_elementorede_ip UNIQUE (ip);


--
-- Name: unq_elementorede_mac; Type: CONSTRAINT; Schema: redes; Owner: sati; Tablespace: 
--

ALTER TABLE ONLY elementorede
    ADD CONSTRAINT unq_elementorede_mac UNIQUE (mac);


--
-- Name: unq_elementorede_nome; Type: CONSTRAINT; Schema: redes; Owner: sati; Tablespace: 
--

ALTER TABLE ONLY elementorede
    ADD CONSTRAINT unq_elementorede_nome UNIQUE (nome);


--
-- Name: unq_elementorede_tombo; Type: CONSTRAINT; Schema: redes; Owner: sati; Tablespace: 
--

ALTER TABLE ONLY elementorede
    ADD CONSTRAINT unq_elementorede_tombo UNIQUE (tombo);


--
-- Name: unq_estacao_nome; Type: CONSTRAINT; Schema: redes; Owner: sati; Tablespace: 
--

ALTER TABLE ONLY estacao
    ADD CONSTRAINT unq_estacao_nome UNIQUE (nome);


--
-- Name: unq_municipio_rack_id; Type: CONSTRAINT; Schema: redes; Owner: sati; Tablespace: 
--

ALTER TABLE ONLY rack
    ADD CONSTRAINT unq_municipio_rack_id UNIQUE (identificacao, cod_municipio);


--
-- Name: unq_nome_tipoconector; Type: CONSTRAINT; Schema: redes; Owner: sati; Tablespace: 
--

ALTER TABLE ONLY tipoconector
    ADD CONSTRAINT unq_nome_tipoconector UNIQUE (nome);


--
-- Name: unq_nome_tipomodulo; Type: CONSTRAINT; Schema: redes; Owner: sati; Tablespace: 
--

ALTER TABLE ONLY tipomodulo
    ADD CONSTRAINT unq_nome_tipomodulo UNIQUE (nome);


--
-- Name: unq_rack_nome_panel; Type: CONSTRAINT; Schema: redes; Owner: sati; Tablespace: 
--

ALTER TABLE ONLY panel
    ADD CONSTRAINT unq_rack_nome_panel UNIQUE (nome, cod_rack);


--
-- Name: unq_rangeip_rede_categoria; Type: CONSTRAINT; Schema: redes; Owner: sati; Tablespace: 
--

ALTER TABLE ONLY rangeip
    ADD CONSTRAINT unq_rangeip_rede_categoria UNIQUE (cod_rede, cod_categoria);


--
-- Name: unq_rede_endereco; Type: CONSTRAINT; Schema: redes; Owner: sati; Tablespace: 
--

ALTER TABLE ONLY rede
    ADD CONSTRAINT unq_rede_endereco UNIQUE (endereco);


--
-- Name: unq_rede_nome; Type: CONSTRAINT; Schema: redes; Owner: sati; Tablespace: 
--

ALTER TABLE ONLY rede
    ADD CONSTRAINT unq_rede_nome UNIQUE (nome);


--
-- Name: unq_servidor_nome; Type: CONSTRAINT; Schema: redes; Owner: sati; Tablespace: 
--

ALTER TABLE ONLY servidor
    ADD CONSTRAINT unq_servidor_nome UNIQUE (nome);


SET search_path = viagem, pg_catalog;

--
-- Name: pkeventoreqviagem; Type: CONSTRAINT; Schema: viagem; Owner: sati; Tablespace: 
--

ALTER TABLE ONLY eventoreqviagem
    ADD CONSTRAINT pkeventoreqviagem PRIMARY KEY (codigo);


--
-- Name: pktarefa; Type: CONSTRAINT; Schema: viagem; Owner: sati; Tablespace: 
--

ALTER TABLE ONLY tarefa
    ADD CONSTRAINT pktarefa PRIMARY KEY (codigo);


--
-- Name: pktipoeventoreqviagem; Type: CONSTRAINT; Schema: viagem; Owner: sati; Tablespace: 
--

ALTER TABLE ONLY tipoeventoreqviagem
    ADD CONSTRAINT pktipoeventoreqviagem PRIMARY KEY (codigo);


--
-- Name: pkviagem; Type: CONSTRAINT; Schema: viagem; Owner: sati; Tablespace: 
--

ALTER TABLE ONLY viagem
    ADD CONSTRAINT pkviagem PRIMARY KEY (codigo);


--
-- Name: unq_eventoreqviagem_viagem_tipo; Type: CONSTRAINT; Schema: viagem; Owner: sati; Tablespace: 
--

ALTER TABLE ONLY eventoreqviagem
    ADD CONSTRAINT unq_eventoreqviagem_viagem_tipo UNIQUE (cod_tipoeventoreqviagem, cod_viagem);


SET search_path = equipamentos, pg_catalog;

--
-- Name: fki_equipamento_lote; Type: INDEX; Schema: equipamentos; Owner: sati; Tablespace: 
--

CREATE INDEX fki_equipamento_lote ON equipamento USING btree (cod_lote);


--
-- Name: fki_lote_modelo; Type: INDEX; Schema: equipamentos; Owner: sati; Tablespace: 
--

CREATE INDEX fki_lote_modelo ON lote USING btree (cod_modelo);


--
-- Name: fki_tipo_equipamento_modelo; Type: INDEX; Schema: equipamentos; Owner: sati; Tablespace: 
--

CREATE INDEX fki_tipo_equipamento_modelo ON modelo USING btree (cod_tipo_equipamento);


SET search_path = public, pg_catalog;

--
-- Name: fki_progint_areati; Type: INDEX; Schema: public; Owner: sati; Tablespace: 
--

CREATE INDEX fki_progint_areati ON progint USING btree (cod_areati);


--
-- Name: fki_progint_unidade; Type: INDEX; Schema: public; Owner: sati; Tablespace: 
--

CREATE INDEX fki_progint_unidade ON progint USING btree (cod_unidade);


SET search_path = redes, pg_catalog;

--
-- Name: trg_portas_panel; Type: TRIGGER; Schema: redes; Owner: sati
--

CREATE TRIGGER trg_portas_panel BEFORE UPDATE ON panel FOR EACH ROW EXECUTE PROCEDURE public.trg_portas_panel();


SET search_path = backup, pg_catalog;

--
-- Name: backup_fita_backup; Type: FK CONSTRAINT; Schema: backup; Owner: sati
--

ALTER TABLE ONLY backup_fita_fita_dados
    ADD CONSTRAINT backup_fita_backup FOREIGN KEY (backup_fita_id) REFERENCES backup_fita(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: backup_fita_fita; Type: FK CONSTRAINT; Schema: backup; Owner: sati
--

ALTER TABLE ONLY backup_fita_fita_dados
    ADD CONSTRAINT backup_fita_fita FOREIGN KEY (fita_dados_id) REFERENCES fita_dados(id);


--
-- Name: fk_backup_estacao_local_backup_estacao; Type: FK CONSTRAINT; Schema: backup; Owner: sati
--

ALTER TABLE ONLY local_backup_estacao
    ADD CONSTRAINT fk_backup_estacao_local_backup_estacao FOREIGN KEY (backup_estacao_id) REFERENCES backup_estacao(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: fk_backup_fita_teste_backup; Type: FK CONSTRAINT; Schema: backup; Owner: sati
--

ALTER TABLE ONLY teste_backup
    ADD CONSTRAINT fk_backup_fita_teste_backup FOREIGN KEY (backup_fita_id) REFERENCES backup_fita(id);


--
-- Name: fk_categoria_fita_dados_fita_dados; Type: FK CONSTRAINT; Schema: backup; Owner: sati
--

ALTER TABLE ONLY fita_dados
    ADD CONSTRAINT fk_categoria_fita_dados_fita_dados FOREIGN KEY (categoria_id) REFERENCES categoria_fita_dados(id);


--
-- Name: fk_conjunto_fita_designacao_fita_id; Type: FK CONSTRAINT; Schema: backup; Owner: sati
--

ALTER TABLE ONLY conjunto_fita
    ADD CONSTRAINT fk_conjunto_fita_designacao_fita_id FOREIGN KEY (designacao_fita_id) REFERENCES designacao_fita(id);


--
-- Name: fk_conjunto_fita_municipio_id; Type: FK CONSTRAINT; Schema: backup; Owner: sati
--

ALTER TABLE ONLY conjunto_fita
    ADD CONSTRAINT fk_conjunto_fita_municipio_id FOREIGN KEY (municipio_id) REFERENCES public.municipio(codigo);


--
-- Name: fk_conjunto_fitas_fita_dados; Type: FK CONSTRAINT; Schema: backup; Owner: sati
--

ALTER TABLE ONLY fita_dados
    ADD CONSTRAINT fk_conjunto_fitas_fita_dados FOREIGN KEY (conjunto_fitas_id) REFERENCES conjunto_fitas(id);


--
-- Name: fk_designacao_conjunto_fitas; Type: FK CONSTRAINT; Schema: backup; Owner: sati
--

ALTER TABLE ONLY conjunto_fitas
    ADD CONSTRAINT fk_designacao_conjunto_fitas FOREIGN KEY (designacao_fita_id) REFERENCES designacao_fita(id);


--
-- Name: fk_equipamento_local_backup_estacao; Type: FK CONSTRAINT; Schema: backup; Owner: sati
--

ALTER TABLE ONLY local_backup_estacao
    ADD CONSTRAINT fk_equipamento_local_backup_estacao FOREIGN KEY (equipamento_id) REFERENCES equipamentos.equipamento(codigo);


--
-- Name: fk_fita_fita_dados; Type: FK CONSTRAINT; Schema: backup; Owner: sati
--

ALTER TABLE ONLY fita_dados
    ADD CONSTRAINT fk_fita_fita_dados FOREIGN KEY (id) REFERENCES fita(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: fk_fita_fita_limpeza; Type: FK CONSTRAINT; Schema: backup; Owner: sati
--

ALTER TABLE ONLY fita_limpeza
    ADD CONSTRAINT fk_fita_fita_limpeza FOREIGN KEY (id) REFERENCES fita(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: fk_fita_limpeza_limpeza; Type: FK CONSTRAINT; Schema: backup; Owner: sati
--

ALTER TABLE ONLY limpeza
    ADD CONSTRAINT fk_fita_limpeza_limpeza FOREIGN KEY (fita_limpeza_id) REFERENCES fita_limpeza(id);


--
-- Name: fk_municipio_conjunto_fitas; Type: FK CONSTRAINT; Schema: backup; Owner: sati
--

ALTER TABLE ONLY conjunto_fitas
    ADD CONSTRAINT fk_municipio_conjunto_fitas FOREIGN KEY (municipio_id) REFERENCES public.municipio(codigo);


--
-- Name: fk_municipio_fita_limpeza; Type: FK CONSTRAINT; Schema: backup; Owner: sati
--

ALTER TABLE ONLY fita_limpeza
    ADD CONSTRAINT fk_municipio_fita_limpeza FOREIGN KEY (municipio_id) REFERENCES public.municipio(codigo);


--
-- Name: fk_ocorrencia_backup_estacao; Type: FK CONSTRAINT; Schema: backup; Owner: sati
--

ALTER TABLE ONLY backup_estacao
    ADD CONSTRAINT fk_ocorrencia_backup_estacao FOREIGN KEY (id) REFERENCES ocorrencia(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: fk_ocorrencia_bacukp_dados; Type: FK CONSTRAINT; Schema: backup; Owner: sati
--

ALTER TABLE ONLY backup_fita
    ADD CONSTRAINT fk_ocorrencia_bacukp_dados FOREIGN KEY (id) REFERENCES ocorrencia(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: fk_ocorrencia_falha_hardware; Type: FK CONSTRAINT; Schema: backup; Owner: sati
--

ALTER TABLE ONLY falha_hardware
    ADD CONSTRAINT fk_ocorrencia_falha_hardware FOREIGN KEY (id) REFERENCES ocorrencia(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: fk_ocorrencia_limpeza; Type: FK CONSTRAINT; Schema: backup; Owner: sati
--

ALTER TABLE ONLY limpeza
    ADD CONSTRAINT fk_ocorrencia_limpeza FOREIGN KEY (id) REFERENCES ocorrencia(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: fk_ocorrencia_teste_backup; Type: FK CONSTRAINT; Schema: backup; Owner: sati
--

ALTER TABLE ONLY teste_backup
    ADD CONSTRAINT fk_ocorrencia_teste_backup FOREIGN KEY (id) REFERENCES ocorrencia(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: fk_tipo_teste_backup_teste_backup; Type: FK CONSTRAINT; Schema: backup; Owner: sati
--

ALTER TABLE ONLY teste_backup
    ADD CONSTRAINT fk_tipo_teste_backup_teste_backup FOREIGN KEY (tipo_teste_backup_id) REFERENCES tipo_teste_backup(id);


SET search_path = calendario, pg_catalog;

--
-- Name: fk_feriado_excecao_feriado_nacional; Type: FK CONSTRAINT; Schema: calendario; Owner: sati
--

ALTER TABLE ONLY excecao_feriado_nacional
    ADD CONSTRAINT fk_feriado_excecao_feriado_nacional FOREIGN KEY (cod_feriado) REFERENCES feriado(codigo) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: fk_feriado_feriado_fixo; Type: FK CONSTRAINT; Schema: calendario; Owner: sati
--

ALTER TABLE ONLY feriado_fixo
    ADD CONSTRAINT fk_feriado_feriado_fixo FOREIGN KEY (codigo) REFERENCES feriado(codigo) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: fk_feriado_feriado_movel; Type: FK CONSTRAINT; Schema: calendario; Owner: sati
--

ALTER TABLE ONLY feriado_movel
    ADD CONSTRAINT fk_feriado_feriado_movel FOREIGN KEY (codigo) REFERENCES feriado(codigo) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: fk_feriado_transferencia; Type: FK CONSTRAINT; Schema: calendario; Owner: sati
--

ALTER TABLE ONLY transferencia
    ADD CONSTRAINT fk_feriado_transferencia FOREIGN KEY (cod_feriado) REFERENCES feriado(codigo);


--
-- Name: fk_municipio_excecao_feriado_nacional; Type: FK CONSTRAINT; Schema: calendario; Owner: sati
--

ALTER TABLE ONLY excecao_feriado_nacional
    ADD CONSTRAINT fk_municipio_excecao_feriado_nacional FOREIGN KEY (cod_municipio) REFERENCES public.municipio(codigo);


--
-- Name: fk_municipio_excecao_transferencia; Type: FK CONSTRAINT; Schema: calendario; Owner: sati
--

ALTER TABLE ONLY excecao_transferencia
    ADD CONSTRAINT fk_municipio_excecao_transferencia FOREIGN KEY (cod_municipio) REFERENCES public.municipio(codigo);


--
-- Name: fk_municipio_feriado; Type: FK CONSTRAINT; Schema: calendario; Owner: sati
--

ALTER TABLE ONLY feriado
    ADD CONSTRAINT fk_municipio_feriado FOREIGN KEY (cod_municipio) REFERENCES public.municipio(codigo);


--
-- Name: fk_municipio_transferencia; Type: FK CONSTRAINT; Schema: calendario; Owner: sati
--

ALTER TABLE ONLY transferencia
    ADD CONSTRAINT fk_municipio_transferencia FOREIGN KEY (cod_municipio) REFERENCES public.municipio(codigo);


--
-- Name: fk_transferencia_excecao_transferencia; Type: FK CONSTRAINT; Schema: calendario; Owner: sati
--

ALTER TABLE ONLY excecao_transferencia
    ADD CONSTRAINT fk_transferencia_excecao_transferencia FOREIGN KEY (cod_transferencia) REFERENCES transferencia(codigo) ON UPDATE CASCADE ON DELETE CASCADE;


SET search_path = certificacao, pg_catalog;

--
-- Name: fk_certificado_marca_etoken; Type: FK CONSTRAINT; Schema: certificacao; Owner: sati
--

ALTER TABLE ONLY certificado
    ADD CONSTRAINT fk_certificado_marca_etoken FOREIGN KEY (id_marca_etoken) REFERENCES marca_etoken(id);


--
-- Name: fk_certificado_status_certificado; Type: FK CONSTRAINT; Schema: certificacao; Owner: sati
--

ALTER TABLE ONLY certificado
    ADD CONSTRAINT fk_certificado_status_certificado FOREIGN KEY (id_status) REFERENCES status_certificado(id);


--
-- Name: fk_certificado_usuario; Type: FK CONSTRAINT; Schema: certificacao; Owner: sati
--

ALTER TABLE ONLY certificado
    ADD CONSTRAINT fk_certificado_usuario FOREIGN KEY (id_usuario) REFERENCES public.usuario_final(id);


SET search_path = equipamentos, pg_catalog;

--
-- Name: equipamento_lote; Type: FK CONSTRAINT; Schema: equipamentos; Owner: sati
--

ALTER TABLE ONLY equipamento
    ADD CONSTRAINT equipamento_lote FOREIGN KEY (cod_lote) REFERENCES lote(codigo);


--
-- Name: fk_equipamento_equipamento_unidade; Type: FK CONSTRAINT; Schema: equipamentos; Owner: sati
--

ALTER TABLE ONLY equipamento_unidade
    ADD CONSTRAINT fk_equipamento_equipamento_unidade FOREIGN KEY (cod_equipamento) REFERENCES equipamento(codigo) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: fk_equipamento_historico; Type: FK CONSTRAINT; Schema: equipamentos; Owner: sati
--

ALTER TABLE ONLY historico
    ADD CONSTRAINT fk_equipamento_historico FOREIGN KEY (cod_equipamento) REFERENCES equipamento(codigo) ON DELETE CASCADE;


--
-- Name: fk_equipamento_usuario_equipamento; Type: FK CONSTRAINT; Schema: equipamentos; Owner: postgres
--

ALTER TABLE ONLY equipamento_usuario
    ADD CONSTRAINT fk_equipamento_usuario_equipamento FOREIGN KEY (equipamento_cod) REFERENCES equipamento(codigo) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: fk_equipamento_usuario_usuario; Type: FK CONSTRAINT; Schema: equipamentos; Owner: postgres
--

ALTER TABLE ONLY equipamento_usuario
    ADD CONSTRAINT fk_equipamento_usuario_usuario FOREIGN KEY (usuario_id) REFERENCES public.usuario_final(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: fk_tipo_equipamento_modelo; Type: FK CONSTRAINT; Schema: equipamentos; Owner: sati
--

ALTER TABLE ONLY modelo
    ADD CONSTRAINT fk_tipo_equipamento_modelo FOREIGN KEY (cod_tipo_equipamento) REFERENCES tipo_equipamento(codigo);


--
-- Name: fk_unidade_equipamento_unidade; Type: FK CONSTRAINT; Schema: equipamentos; Owner: sati
--

ALTER TABLE ONLY equipamento_unidade
    ADD CONSTRAINT fk_unidade_equipamento_unidade FOREIGN KEY (cod_unidade) REFERENCES public.unidade(codigo) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: lote_modelo; Type: FK CONSTRAINT; Schema: equipamentos; Owner: sati
--

ALTER TABLE ONLY lote
    ADD CONSTRAINT lote_modelo FOREIGN KEY (cod_modelo) REFERENCES modelo(codigo);


SET search_path = public, pg_catalog;

--
-- Name: fk_areaTI_municipiosede; Type: FK CONSTRAINT; Schema: public; Owner: sati
--

ALTER TABLE ONLY areati
    ADD CONSTRAINT "fk_areaTI_municipiosede" FOREIGN KEY (cod_municipiosede) REFERENCES municipio(codigo) ON DELETE SET NULL;


--
-- Name: fk_municipio_cod_areati; Type: FK CONSTRAINT; Schema: public; Owner: sati
--

ALTER TABLE ONLY municipio
    ADD CONSTRAINT fk_municipio_cod_areati FOREIGN KEY (cod_areati) REFERENCES areati(codigo);


--
-- Name: fk_progint_areati; Type: FK CONSTRAINT; Schema: public; Owner: sati
--

ALTER TABLE ONLY progint
    ADD CONSTRAINT fk_progint_areati FOREIGN KEY (cod_areati) REFERENCES areati(codigo);


--
-- Name: fk_progint_unidade; Type: FK CONSTRAINT; Schema: public; Owner: sati
--

ALTER TABLE ONLY progint
    ADD CONSTRAINT fk_progint_unidade FOREIGN KEY (cod_unidade) REFERENCES unidade(codigo);


--
-- Name: fk_usuario_final_unidade; Type: FK CONSTRAINT; Schema: public; Owner: sati
--

ALTER TABLE ONLY usuario_final
    ADD CONSTRAINT fk_usuario_final_unidade FOREIGN KEY (cod_unidade) REFERENCES unidade(codigo);


--
-- Name: municipio_areati; Type: FK CONSTRAINT; Schema: public; Owner: sati
--

ALTER TABLE ONLY municipio
    ADD CONSTRAINT municipio_areati FOREIGN KEY (cod_areati) REFERENCES areati(codigo);


--
-- Name: municipio_areati; Type: FK CONSTRAINT; Schema: public; Owner: sati
--

ALTER TABLE ONLY areati
    ADD CONSTRAINT municipio_areati FOREIGN KEY (cod_municipiosede) REFERENCES municipio(codigo);


--
-- Name: unidade_municipio; Type: FK CONSTRAINT; Schema: public; Owner: sati
--

ALTER TABLE ONLY unidade
    ADD CONSTRAINT unidade_municipio FOREIGN KEY (cod_municipio) REFERENCES municipio(codigo);


SET search_path = redes, pg_catalog;

--
-- Name: elementorede_categoria; Type: FK CONSTRAINT; Schema: redes; Owner: sati
--

ALTER TABLE ONLY elementorede
    ADD CONSTRAINT elementorede_categoria FOREIGN KEY (cod_categoria) REFERENCES categoriaelementorede(codigo);


--
-- Name: elementorede_unidade; Type: FK CONSTRAINT; Schema: redes; Owner: sati
--

ALTER TABLE ONLY elementorede
    ADD CONSTRAINT elementorede_unidade FOREIGN KEY (cod_unidade) REFERENCES public.unidade(codigo);


--
-- Name: estacao_categoria; Type: FK CONSTRAINT; Schema: redes; Owner: sati
--

ALTER TABLE ONLY estacao
    ADD CONSTRAINT estacao_categoria FOREIGN KEY (cod_categoria) REFERENCES categoriaelementorede(codigo);


--
-- Name: fk_rede_municipio_municipio; Type: FK CONSTRAINT; Schema: redes; Owner: sati
--

ALTER TABLE ONLY rede_municipio
    ADD CONSTRAINT fk_rede_municipio_municipio FOREIGN KEY (cod_municipio) REFERENCES public.municipio(codigo) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: fk_rede_municipio_rede; Type: FK CONSTRAINT; Schema: redes; Owner: sati
--

ALTER TABLE ONLY rede_municipio
    ADD CONSTRAINT fk_rede_municipio_rede FOREIGN KEY (cod_rede) REFERENCES rede(codigo) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: modulo_tipomodulo; Type: FK CONSTRAINT; Schema: redes; Owner: sati
--

ALTER TABLE ONLY modulo
    ADD CONSTRAINT modulo_tipomodulo FOREIGN KEY (cod_tipomodulo) REFERENCES tipomodulo(codigo);


--
-- Name: modulo_unidade; Type: FK CONSTRAINT; Schema: redes; Owner: sati
--

ALTER TABLE ONLY modulo
    ADD CONSTRAINT modulo_unidade FOREIGN KEY (cod_unidade) REFERENCES public.unidade(codigo);


--
-- Name: panel_rack; Type: FK CONSTRAINT; Schema: redes; Owner: sati
--

ALTER TABLE ONLY panel
    ADD CONSTRAINT panel_rack FOREIGN KEY (cod_rack) REFERENCES rack(codigo);


--
-- Name: rangeip_categoria; Type: FK CONSTRAINT; Schema: redes; Owner: sati
--

ALTER TABLE ONLY rangeip
    ADD CONSTRAINT rangeip_categoria FOREIGN KEY (cod_categoria) REFERENCES categoriaelementorede(codigo);


--
-- Name: rangeip_rede; Type: FK CONSTRAINT; Schema: redes; Owner: sati
--

ALTER TABLE ONLY rangeip
    ADD CONSTRAINT rangeip_rede FOREIGN KEY (cod_rede) REFERENCES rede(codigo);


--
-- Name: servidor_categoria; Type: FK CONSTRAINT; Schema: redes; Owner: sati
--

ALTER TABLE ONLY servidor
    ADD CONSTRAINT servidor_categoria FOREIGN KEY (cod_categoria) REFERENCES categoriaelementorede(codigo);


--
-- Name: servidor_unidade; Type: FK CONSTRAINT; Schema: redes; Owner: sati
--

ALTER TABLE ONLY servidor
    ADD CONSTRAINT servidor_unidade FOREIGN KEY (cod_unidade) REFERENCES public.unidade(codigo);


--
-- Name: tomada_segmento; Type: FK CONSTRAINT; Schema: redes; Owner: sati
--

ALTER TABLE ONLY tomada
    ADD CONSTRAINT tomada_segmento FOREIGN KEY (cod_segmento) REFERENCES segmento(codigo);


--
-- Name: tomadapanel_panel; Type: FK CONSTRAINT; Schema: redes; Owner: sati
--

ALTER TABLE ONLY tomadapanel
    ADD CONSTRAINT tomadapanel_panel FOREIGN KEY (cod_panel) REFERENCES panel(codigo) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: tomadapanel_tomada; Type: FK CONSTRAINT; Schema: redes; Owner: sati
--

ALTER TABLE ONLY tomadapanel
    ADD CONSTRAINT tomadapanel_tomada FOREIGN KEY (codigo) REFERENCES tomada(codigo) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: tomadaremota_modulo; Type: FK CONSTRAINT; Schema: redes; Owner: sati
--

ALTER TABLE ONLY tomadaremota
    ADD CONSTRAINT tomadaremota_modulo FOREIGN KEY (cod_modulo) REFERENCES modulo(codigo) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: tomadaremota_tipoconector; Type: FK CONSTRAINT; Schema: redes; Owner: sati
--

ALTER TABLE ONLY tomadaremota
    ADD CONSTRAINT tomadaremota_tipoconector FOREIGN KEY (cod_tipoconector) REFERENCES tipoconector(codigo);


--
-- Name: tomadaremota_tomada; Type: FK CONSTRAINT; Schema: redes; Owner: sati
--

ALTER TABLE ONLY tomadaremota
    ADD CONSTRAINT tomadaremota_tomada FOREIGN KEY (codigo) REFERENCES tomada(codigo) ON UPDATE CASCADE ON DELETE CASCADE;


SET search_path = viagem, pg_catalog;

--
-- Name: eventoreqviagem_tipoeventoreqviagem; Type: FK CONSTRAINT; Schema: viagem; Owner: sati
--

ALTER TABLE ONLY eventoreqviagem
    ADD CONSTRAINT eventoreqviagem_tipoeventoreqviagem FOREIGN KEY (cod_tipoeventoreqviagem) REFERENCES tipoeventoreqviagem(codigo);


--
-- Name: eventoreqviagem_viagem; Type: FK CONSTRAINT; Schema: viagem; Owner: sati
--

ALTER TABLE ONLY eventoreqviagem
    ADD CONSTRAINT eventoreqviagem_viagem FOREIGN KEY (cod_viagem) REFERENCES viagem(codigo);


--
-- Name: fk_eventoreqviagem_cod_tipoeventoreqviagem; Type: FK CONSTRAINT; Schema: viagem; Owner: sati
--

ALTER TABLE ONLY eventoreqviagem
    ADD CONSTRAINT fk_eventoreqviagem_cod_tipoeventoreqviagem FOREIGN KEY (cod_tipoeventoreqviagem) REFERENCES tipoeventoreqviagem(codigo);


--
-- Name: fk_eventoreqviagem_cod_viagem; Type: FK CONSTRAINT; Schema: viagem; Owner: sati
--

ALTER TABLE ONLY eventoreqviagem
    ADD CONSTRAINT fk_eventoreqviagem_cod_viagem FOREIGN KEY (cod_viagem) REFERENCES viagem(codigo);


--
-- Name: fk_viagem_cod_municipio_destino; Type: FK CONSTRAINT; Schema: viagem; Owner: sati
--

ALTER TABLE ONLY viagem
    ADD CONSTRAINT fk_viagem_cod_municipio_destino FOREIGN KEY (cod_municipio_destino) REFERENCES public.municipio(codigo);


--
-- Name: fk_viagem_cod_municipio_origem; Type: FK CONSTRAINT; Schema: viagem; Owner: sati
--

ALTER TABLE ONLY viagem
    ADD CONSTRAINT fk_viagem_cod_municipio_origem FOREIGN KEY (cod_municipio_origem) REFERENCES public.municipio(codigo);


--
-- Name: fk_viagem_cod_progint; Type: FK CONSTRAINT; Schema: viagem; Owner: sati
--

ALTER TABLE ONLY viagem
    ADD CONSTRAINT fk_viagem_cod_progint FOREIGN KEY (cod_progint) REFERENCES public.progint(codigo);


--
-- Name: municipio_destino_viagem; Type: FK CONSTRAINT; Schema: viagem; Owner: sati
--

ALTER TABLE ONLY viagem
    ADD CONSTRAINT municipio_destino_viagem FOREIGN KEY (cod_municipio_destino) REFERENCES public.municipio(codigo);


--
-- Name: municipio_origem_viagem; Type: FK CONSTRAINT; Schema: viagem; Owner: sati
--

ALTER TABLE ONLY viagem
    ADD CONSTRAINT municipio_origem_viagem FOREIGN KEY (cod_municipio_origem) REFERENCES public.municipio(codigo);


--
-- Name: progint_viagem; Type: FK CONSTRAINT; Schema: viagem; Owner: sati
--

ALTER TABLE ONLY viagem
    ADD CONSTRAINT progint_viagem FOREIGN KEY (cod_progint) REFERENCES public.progint(codigo);


--
-- Name: viagem_tarefa; Type: FK CONSTRAINT; Schema: viagem; Owner: sati
--

ALTER TABLE ONLY tarefa
    ADD CONSTRAINT viagem_tarefa FOREIGN KEY (cod_viagem) REFERENCES viagem(codigo) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: certificacao; Type: ACL; Schema: -; Owner: sati
--

REVOKE ALL ON SCHEMA certificacao FROM PUBLIC;
REVOKE ALL ON SCHEMA certificacao FROM sati;
GRANT ALL ON SCHEMA certificacao TO sati;
GRANT ALL ON SCHEMA certificacao TO PUBLIC;


--
-- Name: equipamentos; Type: ACL; Schema: -; Owner: sati
--

REVOKE ALL ON SCHEMA equipamentos FROM PUBLIC;
REVOKE ALL ON SCHEMA equipamentos FROM sati;
GRANT ALL ON SCHEMA equipamentos TO sati;
GRANT ALL ON SCHEMA equipamentos TO PUBLIC;


--
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


SET search_path = public, pg_catalog;

--
-- Name: trg_portas_panel(); Type: ACL; Schema: public; Owner: postgres
--

REVOKE ALL ON FUNCTION trg_portas_panel() FROM PUBLIC;
REVOKE ALL ON FUNCTION trg_portas_panel() FROM postgres;
GRANT ALL ON FUNCTION trg_portas_panel() TO postgres;
GRANT ALL ON FUNCTION trg_portas_panel() TO PUBLIC;


SET search_path = backup, pg_catalog;

--
-- Name: backup_fita; Type: ACL; Schema: backup; Owner: sati
--

REVOKE ALL ON TABLE backup_fita FROM PUBLIC;
REVOKE ALL ON TABLE backup_fita FROM sati;
GRANT ALL ON TABLE backup_fita TO sati;
GRANT ALL ON TABLE backup_fita TO PUBLIC;


--
-- Name: backup_fita_fita_dados; Type: ACL; Schema: backup; Owner: sati
--

REVOKE ALL ON TABLE backup_fita_fita_dados FROM PUBLIC;
REVOKE ALL ON TABLE backup_fita_fita_dados FROM sati;
GRANT ALL ON TABLE backup_fita_fita_dados TO sati;
GRANT ALL ON TABLE backup_fita_fita_dados TO PUBLIC;


--
-- Name: categoria_fita_dados; Type: ACL; Schema: backup; Owner: sati
--

REVOKE ALL ON TABLE categoria_fita_dados FROM PUBLIC;
REVOKE ALL ON TABLE categoria_fita_dados FROM sati;
GRANT ALL ON TABLE categoria_fita_dados TO sati;
GRANT ALL ON TABLE categoria_fita_dados TO PUBLIC;


--
-- Name: conjunto_fitas; Type: ACL; Schema: backup; Owner: sati
--

REVOKE ALL ON TABLE conjunto_fitas FROM PUBLIC;
REVOKE ALL ON TABLE conjunto_fitas FROM sati;
GRANT ALL ON TABLE conjunto_fitas TO sati;
GRANT ALL ON TABLE conjunto_fitas TO PUBLIC;


--
-- Name: designacao_fita; Type: ACL; Schema: backup; Owner: sati
--

REVOKE ALL ON TABLE designacao_fita FROM PUBLIC;
REVOKE ALL ON TABLE designacao_fita FROM sati;
GRANT ALL ON TABLE designacao_fita TO sati;
GRANT SELECT,REFERENCES,TRIGGER ON TABLE designacao_fita TO PUBLIC;


--
-- Name: falha_hardware; Type: ACL; Schema: backup; Owner: sati
--

REVOKE ALL ON TABLE falha_hardware FROM PUBLIC;
REVOKE ALL ON TABLE falha_hardware FROM sati;
GRANT ALL ON TABLE falha_hardware TO sati;
GRANT ALL ON TABLE falha_hardware TO PUBLIC;


--
-- Name: fita; Type: ACL; Schema: backup; Owner: sati
--

REVOKE ALL ON TABLE fita FROM PUBLIC;
REVOKE ALL ON TABLE fita FROM sati;
GRANT ALL ON TABLE fita TO sati;
GRANT ALL ON TABLE fita TO PUBLIC;


--
-- Name: fita_dados; Type: ACL; Schema: backup; Owner: sati
--

REVOKE ALL ON TABLE fita_dados FROM PUBLIC;
REVOKE ALL ON TABLE fita_dados FROM sati;
GRANT ALL ON TABLE fita_dados TO sati;
GRANT ALL ON TABLE fita_dados TO PUBLIC;


--
-- Name: fita_limpeza; Type: ACL; Schema: backup; Owner: sati
--

REVOKE ALL ON TABLE fita_limpeza FROM PUBLIC;
REVOKE ALL ON TABLE fita_limpeza FROM sati;
GRANT ALL ON TABLE fita_limpeza TO sati;
GRANT ALL ON TABLE fita_limpeza TO PUBLIC;


--
-- Name: limpeza; Type: ACL; Schema: backup; Owner: sati
--

REVOKE ALL ON TABLE limpeza FROM PUBLIC;
REVOKE ALL ON TABLE limpeza FROM sati;
GRANT ALL ON TABLE limpeza TO sati;
GRANT ALL ON TABLE limpeza TO PUBLIC;


--
-- Name: local_backup_estacao; Type: ACL; Schema: backup; Owner: sati
--

REVOKE ALL ON TABLE local_backup_estacao FROM PUBLIC;
REVOKE ALL ON TABLE local_backup_estacao FROM sati;
GRANT ALL ON TABLE local_backup_estacao TO sati;
GRANT ALL ON TABLE local_backup_estacao TO PUBLIC;


--
-- Name: outra_ocorrencia; Type: ACL; Schema: backup; Owner: sati
--

REVOKE ALL ON TABLE outra_ocorrencia FROM PUBLIC;
REVOKE ALL ON TABLE outra_ocorrencia FROM sati;
GRANT ALL ON TABLE outra_ocorrencia TO sati;
GRANT ALL ON TABLE outra_ocorrencia TO PUBLIC;


--
-- Name: teste_backup; Type: ACL; Schema: backup; Owner: sati
--

REVOKE ALL ON TABLE teste_backup FROM PUBLIC;
REVOKE ALL ON TABLE teste_backup FROM sati;
GRANT ALL ON TABLE teste_backup TO sati;
GRANT ALL ON TABLE teste_backup TO PUBLIC;


--
-- Name: tipo_teste_backup; Type: ACL; Schema: backup; Owner: sati
--

REVOKE ALL ON TABLE tipo_teste_backup FROM PUBLIC;
REVOKE ALL ON TABLE tipo_teste_backup FROM sati;
GRANT ALL ON TABLE tipo_teste_backup TO sati;
GRANT SELECT,REFERENCES,TRIGGER ON TABLE tipo_teste_backup TO PUBLIC;


SET search_path = calendario, pg_catalog;

--
-- Name: excecao_feriado_nacional; Type: ACL; Schema: calendario; Owner: sati
--

REVOKE ALL ON TABLE excecao_feriado_nacional FROM PUBLIC;
REVOKE ALL ON TABLE excecao_feriado_nacional FROM sati;
GRANT ALL ON TABLE excecao_feriado_nacional TO sati;
GRANT ALL ON TABLE excecao_feriado_nacional TO PUBLIC;


--
-- Name: feriado; Type: ACL; Schema: calendario; Owner: sati
--

REVOKE ALL ON TABLE feriado FROM PUBLIC;
REVOKE ALL ON TABLE feriado FROM sati;
GRANT ALL ON TABLE feriado TO sati;
GRANT ALL ON TABLE feriado TO PUBLIC;


--
-- Name: feriado_fixo; Type: ACL; Schema: calendario; Owner: sati
--

REVOKE ALL ON TABLE feriado_fixo FROM PUBLIC;
REVOKE ALL ON TABLE feriado_fixo FROM sati;
GRANT ALL ON TABLE feriado_fixo TO sati;
GRANT ALL ON TABLE feriado_fixo TO PUBLIC;


--
-- Name: feriado_movel; Type: ACL; Schema: calendario; Owner: sati
--

REVOKE ALL ON TABLE feriado_movel FROM PUBLIC;
REVOKE ALL ON TABLE feriado_movel FROM sati;
GRANT ALL ON TABLE feriado_movel TO sati;
GRANT ALL ON TABLE feriado_movel TO PUBLIC;


--
-- Name: transferencia; Type: ACL; Schema: calendario; Owner: sati
--

REVOKE ALL ON TABLE transferencia FROM PUBLIC;
REVOKE ALL ON TABLE transferencia FROM sati;
GRANT ALL ON TABLE transferencia TO sati;
GRANT ALL ON TABLE transferencia TO PUBLIC;


SET search_path = certificacao, pg_catalog;

--
-- Name: certificado; Type: ACL; Schema: certificacao; Owner: sati
--

REVOKE ALL ON TABLE certificado FROM PUBLIC;
REVOKE ALL ON TABLE certificado FROM sati;
GRANT ALL ON TABLE certificado TO sati;
GRANT ALL ON TABLE certificado TO PUBLIC;


--
-- Name: certificado_id_seq; Type: ACL; Schema: certificacao; Owner: sati
--

REVOKE ALL ON SEQUENCE certificado_id_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE certificado_id_seq FROM sati;
GRANT ALL ON SEQUENCE certificado_id_seq TO sati;
GRANT ALL ON SEQUENCE certificado_id_seq TO PUBLIC;


--
-- Name: marca_etoken; Type: ACL; Schema: certificacao; Owner: sati
--

REVOKE ALL ON TABLE marca_etoken FROM PUBLIC;
REVOKE ALL ON TABLE marca_etoken FROM sati;
GRANT ALL ON TABLE marca_etoken TO sati;
GRANT ALL ON TABLE marca_etoken TO PUBLIC;


--
-- Name: status_certificado; Type: ACL; Schema: certificacao; Owner: sati
--

REVOKE ALL ON TABLE status_certificado FROM PUBLIC;
REVOKE ALL ON TABLE status_certificado FROM sati;
GRANT ALL ON TABLE status_certificado TO sati;
GRANT ALL ON TABLE status_certificado TO PUBLIC;


SET search_path = equipamentos, pg_catalog;

--
-- Name: equipamento; Type: ACL; Schema: equipamentos; Owner: sati
--

REVOKE ALL ON TABLE equipamento FROM PUBLIC;
REVOKE ALL ON TABLE equipamento FROM sati;
GRANT ALL ON TABLE equipamento TO sati;
GRANT ALL ON TABLE equipamento TO PUBLIC;


--
-- Name: equipamento_unidade; Type: ACL; Schema: equipamentos; Owner: sati
--

REVOKE ALL ON TABLE equipamento_unidade FROM PUBLIC;
REVOKE ALL ON TABLE equipamento_unidade FROM sati;
GRANT ALL ON TABLE equipamento_unidade TO sati;
GRANT ALL ON TABLE equipamento_unidade TO PUBLIC;


--
-- Name: equipamento_usuario; Type: ACL; Schema: equipamentos; Owner: postgres
--

REVOKE ALL ON TABLE equipamento_usuario FROM PUBLIC;
REVOKE ALL ON TABLE equipamento_usuario FROM postgres;
GRANT ALL ON TABLE equipamento_usuario TO postgres;
GRANT ALL ON TABLE equipamento_usuario TO PUBLIC;


--
-- Name: historico; Type: ACL; Schema: equipamentos; Owner: sati
--

REVOKE ALL ON TABLE historico FROM PUBLIC;
REVOKE ALL ON TABLE historico FROM sati;
GRANT ALL ON TABLE historico TO sati;
GRANT ALL ON TABLE historico TO PUBLIC;


--
-- Name: lote; Type: ACL; Schema: equipamentos; Owner: sati
--

REVOKE ALL ON TABLE lote FROM PUBLIC;
REVOKE ALL ON TABLE lote FROM sati;
GRANT ALL ON TABLE lote TO sati;
GRANT ALL ON TABLE lote TO PUBLIC;


--
-- Name: modelo; Type: ACL; Schema: equipamentos; Owner: sati
--

REVOKE ALL ON TABLE modelo FROM PUBLIC;
REVOKE ALL ON TABLE modelo FROM sati;
GRANT ALL ON TABLE modelo TO sati;
GRANT ALL ON TABLE modelo TO PUBLIC;


--
-- Name: tipo_equipamento; Type: ACL; Schema: equipamentos; Owner: sati
--

REVOKE ALL ON TABLE tipo_equipamento FROM PUBLIC;
REVOKE ALL ON TABLE tipo_equipamento FROM sati;
GRANT ALL ON TABLE tipo_equipamento TO sati;
GRANT ALL ON TABLE tipo_equipamento TO PUBLIC;


SET search_path = public, pg_catalog;

--
-- Name: areati; Type: ACL; Schema: public; Owner: sati
--

REVOKE ALL ON TABLE areati FROM PUBLIC;
REVOKE ALL ON TABLE areati FROM sati;
GRANT ALL ON TABLE areati TO sati;
GRANT ALL ON TABLE areati TO PUBLIC;


--
-- Name: areati_codigo_seq; Type: ACL; Schema: public; Owner: sati
--

REVOKE ALL ON SEQUENCE areati_codigo_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE areati_codigo_seq FROM sati;
GRANT ALL ON SEQUENCE areati_codigo_seq TO sati;
GRANT ALL ON SEQUENCE areati_codigo_seq TO PUBLIC;


--
-- Name: municipio; Type: ACL; Schema: public; Owner: sati
--

REVOKE ALL ON TABLE municipio FROM PUBLIC;
REVOKE ALL ON TABLE municipio FROM sati;
GRANT ALL ON TABLE municipio TO sati;
GRANT ALL ON TABLE municipio TO PUBLIC;


--
-- Name: municipio_codigo_seq; Type: ACL; Schema: public; Owner: sati
--

REVOKE ALL ON SEQUENCE municipio_codigo_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE municipio_codigo_seq FROM sati;
GRANT ALL ON SEQUENCE municipio_codigo_seq TO sati;
GRANT ALL ON SEQUENCE municipio_codigo_seq TO PUBLIC;


--
-- Name: progint; Type: ACL; Schema: public; Owner: sati
--

REVOKE ALL ON TABLE progint FROM PUBLIC;
REVOKE ALL ON TABLE progint FROM sati;
GRANT ALL ON TABLE progint TO sati;
GRANT ALL ON TABLE progint TO PUBLIC;


--
-- Name: progint_codigo_seq; Type: ACL; Schema: public; Owner: sati
--

REVOKE ALL ON SEQUENCE progint_codigo_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE progint_codigo_seq FROM sati;
GRANT ALL ON SEQUENCE progint_codigo_seq TO sati;
GRANT ALL ON SEQUENCE progint_codigo_seq TO PUBLIC;


--
-- Name: unidade; Type: ACL; Schema: public; Owner: sati
--

REVOKE ALL ON TABLE unidade FROM PUBLIC;
REVOKE ALL ON TABLE unidade FROM sati;
GRANT ALL ON TABLE unidade TO sati;
GRANT ALL ON TABLE unidade TO PUBLIC;


--
-- Name: unidade_codigo_seq; Type: ACL; Schema: public; Owner: sati
--

REVOKE ALL ON SEQUENCE unidade_codigo_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE unidade_codigo_seq FROM sati;
GRANT ALL ON SEQUENCE unidade_codigo_seq TO sati;
GRANT ALL ON SEQUENCE unidade_codigo_seq TO PUBLIC;


--
-- Name: usuario_final; Type: ACL; Schema: public; Owner: sati
--

REVOKE ALL ON TABLE usuario_final FROM PUBLIC;
REVOKE ALL ON TABLE usuario_final FROM sati;
GRANT ALL ON TABLE usuario_final TO sati;
GRANT ALL ON TABLE usuario_final TO PUBLIC;


SET search_path = redes, pg_catalog;

--
-- Name: categoriaelementorede; Type: ACL; Schema: redes; Owner: sati
--

REVOKE ALL ON TABLE categoriaelementorede FROM PUBLIC;
REVOKE ALL ON TABLE categoriaelementorede FROM sati;
GRANT ALL ON TABLE categoriaelementorede TO sati;
GRANT ALL ON TABLE categoriaelementorede TO PUBLIC;


--
-- Name: categoriaelementorede_codigo_seq; Type: ACL; Schema: redes; Owner: sati
--

REVOKE ALL ON SEQUENCE categoriaelementorede_codigo_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE categoriaelementorede_codigo_seq FROM sati;
GRANT ALL ON SEQUENCE categoriaelementorede_codigo_seq TO sati;
GRANT ALL ON SEQUENCE categoriaelementorede_codigo_seq TO PUBLIC;


--
-- Name: elementorede; Type: ACL; Schema: redes; Owner: sati
--

REVOKE ALL ON TABLE elementorede FROM PUBLIC;
REVOKE ALL ON TABLE elementorede FROM sati;
GRANT ALL ON TABLE elementorede TO sati;
GRANT ALL ON TABLE elementorede TO PUBLIC;


--
-- Name: elementorede_codigo_seq; Type: ACL; Schema: redes; Owner: sati
--

REVOKE ALL ON SEQUENCE elementorede_codigo_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE elementorede_codigo_seq FROM sati;
GRANT ALL ON SEQUENCE elementorede_codigo_seq TO sati;
GRANT ALL ON SEQUENCE elementorede_codigo_seq TO PUBLIC;


--
-- Name: estacao; Type: ACL; Schema: redes; Owner: sati
--

REVOKE ALL ON TABLE estacao FROM PUBLIC;
REVOKE ALL ON TABLE estacao FROM sati;
GRANT ALL ON TABLE estacao TO sati;
GRANT ALL ON TABLE estacao TO PUBLIC;


--
-- Name: modulo; Type: ACL; Schema: redes; Owner: sati
--

REVOKE ALL ON TABLE modulo FROM PUBLIC;
REVOKE ALL ON TABLE modulo FROM sati;
GRANT ALL ON TABLE modulo TO sati;
GRANT ALL ON TABLE modulo TO PUBLIC;


--
-- Name: panel; Type: ACL; Schema: redes; Owner: sati
--

REVOKE ALL ON TABLE panel FROM PUBLIC;
REVOKE ALL ON TABLE panel FROM sati;
GRANT ALL ON TABLE panel TO sati;
GRANT ALL ON TABLE panel TO PUBLIC;


--
-- Name: rack; Type: ACL; Schema: redes; Owner: sati
--

REVOKE ALL ON TABLE rack FROM PUBLIC;
REVOKE ALL ON TABLE rack FROM sati;
GRANT ALL ON TABLE rack TO sati;
GRANT ALL ON TABLE rack TO PUBLIC;


--
-- Name: rangeip; Type: ACL; Schema: redes; Owner: sati
--

REVOKE ALL ON TABLE rangeip FROM PUBLIC;
REVOKE ALL ON TABLE rangeip FROM sati;
GRANT ALL ON TABLE rangeip TO sati;
GRANT ALL ON TABLE rangeip TO PUBLIC;


--
-- Name: rangeip_codigo_seq; Type: ACL; Schema: redes; Owner: sati
--

REVOKE ALL ON SEQUENCE rangeip_codigo_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE rangeip_codigo_seq FROM sati;
GRANT ALL ON SEQUENCE rangeip_codigo_seq TO sati;
GRANT ALL ON SEQUENCE rangeip_codigo_seq TO PUBLIC;


--
-- Name: rede; Type: ACL; Schema: redes; Owner: sati
--

REVOKE ALL ON TABLE rede FROM PUBLIC;
REVOKE ALL ON TABLE rede FROM sati;
GRANT ALL ON TABLE rede TO sati;
GRANT ALL ON TABLE rede TO PUBLIC;


--
-- Name: rede_codigo_seq; Type: ACL; Schema: redes; Owner: sati
--

REVOKE ALL ON SEQUENCE rede_codigo_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE rede_codigo_seq FROM sati;
GRANT ALL ON SEQUENCE rede_codigo_seq TO sati;
GRANT ALL ON SEQUENCE rede_codigo_seq TO PUBLIC;


--
-- Name: rede_municipio; Type: ACL; Schema: redes; Owner: sati
--

REVOKE ALL ON TABLE rede_municipio FROM PUBLIC;
REVOKE ALL ON TABLE rede_municipio FROM sati;
GRANT ALL ON TABLE rede_municipio TO sati;
GRANT ALL ON TABLE rede_municipio TO PUBLIC;


--
-- Name: segmento; Type: ACL; Schema: redes; Owner: sati
--

REVOKE ALL ON TABLE segmento FROM PUBLIC;
REVOKE ALL ON TABLE segmento FROM sati;
GRANT ALL ON TABLE segmento TO sati;
GRANT ALL ON TABLE segmento TO PUBLIC;


--
-- Name: servidor; Type: ACL; Schema: redes; Owner: sati
--

REVOKE ALL ON TABLE servidor FROM PUBLIC;
REVOKE ALL ON TABLE servidor FROM sati;
GRANT ALL ON TABLE servidor TO sati;
GRANT ALL ON TABLE servidor TO PUBLIC;


--
-- Name: tipoconector; Type: ACL; Schema: redes; Owner: sati
--

REVOKE ALL ON TABLE tipoconector FROM PUBLIC;
REVOKE ALL ON TABLE tipoconector FROM sati;
GRANT ALL ON TABLE tipoconector TO sati;
GRANT ALL ON TABLE tipoconector TO PUBLIC;


--
-- Name: tipomodulo; Type: ACL; Schema: redes; Owner: sati
--

REVOKE ALL ON TABLE tipomodulo FROM PUBLIC;
REVOKE ALL ON TABLE tipomodulo FROM sati;
GRANT ALL ON TABLE tipomodulo TO sati;
GRANT ALL ON TABLE tipomodulo TO PUBLIC;


--
-- Name: tomada; Type: ACL; Schema: redes; Owner: sati
--

REVOKE ALL ON TABLE tomada FROM PUBLIC;
REVOKE ALL ON TABLE tomada FROM sati;
GRANT ALL ON TABLE tomada TO sati;
GRANT ALL ON TABLE tomada TO PUBLIC;


--
-- Name: tomadapanel; Type: ACL; Schema: redes; Owner: sati
--

REVOKE ALL ON TABLE tomadapanel FROM PUBLIC;
REVOKE ALL ON TABLE tomadapanel FROM sati;
GRANT ALL ON TABLE tomadapanel TO sati;
GRANT ALL ON TABLE tomadapanel TO PUBLIC;


--
-- Name: tomadaremota; Type: ACL; Schema: redes; Owner: sati
--

REVOKE ALL ON TABLE tomadaremota FROM PUBLIC;
REVOKE ALL ON TABLE tomadaremota FROM sati;
GRANT ALL ON TABLE tomadaremota TO sati;
GRANT ALL ON TABLE tomadaremota TO PUBLIC;


SET search_path = viagem, pg_catalog;

--
-- Name: eventoreqviagem; Type: ACL; Schema: viagem; Owner: sati
--

REVOKE ALL ON TABLE eventoreqviagem FROM PUBLIC;
REVOKE ALL ON TABLE eventoreqviagem FROM sati;
GRANT ALL ON TABLE eventoreqviagem TO sati;
GRANT ALL ON TABLE eventoreqviagem TO PUBLIC;


--
-- Name: tarefa; Type: ACL; Schema: viagem; Owner: sati
--

REVOKE ALL ON TABLE tarefa FROM PUBLIC;
REVOKE ALL ON TABLE tarefa FROM sati;
GRANT ALL ON TABLE tarefa TO sati;
GRANT ALL ON TABLE tarefa TO PUBLIC;


--
-- Name: tarefa_codigo_seq; Type: ACL; Schema: viagem; Owner: sati
--

REVOKE ALL ON SEQUENCE tarefa_codigo_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE tarefa_codigo_seq FROM sati;
GRANT ALL ON SEQUENCE tarefa_codigo_seq TO sati;
GRANT ALL ON SEQUENCE tarefa_codigo_seq TO PUBLIC;


--
-- Name: tipoeventoreqviagem; Type: ACL; Schema: viagem; Owner: sati
--

REVOKE ALL ON TABLE tipoeventoreqviagem FROM PUBLIC;
REVOKE ALL ON TABLE tipoeventoreqviagem FROM sati;
GRANT ALL ON TABLE tipoeventoreqviagem TO sati;
GRANT ALL ON TABLE tipoeventoreqviagem TO PUBLIC;


--
-- Name: viagem; Type: ACL; Schema: viagem; Owner: sati
--

REVOKE ALL ON TABLE viagem FROM PUBLIC;
REVOKE ALL ON TABLE viagem FROM sati;
GRANT ALL ON TABLE viagem TO sati;
GRANT ALL ON TABLE viagem TO PUBLIC;


--
-- Name: viagem_codigo_seq; Type: ACL; Schema: viagem; Owner: sati
--

REVOKE ALL ON SEQUENCE viagem_codigo_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE viagem_codigo_seq FROM sati;
GRANT ALL ON SEQUENCE viagem_codigo_seq TO sati;
GRANT ALL ON SEQUENCE viagem_codigo_seq TO PUBLIC;


--
-- PostgreSQL database dump complete
--

