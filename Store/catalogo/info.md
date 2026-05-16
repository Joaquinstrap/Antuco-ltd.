# Como hacer que funcione el aparato reproductor de catalogo

## Revisa application.properties
Activa la conexion a mysql o mariadb acorde a tu OS. Tambien los datos de usuario, por defecto es usuario root y clave vacia.  
A cualquiera se le puede olvidar. La base de datos se llama `antuco_catalogo_db`, creala por si acaso. 
## Como agregar datos al catalogo
### 1. Album/Merch por SQL Query (Laragon, Dbeaver, etc.)
`INSERT INTO album (titulo, artista, descripcion, portada_url)`  
`VALUES ('La Nueva Cancion Chilena', 'Electrodomesticos', 'Vuelta del hiatus de banda experimental chilena','www.google.com');`  
ID no es necesario agregarlo ya que es autoincremental.  
En el caso de agregar merch el comando es `INSERT INTO merch (nombre, descripcion, merch_url)`, donde el ultimo es un link de una imagen de la prenda.
### 2. Producto por Postman (http://localhost:8080/api/v1/productos)
`{`
	`"sku": "ELECTRO-CD",`
    `"precio": 10000,`
    `"stock": 200,`
    `"especificacion": "{\"formato\": \"CD\", \"duracion\": \"52min\", \"Edicion\": \"Reissue 2012\"}",`
    `"albumId": 1,`
    `"merchId": null`
`}`
Tanto en `albumId` como `merchId` se ingresa el id del objeto ya hecho al que se va a asociar. SOLAMENTE UNO, el otro se deja como null.  
La magia de `especificacion` es que se agrega ahi la informacion del formato. Nunca fue necesario hacer tablas ya que como son datos de un producto fisico no afectan nunca a la base de datos.  
Todavia falta el concepto de carrito, y asociar eso al usuario
