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
public class SubjectCreateExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		// ローカル変数の指定 1
		HttpSession session = req.getSession(); // セッション
		Teacher teacher = (Teacher)session.getAttribute("user");
		String subject_cd = "cd"; // 科目コード
		String subject_name = "name"; // 科目名
		Subject subject = new Subject();
		SubjectDao subjectDao = new SubjectDao();
		Map<String, String> errors = new HashMap<>();

		// リクエストパラメーターの取得 2
		subject_cd = req.getParameter("cd");
		subject_name = req.getParameter("name");

		if (subject_cd.length() != 3) {
		    errors.put("1", "科目コードは3文字で入力してください");
			// リクエストにエラーメッセージをセット
			req.setAttribute("errors", errors);
		} else {
			if (subjectDao.get(subject_cd, teacher.getSchool()) != null) { // 科目コードが重複している場合
				errors.put("2", "科目コードが重複しています");
				// リクエストにエラーメッセージをセット
				req.setAttribute("errors", errors);
			} else {
				// subjectに学生情報をセット
				subject.setCd(subject_cd);
				subject.setName(subject_name);
				subject.setSchool(teacher.getSchool());
						// saveメソッドで情報を登録
				subjectDao.save(subject);

			}
		}

		// リクエストに科目コードをセット
		req.setAttribute("cd", subject_cd);
		// リクエストに科目名をセット
		req.setAttribute("name", subject_name);

		if (errors.isEmpty()) { // エラーメッセージがない場合
			// 登録完了画面にフォワード
			req.getRequestDispatcher("subject_create_done.jsp").forward(req, res);
		} else { // エラーメッセージがある場合
			// 登録画面にフォワード
			req.getRequestDispatcher("SubjectCreate.action").forward(req, res);
		}

	}
}
