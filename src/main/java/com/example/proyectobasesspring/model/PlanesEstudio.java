package com.example.proyectobasesspring.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "PLANES_ESTUDIO")
public class PlanesEstudio {
    @Id
    @Column(name = "ID_PLAN", nullable = false)
    private Long id;

    @Column(name = "NOMBRE", nullable = false, length = 40)
    private String nombre;

    @Column(name = "DESCRIPCION", nullable = false, length = 200)
    private String descripcion;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "CURSOS_ID_CURSO", nullable = false)
    private Curso cursosIdCurso;

}