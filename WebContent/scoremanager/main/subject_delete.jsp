<%-- 科目削除変更JSP --%>More actions
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/common/base.jsp">
  <c:param name="title">得点管理システム</c:param>
  <c:param name="content">
    <div id="wrap_box">
      <!-- 画面タイトル -->
      <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2">科目情報削除</h2>

      <!-- 確認メッセージ -->
      <label class="text-center">
        <p>「${subject_name}(${subject_cd}）」を削除してもよろしいですか？</p>
      </label>

	  <form action="SubjectDeleteExecute.action" method="get">
      <!-- 削除ボタン -->
        <div>
        	<input type="submit" value="削除" class="btn btn-danger" />
        </div>

        <!-- 戻るリンク -->
        <div class="mt-3">
          <a href="SubjectList.action">戻る</a>
        </div>

      <!--  hidden: 科目コード -->
      <!--  hidden: 科目名 -->
        <input type="hidden" name="subject_cd" value="${subject_cd}" />
        <input type="hidden" name="subject_name" value="${subject_name}" />


      </form>

    </div>
  </c:param>
</c:import>
