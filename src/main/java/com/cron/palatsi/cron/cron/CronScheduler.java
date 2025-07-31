package com.cron.palatsi.cron.cron;

import com.cron.palatsi.cron.interfaz.ProcesoInterfaz;
import com.cron.palatsi.cron.interfaz.ProductoInterfaz;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
@Slf4j
@Component
public class CronScheduler {

    @Autowired
    private ProcesoInterfaz interfaz;
    @Autowired
    private ProductoInterfaz productoInterfaz;

    @Scheduled(fixedDelay = 3000)
    public void schedulerTaskUsingCronExpression(){
        long now = System.currentTimeMillis()/1000;
        System.out.println("INICIALIZACION DE PROCESO WEB -> "+ now);
        String result = interfaz.procesoWeb();
        log.info("RESULTADO DE PROCESO =>",result);
        System.out.println("FINLALIZACION DE PROCESO WEB -> "+now);
    }

    //0 9,21 * * *
    //Cron expression to run a job twice a day at 9am and 9pm
    //@Scheduled(cron = "0 9,21 * * *")
  //  @Scheduled(fixedDelay = 6000)
    public void schedulerTaskUsingCron(){
        long now = System.currentTimeMillis()/1000;
        System.out.println("INICIALIZACION DE CREACION DE PRODUCTOS -> "+ now);
        String result = productoInterfaz.procesoProducto();
        log.info("RESULTADO DE CREACION DE PRODUCTO", result);
        System.out.println("FINLALIZACION DE CREACION DE PRODUCTOS -> "+ now);
    }
}
