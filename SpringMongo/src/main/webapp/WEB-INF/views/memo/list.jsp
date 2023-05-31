<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"   %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>list.jsp</title>
<link rel="stylesheet" type="text/css" href="./resources/memo.css">
</head>
<body>
<!-- 한줄 메모장 글쓰기 import-------------------------------- -->
   <c:import url="/memo"/>
<!-- ---------------------------------------------- -->

<div id="wrap">
   <%-- ${memoArr} --%>
   
   <table border="1">
      <tr>
         <th colspan="4">
            <h2>:: 한줄 메모장 목록 ::</h2>
            <span>총 게시글 수:${totalCount}개</span>
            <span>현재 ${cpage} 페이지/ ${pageCount} pages</span>
         </th>
      </tr>
      <tr>
         <td width="10%"><b>글번호</b></td>
         <td width="60%"><b>메모내용</b></td>
         <td width="20%"><b>작성자</b></td>
         <td width="10%"><b>수정|삭제</b></td>
      </tr>
      <c:if test="${memoArr eq null or empty memoArr }">
         <tr>
            <td colspan="4">
               데이터가 없습니다
            </td>            
         </tr>
      </c:if>
      <c:if test="${memoArr ne null and not empty memoArr}">
         <c:forEach var="vo" items="${memoArr}">
         <tr>
            <td>${vo.no}</td>
            <td>${vo.msg} 
            <span class="wdate">[${vo.wdate}]</span></td>
            <td>${vo.name}</td>
            <td>
               <a href="edit/${vo.id}">수정</a>|
               <a href="delete/${vo.id}">삭제</a>
            </td>
         </tr>
         </c:forEach>
         
      </c:if>
      <tr>
         <td colspan='4'>
            <c:forEach var="i" begin="1" end="${pageCount}">
               [<a href="memoList?cpage=${i}">${i}</a>]
            </c:forEach>
         </td>
      </tr>
   </table>
   <p style='text-align:center'>
      [<a href="memo">글쓰기</a>]
   </p>
</div>

</body>
</html>