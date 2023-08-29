import java.io.IOException;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        Sistema sistema = new Sistema();
        int opcion, titulo = 0;

        BufferedReader lector = new BufferedReader(new InputStreamReader(System.in));

        while(true) {
            if(titulo == 0) {
                System.out.println("=========================================================");
                System.out.println(" == BIENVENIDO AL MENU DE MANEJO DE SISTEMA DE SALUD ==");
                System.out.println("=========================================================");
                titulo++;
            }
            
            System.out.println(
                    """
                            ***** Escoga una opción *****
                            1. Agregar paciente al registro
                            2. Editar paciente del registro
                            3. Eliminar paciente del registro
                            4. Agregar consulta médica
                            5. Editar consulta médica
                            6. Eliminar consulta médica
                            7. Mostrar pacientes
                            8. Mostrar consultas médicas por paciente
                            9. Salir del sistema""");


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
                    String rut = lector.readLine();
                    if(!sistema.existePaciente(rut)) {
                        System.out.println("El paciente no existe, volviendo al menú principal");
                        System.out.println("");
                        continue;
                    }


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
                    ConsultaMedica consultaAux = new ConsultaMedica(medico, hora, fecha, motivo, descripcion);

                    sistema.agregarCita(consultaAux,rut);
                    break;
                case 5:

                    break;
                case 6:
                    System.out.println("Ingrese rut del paciente asignado a la cita que quiera eliminar: ");
                    rut = lector.readLine();

                    if (sistema.existePaciente(rut)) {
                        sistema.eliminarCita(rut);
                    } else {
                        System.out.println("El paciente con el rut proporcionado no existe.");
                    }
                    break;
                case 7:
                    sistema.mostrarPacientes();
                    break;
                case 8:
                    sistema.listarConsultasPorPaciente();
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