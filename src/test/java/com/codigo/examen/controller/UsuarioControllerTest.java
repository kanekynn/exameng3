package com.codigo.examen.controller;

import com.codigo.examen.entity.Usuario;
import com.codigo.examen.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioControllerTest {
    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private UsuarioController usuarioController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        usuarioController = new UsuarioController(usuarioService);
    }

    @Test
    void createUsuarioSucces()
    {
        // Arrange
        Usuario usuario = new Usuario();
        when(usuarioService.createUsuario(usuario)).thenReturn(ResponseEntity.ok(usuario));

        // Act
        ResponseEntity<Usuario> response = usuarioController.createUsuario(usuario);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(usuario, response.getBody());
        verify(usuarioService, times(1)).createUsuario(usuario);
    }
    @Test
    void createUsuarioNotFound() {
        // Arrange
        Usuario usuario = new Usuario();
        when(usuarioService.createUsuario(usuario)).thenReturn(ResponseEntity.badRequest().body(null));

        // Act
        ResponseEntity<Usuario> response = usuarioController.createUsuario(usuario);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(null, response.getBody());
        verify(usuarioService, times(1)).createUsuario(usuario);
    }

    @Test
    void getUsuarioByIdSucces() {
        // Arrange
        Long id = 1L;
        Usuario usuario = new Usuario();
        when(usuarioService.getUsuarioById(id)).thenReturn(ResponseEntity.ok(usuario));

        // Act
        ResponseEntity<Usuario> response = usuarioController.getUsuarioById(id);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(usuario, response.getBody());
        verify(usuarioService, times(1)).getUsuarioById(id);
    }
    @Test
    void getUsuarioByIdNotFound() {
        // Arrange
        Long id = 1L;
        when(usuarioService.getUsuarioById(id)).thenReturn(ResponseEntity.notFound().build());

        // Act
        ResponseEntity<Usuario> response = usuarioController.getUsuarioById(id);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(null, response.getBody());
        verify(usuarioService, times(1)).getUsuarioById(id);
    }

    @Test
    void updateUsuario_UsuarioExistente() {
        // Arrange
        Long id = 1L;
        Usuario usuario = new Usuario();
        when(usuarioService.updateUsuario(id, usuario)).thenReturn(ResponseEntity.ok(usuario));

        // Act
        ResponseEntity<Usuario> response = usuarioController.updateUsuario(id, usuario);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(usuario, response.getBody());
        verify(usuarioService, times(1)).updateUsuario(id, usuario);
    }

    @Test
    void updateUsuario_UsuarioNoExistente() {
        // Arrange
        Long id = 1L;
        Usuario usuario = new Usuario();
        when(usuarioService.updateUsuario(id, usuario)).thenReturn(ResponseEntity.notFound().build());

        // Act
        ResponseEntity<Usuario> response = usuarioController.updateUsuario(id, usuario);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(null, response.getBody());
        verify(usuarioService, times(1)).updateUsuario(id, usuario);
    }

    @Test
    void updateUsuario_UsuarioNuevoNombreExistente() {
        // Arrange
        Long id = 1L;
        Usuario usuario = new Usuario();
        when(usuarioService.updateUsuario(id, usuario)).thenReturn(ResponseEntity.badRequest().body(null));

        // Act
        ResponseEntity<Usuario> response = usuarioController.updateUsuario(id, usuario);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(null, response.getBody());
        verify(usuarioService, times(1)).updateUsuario(id, usuario);
    }

    @Test
    void deleteUsuarioSucces() {
        // Arrange
        Long id = 1L;
        when(usuarioService.deleteUsuario(id)).thenReturn(ResponseEntity.noContent().build());

        // Act
        ResponseEntity<?> response = usuarioController.deleteUsuario(id);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(usuarioService, times(1)).deleteUsuario(id);
    }

    @Test
    void deleteUsuarioNotFound() {
        // Arrange
        Long id = 1L;
        when(usuarioService.deleteUsuario(id)).thenReturn(ResponseEntity.notFound().build());

        // Act
        ResponseEntity<?> response = usuarioController.deleteUsuario(id);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(usuarioService, times(1)).deleteUsuario(id);
    }

    private Usuario getUsuario1()
    {
        return new Usuario(12L,"jose","123456","jose@gmail.com","992341526");

    }

}