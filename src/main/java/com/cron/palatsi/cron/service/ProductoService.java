package com.cron.palatsi.cron.service;

import com.cron.palatsi.cron.dto.SkuDto;
import com.cron.palatsi.cron.entity.Articulo;
import com.cron.palatsi.cron.interfaz.ProductoInterfaz;
import com.cron.palatsi.cron.pojo.MyPropertyPojo;
import com.cron.palatsi.cron.repository.ArticuloRepository;
import com.cron.palatsi.cron.repository.ProcesoRepository;
import com.cron.palatsi.cron.utility.Constante;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductoService implements ProductoInterfaz {

    @Autowired
    private final MyPropertyPojo property;

    @Autowired
    private final ArticuloRepository repository;

    @Override
    public String procesoProducto() {

        RestTemplate restTemplate = new RestTemplate();

        MultiValueMap<String, String> headerss = new LinkedMultiValueMap<>();
        headerss.add("Content-Type", "application/json");
        headerss.add("x-api-key", property.getPass_palatsi());

        HttpEntity<Object> param = new HttpEntity<Object>( headerss);
        ResponseEntity<SkuDto> response = restTemplate.exchange(property.getPalatsi_prod_new(), HttpMethod.GET,
                param,  new ParameterizedTypeReference<SkuDto>() {
                });
        if(response.getStatusCode() == HttpStatus.OK){
            SkuDto respon = response.getBody();
            for(String sku : respon.products){
                log.info("Service method called using @Slf4j",sku);
                boolean existe =repository.existsArticuloBySku(sku);
                Integer cantidad = repository.getCantidadArticulo(sku);
                double precio = repository.getPrecioArticulo(sku);
                if(!existe){

                    Articulo articuloDB = new Articulo();
                    articuloDB.setSku(sku);
                    articuloDB.setShopify(sku);
                    articuloDB.setPagina(Constante.PALATSI);
                    articuloDB.setVariante(Constante.VARIANTE);
                    articuloDB.setPrecio(precio);
                    articuloDB.setPrecioAnterio(0);
                    articuloDB.setCantidad(cantidad);
                    articuloDB.setListaPrecio(Constante.COD_LISTA_PRECIO);
                    repository.save(articuloDB);
                }
            }
            return "Proceso de migrado articulo terminado.";
        }else {
            return "No se encontraron productos a migrar.";
        }
    }
}
