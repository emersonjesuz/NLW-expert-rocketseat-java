package com.emersonjesuz.certification_nlw.modules.stundents.useCases;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emersonjesuz.certification_nlw.modules.questions.entities.QuestionEntity;
import com.emersonjesuz.certification_nlw.modules.questions.repositories.QuestionRepository;
import com.emersonjesuz.certification_nlw.modules.stundents.dto.StudentCertificationAnswerDTO;
import com.emersonjesuz.certification_nlw.modules.stundents.dto.VerifyHasCertificationDTO;
import com.emersonjesuz.certification_nlw.modules.stundents.entities.AnswersCertificationsEntity;
import com.emersonjesuz.certification_nlw.modules.stundents.entities.CertificatiosStudentEntity;
import com.emersonjesuz.certification_nlw.modules.stundents.entities.StudentEntity;
import com.emersonjesuz.certification_nlw.modules.stundents.repositories.CertificatiosStudentRepository;
import com.emersonjesuz.certification_nlw.modules.stundents.repositories.StudentRepository;

@Service
public class StudentCertificationAnswersUseCase {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private CertificatiosStudentRepository certificatiosStudentRepository;

    @Autowired
    private VerifyIfHasCertificationUseCase verifyIfHasCertificationUseCase;

    public CertificatiosStudentEntity execute(StudentCertificationAnswerDTO dto) throws Exception {

        var hasCertification = this.verifyIfHasCertificationUseCase
                .execute(new VerifyHasCertificationDTO(dto.getEmail(), dto.getTechnology()));

        if (hasCertification) {
            throw new Exception("Você já tirou sua certificação");
        }

        List<QuestionEntity> questionEntity = questionRepository.findByTechnology(dto.getTechnology());
        List<AnswersCertificationsEntity> answerCertifications = new ArrayList<>();

        AtomicInteger correctAnswers = new AtomicInteger(0);

        dto.getQuestionAnswers().stream().forEach(questionsAnswer -> {
            var question = questionEntity.stream()
                    .filter(q -> q.getId().equals(questionsAnswer.getQuestionID())).findFirst().get();

            var findCorrectAlternative = question.getAlternatives().stream()
                    .filter((alternative -> alternative.isCorrect())).findFirst().get();

            if (findCorrectAlternative.getId().equals(questionsAnswer.getAlternativeID())) {
                questionsAnswer.setCorrect(true);
                correctAnswers.incrementAndGet();
            } else {
                questionsAnswer.setCorrect(false);
            }

            var answersCertificationsEntity = AnswersCertificationsEntity.builder()
                    .answerID(questionsAnswer.getAlternativeID())
                    .questionID(questionsAnswer.getQuestionID())
                    .isCorrect(questionsAnswer.isCorrect())
                    .build();

            answerCertifications.add(answersCertificationsEntity);
        });

        var student = studentRepository.findByEmail(dto.getEmail());
        UUID studentID;

        if (student.isEmpty()) {
            var studentCreated = StudentEntity.builder().email(dto.getEmail()).build();
            studentCreated = studentRepository.save(studentCreated);
            studentID = studentCreated.getId();
        } else {
            studentID = student.get().getId();
        }

        CertificatiosStudentEntity certificatiosStudentEntity = CertificatiosStudentEntity.builder()
                .technology(dto.getTechnology())
                .studentID(studentID)
                .grade(correctAnswers.get())
                .build();

        var certificationStudentCreated = certificatiosStudentRepository.save(certificatiosStudentEntity);

        answerCertifications.stream().forEach(answerCertification -> {
            answerCertification.setCertificationID(certificatiosStudentEntity.getId());
            answerCertification.setCertificatiosStudentEntity(certificatiosStudentEntity);
        });

        certificatiosStudentEntity.setAnswersCertificationsEntity(answerCertifications);

        certificatiosStudentRepository.save(certificatiosStudentEntity);

        return certificationStudentCreated;
    }
}
