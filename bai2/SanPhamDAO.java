
package DAO;

import Entity.SanPham;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import util.DBConnection;

/**
 *
 * @author nvtqu
 */
public class SanPhamDAO {
    private Connection conn;
    public SanPhamDAO(){
        try{
            String url = "jdbc:sqlserver://localhost:1433;databaseName=DB_BTL_JAVA;encrypt=true;trustServerCertificate=true";
            String user = "sa"; // tài khoản SQL Server
            String password = "12345678"; // mật khẩu SQL Server
            conn = DBConnection.getConnection();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public ArrayList<SanPham> hienThiDanhSachSanPham(){
        ArrayList<SanPham> list = new ArrayList<>();
        String sql = "SELECT * FROM SanPham";
        try{
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                SanPham s = new SanPham();
                s.setMaSP(rs.getString("Mã sản phẩm"));
                s.setTenSP(rs.getString("Tên sản phẩm"));
                s.setLoaiSP(rs.getString("Loại sản phẩm"));
                s.setTenHSX(rs.getString("Hãng sản xuất"));
                s.setKichThuoc(rs.getString("Kích thước"));
                s.setGia(rs.getDouble("Giá bán"));
                s.setSoLuong(rs.getInt("Số lượng"));
                list.add(s);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return list;
    }
    public boolean themSanPham(SanPham s) throws Exception {
        String sql = "INSERT INTO SanPham('Mã sản phẩm', 'Tên sản phẩm', 'Loại sản phẩm', 'Hãng sản xuất', 'Kích thước', 'Số lượng', 'Giá bán')"
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try{
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, s.getMaSP());
            ps.setString(2, s.getTenSP());
            ps.setString(3, s.getLoaiSP());
            ps.setString(4, s.getTenHSX());
            ps.setString(5, s.getKichThuoc());
            ps.setInt(6, s.getSoLuong());
            ps.setDouble(7, s.getGia());
            int check = ps.executeUpdate();
            return check > 0;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }       
    public void xoaSanPham(String masp){
        String sql = "DELETE FROM SanPham WHERE ID = ?";
        try{
            PreparedStatement ps = conn.prepareCall(sql);
            ps.setString(1, masp);
            ps.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public SanPham timSanPhamTheoMaSP(String maSP) {
        String sql = "SELECT maSP, tenSP, loaiSP, tenHSx, gia, maKT FROM SanPham WHERE maSP = ?";
        try (Connection conn = DBConnection.getConnection();PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maSP);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new SanPham(
                        rs.getString("maSP"),
                        rs.getString("tenSP"),
                        rs.getString("loaiSP"),
                        rs.getString("tenHSX"),
                        rs.getString("kichThuoc"),
                        rs.getInt("soLuong"),
                        rs.getDouble("gia")
                );
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public boolean suaSanPham(SanPham sp) {
        String sql = "UPDATE SanPham SET tenSP=?, loaiSP=?, tenHSX=?, gia=? WHERE maTK=?";
        
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, sp.getMaSP());
            ps.setString(2, sp.getTenSP());
            ps.setString(3, sp.getLoaiSP());
            ps.setString(4, sp.getTenHSX());
            ps.setString(5, sp.getKichThuoc());
            ps.setInt(6, sp.getSoLuong());
            ps.setDouble(7, sp.getGia());
          
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public static void main(String[] args) {
        new SanPhamDAO();
    }
    
}

