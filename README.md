# 🛒 **Simpsons Store Back**

Proyecto **Spring Boot CRUD** para administrar y comprar **muñecos de Los Simpsons**.

---

## 🚀 **Descripción**

Este proyecto permite:

- Listar, buscar, crear, actualizar y eliminar productos (muñecos).
- Administrar stock y precios.
- Realizar pedidos con productos seleccionados.
- Carga automática de productos de prueba al iniciar la app.

---

## ⚠️ **Limitación de eliminación de productos**

**No se pueden eliminar productos que tienen pedidos asociados** para mantener la integridad de la base de datos.  
Esto es **intencional y correcto** en bases de datos relacionales para evitar registros huérfanos.

---

## 🛠️ **Tecnologías**

- Java 21
- Spring Boot 3
- Maven
- MySQL 8

---

## 📦 **Instalación y ejecución**

### 1️⃣ Clonar el repositorio

```bash
git clone https://github.com/carreon81/simpsonsstore-back.git
cd simpsonsstore-back

2️⃣ Ejecutar la aplicación
./mvnw clean install
./mvnw spring-boot:run
La API quedará disponible en:

http://localhost:8081

🗂️ Base de datos

✅ No es necesario crear tablas manualmente.

Debes crear la base vacía en MySQL con el nombre simpsonsdb.
Spring Boot generará automáticamente la estructura de tablas.
Al primer arranque, si la tabla simpsons_character está vacía, se cargarán automáticamente los productos iniciales desde:
src/main/resources/data/productos.json
Credenciales de ejemplo
Usuario: simpsons_user
Contraseña: simpsons_pass
📓 Endpoints y pruebas

Puedes acceder al Swagger UI para probar todos los endpoints de forma visual:

http://localhost:8081/swagger-ui.html

👨‍💻 Autor

Desarrollado por Emmanuel Carreón.
