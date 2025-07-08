package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Student;
import bean.Teacher;
import bean.TestListStudent;
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestListStudentDao;
import tool.Action;

public class TestListStudentExecuteAction extends Action {


	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//セッションの取得
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");

		//ロ数の指
		String entYearStr = ""; // 入力された入学年度
		String classNum = ""; // 入力されたクラス番号
		String subjectcd = ""; // 入力された科目
		Student student=new Student();
		int entYear = 0; // 入学年度
		String student_no="";//入力された学生番号
		List<Student> subjects = null; // 学生リスト
		TestListStudentDao testliststudentdao=new TestListStudentDao();
		LocalDate todaysDate = LocalDate.now(); // LocalDateインスタンスを取得
		List<TestListStudent> testliststudent=new ArrayList<>();
		int year = todaysDate.getYear(); // 現在の年を取得
		StudentDao studentDao = new StudentDao(); // 学生Dao
		ClassNumDao classNumDao = new ClassNumDao(); // クラス番号Daoを初期化
		SubjectDao subjectDao=new SubjectDao();//科目DAOを初期化
		Map<String, String> errors = new HashMap<>(); // エラーメッセージ
		boolean isAttend=false;//在学フラグ

		String searchType=req.getParameter("f");




		//リクエストパラメーターの取得
		entYearStr=req.getParameter("f1");
		classNum=req.getParameter("f2");
		subjectcd=req.getParameter("f3");
		student_no=req.getParameter("f4");


		if (entYearStr != null) {
			entYear=Integer.parseInt(entYearStr);
		}
		// リストを初期化
		List<Integer> entYearSet = new ArrayList<>();
		// 10年前から1年後まで年をリストに追加

		for (int i = year - 10; i < year + 1; i++) {
			entYearSet.add(i);
		}
		// DBからデータ取得 3
		// ログインユーザーの学校コードをもとにクラス番号の一覧を取得
		List<String> list = classNumDao.filter(teacher.getSchool());


		List<Student> list2=studentDao.filter(teacher.getSchool(),false);


		student = studentDao.get(student_no);

       testliststudent = testliststudentdao.filter(student);




		req.setAttribute("f1", entYear);
		req.setAttribute("f2", classNum);
		req.setAttribute("f3", subjectcd);
		req.setAttribute("f4", student_no);
		req.setAttribute("class_num_set", list);
		req.setAttribute("ent_year_set", entYearSet);
		req.setAttribute("subject_set", list2);
        req.setAttribute("test_list",testliststudent);
        req.setAttribute("searchType", searchType);
        req.setAttribute("student",student);
		req.getRequestDispatcher("test_list_student.jsp").forward(req, res);


}
}