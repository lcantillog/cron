package com.cron.palatsi.cron.pojo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Data
@ConstructorBinding
@ConfigurationProperties(prefix = "my.url")
public class MyPropertyPojo {
    private String palatsi;
    private String palatsi_prod_new;
    private String dolphin;
    private String ellencce;
    private String prueba;
    private String pass_palatsi;
    private String pass_dolphin;
    private String pass_ellencce;
    private String pass_prueba;
}
