package com.company.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.company.dto.Dept;
import com.company.service.DeptService;

@Controller
public class MainController {
	
	private static final Logger logger = LoggerFactory.getLogger(MainController.class);
	
	@Autowired
	private DeptService service;
	
	@Value("${test}")
	private String test;
	//원래는 getProperties로 가져왔는데 Spring에서는 @value를 사용하면 됨.
	// el 태그 내부에 properties파일 내부에 있는 키값을 적어주면 이용가능.
	
	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public String main(Model model) {
		//service ȣ��, mapper�� ����, getAllDeptList�� ������, xml���Ͽ��� ������ �޾ƿ���.
		//Ŭ�� ���� �� url�� localhost:8080/dept/#{deptno}
		System.out.println(test);
		List<Dept> deptList = service.getAllDeptList();
		model.addAttribute("deptList", deptList);
		
		return "main";
	}
	
}
