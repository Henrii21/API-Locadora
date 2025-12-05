package com.locadora.api.service;

import com.locadora.api.model.Usuario;
import com.locadora.api.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario criarUsuario(Usuario usuario) {
        // dividaTotal já é double, nunca vem null
        if (usuario.getDividaTotal() < 0) {
            usuario.setDividaTotal(0.0);
        }
        return usuarioRepository.save(usuario);
    }

    public Usuario buscarUsuario(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    public Usuario atualizarDivida(Long usuarioId, double valor) {
        Usuario usuario = buscarUsuario(usuarioId);

        double novaDivida = usuario.getDividaTotal() + valor;
        usuario.setDividaTotal(novaDivida);

        return usuarioRepository.save(usuario);
    }
}
