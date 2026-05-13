#### @NotNull(message = "...")
> envía el mensaje en caso de que no se escriba nada y es con int

#### @NotBlank(message = "...")
> lo mismo que notnull pero valida con string y que no hayan espacios

#### @GeneratedValue
> lo que hace es generar automaticamente la id sin necesidad de hacerla nosotros

#### @Column(nullable = false o true)
> si el nullable es false entonces tu mismo debes escribir el dato, <br> por ejemplo en la fecha es true para que se genere sola
