☕ Caja de Cafetería Universitaria (Proyecto en Desarrollo)
Bienvenido al sistema de gestión de  Caja de Cafetería Universitaria. Este es un proyecto de aplicación de escritorio en desarrollo, construido con Java Swing, diseñado para explorar y gestionar las operaciones básicas de una cafetería en un entorno universitario. Se enfoca en proporcionar un conjunto de capacidades fundamentales que pueden ser expandidas y mejoradas en el futuro.

🚀 Funcionalidades Implementadas (Estado Actual)
Este sistema ofrece las siguientes funcionalidades, que están en proceso de refinamiento y expansión:

Módulo de Inicio de Sesión:

Autenticación de usuarios con credenciales (usuario y contraseña).

Roles de usuario básicos para controlar el acceso.

Consideración: La gestión de usuarios y roles es funcional, pero el sistema de roles puede ser más granular.

Gestión Básica de Productos:

Interfaz para visualizar, añadir, editar y eliminar productos del inventario.

Funcionalidad para activar o desactivar productos (visible/no visible para la venta).

Consideración: La gestión de stock es un campo crucial que requiere seguimiento y validación constante.

Registro y Consulta de Ventas:

Permite registrar nuevas transacciones de venta, detallando los productos y cantidades.

Calcula automáticamente subtotales, y aplica impuestos (IVA e IVI) y descuentos (actualmente fijo en 0).

Interfaz para consultar ventas realizadas, mostrando un resumen y los detalles de cada transacción.

Consideración: Las reglas de negocio para impuestos y descuentos son básicas y pueden necesitar ser parametrizables.

Generación de Facturas:

Visualización de facturas detalladas por venta.

Funcionalidad para exportar la factura a un archivo de texto plano (.txt).

Consideración: El formato de impresión es simple y no está optimizado para impresoras de tickets.

Herramientas Auxiliares:

Calculadora Integrada: Una herramienta básica para operaciones aritméticas directas.

Consideración: Funcionalidad limitada a operaciones básicas.

Interfaz de Usuario (Look and Feel):

Implementa el tema JTattoo HiFi Look and Feel para mejorar la estética visual estándar de Swing.

Consideración: La aplicación del tema puede ser sensible a la configuración del entorno y requiere una inclusión correcta del JAR.

🛠️ Tecnologías Clave Utilizadas
Java Swing: Framework principal para el desarrollo de la interfaz de usuario de escritorio.

PostgreSQL: Base de datos relacional robusta para la persistencia de datos.

JDBC: Estándar de Java para la conexión y ejecución de consultas SQL a la base de datos.

JTattoo: Librería externa para proporcionar temas de interfaz de usuario avanzados.

Servicios y DAO: El proyecto sigue una arquitectura con capas de Servicio (lógica de negocio) y Data Access Objects (DAO) para la interacción con la base de datos.

📋 Requisitos del Sistema
Para poder configurar y ejecutar este proyecto, necesitarás lo siguiente:

Java Development Kit (JDK) 8 o superior: Asegúrate de que Java esté correctamente instalado y configurado en tu PATH.

Servidor PostgreSQL: Una instancia de PostgreSQL debe estar corriendo y accesible.

NetBeans IDE: El entorno de desarrollo preferido para este proyecto.

⚙️ Configuración del Proyecto (Pasos Críticos)
Sigue estos pasos detallados para configurar el proyecto en tu máquina local. Presta especial atención a los puntos relacionados con la base de datos y JTattoo, que a menudo son fuente de problemas.

1. Obtener el Código Fuente
# Si usas Git (reemplaza <URL_DEL_REPOSITORIO> con la URL real de tu repositorio)
git clone <URL_DEL_REPOSITORIO>
cd CajaDeCafeteriaUniversitaria


Si no usas Git, simplemente descarga y descomprime el archivo ZIP del proyecto.

2. Configurar la Base de Datos PostgreSQL
Crear la Base de Datos y Usuario: Conéctate a tu servidor PostgreSQL (usando pgAdmin, psql, o DBeaver) y ejecuta los siguientes comandos SQL para crear la base de datos y un usuario dedicado (ajusta los nombres y la contraseña):

CREATE DATABASE bdcafeteria;
CREATE USER your_pg_user WITH PASSWORD 'your_pg_password';
GRANT ALL PRIVILEGES ON DATABASE bdcafeteria TO your_pg_user;


