package com.peb.pebb.orateur;

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

@RestController
@RequestMapping("/peb")
public class OrateurController {

    private final OrateurService orateurService;

    @Autowired
    public OrateurController(OrateurService orateurService) {
        this.orateurService = orateurService;
    }

    // Fetch all orateur
    @GetMapping("/orateurs")
    public List<Orateur> getOrateurs() {
        System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        return orateurService.getOrateurs();
    }

    // Add a new orateur
    @PostMapping("/orateurs/add")
    public Orateur addOrateur(@RequestBody Orateur orateur) {
        return orateurService.addOrateur(orateur);
    }

    // Delete orateur
    @DeleteMapping("/orateurs/delete/{id}")
    public void deleteOrateur(@PathVariable("id") Long id) {
        orateurService.deleteOrateur(id);
    }

    // Fetch orateur
    @GetMapping("/orateurs/{id}")
    public Orateur getOrateur(@PathVariable("id") Long id) {
        return orateurService.getOrateur(id);
    }

    // Update orateur
    @PutMapping("/orateurs/update")
    public void updateOrateur(@RequestBody Orateur orateur) {
        orateurService.updateOrateur(orateur);
    }

}
