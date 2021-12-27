package com.ssafy.happyhouse.model;

public class AnswerDto {
	private int ano;
	private String author;
	private String content;
	private String regtime;
	private int qno;
	
	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getRegtime() {
		return regtime;
	}

	public void setRegtime(String regtime) {
		this.regtime = regtime;
	}

	public int getQno() {
		return qno;
	}

	public void setQno(int qno) {
		this.qno = qno;
	}

	@Override
	public String toString() {
		return "AnswerDto [ano=" + ano + ", author=" + author + ", content=" + content + ", regtime=" + regtime
				+ ", qno=" + qno + "]";
	}
}
