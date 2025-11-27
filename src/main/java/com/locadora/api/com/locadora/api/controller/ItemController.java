package com.locadora.api.controller;

import com.locadora.api.model.Item;
import com.locadora.api.repository.EmprestimoRepository;
import com.locadora.api.service.ItemService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/itens")
public class ItemController {

    private final ItemService itemService;
    private final EmprestimoRepository emprestimoRepository;

    public ItemController(ItemService itemService, EmprestimoRepository emprestimoRepository) {
        this.itemService = itemService;
        this.emprestimoRepository = emprestimoRepository;
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@Valid @RequestBody Item item) {
        return ResponseEntity.ok(itemService.salvar(item));
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable Long id) {
        return itemService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @GetMapping("/{id}/disponibilidade")
    public ResponseEntity<?> disponibilidade(@PathVariable Long id) {

        var item = itemService.buscarPorId(id)
                .orElse(null);

        if (item == null)
            return ResponseEntity.notFound().build();

        long emprestados = emprestimoRepository.findAll().stream()
                .filter(e -> !e.isFinalizado() && e.getItem().getId().equals(id))
                .count();

        int disponiveis = item.getQuantidadeEstoque() - (int) emprestados;

        return ResponseEntity.ok(disponiveis);
    }
}
