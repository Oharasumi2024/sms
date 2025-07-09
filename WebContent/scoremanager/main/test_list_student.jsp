<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/common/base.jsp" >
<c:param name="title">
	得点管理システム
</c:param>
<c:param name="scripts"></c:param>
<c:param name="content">
		<div class="row border mx-3 mb-3 py-2 align-items-center rounded" id="filter">
			<form method="get" action="TestListSubjectExecute.action">
				<div class="row">
					<div class="form col-2" style="display:flex; justify-content:center; align-items:center;">
						<p style="margin: auto">科目情報</p>
					</div>
					<div class="form col-2">
						<label class="form-label" for="student-f1-select">入学年度</label>
						<select class="form-select" id="student-f1-select" name="f1">
							<option value="0">--------</option>
							<c:forEach var="year" items="${ent_year_set }">
								<%-- 現在のyearと選択されていたf1が一致していた場合selectedを追記 --%>
								<option value="${year }" <c:if test="${year==f1 }">selected</c:if>>${year }</option>
							</c:forEach>
						</select>
					</div>

					<div class="form col-2">
						<label class="form-label" for="student-f2-select">クラス</label>
						<select class="form-select" id="student-f2-select" name="f2">
							<option value="0">--------</option>
							<c:forEach var="num" items="${class_num_set }">
								<%-- 現在のnumと選択されていたf2が一致していた場合selectedを追記 --%>
								<option value="${num }" <c:if test="${num==f2 }">selected</c:if>>${num }</option>
							</c:forEach>
						</select>
					</div>

					<div class=" form col-4">
						<label class="form-label" for="student-f3-select">科目</label>
						<select class="form-select" id="student-f3-select" name="f3">
							<option value="0">--------</option>
							<c:forEach var="subject" items="${subject_set }">
								<%-- 現在のsubjectと選択されていたf3が一致していた場合selectedを追記 --%>
								<option value="${subject.cd }" <c:if test="${subject.cd==f3 }">selected</c:if>>${subject.name }</option>
							</c:forEach>
						</select>
					</div>
					<div class="form col-2 text-center" style="display:flex; justify-content:center; align-items:center;">
						<button class="btn btn-secondary" id="button_1">検索</button>
					</div>
					<div><input type="hidden" name="f" value="sj"></div>
				</div>
			</form>

			<hr style="color: #dee2e6 border: none; border-top: 1px dashed #999; margin: 30px auto; width: 90%" />

			<form method="get" action="TestListStudentExecute.action">
				<div class="row">
					<div class="col-2" style="display:flex; justify-content:center; align-items:center;">
						<p style="margin: auto">学生情報</p>
					</div>
					<div class="col-4">
						<label>学生番号</label>
						<input class="form-control" size="25" type="text" id="f4" name="f4" value="${f4 }" required maxlength="10" placeholder="学生番号を入力してください" />
					</div>
					<div class="col-2 text-center" style="display:flex; justify-content:center; align-items:center;">
						<button class="btn btn-secondary" id="button_2">検索</button>
					</div>
					<div class="col-4"><input type="hidden" name="f" value="st"></div>
				</div>
			</form>
			</div>

<c:choose>
  <c:when test="${not empty student}">
    <p>氏名：${student.name}（${student.no}）</p>

    <c:if test="${empty test_list}">
      <p>成績情報が存在しませんでした。</p>
    </c:if>

    <c:if test="${not empty test_list}">
      <div class="table-responsive px-3">
        <table class="table table-bordered table-hover">
          <tr>
            <th>科目名</th>
            <th>科目コード</th>
            <th>回数</th>
            <th>点数</th>
          </tr>
          <tbody>
            <c:forEach var="test" items="${test_list}">
              <tr>
                <td>${test.subjectName}</td>
                <td>${test.subjectCd}</td>
                <td>${test.num}</td>
                <td>
                  <c:choose>
                    <c:when test="${test.point == null}">-</c:when>
                    <c:otherwise>${test.point}</c:otherwise>
                  </c:choose>
                </td>
              </tr>
            </c:forEach>
          </tbody>
        </table>
      </div>
    </c:if>
  </c:when>
<c:otherwise>
    <c:if test="${not empty errors.student}">
      <p>${errors.student}</p>
    </c:if>
</c:otherwise>
</c:choose>
</c:param>
</c:import>