Crear el Esquema y Cargar Datos Iniciales:

El proyecto no genera la base de datos automáticamente. Necesitarás ejecutar el script SQL (Database_Creation_SQL_Script.sql o similar, si lo tienes) para crear las tablas (usuarios, productos, ventas, detalles_venta) y cargar los datos de prueba (como el usuario 'admin').

Importante: Si este script no existe o no se ejecuta, la aplicación no encontrará las tablas y fallará.

Puedes ejecutarlo así desde la línea de comandos (asegúrate de que psql esté en tu PATH):

psql -U your_pg_user -d bdcafeteria -h localhost -p 5432 -f path/to/Database_Creation_SQL_Script.sql


Configurar la Conexión en Java:

Abre el archivo infraestructura/ConexionBD.java.

Asegúrate de que las constantes DB_URL, USER, y PASS coincidan exactamente con la configuración de tu base de datos PostgreSQL (jdbc:postgresql://localhost:5432/bdcafeteria, your_pg_user, your_pg_password o los que hayas definido).

3. Añadir la Librería JTattoo a NetBeans (¡Paso Fundamental!)
Una configuración incorrecta de esta librería es una causa común de que el tema no se aplique.

Descarga JTattoo.jar: Obtén el archivo JTattoo.jar de la página oficial de JTattoo. Guárdalo en una ubicación conocida en tu sistema (ej., una carpeta lib dentro de tu proyecto).

Añadir a Librerías del Proyecto en NetBeans:

En NetBeans, en la ventana "Projects" (Proyectos), haz clic derecho en el nodo "Libraries" (Librerías) de tu proyecto.

Selecciona "Add JAR/Folder..." (Añadir JAR/Carpeta...).

Navega hasta el JTattoo.jar que descargaste, selecciónalo y haz clic en "Open".

Verificación: Asegúrate de que JTattoo.jar ahora aparece listado bajo "Libraries". Si no es así, intenta eliminarlo y volver a añadirlo.

4. Configurar la Clase Principal del Proyecto
La aplicación debe saber desde dónde empezar.

En NetBeans, haz clic derecho en el nodo raíz de tu proyecto.

Selecciona "Properties" (Propiedades).

Ve a la categoría "Run" (Ejecutar).

En el campo "Main Class" (Clase Principal), debe estar configurado como app.Main. Si no es así, haz clic en "Browse..." y selecciona app.Main.

▶️ Cómo Ejecutar la Aplicación
Limpiar y Construir el Proyecto (¡Siempre!):

En NetBeans, ve al menú "Run" (Ejecutar) y selecciona "Clean and Build Project" (Limpiar y Construir Proyecto).

Observa la ventana de "Output" (Salida) en la parte inferior. Deberías ver "BUILD SUCCESSFUL". Si hay errores, resuélvelos antes de continuar.

Ejecutar el Proyecto:

Haz clic en el botón "Run Project" (Ejecutar Proyecto) (el icono de play verde) o ve a "Run" > "Run Project".

Revisa la ventana "Output": Busca mensajes de depuración como DEBUG (app.Main): Intentando establecer HiFiLookAndFeel... y cualquier ERROR: si el tema no se aplica. Estos mensajes son cruciales para el diagnóstico.

La ventana de inicio de sesión debería aparecer ahora con el tema JTattoo HiFi aplicado. Si aún tienes problemas, revisa los mensajes de error en la consola.

💾 Respaldo de la Base de Datos (Backup Recomendado)
Es absolutamente crucial realizar copias de seguridad regulares de tu base de datos para prevenir la pérdida de datos. La herramienta estándar de PostgreSQL para esto es pg_dump.

Para realizar un backup desde la línea de comandos:
pg_dump -U <usuario_db> -h <host_db> -p <puerto_db> <nombre_db> > /ruta/donde/guardar/tu_backup_nombre_fecha.sql


Ejemplo Práctico:

pg_dump -U your_pg_user -h localhost -p 5432 bdcafeteria > C:/backups/bdcafeteria_20250809.sql

Este comando creará un archivo SQL que contiene la estructura y los datos de tu base de datos. Puedes usar psql para restaurarlo en caso de necesidad.

📞 Contacto y Soporte
Si tienes alguna pregunta, encuentras algún problema o necesitas ayuda adicional con el proyecto, por favor,  contacta al desarrollador.
