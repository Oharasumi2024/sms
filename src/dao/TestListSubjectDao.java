package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import bean.School;
import bean.Subject;
import bean.TestListSubject;

public class TestListSubjectDao extends Dao {

    private static final String BASE_SQL =
        "SELECT student_no, student_name, classnum, no, point FROM test WHERE ";

    public List<TestListSubject> filter(
            int entYear,
            String classNum,
            Subject subject,
            School school) throws Exception {

        List<TestListSubject> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rSet = null;

        String condition = " ent_year = ? AND class_num = ? AND subject_cd = ? AND school_cd = ? ";
        String order = " ORDER BY student_no";

        try {
            conn = getConnection();
            ps = conn.prepareStatement(BASE_SQL + condition + order);

            ps.setInt(1, entYear);
            ps.setString(2, classNum);
            ps.setString(3, subject.getCd());
            ps.setString(4, school.getCd());

            rSet = ps.executeQuery();
            list = postFilter(rSet);
        } catch (Exception e) {
            throw e;
        } finally {
            if (rSet != null) {
                try { rSet.close(); } catch (SQLException e) { throw e; }
            }
            if (ps != null) {
                try { ps.close(); } catch (SQLException e) { throw e; }
            }
            if (conn != null) {
                try { conn.close(); } catch (SQLException e) { throw e; }
            }
        }

        return list;
    }

    private List<TestListSubject> postFilter(ResultSet rSet) throws Exception {
        Map<String, TestListSubject> map = new LinkedHashMap<>();
        try {
            while (rSet.next()) {
                String stuNo = rSet.getString("student_no");
                TestListSubject tls = map.get(stuNo);
                if (tls == null) {
                    tls = new TestListSubject();
                    tls.setStudentNo(stuNo);
                    tls.setStudentName(rSet.getString("student_name"));
                    tls.setClassNum(rSet.getString("classnum"));
                    tls.setPoints(new LinkedHashMap<>());
                    map.put(stuNo, tls);
                }
                int testNo = rSet.getInt("no");
                int point = rSet.getInt("point");
                tls.getPoints().put(testNo, point);
            }
        } catch (SQLException | NullPointerException e) {
            throw e;
        }
        return new ArrayList<>(map.values());
    }
}