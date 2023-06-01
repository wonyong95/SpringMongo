package com.multi.mapper;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.multi.domain.MelonVO;

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


}
