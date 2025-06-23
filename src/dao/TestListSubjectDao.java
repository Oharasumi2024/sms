package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import bean.School;
import bean.Subject;
import bean.TestListSubject;

public class TestListSubjectDao extends Dao {
    private static final String BASE_SQL =
        "SELECT student_no, student_name, classnum, no, point "
      + "FROM test WHERE ";

    private List<TestListSubject> postFilter(ResultSet rs) throws Exception {
        // student_no をキーに、TestListSubject を一意に保持する Map
        Map<String, TestListSubject> temp = new LinkedHashMap<>();

        while (rs.next()) {
            String stuNo   = rs.getString("student_no");
            TestListSubject tls = temp.get(stuNo);
            if (tls == null) {
                // 初登場の生徒なら bean を作成＆初期化
                tls = new TestListSubject();
                tls.setStudentNo(stuNo);
                tls.setStudentName(rs.getString("student_name"));
                tls.setClassNum(rs.getString("classnum"));
                // Map プロパティも初期化しておく
                tls.setPoints(new LinkedHashMap<>());
                temp.put(stuNo, tls);
            }
            // Map にテスト番号→点数を追加
            int testNo  = rs.getInt("no");
            int point   = rs.getInt("point");
            tls.getPoints().put(testNo, point);
        }
        // Map の値だけをリスト化して返却
        return new ArrayList<>(temp.values());
    }

    public List<TestListSubject> filter(
            int entYear,
            String classNum,
            Subject subject,
            School school) throws Exception {

        String condition =
            "ent_year = ? AND class_num = ? "
          + "AND subject_cd = ? AND school_cd = ? ";
        String order = "ORDER BY student_no";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(
                 BASE_SQL + condition + order)) {

            ps.setInt(1, entYear);
            ps.setString(2, classNum);
            ps.setString(3, subject.getCd());
            ps.setString(4, school.getCd());

            try (ResultSet rs = ps.executeQuery()) {
                return postFilter(rs);
            }
        }
    }
}