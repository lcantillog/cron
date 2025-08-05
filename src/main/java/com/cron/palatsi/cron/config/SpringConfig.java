package com.cron.palatsi.cron.config;

import com.cron.palatsi.cron.pojo.MyPropertyPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@EnableConfigurationProperties(MyPropertyPojo.class)
public class SpringConfig {

}
