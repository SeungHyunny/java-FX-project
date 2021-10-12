package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import common.ConnectionFactory;
import vo.Departments;
import vo.Job_history;

public class DepartmentsDAO implements IDao<Departments, Integer> {

	@Override
	public int insert(Departments vo) throws SQLException {
		Connection conn = ConnectionFactory.create();
		String sql = "INSERT INTO DEPARTMENTS (DEPARTMENT_ID,DEPARTMENT_NAME,MANAGER_ID, "
				+ " LOCATION_ID) VALUES(?,?,?,?) ";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1,vo.getDepartment_id());
		pstmt.setString(2,vo.getDepartment_name());
		pstmt.setInt(3,vo.getManager_id());
		pstmt.setInt(4,vo.getLocation_id());
		int res = pstmt.executeUpdate();
		conn.close();
		return res;
	}

	@Override
	public int delete(Integer key) throws SQLException {
		Connection conn = ConnectionFactory.create();
		String sql = "DELETE FROM DEPARTMENTS WHERE DEPARTMENT_ID = ? ";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1,key);
		int res = pstmt.executeUpdate();
		conn.close();
		return res;
	}

	@Override
	public int update(Departments vo) throws SQLException {
		Connection conn = ConnectionFactory.create();
		String sql = "UPDATE DEPARTMENT_NAME = ?, MANAGER_ID = ?, LOCATION_ID = ?"
				+ " WHERE DEPARTMENT_ID = ? ";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1,vo.getDepartment_name());
		pstmt.setInt(2,vo.getManager_id());
		pstmt.setInt(3,vo.getLocation_id());
		pstmt.setInt(4,vo.getDepartment_id());
		int res = pstmt.executeUpdate();
		conn.close();
		return res;
	}

	@Override
	public Departments select(Integer key) throws SQLException {
		Connection conn = ConnectionFactory.create();
		String sql = "SELECT * FROM DEPARTMENTS WHERE DEPARTMENT_ID ="+key;
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		Departments vo = new Departments();
		while(rs.next()) {
			vo.setDepartment_id(rs.getInt("DEPARTMENT_ID"));
			vo.setDepartment_name(rs.getString("DEPARTMENT_NAME"));
			vo.setManager_id(rs.getInt("MANAGER_ID"));
			vo.setLocation_id(rs.getInt("LOCATION_ID"));
		}
		conn.close();
		return vo;
	}

	@Override
	public List<Departments> selectAll() throws SQLException {
		List<Departments> data = new ArrayList<>();
		Connection conn = ConnectionFactory.create();
		String sql = "SELECT * FROM DEPARTMENTS ";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery(sql);
		while(rs.next()) {
			Departments vo = new Departments();
			vo.setDepartment_id(rs.getInt("DEPARTMENT_ID"));
			vo.setDepartment_name(rs.getString("DEPARTMENT_NAME"));
			vo.setManager_id(rs.getInt("MANAGER_ID"));
			vo.setLocation_id(rs.getInt("LOCATION_ID"));
			
			data.add(vo);
		}
		conn.close();
		return data;
	}

	@Override
	public List<Departments> selectByConditions(String conditions) throws SQLException {
		Connection conn = ConnectionFactory.create();
		String sql = "SELETE * FROM DEPARTMENTS " + conditions;
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		List<Departments> data = new ArrayList<>();
		while(rs.next()) {
			Departments vo = new Departments();
			vo.setDepartment_id(rs.getInt("DEPARTMENT_ID"));
			vo.setDepartment_name(rs.getString("DEPARTMENT_NAME"));
			vo.setManager_id(rs.getInt("MANAGER_ID"));
			vo.setLocation_id(rs.getInt("LOCATION_ID"));
			data.add(vo);
		}
		conn.close();
		return data;
	}

}
