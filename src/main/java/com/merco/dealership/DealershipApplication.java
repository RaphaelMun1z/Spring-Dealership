package com.merco.dealership;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.merco.dealership.config.FileStorageConfig;

@SpringBootApplication
@EnableConfigurationProperties({ FileStorageConfig.class })
@EnableScheduling
@EnableCaching
public class DealershipApplication {

	public static void main(String[] args) {
		SpringApplication.run(DealershipApplication.class, args);
	}

}
