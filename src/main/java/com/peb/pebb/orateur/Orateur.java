package com.peb.pebb.orateur;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "orateurs")
@AllArgsConstructor
@NoArgsConstructor
public class Orateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orateurId;
    private String lastname;
    private String firstname;
}
