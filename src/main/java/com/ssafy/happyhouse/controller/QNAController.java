package com.ssafy.happyhouse.controller;

import com.ssafy.happyhouse.model.AnswerDto;
import com.ssafy.happyhouse.model.QuestionDto;
import com.ssafy.happyhouse.model.service.QNAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/qna")
public class QNAController {
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	private QNAService qnaService;

	@GetMapping("/questions")
	public ResponseEntity<List<QuestionDto>> questionList() {
		return new ResponseEntity<List<QuestionDto>>(qnaService.questionList(), HttpStatus.OK);
	}
	
	@GetMapping("/questions/{qno}")
	public ResponseEntity<QuestionDto> getQuestion(@PathVariable("qno") int questionNo) {
		return new ResponseEntity<QuestionDto>(qnaService.getQuestion(questionNo), HttpStatus.OK);
	}
	
	@PostMapping("/questions")
	public ResponseEntity<String> createQuestion(@RequestBody QuestionDto questionDto) {
		if (qnaService.createQuestion(questionDto)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

	@DeleteMapping("/questions/{qno}")
	public ResponseEntity<String> deleteQuestion(@PathVariable("qno") int questionNo) {
		if (qnaService.deleteQuestion(questionNo)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

	@PutMapping("/questions")
	public ResponseEntity<String> modifyQuestion(@RequestBody QuestionDto questionDto) {
		if(qnaService.modifyQuestion(questionDto)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
	@PostMapping("/answers/{qno}")
	public ResponseEntity<String> createAnswer(@RequestBody AnswerDto answerDto, @PathVariable("qno") int questionNo) {
		System.out.println(answerDto);
//		map.forEach((v,e) -> System.out.println(v + "," + e));
//		map.put("qno", Integer.toString(questionNo));
		if (qnaService.createAnswer(answerDto)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

	@GetMapping("/answers/{qno}")
	public ResponseEntity<List<AnswerDto>> answerList(@PathVariable("qno") int questionNo) {
		return new ResponseEntity<List<AnswerDto>>(qnaService.answerList(questionNo), HttpStatus.OK);
	}
}
