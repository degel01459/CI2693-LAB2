public class NextToYou {
    public static void main(String[] args) {
        DGrafo graph = new DGrafo();
        String archivo = "Caracas.txt";
        graph.cargarGrafo(archivo);
        System.out.println(graph.encontrarCiclos());
    }
}