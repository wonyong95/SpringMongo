package com.multi.service;

import java.util.List;

import com.multi.domain.MemoVO;

public interface MemoService {
	
	int insertMemo(MemoVO memo);
	List<MemoVO> listMemo();
	int deleteMemo(String id);
	int updateMemo(MemoVO memo);
	MemoVO getMemo(String id);
	
}
