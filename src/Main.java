import java.util.*;
import java.time.*;

public class Main{
    public static void main(String[] args){
        MarcadorDeReuniao reuniao = new MarcadorDeReuniao();
        Scanner scanner = new Scanner(System.in);
        int n = 0;
        int escolha = 0;
        boolean continuar = true;
        String participante;
        String inicioReuniao;
        String fimReuniao;
        List<String> participantes = new ArrayList<>();
        System.out.println("--------------------------------------------");
        System.out.println("Bem vindo ao marcador de reunioes!");
        System.out.println("Quantos participantes a reunião deve conter?");
        n = scanner.nextInt();
        while (n > 0) {
            System.out.println("Insira o endereco eletronico do participante: ");
            participante = scanner.next();
            participantes.add(participante);
            n--;
        }
        System.out.println("Insira a data inicial (modelo: 2021-07-30, na ordem:ano-mes-dia): ");
        inicioReuniao = scanner.next();
        LocalDate tempoInicioReuniao = converteData(inicioReuniao);
        System.out.println("Insira a data final (modelo: 2021-07-30, na ordem:ano-mes-dia): ");
        fimReuniao = scanner.next();

        LocalDate tempoFimReuniao = converteData(fimReuniao);

        if(tempoInicioReuniao.isAfter(tempoFimReuniao)) System.out.println("DATA INVALIDA!");
        else reuniao.marcarReuniaoEntre(tempoInicioReuniao, tempoFimReuniao, participantes);

        while (continuar) {
            System.out.println("Para indicar uma disponibilidade, digite 1.");
            System.out.println("Para ver a lista de participantes, digite 2.");
            System.out.println("Para ver a sobreposicao dos horarios, digite 3.");
            System.out.println("Para reservar uma sala de reuniao, digite 4.");
            System.out.println("Para sair do marcador de reunioes, digite 5");
            escolha = scanner.nextInt();
            if (escolha == 1) recebeDisponilbilidade(reuniao);
            else if (escolha == 2) reuniao.mostraDisponibilidades();
            else if (escolha == 3) reuniao.mostraSobreposicao();
            else if (escolha == 4) System.out.println("FAZ AE ADRIANO");
            else if (escolha == 5) continuar = false;
        }
        System.out.println("Obrigado por utilizar o marcador de reunioes.");
    }

    public static void recebeDisponilbilidade(MarcadorDeReuniao reuniao) {
        Scanner scan = new Scanner(System.in);
        System.out.println("-------------------------------------------");
        System.out.println("Insira o email do participante:");
        String email = scan.nextLine();
        System.out.println("Insira a data inicial (modelo: 2021-07-30-18-30-15, na ordem:ano-mes-dia-hora-minuto-segundo):");
        String dataInicio = scan.nextLine();
        System.out.println("Insira a data final (modelo: 2021-07-30-18-30-15, na ordem:ano-mes-dia-hora-minuto-segundo):");
        String dataFim = scan.nextLine();
        LocalDateTime tempoInicio = converteDataTime(dataInicio);
        LocalDateTime tempoFim = converteDataTime(dataFim);
        if(tempoInicio.isAfter(tempoFim)) System.out.println("DATA INVALIDA!");
        reuniao.indicaDisponibilidadeDe(email, tempoInicio, tempoFim);
    }

    public static LocalDateTime converteDataTime(String dataString) {
        String[] dataSplit = dataString.split("-");
        int anoSplit = Integer.parseInt(dataSplit[0]);
        int mesSplit = Integer.parseInt(dataSplit[1]);
        int diaSplit = Integer.parseInt(dataSplit[2]);
        int horaSplit = Integer.parseInt(dataSplit[3]);
        int minutoSplit = Integer.parseInt(dataSplit[4]);
        int segundoSplit = Integer.parseInt(dataSplit[5]);
        return LocalDateTime.of(anoSplit,mesSplit,diaSplit,horaSplit,minutoSplit,segundoSplit);
    }

    public static LocalDate converteData(String dataString) {
        String[] dataSplit = dataString.split("-");
        int anoSplit = Integer.parseInt(dataSplit[0]);
        int mesSplit = Integer.parseInt(dataSplit[1]);
        int diaSplit = Integer.parseInt(dataSplit[2]);
        return LocalDate.of(anoSplit,mesSplit,diaSplit);
    }






    public static void Testes() {
        LocalDate d1 = LocalDate.of(2021, 10, 5);
        LocalDate d2 = LocalDate.of(2021, 10, 12);

        List<String> participantes = new ArrayList<>();
        participantes.add("AdrianoBarbudo");
        participantes.add("Felmateos");
        participantes.add("Fescobar");
        participantes.add("Gigabriel");
        participantes.add("Rodrigao");

        MarcadorDeReuniao reuniao = new MarcadorDeReuniao();
        reuniao.marcarReuniaoEntre(d1, d2, participantes);

        ///colocar verificacao se esta estre o horario da reuniao
        LocalDateTime di0 = LocalDateTime.of(2021, 7, 22, 11, 36, 0);
        LocalDateTime df0 = LocalDateTime.of(2021, 7, 22, 14, 20, 0);
        LocalDateTime di1 = LocalDateTime.of(2021, 7, 22, 12, 43, 0);
        LocalDateTime df1 = LocalDateTime.of(2021, 7, 22, 13, 51, 0);
        LocalDateTime di2 = LocalDateTime.of(2021, 7, 22, 19, 23, 0);
        LocalDateTime df2 = LocalDateTime.of(2021, 7, 22, 20, 5, 0);
        LocalDateTime di3 = LocalDateTime.of(2021, 7, 22, 19, 0, 0);
        LocalDateTime df3 = LocalDateTime.of(2021, 7, 22, 21, 0, 0);
        LocalDateTime di4 = LocalDateTime.of(2021, 7, 22, 12, 45, 0);
        LocalDateTime df4 = LocalDateTime.of(2021, 7, 22, 20, 0, 0);

        reuniao.indicaDisponibilidadeDe("AdrianoBarbudo", di0, df0);

        reuniao.indicaDisponibilidadeDe("Fescobar", di1, df1);
        reuniao.indicaDisponibilidadeDe("Fescobar", di2, df2);

        reuniao.indicaDisponibilidadeDe("AdrianoBarbudo", di3, df3);

        reuniao.indicaDisponibilidadeDe("Felmateos", di4, df4);

        reuniao.mostraSobreposicao();
    }
}