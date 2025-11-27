package com.locadora.api.controller;

import com.locadora.api.model.Emprestimo;
import com.locadora.api.service.EmprestimoService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/emprestimos")
public class EmprestimoController {

    private final EmprestimoService emprestimoService;

    public EmprestimoController(EmprestimoService emprestimoService) {
        this.emprestimoService = emprestimoService;
    }

    @PostMapping
    public Emprestimo criarEmprestimo(@RequestParam Long usuarioId, @RequestParam Long itemId) {
        return emprestimoService.emprestar(usuarioId, itemId);
    }

    @PostMapping("/{id}/renovar")
    public Emprestimo renovar(@PathVariable Long id) {
        return emprestimoService.renovar(id);
    }

    @PostMapping("/{id}/devolver")
    public Emprestimo devolver(@PathVariable Long id) {
        return emprestimoService.devolver(id);
    }
}
