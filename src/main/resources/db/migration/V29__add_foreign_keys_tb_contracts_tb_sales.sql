-- Adicionando a chave estrangeira em tb_contracts para tb_sales
ALTER TABLE "tb_contracts"
ADD CONSTRAINT "fkj0edae44pnkexqb4t1mrpypdv" FOREIGN KEY ("sale_id") REFERENCES "tb_sales" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION;

-- Adicionando a chave estrangeira em tb_sales para tb_contracts
ALTER TABLE "tb_sales"
ADD CONSTRAINT "fkmlk5vlgo2k97jen6h9gmhd3o" FOREIGN KEY ("contract_id") REFERENCES "tb_contracts" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION;
