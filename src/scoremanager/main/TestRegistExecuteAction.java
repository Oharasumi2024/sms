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
        count = Integer.parseInt(req.getParameter("count"));
        subject = req.getParameter("subject");


        /* 学生番号配列分ループ処理 */
        for (String studentNo: regist){

        	/* 学生一人分のリクエストパラメータの取得 */

        	if (req.getParameter("point_" + studentNo).isEmpty()){
        		point = 0;
        	}else {
        		point = Integer.parseInt(req.getParameter("point_" + studentNo));
        	}
        	/* 得点のチェック */
        	if (point < 0 || point > 100) {
                error.put(studentNo,"0～100の範囲で入力してください");
                req.setAttribute("error", error);
                break;
            }
        	else{

            	/* TestBeanを1人分作成して、リストに追加する */
        		Test test = new Test();

        		test.setStudent(studentDao.get(studentNo));
        		test.setClassNum(studentDao.get(studentNo).getClassNum());
        		test.setSubject(subjectDao.get(subject, teacher.getSchool()));
        		test.setSchool(teacher.getSchool());
        		test.setNo(count);
        		test.setPoint(point);

        		testlist.add(test);
        	}
        }

        if (error.isEmpty()) {
            /* TestDaoで得点を保存する */
        	testDao.save(testlist);
        	req.getRequestDispatcher("test_regist_done.jsp").forward(req,res);
        } else {
        	req.getRequestDispatcher("TestRegist.action").forward(req, res);
        }





/*

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
*/

    }
}