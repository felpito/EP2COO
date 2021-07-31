import java.time.*;
import java.util.*;

public class MarcadorDeReuniao {
    LocalDate dataInicial;
    LocalDate dataFinal;
    Collection<String> listaDeParticipantes;

    Map<String, ArrayList<Intervalo>> disponibilidades = new HashMap<>();
    Map<LocalDateTime, String> horarios = new TreeMap<>();
    List<Horario> dataHoras = new ArrayList<>();

    public void marcarReuniaoEntre(LocalDate dataInicial, LocalDate dataFinal, Collection<String> listaDeParticipantes) {
        this.dataInicial = dataInicial;
        this.dataFinal = dataFinal;
        this.listaDeParticipantes = listaDeParticipantes;
        for (String s: listaDeParticipantes) {
            ArrayList<Intervalo> intervalos = new ArrayList<>();
            disponibilidades.put(s, intervalos);
            System.out.println(s + " " + disponibilidades.get(listaDeParticipantes.toString()));
        }
        System.out.println();
    }

    public void indicaDisponibilidadeDe(String participante, LocalDateTime inicio, LocalDateTime fim) {
        Intervalo intervalo = new Intervalo(inicio, fim);
        disponibilidades.get(participante).add(intervalo);
        System.out.println("ADD: " + participante + "  I: "
                + disponibilidades.get(participante).get(disponibilidades.get(participante).size()-1).getDataInicial() + " F: "
                + disponibilidades.get(participante).get(disponibilidades.get(participante).size()-1).getDataFinal());
        ordenaIntervalos(disponibilidades.get(participante));
    }

    public void mostraSobreposicao() {
        addHorarios();
        boolean mesmo = false;
        LocalDateTime inicio = null;
        Set<LocalDateTime> horariosEmOrdem = horarios.keySet();
        List<Intervalo> sobreposicao = new ArrayList<>();
        for (LocalDateTime h: horariosEmOrdem) {
            if (inicio == null) {
                if (horarios.get(h).equals("I")) inicio = h;
                mesmo = true;
            } else {
                if (h.isAfter(inicio) && horarios.get(h).equals("I")) {
                    inicio = h;
                    mesmo = false;
                }
                if (!mesmo && horarios.get(h).equals("F")) {
                    Intervalo intervalo = new Intervalo(inicio, h);
                    sobreposicao.add(intervalo);
                    inicio = null;
                }
            }
        }
        mostraDisponibilidades();
        System.out.println();
        System.out.println("SOBREPOSICAO: ");
        for (Intervalo i: sobreposicao) {
            System.out.println("I: " + i.dataInicial + " -- F: " + i.dataFinal);
        }
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

    public void addHorarios() {
        Set<String> keyParticipantes = disponibilidades.keySet();
        System.out.println();
        for (String s : keyParticipantes) {
            if (!disponibilidades.get(s).isEmpty()) {
                for (int i = 0; i < disponibilidades.get(s).size(); i++) {
                    horarios.put(disponibilidades.get(s).get(i).dataInicial, "I");
                    horarios.put(disponibilidades.get(s).get(i).dataFinal, "F");
                }
            }
        }
    }

    public void mostraDisponibilidades() {
        if(disponibilidades.isEmpty()) return;
        System.out.println();
        Set<String> keyParticipantes = disponibilidades.keySet();
        for (String key : keyParticipantes) {
            System.out.println("ATUAL: " + key);
            if (!disponibilidades.get(key).isEmpty()) {
                for (int i = 0; i < disponibilidades.get(key).size(); i++) {
                    if (!disponibilidades.get(key).isEmpty()) {
                        System.out.println(" I: " + disponibilidades.get(key).get(i).dataInicial + " F: " + disponibilidades.get(key).get(i).dataFinal);
                    } else System.out.println("null");
                }
            } else System.out.println(" null");
            System.out.println();
        }
    }

    public void mostraSobreposicao2() {
        addHorarios2();
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
        mostraDisponibilidades();
        System.out.println();
        System.out.println("SOBREPOSICAO: ");
        for (Intervalo i: sobreposicao) {
            System.out.println("I: " + i.dataInicial + " -- F: " + i.dataFinal);
        }
    }

    public void addHorarios2() {
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