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

			<form method="get">
				<div class="row border mx-3 mb-3 py-2 align-items-center rounded" id="filter">
					<div class="col-2">
					    <label class="form-label" for="test-f1-select">入学年度</label>
					    <select class="form-select" id="student-f1-select" name="f1">
					    	<option value="0">--------</option>
					        <c:forEach var="year" items="${entYear }">
					            <option value="${year }" <c:if test="${year==f1 }">selected</c:if>>${year }</option>
					        </c:forEach>
					    </select>
					</div>
					<div class="col-2">
				    	<label class="form-label" for="test-f2-select">クラス</label>
				    	<select class="form-select" id="student-f2-select" name="f2">
				    		<option value="0">--------</option>
				        	<c:forEach var="num" items="${class_num_list }">
				            	<option value="${num }" <c:if test="${num==f2 }">selected</c:if>>${num }</option>
				        	</c:forEach>
				    	</select>
					</div>
					<div class="col-2">
					    <label class="form-label" for="test-f3-select">科目</label>
					   	<select class="form-select" id="student-f3-select" name="f3">
					    	<option value="0">--------</option>
					        <c:forEach var="subject" items="${subject_list }">
					           <option value="${subject.cd }" <c:if test="${subject.cd==f3 }">selected</c:if>>${subject.name }</option>
					        </c:forEach>
					    </select>
					</div>
					<div class="col-2">
				    	<label class="form-label" for="test-f4-select">回数</label>
					    	<select class="form-select" id="student-f4-select" name="f4">
					    	<option value="0">--------</option>
					    	<option value="1" <c:if test="${1==f4}">selected</c:if>>1</option>
					    	<option value="2" <c:if test="${2==f4}">selected</c:if>>2</option>
					   	 </select>
					</div>
					<div class="col-2 text-center">
				    	<button class="btn btn-secondary" id="filter-button">検索</button>
				    </div>
				    <div class="mt-2 text-warning">${errors.get("e1") }</div>
			    </div>
			</form>

			<form action="TestRegistExecute.action" method="get">
				<c:choose>
					<c:when test="${testlist.size()>0 }">
						<div>科目：${subject_name }（${f4}回目）</div>
					        <table class="table table-hover">
					            <tr>
					                <th>入学年度</th>
					                <th>クラス</th>
					                <th>学生番号</th>
					                <th>氏名</th>
					                <th>点数</th>
					            </tr>
					            <c:forEach var="test" items="${testlist}">
					                <tr>
					                    <td>${test.student.entYear}</td>
					                    <td>${test.student.classNum}</td>
					                    <td>${test.student.name}</td>
					                    <td>${test.student.no}</td>
					                    <td>
					                    <c:choose>
					                    	<c:when test="{$test.no !=0}">
					                    		<input type="text" id="point_${test.student.no}" name="point_${test.student.no}" value="${test.point }">
					                        </c:when>
					                        <c:otherwise>
					                        	<input type="text" id="point_${test.student.no}" name="point_${test.student.no}" value="">
					                        </c:otherwise>
					                    </c:choose>
					               <c:if test="${error[test.student.no] != null }">
					               		<div class="text-warning">${error[test.student.no]}</div>
					               </c:if>
				                </tr>
				                <input type="hidden" name="regist" value="${test.student.no }">
				            </c:forEach>
					    </table>
					    	<input type="hidden" name="count" value="${f4 }">
							<input type="hidden" name="subject" value="${f3 }">
					    <div>
					       	<button type="submit" class="btn btn-secondary" id="filter-button">登録して終了</button>
				        </div>
					</c:when>
					<c:otherwise>
						<div class="mt-2 text-warning">${error.get("e1") }</div>
					</c:otherwise>
				</c:choose>
			</form>
		</section>
	</c:param>
</c:import>