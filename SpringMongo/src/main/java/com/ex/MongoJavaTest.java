package com.ex;
//static import (static메서드, static변수 앞에 클래스명을 생략하고 사용할수있다)
import static com.mongodb.client.model.Filters.eq;
import static java.lang.System.out;
import static com.mongodb.client.model.Updates.*;
////////////
import java.util.Scanner;

//org.bson.Document
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
//////////////////
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;

public class MongoJavaTest {
	
	static Scanner sc=new Scanner(System.in);

	public static void main(String[] args) {
		String db="mydb";
		MongoClient mongoClient=null;
		MongoDatabase mongodb;
		MongoCollection<Document> collection=null;
		String collectionName="posts";//author, title,kind
		
		mongoClient=MongoClients.create("mongodb://localhost:27017");
		mongodb=mongoClient.getDatabase(db);//use mydb
		//db.createCollection('posts')
		//db.posts.find()...
		collection=mongodb.getCollection(collectionName);
		
		//insertOne(collection);
		select(collection);
		//find(collection);
		//delete(collection);
		update(collection);
		out.println("수정후 ----------");
		select(collection);
		mongoClient.close();
		
	}
	
	private static void update(MongoCollection<Document> collection) {
		out.println("수정할 글의 저자(author) : ");
		String author=sc.nextLine();
		
		out.println("수정할 게시판 종류(Kind): ");
		String kind=sc.nextLine();
		
		out.println("수정할 Title: ");
		String title=sc.nextLine();
		out.printf("%s, %s, %s", author, kind, title);
		
		Bson filter=eq("author", author);//where절에 해당
		
		//Bson edit=Updates.combine(Updates.set("title", title), Updates.set("kind", kind) );//set절에 해당
		Bson edit=combine(set("title", title), set("kind",kind));
		
		//UpdateResult res=collection.updateOne(filter, edit);
		UpdateResult res=collection.updateMany(filter, edit);
		
		long cnt=res.getModifiedCount();
		out.println(cnt+"개의 posts컬렉션의 도큐먼트 수정됨");
		
	}

	private static void delete(MongoCollection<Document> collection) {
		out.println("삭제할 글의 author: ");
		String author=sc.nextLine();
		
		//DeleteResult res=collection.deleteOne(Filters.eq("author", author));
		DeleteResult res=collection.deleteMany(eq("author",author));
		
		long cnt=res.getDeletedCount();
		out.println(cnt+"개의 posts컬렉션의 도큐먼트 삭제함");
	}

	private static void find(MongoCollection<Document> collection) {
		out.println("검색할 author: ");
		String author=sc.nextLine();
		
		Bson filter=Filters.eq("author",author);
		
		FindIterable<Document> cursor=collection.find(filter);
		MongoCursor<Document> mc=cursor.iterator();
		while(mc.hasNext()) {
			Document doc=mc.next();
			String kind=doc.getString("kind");
			String title=doc.getString("title");
			String author2=doc.getString("author");
			
			//Object id=doc.get("_id");
			ObjectId oid=doc.getObjectId("_id");
			
		out.println(kind+"\t\t"+title+"\t\t"+author2+"\t"+oid);	
		}
		mc.close();
	}

	private static void select(MongoCollection<Document> collection) {
		FindIterable<Document> cursor=collection.find();
		/*
		 * for(Document doc:cursor) { System.out.println(doc.toJson()); }
		 */
		MongoCursor<Document> mc=cursor.iterator();
		while(mc.hasNext()) {
			Document doc=mc.next();
			String kind=doc.getString("kind");
			String title=doc.getString("title");
			String author=doc.getString("author");
			
			//Object id=doc.get("_id");
			ObjectId oid=doc.getObjectId("_id");
			
		System.out.println(kind+"\t\t"+title+"\t\t"+author+"\t"+oid);	
		}
		mc.close();
		System.out.println("===========몽고디비 컬렉션 조회 성공=============");
	}
	
	public static void insertOne(MongoCollection<Document> col) {
		System.out.println("Kind: ");
		String kind=sc.nextLine();
		
		System.out.println("author: ");
		String author=sc.nextLine();
		
		System.out.println("Title: ");
		String title=sc.nextLine();
		
		//System.out.println(kind+", "+author+", "+title);
		
		Document doc=new Document("kind", kind) //key(필드명), value(데이터)
				.append("author", author)
				.append("title", title);
		InsertOneResult res=col.insertOne(doc);
		System.out.println(res.getInsertedId()+" id로 posts컬렉션 저장함");
	}

}
