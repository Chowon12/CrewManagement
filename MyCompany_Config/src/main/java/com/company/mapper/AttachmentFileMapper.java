package com.company.mapper;

import java.sql.SQLException;

import org.apache.ibatis.annotations.Mapper;

import com.company.dto.AttachmentFile;

@Mapper
public interface AttachmentFileMapper {
	// ���ܴ� mapper���� ������� ��.
	int insertAttachmentFile(AttachmentFile attFile) throws SQLException;

	AttachmentFile getAttachmentFileByDeptno(int deptno) throws SQLException;

	AttachmentFile getAttachmentFileByFileNo(long fileNo) throws SQLException;

	int deleteFileByFileno(long fileno) throws SQLException;
	
}
