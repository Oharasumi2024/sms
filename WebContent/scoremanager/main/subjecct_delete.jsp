<%-- 科目削除変更JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/common/base.jsp" >
	<c:param name="title">
		得点管理システム
	</c:param>

	<c:param name="scripts"></c:param>

	<c:param name="content">
		<section>
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">学生情報削除</h2>
			<form action="StudentUpdateExecute.action" method="get">
				<div class="mx-auto py-2">
					<label><p>////を削除してもよろしいでしょうか。</p></label>
				</div>
				<div class="mx-auto py-2">
					<input class="btn btn-primary" type="submit" name="delete" value="削除"/>
				</div>
			</form>
			<a href="StudentList.action">戻る</a>
			<div  class="mx-auto py-2">
				<label class="mx-auto py-2" for="cd">科目コード</label><br>
				<input type="hidden" name="subject_cd" value="${subject_cd }">
			</div>
			<div  class="mx-auto py-2">
				<label for="name">科目名</label><br>
				<input type="hidden" name="subject_name" value="${subject_name }">
			</div>
		</section>
	</c:param>
</c:import>