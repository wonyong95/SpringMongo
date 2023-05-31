package com.ex;
import java.io.IOException;
import java.util.Iterator;

/*
 * 파이썬 BeautifulSoup 이용해 크롤링
 * Java는 Jsoup이용
 * 
 * 라이브러리 다운로드사이트: http://jsoup.org/download
 * == pom.xml에 Jsoup라이브러리 등록
 * <!-- https://mvnrepository.com/artifact/org.jsoup/jsoup -->
		<dependency>
			<groupId>org.jsoup</groupId>
			<artifactId>jsoup</artifactId>
			<version>1.15.3</version>
		</dependency>
 */
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.common.CommonUtil;


public class CrawlingTest {


	public static void main(String[] args) {
		//CGV 영화순위 가져오자
		String url="http://www.cgv.co.kr/movies/?";
		
		//https://www.melon.com/chart/index.htm=> Melon차트
		Document doc;
		
		try {
			//http프로토콜만 가능. https프로토콜은 보안상 안된다
			doc=Jsoup.connect(url).get();
			//해당 url의 웹페이지 전체 소스를 document에 담아 반환
			//System.out.println(doc);
			//이중에서 우리가 필요한 데이터 추출
			Elements element=doc.select("div.sect-movie-chart");
			//System.out.println(element);
			
			//Elements rankEle=element.select("strong.rank");
			//System.out.println(rankEle);
			//영화순위
			Iterator<Element> movie_rank=element.select("strong.rank").iterator();
			//영화제목
			Iterator<Element> movie_title=element.select("strong.title").iterator();
			//영화예매율
			Iterator<Element> movie_percent=element.select("strong.percent span").iterator();
			//영화점수(eggs)
			Iterator<Element> movie_score=element.select("div > span.percent").iterator();
			//개봉일
			Iterator<Element> movie_open=element.select("span.txt-info strong").iterator();
			//System.out.println(movie_open);
			
			//영화이미지
			Iterator<Element> movie_img=element.select("span.thumb-image img").iterator();
			
			int cnt=1;
			while(movie_rank.hasNext()) {
				MovieVO vo=new MovieVO();
				vo.setMovie_no(cnt++);
				vo.setMovie_rank(movie_rank.next().text());
				vo.setMovie_title(movie_title.next().text());
				vo.setMovie_percent(movie_percent.next().text());
				vo.setMovie_score(movie_score.next().text());
				vo.setMovie_open(movie_open.next().text().substring(0,11));
				
				String imgUrl=movie_img.next().attr("src");//영화이미지 경로
				vo.setMovie_img(imgUrl);
				
				vo.setRank_checkTime(CommonUtil.getDateTime("yyyy-mm-dd HH:mm:ss"));//년월일 시분초
				System.out.println("vo: "+vo);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
