<%-- 成績登録JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/common/base.jsp" >
	<c:param name="title">
		得点管理システム
	</c:param>

	<c:param name="scripts"></c:param>
	<c:param name="content">
		<section class="me=4">
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">成績検索画面</h2>

			<form action="SearchServlet" method="get">
				<div class="row border mx-3 mb-3 py-2 align-items-center rounded" id="filter">
					<div class="col-2">
					    <label class="form-label" for="test-f1-select">入学年度</label>
					    <select name="f1" id="f1">
					    	<option value="0">--------</option>
					        <c:forEach var="year" items="${admissionYears}">
					            <option value="${year}">${year}</option>
					        </c:forEach>
					    </select>
					</div>
					<div class="col-2">
				    	<label class="form-label" for="test-f2-select">クラス</label>
				    	<select name="f2" id="f2">
				    		<option value="0">--------</option>
				        	<c:forEach var="class" items="${classList}">
				            	<option value="${class.no}">${class.name}</option>
				        	</c:forEach>
				    	</select>
					</div>
					<div class="col-2">
					    <label class="form-label" for="test-f3-select">科目</label>
					    <select name="f3" id="f3">
					    	<option value="0">--------</option>
					        <c:forEach var="subject" items="${subjectList}">
					            <option value="${subject.id}">${subject.name}</option>
					        </c:forEach>
					    </select>
					</div>
					<div class="col-2">
				    	<label class="form-label" for="test-f1-select">回数</label>
					    	<select name="f4" id="f4">
					    	<option value="0">--------</option>
					        	<c:forEach var="count" items="${countList}">
					            	<option value="${count}">${count}</option>
					        	</c:forEach>
					   	 </select>
					</div>
					<div class="col-2">
				    	<input type="submit" value="検索" />
				    </div>
			    </div>
			</form>

			<c:choose>
			  <c:when test="${not empty testList}">
			    <section class="me=4">
				    <div>科目：${subject}（${count}回目）</div>
				    <form action="RegisterServlet" method="post">
				        <table class="table table-hover">
				            <tr>
				                <th>入学年度</th>
				                <th>クラス</th>
				                <th>学生番号</th>
				                <th>氏名</th>
				                <th>点数</th>
				            </tr>
				            <c:forEach var="test" items="${testList}">
				            <input type="hidden" name="regist" value="${test.student.no}"/>
				                <tr>
				                    <td>${test.student.admissionYear}</td>
				                    <td>${test.classNo}</td>
				                    <td>${test.student.no}</td>
				                    <td>${test.student.name}</td>
				                    <td>
				                        <input type="text" name="point_${test.student.no}"
				                               value="${test.point}"
				                               pattern="^$|([0-9]{1,2}|100)" />
				                    </td>
				                </tr>
				            </c:forEach>
				        </table>
				        <input type="hidden" name="subject" value="${subject}" />
				        <input type="hidden" name="count" value="${count}" />
				        <input type="submit" value="登録して終了" />
				    </form>
				</section>

			  </c:when>
			  <c:otherwise>
			    <p>該当する成績データがありません。</p>
			  </c:otherwise>
			</c:choose>
		</section>
	</c:param>
</c:import>