package com.peb.pebb.resume;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import com.peb.pebb.appUser.AppUser;
import com.peb.pebb.appUser.AppUserRepository;
import com.peb.pebb.orateur.Orateur;
import com.peb.pebb.orateur.OrateurRepository;
import com.peb.pebb.role.Role;

import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ResumeService {

    private final ResumeRepository resumeRepository;
    private final OrateurRepository orateurRepository;
    private final AppUserRepository appUserRepository;

    @Autowired
    public ResumeService(ResumeRepository resumeRepository, OrateurRepository orateurRepository,
            AppUserRepository appUserRepository) {
        this.resumeRepository = resumeRepository;
        this.orateurRepository = orateurRepository;
        this.appUserRepository = appUserRepository;
    }

    // Get all rrésumés

    public List<Resume> getResumes() {
        return resumeRepository.findAll();
    }

    // Add résumé
    public Resume addResume(Resume resume) {

        // String myDate = resume.getDate().substring(6, 10) +
        // resume.getDate().substring(3, 5)
        // + resume.getDate().substring(0, 2);
        // System.out.println("MyDate==============================" + myDate);
        // resume.setDate(myDate);
        // String output = myDate.substring(6, 8) + "/" + myDate.substring(4, 6) + "/" +
        // myDate.substring(0, 4);
        // System.out.println("output==============================" + output);
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
    public Resume getResume(Long userId, Long id) {

        Resume resume = resumeRepository.findById(id).get();
        Orateur orateur = orateurRepository.getById(resume.getOrateurId());
        resume.setFirstname(orateur.getFirstname());
        resume.setLastname(orateur.getLastname());
        // String output = myDate.substring(6, 8) + "/" + myDate.substring(4, 6) + "/" +
        // myDate.substring(0, 4);
        /*
         * resume.setDate(resume.getDate().substring(6, 8) + "/" +
         * resume.getDate().substring(4, 6) + "/" + resume.getDate().substring(0, 4));
         * System.out.println("MyDate 0==============================" +
         * resume.getDate());
         */
        if (resumeRepository.tagExists(userId, id)) {
            resume.setTag(true);
        }
        return resume;
    }

    public Resume getResumeUnknown(Long id) {

        Resume resume = resumeRepository.findById(id).get();
        Orateur orateur = orateurRepository.getById(resume.getOrateurId());
        resume.setFirstname(orateur.getFirstname());
        resume.setLastname(orateur.getLastname());
        /*
         * resume.setDate(resume.getDate().substring(6, 8) + "/" +
         * resume.getDate().substring(4, 6) + "/" + resume.getDate().substring(0, 4));
         * System.out.println("MyDate 1==============================" +
         * resume.getDate());
         */

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
        List<Resume> resumes2 = new ArrayList<Resume>();
        for (int i = 0; i < resumes.size(); i++) {
            // System.out.println("size= " + resumes.size());
            // System.out.println("IDdddddddddddddddddddddddddd= " +
            // resumes.get(i).getOrateurId());
            Orateur orateur = orateurRepository.findById(resumes.get(i).getOrateurId()).get();
            resumes.get(i).setFirstname(orateur.getFirstname());
            resumes.get(i).setLastname(orateur.getLastname());
            if (resumes.get(i).isPublished())
                resumes2.add(resumes.get(i));
        }

        // return resumes;
        return resumes2;
    }

    @Transactional
    public void addTag(Long appuserId, Long resumeId) {

        // System.out.println("----------------IN----------------");
        AppUser appUser = appUserRepository.findById(appuserId).get();
        // System.out.println("----------------IN2----------------");
        Resume resume = resumeRepository.findById(resumeId).get();
        // System.out.println("----------------IN3----------------");
        // System.out.println("appuserId=" + appuserId + " " + "resumeId=" + resumeId);

        // TagResume tag = resumeRepository.getTag(appuserId, resumeId);

        if (resumeRepository.tagExists(appuserId, resumeId)) {
            // System.out.println("----------------NULL----------------");
            resumeRepository.deleteTag(appuserId, resumeId);

        } else {
            // System.out.println("----------------NOT NULL----------------");
            appUser.getResumes().add(resume);
        }

        // return resumeRepository.addTag(appuserId, resumeId);
    }

    public TagResume getTag(Long appuserId, Long resumeId) {
        return resumeRepository.getTag(appuserId, resumeId);
    }

    public void deleteTag(Long appuserId, Long resumeId) {
        resumeRepository.deleteTag(appuserId, resumeId);
    }

    public List<Resume> getResumesByCategoryByUserId(String cat, Long userId) {

        boolean isAdmin = false;
        AppUser appUser = appUserRepository.findById(userId).get();
        // appUser.getRoles().stream().map(r -> System.out.println(r.getName()));

        List<String> claims2 = new ArrayList<>();
        claims2 = appUser.getAuthorities().stream().map(r -> r.getAuthority()).collect(Collectors.toList());
        for (int i = 0; i < claims2.size(); i++) {

            System.out.println(i + " " + claims2.get(i));

            if (claims2.get(i).equals("ROLE_ADMIN"))
                isAdmin = true;

        }

        List<Resume> resumes = resumeRepository.findByCategory(cat);
        List<Resume> resumes2 = new ArrayList<>();
        for (int i = 0; i < resumes.size(); i++) {
            // System.out.println("size= " + resumes.size());
            // System.out.println("IDdddddddddddddddddddddddddd= " +
            // resumes.get(i).getOrateurId());
            Orateur orateur = orateurRepository.findById(resumes.get(i).getOrateurId()).get();
            resumes.get(i).setFirstname(orateur.getFirstname());
            resumes.get(i).setLastname(orateur.getLastname());

            if (resumeRepository.tagExists(userId, resumes.get(i).getResumeId())) {
                resumes.get(i).setTag(true);
            }

            if (resumes.get(i).isPublished())
                resumes2.add(resumes.get(i));
        }

        if (isAdmin)
            return resumes;
        else
            return resumes2;
    }

    @Transactional
    public void updatePublish(Long resumeId, Boolean status) {
        Resume resume = resumeRepository.findById(resumeId).get();
        resume.setPublished(status);
    }

    public TotalResume getTotalResume() {
        Long totEbm = resumeRepository.getTotalResume("ebm");
        Long totCulte = resumeRepository.getTotalResume("culte");
        Long totAutre = resumeRepository.getTotalResume("autre");

        return (new TotalResume(totEbm, totCulte, totAutre));

    }

}
