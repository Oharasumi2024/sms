package scoremanager.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Teacher;
import bean.Test;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestDao;
import tool.Action;

public class TestRegistExecuteAction extends Action{

    public void execute(HttpServletRequest req, HttpServletResponse res)
            throws Exception {

        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        if (teacher == null) {
            res.sendRedirect("login.jsp");
            return;
        }

        /* ローカル変数の定義 */
        List<Test>testlist = new ArrayList<>();
        TestDao testDao = new TestDao();
        StudentDao studentDao = new StudentDao();
        SubjectDao subjectDao = new SubjectDao();
        Map<String,String> error = new HashMap<>();
        int point = 0;
        String[] regist;
        int count = 0;
        String subject = "";


        /* リクエストパラメータ取得 */
        regist = req.getParameterValues("regist");
        count = Integer.parseInt(req.getParameter("f4"));
        subject = req.getParameter("f3");


        /* 学生番号配列分ループ処理 */
        for (String studentNo : regist) {
            String pointStr = req.getParameter("point_" + studentNo);
            try {
                if (pointStr == null || pointStr.isEmpty()) {
                    point = 0;
                } else {
                    point = Integer.parseInt(pointStr);
                }

                if (point < 0 || point > 100) {
                    throw new NumberFormatException(); // 範囲外も例外として扱う
                }
            } catch (NumberFormatException e) {
                error.put(studentNo, "0～100の範囲で入力してください");
                continue;
            }

            Test test = new Test();
            test.setStudent(studentDao.get(studentNo));
            test.setClassNum(studentDao.get(studentNo).getClassNum());
            test.setSubject(subjectDao.get(subject, teacher.getSchool()));
            test.setSchool(teacher.getSchool());
            test.setNo(count);
            test.setPoint(point);

            testlist.add(test);
        }
        req.setAttribute("errors", error);
        req.setAttribute("f4", count);
        req.setAttribute("f3", subject);

        if (error.isEmpty()) {
            /* TestDaoで得点を保存する */
        	testDao.save(testlist);
        	req.getRequestDispatcher("test_regist_done.jsp").forward(req,res);
        } else {

        	List<Test> testlistTo = testDao.filter(ent_year, ClassNum, subjectDao.get(subject, teacher.getSchool()), count, teacher.getSchool(5));
        	req.setAttribute("testlist", testlistTo);
        	req.getRequestDispatcher("test_regist.jsp").forward(req, res);
        }





}