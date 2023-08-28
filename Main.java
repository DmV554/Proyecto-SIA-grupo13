import java.io.IOException;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        Sistema sistema = new Sistema();
        int opcion, titulo = 0;
        while(true) {
            if(titulo == 0) {
                System.out.println("=========================================================");
                System.out.println(" == BIENVENIDO AL MENU DE MANEJO DE SISTEMA DE SALUD ==");
                System.out.println("=========================================================");
                titulo++;
            }

            System.out.println("Escoja una opcion: ");
            System.out.println("1. Agregar paciente al registro");
            System.out.println("2. Editar paciente del registro");
            System.out.println("3. Eliminar paciente del registro");
            System.out.println("4. Agregar consulta medica");
            System.out.println("5. Editar consulta medica");
            System.out.println("6. Eliminar consulta medica");
            System.out.println("7. Mostrar pacientes");
            System.out.println("8. Mostrar consultas medicas por paciente");
            System.out.println("9. Salir del sistema");
            System.out.println("");

            BufferedReader lector = new BufferedReader(new InputStreamReader(System.in));

            opcion = Integer.parseInt(lector.readLine());

            switch(opcion) {
                case 1:
                    System.out.println("Ingrese rut del paciente: ");
                    String rutPaciente = lector.readLine();

                    if(sistema.existePaciente(rutPaciente)) {
                        System.out.println("El paciente ya existe, volviendo al menú principal");
                        System.out.println("");
                        continue;
                    }

                    System.out.println("Ingrese nombre del paciente: ");
                    String nombrePaciente = lector.readLine();

                    System.out.println("Ingrese edad del paciente: ");
                    int edadPaciente = Integer.parseInt(lector.readLine());


                    sistema.agregarPaciente(new Paciente(nombrePaciente, edadPaciente, rutPaciente));
                    break;
                case 2:

                    break;
                case 3:
                    System.out.println("Ingrese el rut del paciente a eliminar: ");
                    String rutEliminar = lector.readLine();

                    if(!sistema.existePaciente(rutEliminar)) {
                        System.out.println("El paciente no existe, volviendo al menú principal");
                        System.out.println("");
                        continue;
                    }

                    sistema.eliminarPaciente(rutEliminar);
                    break;
                case 4:

                    System.out.println("Ingrese rut del paciente al que se le asignara la cita: ");
                    rutPaciente = lector.readLine();


                    System.out.println("Ingrese nombre del medico: ");
                    String medico = lector.readLine();

                    System.out.println("Ingrese la hora de la cita: ");
                    String hora = lector.readLine();

                    System.out.println("Ingrese la fecha de la cita: ");
                    String fecha = lector.readLine();

                    System.out.println("Ingrese el motivo de la cita: ");
                    String motivo = lector.readLine();

                    System.out.println("Ingrese la descripcion: ");
                    String descripcion = lector.readLine();

                    sistema.agregarCita(new ConsultaMedica(medico, hora, fecha, motivo, descripcion));
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
                    System.out.println("Saliendo del programa...");
                    System.exit(0);

                    break;
                default:
                    System.out.println("Opción inválida");
                    break;
            }
        }
    }
}