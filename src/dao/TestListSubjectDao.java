package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.School;
import bean.Subject;
import bean.TestListSubject;

public class TestListSubjectDao extends Dao {
    // ベースとなる SELECT 文
    private String baseSql = "select * from test where";
    private List<TestListSubject> postFilter(ResultSet resultSet) throws Exception {
    	//リストを初期化
        List<TestListSubject> list = new ArrayList<>();
        try {
        	//MAPを作成
        	Map<Integer,Integer> points = new HashMap<>();
        	//リザルトセットを全権走査
        	while (resultSet.next()) {
        	//インスタンスを初期化
            TestListSubject tls = new TestListSubject();
            //tlsインスタンスに検索結果をセット
            tls.setStudentNo(resultSet.getString("student_no"));
            tls.setStudentName(resultSet.getString("student_name"));
            tls.setClassNum(resultSet.getString("classnum"));
            tls.setPoints(resultSet.getInt("point"));
            //リストに追加
            list.add(tls);
        	}
        } catch (SQLException | NullPointerException e) {
        		e.printStackTrace();
        }
        return list;
    }

    public List<TestListSubject> filter(int entYear, String classNum, Subject subject, School school) throws Exception {
        //リストを初期化
    	List<TestListSubject> list = new ArrayList<>();
    	//コネクションを確立
    	Connection connection = getConnection();
        String condition = "ent_year = ? AND class_num = ? "
                         + "AND subject_cd = ? AND school_cd = ? ";
        String order     = "ORDER BY student_no";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(baseSql + condition + order)) {

            ps.setInt(1, entYear);
            ps.setString(2, classNum);
            ps.setString(3, subject.getCd());
            ps.setString(4, school.getCd());

            try (ResultSet rs = ps.executeQuery()) {
                list = postFilter(rs);
            }
        }
        return list;
    }
}