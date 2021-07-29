import java.util.*;
import java.time.*;

public class MarcadorDeReuniao{

    public boolean adicionaParticipante(Collection<String> listaDeParticipantes){
        Scanner scan = new Scanner(System.in);
        System.out.println("-------------------------------------------");
        System.out.println("Insira o email do participante:");
        String email = scan.nextLine();
        System.out.println("Insira a data inicial (modelo: 2021-07-30-18-30-15, na ordem:ano-mes-dia-hora-minuto-segundo):");
        String dataInicio = scan.nextLine();
        String[] inicio = dataInicio.split("-");
        int anoInicio = Integer.parseInt(inicio[0]);
        int mesInicio = Integer.parseInt(inicio[1]);
        int diaInicio = Integer.parseInt(inicio[2]);
        int horaInicio = Integer.parseInt(inicio[3]);
        int minutoInicio = Integer.parseInt(inicio[4]);
        int segundoInicio = Integer.parseInt(inicio[5]);
        LocalDateTime tempoInicio = LocalDateTime.of(anoInicio,mesInicio,diaInicio,horaInicio,minutoInicio,segundoInicio);
        System.out.println("Insira a data final (modelo: 2021-07-30-18-30-15, na ordem:ano-mes-dia-hora-minuto-segundo):");
        String dataFim = scan.nextLine();
        String[] fim = dataFim.split("-");
        int anoFim = Integer.parseInt(fim[0]);
        int mesFim = Integer.parseInt(fim[1]);
        int diaFim = Integer.parseInt(fim[2]);
        int horaFim = Integer.parseInt(fim[3]);
        int minutoFim = Integer.parseInt(fim[4]);
        int segundoFim = Integer.parseInt(fim[5]);
        LocalDateTime tempoFim = LocalDateTime.of(anoFim,mesFim,diaFim,horaFim,minutoFim,segundoFim);
        if(tempoInicio.isAfter(tempoFim)){
            return false;
        }
        listaDeParticipantes.add(email);
        indicaDisponibilidadeDe(email, tempoInicio, tempoFim);
        return true;
    }

    public static void ordenaData(List<LocalDateTime> dayList){
        System.out.println(dayList);
        Comparator<LocalDateTime> c = new Comparator<LocalDateTime>() {
            @Override
            public int compare(LocalDateTime d1, LocalDateTime d2) {
                if(d1.getYear() != d2.getYear())
                    return d1.getYear() - d2.getYear();
                else if(d1.getMonthValue() != d2.getMonthValue())
                    return d1.getMonthValue() - d2.getMonthValue();
                else if(d1.getDayOfMonth() != d2.getDayOfMonth())
                    return d1.getDayOfMonth() - d2.getDayOfMonth();
                else if(d1.getHour() != d2.getHour())
                    return d1.getHour() - d2.getHour();
                else if(d1.getMinute() != d2.getMinute())
                    return d1.getMinute() - d2.getMinute();
                else if(d1.getSecond() != d2.getSecond())
                    return d1.getSecond() - d2.getSecond();
                return 0;
            }
        };
        dayList.sort(c);
        System.out.println(dayList);
    }

    public void indicaDisponibilidadeDe(String participante,LocalDateTime inicio,LocalDateTime fim){
        Participante p = new Participante(participante, inicio, fim);
        System.out.println("-------------------------------------------");
        System.out.println("Participante \"" + participante + "\" criado com sucesso.");
    }

    public void mostraSobreposicao(){

    }
/*
	public void marcarReuniaoEntre(LocalDate dataInicial,LocalDate dataFinal,Collection<String> listaDeParticipantes){

	}

	javac MarcadorDeReuniao.java && java MarcadorDeReuniao

	1/4 a
	2/5 b

	1 a
	2 b
	4 a
	5 b
	...
	3424 a
*/
}