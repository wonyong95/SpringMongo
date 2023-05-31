<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>input.jsp</title>
<!-- memo.css참조--------------------------------------------- -->
<link rel="stylesheet" type="text/css" href="./resources/memo.css">
<!-- -------------------------------------------------------- -->
<script type="text/javascript">
	function check(){
		//window.document.mf.submit();
		if(mf.name.value==""){
			alert('작성자를 입력하세요');
			mf.name.focus();
			return;
		}
		if(mf.msg.value==""){
			alert('메모내용을 입력하세요');
			mf.msg.focus();
			return;
		}
		let len=mf.msg.value.length;
		if(len>100){
			alert('메모 내용은 100자까지만 가능해요');
			mf.msg.select();
			return;
		}
		
		mf.submit();
	}
</script>
</head>
<body>
<div id="wrap">
<!-- 
	/memo  => get방식이면 메모 등록 폼을 보여주고
	/memo  => post방식이면 메모 글을 등록 하는 로직을 수행 
 -->
	<form name="mf" action="memo" method="post">
		<!-- table로 화면과 같이 구성하세요 -->
		<table border="1">
			<tr>
				<th colspan="2"><h1>::한줄 메모장::</h1></th>
			</tr>
			<tr>
				<td width="20%">
					<b>작성자</b>
				</td>
				<td width="80%">
					<input type="text" name="name" placeholder="Name">
				</td>				
			</tr>
			
			<tr>
				<td width="20%">
					<b>메모 내용</b>
				</td>
				<td width="80%">
					<input type="text" name="msg" placeholder="Message">
				</td>				
			</tr>
			<tr>
				<td colspan="2">
					<button type="button" onclick="check()">글 남기기</button>
					<button type="reset">다시 쓰기</button>
				</td>
			</tr>
		</table>
	
	</form>
</div>
</body>
</html>