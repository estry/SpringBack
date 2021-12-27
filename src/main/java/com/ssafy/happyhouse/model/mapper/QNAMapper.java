package com.ssafy.happyhouse.model.mapper;

import com.ssafy.happyhouse.model.AnswerDto;
import com.ssafy.happyhouse.model.QuestionDto;

import java.util.List;

public interface QNAMapper {
	List<QuestionDto> questionList();
	
	int createQuestion(QuestionDto questionDto);
	
	int deleteQuestion(int questionNo);
	
	int modifyQuestion(QuestionDto questionDto);
	
	int createAnswer(AnswerDto answerDto);
	
	List<AnswerDto> answerList(int questionNo);

	QuestionDto getQuestion(int questionNo); 
}
