package com.locadora.api.service;

import com.locadora.api.model.Emprestimo;
import com.locadora.api.model.Item;
import com.locadora.api.model.Usuario;
import com.locadora.api.repository.EmprestimoRepository;
import com.locadora.api.repository.ItemRepository;
import com.locadora.api.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class EmprestimoService {

    private final EmprestimoRepository emprestimoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ItemRepository itemRepository;

    private final int DIAS_EMPRESTIMO = 7;       // duração padrão
    private final double MULTA_DIARIA = 2.0;     // valor definido pelo professor

    public EmprestimoService(EmprestimoRepository emprestimoRepository,
                             UsuarioRepository usuarioRepository,
                             ItemRepository itemRepository) {
        this.emprestimoRepository = emprestimoRepository;
        this.usuarioRepository = usuarioRepository;
        this.itemRepository = itemRepository;
    }

    public Optional<Emprestimo> buscarPorId(Long id) {
        return emprestimoRepository.findById(id);
    }

    public List<Emprestimo> listarTodos() {
        return emprestimoRepository.findAll();
    }

    public Emprestimo emprestar(Long usuarioId, Long itemId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (usuario.getDividaTotal() > 0) {
            throw new RuntimeException("Usuário com dívidas não pode emprestar.");
        }

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item não encontrado"));

        long emprestados = emprestimoRepository.findAll().stream()
                .filter(e -> !e.isFinalizado() && e.getItem().getId().equals(itemId))
                .count();

        if (emprestados >= item.getQuantidadeEstoque()) {
            throw new RuntimeException("Item sem estoque disponível");
        }

        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setUsuario(usuario);
        emprestimo.setItem(item);
        emprestimo.setDataEmprestimo(LocalDate.now());
        emprestimo.setDataPrevistaDevolucao(LocalDate.now().plusDays(DIAS_EMPRESTIMO));
        emprestimo.setRenovacoes(0);
        emprestimo.setFinalizado(false);

        return emprestimoRepository.save(emprestimo);
    }

    public Emprestimo renovar(Long emprestimoId) {
        Emprestimo emprestimo = buscarPorId(emprestimoId)
                .orElseThrow(() -> new RuntimeException("Empréstimo não encontrado"));

        if (emprestimo.isFinalizado())
            throw new RuntimeException("Empréstimo já finalizado.");

        if (emprestimo.getRenovacoes() >= 2)
            throw new RuntimeException("Limite de renovações atingido.");

        emprestimo.setRenovacoes(emprestimo.getRenovacoes() + 1);
        emprestimo.setDataPrevistaDevolucao(
                emprestimo.getDataPrevistaDevolucao().plusDays(DIAS_EMPRESTIMO)
        );

        return emprestimoRepository.save(emprestimo);
    }

    public Emprestimo devolver(Long emprestimoId) {
        Emprestimo emprestimo = buscarPorId(emprestimoId)
                .orElseThrow(() -> new RuntimeException("Empréstimo não encontrado"));

        if (emprestimo.isFinalizado())
            throw new RuntimeException("Este empréstimo já foi finalizado.");

        emprestimo.setDataDevolucao(LocalDate.now());
        emprestimo.setFinalizado(true);

        long atraso = Math.max(0,
                ChronoUnit.DAYS.between(
                        emprestimo.getDataPrevistaDevolucao(),
                        emprestimo.getDataDevolucao()
                )
        );

        double multa = atraso * MULTA_DIARIA;
        emprestimo.setMulta(multa);

        if (multa > 0) {
            Usuario usuario = emprestimo.getUsuario();
            usuario.setDividaTotal(usuario.getDividaTotal() + multa);
            usuarioRepository.save(usuario);
        }

        return emprestimoRepository.save(emprestimo);
    }
}
