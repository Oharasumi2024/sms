package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;

public class SubjectDao extends Dao {

	public Subject get(String cd,School school)throws Exception {
		//Subjectの初期化
		Subject subject=new Subject();
		//コネクションを確立
		Connection connection=getConnection();
		//SQL文をセット(データの取得)
		PreparedStatement statement=null;
	try {
		statement=connection.prepareStatement(
				"select * from subject where cd=? and school_cd=?");
		//値をバインド
		statement.setString(1,cd);
		statement.setString(2, school.getCd());
		//リザルトセット
		ResultSet rSet=null;
		//SQLの実行
		rSet=statement.executeQuery();
		SchoolDao sDao=new SchoolDao();
		//結果の格納
		if (rSet.next()) {
			subject.setCd(rSet.getString("cd"));
			subject.setName(rSet.getString("name"));
			subject.setSchool(sDao.get(rSet.getString("school_cd")));
			}else {
			subject=null;
		}
	}catch(Exception e){
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
		return subject;
	}


	public List<Subject> filter(School school)throws Exception{
		//リストの作成
		List<Subject> list=new ArrayList<>();
		//コネクションを確立
		Connection connection=getConnection();
		//SQL文をセット(データの取得)
		PreparedStatement statement=null;
	try {
		statement=connection.prepareStatement(
				"select * from subject where school_cd=?");
		//学校コードをバインド
		statement.setString(1,school.getCd());
		//リザルトセット
		ResultSet rSet=null;
		//SQLの実行
		rSet=statement.executeQuery();
		//結果の格納
		while (rSet.next()) {
			Subject subject=new Subject();
			subject.setCd(rSet.getString("cd"));
			subject.setName(rSet.getString("name"));
			subject.setSchool(school);
			list.add(subject);
		}
	}catch(Exception e){
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

	public boolean save(Subject subject)throws Exception {
		//コネクションを確立
		Connection connection=getConnection();
		//プリパレ
		PreparedStatement statement=null;
		//実行件数
		int count=0;

		try {
			//DBから科目を取得
			Subject old=get(subject.getCd(),subject.getSchool());
			if (old==null) {
				//科目がnullだった場合insert
				statement=connection.prepareStatement(
						"insert into subject(school_cd,cd,name) values(?,?,?)");
				//プリパレに値をバインド
				statement.setString(1, subject.getSchool().getCd());
				statement.setString(2, subject.getCd());
				statement.setString(3, subject.getName());
			}else {
				//nullじゃなかった場合update
				statement=connection.prepareStatement(
						"update subject set name=? where school_cd=? snd cd=?");
				//値をバインド
				statement.setString(1, subject.getName());
				statement.setString(2, subject.getSchool().getCd());
				statement.setString(3, subject.getCd());
			}
			//プリパレの実行
			count=statement.executeUpdate();
		}catch(Exception e) {
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
		if (count>0) {
			//実行件数が1件以上の時
			return true;
		}else {
			//実行件数が０件の時
			return false;
		}
	}

	public boolean delete(Subject subject)throws Exception {
		//コネクションを確立
		Connection connection=getConnection();
		//プリパレ
		PreparedStatement statement=null;
		//実行件数
		int count=0;

		try {
			//プリパレ
			statement=connection.prepareStatement(
					"delete from subject where school_cd=? and cd=?");
			statement.setString(1, subject.getSchool().getCd());
			statement.setString(2, subject.getCd());
			//プリパレの実行
			count=statement.executeUpdate();
		}catch(Exception e) {
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
		if (count>0) {
			//実行件数が1件以上の時
			return true;
		}else {
			//実行件数が０件の時
			return false;
		}
	}


	public Subject getInt(String cd) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}
}
