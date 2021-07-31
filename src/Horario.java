import java.time.LocalDateTime;

public class Horario {
    LocalDateTime dataHora;
    String tipo;

    public Horario (LocalDateTime dataHora, String tipo) {
        this.dataHora = dataHora;
        this.tipo = tipo;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public String getTipo() {
        return tipo;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
