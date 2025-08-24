-- V1: Cria a estrutura de tabelas inicial para toda a aplicação (nomes em minúsculo)

create table tb_bikefit (
    id bigint not null auto_increment,
    cavalo double precision not null,
    esterno double precision not null,
    braco double precision not null,
    tronco double precision,
    email varchar(255) not null,
    quadro_speed double precision,
    quadro_mtb double precision,
    altura_selim double precision,
    top_tube_efetivo double precision,
    created_at datetime(6) not null,
    updated_at datetime(6),
    primary key (id)
);

create table tb_mural (
    id bigint not null auto_increment,
    nome varchar(255) not null,
    email varchar(255),
    mensagem text not null,
    created_at datetime(6) not null,
    updated_at datetime(6),
    primary key (id)
);

create table tb_link (
    id bigint not null auto_increment,
    descricao varchar(250) not null,
    link varchar(200) not null,
    created_at datetime(6) not null,
    updated_at datetime(6),
    primary key (id)
);

create table tb_banco (
    id bigint not null auto_increment,
    ispb varchar(255) not null unique,
    nome_reduzido varchar(255) not null,
    numero_codigo varchar(255),
    participa_compe varchar(255) not null,
    acesso_principal varchar(255) not null,
    nome_extenso varchar(255) not null,
    inicio_operacao varchar(255) not null,
    created_at date not null,
    primary key (id)
);

create table tb_calculo_emprestimo (
    id bigint not null auto_increment,
    codigo_banco varchar(255) not null,
    proxima_parcela date not null,
    ultima_parcela date not null,
    quantidade_parcelas integer not null,
    valor_parcela double precision not null,
    valor_emprestado double precision not null,
    taxa_juros double precision,
    meses_em_ser integer,
    saldo_devedor double precision,
    created_at date,
    primary key (id)
);