<%-- 科目情報変更JSP --%>
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
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">科目情報変更</h2>
			<form action="SubjectUpdateExecute.action" method="get">
				<div class="mx-auto py-2">
					<label for="cd">科目コード</label><br>
					<input  type="text" id="cd" name="cd" style="border :none; outline :none;" value="${cd }" readonly placeholder="選択された科目の科目コード"/>
				</div>
				<div class="mt-2 text-warning">${errors.get("1") }</div>
				<div class="mx-auto py-2">
					<label for="name">科目名</label><br>
					<input class="form-control" type="text" id="name" name="name" value="${name }" required maxlength="20" placeholder="選択された科目の科目名"/>
				</div>
				<div class="mx-auto py-2">
					<input class="btn btn-primary" type="submit" value="変更"/>
				</div>
			</form>
			<div class="mt-3">
          		<a href="SubjectList.action" class="btn btn-secondary">戻る</a>
        	</div>
		</section>
	</c:param>
</c:import>