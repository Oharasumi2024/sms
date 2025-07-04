package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import bean.School;
import bean.Subject;
import bean.TestListSubject;

public class TestListSubjectDao extends Dao {

	private static final String BASE_SQL =
			  "SELECT test.student_no, student.name, test.subject_cd, test.school_cd, test.no, test.point, test.class_num, student.ent_year from test "
			  + "join student on test.student_no = student.no where test.school_cd = ?";

    private List<TestListSubject> postFilter(ResultSet rSet) throws Exception {
        List<TestListSubject> list = new ArrayList<>();
        try {
        	//リザルトセットを全権走査
            while (rSet.next()) {
                String stuNo = rSet.getString("student_no");
                TestListSubject tls = new TestListSubject();
                	//インスタンスを初期化
                    tls = new TestListSubject();
                    tls.setStudentNo(stuNo);
                    tls.setStudentName(rSet.getString("name"));
                    tls.setClassNum(rSet.getString("classnum"));
                    tls.setPoints(new LinkedHashMap<>());
                    //リストに追加
                    list.add(tls);

                int testNo = rSet.getInt("no");
                int point = rSet.getInt("point");
                tls.getPoints().put(testNo, point);
            }
        } catch (SQLException | NullPointerException e) {
            throw e;
        }
        return list;
    }

    public List<TestListSubject> filter(int entYear, String classNum, Subject subject, School school) throws Exception {

        List<TestListSubject> list = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement ps = null;
        ResultSet rSet = null;

        String condition =
        	"and student.ent_year = ? and test.class_num = ? and test.subject_cd = ?";

        /*String condition = "ent_year = ? And class_num = ? AND subject_cd = ? AND school_cd = ? ";*/
        String order = " ORDER BY subject_cd";

        try {
            ps = connection.prepareStatement(BASE_SQL + condition + order);
            //値をバインド
            ps.setInt(1, entYear);
            ps.setString(2, classNum);
            ps.setString(3, subject.getCd());
            ps.setString(4, school.getCd());
            //psの実行
            rSet = ps.executeQuery();
            list = postFilter(rSet);
        } catch (Exception e) {
            throw e;
        }finally {
    		// プリペアードステートメントを閉じる
    		if (ps != null) {
    			try {
    				ps.close();
    			} catch (SQLException sqle) {
    				throw sqle;
    			}
    		}
    		// コネクションを閉じる
    		if (ps != null) {
    			try {
    				ps.close();
    			} catch (SQLException sqle) {
    				throw sqle;
    			}
    		}
    	}
    	return list;
    	}
}