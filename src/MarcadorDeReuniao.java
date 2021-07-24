import java.util.*;
import java.time.*;

public class MarcadorDeReuniao{
    public static void main(String[] args){

        Collection<String> listaDeParticipantes = new ArrayList();
        listaDeParticipantes.add("adriano@email.com");
        System.out.println("participantes:" + listaDeParticipantes);
        Participante adriano = new Participante("adriano@email.com", LocalDateTime.of(2018,07,23,15,10,05), LocalDateTime.of(2018,07,23,16,10,05));
        LocalDateTime data = adriano.getInicio();
        System.out.println(data);
        System.out.println(adriano.getEmail());
        //indicaDisponibilidadeDe("adriano@email.com", LocalDateTime.of(2018,07,23,15,10,05), LocalDateTime.of(2018,07,23,16,10,05));
    }

    public void indicaDisponibilidadeDe(String participante,LocalDateTime inicio,LocalDateTime fim){
        Participante p = new Participante(participante, inicio, fim);
        //listaDeParticipantes.add(participante);
        System.out.println("Participante \"" + participante + "\" criado com sucesso.");
    }
/*
	public void marcarReuniaoEntre(LocalDate dataInicial,LocalDate dataFinal,Collection<String> listaDeParticipantes){

	}

	public void mostraSobreposicao(){

	}
	javac MarcadorDeReuniao.java && java MarcadorDeReuniao
*/
}