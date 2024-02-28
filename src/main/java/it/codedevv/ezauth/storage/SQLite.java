package it.codedevv.ezauth.storage;

import it.codedevv.ezauth.EzAuth;
import it.codedevv.ezauth.auth.model.AuthPlayer;
import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SQLite {
    private static final ExecutorService executor = Executors.newSingleThreadExecutor();
    @Getter
    private Connection connection;

    public SQLite() {
        File file = new File(EzAuth.getInstance().getDataFolder(), "database.db");
        try {
            if (!file.exists())
                file.createNewFile();
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:database.db");
            loadTables();
            EzAuth.LOGGER.info("[SQLite] Local database connected!");
        } catch (SQLException | ClassNotFoundException | IOException ex) {
            ex.printStackTrace();
        }
    }

    public void loadTables() {
        try (Connection conn = connection;
                PreparedStatement statement = conn.prepareStatement("CREATE TABLE IF NOT EXISTS ezauth_players (uuid VARCHAR(46), password VARCHAR, isPremium BOOLEAN, isRegistered BOOLEAN);")) {
            statement.executeUpdate();
            EzAuth.LOGGER.info("[SQLite] Table ezauth_players created successfully.");
        } catch (SQLException ex) {
            EzAuth.LOGGER.severe("[SQLite] Error creating table ezauth_players: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void createPlayer(AuthPlayer authPlayer) {
        executor.execute(() -> {
            try (PreparedStatement selectStatement = connection.prepareStatement("SELECT * FROM ezauth_players WHERE uuid=?")) {
                selectStatement.setString(1, authPlayer.getUuid().toString());
                ResultSet result = selectStatement.executeQuery();
                if (!result.next()) {
                    String insertQuery = "INSERT INTO ezauth_players (uuid, password, isPremium, isRegistered) VALUES (?, ?, ?, ?)";
                    try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
                        insertStatement.setString(1, authPlayer.getUuid().toString());
                        insertStatement.setString(2, authPlayer.getPassword());
                        insertStatement.setBoolean(3, authPlayer.isPremium());
                        insertStatement.setBoolean(4, authPlayer.isRegistered());
                        insertStatement.executeUpdate();
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

    }

    public void deletePlayer(UUID uuid) {
        executor.execute(() -> {
            try (PreparedStatement statement = connection.prepareStatement("DELETE FROM ezauth_players WHERE uuid=?")) {
                statement.setString(1, uuid.toString());
                statement.executeUpdate();
            } catch (SQLException ex) {
                ex.printStackTrace(); //a
            }
        });
    }
}
