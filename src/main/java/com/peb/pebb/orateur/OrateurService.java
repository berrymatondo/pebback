package com.peb.pebb.orateur;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

import javax.transaction.Transactional;
import java.util.Objects;

@Service
public class OrateurService {

    private final OrateurRepository orateurRepository;

    @Autowired
    public OrateurService(OrateurRepository orateurRepository) {
        this.orateurRepository = orateurRepository;
    }

    // Get all orateurs

    public List<Orateur> getOrateurs() {
        return orateurRepository.findAll();
    }

    // Add orateur
    public Orateur addOrateur(Orateur orateur) {
        return orateurRepository.save(orateur);
    }

    // Delete Orateur
    public void deleteOrateur(Long id) {

        boolean exists = orateurRepository.existsById(id);
        if (!exists) {
            throw new IllegalStateException("Cet orateur n'existe pas : " + id);
        }
        orateurRepository.deleteById(id);
    }

    // Retrieve a specific orateur
    public Orateur getOrateur(Long id) {
        return orateurRepository.findById(id).get();
    }

    // Update orateur
    @Transactional // On utilise les setters pour faire automatiquement les updates de l'entité
    public void updateOrateur(Orateur orateur) {
        Orateur findOrateur = orateurRepository.findById(orateur.getOrateurId())
                .orElseThrow(() -> new IllegalStateException("Cet orateur n'existe pas : " + orateur.getOrateurId()));

        // Update Lastname
        if (orateur.getLastname() != null && orateur.getLastname().length() > 0
                && !Objects.equals(orateur.getLastname(), findOrateur.getLastname())) {
            findOrateur.setLastname(orateur.getLastname());
        }

        // Update firstname
        if (orateur.getFirstname() != null && orateur.getFirstname().length() > 0
                && !Objects.equals(orateur.getFirstname(), findOrateur.getFirstname())) {
            findOrateur.setFirstname(orateur.getFirstname());
        }

    }

}
