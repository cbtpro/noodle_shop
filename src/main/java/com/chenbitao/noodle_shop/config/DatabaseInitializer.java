package com.chenbitao.noodle_shop.config;

import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Slf4j
@Component
@Profile({"dev", "test"})
public class DatabaseInitializer {

    // ⭐️ 注入 DynamicRoutingDataSource，它是所有数据源的容器
    @Autowired
    private DynamicRoutingDataSource dynamicDataSource;

    private static final String PRIMARY_DATASOURCE_KEY = "primary";

    @PostConstruct
    public void init() {
        // 1. 从 DynamicRoutingDataSource 中获取名为 "primary" 的实际 DataSource
        DataSource primaryDataSource = dynamicDataSource.getDataSource(PRIMARY_DATASOURCE_KEY);

        if (primaryDataSource == null) {
            log.info("⚠️ Cannot find primary datasource named '" + PRIMARY_DATASOURCE_KEY + "', skipping initialization.");
            return;
        }

        try (Connection conn = primaryDataSource.getConnection()) {
            initDatabase(conn);
        } catch (SQLException e) {
            log.info("⚠️ Failed to initialize primary database connection: " + e.getMessage());
        }
    }

    /**
     * 执行 SQL 脚本
     */
    private void initDatabase(Connection conn) {
        try {
            // ⚠️ 建议：在 data.sql/schema.sql 顶部加入清理/删除语句，避免多次重启导致冲突
            
            // 1. 执行建表脚本
            ScriptUtils.executeSqlScript(conn, new ClassPathResource("db/schema.sql"));
            
            // 2. 执行初始化数据脚本
            ScriptUtils.executeSqlScript(conn, new ClassPathResource("db/data.sql"));
            
            log.info("✅ Primary database schema and data initialized successfully.");
        } catch (Exception e) {
            log.info("⚠️ Failed to initialize primary database: " + e.getMessage());
            // 重新抛出 RuntimeException 确保启动失败，以便于调试
            throw new RuntimeException("Primary database initialization failed.", e);
        }
    }

}