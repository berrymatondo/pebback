package com.peb.pebb.resume;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@CrossOrigin("*")
@RestController
@RequestMapping("/peb")
public class ResumeController {

    private final ResumeService resumeService;

    @Autowired
    public ResumeController(ResumeService resumeService) {
        this.resumeService = resumeService;
    }

    // Fetch all résumé
    @GetMapping("/resumes")
    public List<Resume> getResumes() {
        return resumeService.getResumes();
    }

    // Fetch all résumé
    @GetMapping("/resumes/category/{cat}")
    public List<Resume> getResumesByCategory(@PathVariable("cat") String cat) {
        return resumeService.getResumesByCategory(cat);
    }

    // Fetch all résumé
    @GetMapping("/resumes/category/{cat}/{userId}")
    public List<Resume> getResumesByCategoryByUserId(@PathVariable("cat") String cat,
            @PathVariable("userId") Long userId) {
        System.out.println("cat=" + cat + " " + "userId=" + userId);
        return resumeService.getResumesByCategoryByUserId(cat, userId);
    }

    // Add a new résumé
    @PostMapping("/resumes/add")
    public Resume addResume(@RequestBody Resume orateur) {
        return resumeService.addResume(orateur);
    }

    // Delete résumé
    @DeleteMapping("/resumes/delete/{id}")
    public void deleteResume(@PathVariable("id") Long id) {
        resumeService.deleteResume(id);
    }

    // Fetch résumé
    @GetMapping("/resumes/{id}")
    public Resume getResume(@PathVariable("id") Long id) {
        return resumeService.getResume(id);
    }

    // Update résumé
    @PutMapping("/resumes/update")
    public void updateResume(@RequestBody Resume resume) {
        resumeService.updateResume(resume);
    }

    // addTag
    @PostMapping("/resumes/tag/add")
    public void addTag(@RequestBody InTag tagResume) {
        resumeService.addTag(tagResume.getUsId(), tagResume.getResId());
    }

    // addTag
    @GetMapping("/resumes/tag")
    public TagResume getTag(@RequestBody TagResume tagResume) {
        return resumeService.getTag(tagResume.getAppusersId(), tagResume.getResumesResumeId());
    }

    // untag resume
    @DeleteMapping("/resumes/tag/delete")
    public void deleteTag(@RequestBody TagResume tagResume) {
        resumeService.deleteTag(tagResume.getAppusersId(), tagResume.getResumesResumeId());
    }

}

@Data
@AllArgsConstructor
@NoArgsConstructor
class InTag {
    private Long usId;
    private Long resId;
}
