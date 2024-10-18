package com.haile.exe101.depairapplication.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Optional;

@Configuration
public class AuditingDateTimeConfig {
    @Bean
    public DateTimeProvider auditingDateTimeProvider() {
        return () -> Optional.of(OffsetDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh")));
    }
}
