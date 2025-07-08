<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<p>氏名;${student.name}(${student.no })</p>

<c:choose>
<c:when test="${empty test_list}">
<div class="ms-4 text-danger">該当する成績データが見つかりませんでした。</div>
</c:when>
<c:otherwise>
<div class="table-responsive px-3">
<table class="table table-bordered table-hover">
<thead class="table-light">
<tr>
<th>科目名</th>
<th>科目コード</th>
<th>回数</th>
<th>点数</th>
</tr>
</thead>
<tbody>

<c:forEach var="test" items="${test_list}">
<tr>
<td>${test.subjectName}</td>
<td>${test.subjectCd}</td>
<td>${test.num}</td>
<td>
<c:choose>
<c:when test="${test.point == null}">
                                        -
</c:when>
<c:otherwise>
                                        ${test.point}
</c:otherwise>
</c:choose>
</td>
</tr>
</c:forEach>
</tbody>
</table>
</div>
</c:otherwise>
</c:choose>
