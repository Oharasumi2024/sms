package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.ClassNumDao;
import dao.SubjectDao;
import dao.TestDao;
import tool.Action;

public class TestRegistAction extends Action{

    public void execute(HttpServletRequest req, HttpServletResponse res)
            throws Exception {

        // セッション／ログインチェック
        HttpSession session = req.getSession(false);
        Teacher teacher = (Teacher)session.getAttribute("user");

        //ローカル変数の指定
        String entYearStr = null; //入力された
        int ent_year = 0;
        String ClassNum = "";
        String subject = "";
        String countStr = null;
        int count = 0;
        ClassNumDao classnumDao = new ClassNumDao();
        SubjectDao subjectDao = new SubjectDao();
        TestDao testDao = new TestDao();
        LocalDate todaysDate = LocalDate.now(); // LocalDateインスタンスを取得
		int year = todaysDate.getYear(); // 現在の年を取得
		Map<String, String> errors = new HashMap<>(); //エラー
        School school = teacher.getSchool();
        String searchParam = req.getParameter("search");
        boolean didSearch = "true".equals(searchParam);


        //  リクエストパラメータ取得
        entYearStr = req.getParameter("f1");
		ClassNum = req.getParameter("f2");
		subject = req.getParameter("f3");
		countStr = req.getParameter("f4");

		//DBから取得
		List<String>class_num_list = classnumDao.filter(school); //クラス情報
		List<Subject>subject_list = subjectDao.filter(school);

		if (entYearStr != null) {
			ent_year = Integer.parseInt(entYearStr);
		}
		if (countStr != null) {
			count = Integer.parseInt(countStr);
		}

		// リストを初期化
		List<Integer> entYearSet = new ArrayList<>();
		// 10年前から10年後まで年をリストに追加
		for (int i = year - 10; i < year + 11; i++) {
			entYearSet.add(i);
		}

		// リストを初期化
		List<Integer> countSet = new ArrayList<>();
		// 2回までセット
		for (int i = 1; i < 3 ; i++) {
			countSet.add(i);
		}

		// 検索
		if (didSearch) {
		    boolean isValid =
		        ent_year != 0 &&
		        ClassNum != null && !ClassNum.equals("0") &&
		        subject != null && !subject.equals("0") &&
		        count != 0;

		    if (isValid) {
		        // 検索処理
		        List<Test> testlist = testDao.filter(ent_year, ClassNum, subjectDao.get(subject, school), count, school);
		        String subject_name = subjectDao.get(subject, school).getName();
		        req.setAttribute("testlist", testlist);
		        req.setAttribute("subject_name", subject_name);
		    } else {
		        errors.put("e1", "入学年度とクラスと科目と回数を選択してください");
		        req.setAttribute("errors", errors);
		    }
		}


				//レスポンス値をセット6
				//リクエストに値をセット
				req.setAttribute("f1", ent_year);
				req.setAttribute("f2", ClassNum);
				req.setAttribute("f3", subject);
				req.setAttribute("f4", count);
				req.setAttribute("entYear", entYearSet);
				req.setAttribute("class_num_list", class_num_list);
				req.setAttribute("subject_list", subject_list);
				req.setAttribute("countList", countSet);

        req.getRequestDispatcher("test_regist.jsp").forward(req, res);
    }
}