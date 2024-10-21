package com.company.controller;

import java.sql.SQLException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.company.dto.Emp;
import com.company.service.EmpService;

@Controller
public class LoginController {
	
	@Autowired
	EmpService empService;
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginForm() {
		return "login";
	}
	
//	@RequestMapping(value = "/login", method = RequestMethod.POST)
//	public String login(@ModelAttribute Emp emp, HttpSession session) {
//		String view = "error";
//		try {
//			if(empService.getEmpByEmpnoAndEname(emp.getEmpno(), emp.getEname()) != null) {
//				session.setAttribute("empno", emp.getEmpno());
//				session.setAttribute("ename", emp.getEname());
//				session.setMaxInactiveInterval(60*60);
//				view = "main";
//			}
//			
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return view;
//	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(int empno, String ename, HttpSession session) {
		String view = "error";
		
		//세션이 이미 존재한다면 로그인이 되지 않는 곳으로 이동.
		//로그인을 하게되면 원하는 페이지도 이동.
		try {
			Emp emp = empService.getEmpByEmpnoAndEname(empno, ename);
			System.out.println(emp);
			if(emp != null) {
				session.setAttribute("userId", empno);
				session.setAttribute("userName", ename);
				session.setMaxInactiveInterval(60*60);
				view = "redirect:/main";
				return view;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return view;
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession session) {
		if(session != null) {
//			session.setAttribute("userId", null);
//			session.setMaxInactiveInterval(0);
			session.invalidate();
		}
		return "redirect:/login";
	}
	
}