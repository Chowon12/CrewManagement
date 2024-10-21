package com.company.dto;


import java.sql.Timestamp;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class AttachmentFile {
	private long attachmentFileNo; 				// ���� ��ȣ
	private String filePath;					// ���� ���� ���(base��� + ���ο� ���� ���)
	private String attachmentFileName;			// ���ϸ�(UUID + �������ϸ�)
	private String attachmentOriginalFileName;	// ���� ���ϸ�
	private Timestamp registeredDate;			// ���� ��� ����
	private Long attachmentFileSize; 			// ���� ũ��
	private int deptno; 						// �μ� ��ȣ
	
	@Builder
	public AttachmentFile(long attachmentFileNo, String filePath, String attachmentFileName,
			String attachmentOriginalFileName, Timestamp registeredDate, Long attachmentFileSize, int deptno) {
		this.attachmentFileNo = attachmentFileNo;
		this.filePath = filePath;
		this.attachmentFileName = attachmentFileName;
		this.attachmentOriginalFileName = attachmentOriginalFileName;
		this.registeredDate = registeredDate;
		this.attachmentFileSize = attachmentFileSize;
		this.deptno = deptno;
	}
}