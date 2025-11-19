package coursework2.examinerservice.controller;

import coursework2.examinerservice.domain.Question;
import coursework2.examinerservice.service.ExaminerService;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/exam")
public class ExaminerController {

    private final ExaminerService examinerService;

    public ExaminerController(ExaminerService examinerServiceImpl) {
        this.examinerService = examinerServiceImpl;
    }

    @GetMapping("/get/{amount}")
    public Collection<Question> getQuestions(@PathVariable int amount) {
        return examinerService.getQuestions(amount);
    }

}