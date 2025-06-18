package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.Student;
import bean.TestListStudent;

public class TestListStudentDao extends Dao {

	private String baseSql="select * from test where ";

	private List<TestListStudent> postFilter(ResultSet rSet)throws Exception{
		// リストを初期化
		List<TestListStudent> list = new ArrayList<>();
		try {
			// リザルトセットを全権走査
			while (rSet.next()) {
				// 学生リストインスタンスを初期化
				TestListStudent testliststudent = new TestListStudent();
				// 学生リストインスタンスに検索結果をセット
				testliststudent.setSubjectCd(rSet.getString("subject_cd"));
				testliststudent.setNum(rSet.getInt("no"));
				testliststudent.setPoint(rSet.getInt("point"));
				// リストに追加
				list.add(testliststudent);
			}
		} catch (SQLException | NullPointerException e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<TestListStudent> filter(Student student)throws Exception{
		//リストを初期化
		List<TestListStudent>list=new ArrayList<>();
		//コネクションを確立
		Connection connection=getConnection();
		//プリパレ
		PreparedStatement statement=null;
		//リザルトセット
		ResultSet rSet=null;
		//条件
		String condition="student_no=? ";
		//ソート
		String order="order by subject_cd";
		try {
			//プリパレにステートメントをセット
			statement=connection.prepareStatement(baseSql+condition+order);
			//学校コードをバインド
			statement.setString(1, student.getNo());
			//プリパレの実行
			rSet=statement.executeQuery();
			//リストへの格納処理を実行
			list=postFilter(rSet);
	}catch (Exception e) {
		throw e;
	}finally {
		// プリペアードステートメントを閉じる
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException sqle) {
				throw sqle;
			}
		}
		// コネクションを閉じる
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException sqle) {
				throw sqle;
			}
		}
	}
	return list;
	}
}
