package com.company.dto;

import java.sql.Date;
import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
//@AllArgsConstructor
@ToString
public class Emp {
	private Integer empno;
	private String ename;
	private String job;
	private String mgr;
	private Date hiredate;
	private Float sal;
	private Float comm;
	private Integer deptno;
	
	@Builder
	public Emp(Integer empno, String ename, String job, String mgr, Date hiredate, Float sal, Float comm,
			Integer deptno) {
		super();
		this.empno = empno;
		this.ename = ename;
		this.job = job;
		this.mgr = mgr;
		this.hiredate = hiredate;
		this.sal = sal;
		this.comm = comm;
		this.deptno = deptno;
	}
	
}
