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

-- Copiando estrutura para tabela public.tb_branches
CREATE TABLE IF NOT EXISTS "tb_branches" (
	"id" VARCHAR(255) NOT NULL,
	"branch_type" VARCHAR(255) NOT NULL,
	"created_at" DATE NULL DEFAULT NULL,
	"email" VARCHAR(255) NOT NULL,
	"manager_name" VARCHAR(255) NOT NULL,
	"name" VARCHAR(255) NOT NULL,
	"opening_hours" VARCHAR(255) NOT NULL,
	"phone_number" VARCHAR(255) NULL DEFAULT NULL,
	"status" VARCHAR(255) NULL DEFAULT NULL,
	"updated_at" DATE NULL DEFAULT NULL,
	"branch_address_id" VARCHAR(255) NOT NULL,
	PRIMARY KEY ("id"),
	CONSTRAINT"ukr0vd1xihklkhf967ywfrr2jbv" UNIQUE ("branch_address_id"),
	CONSTRAINT "fkkdduhi0arjbnqu15yrs0stnn0" FOREIGN KEY ("branch_address_id") REFERENCES "tb_branches_address" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Copiando dados para a tabela public.tb_branches: -1 rows
/*!40000 ALTER TABLE "tb_branches" DISABLE KEYS */;
/*!40000 ALTER TABLE "tb_branches" ENABLE KEYS */;

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
