package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Test;

public class TestDao extends Dao {

	private String baseSql = "select * from test where school_cd = ?";

	public Test get(Student student, Subject subject, School school, int no ) throws Exception{

		//テストインスタンスを初期化
		Test test = new Test();
		//データベースへのコネクションを確率
		Connection connection = getConnection();
		//プリペアードステートメント(データの取得)
		PreparedStatement statement = null;

		try{
			//プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement(
					"select * from test where no = ?");
			//値をバインド
			statement.setInt(1, no);
			//プリペアードステートメントを実行
			ResultSet resultSet = statement.executeQuery();

			//結果が存在すれば値をセット
			if (resultSet.next()) {
				test.setNo(resultSet.getInt("no"));
				test.setStudent(student);
				test.setSubject(subject);
				test.setPoint(resultSet.getInt("point"));
				test.setSchool(school);
			} else { //結果がなければnull
				test = null;
			}
		} catch(Exception e){
			throw e;
		}finally {
			//プリペアードステートメントとコネクションを閉じる
			if (statement != null) {
				try{
					statement.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
			if (connection != null){
				try {
					connection.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
		}
		return test;
	}

	public List<Test> postFilter(ResultSet resultSet, School school)throws Exception{

		//リストを初期化
		List<Test> list = new ArrayList<>();
		try {
			//リザルトセットを全権走査
			while (resultSet.next()) {
				//テストインスタンスを初期化
				Test test = new Test();
				//テストインスタンスに検索結果をセット
				test.setNo(resultSet.getInt("no"));
				test.setPoint(resultSet.getInt("point"));
				test.setSchool(school);
				//リストに追加
				list.add(test);
			}
		} catch (SQLException | NullPointerException e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<Test> filter(int entYear, String classNum, Subject subject, int num, School school) throws Exception {
	    List<Test> list = new ArrayList<>();
	    Connection connection = getConnection();
	    PreparedStatement statement = null;
	    ResultSet resultSet = null;

	    // SQL構築
	    StringBuilder sql = new StringBuilder(baseSql); // baseSql = "select * from test where schoo_cd = ?"
	    List<Object> params = new ArrayList<>();

	    sql.append(" and ent_year = ?");
	    params.add(entYear);

	    sql.append(" and class_num = ?");
	    params.add(classNum);

	    sql.append(" and subject_cd = ?");
	    params.add(subject.getCd());

	    sql.append(" and num = ?");
	    params.add(num);

	    sql.append(" order by no asc");

	    try {
	        statement = connection.prepareStatement(sql.toString());
	        statement.setString(1, school.getCd()); // school_cd は必須
	        for (int i = 0; i < params.size(); i++) {
	            Object value = params.get(i);
	            if (value instanceof String) {
	                statement.setString(i + 2, (String) value); // index+2 because 1 is already used
	            } else if (value instanceof Integer) {
	                statement.setInt(i + 2, (Integer) value);
	            }
	        }

	        resultSet = statement.executeQuery();
	        list = postFilter(resultSet, school);

	    } catch (Exception e) {
	        throw e;
	    } finally {
	        if (statement != null) try { statement.close(); } catch (SQLException sqle) { throw sqle; }
	        if (connection != null) try { connection.close(); } catch (SQLException sqle) { throw sqle; }
	    }

	    return list;
	}

	public boolean save(List<Test> list) throws Exception {
		 Connection connection = getConnection();
		    PreparedStatement statement = null;
		    int count = 0;

		    try {
		        for (Test test : list) {
		            // 既存のデータがあるか確認
		            Test old = get(test.getStudent(), test.getSubject(), test.getSchool(), test.getNo());
		            if (old == null) {
		                // データが存在しない場合はINSERT
		                statement = connection.prepareStatement(
		                    "INSERT INTO test(no, point, date, student_no, subject_cd, school_cd) VALUES(?, ?, ?, ?, ?, ?)"
		                );
		                statement.setInt(1, test.getNo());
		                statement.setInt(2, test.getPoint());
		                statement.setString(3, test.getStudent().getNo());
		                statement.setString(5, test.getSubject().getCd());
		                statement.setString(6, test.getSchool().getCd());
		            } else {
		                // 既存のデータがある場合はUPDATE
		                statement = connection.prepareStatement(
		                    "UPDATE test SET point = ?, date = ? WHERE no = ?"
		                );
		                statement.setInt(1, test.getPoint());
		                statement.setInt(3, test.getNo());
		            }

		            // SQL実行
		            count += statement.executeUpdate();
		        }
		    } catch (Exception e) {
		        throw e;
		    } finally {
		        if (statement != null) {
		            try {
		                statement.close();
		            } catch (SQLException sqle) {
		                throw sqle;
		            }
		        }
		        if (connection != null) {
		            try {
		                connection.close();
		            } catch (SQLException sqle) {
		                throw sqle;
		            }
		        }
		    }

		    // 成功か失敗か
		    return count > 0;

	}

	public boolean save(Test test, Connection connection) throws Exception {
		PreparedStatement statement = null;
		int count = 0;

		try {
			// INSERT文
			statement = connection.prepareStatement("insert into test(no, point, student_no, subject_cd, school_cd) values(?, ?, ?, ?, ?)");
			//値をバインド
			statement.setInt(1, test.getNo());
			statement.setInt(2, test.getPoint());
			statement.setString(3, test.getStudent().getNo());
			statement.setString(4, test.getSubject().getCd());
			statement.setString(5, test.getSchool().getCd());

			// SQLを実行
			count = statement.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			// ステートメントを閉じる
			if (statement != null) {
				try { statement.close(); } catch (SQLException sqle) { throw sqle; }
			}
		}

		//成功か失敗を判定
		return count > 0;

	}

}
