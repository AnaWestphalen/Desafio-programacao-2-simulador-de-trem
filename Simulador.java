import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Simulador {
    private Estacao[] estacoes;
    private Random rand;
    private int numeroEstacoes;
    private Trilho trilhoA;
    private Trilho trilhoB;
    private List<Trem> trens;
    private List<String> log;

    public Simulador(int numeroEstacoes) {
        this.numeroEstacoes = numeroEstacoes;
        this.estacoes = new Estacao[numeroEstacoes];
        for (int i = 0; i < numeroEstacoes; i++) {
            estacoes[i] = new Estacao(i + 1);
        }
        this.rand = new Random();
        this.trilhoA = new Trilho(numeroEstacoes * 20);
        this.trilhoB = new Trilho(numeroEstacoes * 20);
        this.trens = new ArrayList<>();
        this.log = new ArrayList<>();
    }

    public void iniciarSimulacao() {
        Scanner scanner = new Scanner(System.in);
        boolean rodando = true;

        // Adicionando trens iniciais
        adicionarTrem(8, 0, true); // Trem inicial indo de A para B
        adicionarTrem(8, 0, false); // Trem inicial indo de B para A

        while (rodando) {
            System.out.println("Pressione qualquer tecla para avançar 1 minuto ou 'q' para sair.");
            String input = scanner.nextLine();

            if (input.equals("q")) {
                rodando = false;
            } else {
                avancarSimulacao();
            }
        }

        scanner.close();
        gerarRelatorio();
    }

    private void adicionarTrem(int hora, int minuto, boolean indoParaB) {
        int id = trens.size() + 1;
        Trem novoTrem = new Trem(id, indoParaB);
        trens.add(novoTrem);
        log.add(String.format("Trem %d saiu às %02d:%02d indo para %s", id, hora, minuto, indoParaB ? "B" : "A"));
    }

    private void avancarSimulacao() {
        System.out.println("Avançando 1 minuto na simulação...");

        for (Trem trem : trens) {
            if (trem.isIndoParaB()) {
                avancarTrem(trem, trilhoA, trilhoB);
            } else {
                avancarTrem(trem, trilhoB, trilhoA);
            }
        }

        // Adicionar novos trens a cada meia hora
        int currentMinute = (trens.size() * 30) % 1440;
        int currentHour = (8 + currentMinute / 60) % 24;
        int minuteOfHour = currentMinute % 60;

        if (minuteOfHour == 0 || minuteOfHour == 30) {
            if (currentHour >= 8 && currentHour < 17 || (currentHour == 17 && minuteOfHour == 0)) {
                adicionarTrem(currentHour, minuteOfHour, true);
                adicionarTrem(currentHour, minuteOfHour, false);
            }
        }
    }

    private void avancarTrem(Trem trem, Trilho trilhoPrincipal, Trilho trilhoOposto) {
        int estacaoPosicao = (trem.getPosicao() / 20) * 20;

        if (trem.isInEstacao(estacaoPosicao)) {
            int[] passageiros = estacoes[estacaoPosicao / 20].gerenciarPassageiros(rand);
            int desceram = Math.min(passageiros[0], trem.getPassageiros());
            trem.desembarcar(desceram);
            estacoes[estacaoPosicao / 20].registrarDesembarque(desceram);

            int subiram = Math.min(passageiros[1], 50 - trem.getPassageiros());
            trem.embarcar(subiram);
            estacoes[estacaoPosicao / 20].registrarEmbarque(subiram);

            log.add(String.format("Trem %d na estação %d: desceram %d, subiram %d", trem.getId(), estacaoPosicao / 20 + 1, desceram, subiram));
        }

        if (!trem.isInDesvio(estacaoPosicao)) {
            trem.mover();
        } else {
            // Checar se o desvio está livre
            boolean desvioLivre = true;
            for (Trem outroTrem : trens) {
                if (outroTrem != trem && outroTrem.getPosicao() == trem.getPosicao() + 1) {
                    desvioLivre = false;
                    break;
                }
            }

            if (desvioLivre) {
                trem.mover();
            } else {
                log.add(String.format("Trem %d esperando no desvio antes da estação %d", trem.getId(), estacaoPosicao / 20 + 1));
            }
        }
    }

    private void gerarRelatorio() {
        try (FileWriter writer = new FileWriter("relatorio.txt")) {
            for (Estacao estacao : estacoes) {
                writer.write(String.format("Estação %d: subiram %d, desceram %d%n", estacao.getId(), estacao.getPessoasQueSubiram(), estacao.getPessoasQueDesceram()));
            }
            writer.write("\nLog de Movimentações:\n");
            for (String entrada : log) {
                writer.write(entrada + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        int numeroEstacoes = new Random().nextInt(21) + 10; // 10 a 30 estações
        Simulador simulador = new Simulador(numeroEstacoes);
        simulador.iniciarSimulacao();
    }
}


