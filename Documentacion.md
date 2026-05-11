# Microservicios
## Catalogo
Se encarga de almacenar los albumes, los formatos de album, la ropa y los productos que se venden en la tienda
para hacer interface con el catalogo se usa un comando que es de busqueda, y retorna tanto el album, como un producto especifico.
## Carrito
Se encarga de pedirle los productos al catalogo y enviarlos a un carrito que es exclusivo por cliente.
Calcula el precio final, iva, etc, y tambien procesa cupones
Conecta directamente con la API de pagos para procesar el pago de los productos del carrito
## Audio
Es un reproductor de audio en la pagina de album. Puede tener multiples archivos llamados "tracks" que van asociados a un album.
## Inventario
Almacenamiento de stock de formatos/ropa, y los asocia a un producto especifico. Luego este stock se envia al usuario acorde a sus datos luego de ser pagado.
## Usuario
Contiene los datos necesarios para las compras. correo, clave, se le asocia el carrito y el pago, y tambien tiene su direccion de casa para enviar productos.
## Comentario
Es texto asociado a los usuarios que aparece en la pagina de un album en especifico. Solo se puede escribir 1 comentario. solo se puede comentar despues de comprar un album. de nuevo, solo para album, no hay comentarios en ropa o formatos
## Pagos
pagos noma, como paypal o visa lo que sea, se accede desde el carrito y se paga acorde al calculo que se hizo ahi
# Relacion de microservicios entre si (que depende de que)
<img width="780" height="705" alt="image" src="https://github.com/user-attachments/assets/c3bf8e6d-abe1-4e67-badf-2837d8c1932b" />
