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
	private long attachmentFileNo; 				// 파일 번호
	private String filePath;					// 파일 저장 경로(base경로 + 새로운 폴더 경로)
	private String attachmentFileName;			// 파일명(UUID + 원본파일명)
	private String attachmentOriginalFileName;	// 원본 파일명
	private Timestamp registeredDate;			// 파일 등록 일자
	private Long attachmentFileSize; 			// 파일 크기
	private int deptno; 						// 부서 번호
	
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