/*
 * Copyright (c) Waitrose Ltd 2021. All Rights reserved.
 * This software is the confidential and proprietary information of Waitrose Ltd ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with Waitrose Ltd.
 */

package com.jrmcdonald.testcontainers.issue3790.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"com.jrmcdonald.testcontainers.issue3790.repository"})
@EntityScan("com.jrmcdonald.testcontainers.issue3790.model")
public class TestConfiguration {

}
