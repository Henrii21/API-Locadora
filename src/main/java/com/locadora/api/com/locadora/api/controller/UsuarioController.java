package com.locadora.api.controller;

import com.locadora.api.model.Usuario;
import com.locadora.api.service.UsuarioService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public Usuario criarUsuario(@RequestBody Usuario usuario) {
        return usuarioService.criarUsuario(usuario);
    }

    @GetMapping("/{id}")
    public Usuario buscarUsuario(@PathVariable Long id) {
        return usuarioService.buscarUsuario(id);
    }

    @PutMapping("/{id}/divida")
    public Usuario atualizarDivida(
            @PathVariable Long id,
            @RequestParam double valor
    ) {
        return usuarioService.atualizarDivida(id, valor);
    }

    @GetMapping("/{id}/divida")
    public double consultarDivida(@PathVariable Long id) {
        Usuario usuario = usuarioService.buscarUsuario(id);
        return usuario.getDividaTotal();
    }
}
