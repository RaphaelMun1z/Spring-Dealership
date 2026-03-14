package com.merco.dealership.controllers;

import com.merco.dealership.dto.DashboardStatsResponseDTO;
import com.merco.dealership.services.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
@Tag(name = "Dashboard", description = "Endpoints de resumo para o painel administrativo")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/summary")
    @Operation(
            summary     = "Retorna totais do sistema",
            description = "Retorna a contagem de veículos, itens em estoque, vendas e clientes em uma única requisição"
    )
    public ResponseEntity<DashboardStatsResponseDTO> getSummary() {
        return ResponseEntity.ok(dashboardService.getSummary());
    }
}