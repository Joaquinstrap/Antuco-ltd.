dependencias, ¿Quien necesita depender de quien?.

- Microservicios los cuales no dependen de nadie
	- Servicio de Catalogo
	- Servicio de usuarios / Autenticacion
	- Servicio de Eventos

- Mircroservicios que dependen de los de arriba
	- Servicio de inventario: Depende del catalogo, porque si hay o no stock de un producto no sirve de nada si no se sabe que producto es
	
	- Servicio de media: Depende del catalogo, ya que guarda las URLs de las imagenes de los albumes o las poleras asociadas por ID.
	 
	- Servicio de comentarios: Depende del de usuarios y del catalogo. Porque necesitamos saber a que usuario escribio el comentario por el id usuario y el catalogo para saber por la id del producto a que producto esta comentando.


- Microservicios que van por flujo transaccional.

	- Servicio del Carrito: Depende del usuario y carrito, del cual requiero al usuario que hace la peticion para el carrito y que producto el usuario quiere con la id de producto
	
	- Servicio de pedido: El carrito le envia la informacion al pedido el cual genera un numero de orden, lo guarda a la espera del pago, ya que se necesita a registrar a alguien que va a comprar pero aun no ha puesto el dinero.
	
	- Servicio de pago: aqui el servicio envia una alerta que dice: "El cobro de la orden #0010 por un total de $".
	 El servicio de pago habla nose, Stripe, Matchbank, Transbank. y a partir de la respuesta se genera el efecto.
		- Si el pago es rechazado: El pedido emite una alerta de error, no se descuenta el producto y, ademas no se realiza el pago.
		- Si el pago es aprobado: se genera un mensaje de "Pago de la orden #0010 exitoso" al ser aprobado: El servicio de pedidos pasa la orden a pagada / En preparacion. Ademas de al inventario al ser pagado el producto se le restara el stock dependiendo de la cantidad del producto que fueron solicitadas
