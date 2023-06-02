<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>:::melon chart:::</title>
<style>
	#wrap{
		padding:3em;
	}
	#menu{
		width:80%;
		margin:1em auto;
		text-align:center;	
	}
	#menu button{
		padding:7px;
	}
	#melonList{
		width:49%;
		float:left;
		/* border:1px solid red; */
	}
	#melonSingerCnt{
		width:49%;
		float:right;
		/* border:1px solid green; */
	}
	#melonList ul.melon_rank>li{
		list-style:none;
		float:left;
		height:120px;
		margin-bottom:3px;
	}
	#melonList ul.melon_rank>li:nth-child(3n+1){
		width:10%;
		font-weight:bold;
		font-size:1.2em;
	}
	#melonList ul.melon_rank>li:nth-child(3n+2){
		width:25%;
	}
	#melonList ul.melon_rank>li:nth-child(3n+3){
		width:65%;
	}
	span.title{
		font-size:1.1em;
		font-weight:bold;
	}
	span.singer{
		color:gray;
	}
	#melonSingerCnt .melon_singer_cnt>li{
		list-style:none;
		float:left;
		height:25px;
		line-height:25px;
		margin-bottom:3px;
	}
	#melonSingerCnt .melon_singer_cnt>li:nth-child(2n){
		width:25%;
	}
	#melonSingerCnt .melon_singer_cnt>li:nth-child(2n+1){
		width:75%;
	}
	
</style>
<script src="https://cdn.jsdelivr.net/npm/jquery@3.6.1/dist/jquery.min.js"></script>
<!-- 구글 차트 라이브러리 cdn----------------------------------------------------------- -->
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
<!-- ----------------------------------------------------------------------------- -->

<script>
	$(function(){
		google.charts.load('current', {'packages':['corechart','bar']});
		getMelonList();//멜론 목록 가져오기
		getMelonChart();//가수별 노래수 차트 가져오기
		$('#btn1').click(()=>{
			$.ajax({
				type:'get',
				url:'crawling',
				dataType:'xml',
				cache:false
			})
			.done((res)=>{
				//alert(res);//XMLDocument
				let cnt=$(res).find('result').text();
				$('#melonList').html("<h3>"+cnt+"개의 데이터를 크롤링했습니다</h3>");
				getMelonList();
			})
			.fail((err)=>{
				alert('error: '+err.status)
			})
		})//#btn1--------------
		
		$('#btn3').click(()=>{			
			getMelonChart();
		})//#btn3---------------
	})//$() end------------------
	
	const getMelonChart=function(){
		$.ajax({
			type:'get',
			url:'singerCnt',
			dataType:'json',
			cache:false
		})
		.done((res)=>{
			//alert(JSON.stringify(res))
			showCntBySinger(res);
		})
		.fail((err)=>{
			alert(err.status)
		})
	}//------------------------
	
	const showCntBySinger=function(jsonArr){
		google.charts.load('current', {'packages':['corechart','bar']});
	   var data = new google.visualization.DataTable()
	   data.addColumn("string","Singer")
	   data.addColumn("number","Song Count");
		
		let mydata=[];//배열 <==2차원 배열 형태로 넣어주어야 한다
		
		let str='<ul class="melon_singer_cnt">';
			$.each(jsonArr, function(i, obj){
				let arr=[];//배열 <=1차원 배열
				arr.push(obj.singer);//가수명
				arr.push(obj.cntBySinger)//노래개수
				//arr=['BTS',5]
				mydata.push(arr);
				console.log(arr)
				str+='<li>'+obj.singer+"</li>";
				str+='<li>'+obj.cntBySinger+'</li>';
			})
			console.log('mydata: '+mydata);
			data.addRows(mydata);
			str+='</ul>';
		$('#cntList').html(str);
		
		renderChart(data);
	}//-----------------------------
	
	const renderChart=function(data){
		let options={
				'title':'멜론차트에 오른 가수별 노래수',
				'width':600,
				'height':800,
				'fontSize':11,
				'fontName':'Verdana',
				//'colors':['#aaccee','#325912']
		}
		let bar_chart=new google.visualization.BarChart(document.getElementById('myBarChart'))
		bar_chart.draw(data, options);
		
        let pie_chart=new google.visualization.PieChart(document.getElementById('myPieChart'));
        pie_chart.draw(data,options);
        
	}//--------------------------------
	
	const getMelonList=function(){
		  $.ajax({
		   type:'get',
		   url:'list',
		   dataType:'json',
		   cache:false,
		   success:function(res){
		    //alert(res);
		    if(res.length==0){
		     $('melonList').html("<h3>오늘의 멜론차트 목록이 없어요. 크롤링해야 해요</h3>");
		    }else{
		     showList(res);
		    }
		   },
		   error:function(err){
		    alert(err.status)
		   }
		  })
		 }//-----------------------------
	
	const showList=function(jsonArr){
		let str='<ul class="melon_rank">';
			$.each(jsonArr, function(i, obj){
				str+='<li>';
				str+=obj.ranking;
				str+='</li>';
				str+='<li>';
				str+='<img src="'+obj.albumImage+'">';
				str+='</li>';
				str+='<li>';
				str+='<span class="title">'+obj.songTitle+'</span><br>';
				str+='<span class="singer">'+obj.singer+"</span>";
				str+='</li>';
			})				
			str+='</ul>';
		$('#melonList').html(str);	
		
	}//-------------------------------	
	
	
</script>

</head>
<body>

<div id="wrap" class="container">
	<div id="menu">
		<h1>오늘의 Melon Chart - ${today}</h1>
		<button  id="btn1">멜론 차트 크롤링하기</button>
		<button  id="btn2" onclick="getMelonList()">멜론 차트 목록보기</button>
		<button  id="btn3">멜론 차트 가수별 노래</button>
	</div>
	<div id="melonList">
		<!-- 여기에 멜론차트 목록 들어옴 -->
	</div>
	<div id="melonSingerCnt">
		<!-- 여기에 멜론차트 가수별 노래 들어옴  -->
		<div id="cntList"></div>
		<div style="clear:both"></div>
		<div id="myBarChart" ></div>
		<div id="myPieChart"></div>
	</div>

</div>


</body>
</html>