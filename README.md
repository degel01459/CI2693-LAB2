Estudiante: Kevin Briceño
Carnet: 15-11661
Laboratorio #2

El programa realiza el recorrido entre los vértices en el grafo con el fin de encontrar ciclos. 
Luego, calcula el valor de los ciclos en funcion del numero de vertices que ppertenezcan al ciclo. 
Finalmente, imprime el valor del la suma de ciclos de la lista dada que representa el numero de repartidores necesarios para cumplir con las entregas en el tiempo deseado.

cargarGrafo se encarga de leer un archivo de texto y cargar los datos en el grafo. Aquí está una explicación paso a paso de cómo funciona:
Se recibe como parámetro la dirección del archivo de texto a cargar (dirArchivo).
Se crea un objeto BufferedReader para leer el archivo de texto utilizando FileReader y BufferedReader.
Se declara una variable linea para almacenar cada línea leída del archivo.
Se utiliza un bucle while para leer cada línea del archivo hasta que no haya más líneas.
Dentro del bucle, se divide la línea en datos separados por espacios utilizando el método split(" "), y se almacenan en un arreglo datos.
Se extraen los nombres de los vertices (A y B) de los datos obtenidos.
Se crean objetos vertex para representar a los vertices (a1 y a2).
Se llama al método add para agregar los vértices al grafo.
Se llama al método connect para establecer la conexión entre los vértices en el grafo.
Se repite el bucle hasta que se hayan leído todas las líneas del archivo.
Se retorna true para indicar que la carga del grafo fue exitosa.
Se aplica una implementación (DFS) para encontrar ciclos en un grafo. La función "encontrarCiclos" busca ciclos en el grafo representado por la estructura de datos "vertices" y "connect". 
La función "encontrarCiclos" inicializa una lista de ciclos, recorre todos los nodos del grafo y llama a la función "encontrarCiclosAux" para buscar ciclos que comiencen en cada nodo. Luego, verifica y puntúa los ciclos encontrados.
La función "encontrarCiclosAux" es una implementación recursiva de DFS que busca ciclos a partir de un nodo dado. Utiliza una lista de nodos visitados para rastrear el camino actual y busca ciclos en el grafo.
Las funciones "verificarciclos", "listavertice", "listalista" y "arreglolista" son utilitarias que ayudan en la verificación y manipulación de listas de vértices y ciclos.