import java.time.*;
import java.util.*;
import java.io.*;

public class GerenciadorDeSalas {
    List<Sala> listaDeSalas = new ArrayList<>();
    List<Reserva> listaDeReservas = new ArrayList<>();

    public void adicionaSalaChamada(String nome, int capacidadeMaxima, String descricao) {
        Sala sala = new Sala(nome, capacidadeMaxima, descricao);
        adicionaSala(sala);
    }

    public void removeSalaChamada(String nomeDaSala) {
        int posicao = -1;
        for(Sala sala : listaDeSalas){
            if(sala.nome.equals(nomeDaSala)){
                posicao = listaDeSalas.indexOf(sala);
                listaDeSalas.remove(posicao);
                System.out.println("Sala \"" + nomeDaSala + "\" apagada com sucesso!");
                break;
            }
        }
        if(posicao == -1){
            System.out.println("Sala nao encontrada.");
        }
    }

    public List<Sala> listaDeSalas() {
        return listaDeSalas;
    }

    public void adicionaSala(Sala novaSala) {
        listaDeSalas.add(novaSala);
        System.out.println("Sala \"" + novaSala.nome + "\" adicionada com sucesso!");
    }

    public Reserva reservaSalaChamada(String nomeDaSala, LocalDateTime dataInicial, LocalDateTime dataFinal) throws ReservaException {
        int posicao = -1;
        boolean achou = false;
        for(Sala sala : listaDeSalas){
            if(sala.nome.equals(nomeDaSala)){
                achou = true;
                posicao = listaDeSalas.indexOf(sala);
            }
            else{
                if(achou){
                    break;
                }
            }
        }
        if(posicao == -1){
            throw new ReservaException("sala");
        }
        else{
            if(dataInicial.isAfter(dataFinal)){
                throw new ReservaException("periodo");
            }
            else{
                for(Reserva reserva : listaDeReservas){
                    if(reserva.getNomeDaSala().equals(nomeDaSala)){
                        if((!reserva.getDataInicial().isAfter(dataFinal)) || (!reserva.getDataFinal().isAfter(dataInicial)) || ((reserva.getDataInicial().isAfter(dataInicial)) && (!reserva.getDataFinal().isAfter(dataFinal)))){
                            throw new ReservaException("conflito");
                        }
                    }
                }
            }
            Reserva r = new Reserva(nomeDaSala, dataInicial, dataFinal);
            listaDeReservas.add(r);
            return r;
        }
    }

    public void cancelaReserva(Reserva cancelada) {
        int posicao = -1;
        for(Reserva reserva : listaDeReservas){
            if(reserva.nomeDaSala.equals(cancelada.nomeDaSala)){
                posicao = listaDeReservas.indexOf(reserva);
                listaDeReservas.remove(posicao);
                System.out.println("Reserva \"" + cancelada + "\" cancelada com sucesso!");
                break;
            }
        }
        if(posicao == -1){
            System.out.println("Reserva nao encontrada.");
        }
    }

    public Collection<Reserva> reservasParaSala(String nomeSala) {
        Collection<Reserva> listaDeReservasDaSala = new ArrayList<>();
        int posicao = -1;
        boolean achou = false;
        for(Reserva reserva : listaDeReservas){
            if(reserva.nomeDaSala.equals(nomeSala)){
                achou = true;
                posicao = listaDeReservas.indexOf(reserva);
                listaDeReservasDaSala.add(reserva);
            }
            else{
                if(achou){
                    break;
                }
            }
        }
        if(posicao == -1){
            System.out.print("Nao existem reservas para a sala: ");
        }
        return listaDeReservasDaSala;
    }

    public void imprimeReservasDaSala(String nomeSala) {
        int i = 1;
        Collection<Reserva> listaDeReservasDaSala = reservasParaSala(nomeSala);
        ArrayList<Reserva> listaDeReservasDaSalaArray = new ArrayList<>();
        listaDeReservasDaSalaArray.addAll(listaDeReservasDaSala);
        System.out.println(nomeSala);
        for(Reserva r : listaDeReservasDaSalaArray){
            System.out.println(i + " - De " + r.getDataInicial() + " ate " + r.getDataFinal());
            i++;
        }
    }

}