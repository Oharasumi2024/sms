package scoremanager.main;

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

       HttpSession session = req.getSession();
       Teacher teacher = (Teacher) session.getAttribute("user");

        String cd = req.getParameter("cd");
        String name = req.getParameter("name");

        SubjectDao subjectDao = new SubjectDao();
        String errors = "";


        // 科目の存在チェック
            if (subjectDao.get(cd, teacher.getSchool()) == null) {
                errors= "科目が存在しません";
            }else{
                // 科目情報を更新
                Subject subject = new Subject();
                subject.setCd(cd);
                subject.setName(name);
                subject.setSchool(teacher.getSchool());

                subjectDao.save(subject);
            }

            req.setAttribute("cd", cd);
            req.setAttribute("name", name);
            req.setAttribute("errors", errors);


        // 完了画面へ遷移
        	if (errors.isEmpty()){
        req.getRequestDispatcher("subject_update_done.jsp").forward(req, res);
        	}else{
        		req.getRequestDispatcher("SubjectUpdate.action").forward(req, res);
        	}
    }
}
