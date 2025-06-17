package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.Action;

public class SubjectUpdateAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// ローカル変数の指定 1
				HttpSession session = req.getSession(); // セッション
				Teacher teacher = (Teacher)session.getAttribute("user");

				String cd = ""; // 科目コード
				String name= ""; // 科目名

				Subject subject = new Subject();
				SubjectDao subjectDao = new SubjectDao();

				// リクエストパラメーターの取得 2
				cd = req.getParameter("cd");
				

				// DBからデータ取得 3
				// 学生の詳細データを取得
				subject = subjectDao.get(cd);
				

				// ビジネスロジック 4
				name = subject.getName();

				// レスポンス値をセット 6
				// リクエストに学生番号をセット
				req.setAttribute("cd", cd);
				// リクエストに氏名をセット
				req.setAttribute("name", name);

				// JSPへフォワード 7
				req.getRequestDispatcher("subject_update.jsp").forward(req, res);

	}

}
