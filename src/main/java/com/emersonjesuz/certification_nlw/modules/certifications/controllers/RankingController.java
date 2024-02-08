package com.emersonjesuz.certification_nlw.modules.certifications.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emersonjesuz.certification_nlw.modules.certifications.useCases.Top10RankingUseCase;
import com.emersonjesuz.certification_nlw.modules.stundents.entities.CertificatiosStudentEntity;

@RestController
@RequestMapping("/ranking")
public class RankingController {

    @Autowired
    private Top10RankingUseCase top10RankingUseCase;

    @GetMapping("/top10")
    public List<CertificatiosStudentEntity> top10() {
        return this.top10RankingUseCase.execute();
    }

}
