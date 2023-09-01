import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        Sistema sistema = new Sistema();
        sistema.agregarPacientesDefecto();
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
                            ***** Escoja una opcion *****
                            1. Agregar paciente al registro
                            2. Editar paciente del registro
                            3. Eliminar paciente del registro
                            4. Agregar consulta medica
                            5. Editar consulta medica
                            6. Eliminar consulta medica
                            7. Mostrar pacientes
                            8. Mostrar consultas medicas por paciente
                            9. Salir del sistema""");


            opcion = Integer.parseInt(lector.readLine());

            System.out.println("");

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
                    String rutCita = lector.readLine();
                    if(!sistema.existePaciente(rutCita)) {
                        System.out.println("El paciente no existe, volviendo al menú principal");
                        System.out.println("");
                        continue;
                    }

                    System.out.println("Ingrese el nombre o identificador de la consulta: ");
                    String nombreConsulta = lector.readLine();

                    Paciente pacienteAuxConsulta = sistema.buscarPaciente(rutCita);

                    if(pacienteAuxConsulta.buscarConsulta(nombreConsulta) != null) {
                        System.out.println("La consulta ya existe, volviendo al menú principal");
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

                    ConsultaMedica consultaAux = new ConsultaMedica(medico, hora, fecha, motivo, descripcion, nombreConsulta);

                    pacienteAuxConsulta.agregarConsulta(consultaAux);
                    break;
                case 5:

                    break;
                case 6:
                    System.out.println("Ingrese rut del paciente asignado a la consulta que quiera eliminar: ");
                    String rutConsultaEliminar = lector.readLine();

                     if(!sistema.existePaciente(rutConsultaEliminar)) {
                        System.out.println("El paciente no existe, volviendo al menú principal");
                        System.out.println("");
                        continue;
                    }

                    Paciente pacienteAux = sistema.buscarPaciente(rutConsultaEliminar);
                    
                    if(pacienteAux.noHayConsultas()) {
                        System.out.println("El paciente no tiene consultas asignadas, volviendo al menú principal");
                        System.out.println("");
                        continue;
                    }

                    System.out.println("Ingrese el nombre o identificador de la consulta: ");
                    String nombreConsultaEliminar = lector.readLine();

                    ConsultaMedica consultaAuxiliar = pacienteAux.buscarConsulta(nombreConsultaEliminar);

                    if(consultaAuxiliar == null) {
                        System.out.println("La consulta no existe, volviendo al menú principal");
                        System.out.println("");
                        continue;
                    }

                    pacienteAux.eliminarConsulta(consultaAuxiliar);

                    break;
                case 7:
                    sistema.mostrarPacientes();
                    break;
                case 8:
                    System.out.println("Ingrese rut del paciente al que listar sus consultas: ");
                    String rutConsultaListar = lector.readLine();

                     if(!sistema.existePaciente(rutConsultaListar)) {
                        System.out.println("El paciente no existe, volviendo al menú principal");
                        System.out.println("");
                        continue;
                    }

                    Paciente pacienteAuxListar = sistema.buscarPaciente(rutConsultaListar);
                    
                    if(pacienteAuxListar.noHayConsultas()) {
                        System.out.println("El paciente no tiene consultas asignadas, volviendo al menú principal");
                        System.out.println("");
                        continue;
                    }

                    pacienteAuxListar.listarConsultasPorPaciente();
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