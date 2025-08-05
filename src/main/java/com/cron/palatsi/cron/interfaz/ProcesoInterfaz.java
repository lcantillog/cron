package com.cron.palatsi.cron.interfaz;

import com.cron.palatsi.cron.entity.Proceso;

import java.util.List;

public interface ProcesoInterfaz {
    List<Proceso> listAllProceso();
    String procesoWeb();

}
