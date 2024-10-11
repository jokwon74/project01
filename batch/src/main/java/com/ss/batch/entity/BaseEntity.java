package com.ss.batch.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;

@MappedSuperclass
@Data
//엔터티가 생성되거나 수정될때 자동으로 생성일,수정일을 기록할수잇게해준다.
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

	
	@CreatedDate
	@Column(nullable = false,updatable = false,name="create_at")
	private LocalDateTime createAt;
	
	@LastModifiedDate
	@Column(nullable = false,name="modified_at")
	private LocalDateTime modifiedAt;

}
