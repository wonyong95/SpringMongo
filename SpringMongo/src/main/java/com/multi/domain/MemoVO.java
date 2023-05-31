package com.multi.domain;

import org.bson.codecs.pojo.annotations.BsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection="memo")
@Data
public class MemoVO {
	
	@Id
	private String id;
	
	@BsonProperty
	private int no;
	@BsonProperty
	private String name;
	@BsonProperty
	private String msg;
	@BsonProperty
	private String wdate;
}
