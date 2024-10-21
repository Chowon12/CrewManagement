package com.company.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.company.dto.Dept;

@Mapper
public interface DeptMapper {
	
	// List
	public List<Dept> getAllDeptList();

	// 객체 - Dept
	public Dept getDeptByDeptno(int deptno);
	
	// insert - Dept
	public int insertDept(Dept dept) throws Exception;
	
	// update - Dept
	public int updateDnameAndLoc(Dept dept) throws SQLException; 

	// delete - dept
	public int deleteDeptByDeptno(int deptno) throws SQLException;
	
	// dynamic
	public List<Dept> getDynamicDeptno(Map<String, Integer> map1) throws SQLException;

	public Dept getDynamicChoose(Map<String, String> map2) throws SQLException;

	public List<Dept> getDynamicWhereTrim(Map<String, String> map3);

	public int updateDynamicSet(Map<String, Object> map4);

	public List<Dept> getDynamicForeachDeptno(List<Integer> list5);

	public int insertDynamicForeachDeptList(List<Dept> insertDeptList);

}
