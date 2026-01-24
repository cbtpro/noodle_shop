package com.chenbitao.noodle_shop.config;

import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner; // ⭐️ 引入 ApplicationRunner
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
// ⭐️ 实现 ApplicationRunner 接口
public class DatabaseInitializer implements ApplicationRunner { 

    // 注入 DynamicRoutingDataSource，它是所有数据源的容器
    @Autowired
    private DynamicRoutingDataSource dynamicDataSource;

    private static final String PRIMARY_DATASOURCE_KEY = "primary";
    
    // ⭐️ 移除 @PostConstruct，将初始化逻辑移至 run 方法
    @Override
    public void run(ApplicationArguments args) {
        log.info("▶️ Starting database initialization (via ApplicationRunner)...");
        
        // 1. 从 DynamicRoutingDataSource 中获取名为 "primary" 的实际 DataSource
        DataSource primaryDataSource = dynamicDataSource.getDataSource(PRIMARY_DATASOURCE_KEY);

        if (primaryDataSource == null) {
            log.warn("⚠️ Cannot find primary datasource named '" + PRIMARY_DATASOURCE_KEY + 
                     "'. Please check dynamic datasource configuration. Skipping initialization.");
            return;
        }

        try (Connection conn = primaryDataSource.getConnection()) {
            initDatabase(conn);
        } catch (SQLException e) {
            log.error("❌ Failed to establish primary database connection.", e);
            throw new RuntimeException("Primary database connection failed.", e);
        }
    }

    /**
     * 执行 SQL 脚本
     */
    private void initDatabase(Connection conn) {
        try {
            // 1. 执行建表脚本
            ScriptUtils.executeSqlScript(conn, new ClassPathResource("db/schema.sql"));
            
            // 2. 执行初始化数据脚本
            ScriptUtils.executeSqlScript(conn, new ClassPathResource("db/data.sql"));
            
            log.info("✅ Primary database schema and data initialized successfully.");
        } catch (Exception e) {
            log.error("❌ Failed to initialize primary database.", e);
            // 重新抛出 RuntimeException 确保启动失败，以便于调试
            throw new RuntimeException("Primary database initialization failed.", e);
        }
    }
}