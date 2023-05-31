package com.ex;

import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
//POJO(Plain Old Java Object객체)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostsVO {
	
	@Id
	private ObjectId id;
	
	@BsonProperty(value="author")
	private String author;
	@BsonProperty(value="kind")
	private String kind;
	@BsonProperty(value="title")
	private String title;
}
