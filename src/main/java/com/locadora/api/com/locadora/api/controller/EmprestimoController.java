package com.locadora.api.controller;

import com.locadora.api.model.Emprestimo;
import com.locadora.api.service.EmprestimoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/emprestimos")
public class EmprestimoController {

    private final EmprestimoService emprestimoService;

    public EmprestimoController(EmprestimoService emprestimoService) {
        this.emprestimoService = emprestimoService;
    }

    // 🔹 LISTAR TODOS (GET)
    @GetMapping
    public List<Emprestimo> listarTodos() {
        return emprestimoService.listarTodos();
    }

    // 🔹 BUSCAR POR ID (GET)
    @GetMapping("/{id}")
    public Emprestimo buscarPorId(@PathVariable Long id) {
        return emprestimoService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Empréstimo não encontrado"));
    }

    // 🔹 CRIAR EMPRÉSTIMO (POST)
    @PostMapping
    public Emprestimo criarEmprestimo(@RequestParam Long usuarioId,
                                      @RequestParam Long itemId) {
        return emprestimoService.emprestar(usuarioId, itemId);
    }

    // 🔹 RENOVAR (POST)
    @PostMapping("/{id}/renovar")
    public Emprestimo renovar(@PathVariable Long id) {
        return emprestimoService.renovar(id);
    }

    // 🔹 DEVOLVER (POST)
    @PostMapping("/{id}/devolver")
    public Emprestimo devolver(
            @PathVariable Long id,
            @RequestParam(required = false) String dataDevolucao
    ) {
        return emprestimoService.devolver(id, dataDevolucao);
    }
}
