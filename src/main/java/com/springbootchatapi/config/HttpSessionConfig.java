package com.springbootchatapi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;

//@EnableJdbcHttpSession(tableName = "custom_session_table")

@Configuration
@EnableJdbcHttpSession

public class HttpSessionConfig {
}
