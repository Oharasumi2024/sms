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
        String subject_cd = req.getParameter("subject_cd");

        if (subject_cd != null && !subject_cd.isEmpty()) {
            HttpSession session = req.getSession();
            School school = (School) session.getAttribute("school");

            if (school != null) {
                SubjectDao subjectDao = new SubjectDao();
                Subject subject = subjectDao.get(subject_cd, school);

                if (subject != null) {
                    req.setAttribute("subject_cd", subject.getCd());
                    req.setAttribute("subject_name", subject.getName());
                } else {
                    req.setAttribute("error", "指定された科目コードは存在しません。");
                }
            } else {
                req.setAttribute("error", "学校情報が取得できませんでした。");
            }
        }
        req.getRequestDispatcher("subject_delete.jsp").forward(req, res);
    }
}
