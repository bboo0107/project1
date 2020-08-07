package com.mvc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServlet;
import javax.sql.DataSource;

import com.mvc.dto.ReviewDTO;

public class ReviewDAO {
	
	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	String id;
	
	public ReviewDAO() {
		
		try {
			Context ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/Oracle");
			conn = ds.getConnection();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<ReviewDTO> list() {
		String sql;
		ArrayList<ReviewDTO> list = new ArrayList<ReviewDTO>();
		try {
			sql = "Select b_idx, id, content FROM bbs WHERE category = 2 ORDER BY b_idx DESC";
			
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				ReviewDTO dto = new ReviewDTO();
				dto.setB_idx(rs.getInt("b_idx"));
				System.out.println(dto.getB_idx());
				dto.setId(rs.getString("id"));
				System.out.println(dto.getId());
				dto.setContent(rs.getString("content"));
				System.out.println(dto.getContent());
				list.add(dto);
			}System.out.println("dto받아왔냐?");
		
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			resClose();
		}
		
		return list;
		
	}
	
	
	public boolean write(String content, String id) {
		String sql = "INSERT INTO bbs (b_idx,id,content,category) VALUES(bbs_seq.NEXTVAL,?,?,2)";
		boolean success = false;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, content);
			if(ps.executeUpdate()>0){
				success = true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			resClose();
		}
		return success;
	}

	

	
	public void resClose() {
		try {
			if(rs!=null) {rs.close();}
			if(ps!=null) {ps.close();}
			if(conn!=null) {conn.close();}
		}catch(Exception e) {
			e.printStackTrace();
		}		
	}




	


}