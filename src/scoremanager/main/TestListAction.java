package scoremanager.main;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Student;
import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;
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
		List<Student> students = null; // 学生リスト
		LocalDate todaysDate = LocalDate.now(); // LocalDateインスタンスを取得
		int year = todaysDate.getYear(); // 現在の年を取得
		StudentDao studentDao = new StudentDao(); // 学生Dao
		ClassNumDao classNumDao = new ClassNumDao(); // クラス番号Daoを初期化
		SubjectDao subjectDao=new SubjectDao();//科目DAOを初期化
		Map<String, String> errors = new HashMap<>(); // エラーメッセージ


	}

}
