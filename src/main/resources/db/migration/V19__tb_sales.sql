-- --------------------------------------------------------
-- Servidor:                     127.0.0.1
-- Versão do servidor:           PostgreSQL 16.3, compiled by Visual C++ build 1938, 64-bit
-- OS do Servidor:               
-- HeidiSQL Versão:              12.8.0.6908
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES  */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

-- Copiando estrutura para tabela public.tb_sales
CREATE TABLE IF NOT EXISTS "tb_sales" (
	"id" VARCHAR(255) NOT NULL,
	"applied_discount" DOUBLE PRECISION NULL DEFAULT NULL,
	"gross_amount" DOUBLE PRECISION NOT NULL,
	"installments_number" INTEGER NOT NULL,
	"net_amount" DOUBLE PRECISION NULL DEFAULT NULL,
	"payment_method" VARCHAR(255) NOT NULL,
	"receipt" VARCHAR(255) NOT NULL,
	"sale_date" DATE NOT NULL,
	"contract_id" VARCHAR(255) NULL DEFAULT NULL,
	"customer_id" VARCHAR(255) NOT NULL,
	"inventory_item_id" VARCHAR(255) NOT NULL,
	"seller_id" VARCHAR(255) NOT NULL,
	PRIMARY KEY ("id"),
	CONSTRAINT "ukpmgoet48oa564qaclmtvbf256" UNIQUE ("contract_id"),
	CONSTRAINT "ukg85ptbt5699osarfv0eeainpd" UNIQUE ("inventory_item_id"),
	CONSTRAINT "fk1lhwdeb4675510pxs1dw5j0of" FOREIGN KEY ("inventory_item_id") REFERENCES "tb_inventory" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "fko2ayit8puh0hq87wsur041c40" FOREIGN KEY ("customer_id") REFERENCES "tb_customers" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "fkp0nn0oixeiw2fq3jivj6vnsu" FOREIGN KEY ("seller_id") REFERENCES "tb_sellers" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Copiando dados para a tabela public.tb_sales: -1 rows
/*!40000 ALTER TABLE "tb_sales" DISABLE KEYS */;
/*!40000 ALTER TABLE "tb_sales" ENABLE KEYS */;

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
