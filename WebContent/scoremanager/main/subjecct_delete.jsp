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
        <c:param name="content">
	        <section class="container">
	            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">科目削除</h2>
	            <c:if test="${not empty errors}">
	                <div class="alert alert-danger">
	                    <c:forEach var="error" items="${errors}">
	                        <p><c:out value="${error.value}"/></p>
	                    </c:forEach>
	                </div>
	            </c:if>
	            <form action="SubjectDeleteExecute.action" method="post" onsubmit="return confirmDelete()">
	                <div class="mb-3">
	                    <label class="form-label">科目コード</label>
	                    <p><c:out value="${subject_cd}"/></p>
	                    <input type="hidden" name="subject_cd" value="${subject_cd}"/>
	                </div>
	                <div class="mb-3">
	                    <label class="form-label">科目名</label>
	                    <p><c:out value="${subject_name}"/></p>
	                    <input type="hidden" name="subject_name" value="${subject_name}"/>
	                </div>
	                <div class="mb-3">
	                    <input class="btn btn-danger" type="submit" value="削除" />
	                    <a href="SubjectList.action" class="btn btn-secondary ms-2">戻る</a>
	                </div>
	            </form>
	        </section>
	    </c:param>
    </c:param>
</c:import>