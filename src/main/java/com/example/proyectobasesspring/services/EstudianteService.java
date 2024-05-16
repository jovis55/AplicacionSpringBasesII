package com.example.proyectobasesspring.services;

import com.example.proyectobasesspring.model.Estudiante;

import java.util.List;
import java.util.Optional;

public interface EstudianteService {
    List<Estudiante> listarUsuarios();
    Estudiante guardar(Estudiante estudiante);
    Optional<Estudiante> buscarPorId(String id);
}
