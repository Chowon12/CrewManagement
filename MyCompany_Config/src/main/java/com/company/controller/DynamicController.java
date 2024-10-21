package com.company.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.dto.Dept;
import com.company.mapper.DeptMapper;

@RestController
//@responseBody + controller 둘을 합친 것임. 메소드들의 return 값이 json이 됨. 비동기 요청 사용할 때 주로 사용함.
// 그래서 url에도 api를 적어주고 컨트롤러의 이름에도 api를 적어주는게 관례임.
public class DynamicController {
	@Autowired
	private DeptMapper deptmapper;
	@GetMapping("/api/test")
	public String test() {
		return "test";
	}
	
	@GetMapping("/api/dynamic-sql")
	public Object getAPIDynamicSQL() throws SQLException {
		Object result = null;
		
		//getDynamicDeptno 찾는게 없으면 전체 List 있으면 특정 객체만 return
		Map<String, Integer> map1 = new HashMap<String, Integer>();
		map1.put("deptno", 99);
		List<Dept> result1 = deptmapper.getDynamicDeptno(map1);
		result = result1;
		
		return result;
	}
	
	// getDynamicChoose
	// SELECT * FROM dept WHERE dname = "SALES" OR loc = "DALLAS" 
	// Choose : 첫번째 혹은 두번째중에서 첫번째로 만족하는 결과만 가져오는 동적 SQL
	
	@GetMapping("/api/choose")
	public Dept getDynamicChoose() throws SQLException {
		Dept dept = null;
		Map<String, String> map2 = new HashMap<String, String>();
		map2.put("dname", "SALES");
		map2.put("loc", "DALLAS");
		dept = deptmapper.getDynamicChoose(map2);
		
		return dept;
	}
	
	@GetMapping("/api/trim")
	public List<Dept> getDynamicWhereTrim(){
		// getDynamicWhereTrim
		Map<String, String> map3 = new HashMap<String, String>();
		map3.put("dname", "SALES");
		map3.put("loc", "DALLAS");		
		List<Dept> result3 = deptmapper.getDynamicWhereTrim(map3);
		return result3;
	}
	
	// set
			// UPDATE dept SET loc = "LA" WHERE deptno = 40
	    // updateDynamicSet
	@GetMapping("/api/updateDynamic")
	public int updateDynamicSet() {
		Map<String, Object> map4 = new HashMap<String, Object>();
		map4.put("deptno", 40);
		map4.put("dname", "DEV");
		map4.put("loc", "LA");		
		int result = deptmapper.updateDynamicSet(map4);
		return result;
	}
	
	// foreach : SELECT, UPDATE, DELETE
			// SELECT * FROM dept WHERE deptno IN (10, 20, 30)
	@GetMapping("/api/forEach")
	public List<Dept> getDynamicForeachDeptno() {
		
		List<Integer> list5 = Arrays.asList(10, 20, 30); 
		List<Dept>result = deptmapper.getDynamicForeachDeptno(list5);
		return result;
	}
	
	// foreach : INSERT
//			 INSERT INTO dept (deptno, dname, loc) VALUES (deptno, dname, loc),
//			 											(deptno, dname, loc)
			// insertDynamicForeachDeptList
	@GetMapping("/api/insertForEach")
	public int insertDynamicForeachDeptList() {
		Dept dept1 = new Dept(15, "15", "15");
		Dept dept2 = new Dept(16, "16", "16");
		Dept dept3 = new Dept(17, "17", "17");
		List<Dept> insertDeptList = Arrays.asList(dept1, dept2, dept3);
		int result = deptmapper.insertDynamicForeachDeptList(insertDeptList);
		return result;
	}
}
