<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<div class="container">
	<h2 class="text-primary">회원 정보 상세</h2>
		<table class="table table-striped">
			<tr><td>아이디</td><td>${member.id }</td></tr>
			<tr><td>이름</td><td>${member.name }</td></tr>
			<tr><td>이메일</td><td>${member.email }</td></tr>
			<tr><td>등록일</td><td>${member.regdate }</td></tr>
			<tr><td colspan="2">사진</td></tr>
			<tr><td colspan="2">
				<c:forEach var="ph" items="${list }">
				<img alt="" src="resources/upload/${ph.fileName }" width="200">	
				</c:forEach>
				</td></tr>
		</table>
		<a href="main.do" class="btn btn-default">메인</a>
</div>
</body>
</html>