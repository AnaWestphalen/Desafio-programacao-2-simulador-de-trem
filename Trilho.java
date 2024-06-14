public class Trilho {
    private class No {
        No proximo;
    }

    private No inicio;

    public Trilho(int tamanho) {
        inicio = new No();
        No atual = inicio;
        for (int i = 1; i < tamanho; i++) {
            atual.proximo = new No();
            atual = atual.proximo;
        }
    }

    public No getInicio() {
        return inicio;
    }
}

