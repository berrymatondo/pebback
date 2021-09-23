package com.peb.pebb.resume;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface ResumeRepository extends JpaRepository<Resume, Long> {

    List<Resume> findByCategory(String cat);

    @Modifying
    @Query(value = "delete from appusers_resumes where appusers_id=?1 and resumes_resume_id=?2", nativeQuery = true)
    void deleteTag(Long appuserId, Long resumeId);

    @Query(value = "select p.appusers_id as appusersId, p.resumes_resume_id as resumesResumeId from appusers_resumes p where appusers_id=?1 and resumes_resume_id=?2", nativeQuery = true)
    TagResume getTag(Long appuserId, Long resumeId);

    @Query(value = "select case when count(1) > 0 then true else false end from appusers_resumes where appusers_id=?1 and resumes_resume_id=?2 ", nativeQuery = true)
    boolean tagExists(Long appuserId, Long resumeId);

    @Modifying
    @Query(value = "update resumes set publish=?2 where resume_id=?1", nativeQuery = true)
    void updatePublish(Long resumeId, boolean status);

}
