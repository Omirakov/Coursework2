package coursework2.examinerservice.controller;

import coursework2.examinerservice.domain.Question;
import coursework2.examinerservice.service.QuestionService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.MethodNotAllowedException;

import java.util.Collection;

@RestController
@RequestMapping("/exam/math")
public class MathQuestionController {

    private final QuestionService questionService;

    public MathQuestionController(@Qualifier("mathQuestionService") QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping("/add")
    public Question addQuestion(@RequestParam String question, @RequestParam String answer) {
        throw new MethodNotAllowedException("POST", null);
    }

    @DeleteMapping("/remove")
    public Question removeQuestion(@RequestParam String question, @RequestParam String answer) {
        throw new MethodNotAllowedException("DELETE", null);
    }

    @GetMapping("/find")
    public Collection<Question> getAllQuestions() {
        throw new MethodNotAllowedException("GET", null);
    }

    @GetMapping("/random")
    public Question getRandomQuestion() {
        return questionService.getRandomQuestion();
    }
}