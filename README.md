# Broker RMI

1.- Compilar el directorio `src` mediante:
```
javac *.java
```

2.- Lanza el `rmiregistry` en cada máquina:
```
rmiregistry <serverPort> &
```

3.- A continuación, lanza el Broker:
```
java BrokerImpl <brokerIP>:<brokerPort>
```

4.- Posteriormente, lanza el ServerA y el ServerB en las respectivas máquinas:
```
java ServerAImpl <brokerIP>:<brokerPort> <serverAIP> <serverAPort>
```
```
java ServerBImpl <brokerIP>:<brokerPort> <serverBIP> <serverBPort>
```

5.- Por último, lanza el ClientC:
```
java ClientC <brokerIP>:<brokerPort> 
```

## Interfaz ServerA
El ServerA cuenta con una interfaz que permite dar de baja y de alta servicios.
Y también terminar la ejecución del ServerA dando de baja todos los servicios
y el servidor en el Broker.

```
Servicios disponibles. Escribe un número para registrarlo/darlo de baja:
0. [UP]   dar_fecha
1. [UP]   dar_hora
2. [UP]   coger_variable
3. [UP]   guardar_variable
O escribe "fin" para salir:
```

## Interfaz ClientC
```
Esribe el número del servicio que quieres ejecutar.
Escribe "fin" para salir.
Escribe "r" para actualizar el listado de servicios disponibles

0 name_of_collection
1 guardar_variable
2 set_name_of_collection
3 coger_variable
4 dar_hora
5 number_of_books
6 dar_fecha
```