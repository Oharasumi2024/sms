<%-- 科目削除変更JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/common/base.jsp">
    <c:param name="title">得点管理システム</c:param>
    <c:param name="scripts">
     <script>
            function confirmDelete() {
                return confirm('この科目を削除しますか？');
            }
        </script>
    </c:param>
    <c:param name="content">
	        <section class="container">
	            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">科目情報削除</h2>
	            <c:if test="${not empty errors}">
	                <div class="alert alert-danger">
	                    <c:forEach var="error" items="${errors}">
	                        <p><c:out value="${error.value}"/></p>
	                    </c:forEach>
	                </div>
	            </c:if>
	            <div class="mx-auto py-2">
	            	<input class="btn btn-danger" type="submit" value="削除" />
	            	<br>
	            	<br>
	            	<br>
	            </div>
	            <a href="List.action">戻る</a>
	            <form action="SubjectDeleteExecute.action" method="get" onsubmit="return confirmDelete()">
	                <div class="mx-auto py-2">
	                    <p><c:out value="${subject_cd}"/></p>
	                    <input type="hidden" name="subject_cd" value="${subject_cd}"/>
	                </div>
	                <div class="mx-auto py-2">
	                    <p><c:out value="${subject_name}"/></p>
	                    <input type="hidden" name="subject_name" value="${subject_name}"/>
	                </div>
	            </form>
	        </section>
    </c:param>
</c:import>