package com.ssafy.happyhouse.model.service;

import com.ssafy.happyhouse.model.AnswerDto;
import com.ssafy.happyhouse.model.QuestionDto;

import java.util.List;

public interface QNAService {
	List<QuestionDto> questionList();

	boolean createQuestion(QuestionDto questionDto);

	boolean deleteQuestion(int questionNo);
	
	boolean modifyQuestion(QuestionDto questionDto);
	
	boolean createAnswer(AnswerDto answerDto);
	
	List<AnswerDto> answerList(int questionNo);

	QuestionDto getQuestion(int questionNo); 

}
