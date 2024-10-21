package com.company.common.exception;

import java.sql.SQLException;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@ControllerAdvice
//@RestControllerAdvice 비동기로 처리할거면 이걸 써도 됨.
public class AttachmentFileException {
	@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "페이지 누락")
	@ExceptionHandler({NullPointerException.class, SQLException.class})
	public String bizExceptionHandler(Exception e, Model model) {
		String view = "error";
		model.addAttribute("errorMsg", e.getMessage());
		return view;
	}
	@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "메소드 오류")
	@ExceptionHandler({Exception.class, NoSuchElementException.class})
	public String bizExceptionHandler2(Exception e, Model model) {
		String view = "error";
		model.addAttribute("errorMsg", e.getMessage());
		return view;
	}
}
