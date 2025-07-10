package com.simpsons.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.simpsons.model.Producto;
import com.simpsons.repository.ProductoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;

@Component
@Profile("!test") // evita que se ejecute en perfil de test
public class DataLoader implements CommandLineRunner {

    private final ProductoRepository productoRepository;
    private final ObjectMapper objectMapper;

    public DataLoader(ProductoRepository productoRepository, ObjectMapper objectMapper) {
        this.productoRepository = productoRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run(String... args) throws Exception {
        long count = productoRepository.count();
        System.out.println("DEBUG: Cantidad de productos en base al iniciar = " + count);

        if (count == 0) {
            System.out.println("Cargando productos iniciales...");

            InputStream inputStream = new ClassPathResource("data/productos.json").getInputStream();
            List<Producto> productos = objectMapper.readValue(inputStream, new TypeReference<List<Producto>>() {});

            productoRepository.saveAll(productos);

            System.out.println("Productos iniciales cargados correctamente.");
        } else {
            System.out.println("Productos ya existen, no se cargan nuevamente.");
        }
    }

}
