package com.epam.esm.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

@Configuration
public class TestSpringConfig extends LogicSpringConfig {
    private static final String DATA_BASE_STRUCTURE_SCRIPT = "sql/db_structure.sql";
    private static final String DATA_BASE_DATA_SCRIPT = "sql/db_data.sql";
    private static final String UTF8_ENCODING = "UTF-8";

    @Override
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .setScriptEncoding(UTF8_ENCODING)
                .addScript(DATA_BASE_STRUCTURE_SCRIPT)
                .addScript(DATA_BASE_DATA_SCRIPT)
                .build();
    }
}