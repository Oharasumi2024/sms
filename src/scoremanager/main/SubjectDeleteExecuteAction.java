package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Subject;
import dao.SubjectDao;
import tool.Action;

public class SubjectDeleteExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		String subject_cd = "";
		String subject_name = "";
		Subject subject = new Subject();
		SubjectDao subjectDao = new SubjectDao();

		// リクエストパラメーターの取得 2
		subject_cd = req.getParameter("subject_cd");
		subject_name = req.getParameter("subject_name");

		// DBからデータ取得 3
		// なし

		// ビジネスロジック 4
		//無し
		// subjectに学生情報をセット
		subject.setCd(subject_cd);
		subject.setName(subject_name);

		// 削除を保存
		subjectDao.save(subject);
		// JSPへフォワード 6
		req.getRequestDispatcher("subject_delete_done.jsp").forward(req, res);

	}

}
