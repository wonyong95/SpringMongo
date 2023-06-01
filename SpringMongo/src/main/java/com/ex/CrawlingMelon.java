package com.ex;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.common.CommonUtil;
import com.multi.domain.MelonVO;

public class CrawlingMelon {
   
   
   String url = "https://www.melon.com/chart/index.htm";
   public static void main(String[] args) throws Exception {
      // TODO Auto-generated method stub
      CrawlingMelon app = new CrawlingMelon();
      app.crawlingMelon();
   }

   private int crawlingMelon() throws Exception {
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
      return mList.size();
   }
   
   
}