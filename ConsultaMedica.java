public class ConsultaMedica {
    private String descripcion;
    private String hora;
    private String fecha;
    private String medico;
    private String motivoVisita;

    public ConsultaMedica() {
    }

    public void setDescripcion(String var1) {
        this.descripcion = var1;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setHora(String var1) {
        this.hora = var1;
    }

    public String getHora() {
        return this.hora;
    }

    public void setFecha(String var1) {
        this.fecha = var1;
    }

    public String getFecha() {
        return this.fecha;
    }

    public void setMedico(String var1) {
        this.medico = var1;
    }

    public String getMedico() {
        return this.medico;
    }

    public void setMotivoVisita(String var1) {
        this.motivoVisita = var1;
    }

    public String getMotivoVisita() {
        return this.motivoVisita;
    }
}