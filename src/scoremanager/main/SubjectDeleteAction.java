package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.Action;

public class SubjectDeleteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		HttpSession session = req.getSession(); // セッション
		Teacher teacher = (Teacher)session.getAttribute("user");
        String subject_cd = req.getParameter("cd");


		SubjectDao subjectDao = new SubjectDao();
		Subject subject = new Subject();

		subject=subjectDao.get(subject_cd, teacher.getSchool());
		String name=subject.getName();

		req.setAttribute("subject_cd",subject.getCd());
		req.setAttribute("subject_name", name);


        req.getRequestDispatcher("subject_delete.jsp").forward(req, res);
    }
}
