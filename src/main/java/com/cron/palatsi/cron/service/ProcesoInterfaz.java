package com.cron.palatsi.cron.service;

import com.cron.palatsi.cron.entity.Proceso;

import java.util.List;

public interface ProcesoInterfaz {
    List<Proceso> listAllProceso();
    String procesoWeb();

}
