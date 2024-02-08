package com.emersonjesuz.certification_nlw.modules.stundents.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emersonjesuz.certification_nlw.modules.stundents.dto.StudentCertificationAnswerDTO;
import com.emersonjesuz.certification_nlw.modules.stundents.dto.VerifyHasCertificationDTO;
import com.emersonjesuz.certification_nlw.modules.stundents.entities.CertificatiosStudentEntity;
import com.emersonjesuz.certification_nlw.modules.stundents.useCases.StudentCertificationAnswersUseCase;
import com.emersonjesuz.certification_nlw.modules.stundents.useCases.VerifyIfHasCertificationUseCase;

@RestController
@RequestMapping("/students")
public class StundentController {

    @Autowired
    private VerifyIfHasCertificationUseCase verifyIfHasCertificationUseCase;

    @Autowired
    private StudentCertificationAnswersUseCase certificationAnswersUseCase;

    @PostMapping("/verifyIfHasCertification")
    public String verifyIfHasCertification(@RequestBody VerifyHasCertificationDTO verifyHasCertificationDTO) {

        var result = this.verifyIfHasCertificationUseCase.execute(verifyHasCertificationDTO);

        if (result) {
            return "Usuário já fez a prova";
        }

        return "Usuario pode fazer a prova";
    }

    @PostMapping("/certification/answer")
    public ResponseEntity<Object> certificationAnswer(
            @RequestBody StudentCertificationAnswerDTO studentCertificationAnswerDTO) throws Exception {

        try {
            var result = certificationAnswersUseCase.execute(studentCertificationAnswerDTO);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}