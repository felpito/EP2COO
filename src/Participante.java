import java.time.*;

public class Participante{
    String email;
    LocalDateTime inicio,fim;

    public Participante(String email, LocalDateTime inicio, LocalDateTime fim){
        this.email = email;
        this.inicio = inicio;
        this.fim = fim;
    }

    String getEmail(){
        return email;
    }

    LocalDateTime getInicio(){
        return inicio;
    }

    LocalDateTime getFim(){
        return fim;
    }
}