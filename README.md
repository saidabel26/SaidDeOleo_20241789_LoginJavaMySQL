# 📋 Sistema de Login con Java Swing y MySQL

- **Aplicación Java Swing** con conexión a MySQL para registro, login y CRUD de usuarios
- **Autor**: Said De Oleo
- **Matrícula**: 20241789
- **Asignatura**: Programación Orientada a Objetos

## 🛠 Tecnologías Utilizadas
- **Java 17** (JDK)
- **Swing** (Interfaz gráfica)
- **MySQL** (Base de datos)
- **Patrones de Diseño**: Singleton, DAO

## 🚀 Instalación y Ejecución

### Requisitos Previos
- MySQL Server instalado
- Java 17 o superior
- Librería [Microsoft JDBC Driver](https://learn.microsoft.com/en-us/sql/connect/jdbc/download-microsoft-jdbc-driver-for-sql-server?view=sql-server-ver16) en `src/lib/`

### Pasos para Configurar
1. Clonar el repositorio:

    ```bash
    git clone https://github.com/tu-usuario/SaidDeOleo_20241789_LoginJavaMySQL.git

## 🔧 Configurar la base de datos
**Ejecuta en MySQL:**

    CREATE DATABASE tarea4;
    GO

    USE tarea4;
    GO

    CREATE TABLE usuarios (
        usuario VARCHAR(50) PRIMARY KEY,
        nombre VARCHAR(50),
        apellido VARCHAR(50),
        telefono VARCHAR(15),
        correo VARCHAR(100),
        contraseña VARCHAR(100)
    );

## ▶️ Ejecución
1. **Importar**: En tu IDE, abre la carpeta `SaidDeOleo_20241789_Tarea4`.
2. **Ejecutar**:  
   - Clase principal: `src/Main.java`.  
   - Dependencia: Asegúrate de tener `mysql-connector-java-*.jar` en `src/lib/`.

## 📂 Estructura del Proyecto:
    SaidDeOleo_20241789_LoginJavaMySQL/
    ├── src/
    │ ├── dao/
    │ │ ├── UsuarioDAO.java
    │ │ └── DatabaseConnection.java
    │ ├── modelo/
    │ │ └── Usuario.java
    │ ├── vista/
    │ │ ├── LoginFrame.java
    │ │ └── PrincipalFrame.java
    │ ├── lib/
    │ │ └── msssql-jdbc-(versión).jre11.jar
    │ └── Main.java
    ├── .gitignore
    └── README.md

## 🛠 Guí de Soluciones a Problemas con la Conexión a SQL Server

    -- =============================================
    -- 1. CONFIGURACIÓN INICIAL DEL SERVIDOR SQL
    -- =============================================
    
    -- Verificar estado del puerto
    EXEC xp_readerrorlog 0, 1, N'Server is listening on';
    
    -- Ver información de la instancia
    SELECT @@SERVERNAME AS [Server Name], 
           @@SERVICENAME AS [Service Name];
    
    -- =============================================
    -- 2. SOLUCIÓN ERROR "TCP/IP CONNECTION FAILED"
    -- =============================================
    
    /* Pasos en SQL Server Configuration Manager:
    1. SQL Server Network Configuration → Protocols for [INSTANCIA]
    2. Habilitar TCP/IP
    3. IP Addresses → IPAll → TCP Port = 1433
    4. Reiniciar servicio SQL Server */
    
    -- =============================================
    -- 3. CONFIGURACIÓN DE FIREWALL (POWERSHELL ADMIN)
    -- =============================================
    
    New-NetFirewallRule -DisplayName "SQLServer-1433" `
        -Direction Inbound `
        -Protocol TCP `
        -LocalPort 1433 `
        -Action Allow;
    
    -- =============================================
    -- 4. SOLUCIÓN ERROR "LOGIN FAILED FOR USER"
    -- =============================================
    
    -- Crear usuario con permisos completos
    USE master;
    GO
    CREATE LOGIN app_user WITH PASSWORD = 'PasswordSegura123!';
    GO
    USE tarea4;
    GO
    CREATE USER app_user FOR LOGIN app_user;
    GO
    GRANT CONTROL ON DATABASE::tarea4 TO app_user;
    GO
    
    -- =============================================
    -- 5. CADENA DE CONEXIÓN RECOMENDADA
    -- =============================================
    
    /*
    String url = "jdbc:sqlserver://127.0.0.1:1433;"
               + "databaseName=tarea4;"
               + "encrypt=true;"
               + "trustServerCertificate=true;"
               + "loginTimeout=30;";
    String user = "app_user";
    String password = "PasswordSegura123!";
    */
    
    -- =============================================
    -- 6. COMANDOS ÚTILES PARA DIAGNÓSTICO
    -- =============================================
    
    -- Verificar conexión con telnet (CMD Admin)
    /*
    telnet 127.0.0.1 1433
    */
    
    -- Ver procesos usando el puerto
    /*
    netstat -ano | findstr 1433
    */
    
    -- Habilitar conexiones remotas en SQL Server
    /*
    EXEC sp_configure 'remote access', 1;
    RECONFIGURE;
    */