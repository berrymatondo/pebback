package com.peb.pebb.resume;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ResumeRepository extends JpaRepository<Resume, Long> {

    List<Resume> findByCategory(String cat);

}
