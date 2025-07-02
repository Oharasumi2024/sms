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
				String name=(String)req.getAttribute("name") ; // 科目名

				Subject subject = new Subject();
				SubjectDao subjectDao = new SubjectDao();

				// リクエストパラメーターの取得 2
				cd = req.getParameter("cd");
				String errors=(String)req.getAttribute("errors");


				// DBからデータ取得 3
				// 科目の詳細データを取得
				try{
					subject = subjectDao.get(cd,teacher.getSchool());

					if(subject==null){
						errors="科目が存在しません";
					}else{
						if (name==null || name.isEmpty()){
							name=subject.getName();
						}
					}
				}catch(NullPointerException e){
					e.printStackTrace();
				}

				// リクエストに科目情報をセット
				req.setAttribute("cd", cd);
				// リクエストに氏名をセット
				req.setAttribute("name", name);
				req.setAttribute("errors", errors);

				// JSPへフォワード 7
				req.getRequestDispatcher("subject_update.jsp").forward(req, res);

	}

}
