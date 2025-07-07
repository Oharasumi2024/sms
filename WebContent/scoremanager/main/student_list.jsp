
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
						<th>入学年度</th>
							<th>学生番号</th>
							<th>クラス</th>
							<th>氏名</th>
							<th>1回</th>
							<th>2回</th>



						</tr>
						<c:forEach var="subjects" items="${subjects}">
							<tr>
								<td>${subjects.entYear}</td>
								<td>${subjects.studentNo }</td>
								<td>${subjects.studentName }</td>
								<td>${subjects.classNum }</td>
								<td>${subjects.points1}</td>
								<td>${subjects.points2 }
                                 <td><td/>
								<td class="text-center">

								</td>
								<td></td>
							</tr>
						</c:forEach>
					</table>
	</section>
	</c:param>
</c:import>