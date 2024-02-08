package com.emersonjesuz.certification_nlw.modules.stundents.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emersonjesuz.certification_nlw.modules.stundents.dto.VerifyHasCertificationDTO;
import com.emersonjesuz.certification_nlw.modules.stundents.repositories.CertificatiosStudentRepository;

@Service
public class VerifyIfHasCertificationUseCase {

    @Autowired
    private CertificatiosStudentRepository certificatiosStudentRepository;

    public boolean execute(VerifyHasCertificationDTO dto) {
        var result = this.certificatiosStudentRepository.findByStudentEmailAndTechnology(dto.getEmail(),
                dto.getTechnology());
        if (!result.isEmpty()) {
            return true;
        }

        return false;
    }
}
