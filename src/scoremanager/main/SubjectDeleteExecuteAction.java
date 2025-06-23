package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Subject;
import dao.SubjectDao;
import tool.Action;

public class SubjectDeleteExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		 String subject_cd = req.getParameter("subject_cd");
	        String subject_name = req.getParameter("subject_name");

	        // 空チェック
	        if (subject_cd == null || subject_cd.isEmpty()) {
	            // エラー処理（例: エラーメッセージを設定して戻る）
	            req.setAttribute("error", "科目コードが入力されていません。");
	            req.getRequestDispatcher("subject_delete.jsp").forward(req, res);
	            return;
	        }

	        // Subjectオブジェクトにセット
	        Subject subject = new Subject();
	        subject.setCd(subject_cd);
	        subject.setName(subject_name);

	        // SubjectDaoで削除処理
	        SubjectDao subjectDao = new SubjectDao();
	        subjectDao.delete(subject); // deleteメソッドを仮定
		// JSPへフォワード
		req.getRequestDispatcher("subject_delete_done.jsp").forward(req, res);

	}

}
