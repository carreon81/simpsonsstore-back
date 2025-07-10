package com.simpsons.controller;

import com.simpsons.model.Producto;
import com.simpsons.repository.ProductoRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productos")
@CrossOrigin(origins = "*")
public class ProductoController {

    private final ProductoRepository repository;

    public ProductoController(ProductoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Producto> getAll() {
        return repository.findAll();
    }

    @GetMapping("/search")
    public List<Producto> searchByName(@RequestParam String name) {
        return repository.findByNameContainingIgnoreCase(name);
    }

    @PostMapping
    public Producto create(@RequestBody Producto producto) {
        if (producto.getStock() != null && producto.getStock() < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo.");
        }
        return repository.save(producto);
    }

    @GetMapping("/{id}")
    public Producto getById(@PathVariable Long id) {
        return repository.findById(id).orElseThrow();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Producto productoActualizado) {
        return repository.findById(id).map(producto -> {
            if (productoActualizado.getStock() != null) {
                if (productoActualizado.getStock() < 0) {
                    throw new IllegalArgumentException("El stock no puede ser negativo.");
                }
                producto.setStock(productoActualizado.getStock());
            }
            if (productoActualizado.getName() != null) producto.setName(productoActualizado.getName());
            if (productoActualizado.getDescription() != null) producto.setDescription(productoActualizado.getDescription());
            if (productoActualizado.getPrecio() != null) producto.setPrecio(productoActualizado.getPrecio());
            if (productoActualizado.getCategoria() != null) producto.setCategoria(productoActualizado.getCategoria());
            if (productoActualizado.getImageUrl() != null) producto.setImageUrl(productoActualizado.getImageUrl());

            repository.save(producto);
            return ResponseEntity.ok(producto);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return repository.findById(id).map(producto -> {
            // Verificar si tiene pedidos asociados
            if (producto.getLineas() != null && !producto.getLineas().isEmpty()) {
                return ResponseEntity.badRequest().body("No se puede eliminar: el producto tiene pedidos asociados.");
            }

            repository.delete(producto);
            return ResponseEntity.ok("Producto eliminado correctamente.");
        }).orElse(ResponseEntity.notFound().build());
    }
}
