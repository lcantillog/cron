package com.cron.palatsi.cron.service;

import com.cron.palatsi.cron.dto.ProcesoDTO;
import com.cron.palatsi.cron.dto.ProductJsonDTO;
import com.cron.palatsi.cron.dto.ProductoDTO;
import com.cron.palatsi.cron.dto.VariantJsonDTO;
import com.cron.palatsi.cron.entity.History;
import com.cron.palatsi.cron.entity.Pagina;
import com.cron.palatsi.cron.entity.Proceso;
import com.cron.palatsi.cron.interfaz.ProcesoInterfaz;
import com.cron.palatsi.cron.pojo.MyPropertyPojo;
import com.cron.palatsi.cron.repository.HisotryRepository;
import com.cron.palatsi.cron.repository.PaginaRepository;
import com.cron.palatsi.cron.repository.ProcesoRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProcesoService implements ProcesoInterfaz {

    @Autowired
    private final ProcesoRepository repository;

    @Autowired
    private final HisotryRepository hisotryRepository;

    @Autowired
        private final MyPropertyPojo property;

    @Autowired
    private final PaginaRepository paginaRepository;
    private final Log LOGGER = LogFactory.getLog(this.getClass());

    @Override
    public List<Proceso> listAllProceso() {
        return repository.findAll();
    }

    @Override
    public String procesoWeb() {
        String url;
        String password;
        Optional<List<Pagina>> paginasWeb = paginaRepository.findByPaginaAndEstado();
        LOGGER.info("paginasWeb IS PRESENTEPagina Web->"+paginasWeb.isPresent());
        if (paginasWeb.isPresent()) {
            LOGGER.info("Ingreso a proceso Pagina Web->");
            for (Pagina pagina : paginasWeb.get()) {
                try {

                    LOGGER.info("Pagina Web->" + pagina.getId());
                    switch (pagina.getId()) {
                        case "Palatsi":
                            url = property.getPalatsi();
                            password = property.getPass_palatsi();
                            break;
                        case "Dolphin":
                            url = property.getDolphin();
                            password = property.getPass_dolphin();
                            break;
                        case "Ellence":
                            url = property.getEllencce();
                            password = property.getPass_ellencce();
                            break;
                        case "Prueba":
                            url = property.getPrueba();
                            password = property.getPass_prueba();
                            break;
                        default:
                            url = "";
                            password = "";
                            break;
                    }

                    LOGGER.info("Valida credenciales Web->" + pagina.getId());
                    LOGGER.info("Valida credenciales pp->" + password);
                    LOGGER.info("Valida credenciales uu->" + url);
                    if (!password.trim().equals("") && !url.trim().equals("")) {

                        LOGGER.info("switch pagina.getFlujoWeb()->" + pagina.getFlujoWeb());
                        switch (pagina.getFlujoWeb()) {
                            case "Shopify":
                                procesoEnvioRequestShopify(pagina, url, password);
                                break;
                            default:
                                Optional<List<Proceso>> opListaProceso = repository.findByPagina(pagina.getId());
                                List<Proceso> lista = opListaProceso.get();
                                if (lista.isEmpty()) {
                                    continue;
                                }
                                procesoEnvioRequest(pagina, opListaProceso.get(), url, password);
                                break;
                        }
                        LOGGER.info("Proceso finalizado exitosamente de la pagina web " + pagina.getId());
                    } else {
                        return "Api key paginas web no configuradas.";
                    }
                } catch (Exception e) {
                    LOGGER.info("Error ->" + e.getMessage());
                }

            }
            return "Proceso finalizado exitosamente...";

        }
        return "Paginas web no configuradas.";
    }

    private void procesoEnvioRequestShopify(Pagina pagina,
                                     String url,
                                     String password) {
        try {

            LOGGER.info("procesoEnvioRequestShopify->"+ pagina.getId());
            RestTemplate restTemplate = new RestTemplate();
            repository.findAll()
                    .forEach(proceso -> {
                        LOGGER.info("procesoEnvioRequestShopify armando jso request->"+ pagina.getId());
                        ProductoDTO prod;
                        List<VariantJsonDTO> listVariantsJson = new ArrayList<>();

                        VariantJsonDTO variantJson = VariantJsonDTO.builder()
                                .sku(proceso.getSku())
                                .price(proceso.getPrecio())
                                .compareAtPrice(proceso.getPrecioAnterior())
                                .inventoryQuantity(proceso.getCantidad()).build();

                        listVariantsJson.add(variantJson);

                        ProductJsonDTO productJson = ProductJsonDTO.builder()
                                .id(proceso.getShopify())
                                .variants(listVariantsJson)
                                .build();

                        prod = ProductoDTO.builder().product(productJson).build();

                        MultiValueMap<String, String> headerss = new LinkedMultiValueMap<>();
                        headerss.add("Content-Type", "application/json");
                        headerss.add("X-Shopify-Access-Token", password);
                        LOGGER.info("procesoEnvioRequestShopify send request->");

                        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT,
                                new HttpEntity<Object>(prod,headerss), String.class);

                        LOGGER.info("procesoEnvioRequestShopify - getStatusCode ->"+ response.getStatusCode());
                        LOGGER.info("procesoEnvioRequestShopify - Status getBody ->"+ response.getBody());
                        History history = History.builder()
                                .shopify(proceso.getShopify())
                                .sku(proceso.getSku())
                                .pagina(proceso.getPagina())
                                .cantidad(proceso.getCantidad())
                                .fecha(proceso.getFecha())
                                .precio(proceso.getPrecio())
                                .precioAnterior(proceso.getPrecioAnterior())
                                .path(url)
                                .request(prod.toString())
                                .codigoEstado(response.getStatusCode().toString())
                                .response(response.getBody()).build();
                        hisotryRepository.save(history) ;
                        repository.delete(proceso);
                    });

        } catch (Exception e) {
            LOGGER.info("Error-procesoEnvioRequestShopify " + e.getMessage());
            LOGGER.info("Error " + e.getMessage());
        }

    }
    private void procesoEnvioRequest(Pagina pagina,
                                     List<Proceso> listaProceso,
                                     String url,
                                     String password) {
        try {

            RestTemplate restTemplate = new RestTemplate();

            List<ProcesoDTO> listProcesoDTO = this.obtenerLista(listaProceso);

            MultiValueMap<String, String> headerss = new LinkedMultiValueMap<>();
            headerss.add("Content-Type", "application/json");
            headerss.add("x-api-key", password);
            HttpEntity<Object> param = new HttpEntity<Object>(listProcesoDTO, headerss);
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST,
                    param, String.class);
            String request = listProcesoDTO.toString();
            LOGGER.info("getStatusCode ->" + response.getStatusCode());
            LOGGER.info("Status getBody ->" + response.getBody());
            LOGGER.info("Status getBody ->" + param.getBody());

            procesoHistoricoAndEliminacionInfo(
                    pagina,
                    listaProceso,
                    request,
                    response,
                    url);
        } catch (Exception e) {
            LOGGER.info("Error " + e.getMessage());
        }

    }

    private void procesoHistoricoAndEliminacionInfo(Pagina pagina,
                                                    List<Proceso> listaProceso,
                                                    String request,
                                                    ResponseEntity<String> response,
                                                    String url) {

        LOGGER.info("Realizando proceso de liminacion ->");
        for (Proceso proceso : listaProceso) {
            if (pagina.isHistorico()) {
                LOGGER.info("Eliminacion de sku  ->" + proceso.getSku());
                History history = History.builder()
                        .shopify(proceso.getShopify())
                        .sku(proceso.getSku())
                        .pagina(proceso.getPagina())
                        .cantidad(proceso.getCantidad())
                        .fecha(proceso.getFecha())
                        .precio(proceso.getPrecio())
                        .precioAnterior(proceso.getPrecioAnterior())
                        .path(url)
                        .request(request)
                        .response(response.getBody())
                        .codigoEstado(response.getStatusCode().toString()).build();
                hisotryRepository.save(history);
            }
            if (pagina.isEliminar()) {
                repository.delete(proceso);
            }
        }
    }

    private List<ProcesoDTO> obtenerLista(List<Proceso> procesoList) {
        return procesoList.stream()
                .map(a -> ProcesoDTO.builder()
                        .stock(a.getCantidad())
                        .sku(a.getSku())
                        .sale_price(a.getPrecioAnterior())
                        .price(a.getPrecio()).build()).collect(Collectors.toList());
    }
}
