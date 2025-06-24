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
		String cd = "";
		String name = "";
		Subject subject = new Subject();
		SubjectDao subjectDao = new SubjectDao();
		Map<String, String> errors = new HashMap<>(); // エラーメッセージ

		// リクエストパラメーターの取得 2
		cd = req.getParameter("cd");
		name = req.getParameter("name");

		// DBからデータ取得 3
		// なし



		// ビジネスロジック 4
		// 科目存在チェック
        if (errors.isEmpty()) {
            Subject existingSubject = subjectDao.get(cd, subject.getSchool());
            if (existingSubject == null) {
                errors.put("cd", "指定された科目コードは存在しません");
            }
        }
		if (cd == null || cd.trim().isEmpty()) {
            errors.put("cd", "このフィールドを入力してください");
        }
        if (name == null || name.trim().isEmpty()) {
            errors.put("name", "このフィールドを入力してください");
        }

        if (!errors.isEmpty()) {
            req.setAttribute("errors", errors);
            req.setAttribute("cd", cd);
            req.setAttribute("name", name);
            req.getRequestDispatcher("subject_update.jsp").forward(req, res);
            return;
        }
		// subjectに科目情報をセット
		subject.setCd(cd);
		subject.setName(name);
		subject.setSchool(teacher.getSchool());

		// 変更内容を保存
		subjectDao.save(subject);

		// レスポンス値をセット 6
		// なし

		// JSPへフォワード 7
		req.getRequestDispatcher("subject_update_done.jsp").forward(req, res);

	}

}
