import java.util.*;
import java.time.*;
import java.io.*;

public class Main{
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args){
        MarcadorDeReuniao reuniao = new MarcadorDeReuniao();
        GerenciadorDeSalas sala = new GerenciadorDeSalas();
        int n = 0;
        int i = 1;
        int escolha = 0;
        boolean continuar = true;
        String participante;
        String inicioReuniao;
        String fimReuniao;
        List<String> participantes = new ArrayList<>();
        System.out.println("--------------------------------------------");
        System.out.println("Bem vindo ao marcador de reunioes!");
        System.out.println("Quantos participantes a reuniao deve conter?");
        n = Integer.parseInt(scanner.nextLine());
        while (n > 0) {
            System.out.println();
            System.out.println("Insira o endereco eletronico do participante " + i + ":");
            participante = scanner.nextLine();
            if (!participantes.contains(participante)) {
                participantes.add(participante);
                n--;
                i++;
            }
            else System.out.println("PARTICIPANTE JA HAVIA SIDO INSERIDO!");
        }
        while (true) {
            System.out.println();
            System.out.println("Insira uma data para o inicio da reuniao (modelo: 2021-07-30, na ordem:ano-mes-dia): ");
            inicioReuniao = scanner.next();
            LocalDate tempoInicioReuniao = converteDate(inicioReuniao);
            System.out.println();
            System.out.println("Insira uma data para o fim da reuniao (modelo: 2021-07-30, na ordem:ano-mes-dia): ");
            fimReuniao = scanner.next();
            LocalDate tempoFimReuniao = converteDate(fimReuniao);

            if (tempoInicioReuniao.isAfter(tempoFimReuniao) || (tempoInicioReuniao.isEqual(tempoFimReuniao)))
                System.out.println("DATA INVALIDA!");
            else {
                reuniao.marcarReuniaoEntre(tempoInicioReuniao, tempoFimReuniao, participantes);
                break;
            }
        }
        while (continuar) {
            System.out.println("Para indicar uma disponibilidade, digite 1.");
            System.out.println("Para ver a lista de participantes, digite 2.");
            System.out.println("Para ver a sobreposicao dos horarios, digite 3.");
            System.out.println("Para reservar uma sala de reuniao, digite 4.");
            System.out.println("Para encerrar a sessao, digite 5");
            System.out.println();
            escolha = scanner.nextInt();
            if (escolha == 1) recebeDisponilbilidade(reuniao, participantes);
            else if (escolha == 2) reuniao.mostraDisponibilidades();
            else if (escolha == 3) {
                reuniao.mostraSobreposicao();
            }
            else if (escolha == 4){
                continuar = false;
            }
            else if (escolha == 5) continuar = false;
        }
        if(escolha == 4){
            System.out.println();
            System.out.println("Bem vindo ao gerenciador de salas!");
            System.out.println("O periodo escolhido para reuniao foi de " + inicioReuniao + " a " + fimReuniao + ".");
            continuar = true;
            while (continuar) {
                System.out.println();
                System.out.println("Para adicionar uma sala, digite 1.");
                System.out.println("Para remover uma sala, digite 2.");
                System.out.println("Para ver a lista de salas, digite 3.");
                System.out.println("Para reservar um horario em uma sala, digite 4");
                System.out.println("Para cancelar uma reserva, digite 5.");
                System.out.println("Para ver a lista de reservas, digite 6.");
                System.out.println("Para encerrar a sessao, digite 7.");
                System.out.println();
                escolha = scanner.nextInt();
                System.out.println();
                if(escolha == 1){
                    System.out.println("Insira o nome da sala:");
                    scanner.nextLine();
                    String nome = scanner.nextLine();
                    System.out.println("Insira a capacidade maxima da sala (valor inteiro):");
                    int capacidadeMaxima = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Insira a descricao da sala:");
                    String descricao = scanner.nextLine();
                    System.out.println();
                    System.out.println("Dados recebidos. Criando sala...");
                    sala.adicionaSalaChamada(nome, capacidadeMaxima, descricao);
                }
                if(escolha == 2){
                    if(sala.listaDeSalas.isEmpty()){
                        System.out.println("Nao existem salas no momento.");
                    }
                    else{
                        System.out.println("Insira o nome da sala a ser removida:");
                        String nome = scanner.nextLine();
                        System.out.println();
                        sala.removeSalaChamada(nome);
                    }
                }
                if(escolha == 3){
                    if(sala.listaDeSalas.isEmpty()){
                        System.out.println("Nenhuma sala foi criada.");
                    }
                    else{
                        for(Sala s : sala.listaDeSalas()){
                            System.out.println(s.getNome() + " (" + s.getDescricao() + ")");
                        }
                    }
                }
                if(escolha == 4){
                    if(sala.listaDeSalas.isEmpty()){
                        System.out.println("Nao existem salas no momento.");
                    }
                    else{
                        String nomeDaSala, dataInicialString, dataFinalString;

                        scanner.nextLine();
                        System.out.println("Insira o nome da sala desejada:");
                        nomeDaSala = scanner.nextLine();
                        System.out.println("Insira a data inicial da chamada (modelo: 2021-07-30-18-30-15, na ordem:ano-mes-dia-hora-minuto-segundo):");
                        dataInicialString = scanner.nextLine();
                        System.out.println("Insira a data final da chamada (modelo: 2021-07-30-18-30-15, na ordem:ano-mes-dia-hora-minuto-segundo):");
                        dataFinalString = scanner.nextLine();
                        LocalDateTime dataInicial = converteDateTime(dataInicialString);
                        LocalDateTime dataFinal = converteDateTime(dataFinalString);
                        boolean falha = false;
                        try{
                            sala.reservaSalaChamada(nomeDaSala, dataInicial, dataFinal);
                        } catch(ReservaException erro){
                            falha = true;
                            if (erro.getException().equals("sala")){
                                System.out.println();
                                System.out.println("A sala inserida nao foi encontrada.");
                            }
                            if(erro.getException().equals("periodo")){
                                System.out.println();
                                System.out.println("O periodo selecionado nao eh valido.");
                            }
                            if(erro.getException().equals("conflito")){
                                System.out.println();
                                System.out.println("O horario escolhido esta em conflito com outra reuniao.");
                            }
                        }
                        if(falha == false){
                            System.out.println();
                            System.out.println("Reserva efetuada com sucesso!");
                        }
                    }
                }
                if(escolha == 5){
                    if(sala.listaDeSalas.isEmpty()){
                        System.out.println("Eh necessario criar uma sala antes.");
                    }
                    else{
                        System.out.println("Insira o nome da sala que tera a reserva cancelada:");
                        String nome = scanner.nextLine();
                        if(sala.listaDeReservas.isEmpty()){
                            System.out.println("Nenhuma reserva foi feita nessa sala.");
                        }
                        else{
                            sala.imprimeReservasDaSala(nome);
                            System.out.println();
                            System.out.println("Escolha o numero da reserva a ser cancelada:");
                            int decisao = scanner.nextInt();
                            Collection<Reserva> reservas = sala.reservasParaSala(nome);
                            ArrayList<Reserva> reservasArray = new ArrayList<>();
                            reservasArray.addAll(reservas);
                            sala.cancelaReserva(reservasArray.get(decisao-1));
                        }
                    }
                }
                if(escolha == 6){
                    if(sala.listaDeReservas.isEmpty()){
                        System.out.println("Nao existe nenhuma reserva atualmente.");
                    }
                    else{
                        List<Sala> salas = sala.listaDeSalas();
                        for(Sala s : salas){
                            sala.imprimeReservasDaSala(s.getNome());
                        }
                    }
                }
                if(escolha == 7){
                    continuar = false;
                }
            }
        }
        System.out.println("Obrigado por utilizar o marcador de reunioes.");
    }

    public static void recebeDisponilbilidade(MarcadorDeReuniao reuniao, Collection<String> participantes) {
        String participante = null;
        while (!participantes.contains(participante)) {
            System.out.println("-------------------------------------------");
            System.out.println("Insira o endereco eletronico do participante:");
            scanner.nextLine();
            participante = scanner.nextLine();
            System.out.println();
            if(!participantes.contains(participante)){
                System.out.println("Participante nao encontrado.");
            }
        }
        while (true) {
            System.out.println("Insira a data inicial (modelo: 2021-07-30-18-30-15, na ordem:ano-mes-dia-hora-minuto-segundo):");
            String dataInicio = scanner.nextLine();
            System.out.println();
            System.out.println("Insira a data final (modelo: 2021-07-30-18-30-15, na ordem:ano-mes-dia-hora-minuto-segundo):");
            String dataFim = scanner.nextLine();
            System.out.println();
            LocalDateTime tempoInicio = converteDateTime(dataInicio);
            LocalDateTime tempoFim = converteDateTime(dataFim);
            String reuniaoInicialString = reuniao.dataInicial.toString();
            String reuniaoFinalString = reuniao.dataFinal.toString();
            LocalDateTime reuniaoTempoInicio = converteDateTime(reuniaoInicialString);
            LocalDateTime reuniaoTempoFim = converteDateTime(reuniaoFinalString);
            if (tempoInicio.isAfter(tempoFim) || tempoInicio.isEqual(tempoFim)) System.out.println("DATA INVALIDA!");
            else {
                if((reuniaoTempoInicio.isAfter(tempoInicio)) && (reuniaoTempoInicio.isAfter(tempoFim))) System.out.println("DATA INVALIDA, OCORRE ANTES DA REUNIAO");
                else if((tempoInicio.isAfter(reuniaoTempoFim)) && (tempoFim.isAfter(reuniaoTempoFim))) System.out.println("DATA INVALIDA, OCORRE DEPOIS DA REUNIAO");
                else{
                    reuniao.indicaDisponibilidadeDe(participante, tempoInicio, tempoFim);
                    break;
                }
            }
        }
    }

    public static LocalDateTime converteDateTime(String dataString) {
        String[] dataSplit;
        if(dataString.length() < 13){
            dataString = dataString + "-00-00-00";
        }
        dataSplit = dataString.split("-");
        int anoSplit = Integer.parseInt(dataSplit[0]);
        int mesSplit = Integer.parseInt(dataSplit[1]);
        int diaSplit = Integer.parseInt(dataSplit[2]);
        int horaSplit = Integer.parseInt(dataSplit[3]);
        int minutoSplit = Integer.parseInt(dataSplit[4]);
        int segundoSplit = Integer.parseInt(dataSplit[5]);
        return LocalDateTime.of(anoSplit,mesSplit,diaSplit,horaSplit,minutoSplit,segundoSplit);
    }

    public static LocalDate converteDate(String dataString) {
        String[] dataSplit = dataString.split("-");
        int anoSplit = Integer.parseInt(dataSplit[0]);
        int mesSplit = Integer.parseInt(dataSplit[1]);
        int diaSplit = Integer.parseInt(dataSplit[2]);
        return LocalDate.of(anoSplit,mesSplit,diaSplit);
    }
}
