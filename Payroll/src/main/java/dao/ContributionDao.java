package dao;

import Db.DBUtil;
import model.Contribution;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContributionDao {


    public boolean saveContribution(Contribution contribution) {
        String sql = "INSERT INTO public.contributions (employer_epf, employer_etf, employee_epf) VALUES (?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, contribution.getEmployerEPF());
            stmt.setDouble(2, contribution.getEmployerETF());
            stmt.setDouble(3, contribution.getEmployeeEPF());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public List<Contribution> getAllContributions() {
        List<Contribution> list = new ArrayList<>();
        String sql = "SELECT * FROM public.contributions";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Contribution c = new Contribution();
                c.setId(rs.getInt("id"));
                c.setEmployerEPF(rs.getDouble("employer_epf"));
                c.setEmployerETF(rs.getDouble("employer_etf"));
                c.setEmployeeEPF(rs.getDouble("employee_epf"));
                list.add(c);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


    public Contribution getContributionById(int id) {
        String sql = "SELECT * FROM public.contributions WHERE id = ?";
        Contribution c = null;

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    c = new Contribution();
                    c.setId(rs.getInt("id"));
                    c.setEmployerEPF(rs.getDouble("employer_epf"));
                    c.setEmployerETF(rs.getDouble("employer_etf"));
                    c.setEmployeeEPF(rs.getDouble("employee_epf"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return c;
    }


    public boolean updateContribution(Contribution contribution) {
        String sql = "UPDATE public.contributions SET employer_epf=?, employer_etf=?, employee_epf=? WHERE id=?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, contribution.getEmployerEPF());
            stmt.setDouble(2, contribution.getEmployerETF());
            stmt.setDouble(3, contribution.getEmployeeEPF());
            stmt.setInt(4, contribution.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean deleteContribution(int id) {
        String sql = "DELETE FROM public.contributions WHERE id=?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
