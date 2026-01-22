

# Login con JWT

Este es un proyecto de inicio de sesión con JWT, desarrollado con roles y permisos, usando Spring Boot y Spring Security.

## 🛠️ Tecnologías Utilizadas

    Java 17

    Spring Boot 3

    Spring Data JPA

    Spring Security

    JWT

    Mapstruct

    Swagger

    Docker

    PostgreSQL



## 🐳 Ejecución con Docker
> **Requisitos:**  
> - Docker
> - Configurar el `admin_username` y `admin_password` para el usuario con rol ADMIN en el archivo `docker-compose.yml`  


Clona el repositorio:
```bash
  git clone https://github.com/JamirJS/login.git
```

Accede a la carpeta del proyecto:
```bash
  cd login
```

Inicia la aplicación:
```bash
  docker compose up --build
```

Detén la aplicación:
```bash
  docker compose down
```



## 👤 Autor
JamirJS - [GitHub](https://github.com/JamirJS)

## 🚀 API Reference
Una vez levantada la aplicación, puedes acceder a la documentación Swagger en:

http://localhost:8070/swagger-ui.html

### 🔑 Autenticación
Iniciar sesión

`POST http://localhost:8070/auth/login`

| Parámetro | Tipo     | Descripción                       |
| :-------- | :------- | :-------------------------------- |
| `username`      | `string` | **Requerido**.     |
| `password`      | `string` | **Requerido**.     |

#### Ejemplo de request:
```json
{
    "username": "useradmin",
    "password": "contraseña"
}
```

### Ejemplo de response:
```json
{
    "username": "useradmin",
    "message": "Login Successfull",
    "jwt": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.egViIjoicCIsImF1dGhvcml0aWVzIjoiQ1JFQVRFLERFTEVURSxSRUFELFJPTEVfQURNSU4sVVBEQVRFIiwiaWF0IjoxNzY5MDM1ODQyLCJleHAiOjE3NjkwMzY3NDIsImp0aSI6IjI5Njk2ZDVjLWI1NDMtNGY3ZS1iYWJmLWQ4ZjkyYTg4NTY0NSIsdgdfgdfgdf.fyhfasdmFojwXY0",
    "status": true
}
```
