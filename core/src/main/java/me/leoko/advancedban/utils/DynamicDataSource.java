package me.leoko.advancedban.utils;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import me.leoko.advancedban.MethodInterface;
import me.leoko.advancedban.Universal;
import me.leoko.advancedban.manager.DatabaseManager;

public class DynamicDataSource {
    private final HikariConfig config = new HikariConfig();

    public DynamicDataSource(DatabaseManager.Type type) {
        MethodInterface mi = Universal.get().getMethods();
        config.setDriverClassName(type.getJdbcDriver());

        if (type == DatabaseManager.Type.H2) {
            config.setJdbcUrl("jdbc:" + type.getJdbcProtocol() + ":file:" + mi.getDataFolder().getPath() + "/data/storage;hsqldb.lock_file=false");
            config.setUsername("SA");
            config.setPassword("");
        } else {
            String ip = mi.getString(mi.getMySQLFile(), "Database.IP", "Unknown");
            String dbName = mi.getString(mi.getMySQLFile(), "Database.DB-Name", "Unknown");
            String usrName = mi.getString(mi.getMySQLFile(), "Database.Username", "Unknown");
            String password = mi.getString(mi.getMySQLFile(), "Database.Password", "Unknown");
            String properties = mi.getString(mi.getMySQLFile(), "Database.Properties", "verifyServerCertificate=false&useSSL=false&useUnicode=true&characterEncoding=UTF-8");
            int port = mi.getInteger(mi.getMySQLFile(), "Database.Port", 3306);
            int maxLifeTime = mi.getInteger(mi.getMySQLFile(), "Database.Max-Lifetime", 1800000);

            config.setJdbcUrl("jdbc:" + type.getJdbcProtocol() + "://" + ip + ":" + port + "/" + dbName + "?" + properties);
            config.setUsername(usrName);
            config.setPassword(password);
            config.setMaxLifetime(maxLifeTime);
        }
    }

    public HikariDataSource generateDataSource(){
        return new HikariDataSource(config);
    }
}
