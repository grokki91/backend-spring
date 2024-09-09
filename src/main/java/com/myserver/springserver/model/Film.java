package com.myserver.springserver.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Entity
@Builder
@Table(name="Films")
public class Film {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(unique = true)
    public String title;

    public Integer time;

    public Film() {}

    public Film(Long id,String title, Integer time) {
        this.id = id;
        this.title = title;
        this.time = time;
    }
}
