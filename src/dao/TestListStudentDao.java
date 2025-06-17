package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;

public class TestListStudentDao extends Dao {

	private String baseSql="select * from subject where ";

	private List<TestListStudent> postFilter(ResultSet rSet)throws Exception{
		// リストを初期化
		List<TestListStudent> list = new ArrayList<>();
		try {
			// リザルトセットを全権走査
			while (rSet.next()) {
				// 学生リストインスタンスを初期化
				TestListStudent testliststudent = new TestListStudent();
				// 学生リストインスタンスに検索結果をセット
				testliststudent.setSubjectName(rSet.getString("subjectName"));
				testliststudent.setSubjectCd(rSet.getString("subjectCd"));
				testliststudent.setNum(rSet.getInt("num"));
				testliststudent.setPoint(rSet.getInt("point"));
				// リストに追加
				list.add(testliststudent);
			}
		} catch (SQLException | NullPointerException e) {
			e.printStackTrace();
		}
		return list;
	}
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
		String condition="school_cd=? ";
		//ソート
		String order="order by cd";
		try {
			//プリパレにステートメントをセット
			statement=connection.prepareStatement(baseSql+condition+order);
			//学校コードをバインド
			statement.setString(1, student.getSchool().getCd());
			//プリパレの実行
			rSet=statement.executeQuery();
			//リストへの格納処理を実行


	}
}
