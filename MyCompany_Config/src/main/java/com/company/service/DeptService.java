package com.company.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.dto.Dept;
import com.company.mapper.DeptMapper;

@Service
public class DeptService {
	
	@Autowired
	DeptMapper mapper;
	
	// List
	public List<Dept> getAllDeptList() {
		//List<Dept> deptList = null; 원래는 널 확인을 하고 예외 발생시켜서 예외처리 해야 함. 
		return mapper.getAllDeptList();
	}

	// 媛앹껜
	public Dept getDeptByDeptno(int deptno) throws SQLException { 
		Dept dept = mapper.getDeptByDeptno(deptno);
		if(dept == null) {
			throw new NullPointerException("해당 부서는 존재하지 않습니다.");
		}
		return dept; 
	}
	
	// insert - Dept
	//mapper에서 DML은 무조건 INT값으로 받아옴.
	public boolean insertDept(Dept dept) throws Exception {
		boolean result = false;
		if(mapper.insertDept(dept) != 0) {
			result = true;
		} else {
			new Exception("부서 생성 실패");
		}
		return result;
	}
	
	// update-dept
	public boolean updateDnameAndLoc(Dept dept) throws SQLException, Exception {
		boolean result = false;
		int res = mapper.updateDnameAndLoc(dept);
		if(res != 0) {
			result = true;
		} else {
			new Exception("부서 수정 실패");
		}
		return result;
	}
	
	// delete - dept
	public boolean deleteDeptByDeptno(int deptno) throws SQLException, Exception {
		boolean result = false;
		if(mapper.deleteDeptByDeptno(deptno) != 0) {
			result = true;
		}
		return result;
	}

	
}