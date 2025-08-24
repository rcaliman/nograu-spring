-- V2: Migra os dados do schema antigo (nograu) para o novo schema (db_nograu)
-- Preserva as datas históricas conforme solicitado.

-- Migração para a tabela tb_bikefit (usando a data original)
insert into db_nograu.tb_bikefit 
(id, cavalo, esterno, braco, email, created_at, tronco, quadro_speed, quadro_mtb, altura_selim, top_tube_efetivo, updated_at)
select 
    id, cavalo, esterno, braco, email, data, -- A coluna 'data' (DATE) será convertida para DATETIME com hora 00:00:00
    (esterno - cavalo),
    (cavalo * 0.67),
    ((cavalo * 0.67 - 10) * 0.393700787),
    (cavalo * 0.883),
    (((esterno - cavalo + braco) / 2) + 4),
    null
from nograu.bikefit_modelbikefit;

alter table db_nograu.tb_bikefit auto_increment = 6000;

-- Migração para a tabela tb_mural (usando a data original)
insert into db_nograu.tb_mural
(id, nome, email, mensagem, created_at, updated_at)
select id, nome, email, mensagem, data, null from nograu.mural_modelmural; -- A coluna 'data' já é DATETIME, então a hora será preservada

alter table db_nograu.tb_mural auto_increment = 100;

-- Migração para a tabela tb_link (usando a data da migração, pois não há data original)
insert into db_nograu.tb_link (id, descricao, link, created_at)
select id, descricao, link, now() from nograu.bikefit_modelbikefitlinks;

alter table db_nograu.tb_link auto_increment = 10;

-- Migração para a tabela tb_calculo_emprestimo (usando a data original)
insert into db_nograu.tb_calculo_emprestimo
(id, codigo_banco, proxima_parcela, ultima_parcela, quantidade_parcelas, valor_parcela, valor_emprestado, created_at)
select
    id,
    codigo_banco,
    str_to_date(proxima_parcela, '%d%m%Y'),
    str_to_date(ultima_parcela, '%d%m%Y'),
    cast(quantidade_parcelas as unsigned),
    cast(valor_parcela as double) / 100,
    cast(valor_emprestado as double) / 100,
    data_calculo -- Mapeamento direto de DATE para DATE
from nograu.pcd_modelpcd;

alter table db_nograu.tb_calculo_emprestimo auto_increment = 5000;