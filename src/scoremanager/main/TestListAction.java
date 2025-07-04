package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.ClassNumDao;
import dao.SubjectDao;
import tool.Action;

public class TestListAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//セッションの取得
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");

		//ローカル変数の指定
		String entYearStr = ""; // 入力された入学年度
		String classNum = ""; // 入力されたクラス番号
		String subject = ""; // 入力された科目
		int entYear = 0; // 入学年度
		String student_no="";//入力された学生番号
		LocalDate todaysDate = LocalDate.now(); // LocalDateインスタンスを取得
		int year = todaysDate.getYear(); // 現在の年を取得
		ClassNumDao classNumDao = new ClassNumDao(); // クラス番号Daoを初期化
		SubjectDao subjectDao=new SubjectDao();//科目DAOを初期化

		//リクエストパラメーターの取得
		entYearStr=req.getParameter("f1");
		classNum=req.getParameter("f2");
		subject=req.getParameter("f3");
		student_no=req.getParameter("f4");

		if (entYearStr != null) {
			entYear=Integer.parseInt("entYearStr");
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

		List<Subject> list2=subjectDao.filter(teacher.getSchool());




		req.setAttribute("f1", entYear);
		req.setAttribute("f2", classNum);
		req.setAttribute("f3", subject);
		req.setAttribute("f4", student_no);
		req.setAttribute("class_num_set", list);
		req.setAttribute("ent_year_set", entYearSet);
		req.setAttribute("subject_set", list2);

		req.getRequestDispatcher("test_list.jsp").forward(req, res);

		}
	}
