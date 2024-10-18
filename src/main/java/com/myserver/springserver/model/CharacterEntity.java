package com.myserver.springserver.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="Character")
public class CharacterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @NonNull
    @Column(unique = true)
    public String alias;

    @NonNull
    public String full_name;

    @NonNull
    public String alignment;

    @NonNull
    public String abilities;

    @NonNull
    public Integer age;

    @NonNull
    public String team;

}
