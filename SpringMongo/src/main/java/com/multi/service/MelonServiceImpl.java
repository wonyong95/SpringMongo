package com.multi.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.common.CommonUtil;
import com.multi.domain.MelonVO;
import com.multi.domain.MemoVO;
import com.multi.mapper.MelonMapper;

@Service("melonService")
public class MelonServiceImpl implements MelonService {
   
   @Inject
   private MelonMapper melonMapper;
   
   
   private String url = "https://www.melon.com/chart/index.htm";
   
   @Override
   public int crawlingMelon() throws Exception {
      Connection con = Jsoup.connect(url);
      Document doc = con.get();
     
      Elements divElem = doc.select("div.service_list_song");
      Elements imgElem = divElem.select("div.wrap img");
      Elements rankElem = divElem.select("span.rank");
      Elements songElem = divElem.select("table>tbody>tr>td:nth-child(6) div.wrap_song_info");
      
      List<MelonVO> mList = new ArrayList<>();
      for(int i=0; i<songElem.size(); i++) {
         Element rankInfo = rankElem.get(i+1);
         Element songInfo = songElem.get(i);
         Element songImg = imgElem.get(i);
         
         String ranking = rankInfo.text();
         String songTitle = songInfo.select("div.ellipsis.rank01 a").text();
         String songSinger = songInfo.select("div.ellipsis.rank02>a").text();
         String songUrl = songImg.attr("src");
         
         MelonVO vo = new MelonVO();
         vo.setRanking(ranking);
         vo.setSongTitle(songTitle);
         vo.setSinger(songSinger);
         vo.setAlbumImage(songUrl);
         vo.setCrawlingTime(CommonUtil.getDateTime("yyyyMMddHHmmss"));
         
         mList.add(vo);
            
         
      }// for------------------------
      String collectionName="Melon_"+CommonUtil.getDateTime("yyMMdd");
      //몽고디비에 크롤링한 데이터 insert하기
      int cnt=this.melonMapper.insertMelon(mList, collectionName);
      
      return cnt;
   }

@Override
public List<MelonVO> getMelonList() throws Exception {
	String colName="Melon_"+CommonUtil.getDateTime("yyMMdd");
	
	return this.melonMapper.getMelonList(colName);
}
   
   

}