package com.peb.pebb.resume;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

import javax.transaction.Transactional;

import com.peb.pebb.orateur.Orateur;
import com.peb.pebb.orateur.OrateurRepository;

import java.util.Objects;

@Service
public class ResumeService {

    private final ResumeRepository resumeRepository;
    private final OrateurRepository orateurRepository;

    @Autowired
    public ResumeService(ResumeRepository resumeRepository, OrateurRepository orateurRepository) {
        this.resumeRepository = resumeRepository;
        this.orateurRepository = orateurRepository;
    }

    // Get all rrésumés

    public List<Resume> getResumes() {
        return resumeRepository.findAll();
    }

    // Add résumé
    public Resume addResume(Resume resume) {

        return resumeRepository.save(resume);
    }

    // Delete résumé
    public void deleteResume(Long id) {

        boolean exists = resumeRepository.existsById(id);
        if (!exists) {
            throw new IllegalStateException("Cet résumé n'existe pas : " + id);
        }
        resumeRepository.deleteById(id);
    }

    // Retrieve a specific résumé
    public Resume getResume(Long id) {

        Resume resume = resumeRepository.findById(id).get();
        Orateur orateur = orateurRepository.getById(resume.getOrateurId());
        resume.setFirstname(orateur.getFirstname());
        resume.setLastname(orateur.getLastname());
        return resume;
    }

    // Update résumé
    @Transactional // On utilise les setters pour faire automatiquement les updates de l'entité
    public void updateResume(Resume resume) {
        Resume findResume = resumeRepository.findById(resume.getResumeId())
                .orElseThrow(() -> new IllegalStateException("Ce résumé n'existe pas : " + resume.getResumeId()));

        // Update date
        if (resume.getDate() != null && resume.getDate().length() > 0
                && !Objects.equals(resume.getDate(), findResume.getDate())) {
            findResume.setDate(resume.getDate());
        }

        // Update category
        if (resume.getCategory() != null && resume.getCategory().length() > 0
                && !Objects.equals(resume.getCategory(), findResume.getCategory())) {
            findResume.setCategory(resume.getCategory());
        }

        // Update reference
        if (resume.getReference() != null && resume.getReference().length() > 0
                && !Objects.equals(resume.getReference(), findResume.getReference())) {
            findResume.setReference(resume.getReference());
        }

        // Update texte
        if (resume.getTexte() != null && resume.getTexte().length() > 0
                && !Objects.equals(resume.getTexte(), findResume.getTexte())) {
            findResume.setTexte(resume.getTexte());
        }

        // Update theme
        if (resume.getTheme() != null && resume.getTheme().length() > 0
                && !Objects.equals(resume.getTheme(), findResume.getTheme())) {
            findResume.setTheme(resume.getTheme());
        }

        // Update message
        if (resume.getMessage() != null && resume.getMessage().length() > 0
                && !Objects.equals(resume.getMessage(), findResume.getMessage())) {
            findResume.setMessage(resume.getMessage());
        }

    }

    public List<Resume> getResumesByCategory(String cat) {

        List<Resume> resumes = resumeRepository.findByCategory(cat);
        for (int i = 0; i < resumes.size(); i++) {
            // System.out.println("size= " + resumes.size());
            // System.out.println("IDdddddddddddddddddddddddddd= " +
            // resumes.get(i).getOrateurId());
            Orateur orateur = orateurRepository.findById(resumes.get(i).getOrateurId()).get();
            resumes.get(i).setFirstname(orateur.getFirstname());
            resumes.get(i).setLastname(orateur.getLastname());
        }

        return resumes;
    }

}
