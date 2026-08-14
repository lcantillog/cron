package com.cron.palatsi.cron.service.impl;

import com.cron.palatsi.cron.config.pojo.Property;
import com.cron.palatsi.cron.dto.SkuDto;
import com.cron.palatsi.cron.entity.Articulo;
import com.cron.palatsi.cron.service.ProductoInterfaz;
import com.cron.palatsi.cron.config.pojo.MyPropertyPojo;
import com.cron.palatsi.cron.repository.ArticuloRepository;
import com.cron.palatsi.cron.utility.Constante;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.*;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductoService implements ProductoInterfaz {

    @Autowired
    private final MyPropertyPojo property;

    @Autowired
    private final Property propertys;

    @Autowired
    private final ArticuloRepository repository;

    @Value("${config.empresa}")
    private Integer empresa;

    @Value("${config.listaPrecio}")
    private Integer listaPrecio;

    @Value("${config.bodega}")
    private Integer bodega;

    @Value("${config.pagina}")
    private String pagina;

    @Override
    public String procesoProducto() {
        try {
            RestTemplate restTemplate = new RestTemplate();

            MultiValueMap<String, String> headerss = new LinkedMultiValueMap<>();
            headerss.add("Content-Type", "application/json");
            headerss.add("x-api-key", property.getPass_palatsi_prod_new());
            headerss.add("User-Agent", "Palatsi-Sync/1.0");

            HttpEntity<Object> param = new HttpEntity<Object>(headerss);
            ResponseEntity<SkuDto> response = restTemplate.exchange(property.getPalatsi_prod_new(), HttpMethod.GET,
                    param, new ParameterizedTypeReference<SkuDto>() {
                    });
            if (response.getStatusCode() == HttpStatus.OK) {
                SkuDto respon = response.getBody();
                for (String sku : respon.products) {
                    log.info("Service method called using @Slf4j", sku);
                    boolean existe = repository.existsArticuloBySku(sku);
                    Integer cantidad = repository.getCantidadArticulo(empresa,bodega,sku);
                    double precio = repository.getPrecioArticulo(empresa,listaPrecio,sku);
                    if (!existe) {
                        try {
                            Articulo articuloDB = new Articulo();
                            articuloDB.setSku(sku);
                            articuloDB.setShopify(sku);
                            articuloDB.setPagina(pagina);
                            articuloDB.setVariante(Constante.VARIANTE);
                            articuloDB.setPrecio(precio);
                            articuloDB.setPrecioAnterio(0);
                            articuloDB.setCantidad(cantidad);
                            articuloDB.setListaPrecio(listaPrecio);
                            repository.save(articuloDB);
                        }catch (Exception e){
                            System.err.println("Error al intengar guardar el articulo: " + e.getMessage());
                        }
                    }
                }
                return "Proceso de migrado articulo terminado.";
            } else {
                return "No se encontraron productos a migrar.";
            }
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            // Errores HTTP (4xx o 5xx)
            System.err.println("Error de respuesta HTTP: " + ex.getStatusCode() + " - " + ex.getResponseBodyAsString());
        } catch (
                ResourceAccessException ex) {
            // Problemas de conexión
            System.err.println("Error de acceso al recurso: " + ex.getMessage());
        } catch (
                RestClientException ex) {
            // Cualquier otro error del cliente
            System.err.println("Error en RestTemplate: " + ex.getMessage());
        } catch (Exception ex) {
            // Cualquier otra excepción no prevista
            System.err.println("Error inesperado: " + ex.getMessage());
        }

        return "Error al consumir el servicio.";
    }

    public String getUrl(String key) {
        if (propertys.getUrls() == null || key == null || key.isEmpty()) {
            return "Clave inválida";
        }
        return propertys.getUrls().getOrDefault(key, "URL no encontrada");
    }

}
