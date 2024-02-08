package com.emersonjesuz.certification_nlw.modules.stundents.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emersonjesuz.certification_nlw.modules.stundents.entities.StudentEntity;

public interface StudentRepository extends JpaRepository<StudentEntity, UUID> {

    public Optional<StudentEntity> findByEmail(String email);
}
