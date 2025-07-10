package com.simpsons.controller;
import com.simpsons.dto.PedidoItem;
import com.simpsons.dto.PedidoRequest;
import com.simpsons.model.LineaPedido;
import com.simpsons.model.Pedido;
import com.simpsons.model.Producto;
import com.simpsons.repository.PedidoRepository;
import com.simpsons.repository.ProductoRepository;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/pedidos")
@CrossOrigin(origins = "*")
public class PedidoController {

    private final PedidoRepository pedidoRepository;
    private final ProductoRepository productoRepository;

    public PedidoController(PedidoRepository pedidoRepository, ProductoRepository productoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.productoRepository = productoRepository;
    }

    @PostMapping
    public ResponseEntity<?> crearPedido(@RequestBody PedidoRequest request) {
        System.out.println("📌 Pedido recibido: " + request);
        List<LineaPedido> lineas = new ArrayList<>();
        double total = 0.0;

        for (PedidoItem item : request.getItems()) {
            Producto producto = productoRepository.findById(item.getProductoId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + item.getProductoId()));

            if (producto.getStock() < item.getCantidad()) {
                return ResponseEntity.badRequest().body("Stock insuficiente para: " + producto.getName());
            }

            double subtotal = producto.getPrecio() * item.getCantidad();
            total += subtotal;

            LineaPedido linea = new LineaPedido();
            linea.setProducto(producto);
            linea.setCantidad(item.getCantidad());
            linea.setSubtotal(subtotal);
            lineas.add(linea);

            producto.setStock(producto.getStock() - item.getCantidad());
            productoRepository.save(producto);
        }

        Pedido pedido = new Pedido();
        pedido.setFecha(LocalDateTime.now());
        pedido.setTotal(total);

        pedido.setEstado("Pendiente");


        for (LineaPedido linea : lineas) {
            linea.setPedido(pedido);
        }

        pedido.setLineas(lineas);
        pedidoRepository.save(pedido);

        return ResponseEntity.ok(pedido);
    }

    @GetMapping
    public List<Pedido> obtenerPedidos() {
        return pedidoRepository.findAll();
    }

    @GetMapping("/test")
    public String test() {
        System.out.println("Endpoint /pedidos/test llamado correctamente.");
        return "Controlador de pedidos funcionando.";
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<String> borrarTodo() {
        pedidoRepository.deleteAll();
        return ResponseEntity.ok("Todos los pedidos eliminados.");
    }

    // Cancela un pedido y actualiza su estado
    @PutMapping("/{id}/cancelar")
    public ResponseEntity<String> cancelarPedido(@PathVariable Long id) {
        Optional<Pedido> pedidoOpt = pedidoRepository.findById(id);
        if (pedidoOpt.isPresent()) {
            Pedido pedido = pedidoOpt.get();
            pedido.setEstado("Cancelado");

            for (LineaPedido linea : pedido.getLineas()) {
                Producto producto = productoRepository.findById(linea.getProducto().getId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado al cancelar: " + linea.getProducto().getId()));
                
                producto.setStock(producto.getStock() + linea.getCantidad());
                productoRepository.save(producto);
            }

            pedidoRepository.save(pedido);
            return ResponseEntity.ok("Pedido cancelado correctamente y stock restaurado.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Aprueba pedido
    @PutMapping("/{id}/aprobar")
    public ResponseEntity<String> apruebaPedido(@PathVariable Long id) {
        Optional<Pedido> pedidoOpt = pedidoRepository.findById(id);
        if (pedidoOpt.isPresent()) {
            Pedido pedido = pedidoOpt.get();
            pedido.setEstado("Aprobado");

            for (LineaPedido linea : pedido.getLineas()) {
                Producto producto = productoRepository.findById(linea.getProducto().getId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado al intentar aprobar: " + linea.getProducto().getId()));
                
                productoRepository.save(producto);
            }

            pedidoRepository.save(pedido);
            return ResponseEntity.ok("Pedido aprobado correctamente.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
