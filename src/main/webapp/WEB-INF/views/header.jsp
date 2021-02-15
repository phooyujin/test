<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initialscale=1">
<!-- path가 ch06이라는 뜻 -->
<c:set var="path" value="${pageContext.request.contextPath }"></c:set>
<link rel="stylesheet" type="text/css" 
	href="${path }/resources/bootstrap/css/bootstrap.min.css">
<!-- 중복체크 하고 빨간색으로 표시해라 -->
<style>
	.err{color:red; font-weight: bold;}
</style>
<script type="text/javascript" 
	src="${path }/resources/bootstrap/js/jquery.js"></script>
<script type="text/javascript" 
	src="${path }/resources/bootstrap/js/bootstrap.min.js"></script>