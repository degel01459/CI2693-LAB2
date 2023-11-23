import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;


public class DGrafo implements Graph<vertex> {
    // Declaramos las variables, arreglos y tablas que vayamos a utilizar
    private HashMap<String, vertex> vertices;
    private ArrayList<edges> connect;
    public static final int inf = Integer.MAX_VALUE;

    public DGrafo() {
        // Constructor de la clase.
        vertices = new HashMap<>();
        connect = new ArrayList<>();
    }

    // Método para agregar un vértice al grafo
    public boolean add(vertex vertex) {
        if (!vertices.containsKey(vertex.getId())) {
            // Agregar el vértice al hashtable
            vertices.put(vertex.getId(), vertex);
            // Vértice agregado exitosamente
            return true;
        }
        // El vértice ya existe en el grafo
        return false;
    }

    // Método para verificar si un vértice existe en el grafo dado su identificador
    public boolean contains(vertex vertex) {
        if (vertices.containsKey(vertex.getId())) {
            // El vértice existe en el grafo
            return true;
        }
        // El vértice no existe en el grafo
        return false;
    }

    // Método para verificar si existe un lado entre dos vértices
    public boolean containsconnect(vertex from, vertex to) {
        // Iterar sobre todos los lados
        for (edges a : connect) {
            // Verificar si los vértices extremos coinciden en cualquier dirección
            if (a.getExtremoInicial().equals(from) && a.getExtremoFinal().equals(to)){
                // Existe un lado entre los vértices
                return true;
            }
        }
        // No existe un lado entre los vértices
        return false;
    }

    public boolean connect(vertex from, vertex to) {
        // Esta función establece la relación entre dos vértices si existe una conexión
        // entre ellos.
        if (!containsconnect(from, to)) {
            edges arista = new edges("" + from.getId() + to.getId() + "", "0", from, to);
            connect.add(arista);
            return true;
        }
        return false;
    }

    public boolean disconnect(vertex from, vertex to) {
        // Esta función elimina la relación entre dos vértices si existe una conexión
        // entre ellos.
        if (contains(from) && contains(to) && containsconnect(from, to)) {
            for (edges a : connect) {
                if (a.getExtremoInicial().equals(from) && a.getExtremoFinal().equals(to)){
                    connect.remove(a);
                    return true;
                }
            }
        }
        return false;
    }

    public List<vertex> getInwardEdges(vertex to) {
        // Esta función devuelve una lista de vértices predecesores que tienen una
        // conexión con el vértice dado.
        List<vertex> inwardEdges = new ArrayList<>();
        for (edges a : connect) {
            if (a.getExtremoFinal().getId().equals(to.getId())) {
                inwardEdges.add(a.getExtremoInicial());
            }
        }
        return inwardEdges;
    }

    public List<vertex> getOutwardEdges(vertex from) {
        // Esta función devuelve una lista de vértices sucesores que tienen una conexión
        // con el vértice dado.
        List<vertex> outwardEdges = new ArrayList<>();
        for (edges a : connect) {
            if (a.getExtremoInicial().getId().equals(from.getId())) {
                outwardEdges.add(a.getExtremoFinal());
            }
        }
        return outwardEdges;
    }

    public List<vertex> getVerticesConnectedTo(vertex vertex) {
        // Esta función devuelve una lista de vértices que tienen una conexión con el
        // vértice dado.
        List<vertex> verticesConnectedTo = new ArrayList<>();
        for (edges a : connect) {
            if (a.getExtremoInicial().getId().equals(vertex.getId())) {
                verticesConnectedTo.add(a.getExtremoFinal());
            } else if (a.getExtremoFinal().equals(vertex)) {
                verticesConnectedTo.add(a.getExtremoInicial());
            }
        }
        return verticesConnectedTo;
    }

    public List<vertex> getAllVertices() {
        // Esta función devuelve una lista de todos los vértices del grafo.
        List<vertex> allVertices = new ArrayList<>();
        for (String key : vertices.keySet()) {
            allVertices.add(vertices.get(key));
        }
        return allVertices;
    }

    public boolean remove(vertex vertex) {
        // Esta función elimina un vértice del grafo.
        if (contains(vertex)) {
            vertices.remove(vertex.getId());
            return true;
        }
        return false;
    }

    public int size() {
        // Esta función devuelve el número de vértices en el grafo.
        return vertices.size();
    }

    public Graph<vertex> subgraph(Collection<vertex> vertices) {
        // Esta función devuelve un subgrafo del grafo dado un conjunto de vértices.
        Graph<vertex> subgraph = new DGrafo();
        for (vertex v : vertices) {
            subgraph.add(v);
        }
        for (edges a : connect) {
            if (subgraph.contains(a.getExtremoInicial()) && subgraph.contains(a.getExtremoFinal())) {
                subgraph.connect(a.getExtremoInicial(), a.getExtremoFinal());
            }
        }
        return subgraph;
    }

