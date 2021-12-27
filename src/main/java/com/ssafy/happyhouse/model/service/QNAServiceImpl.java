package com.ssafy.happyhouse.model.service;

import com.ssafy.happyhouse.model.AnswerDto;
import com.ssafy.happyhouse.model.QuestionDto;
import com.ssafy.happyhouse.model.mapper.QNAMapper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QNAServiceImpl implements QNAService {

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public List<QuestionDto> questionList() {
		// TODO Auto-generated method stub
		return sqlSession.getMapper(QNAMapper.class).questionList();
	}

	@Override
	public boolean createQuestion(QuestionDto questionDto) {
		// TODO Auto-generated method stub
		return sqlSession.getMapper(QNAMapper.class).createQuestion(questionDto) == 1;
	}

	@Override
	public boolean deleteQuestion(int questionNo) {
		// TODO Auto-generated method stub
		return sqlSession.getMapper(QNAMapper.class).deleteQuestion(questionNo) == 1;
	}

	@Override
	public boolean createAnswer(AnswerDto answerDto) {
		// TODO Auto-generated method stub
		return sqlSession.getMapper(QNAMapper.class).createAnswer(answerDto) == 1;
	}

	@Override
	public List<AnswerDto> answerList(int questionNo) {
		// TODO Auto-generated method stub
		return sqlSession.getMapper(QNAMapper.class).answerList(questionNo);
	}

	@Override
	public boolean modifyQuestion(QuestionDto questionDto) {
		// TODO Auto-generated method stub
		return sqlSession.getMapper(QNAMapper.class).modifyQuestion(questionDto) == 1;
	}

	@Override
	public QuestionDto getQuestion(int questionNo) {
		// TODO Auto-generated method stub
		return sqlSession.getMapper(QNAMapper.class).getQuestion(questionNo);
	}

}
