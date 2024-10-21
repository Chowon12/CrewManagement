package com.company.controller;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.company.dto.AttachmentFile;
import com.company.dto.Dept;
import com.company.service.AttachmentFileService;
import com.company.service.DeptService;

@Controller
public class DeptController {
	
	private static final Logger logger = LoggerFactory.getLogger(DeptController.class);
	
	@Autowired
	private DeptService service;
	@Autowired
	private AttachmentFileService fileService;
	
	// /dept/10
	@RequestMapping(value = "/dept/{deptno}", method = RequestMethod.GET)
	public String getDeptByDeptno(@PathVariable int deptno, Model model) throws Exception {
		Dept dept = null;
		AttachmentFile file = null;
		dept = service.getDeptByDeptno(deptno);
		file = fileService.getAttachmentFileByDeptno(deptno);
		System.out.println(file);
		model.addAttribute("dept", dept);
		model.addAttribute("file", file);
		return "deptDetail";
	}
	
	// /dept
	@RequestMapping(value = "/dept", method = RequestMethod.GET)
	public String insertDeptForm() {
		return "registerDept";
	}
	
	// /dept
	//post로 받아오면 body를 읽어오고 해당 데이터로 객체를 만들어서 데이터에 넣어줌. 
	//v1 without File
//	@RequestMapping(value = "/dept", method = RequestMethod.POST)
//	public String insertDept(@ModelAttribute Dept newDept){
//		String view = "error";
//		boolean result = false;
//		try {
//			result = service.insertDept(newDept);
//			if(result) {
//				//redirect:를 넣어주면 jsp가 아니라 main으로 갈 수 있게 해줌. 
//				//spring은 기본적으로 forward방식이라 redirect는 지금처럼 따로 설정해줘야함.
//				view = "redirect:/main";
//				return view;
//			}
//		} catch (Exception e) {
//			//e.printStackTrace();
//			// web.xml에서 error코드에 따라 어떤 페이지로 넘어갈 지 설정해줄 수 있음. 
//			return view;
//		}
//		return view;
//	}
	
	@RequestMapping(value = "/dept", method = RequestMethod.POST)
	public String insertDept(@ModelAttribute Dept newDept, MultipartFile file){
		
		// file != null -> insert dept, file
		// file == null -> insert dept
		String view = "error";
		
		boolean deptResult = false;
		boolean fileResult = false;
		try {
			deptResult = service.insertDept(newDept);

			if(file != null) {
				fileResult = fileService.insertAttachmentFile(file, newDept.getDeptno());
			}
			
			if(deptResult || fileResult) {
				view = "redirect:/main";
				return view;
			}
			
		} catch (Exception e) {
			return view;
		}
		return view;
	}
	
	
	// /modify/dept/10
	@RequestMapping(value = "/modify/dept/{deptno}", method = RequestMethod.GET)
	public String updateDeptForm(@PathVariable int deptno, Model model) throws Exception {
		Dept dept = service.getDeptByDeptno(deptno);
		AttachmentFile file = fileService.getAttachmentFileByDeptno(deptno);
		model.addAttribute("dept", dept);
		model.addAttribute("file", file);
		return "updateDept";
	}
	
//	@RequestMapping(value="/dept/{deptno}", method= RequestMethod.POST)
//	public String updateDeptDnameAndLoc(@PathVariable int deptno, 
//										@ModelAttribute("dname") String dname,
//										@ModelAttribute("loc") String loc) {
//		//수정할 내용들을 modelAttribute로 받아옴.
//		//modelAttribute로 2개 이상의 데이터를 받아올 때는 뒤에 데이터의 별칭을 명시해줘야 함. 
//		//그러므로 객체 내의 여러 데이터를 가져오려고 한다면 그냥 객체 자체를 받아오는게 편함. @ModelAttribute Dept dept
//		String view = "error";
//		Dept dept = null;
//		boolean result = false;
//		
//		try {
//			dept = service.getDeptByDeptno(deptno);
//			dept.setDname(dname);
//			dept.setLoc(loc);
//			
//			result = service.updateDnameAndLoc(dept);
//			
//			if(result) {
//				view = "redirect:/dept/" + deptno;
//				return view;
//			}
//		} catch (Exception e) {
////			e.printStackTrace();
//			
//		}
//		
//		return view;
//	}
	
