import java.util.Random;

public class Trem {
    private int id;
    private int passageiros;
    private int posicao;
    private boolean indoParaB;

    public Trem(int id, boolean indoParaB) {
        this.id = id;
        this.passageiros = new Random().nextInt(41) + 10; // 10 a 50 passageiros
        this.posicao = 0;
        this.indoParaB = indoParaB;
    }

    public int getId() {
        return id;
    }

    public int getPassageiros() {
        return passageiros;
    }

    public int getPosicao() {
        return posicao;
    }

    public boolean isIndoParaB() {
        return indoParaB;
    }

    public void mover() {
        posicao++;
    }

    public void parar(int tempo) {
        // Simular parada
    }

    public void embarcar(int pessoas) {
        passageiros += pessoas;
    }

    public void desembarcar(int pessoas) {
        passageiros -= pessoas;
    }

    public boolean isInEstacao(int estacaoPosicao) {
        return posicao % 20 == estacaoPosicao % 20;
    }

    public boolean isInDesvio(int estacaoPosicao) {
        return (posicao == estacaoPosicao - 1) || (posicao == estacaoPosicao + 1);
    }
}


