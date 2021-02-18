/*
 * Copyright (c) Waitrose Ltd 2021. All Rights reserved.
 * This software is the confidential and proprietary information of Waitrose Ltd ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with Waitrose Ltd.
 */

package com.jrmcdonald.testcontainers.issue3790.container;

import com.github.dockerjava.api.command.CreateContainerCmd;
import com.github.dockerjava.api.model.HealthCheck;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.testcontainers.containers.BindMode;
import org.testcontainers.containers.PostgreSQLContainer;

import static java.util.Arrays.asList;
import static java.util.concurrent.TimeUnit.NANOSECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.testcontainers.containers.wait.strategy.Wait.forHealthcheck;

@SuppressWarnings("rawtypes")
public final class PostgresSqlContainerFactory {

    private static final String DATABASE_NAME = "bookentity";

    private static PostgreSQLContainer container;

    public static synchronized PostgreSQLContainer getInstance() {
        if (container == null) {
            container = buildPostgresContainer();
        }
        return container;
    }

    public static void registerProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", () -> getInstance().getJdbcUrl());
        registry.add("spring.datasource.username", () -> getInstance().getUsername());
        registry.add("spring.datasource.password", () -> getInstance().getPassword());
    }

    @SuppressWarnings("java:S2095") // the lifecycle of this resource is managed externally
    private static PostgreSQLContainer buildPostgresContainer() {
        HealthCheck isPgReadyHealthCheck = new HealthCheck()
                .withTest(asList("CMD-SHELL", "pg_isready -U postgres"))
                .withInterval(NANOSECONDS.convert(10, SECONDS))
                .withTimeout(NANOSECONDS.convert(10, SECONDS))
                .withRetries(5);

        return (PostgreSQLContainer) new PostgreSQLContainer(PostgreSQLContainer.IMAGE)
                .withUsername(DATABASE_NAME)
                .withPassword(DATABASE_NAME)
                .withDatabaseName(DATABASE_NAME)
                .waitingFor(forHealthcheck())
                .withCreateContainerCmdModifier(cmd -> ((CreateContainerCmd) cmd).withHealthcheck(isPgReadyHealthCheck))
                .withClasspathResourceMapping("db-init/", "/docker-entrypoint-initdb.d", BindMode.READ_WRITE);
    }
}
