package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;
import bean.TestListSubject;

public class TestListSubjectDao extends Dao {

	private String BASE_SQL = "select student.ent_year, student.no, student.name, student.class_num, a.no as no1,"
			+ " a.point as point1, b.no as no2, b.point as point2";


    private List<TestListSubject> postFilter(ResultSet rSet) throws Exception {
        List<TestListSubject> list = new ArrayList<>();
        try {
        	//リザルトセットを全権走査
            while (rSet.next()) {
                //インスタンスを初期化
                TestListSubject tls = new TestListSubject();
                //検索結果をセット
                tls.setYear(rSet.getInt("ent_year"));
                tls.setStudentNo(rSet.getString("no"));
                tls.setStudentName(rSet.getString("name"));
                tls.setClassNum(rSet.getString("class_num"));
                tls.putPoint(rSet.getInt("no1"), rSet.getInt("point1"));
                if (rSet.getInt("no2") != 0) {
                	tls.putPoint(rSet.getInt("no2"), rSet.getInt("point2"));
                } else {
                	tls.putPoint(2, -1);
                }

                //リストに追加
                list.add(tls);

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

        String from = " from (select test.student_no, test.subject_cd, test.school_cd, test.no, test.point, test.class_num"
        		+ " from test join student on test.student_no = student.no where student.ent_year = ? and subject_cd = ? "
        		+ "and test.class_num = ? and test.school_cd = ? and test.no = 1 order by test.student_no) as a";

        String condition = " on a.student_no = b.student_no and a.subject_cd = b.subject_cd and a.class_num = b.class_num";

        String join = " left join (select test.student_no, test.subject_cd, test.school_cd, test.no, test.point, test.class_num from test "
        		+ "join student on test.student_no = student.no where student.ent_year = ? "
        		+ "and subject_cd = ? and test.class_num = ? and test.school_cd = ? and test.no = 2 order by test.student_no) as b";

        String join2 = " join student on a.student_no = student.no";

        String order = " order by a.student_no asc, a.no asc";

        try {
            ps = connection.prepareStatement(BASE_SQL + from + join + condition + join2 + order);
            //値をバインド
            ps.setInt(1, entYear);
            ps.setString(2, subject.getCd());
            ps.setString(3, classNum);
            ps.setString(4, school.getCd());
            ps.setInt(5, entYear);
			ps.setString(6, subject.getCd());
			ps.setString(7, classNum);
			ps.setString(8, school.getCd());
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