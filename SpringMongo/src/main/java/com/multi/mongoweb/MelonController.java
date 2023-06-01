package com.multi.mongoweb;

import java.util.List;

import javax.inject.Inject;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.common.CommonUtil;
import com.multi.domain.MelonVO;
import com.multi.service.MelonService;

import lombok.extern.log4j.Log4j;

@RestController
@RequestMapping("/melon")
@Log4j
public class MelonController {
	
	@Inject
	private MelonService melonService;
	
	@GetMapping("/chart")
	public ModelAndView melonChart() {
		
		String todayStr=CommonUtil.getDateTime("yyyy-MM-dd");
		ModelAndView mv=new ModelAndView();
		mv.addObject("today", todayStr);
		mv.setViewName("melon/chart");///WEB-INF/views/melon/chart.jsp
		return mv;
		
	}
	@GetMapping(value="/crawling", produces="application/xml")
	public ModelMap collectiMelonChart() throws Exception {
		log.info("--크롤링 시작--------------------");
		int cnt=this.melonService.crawlingMelon();		
				
		log.info("--크롤링 끝--------------------");
		
		ModelMap map=new ModelMap();
		map.addAttribute("result",cnt);
		return map;
	}
	@GetMapping(value="list", produces="application/json")
	public List<MelonVO> getMelonList() throws Exception {
		return this.melonService.getMelonList();
	}
}
