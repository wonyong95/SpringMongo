package com.multi.mapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.multi.domain.MelonVO;
import com.multi.domain.SumVO;

import lombok.extern.log4j.Log4j;

@Repository("melonMapper")
@Log4j
public class MelonMapperImpl implements MelonMapper {

	@Inject
	private MongoTemplate mongoTemplate;
	
	@Override
	public int crawlingMelon() throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<MelonVO> getMelonList(String collectionName) throws Exception {
		
		return this.mongoTemplate.findAll(MelonVO.class, collectionName);
	}

	@Override
	public int insertMelon(List<MelonVO> mList, String collectionName) {
		log.info("collectionName: "+collectionName);
		
		Collection<MelonVO> arr=mongoTemplate.insert(mList, collectionName);
		
		return arr.size();
	}
	/*
	 * 		db.Melon_230602.aggregate([
	 *  {
		    $group:{_id:'$singer', cntBySinger:{$sum:1}}
		},
		{
		    $project:{singer:'$_id', cntBySinger:'$cntBySinger'}
		},
		{
		    $match:{cntBySinger:{$gt:1}}
		},
		{
		    $sort:{cntBySinger:-1}
		}
		])
	 */
	
	@Override
	public List<SumVO> getCntBySinger(String colName) {
		List<? extends Bson> pipeline=Arrays.asList(
				new Document().append("$group", 
						new Document().append("_id", "$singer")
									  .append("cntBySinger", new Document().append("$sum", 1))),
				new Document().append("$project", new Document().append("singer", "$_id").append("cntBySinger", "$cntBySinger")),
				new Document().append("$match", new Document().append("cntBySinger", new Document().append("$gt", 1))),
				new Document().append("$sort", new Document().append("cntBySinger",-1))
				);
		MongoCollection<Document> col=mongoTemplate.getCollection(colName);
				
		AggregateIterable<Document> cr=col.aggregate(pipeline);
		if(cr==null) return null;
		
		MongoCursor<Document> mc=cr.cursor();
		
		List<SumVO> arr=new ArrayList<>();
		while(mc.hasNext()) {
			Document doc=mc.next();
			String singer=doc.getString("singer");
			int cntBySinger=doc.getInteger("cntBySinger", 0);
			SumVO svo=new SumVO();
			svo.setSinger(singer);
			svo.setCntBySinger(cntBySinger);
			arr.add(svo);
		}
			
		return arr;
	}


}
