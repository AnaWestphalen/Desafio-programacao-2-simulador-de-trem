import java.util.Random;

public class Estacao {
    private int id;
    private int pessoasQueSubiram;
    private int pessoasQueDesceram;

    public Estacao(int id) {
        this.id = id;
        this.pessoasQueSubiram = 0;
        this.pessoasQueDesceram = 0;
    }

    public int getId() {
        return id;
    }

    public int getPessoasQueSubiram() {
        return pessoasQueSubiram;
    }

    public int getPessoasQueDesceram() {
        return pessoasQueDesceram;
    }

    public void registrarEmbarque(int pessoas) {
        this.pessoasQueSubiram += pessoas;
    }

    public void registrarDesembarque(int pessoas) {
        this.pessoasQueDesceram += pessoas;
    }

    public int[] gerenciarPassageiros(Random rand) {
        int maxDesembarque = Math.min(10, rand.nextInt(11));
        int maxEmbarque = Math.min(10, rand.nextInt(11));
        return new int[]{maxDesembarque, maxEmbarque};
    }
}
