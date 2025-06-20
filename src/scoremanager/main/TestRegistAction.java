package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Teacher;
import bean.Test;
import dao.TestDao;
import tool.Action;

public class TestRegistAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//セッション
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");

        // リクエストパラメータの取得
        String student = req.getParameter("student");
        String subject   = req.getParameter("subject");
        String pointStr  = req.getParameter("point");
        int point = 0;
        try {
            point = Integer.parseInt(pointStr);
        } catch (NumberFormatException e) {

            return;
        }

        //  Bean に詰め替え
        Test test = new Test();
        test.setStudent(student);
        test.setSubject(subject);
        test.setPoint(point);
        test.setTeacherNo(teacher.getTeacherNo());

        // 4. DAO を使って登録
        TestDao tDao = new TestDao();



	}

}
