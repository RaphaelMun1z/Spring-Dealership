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

-- Copiando estrutura para tabela public.tb_inventory
CREATE TABLE IF NOT EXISTS "tb_inventory" (
	"id" VARCHAR(255) NOT NULL,
	"acquisition_price" DOUBLE PRECISION NULL DEFAULT NULL,
	"chassis" VARCHAR(255) NULL DEFAULT NULL,
	"license_plate" VARCHAR(255) NULL DEFAULT NULL,
	"profit_margin" DOUBLE PRECISION NULL DEFAULT NULL,
	"stock_entry_date" DATE NULL DEFAULT NULL,
	"stock_exit_date" DATE NULL DEFAULT NULL,
	"supplier" VARCHAR(255) NULL DEFAULT NULL,
	"vehicle_id" VARCHAR(255) NOT NULL,
	PRIMARY KEY ("id"),
	CONSTRAINT "fksh20yjixljanlk0pettbv42ds" FOREIGN KEY ("vehicle_id") REFERENCES "tb_vehicles" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Copiando dados para a tabela public.tb_inventory: -1 rows
/*!40000 ALTER TABLE "tb_inventory" DISABLE KEYS */;
/*!40000 ALTER TABLE "tb_inventory" ENABLE KEYS */;

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
