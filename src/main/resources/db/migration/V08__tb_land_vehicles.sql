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

-- Copiando estrutura para tabela public.tb_land_vehicles
CREATE TABLE IF NOT EXISTS "tb_land_vehicles" (
	"autonomy_city" DOUBLE PRECISION NULL DEFAULT NULL,
	"autonomy_road" DOUBLE PRECISION NULL DEFAULT NULL,
	"brake_type" VARCHAR(255) NULL DEFAULT NULL,
	"ground_clearance" DOUBLE PRECISION NULL DEFAULT NULL,
	"number_of_gears" INTEGER NULL DEFAULT NULL,
	"steering_type" VARCHAR(255) NULL DEFAULT NULL,
	"tire_size" INTEGER NULL DEFAULT NULL,
	"transmission_type" INTEGER NULL DEFAULT NULL,
	"id" VARCHAR(255) NOT NULL,
	PRIMARY KEY ("id"),
	CONSTRAINT "fktq95w05gk63jnc972obes6yie" FOREIGN KEY ("id") REFERENCES "tb_vehicles" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Copiando dados para a tabela public.tb_land_vehicles: -1 rows
/*!40000 ALTER TABLE "tb_land_vehicles" DISABLE KEYS */;
/*!40000 ALTER TABLE "tb_land_vehicles" ENABLE KEYS */;

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
