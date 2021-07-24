import java.time.*;

public class Reserva{
    String nomeDaSala ;
    LocalDateTime dataInicial;
    LocalDateTime dataFinal;

    public Reserva(String nomeDaSala, LocalDateTime dataInicial, LocalDateTime dataFinal){
        this.nomeDaSala = nomeDaSala;
        this.dataInicial = dataInicial;
        this.dataFinal = dataFinal;
    }

    String getNomeDaSala(){
        return nomeDaSala;
    }

    LocalDateTime getDataInicial(){
        return dataInicial;
    }

    LocalDateTime getDataFinal(){
        return dataFinal;
    }
}