
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/common/base.jsp" >

			<c:choose>
				<c:when test="${students.size()>0 }">
					<div>科目：${students.size() }件</div>
					<table class="table table-hover">
						<tr>
							<th>入学年度</th>
							<th>クラス</th>
							<th>学生番号</th>
							<th>氏名</th>
							<th>１回目</th>
							<th>2回目</th>
						</tr>
						<c:forEach var="student" items="${students}">
							<tr>
								<td>${student.entYear }</td>
								<td>${student.no }</td>
								<td>${student.name }</td>
								<td>${student.classNum }</td>
								<td class="text-center">


								</td>
								<td></td>
							</tr>
						</c:forEach>
					</table>
				</c:when>

			</c:choose>	


</c:import>