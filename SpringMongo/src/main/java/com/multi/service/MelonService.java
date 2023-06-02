package com.multi.service;

import java.util.List;

import com.multi.domain.MelonVO;
import com.multi.domain.SumVO;

public interface MelonService {

	//오늘의 멜론 크롤링해서 몽고디비에 저장
	int crawlingMelon() throws Exception;
	
	//수집된 멜론가져오기
	List<MelonVO> getMelonList() throws Exception;

	List<SumVO> getCntBySinger();
}
