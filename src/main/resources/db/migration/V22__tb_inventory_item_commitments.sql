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

-- Copiando estrutura para tabela public.tb_inventory_item_commitments
CREATE TABLE IF NOT EXISTS "tb_inventory_item_commitments" (
	"appointment_id" VARCHAR(255) NOT NULL,
	"inventory_item_id" VARCHAR(255) NOT NULL,
	PRIMARY KEY ("appointment_id", "inventory_item_id"),
	CONSTRAINT "fkfst6ite29x3x4fpa5six08yvv" FOREIGN KEY ("inventory_item_id") REFERENCES "tb_inventory" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "fkn41n98v37dy5vu6v2cyyv3w7d" FOREIGN KEY ("appointment_id") REFERENCES "tb_appointments" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Copiando dados para a tabela public.tb_inventory_item_commitments: -1 rows
/*!40000 ALTER TABLE "tb_inventory_item_commitments" DISABLE KEYS */;
/*!40000 ALTER TABLE "tb_inventory_item_commitments" ENABLE KEYS */;

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
