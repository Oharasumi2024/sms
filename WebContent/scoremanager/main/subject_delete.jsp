<%-- 科目削除変更JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/common/base.jsp">
  <c:param name="title">得点管理システム</c:param>
  <c:param name="content">
    <div id="wrap_box">
      <!-- ①画面タイトル -->
      <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2">科目情報削除</h2>

      <!-- ②確認メッセージ -->
      <p class="text-danger text-center">
        「<c:out value='${subject_name}'/>（<c:out value='${subject_cd}'/>）」を削除してもよろしいですか？
      </p>

      <!-- ⑤ hidden: 科目コード -->
      <!-- ⑥ hidden: 科目名 -->
      <form action="SubjectDeleteExecute.action" method="post" class="text-center">
        <input type="hidden" name="subject_cd" value="${subject_cd}" />
        <input type="hidden" name="subject_name" value="${subject_name}" />

        <!-- ③削除ボタン -->
        <input type="submit" value="削除" class="btn btn-danger" />

        <!-- ④戻るリンク -->
        <div class="mt-3">
          <a href="SubjectList.action" class="btn btn-secondary">戻る</a>
        </div>
      </form>

    </div>
  </c:param>
</c:import>
