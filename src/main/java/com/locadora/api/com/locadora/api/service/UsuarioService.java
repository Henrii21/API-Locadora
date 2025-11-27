package com.locadora.api.service;

import com.locadora.api.model.Usuario;
import com.locadora.api.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario salvar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public boolean usuarioTemDividas(Usuario usuario) {
        return usuario.getDividaTotal() > 0;
    }

    public void adicionarDivida(Usuario usuario, double valor) {
        usuario.setDividaTotal(usuario.getDividaTotal() + valor);
        usuarioRepository.save(usuario);
    }

    public void quitarDividas(Usuario usuario) {
        usuario.setDividaTotal(0);
        usuarioRepository.save(usuario);
    }
}
