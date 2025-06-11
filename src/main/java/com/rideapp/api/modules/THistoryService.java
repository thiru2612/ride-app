package com.rideapp.api.modules;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.rideapp.api.db.DatabaseConnection;
import com.rideapp.api.entity.TransactionHistory;

public class THistoryService {
	public List<TransactionHistory> getTHistory(int userId){
		List<TransactionHistory>th=new ArrayList<>();
		try (Connection conn = DatabaseConnection.getConnection()) {
			PreparedStatement stmt = conn.prepareStatement("SELECT t.id as tran_id,ur.ride_id,t.tmode, t.date, ts.tStatus, \r\n"
    				+ "       COALESCE(t.final_fair, 0) AS final \r\n"
    				+ "FROM user_ride ur \r\n"
    				+ "INNER JOIN transaction_ride trs ON trs.ride_id = ur.ride_id \r\n"
    				+ "INNER JOIN transactions t ON trs.t_id = t.id \r\n"
    				+ "inner join TStatus ts on t.id=ts.t_id \r\n"
    				+ "WHERE ur.user_id=? \r\n"
    				+ "ORDER BY t.date DESC;");
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                TransactionHistory t = new TransactionHistory(
                		rs.getInt("tran_id"),
                		rs.getInt("ride_id"),
                		rs.getString("tmode"),
                		rs.getString("tStatus"),
                		rs.getDouble("final"),
                		rs.getTimestamp("date")
                );
                th.add(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
		return th;
	}
	public List<TransactionHistory> getTStatus(int userId,String status){
		List<TransactionHistory>th=new ArrayList<>();
		try (Connection conn = DatabaseConnection.getConnection()) {
			PreparedStatement stmt = conn.prepareStatement("SELECT t.id as tran_id,ur.ride_id,t.tmode, t.date, ts.tStatus, \r\n"
					+ "       COALESCE(t.final_fair, 0) AS final \r\n"
					+ "FROM user_ride ur \r\n"
					+ "INNER JOIN transaction_ride trs ON trs.ride_id = ur.ride_id \r\n"
					+ "INNER JOIN transactions t ON trs.t_id = t.id \r\n"
					+ "inner join TStatus ts on t.id=ts.t_id \r\n"
					+ "WHERE ur.user_id=? and tStatus=? \r\n"
					+ "ORDER BY t.date DESC;");
			stmt.setInt(1, userId);
			stmt.setString(2,status);
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				TransactionHistory t = new TransactionHistory(
						rs.getInt("tran_id"),
						rs.getInt("ride_id"),
						rs.getString("tmode"),
						rs.getString("tStatus"),
						rs.getDouble("final"),
						rs.getTimestamp("date")
						);
				th.add(t);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return th;
	}
	public List<TransactionHistory> getTStatusDate(int userId,String status,Date ti){
		List<TransactionHistory>th=new ArrayList<>();
		try (Connection conn = DatabaseConnection.getConnection()) {
			PreparedStatement stmt = conn.prepareStatement("SELECT t.id as tran_id,ur.ride_id,t.tmode, t.date, ts.tStatus, \r\n"
					+ "       COALESCE(t.final_fair, 0) AS final \r\n"
					+ "FROM user_ride ur \r\n"
					+ "INNER JOIN transaction_ride trs ON trs.ride_id = ur.ride_id \r\n"
					+ "INNER JOIN transactions t ON trs.t_id = t.id \r\n"
					+ "inner join TStatus ts on t.id=ts.t_id \r\n"
					+ "WHERE ur.user_id=? and tStatus=? and Date(t.date)=? \r\n"
					+ "ORDER BY t.date DESC;");
			stmt.setInt(1, userId);
			stmt.setString(2,status);
			stmt.setDate(3, ti);
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				TransactionHistory t = new TransactionHistory(
						rs.getInt("tran_id"),
						rs.getInt("ride_id"),
						rs.getString("tmode"),
						rs.getString("tStatus"),
						rs.getDouble("final"),
						rs.getTimestamp("date")
						);
				th.add(t);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return th;
	}
	public List<TransactionHistory> getTDate(int userId,Date ti){
		List<TransactionHistory>th=new ArrayList<>();
		try (Connection conn = DatabaseConnection.getConnection()) {
			PreparedStatement stmt = conn.prepareStatement("SELECT t.id as tran_id,ur.ride_id,t.tmode, t.date, ts.tStatus, \r\n"
					+ "       COALESCE(t.final_fair, 0) AS final \r\n"
					+ "FROM user_ride ur \r\n"
					+ "INNER JOIN transaction_ride trs ON trs.ride_id = ur.ride_id \r\n"
					+ "INNER JOIN transactions t ON trs.t_id = t.id \r\n"
					+ "inner join TStatus ts on t.id=ts.t_id \r\n"
					+ "WHERE ur.user_id=?  and DATE(t.date)=? \r\n"
					+ "ORDER BY t.date DESC;");
			stmt.setInt(1, userId);
			stmt.setDate(2, ti);
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				TransactionHistory t = new TransactionHistory(
						rs.getInt("tran_id"),
						rs.getInt("ride_id"),
						rs.getString("tmode"),
						rs.getString("tStatus"),
						rs.getDouble("final"),
						rs.getTimestamp("date")
						);
				th.add(t);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return th;
	}
	public List<TransactionHistory> getTMode(int userId,String mode){
		List<TransactionHistory>th=new ArrayList<>();
		try (Connection conn = DatabaseConnection.getConnection()) {
			PreparedStatement stmt = conn.prepareStatement("SELECT t.id as tran_id,ur.ride_id,t.tmode, t.date, ts.tStatus, \r\n"
					+ "       COALESCE(t.final_fair, 0) AS final \r\n"
					+ "FROM user_ride ur \r\n"
					+ "INNER JOIN transaction_ride trs ON trs.ride_id = ur.ride_id \r\n"
					+ "INNER JOIN transactions t ON trs.t_id = t.id \r\n"
					+ "inner join TStatus ts on t.id=ts.t_id \r\n"
					+ "WHERE ur.user_id=?  and t.tmode=? \r\n"
					+ "ORDER BY t.date DESC;");
			stmt.setInt(1, userId);
			stmt.setString(2, mode);
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				TransactionHistory t = new TransactionHistory(
						rs.getInt("tran_id"),
						rs.getInt("ride_id"),
						rs.getString("tmode"),
						rs.getString("tStatus"),
						rs.getDouble("final"),
						rs.getTimestamp("date")
						);
				th.add(t);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return th;
	}
}
