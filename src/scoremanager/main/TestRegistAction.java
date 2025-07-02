package scoremanager.main;

import java.io.IOException;
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

public class TestRegistAction {

    public void execute(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        // 1. セッション／ログインチェック
        HttpSession session = req.getSession(false);
        Teacher teacher = (session != null)
            ? (Teacher) session.getAttribute("user")
            : null;
        if (teacher == null) {
            res.sendRedirect("login.jsp");
            return;
        }
        School school = teacher.getSchool();

        // 2. リクエストパラメータ取得
        String ent_year = req.getParameter("ent_year"); // 入学年度
        String class_num = req.getParameter("class_num"); // クラス
        String subject = req.getParameter("subject"); // 科目
        String test_times = req.getParameter("test_times"); // 回数

        List<String> errors = new ArrayList<>();

        // 3. 入力チェック：いずれか未選択／未入力なら共通メッセージ
        if (ent_year == null || ent_year.isEmpty()
         || class_num == null || class_num.isEmpty()
         || subject == null || subject.isEmpty()
         || test_times == null || test_times.isEmpty()) {
            errors.add("入学年度とクラスと科目と回数を選択してください");
        }

        // 4. 再表示用リスト取得（必ず）
        try {
            List<Subject> subjectList = new SubjectDao().filter(school);
            List<String> classNumList = new ClassNumDao().filter(school);
            req.setAttribute("subject_list", subjectList);
            req.setAttribute("class_num_list", classNumList);
        } catch (Exception e) {
            errors.add("データの取得に失敗しました");
        }

        // 5. 入力済み値をセット
        req.setAttribute("entrance_year", ent_year);
        req.setAttribute("class_num", class_num);
        req.setAttribute("subject_id", subject);
        req.setAttribute("test_times", test_times);

        // 6. 成績データ取得（パラメータが揃っていれば）
        if (errors.isEmpty()) {
            try {
                int year = Integer.parseInt(ent_year);
                String classNum = class_num;
                String subjectCd = subject; // リクエストパラメータ（科目コード）
                int times = Integer.parseInt(test_times);

                Subject subj = new Subject();
                subj.setCd(subjectCd); // ← ここがポイント！

                TestDao testDao = new TestDao();
                List<Test> testList = testDao.filter(year, classNum, subj, times, school);
                req.setAttribute("test_list", testList);

            } catch (NumberFormatException nfe) {
                // 数値変換エラーは無視（入力チェックで対応）
            } catch (Exception ex) {
                errors.add("既存の成績データ取得に失敗しました");
            }
        }

        // 7. エラー or 次画面へフォワード
        if (!errors.isEmpty()) {
            req.setAttribute("errors", errors);
        }

        req.getRequestDispatcher("test_regist.jsp").forward(req, res);
    }
}