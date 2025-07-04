package scoremanager.main;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
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
            throws ServletException, IOException {

        // セッション／ログインチェック
        HttpSession session = req.getSession(false);
        Teacher teacher = (Teacher)session.getAttribute("user");

        //ローカル変数の指定
        String entYearStr = "";
        String ClassNum = "";
        String subject = "";
        String test_times = "";
        LocalDate todaysDate = LocalDate.now(); // LocalDateインスタンスを取得
		int year = todaysDate.getYear(); // 現在の年を取得

        School school = teacher.getSchool();

        //  リクエストパラメータ取得
        entYearStr = req.getParameter("f1");
		ClassNum = req.getParameter("f2");
		subject = req.getParameter("f3");
		test_times = req.getParameter("f4");

        List<String> errors = new ArrayList<>();
		// リストを初期化
		List<Integer> entYearSet = new ArrayList<>();
		// 10年前から1年後まで年をリストに追加
		for (int i = year - 10; i < year + 1; i++) {
			entYearSet.add(i);
		}


        if (entYearStr == null || entYearStr.isEmpty()
         || ClassNum == null || ClassNum.isEmpty()
         || subject == null || subject.isEmpty()
         || test_times == null || test_times.isEmpty()) {
            errors.add("入学年度とクラスと科目と回数を選択してください");
        }

        try {
            List<Subject> subjectList = new SubjectDao().filter(school);
            List<String> classNumList = new ClassNumDao().filter(school);
            req.setAttribute("subject_list", subjectList);
            req.setAttribute("class_num_list", classNumList);
        } catch (Exception e) {
            errors.add("データの取得に失敗しました");
        }

        //  入力済み値をセット
        req.setAttribute("ent_year", entYearStr);
        req.setAttribute("class_num", ClassNum);
        req.setAttribute("subject_id", subject);
        req.setAttribute("test_times", test_times);

        //  成績データ取得（パラメータが揃っていれば）
        if (errors.isEmpty()) {
            try {
                int ent_year = Integer.parseInt(entYearStr);
                String classNum = ClassNum;
                String subjectCd = subject;
                int times = Integer.parseInt(test_times);

                Subject subj = new Subject();
                subj.setCd(subjectCd);

                TestDao testDao = new TestDao();
                List<Test> testList = testDao.filter(ent_year, classNum, subj, times, school);
                req.setAttribute("test_list", testList);

            } catch (NumberFormatException nfe) {
            } catch (Exception ex) {
                errors.add("既存の成績データ取得に失敗しました");
            }
        }

        // エラー or 次画面へフォワード
        if (!errors.isEmpty()) {
            req.setAttribute("errors", errors);
        }

        req.getRequestDispatcher("test_regist.jsp").forward(req, res);
    }
}