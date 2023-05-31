package com.multi.mongoweb;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.multi.domain.MemoVO;
import com.multi.service.MemoService;

import lombok.extern.log4j.Log4j;

@Controller
@Log4j
public class MemoController {
	
	@Autowired
	private MemoService mService;
	
	@GetMapping("/memo")
	public String memoForm() {
		
		return "memo/input";
	}
	/* 글번호값을 일정 간격으로 증가시켜 저장해놓을 컬렉션을 미리 생성해두자
	 * 컬렉션명: sequence (필드명: count:0)
	 * use mydb
	 * db.createCollection("sequence")
	 * db.sequence.insertOne({collectionName:'memo', count:0})
	 * */
	@PostMapping("/memo")
	public String memoInsert(@ModelAttribute MemoVO memo) {
		log.info("memo==="+memo);
		int n=mService.insertMemo(memo);
		return "redirect:list";
	}
	
	@GetMapping("/list")
	public String memoList(Model m) {
		List<MemoVO> memoArr=mService.listMemo();
		m.addAttribute("memoArr", memoArr);
		return "memo/list";
	}
	
	@GetMapping("/delete/{id}")
	public String memoDelete(@PathVariable("id") String id) {
		log.info("id=="+id);
		mService.deleteMemo(id);
		return "redirect:../list";
	}
	
	@GetMapping("/edit/{id}")
	public String memoEditForm(@PathVariable("id") String id,Model m) {
		MemoVO vo=mService.getMemo(id);
		
		m.addAttribute("memo", vo);
		
		return "memo/edit";
	}
	
	@PostMapping("/edit")
	   public String memoEditEnd(@ModelAttribute MemoVO memo) {
	      log.info("memo == " +memo);
	      int cnt = mService.updateMemo(memo);
	      log.info(cnt+"개의 데이터 수정 완료");
	      return "redirect:list";
	   }

}
