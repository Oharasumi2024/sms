package scoremanager.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestDao;

public class TestRegistExecuteAction {

    public void execute(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        if (teacher == null) {
            res.sendRedirect("login.jsp");
            return;
        }

        String actionType = req.getParameter("action");
        School school = teacher.getSchool();

        if ("register".equals(actionType)) {
            String subjectCd = req.getParameter("subject_cd");
            String classNum = req.getParameter("class_num");
            String testName = req.getParameter("test_name");
            String testDate = req.getParameter("test_date");
            String pointStr = req.getParameter("point");

            List<String> errors = new ArrayList<>();
            int point = Integer.parseInt(pointStr);
            if (point < 0 || point > 100) {
                errors.add("0～100の範囲で入力してください");
            }

            if (!errors.isEmpty()) {
                req.setAttribute("errors", errors);
                req.setAttribute("subject_cd", subjectCd);
                req.setAttribute("class_num", classNum);
                req.setAttribute("test_name", testName);
                req.setAttribute("test_date", testDate);
                req.setAttribute("point", point);
                RequestDispatcher dispatcher = req.getRequestDispatcher("test_regist.jsp");
                dispatcher.forward(req, res);
                return;
            }

            SubjectDao subjectDao = new SubjectDao();
            Subject subject = subjectDao.get(subjectCd, school);

            Test test = new Test();
            test.setSchool(school);
            test.setSubject(subject);
            test.setClassNum(classNum);
            test.setPoint(point);

            TestDao testDao = new TestDao();
            boolean ok = testDao.save(java.util.Collections.singletonList(test));


            req.setAttribute("subject_cd", subjectCd);
            req.setAttribute("class_num", classNum);
            req.setAttribute("test_name", testName);
            req.setAttribute("test_date", testDate);
            req.setAttribute("point", pointStr);
            RequestDispatcher dispatcher = req.getRequestDispatcher("test_regist.jsp");
            dispatcher.forward(req, res);

        } else if ("reference".equals(actionType)) {
        	 	String studentNo = req.getParameter("student_no");
        	    String subjectCd = req.getParameter("subject_cd");
        	    StudentDao studentDao = new StudentDao();
        	    SubjectDao subjectDao = new SubjectDao();
        	    TestDao testDao = new TestDao();

        	    Student student = studentDao.get(studentNo);
        	    Subject subject = subjectDao.get(subjectCd, school);


        	    List<Test> testList = testDao.filter(
        	        student.getEntYear(),
        	        student.getClassNum(),
        	        subject,
        	        student.getNo(),
        	        school
        	    );

        	    req.setAttribute("test_list", testList);
        	    req.setAttribute("student", student);
        	    RequestDispatcher dispatcher = req.getRequestDispatcher("test_regist_regist.jsp");
        	    dispatcher.forward(req, res);

        }
    }
}