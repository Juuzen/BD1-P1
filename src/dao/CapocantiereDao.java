package dao;

import model.Capocantiere;
import org.jetbrains.annotations.Nullable;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.sql.Types.NULL;

public class CapocantiereDao implements Dao<Capocantiere, Integer> {

    private static final Logger LOGGER =
            Logger.getLogger(AdminDao.class.getName());

    @Override
    public Optional<Capocantiere> get(Integer key) {
        AtomicReference<Optional<Capocantiere>> capocantiere = new AtomicReference<>(Optional.empty());
        Optional<Connection> connection = JdbcConnection.getConnection();

        connection.ifPresent(conn -> {
            String query = "SELECT * FROM capocantiere WHERE id = " + key;

            try (Statement statement = conn.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {
                if (resultSet.next()) {
                    String username = resultSet.getString("username");
                    String password = resultSet.getString("password");
                    String name = resultSet.getString("name");
                    String surname = resultSet.getString("surname");
                    String fiscalCode = resultSet.getString("fiscalCode");
                    Integer id = resultSet.getInt("id");
                    @Nullable Integer cantiereId;
                    int cantId = resultSet.getInt("cantiere_id");
                    if (cantId == 0) cantiereId = NULL;
                    else cantiereId = cantId;

                    capocantiere.set(Optional.of(new Capocantiere(username, password, name, surname, fiscalCode, id, cantiereId)));
                }

                conn.close();
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, "GET-CAPOCANTIERE", ex);
            }
        });

        return capocantiere.get();
    }

    public Optional<Capocantiere> getByUsername(String key) {
        AtomicReference<Optional<Capocantiere>> capocantiere = new AtomicReference<>(Optional.empty());
        Optional<Connection> connection = JdbcConnection.getConnection();

        connection.ifPresent(conn -> {
            String query = "SELECT * FROM capocantiere WHERE username = " + key;

            try (Statement statement = conn.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {
                if (resultSet.next()) {
                    String username = resultSet.getString("username");
                    String password = resultSet.getString("password");
                    String name = resultSet.getString("name");
                    String surname = resultSet.getString("surname");
                    String fiscalCode = resultSet.getString("fiscal_code");
                    Integer id = resultSet.getInt("id");
                    @Nullable Integer cantiereId;
                    int cantId = resultSet.getInt("cantiere_id");
                    if (cantId == 0) cantiereId = NULL;
                    else cantiereId = cantId;

                    capocantiere.set(Optional.of(new Capocantiere(username, password, name, surname, fiscalCode, id, cantiereId)));
                }

                conn.close();
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, "GETBYUSERNAME-CAPOCANTIERE", ex);
            }
        });

        return capocantiere.get();
    }

    @Override
    public Collection<Capocantiere> getAll() {
        Collection<Capocantiere> capocantiereList = new ArrayList<>();
        Optional<Connection> connection = JdbcConnection.getConnection();

        connection.ifPresent(conn -> {
            String query = "SELECT * FROM capocantiere";

            try (Statement statement = conn.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    String username = resultSet.getString("username");
                    String password = resultSet.getString("password");
                    String name = resultSet.getString("name");
                    String surname = resultSet.getString("surname");
                    String fiscalCode = resultSet.getString("fiscalCode");
                    Integer id = resultSet.getInt("id");
                    @Nullable Integer cantiereId;
                    int cantId = resultSet.getInt("cantiere_id");
                    if (cantId == 0) cantiereId = null;
                    else cantiereId = cantId;

                    Capocantiere capocantiere = new Capocantiere(username, password, name, surname, fiscalCode, id, cantiereId);
                    capocantiereList.add(capocantiere);
                }
                conn.close();
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, "GETALL-CAPOCANTIERE", ex);
            }
        });

        return capocantiereList;
    }

    @Override
    public Optional<Integer> save(Capocantiere capocantiere) {
        AtomicReference<Optional<Integer>> insertedKey = new AtomicReference<>(Optional.empty());
        Optional<Connection> connection = JdbcConnection.getConnection();

        connection.ifPresent(conn -> {
            String query = "INSERT INTO "
                    + "capocantiere(username, password, name, surname, fiscal_code, cantiere_id) "
                    + "VALUES (?, crypt(?, gen_salt('bf')), ?, ?, ?, ?)";

            try (PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, capocantiere.getUsername());
                statement.setString(2, capocantiere.getPassword());
                statement.setString(3, capocantiere.getName());
                statement.setString(4, capocantiere.getSurname());
                statement.setString(5, capocantiere.getFiscalCode());
                @Nullable Integer cId = capocantiere.getCantiereId();
                if (cId == null) statement.setNull(6, Types.INTEGER);
                else statement.setInt(6, cId);

                int insertedRows = statement.executeUpdate();
                if (insertedRows > 0) {
                    ResultSet resultSet = statement.getGeneratedKeys();
                    if (resultSet.next()) {
                        insertedKey.set(Optional.of(resultSet.getInt(1)));
                    }
                }

            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, "SAVE-CAPOCANTIERE", ex);
            }
        });

        return insertedKey.get();
    }

    @Override
    public void update(Capocantiere capocantiere) {

    }

    @Override
    public void delete(Capocantiere capocantiere) {

    }

    public boolean login(String username, String password) {
        AtomicBoolean response = new AtomicBoolean(false);
        Optional<Connection> connection = JdbcConnection.getConnection();

        connection.ifPresent(conn -> {
            String query = "SELECT username FROM capocantiere WHERE username = ? AND password = crypt(?, password)";

            try (PreparedStatement statement = conn.prepareStatement(query)) {
                statement.setString(1, username);
                statement.setString(2, password);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    response.set(true);
                }

            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, "LOGIN-CAPOCANTIERE", ex);
            }
        });

        return response.get();
    }
}
