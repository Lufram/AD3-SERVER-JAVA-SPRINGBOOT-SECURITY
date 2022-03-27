package com.sistem.blog.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
@Getter@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="publication",uniqueConstraints={@UniqueConstraint(columnNames = {"title"})})
public class Publication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "descripcion", nullable = false)
    private String description;
    @Column(name = "contenido", nullable = false)
    private String content;
}