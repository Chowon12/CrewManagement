package com.company.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.service.AttachmentFileService;

@Controller
public class AttachmentFileController {
	@Autowired
	AttachmentFileService fileService;
	@ResponseBody
	@DeleteMapping(value="/file/{fileno}")
	public String deleteFileByFileno(@PathVariable long fileno) {
		boolean result = false;
		System.out.println(fileno);
		try {
			result = fileService.deleteFileByFileno(fileno);
			if(result) {
				return "성공";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "실패";
	}
	
}
