package com.emersonjesuz.certification_nlw.modules.questions.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emersonjesuz.certification_nlw.modules.questions.dto.AlternativesResultDTO;
import com.emersonjesuz.certification_nlw.modules.questions.dto.QuestionResultDTO;
import com.emersonjesuz.certification_nlw.modules.questions.entities.AlternativesEntity;
import com.emersonjesuz.certification_nlw.modules.questions.entities.QuestionEntity;
import com.emersonjesuz.certification_nlw.modules.questions.repositories.QuestionRepository;

@RestController
@RequestMapping("/questions")
public class QuestionControllers {

    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/technology/{technology}")
    public List<QuestionResultDTO> findByTechnology(@PathVariable String technology) {
        var result = this.questionRepository.findByTechnology(technology);

        return result.stream().map(question -> mapQuestionToDTO(question))
                .collect(Collectors.toList());

    }

    static QuestionResultDTO mapQuestionToDTO(QuestionEntity question) {
        var questionResultDTO = QuestionResultDTO.builder().id(question.getId()).technology(question.getTechnology())
                .description(question.getDescription()).build();

        List<AlternativesResultDTO> aLternativesResultDTOs = question.getAlternatives().stream()
                .map(alternative -> mapALternativesDTO((alternative))).collect(Collectors.toList());

        questionResultDTO.setAlternatives(aLternativesResultDTOs);
        return questionResultDTO;
    }

    static AlternativesResultDTO mapALternativesDTO(AlternativesEntity aLternativesResultDTO) {
        return AlternativesResultDTO.builder().id(aLternativesResultDTO.getId())
                .description(aLternativesResultDTO.getDescription()).build();
    }
}
