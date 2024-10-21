package com.company.service;

import java.io.File;
import java.sql.SQLException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.company.dto.AttachmentFile;
import com.company.mapper.AttachmentFileMapper;

@Service
public class AttachmentFileService {
	
	@Autowired
	AttachmentFileMapper attachmentFileMapper;
	
	// ��ü
	public AttachmentFile getAttachmentFileByFileNo(long fileNo) throws SQLException, Exception { 
		AttachmentFile attFile = null;
		attFile = attachmentFileMapper.getAttachmentFileByFileNo(fileNo);
//		if(attFile == null) {
//			throw new NullPointerException("������ �������� �ʽ��ϴ�.");
//		}
		return attFile;
	}
	
	// insert - 
	public boolean insertAttachmentFile(MultipartFile file, int deptno) throws SQLException, Exception {
		boolean result = false;
		
//		if(file == null) {
//			throw new NullPointerException("���� ���� ���� �߻�");
//		}
		
		/* ������ �����ϴ� �ǹ�
		   1. DB ���� ���� ���� - attFile ��ü ���� -> mapper -> db ����
		   2. server�� ������ ������ ����  - multipartFile transferTo()
		   3. ���� ������ ���� - true / �ƴϸ� - false
		 */
		
		String filePath = "C:\\oracle_example\\MULTI\\00.spring";
		String attachmentOriginalFileName = file.getOriginalFilename();
		UUID uuid = UUID.randomUUID();
		String attachmentFileName = uuid.toString() + "_" + attachmentOriginalFileName;
		Long attachmentFileSize = file.getSize();
		
		AttachmentFile attachmentFile = AttachmentFile.builder()
													.attachmentOriginalFileName(attachmentOriginalFileName)
													.attachmentFileName(attachmentFileName)
													.attachmentFileSize(attachmentFileSize)
													.filePath(filePath)
													.deptno(deptno)
													.build();
													
		int res = attachmentFileMapper.insertAttachmentFile(attachmentFile);
		
		if(res != 0) {
			file.transferTo(new File(filePath + "\\" + attachmentFileName));
			result = true;
		}
		
		return result;
	}
	
	// �μ���ȣ�� ���� ���
	public AttachmentFile getAttachmentFileByDeptno(int deptno) throws SQLException, Exception {
		AttachmentFile attachmentFile = null;
		
		attachmentFile = attachmentFileMapper.getAttachmentFileByDeptno(deptno);
//		if(attachmentFile == null) {
//			throw new NullPointerException("�ش� ����� �������� �ʽ��ϴ�.");
//		}
		return attachmentFile;
	}
	
	//�� �� �̻��� ���� ������ ���� �� �� �� �ϳ��� ������ �ȵȴٸ� �ѹ�. ���� �޼ҵ忡�� ���� ���.
	@Transactional
	public boolean deleteFileByFileno(long fileno) throws SQLException, Exception {
		boolean result = false;
		boolean serverDeleteResult = false;
		//���� ���� ���� ����
		AttachmentFile file = getAttachmentFileByFileNo(fileno);
//		if(file == null) {
//			throw new NullPointerException("������� ������ �������� �ʽ��ϴ�");
//		}
		File serverFile = new File(file.getFilePath() + "\\" +file.getAttachmentFileName());
		serverDeleteResult = serverFile.delete();
		//db����
		int res = attachmentFileMapper.deleteFileByFileno(fileno);
		if(serverDeleteResult && res != 0) {
			result = true;
		}
		return result;
	}
}
