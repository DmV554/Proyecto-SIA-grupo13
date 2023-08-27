import java.io.IOException;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        Sistema sistema = new Sistema();
        int opcion, titulo = 0;
        while(true) {
            if(titulo == 0) {
                System.out.println("=========================================================");
                System.out.println(" == BIENVENIDO AL MENÚ DE MANEJO DE SISTEMA DE SALUD ==");
                System.out.println("=========================================================");
                titulo++;
            }

            System.out.println("Escoja una opción: ");
            System.out.println("1. Agregar paciente al registro");
            System.out.println("2. Editar paciente del registro");
            System.out.println("3. Eliminar paciente del registro");
            System.out.println("4. Agregar consulta médica");
            System.out.println("5. Editar consulta médica");
            System.out.println("6. Eliminar consulta médica");
            System.out.println("7. Mostrar pacientes");
            System.out.println("8. Mostrar consultas médicas por paciente");
            System.out.println("9. Salir del sistema");
            System.out.println("");

            BufferedReader lector = new BufferedReader(new InputStreamReader(System.in));

            opcion = Integer.parseInt(lector.readLine());

            switch(opcion) {
                case 1:
                    String nombrePaciente = lector.readLine();
                    int edadPaciente = Integer.parseInt(lector.readLine());
                    String rutPaciente = lector.readLine();
                    String enfermedadesPaciente = lector.readLine();

                    sistema.agregarPaciente(new Paciente(nombrePaciente, edadPaciente, rutPaciente, enfermedadesPaciente));
                    break;
                case 2:
                    
                    break;
                case 3:
                    sistema.eliminarPaciente();
                    break;
                case 4:
                   
                    break;
                case 5:
                    
                    break;
                case 6:
                    
                    break;
                case 7:
                    sistema.mostrarPacientes();
                    break;
                case 8:
                    
                    break;
                case 9:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opción inválida");
                    break;
            }
        }
    }
}
