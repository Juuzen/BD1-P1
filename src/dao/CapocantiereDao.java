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
                    @Nullable
                    Integer cantiereId;
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
                    if (cantId == 0) cantiereId = NULL;
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
        return Optional.empty();
    }

    @Override
    public void update(Capocantiere capocantiere) {

    }

    @Override
    public void delete(Capocantiere capocantiere) {

    }

    public boolean login(Capocantiere capocantiere) {
        AtomicBoolean response = new AtomicBoolean(false);
        Optional<Connection> connection = JdbcConnection.getConnection();

        connection.ifPresent(conn -> {
            String query = "SELECT username FROM admin WHERE username = ? AND password = crypt(?, password)";

            try (PreparedStatement statement = conn.prepareStatement(query)) {
                statement.setString(1, capocantiere.getUsername());
                statement.setString(2, capocantiere.getPassword());
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
