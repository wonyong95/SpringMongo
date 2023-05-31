package com.multi.mapper;

import java.util.List;

import com.multi.domain.MemoVO;

public interface MemoMapper {
	
	int insertMemo(MemoVO memo);
	List<MemoVO> listMemo();
	int deleteMemo(String id);
	int updateMemo(MemoVO memo);
	MemoVO getMemo(String id);
}
