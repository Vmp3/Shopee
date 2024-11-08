package com.projeto.shopee.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.shopee.dto.LojaDTO;
import com.projeto.shopee.exception.UsuarioJaPossuiLojaException;
import com.projeto.shopee.service.LojaService;

import jakarta.servlet.http.HttpSession;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/lojas")
public class LojaController {

    @Autowired
    private LojaService lojaService;

    @GetMapping
    public List<LojaDTO> getAllLojas() {
        return lojaService.getAllLojas();
    }

@GetMapping("/{id}")
public ResponseEntity<?> getLojaById(@PathVariable Long id, HttpSession session) {
    Long usuarioId = (Long) session.getAttribute("userId");
    if (usuarioId == null) {
        System.out.println("Usuário não autenticado");
        return ResponseEntity.status(401).body("Usuário não autenticado");
    }
    System.out.println("Usuário autenticado com ID: " + usuarioId);
    LojaDTO loja = lojaService.getLojaById(id);
    if (loja == null) {
        System.out.println("Loja não encontrada com ID: " + id);
        return ResponseEntity.status(404).body("Loja não encontrada");
    }
    System.out.println("Loja encontrada: " + loja.getNome());
    return ResponseEntity.ok(loja);
}

    @GetMapping("/minha-loja")
    public ResponseEntity<?> getLojaByUsuario(HttpSession session) {
        Long usuarioId = (Long) session.getAttribute("userId");
        if (usuarioId == null) {
            return ResponseEntity.status(401).body("Usuário não autenticado");
        }
        LojaDTO loja = lojaService.getLojaByUsuarioId(usuarioId);
        if (loja == null) {
            return ResponseEntity.status(404).body("Loja não encontrada");
        }
        return ResponseEntity.ok(loja);
    }

    @PostMapping
    public ResponseEntity<?> createLoja(@RequestBody LojaDTO lojaDTO, HttpSession session) {
        Long usuarioId = (Long) session.getAttribute("userId");
        if (usuarioId == null) {
            return ResponseEntity.status(401).body("Usuário não autenticado");
        }
        lojaDTO.setUsuarioId(usuarioId);
        try {
            LojaDTO novaLoja = lojaService.createLoja(lojaDTO);
            return ResponseEntity.ok(novaLoja);
        } catch (UsuarioJaPossuiLojaException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<LojaDTO> updateLoja(@PathVariable Long id, @RequestBody LojaDTO lojaDTO) {
        LojaDTO lojaAtualizada = lojaService.updateLoja(id, lojaDTO);
        return ResponseEntity.ok(lojaAtualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLoja(@PathVariable Long id) {
        lojaService.deleteLoja(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/verificar-loja")
    public ResponseEntity<String> verificarLojaUsuario(HttpSession session) {
        Long usuarioId = (Long) session.getAttribute("userId");
        if (usuarioId == null) {
            return ResponseEntity.status(401).body("Usuário não autenticado");
        }
        boolean possuiLoja = lojaService.usuarioPossuiLoja(usuarioId);
        if (possuiLoja) {
            return ResponseEntity.ok("Redirecionar para /minha-loja");
        } else {
            return ResponseEntity.ok("Redirecionar para /criar-loja");
        }
    }
}
