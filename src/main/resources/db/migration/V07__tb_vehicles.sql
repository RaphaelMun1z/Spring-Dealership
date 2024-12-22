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

-- Copiando estrutura para tabela public.tb_vehicles
CREATE TABLE IF NOT EXISTS "tb_vehicles" (
	"id" VARCHAR(255) NOT NULL,
	"availability" INTEGER NULL DEFAULT NULL,
	"brand" VARCHAR(255) NOT NULL,
	"category" INTEGER NOT NULL,
	"color" VARCHAR(255) NOT NULL,
	"description" VARCHAR(255) NOT NULL,
	"engine_power" DOUBLE PRECISION NOT NULL,
	"fuel_tank_capacity" DOUBLE PRECISION NOT NULL,
	"fuel_type" INTEGER NOT NULL,
	"infotainment_system" VARCHAR(255) NOT NULL,
	"last_update" DATE NULL DEFAULT NULL,
	"manufacture_year" DATE NOT NULL,
	"mileage" DOUBLE PRECISION NOT NULL,
	"model" VARCHAR(255) NOT NULL,
	"number_of_cylinders" INTEGER NOT NULL,
	"passenger_capacity" INTEGER NOT NULL,
	"sale_price" DOUBLE PRECISION NOT NULL,
	"status" INTEGER NULL DEFAULT NULL,
	"type" INTEGER NOT NULL,
	"weight" DOUBLE PRECISION NOT NULL,
	"branch_id" VARCHAR(255) NOT NULL,
	PRIMARY KEY ("id"),
	CONSTRAINT "fksoopehewoq9qep1wbnw7bpvo" FOREIGN KEY ("branch_id") REFERENCES "tb_branches" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Copiando dados para a tabela public.tb_vehicles: -1 rows
/*!40000 ALTER TABLE "tb_vehicles" DISABLE KEYS */;
/*!40000 ALTER TABLE "tb_vehicles" ENABLE KEYS */;

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
