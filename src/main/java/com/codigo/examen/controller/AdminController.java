package com.codigo.examen.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ms-examen/v1/usuarios")
@RequiredArgsConstructor
public class AdminController {
    @GetMapping
    public ResponseEntity<String> saludoAdmin(){
        return ResponseEntity.ok("Hola Admin");
    }
}
