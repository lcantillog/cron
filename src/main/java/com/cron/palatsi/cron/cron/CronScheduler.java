package com.cron.palatsi.cron.cron;

import com.cron.palatsi.cron.service.ProcesoInterfaz;
import com.cron.palatsi.cron.service.ProductoInterfaz;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@Component
public class CronScheduler {

    private final ProcesoInterfaz interfaz;
    private final ProductoInterfaz productoInterfaz;

    private final AtomicBoolean runningWeb = new AtomicBoolean(false);
    private final AtomicBoolean runningProducto = new AtomicBoolean(false);

    @Value("${scheduler.procesoWebCron}")
    private String procesoWebCron;

    @Value("${scheduler.procesoProductoCron}")
    private String procesoProductoCron;

    public CronScheduler(ProcesoInterfaz interfaz, ProductoInterfaz productoInterfaz) {
        this.interfaz = interfaz;
        this.productoInterfaz = productoInterfaz;
    }

    @Bean
    public TaskScheduler taskScheduler() {
        return new ConcurrentTaskScheduler(Executors.newScheduledThreadPool(2));
    }

    @Bean
    public ScheduledAnnotationBeanPostProcessor postProcessor() {
        return new ScheduledAnnotationBeanPostProcessor();
    }

    @PostConstruct
    public void scheduleTasks() {
        ScheduledTaskRegistrar registrar = new ScheduledTaskRegistrar();
        registrar.setScheduler(taskScheduler());

        registrar.addCronTask(this::runProcesoWeb, procesoWebCron);
        registrar.addCronTask(this::runProcesoProducto, procesoProductoCron);

        registrar.afterPropertiesSet();
    }

    private void runProcesoWeb() {
        if (runningWeb.compareAndSet(false, true)) {
            try {
                long now = System.currentTimeMillis() / 1000;
                System.out.println("INICIALIZACION DE PROCESO WEB -> " + now);
                String result = interfaz.procesoWeb();
                log.info("RESULTADO DE PROCESO => {}", result);
                System.out.println("FINALIZACION DE PROCESO WEB -> " + now);
            } catch (Exception e) {
                log.error("ERROR EN PROCESO WEB", e);
            } finally {
                runningWeb.set(false);
            }
        } else {
            log.warn("PROCESO WEB YA EN EJECUCIÓN, SE OMITE");
        }
    }

    private void runProcesoProducto() {
        if (runningProducto.compareAndSet(false, true)) {
            try {
                long now = System.currentTimeMillis() / 1000;
                System.out.println("INICIALIZACION DE CREACION DE PRODUCTOS -> " + now);
                String result = productoInterfaz.procesoProducto();
                log.info("RESULTADO DE CREACION DE PRODUCTO => {}", result);
                System.out.println("FINALIZACION DE CREACION DE PRODUCTOS -> " + now);
            } catch (Exception e) {
                log.error("ERROR EN CREACION DE PRODUCTOS", e);
            } finally {
                runningProducto.set(false);
            }
        } else {
            log.warn("PROCESO DE PRODUCTOS YA EN EJECUCIÓN, SE OMITE");
        }
    }
}

/*package com.cron.palatsi.cron.cron;

import com.cron.palatsi.cron.service.ProcesoInterfaz;
import com.cron.palatsi.cron.service.ProductoInterfaz;
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

    @Scheduled(fixedDelay = 15000)
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
*/