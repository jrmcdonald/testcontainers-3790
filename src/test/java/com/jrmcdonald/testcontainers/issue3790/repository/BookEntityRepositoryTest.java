package com.jrmcdonald.testcontainers.issue3790.repository;

import com.jrmcdonald.testcontainers.issue3790.config.PostgresIntegrationTestConfiguration;
import com.jrmcdonald.testcontainers.issue3790.container.PostgresSqlContainerFactory;
import com.jrmcdonald.testcontainers.issue3790.model.BookEntity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@PostgresIntegrationTestConfiguration
@ExtendWith({SpringExtension.class})
@TestPropertySource(properties = {"logging.level.org.hibernate.SQL=DEBUG", "logging.level.org.hibernate.type=TRACE"})
class BookEntityRepositoryTest {

    @Container
    @SuppressWarnings("rawtypes")
    private static final PostgreSQLContainer postgreSQLContainer = PostgresSqlContainerFactory.getInstance();

    @Autowired
    private BookEntityRepository repository;

    @DynamicPropertySource
    public static void registerProperties(DynamicPropertyRegistry registry) {
        PostgresSqlContainerFactory.registerProperties(registry);
    }

    @Test
    void shouldCreateBookEntity() {
        var bookEntity = new BookEntity();
        var savedBookEntity = repository.saveAndFlush(bookEntity);
        assertThat(savedBookEntity.getId()).isNotNull();
    }
}