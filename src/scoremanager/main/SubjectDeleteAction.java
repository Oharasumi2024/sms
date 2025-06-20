package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Subject;
import dao.SubjectDao;
import tool.Action;

public class SubjectDeleteAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// リクエストパラメーターの取得（科目コード）
        String subject_cd = req.getParameter("subject_cd");

        if (subject_cd != null && !subject_cd.isEmpty()) {
            // セッションからSchoolを取得（ログイン中の学校）
            HttpSession session = req.getSession();
            School school = (School) session.getAttribute("school"); // schoolがセッションに保存されている前提

            if (school != null) {
                // SubjectDaoで対象データを取得
                SubjectDao subjectDao = new SubjectDao();
                Subject subject = subjectDao.get(subject_cd, school);

                if (subject != null) {
                    // 取得したデータをリクエストにセット
                    req.setAttribute("subject_cd", subject.getCd());
                    req.setAttribute("subject_name", subject.getName());
                } else {
                    // 該当データがない場合のエラー処理
                    req.setAttribute("error", "指定された科目コードは存在しません。");
                }
            } else {
                // Schoolが取得できない場合のエラー処理
                req.setAttribute("error", "学校情報が取得できませんでした。");
            }
        }
        // JSPにフォワード
        req.getRequestDispatcher("subject_delete.jsp").forward(req, res);

	}

}
