package com.example.proyectobasesspring.controllers;

import com.example.proyectobasesspring.model.Estudiante;
import com.example.proyectobasesspring.model.Profesor;
import com.example.proyectobasesspring.model.Usuario;
import com.example.proyectobasesspring.services.implementations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {
    private final UsuarioServiceImpl usuarioService;
    private final TipoUsuarioServiceImpl tipoUsuarioService;
    private final GrupoServiceImpl grupoService;
    private final EstudianteServiceImpl estudianteService;
    private final ProfesorServiceImpl profesorService;

    @PostMapping
    public ResponseEntity<?> registrarUsuario(@RequestBody Map<String, Object> userData) {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario((String) userData.get("cedula"));
        usuario.setCorreo((String) userData.get("correo"));
        usuario.setContrasena((String) userData.get("contrasena"));
        Long idTipoUsuario = Long.parseLong((String) userData.get("idTipoUsuario"));
        usuario.setIdTipoUsuario(tipoUsuarioService.buscarPorId(idTipoUsuario).get());
        try{
            Usuario usuarioGuardado = usuarioService.guardar(usuario);
            if(usuarioGuardado.getIdTipoUsuario().getId() == 1){
                Estudiante estudiante = new Estudiante();
                estudiante.setUsuarios(usuarioGuardado);
                estudiante.setNombre((String) userData.get("nombre"));
                estudiante.setApellido((String) userData.get("apellido"));
                Long idGrupo = Long.parseLong((String) userData.get("idGrupo"));
                estudiante.setGruposIdGrupo(grupoService.buscarPorId(idGrupo).get());
                try{
                    estudianteService.guardar(estudiante);
                    return ResponseEntity.ok().body(usuarioGuardado);
                }catch (Exception e){
                    return ResponseEntity.badRequest().body(e.getMessage());
                }
            }else {
                Profesor profesor = new Profesor();
                profesor.setUsuarios(usuarioGuardado);
                profesor.setNombre((String) userData.get("nombre"));
                profesor.setApellido((String) userData.get("apellido"));
                try {
                    profesorService.guardar(profesor);
                    return ResponseEntity.ok().body(usuarioGuardado);
                } catch (Exception e) {
                    return ResponseEntity.badRequest().body(e.getMessage());
                }
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al crear el usuario");
        }
    }
}