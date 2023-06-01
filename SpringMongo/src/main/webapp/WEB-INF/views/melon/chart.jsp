<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>melon chart</title>
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
		border:1px solid red;
	}
	#melonSingetCnt{
		width:49%;
		float:right;
		border:1px solid green;
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
	#melonList ul.melon_rank>li:nth-child(3n){
		width:65%;
	}
	span.title{
		font-size:1.1em;
		font-weight:bold;
	}
	span.singer{
		color:gray;
	}
</style>
<script src="https://cdn.jsdelivr.net/npm/jquery@3.6.1/dist/jquery.min.js"></script>

<script>
	$(function(){
		getMelonList();//멜론 목록 가져오기
		
		$('#btn1').click(()=>{
			$.ajax({
				type:'get',
				url:'crawling',
				dateType:'xml',
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
			
		})//#btn1
		
	})
	
	const getMelonList=function(){
		$.ajax({
			type:'get',
			url:'list',
			datatype:'json',
			cache:false,
			success:function(res){
				//alert(res);
				if(res.length==0){
					$('#melonList').html("<h3>오늘의 멜론차트 목록이 없어요 크롤링해야되요</h3>");
				}else{
					showList(res);
				}
			},
			error:function(err){
				alert(err.status)
			}
		})
		
	}
	
	const showList=function(jsonArr){
		let str='<ul class="melon_rank">';
		$.each(jsonArr, function(i, obj){
			str+='<li>';
			str+=obj.ranking;
			str+='</li>';
			str+='<li>';
			str+='<img src="'+obj.albumImage+'">';
			str+="</li>";
			str+='<li>';
			str+='<span class="title">'+obj.songTitle+'</span><br>';
			str+='<span class="singer">'+obj.singer+"</span>";
			str+='</li>';
		})
		str+='</ul>';
		$('#melonList').html(str);
	}
</script>

</head>
<body>

<div id="wrap" class="container">
	<div id="menu">
		<h1>오늘의 melon chart - ${today}</h1>
		<button id="btn1">멜론 차트 크롤링하기</button>
		<button id="btn2">멜론 차트 목록보기</button>
		<button id="btn3">멜론 차트 가수별 노래수 보기</button>
	</div>
	<div id="melonList">
		<!-- 여기에 멜론차트 목록 -->
	</div>
	<div id="melonSingetCnt">
		<!-- 여기에 멜론차트 가수별 노래들어옴 -->
	</div>
</div>

</body>
</html>