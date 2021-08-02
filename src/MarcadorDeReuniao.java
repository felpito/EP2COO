import java.time.*;
import java.util.*;

public class MarcadorDeReuniao {
    LocalDate dataInicial;
    LocalDate dataFinal;
    Collection<String> listaDeParticipantes;

    Map<String, ArrayList<Intervalo>> disponibilidades = new HashMap<>();
    List<Horario> dataHoras = new ArrayList<>();

    public void marcarReuniaoEntre(LocalDate dataInicial, LocalDate dataFinal, Collection<String> listaDeParticipantes) {
        this.dataInicial = dataInicial;
        this.dataFinal = dataFinal;
        this.listaDeParticipantes = listaDeParticipantes;
        for (String s: listaDeParticipantes) {
            ArrayList<Intervalo> intervalos = new ArrayList<>();
            disponibilidades.put(s, intervalos);
        }
        System.out.println();
    }

    public void indicaDisponibilidadeDe(String participante, LocalDateTime inicio, LocalDateTime fim) {
        Intervalo intervalo = new Intervalo(inicio, fim);
        disponibilidades.get(participante).add(intervalo);
        System.out.println();
        System.out.println("Disponibilidade do participante \"" + participante + "\" adicionado com sucesso!");
        System.out.println();
        ordenaIntervalos(disponibilidades.get(participante));
    }

    public void ordenaIntervalos(List<Intervalo> intervalos) {
        Comparator<Intervalo> c = new Comparator<Intervalo>() {
            @Override
            public int compare(Intervalo i1, Intervalo i2) {
                LocalDateTime d1 = i1.dataInicial;
                LocalDateTime d2 = i2.dataInicial;
                if(d1.getYear() != d2.getYear()) return d1.getYear() - d2.getYear();
                else if(d1.getMonthValue() != d2.getMonthValue()) return d1.getMonthValue() - d2.getMonthValue();
                else if(d1.getDayOfMonth() != d2.getDayOfMonth()) return d1.getDayOfMonth() - d2.getDayOfMonth();
                else if(d1.getHour() != d2.getHour()) return d1.getHour() - d2.getHour();
                else if(d1.getMinute() != d2.getMinute()) return d1.getMinute() - d2.getMinute();
                else if(d1.getSecond() != d2.getSecond()) return d1.getSecond() - d2.getSecond();
                return 0;
            }
        };
        intervalos.sort(c);
    }

    public void mostraDisponibilidades() {
        if(disponibilidades.isEmpty()) return;
        System.out.println();
        Set<String> keyParticipantes = disponibilidades.keySet();
        for (String key : keyParticipantes) {
            System.out.println("Participante: " + key);
            if (!disponibilidades.get(key).isEmpty()) {
                for (int i = 0; i < disponibilidades.get(key).size(); i++) {
                    if (!disponibilidades.get(key).isEmpty()) {
                        System.out.println(" I: " + disponibilidades.get(key).get(i).dataInicial + " F: " + disponibilidades.get(key).get(i).dataFinal);
                    } else System.out.println("Sem disponibilidade indicada");
                }
            } else System.out.println("Sem disponibilidade indicada");
            System.out.println();
        }
    }

    public void mostraSobreposicao() {
        addHorarios();
        ordenaHorarios(dataHoras);
        LocalDateTime inicio = null;
        boolean valido = false;
        List<Intervalo> sobreposicao = new ArrayList<>();
        for (Horario h : dataHoras) {
            if (inicio == null) {
                if (h.getTipo().equals("I")) inicio = h.getDataHora();
            } else {
                if (h.getTipo().equals("I") && (h.getDataHora().isAfter(inicio) || h.getDataHora().isEqual(inicio))) {
                    inicio = h.getDataHora();
                    valido = true;
                }
                if (h.getTipo().equals("F") && valido) {
                    Intervalo intervalo = new Intervalo(inicio, h.getDataHora());
                    sobreposicao.add(intervalo);
                    valido = false;
                }
            }
        }
        System.out.println();
        if(sobreposicao.isEmpty()){
            System.out.println("Nao existe um intervalo de tempo onde todos os participantes possam comparecer a reuniao.");
        }
        else{
            System.out.println("SOBREPOSICAO: ");
            for (Intervalo i: sobreposicao) {
                System.out.println("I: " + i.dataInicial + " -- F: " + i.dataFinal);
            }
        }
        System.out.println();
    }

    public void addHorarios() {
        Set<String> participantes = disponibilidades.keySet();
        for(String s : participantes) {
            for (Intervalo h : disponibilidades.get(s)) {
                Horario dataHoraI = new Horario(h.dataInicial, "I");
                Horario dataHoraF = new Horario(h.dataFinal, "F");
                dataHoras.add(dataHoraI);
                dataHoras.add(dataHoraF);
            }
        }
    }

    public void ordenaHorarios(List<Horario> horarios) {
        Comparator<Horario> c = new Comparator<Horario>() {
            @Override
            public int compare(Horario h1, Horario h2) {
                LocalDateTime d1 = h1.getDataHora();
                LocalDateTime d2 = h2.getDataHora();
                if(d1.getYear() != d2.getYear()) return d1.getYear() - d2.getYear();
                else if(d1.getMonthValue() != d2.getMonthValue()) return d1.getMonthValue() - d2.getMonthValue();
                else if(d1.getDayOfMonth() != d2.getDayOfMonth()) return d1.getDayOfMonth() - d2.getDayOfMonth();
                else if(d1.getHour() != d2.getHour()) return d1.getHour() - d2.getHour();
                else if(d1.getMinute() != d2.getMinute()) return d1.getMinute() - d2.getMinute();
                else if(d1.getSecond() != d2.getSecond()) return d1.getSecond() - d2.getSecond();
                return 0;
            }
        };
        horarios.sort(c);
    }

}