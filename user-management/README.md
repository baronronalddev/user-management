### MICROSERVICIO DE USER MANAGEMENT

- Servicio que crea usuarios.

Tener en cuenta las siguientes configuraciones :

![](docs/properties.png)

- El servicio se manejará en el puerto 8099  para pruebas locales.
- Las configuraciones  de la base de datos en memoria (H2) .
- Las configuraciones de usuario para el manejo de seguridad de los microservicios .
- Finalmente tenemos las configuración para utilizar las librerías para implementar   	swagger en el proyecto.

####API TOKEN :

- Servicio que  generará un token  mediante un usuario y contraseña configurada.
- Este token será utilizado por el microservicio de user management.

![](docs/generated_token.png)

####Prueba del flujo de token.

CASO I :  ERROR 401 - UNAUTHORIZED

![](docs/welcome_error_401.png)

CASO II : EXITOSO.

![](docs/welcome_ok_200.png)

####Prueba del flujo del servicio de user-managemet

Tener en cuenta  los siguientes campos a crear :

Campos a considerar en la BD: usuariosdb    y tb :  user_nisum 

|  CAMPO | DESCRIPCIÓN  |
| ------------ | ------------ |
|  ID |  Identificar del usuario creado  en formato UUID |
|  PHONES | Detalle de líneas telefónicas |
| CREATED  | Fecha de creación del usuario  |
| EMAIL  | Correo del usuario  |
| ISACTIVE |Indica si el usuario sigue habilitado dentro del sistema. |
|LAST_LOGIN   | Coincide con la fecha de creación del usuario  |
| MODIFIED  | fecha de la última actualización de usuario  |
|NAME   |  Nombre del usuario |
|PASSWORD   | Clave del usuario   |
|TOKEN |  Sas Token que viene el servicio que genera token.  | |

Levatamos el proyecto  en el puerto 8099:

Visualizamos el login de H2 :

![](docs/login_h2.png)

Obtenemos la BD: usuariosdb    y tb :  user_nisum :

![](docs/tb_user_nisum_parameters.png)

Probamos el servicio en postman:

CASO 1 : ERROR 401 - UNAUTHORIZED

![](docs/create_user_register_401.png)

Para ello obetner el token del servicio de api token  :

![](docs/create_user_register_token.png)

CASO 2:  CASO EXITOSO  201 - CREATED

![](docs/created_user_register_ok.png)

y está se registrará en la tb user_nisum :

![](docs/tb_user_nisum_view.png)


CASO 3 : Correo en formato inválido :

![](docs/create_user_register_email_invalid.png)


CASO 4 : Cuando el campo email es requerido :

![](docs/created_user_register_email_requerid.png)

CASO 5 : El correo ya está registrado:

![](docs/created_user_register_email_ya_registrado.png)


