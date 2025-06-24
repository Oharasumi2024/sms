package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.Action;

public class SubjectDeleteExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        String subject_cd = req.getParameter("subject_cd");

        if (subject_cd == null || subject_cd.isEmpty()) {
            req.setAttribute("error", "科目コードが入力されていません。");
            req.getRequestDispatcher("subject_delete.jsp").forward(req, res);
            return;
        }

        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        if (teacher == null) {
            req.setAttribute("error", "ログイン情報が取得できませんでした。");
            req.getRequestDispatcher("subject_delete.jsp").forward(req, res);
            return;
        }

        SubjectDao subjectDao = new SubjectDao();
        Subject subject = subjectDao.get(subject_cd, teacher.getSchool());

        if (subject == null) {
            req.setAttribute("error", "削除対象の科目が見つかりません。");
            req.getRequestDispatcher("subject_delete.jsp").forward(req, res);
            return;
        }

        subjectDao.delete(subject);
        req.setAttribute("subject_name", subject.getName());
        req.getRequestDispatcher("subject_delete_done.jsp").forward(req, res);
    }
}
