package com.jdbc.example;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

public class Model {
	
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String uid = "hr";
	private String upw = "hr";

	//select 할 내용 작성
	public void selectOne() {
		
		String sql = "SELECT * FROM EMPLOYEES WHERE EMPLOYEE_ID >= ?";  //? 는 변할수 있는값일때
		//모든 jdbc 코드는 try-catch 구문에서 작성해야한다. (throws를 던지고있기 때문에)
		Connection conn = null;  //마지막에 close 하기위한 전역변수선언
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			//1.JDBC 드라이버 준비
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//2. conn 객체 생성
			conn = DriverManager.getConnection(url, uid, upw);
			//3. conn으로부터 statement 객체 생성 - sql 상태를 지정하기 위한 객체
			pstmt = conn.prepareStatement(sql);
			//?에 대한 값을 채웁니다. setString(순서,문자열)  setInt, setDouble
			//?에 대한 순서는 1부터 시작한다.
			pstmt.setString(1, "180"); //지금은 ?가 1개 이므로 
			//4. 실행
			//executeQuery - SELECT 문에 사용
			//executeUpdate - INSERT, UPDATE, DELETE 문에 사용
			rs = pstmt.executeQuery();  //SELECT는 해당값을 return 하므로
			while(rs.next()) {	//next() ResultSet의 메서드
				//rs.getString(컬럼명) , getInt(컬럼명), getDouble(컬럼명), getDate(컬럼명)
				int emp_id = rs.getInt("EMPLOYEE_ID");
				String first_name = rs.getString("FIRST_NAME");
				String phone_number = rs.getString("phone_number");
//				String hire_date = rs.getString("hire_date");  //getDate 안된다 주의
				Timestamp hire_date = rs.getTimestamp("hire_date");
				int salary = rs.getInt("SALARY");
				
				System.out.println("아이디:" + emp_id);
				System.out.println("이름:" + first_name);
				System.out.println("전화번호:" + phone_number);
				System.out.println("입사일:" + hire_date);
				System.out.println("급여:" + salary);
				System.out.println("--------------------------");
			}
		} catch (Exception e) {
			e.printStackTrace();  //꼭찍어야한다
		} finally {
			try {
				conn.close();
				pstmt.close();
				rs.close();  //**close 를 꼭해야한다
			} catch (Exception e2) {
			}
		}
		
	}

	//insert 할 내용 작성
	public void insertOne(int id, String name, String mid, String lid) {
		Connection conn = null;  //마지막에 close 하기위한 전역변수선언
		PreparedStatement pstmt = null;  //이것들도 selectOne 과 중복되는데 왜 멤버변수로 안하는가
		//동시접속으로인한 보안 문제 발생 가능성
		//ResultSet은 INSERT에서 불필요
		String sql = "INSERT INTO DEPTS VALUES(?, ?, ?, ?)"; //set 이 4개가 필요하다
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			//1.conn 생성
			conn = DriverManager.getConnection(url, uid, upw);
			//2.pstmt 생성
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			pstmt.setString(2, name);
			pstmt.setString(3, mid);
			pstmt.setString(4, lid);
			//3.실행
			int res = pstmt.executeUpdate();  //성공시 1 or 실패시 0
			if (res == 1) System.out.println("INSERT 성공");
			else System.out.println("INSERT 실패");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
				pstmt.close();
			} catch (Exception e2) {
			}
		}
	}
	
	//Main 에서 부서아이디, 부서명, 매니저아이디만 받아서, 해당부서의 부서명과 매니저 아이디를 수정
	public void updateOne(int id, String name, String mid) {
				Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "UPDATE DEPTS "
				+ "SET DEPARTMENT_NAME=?, MANAGER_ID=? "
				+ "WHERE DEPARTMENT_ID = ?";
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//1.conn 생성
			conn = DriverManager.getConnection(url, uid, upw);
			//2.pstmt 생성
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(3, id);
			pstmt.setString(1, name);
			pstmt.setString(2, mid);
			//3.실행
			int res = pstmt.executeUpdate();  //성공시 1 or 실패시 0
			if (res == 1) System.out.println("UPDATE 성공");
			else System.out.println("UPDATE 실패");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
				pstmt.close();
			} catch (Exception e2) {
			}
		}
	}
	
	//Main 에서 emps 테이블에서 employee_id 를 받아서 해당 아이디를 삭제해주세요
	public void deleteOne(int id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "DELETE FROM EMPS WHERE EMPLOYEE_ID = ? ";
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//1.conn 생성
			conn = DriverManager.getConnection(url, uid, upw);
			//2.pstmt 생성
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			//3.실행
			
			int res = pstmt.executeUpdate();
			//단순 pstmt.executeUpdate(); 만 써도 실행되지만 호가인을 위해
			if(res == 1) 	
				System.out.println("DELETE 성공");
			else 		 
				System.out.println("DELETE 실패");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
				pstmt.close();
			} catch (Exception e2) {
			}
		}
	}
}