	//파일 수정이 multipart타입으로 인해 post로만 실행되니 post에서 multipartFile을 받든가 비동기로 처리하든가 하면됨.
	@RequestMapping(value = "/dept/{deptno}", method = RequestMethod.POST)
	public String updateDeptDnameAndLoc(@PathVariable int deptno,
										@ModelAttribute Dept newDept) {
		String view = "error";
		
		// 정상 수정 -> /dept/10 GET
		System.out.println(deptno);
		System.out.println(newDept.getDeptno());
		System.out.println(newDept.getDname());
		System.out.println(newDept.getLoc());
		
		
		Dept dept = null;
		boolean result = false;
		try {
			dept = service.getDeptByDeptno(deptno);
			
			dept.setDname(newDept.getDname());
			dept.setLoc(newDept.getLoc());
			
			result = service.updateDnameAndLoc(dept);
			
			if(result) {
				view = "redirect:/dept/" + deptno;
				return view;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return view;
		}
		return view;
	}
	
	// /dept/10
	//url이 겹치면 method를 바꿔줘야 함. url을 바꿔주면 보안상 취약해질 수 있음. 
	@RequestMapping(value = "/dept/u/{deptno}", method = RequestMethod.POST)
	public String updateDept(@PathVariable int deptno,
							@ModelAttribute Dept newDept,
							MultipartFile file) throws Exception {
		// dname, loc �솗�씤 O
		// deptno濡� 湲곗〈 dept 媛앹껜 �솗�씤 -> �쐞�뿉�꽌 �솗�씤�븳 dname, loc �빐�떦 媛앹껜 setter
		// �젣��濡� update媛� �릺�뿀�떎怨� �븳�떎硫� -> dept/{deptno} detail �럹�씠吏�濡� �씠�룞
		String view = "error";
		
		// 정상 수정 -> /dept/10 GET
		System.out.println(deptno);
		System.out.println(newDept.getDeptno());
		System.out.println(newDept.getDname());
		System.out.println(newDept.getLoc());
		
		
		Dept dept = null;
		boolean result = false;
		try {
		dept = service.getDeptByDeptno(deptno);
		
		dept.setDname(newDept.getDname());
		dept.setLoc(newDept.getLoc());
		
		result = service.updateDnameAndLoc(dept);
		if(file != null) {
			fileService.insertAttachmentFile(file, dept.getDeptno());
		}
		if(result) {
		view = "redirect:/dept/" + deptno;
		return view;
		}
		} catch (Exception e) {
		e.printStackTrace();
		return view;
		}
		return view;
	}
	
	// /dept/10
	@RequestMapping(value = "/dept/{deptno}", method = RequestMethod.DELETE)
	public String deleteDept(@PathVariable int deptno) {
		String view = "error";
		try {
			boolean result = service.deleteDeptByDeptno(deptno);
			if(result) {
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
	
	@GetMapping(value="/download/file/{fileNo}")
	public ResponseEntity<Resource> downloadFile(@PathVariable int fileNo){
		AttachmentFile attFile = null;
		Resource resource = null;
		
		try {
			attFile = fileService.getAttachmentFileByFileNo(fileNo);
			Path path = Paths.get(attFile.getFilePath() + "\\" +attFile.getAttachmentFileName());
			resource = new InputStreamResource(Files.newInputStream(path));
		} catch (Exception e) {
			e.printStackTrace();
		}
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers.setContentDisposition(ContentDisposition
											.builder("attachment")
											.filename(attFile.getAttachmentOriginalFileName())
											.build());
		
		return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
	}
}