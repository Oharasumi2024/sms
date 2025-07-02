
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/common/base.jsp" >

	<c:param name="scripts"></c:param>

	<c:param name="content">
		<section class="me=4">

				<div class="row border mx-3 mb-3 py-2 align-items-center rounded" id="filter">

				<div class="col-2 text-center">


					</div>
					<div class="mt-2 text-warning">${errors.get("f1") }</div>
				</div>

                  	<div>氏名：${students.size() }件</div>
					<table class="table table-hover">

						<tr>
							<th>科目名</th>
							<th>科目コード</th>
							<th>回数</th>
							<th>点数</th>

						</tr>
						<c:forEach var="student" items="${students}">
							<tr>
								<td>${student.subjectName}</td>
								<td>${student.subjectCd }</td>
								<td>${student.num }</td>
								<td>${student.point }</td>

								<td class="text-center">
								</td>
								<td></td>
							</tr>
						</c:forEach>
					</table>
	</section>
	</c:param>
</c:import>