    @Override
    public String toString() {
        // Este método devuelve una representación en forma de cadena del grafo.
        StringBuilder sb = new StringBuilder();
        // Iterar sobre todos los vertices en el grafo
        for (String a : vertices.keySet()) {
            sb.append("Persona: ").append(a).append("\n");
        }
        // Iterar sobre todas las aristas en el grafo
        for (edges l : connect) {
            sb.append(l).append("\n");
        }
        // Devolver la representación en forma de cadena del grafo
        return sb.toString();
    }

    public boolean cargarGrafo(String dirArchivo) {
        // Esta función cargará los datos de un .txt
        try (BufferedReader lista = new BufferedReader(new FileReader(dirArchivo))) {
            String linea;
            // Dividir la línea en datos separados por '|'
            while ((linea = lista.readLine()) != null) {
                String[] datos = linea.split(", ");
                String A = datos[0];
                String B = datos[1];
                vertex a1 = new vertex(A);
                vertex a2 = new vertex(B);
                // Agregar el vértices y la conexión al grafo
                add(a1);
                add(a2);
                connect(a1, a2);
            }
            return true;
        } catch (IOException | NumberFormatException e) {
            return false;
        }
    }

    public Integer encontrarCiclos() {
        List<List<vertex>> ciclos = new ArrayList<>();
        List<List<vertex>> cicloverificados = new ArrayList<>();
        boolean ciclo=false;
        Integer contador=0;
        for (vertex nodo : vertices.values()) {
            List<vertex> visitados = new ArrayList<>();
            visitados.add(nodo);
            encontrarCiclosAux(nodo, nodo, visitados, ciclos, ciclo);
        }
        verificarciclos(ciclos, cicloverificados);
        List<List<vertex>> cicloSet = new ArrayList<>();
        for (List<vertex> c : cicloverificados) {
            if (cicloSet.isEmpty()) {
                cicloSet.add(c);
            }
            else if (arreglolista(cicloSet, c)){
                cicloSet.add(c);
            }
        }
        for (List<vertex> c : cicloSet) {
            if (c.size()<=2) {
                contador=contador+10;
            }
            else if (2<c.size() && c.size()<=5){
                contador=contador+20;
            }
            else if (c.size()>5){
                contador=contador+30;
            }
        }
        return contador;
    }
    public List<List<vertex>> verificarciclos(List<List<vertex>> ciclos, List<List<vertex>> cicloverificados) {
        for (List<vertex> c : ciclos) {
           for (edges a : connect) {
                if (a.getExtremoInicial().getId().equals(c.get(c.size()-1).getId()) && a.getExtremoFinal().getId().equals(c.get(0).getId())){
                    cicloverificados.add(new ArrayList<>(c));
                }
            }
        }
        return ciclos;
    }

    private boolean encontrarCiclosAux(vertex inicio, vertex actual, List<vertex> visitados, List<List<vertex>> ciclos, boolean ciclo) {
        List<vertex> sucesores = getOutwardEdges(actual);
        Integer v=0;
        if (sucesores.size()>=1) {
            while ((v<sucesores.size())) {
                if (listalista(sucesores, visitados)) {
                    ciclos.add(new ArrayList<>(visitados));
                    ciclo=true;
                    return ciclo;
                } else if (!listavertice(visitados, sucesores.get(v))) {
                    visitados.add(sucesores.get(v));
                    encontrarCiclosAux(inicio, sucesores.get(v), visitados, ciclos, ciclo);
                    v=v+1;
                    visitados.remove(visitados.size() - 1);
                    return ciclo;
                }
                else{
                    v=v+1;
                    if (sucesores.size()>1){
                        encontrarCiclosAux(inicio, sucesores.get(v), visitados, ciclos, ciclo);
                        return ciclo;
                    }
                    else{
                        return ciclo;
                    }
                }
            }
        }
        return ciclo;
    }
 
    public boolean listavertice(List<vertex> A, vertex b) {
        boolean confirmacion = false;
        for (vertex v : A) {
            if (v.getId().equals(b.getId())) {
                confirmacion=true;
            }
        }
        if (confirmacion) {
            return true;
        }
        else{
            return false;
        }
    }

    private boolean listalista(List<vertex> A, List<vertex> B) {
        boolean confirmacion = false;
        for (vertex x : A) {
            if (listavertice(B, x)) {
                confirmacion=true;
            }
            else{
                return false;
            }
        }
        if (confirmacion) {
            return true;
        }
        else{
            return false;
        }
    }

    private boolean arreglolista(List<List<vertex>> A, List<vertex> B) {
        boolean confirmacion = false;
        for (List<vertex> x : A) {
            if (listalista(x, B)) {
                if (x.size()==B.size()) {
                    confirmacion=false;
                }
                else{
                    confirmacion=true;
                }
                
            }
            else{
                confirmacion=true;
            }
        }
        if (confirmacion) {
            return true;
        }
        else{
            return false;
        }
    }
}
