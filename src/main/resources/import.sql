

insert into marcas (id,ativo,dt_atualizacao,dt_cadastro,nome) Values (1,true,'10-05-2000 10:05:20', '10-05-2000 10:05:20', 'Honda');



insert into modelos (id,ativo,dt_atualizacao,dt_cadastro,nome, marca) Values (1,true,'10-05-2000 10:05:20', '10-05-2000 10:05:20','biz',1);


insert into condutores (id,ativo,dt_atualizacao,dt_cadastro,cpf,nome,telefone, tempodesconto,tempogasto,tempototal) Values (1,true,'10-05-2000 10:05:20', '10-05-2000 10:05:20', '102.987.859-02', 'eduardo', '45 3525-7073', '00:00:00', '00:00:00',0);


insert into configuracoes(id,ativo,dt_atualizacao,dt_cadastro,fimexpediente,vgerardesconto,inicioexpediente,tempodedesconto,tempoparadesconto, vagascarro,vagasmoto,vagasvan,valorhora,valorminutomulta)values(1,true,'10-05-2000 10:05:20','10-05-2000 18:05:20', '18:00:00', false, '08:00:00','00:00:00','00:00:00','25','25','25','10.00','01.00' )


insert into veiculos (id,ativo,dt_atualizacao,dt_cadastro,ano, cor,placa,  tipo, modelo) Values (1,true,'10-05-2000 10:05:20', '10-05-2000 10:05:20','2000','Preto','zzz-bbbb', 'Carro', 1 );



insert into movimentacoes (id,ativo,dt_atualizacao,dt_cadastro,entrada,tempo_total_hora,tempo_total_minuto,saida,  tempodesconto, tempomulta_minuto, tempomulta_hora,valordesconto, valorhora, valorminutomulta, valormulta,valortotal, condutor, veiculo ) Values (1,true,'10-05-2000 10:05:20', '10-05-2000 10:05:20','10-05-2000 10:05:20',0,0,' 10-05-2000 10:05:20', 0,0,0, '000.00', '10.00', '100.00', '10.00', '10.00',1,1);

