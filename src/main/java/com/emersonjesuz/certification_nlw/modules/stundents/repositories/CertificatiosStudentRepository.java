package com.emersonjesuz.certification_nlw.modules.stundents.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.emersonjesuz.certification_nlw.modules.stundents.entities.CertificatiosStudentEntity;

@Repository
public interface CertificatiosStudentRepository extends JpaRepository<CertificatiosStudentEntity, UUID> {

    @Query("SELECT c FROM certifications c INNER JOIN c.studentEntity std WHERE std.email = :email AND c.technology = :technology")
    List<CertificatiosStudentEntity> findByStudentEmailAndTechnology(String email, String technology);

    @Query("SELECT c FROM certifications c ORDER BY c.grade DESC LIMIT 10")
    List<CertificatiosStudentEntity> findTop10ByOrderByGradeDesc();
}
