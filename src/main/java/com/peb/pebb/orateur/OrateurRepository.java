package com.peb.pebb.orateur;

import org.springframework.stereotype.Repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface OrateurRepository extends JpaRepository<Orateur, Long> {

}
