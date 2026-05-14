# Catalogo
Se encarga de almacenar los albumes, los formatos de album, la ropa y los productos que se venden en la tienda para hacer interface con el catalogo se usa un comando que es de busqueda, y retorna tanto el album, como un producto especifico.

## descripcion abstracta de datos
Un "album" es una representacion de un grupo de canciones de un artista. Tiene nombre, fecha, portada y canciones.
al usar el buscador y abrir un articulo, lo que se abre es el album. Pero se trabaja con ediciones

Una edicion es el album, pero en un formato. Puede ser digital, CD, vinilo, cassette, etc. van asociadas al album.
se compra una edicion entonces? NO, se compra un producto

Un producto es una edicion que se vende. un producto puede tener varias ediciones, como una con un vinilo + cd, o + polera.

La ropa es como una edicion, pero que no esta asociada a un album.

## Que hace el microservicio
Guarda una base de datos de los albumes, de las ediciones, de la ropa y de los productos.
Permite buscar mediante entrada de texto un album especifico, o directamente manda productos al carrito del usuario. El carrito es un microservicio separado.
