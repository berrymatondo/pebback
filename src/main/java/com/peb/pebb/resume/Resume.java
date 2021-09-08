package com.peb.pebb.resume;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.peb.pebb.orateur.Orateur;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "resumes")
@AllArgsConstructor
@NoArgsConstructor
public class Resume {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long resumeId;
    private String date;
    private String category;
    private String reference;
    private String texte;
    private String theme;
    @Column(columnDefinition = "TEXT")
    private String message;

    /*
     * @JsonIgnore
     * 
     * @ManyToOne
     * 
     * @JoinColumn(name = "orateur_id") private Orateur orateur;
     */
    // private Orateur orateur;

    // @Transient
    private Long orateurId;

    @Transient
    private String firstname;

    @Transient
    private String lastname;
}
