package com.codigo.examen.service.impl;


import com.codigo.examen.entity.Rol;
import com.codigo.examen.entity.Usuario;
import com.codigo.examen.repository.RolRepository;
import com.codigo.examen.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;



import java.util.Optional;
import java.util.Set;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class UsuarioServiceImplTest {

    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private RolRepository rolRepository;

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        usuarioService = new UsuarioServiceImpl(usuarioRepository, rolRepository);
    }


    @Test
    void createUsuario_whenPersonaExist_returnResponseUsuario() {
        // Arrange
        Usuario usuario = new Usuario();
        Optional<Usuario> usuarioOptional = Optional.empty();
        // Act
        when(usuarioRepository.findByUsername(usuario.getUsername())).thenReturn(usuarioOptional);
        ResponseEntity<Usuario> responseUsuario = usuarioService.createUsuario(usuario);
        // Assert
        assertEquals(HttpStatus.OK, responseUsuario.getStatusCode());
        verify(usuarioRepository, times(1)).findByUsername(usuario.getUsername());
    }

    @Test
    void createUsuario_whenPersonaDoesNotExist_returnEmptyOptional() {
        // Arrange
        Usuario usuario = new Usuario();
        Optional<Usuario> usuarioOptional = Optional.of(usuario);
        // Act
        when(usuarioRepository.findByUsername(usuario.getUsername())).thenReturn(usuarioOptional);
        ResponseEntity<Usuario> responseUsuario = usuarioService.createUsuario(usuario);
        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseUsuario.getStatusCode());
        verify(usuarioRepository, times(1)).findByUsername(usuario.getUsername());
    }


    @Test
    void getUsuarioByIdSuccess() {
        //Arrange
        Long id = 1L;
        Usuario usuario = new Usuario();
        Optional<Usuario> optionalUsuario = Optional.of(usuario);
        // Act
        when(usuarioRepository.findById(id)).thenReturn(optionalUsuario);

        ResponseEntity<Usuario> result01 = usuarioService.getUsuarioById(1L);

        // Assert
        assertNotNull(result01);
        assertEquals(HttpStatus.OK, result01.getStatusCode());
        assertEquals(usuario, result01.getBody());

    }

    @Test
    void getUsuarioByIdNotFound() {
        // Arrange
        Long id = 1L;
        Optional<Usuario> optionalUsuario = Optional.empty();

        // Act
        when(usuarioRepository.findById(id)).thenReturn(optionalUsuario);
        ResponseEntity<Usuario> result01 = usuarioService.getUsuarioById(id);

        // Assert
        assertNotNull(result01);
        assertEquals(HttpStatus.NOT_FOUND, result01.getStatusCode());
        assertNull(result01.getBody());
    }

    // 03
    @Test
    void updateUsuario_UsuarioExistente() {
        // Arrange
        Long id = 12L;
        Usuario usuario = new Usuario();
        usuario.setUsername("nombredeusuario");
        Rol rol = new Rol(1, "admin");
        usuario.setRoles(Set.of(rol));
        when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuario));
        when(rolRepository.findById(any())).thenReturn(Optional.of(rol));

        // Act
        ResponseEntity<Usuario> response = usuarioService.updateUsuario(id, usuario);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(usuarioRepository, times(1)).findById(id);
        verify(usuarioRepository, times(1)).save(usuario);
    }

    @Test
    void updateUsuario_UsuarioNoExistente() {
        // Arrange
        Long id = 1L;
        Usuario usuario = new Usuario();
        when(usuarioRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Usuario> response = usuarioService.updateUsuario(id, usuario);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(usuarioRepository, times(1)).findById(id);
        verify(usuarioRepository, never()).save(any());
    }

    /*@Test
    void updateUsuario_UsuarioNuevoNombreExistente() {
        // Arrange
        Long id = 1L;
        Usuario usuario = new Usuario();
        when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.findByUsername(any())).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Usuario> response = usuarioService.updateUsuario(id, usuario);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(usuarioRepository, times(1)).findById(id);
        verify(usuarioRepository, never()).save(any());
    }

     */

    //04
    @Test
    void testDeleteUsuarioSucces() {
        // Arrange
        Long id = 1L;
        Usuario usuario = new Usuario();
        Optional<Usuario> optionalUsuario = Optional.of(usuario);
        when(usuarioRepository.findById(id)).thenReturn(optionalUsuario);

        // Act
        ResponseEntity<Usuario> result = usuarioService.deleteUsuario(id);

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        verify(usuarioRepository, times(1)).delete(usuario);
    }

    @Test
    void testDeleteUsuarioFound() {
        // Arrange
        Long id = 1L;
        Optional<Usuario> optionalUsuario = Optional.empty();
        when(usuarioRepository.findById(id)).thenReturn(optionalUsuario);

        // Act
        ResponseEntity<Usuario> result = usuarioService.deleteUsuario(id);

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        verify(usuarioRepository, never()).delete(any());
    }

    private Usuario getUsuario()
    {
        return new Usuario(32L,"jose","123456","jose@gmail.com","992341526");

    }
}