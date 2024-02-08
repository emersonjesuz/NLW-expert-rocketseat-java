package com.emersonjesuz.certification_nlw.modules.certifications.useCases;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emersonjesuz.certification_nlw.modules.stundents.entities.CertificatiosStudentEntity;
import com.emersonjesuz.certification_nlw.modules.stundents.repositories.CertificatiosStudentRepository;

@Service
public class Top10RankingUseCase {

    @Autowired
    private CertificatiosStudentRepository certificatiosStudentRepository;

    public List<CertificatiosStudentEntity> execute() {
        var result = this.certificatiosStudentRepository.findTop10ByOrderByGradeDesc();
        return result;
    }

}
