public class Sala{
    String nome;
    int capacidadeMaxima;
    String descricao;
    String local;

    public Sala(String nome, int capacidadeMaxima, String descricao){
        this.nome = nome;
        this.capacidadeMaxima = capacidadeMaxima;
        this.descricao = descricao;
    }

    String getNome(){
        return nome;
    }

    int getCapacidadeMaxima(){
        return capacidadeMaxima;
    }

    String getDescricao(){
        return descricao;
    }

    String getLocal(){
        return local;
    }
}