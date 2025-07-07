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

	private String baseSql = "select student.no as student_no, student.name, student.ent_year, student.class_num, "
			+ "student.is_attend, student.school_cd, test.subject_cd, test.no as count, test.point "
			+ "from student left join (select * from test where subject_cd = ? and no = ?) as test on student.no = test.student_no";

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
					"select * from test where student_no = ? and school_cd = ? and subject_cd = ? and no = ?");
			//値をバインド
			statement.setString(1, student.getNo());
			statement.setString(2, school.getCd());
			statement.setString(3, subject.getCd());
			statement.setInt(4, no);
			//プリペアードステートメントを実行
			ResultSet rSet = statement.executeQuery();

			//studentdao
			StudentDao studentDao = new StudentDao();
			//subjectdao
			SubjectDao subjectDao = new SubjectDao();
			//schooldao
			SchoolDao schoolDao = new SchoolDao();
			//結果が存在すれば値をセット
			if (rSet.next()) {
				test.setStudent(studentDao.get(rSet.getString("student_no")));
				test.setSubject(subjectDao.get(rSet.getString("subject_cd"), schoolDao.get(rSet.getString("school_cd"))));
				test.setSchool(schoolDao.get(rSet.getString("school_cd")));
				test.setNo(rSet.getInt("no"));
				test.setPoint(rSet.getInt("point"));
				test.setClassNum(rSet.getString("class_num"));
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

	private List<Test> postFilter(ResultSet rSet, School school)throws Exception{

		//リストを初期化
		List<Test> list = new ArrayList<>();
		StudentDao studentDao = new StudentDao();
		SubjectDao subjectDao = new SubjectDao();
		try {
			//リザルトセットを全権走査
			while (rSet.next()) {
				//テストインスタンスを初期化
				Test test = new Test();
				//テストインスタンスに検索結果をセット
				test.setStudent(studentDao.get(rSet.getString("student_no")));
				test.setClassNum(rSet.getString("class_num"));
				test.setSubject(subjectDao.get(rSet.getString("subject_cd"), school));
				test.setSchool(school);
				test.setNo(rSet.getInt("count"));
				test.setPoint(rSet.getInt("point"));
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
	    ResultSet rSet = null;

	    // SQL構築
	    String condition = " where ent_year = ? and student.class_num = ? and student.school_cd = ?";
	    String order = " order by student.no asc";

	    try {
			// プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement(baseSql + condition + order);
			//値をバインド
			statement.setString(1, subject.getCd());
			statement.setInt(2, num);
			statement.setInt(3, entYear);
			statement.setString(4, classNum);
			statement.setString(5, school.getCd());
			// プリペアードステートメントを実行
			rSet = statement.executeQuery();
			// リストへの格納処理を実行
			list = postFilter(rSet, school);
		} catch (Exception e) {
			throw e;
		} finally {
			// プリペアードステートメントとコネクションを閉じる
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

		return list;
	}

	public boolean save(List<Test> list) throws Exception {

		try {
			for (Test test : list) {
				// コネクションを確立
				Connection connection = getConnection();
				// saveメソッドで情報を保存
				save(test, connection);
			}
		} catch (Exception e) {
			throw e;
		}

		return true;

	}

	public boolean save(Test test, Connection connection) throws Exception {

		// プリペアードステートメント
				PreparedStatement statement = null;
				// 実行件数
				int count = 0;

				try {
					// データベースからテスト情報を取得
					Test old = get(test.getStudent(), test.getSubject(), test.getSchool(), test.getNo());
					if (old == null) {
						// 学生が存在しなかった場合
						// プリペアードステートメントにINSERT文をセット
						statement = connection.prepareStatement("insert into test(student_no, subject_cd, school_cd, no, point, class_num) values(?, ?, ?, ?, ?, ?)");
						// バインド
						statement.setString(1, test.getStudent().getNo());
						statement.setString(2, test.getSubject().getCd());
						statement.setString(3, test.getSchool().getCd());
						statement.setInt(4, test.getNo());
						statement.setInt(5, test.getPoint());
						statement.setString(6, test.getClassNum());
					} else {
						// テストが存在した場合
						// プリペアードステートメントにUPDATE文をセット
						statement = connection.prepareStatement("update test set point = ? where student_no = ? and subject_cd = ? and school_cd = ? and no = ?");
						// 値をバインド
						statement.setInt(1, test.getPoint());
						statement.setString(2, test.getStudent().getNo());
						statement.setString(3, test.getSubject().getCd());
						statement.setString(4, test.getSchool().getCd());
						statement.setInt(5, test.getNo());
					}

					// プリペアードステートメントを実行
					count = statement.executeUpdate();

				} catch (Exception e) {
					throw e;
				} finally {
					// コネクションとプリペアードステートメントを閉じる
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

				if (count > 0) {
					// 実行件数が1件以上ある場合
					return true;
				} else {
					// 実行件数が0件の場合
					return false;
				}
			}

		}