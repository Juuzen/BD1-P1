package dao;

import model.Admin;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AdminDao implements Dao<Admin, String> {

    private static final Logger LOGGER =
            Logger.getLogger(AdminDao.class.getName());

    @Override
    public Optional<Admin> get(String key) {
        Optional<Connection> connection = JdbcConnection.getConnection();
        AtomicReference<Optional<Admin>> admin = new AtomicReference<>(Optional.empty());

        connection.ifPresent(conn -> {
            String query = "SELECT * FROM admin WHERE username = " + key;

            try (Statement statement = conn.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {
                if (resultSet.next()) {
                    String username = resultSet.getString(1);
                    String password = resultSet.getString(2);

                    admin.set(Optional.of(new Admin(username, password)));
                }

                conn.close();
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, "GET-ADMIN", ex);
            }
        });

        return admin.get();
    }

    @Override
    public Collection<Admin> getAll() {
        Optional<Connection> connection = JdbcConnection.getConnection();
        Collection<Admin> adminList = new ArrayList<>();

        connection.ifPresent(conn -> {
            String query = "SELECT * FROM capocantiere";
            try (Statement statement = conn.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    String username = resultSet.getString("username");
                    String password = resultSet.getString("password");

                    Admin admin = new Admin (username, password);
                    adminList.add(admin);
                }
                conn.close();
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, "GETALL-ADMIN", ex);
            }
        });

        return adminList;
    }

    @Override
    public Optional<String> save(Admin admin) {
        Optional<Connection> connection = JdbcConnection.getConnection();
        AtomicReference<Optional<String>> insertedKey = new AtomicReference<>(Optional.empty());

        connection.ifPresent(conn -> {
            String query = "INSERT INTO admin(username, password) VALUES(?, crypt(?, gen_salt('bf')))";

            try (PreparedStatement statement = conn.prepareStatement(query)) {
                statement.setString(1, admin.getUsername());
                statement.setString(2, admin.getPassword());

                int insertedRows = statement.executeUpdate();
                if (insertedRows > 0)
                    insertedKey.set(Optional.of(admin.getUsername()));

                conn.close();
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, "SAVE-ADMIN", ex);
            }
        });
        return insertedKey.get();
    }

    @Override
    public void update(Admin admin) {

    }

    @Override
    public void delete(Admin admin) {

    }

    public boolean login(String username, String password) {
        AtomicBoolean response = new AtomicBoolean(false);
        Optional<Connection> connection = JdbcConnection.getConnection();

        connection.ifPresent(conn -> {
            String query = "SELECT username FROM admin WHERE username = ? AND password = crypt(?, password)";

            try (PreparedStatement statement = conn.prepareStatement(query)) {
                statement.setString(1, username);
                statement.setString(2, password);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    response.set(true);
                }

            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, "LOGIN-ADMIN", ex);
            }
        });

        return response.get();
    }
}
