package com.cron.palatsi.cron.config;

import com.cron.palatsi.cron.config.pojo.MyPropertyPojo;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@EnableConfigurationProperties(MyPropertyPojo.class)
public class SpringConfig {

}
