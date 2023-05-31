package com.multi.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.mongodb.client.result.DeleteResult;
import com.multi.domain.MemoVO;
import com.multi.mapper.MemoMapper;

@Service("memoService")
public class MemoServiceImpl implements MemoService {

	@Inject
	private MemoMapper mMapper;

	@Override
	public int insertMemo(MemoVO memo) {
		return mMapper.insertMemo(memo);
	}

	@Override
	public List<MemoVO> listMemo() {
		return this.mMapper.listMemo();
	}

	@Override
	public int deleteMemo(String id) {

		return this.mMapper.deleteMemo(id);
	}

	@Override
	public int updateMemo(MemoVO memo) {
		return this.mMapper.updateMemo(memo);
	}

	@Override
	public MemoVO getMemo(String id) {
		return mMapper.getMemo(id);
	}

}