package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import javax.security.auth.Subject;

import bean.School;
import bean.Student;
import bean.Test;

public class TestDao extends Dao {

	private String baseSql = "select * from test where schoo_cd = ?";

	public Test get(Student student, Subject subject, School school, int no ){

		//テストインスタンスを初期化
		Test test = new Test();
		//データベースへのコネクションを確率
		Connection connection = getConnection();
		//プリペアードステートメント
		PreparedStatement statement = null;

		try{
			//プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement(
					"select * from test where point = ?");
			//プリペアードステートメントに学生番号をバインド
			statement.setString(1, no);
			//プリペアードステートメントを実行
			ResultSet resultSet = statement.executeQuery();

		}
	}

	private List<Test> postFilter(ResultSet resultSet, School school)throws Exception{

		//リストを初期化
		List<Test> list = new ArrayList<>();

	}

	public List<Test> filter(int entYear, String classNum, Subject subject, int num, School school) throws Exception {

	}

	public boolean save(List<Test> list) throws Exception {

	}

	public boolean save(Test test, Connection connection) {

	}

}
