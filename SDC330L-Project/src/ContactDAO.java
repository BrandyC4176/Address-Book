/*****
 * Name: Brandy Christopher
 * Date: 11/1/25
 * Purpose: Data Access Object for Contact hierarchy (CRUD against SQLite).
 */
import java.sql.*;
import java.util.*;

public class ContactDAO {

    // CREATE
    public int insert(Contact c) throws SQLException {
        String sql = "INSERT INTO contacts (type, first_name, last_name, phone, email, street, city, state, zip," +
                "company_name, company_phone, job_title, relationship, how_we_met) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            bindCommon(ps, c);
            bindSpecific(ps, c);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                return rs.next() ? rs.getInt(1) : -1;
            }
        }
    }

    // READ all
    public List<Contact> findAll() throws SQLException {
        String sql = "SELECT * FROM contacts ORDER BY last_name, first_name";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            List<Contact> list = new ArrayList<>();
            while (rs.next()) list.add(mapRow(rs));
            return list;
        }
    }

    // READ by type
    public List<Contact> findByType(String type) throws SQLException {
        String sql = "SELECT * FROM contacts WHERE LOWER(type)=LOWER(?) ORDER BY last_name, first_name";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, type);
            try (ResultSet rs = ps.executeQuery()) {
                List<Contact> list = new ArrayList<>();
                while (rs.next()) list.add(mapRow(rs));
                return list;
            }
        }
    }

    // READ by last-name initial
    public List<Contact> findByLastInitial(char ch) throws SQLException {
        String sql = "SELECT * FROM contacts WHERE UPPER(SUBSTR(last_name,1,1))=? ORDER BY last_name, first_name";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, String.valueOf(Character.toUpperCase(ch)));
            try (ResultSet rs = ps.executeQuery()) {
                List<Contact> list = new ArrayList<>();
                while (rs.next()) list.add(mapRow(rs));
                return list;
            }
        }
    }

    // UPDATE (simple: phone/email by id)
    public boolean updateContactBasics(int id, String newPhone, String newEmail) throws SQLException {
        String sql = "UPDATE contacts SET phone=?, email=? WHERE id=?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nullToEmpty(newPhone));
            ps.setString(2, nullToEmpty(newEmail));
            ps.setInt(3, id);
            return ps.executeUpdate() > 0;
        }
    }

    // DELETE by id
    public boolean deleteById(int id) throws SQLException {
        String sql = "DELETE FROM contacts WHERE id=?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    // ----- helpers -----
    private void bindCommon(PreparedStatement ps, Contact c) throws SQLException {
        ps.setString(1, c.getType());
        ps.setString(2, c.getFirstName());
        ps.setString(3, c.getLastName());
        ps.setString(4, nullToEmpty(c.getPhone()));
        ps.setString(5, nullToEmpty(c.getEmail()));
        Address a = c.getAddress();
        ps.setString(6, a == null ? "" : nullToEmpty(a.getStreet()));
        ps.setString(7, a == null ? "" : nullToEmpty(a.getCity()));
        ps.setString(8, a == null ? "" : nullToEmpty(a.getState()));
        ps.setString(9, a == null ? "" : nullToEmpty(a.getZip()));
    }

    private void bindSpecific(PreparedStatement ps, Contact c) throws SQLException {
        String companyName = "", companyPhone = "", jobTitle = "", relationship = "", howWeMet = "";
        if (c instanceof BusinessContact) {
            BusinessContact bc = (BusinessContact) c;
            Company co = bc.getCompany();
            companyName  = co == null ? "" : nullToEmpty(co.getName());
            companyPhone = co == null ? "" : nullToEmpty(co.getMainPhone());
            jobTitle     = nullToEmpty(bc.getJobTitle());
        } else if (c instanceof FamilyContact) {
            FamilyContact fc = (FamilyContact) c;
            relationship = nullToEmpty(fc.getRelationship());
        } else if (c instanceof FriendContact) {
            FriendContact fr = (FriendContact) c;
            howWeMet = nullToEmpty(fr.getHowWeMet());
        }
        ps.setString(10, companyName);
        ps.setString(11, companyPhone);
        ps.setString(12, jobTitle);
        ps.setString(13, relationship);
        ps.setString(14, howWeMet);
    }

    private Contact mapRow(ResultSet rs) throws SQLException {
        String type  = rs.getString("type");
        String fn    = rs.getString("first_name");
        String ln    = rs.getString("last_name");
        String phone = rs.getString("phone");
        String email = rs.getString("email");

        Address addr = new Address(
            rs.getString("street"),
            rs.getString("city"),
            rs.getString("state"),
            rs.getString("zip")
        );

        if ("Business".equals(type)) {
            Company co = new Company(rs.getString("company_name"), rs.getString("company_phone"));
            String jt = rs.getString("job_title");
            return new BusinessContact(fn, ln, phone, email, addr, co, jt);
        } else if ("Family".equals(type)) {
            return new FamilyContact(fn, ln, phone, email, addr, rs.getString("relationship"));
        } else if ("Friend".equals(type)) {
            return new FriendContact(fn, ln, phone, email, addr, rs.getString("how_we_met"));
        } else {
            return new FriendContact(fn, ln, phone, email, addr, "");
        }
    }

    private String nullToEmpty(String s) { return s == null ? "" : s; }
}
