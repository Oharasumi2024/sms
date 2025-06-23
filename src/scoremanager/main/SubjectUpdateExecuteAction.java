package scoremanager.main;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.Action;

public class SubjectUpdateExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		// ローカル変数の指定 1
		HttpSession session = req.getSession(); // セッション
		Teacher teacher = (Teacher)session.getAttribute("user");
		String subject_cd = "";
		String subject_name = "";
		Subject subject = new Subject();
		SubjectDao subjectDao = new SubjectDao();
		Map<String, String> errors = new HashMap<>(); // エラーメッセージ

		// リクエストパラメーターの取得 2
		subject_cd = req.getParameter("cd");
		subject_name = req.getParameter("name");

		// DBからデータ取得 3
		// なし

		// ビジネスロジック 4
		// 科目存在チェック
        if (errors.isEmpty()) {
            Subject existingSubject = subjectDao.get(subject_cd, subject.getSchool());
            if (existingSubject == null) {
                errors.put("cd", "指定された科目コードは存在しません");
            }
        }
		if (subject_cd == null || subject_cd.trim().isEmpty()) {
            errors.put("cd", "このフィールドを入力してください");
        }
        if (subject_name == null || subject_name.trim().isEmpty()) {
            errors.put("name", "このフィールドを入力してください");
        }

        if (!errors.isEmpty()) {
            req.setAttribute("errors", errors);
            req.setAttribute("cd", subject_cd);
            req.setAttribute("name", subject_name);
            req.getRequestDispatcher("subject_update.jsp").forward(req, res);
            return;
        }
		// subjectに科目情報をセット
		subject.setCd(subject_cd);
		subject.setName(subject_name);
		subject.setSchool(teacher.getSchool());

		// 変更内容を保存
		subjectDao.save(subject);

		// レスポンス値をセット 6
		// なし

		// JSPへフォワード 7
		req.getRequestDispatcher("subject_update_done.jsp").forward(req, res);

	}

}
