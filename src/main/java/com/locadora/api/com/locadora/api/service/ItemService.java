package com.locadora.api.service;

import com.locadora.api.model.Item;
import com.locadora.api.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Item salvar(Item item) {
        return itemRepository.save(item);
    }

    public Optional<Item> buscarPorId(Long id) {
        return itemRepository.findById(id);
    }

    // quantidade disponível = estoque total - emprestados
    public int calcularDisponibilidade(Item item, int emprestados) {
        return item.getQuantidadeEstoque() - emprestados;
    }
}
