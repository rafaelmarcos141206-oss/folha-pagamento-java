// Autor: Rafael Marcos Santos Baiao - RA: 1252211620727 - Algoritmos e Programação - 13/11/2026

public class Funcionario {

    private String nome;
    private double salarioMensal;
    private final boolean pj;

    public Funcionario(String nome, double salarioMensal) {
        this(nome, salarioMensal, false);
    }

    protected Funcionario(String nome, double salarioMensal, boolean pj) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome inválido: o nome deve ser preenchido.");
        }
        if (salarioMensal < 0) {
            throw new IllegalArgumentException("Salário inválido: não pode ser negativo.");
        }
        this.nome = nome;
        this.salarioMensal = salarioMensal;
        this.pj = pj;
    }

    public String getNome() {
        return nome;
    }

    public double getSalarioMensal() {
        return salarioMensal;
    }

    public boolean isPJ() {
        return pj;
    }

    public double calcularBruto() {
        return salarioMensal;
    }

    public double calcularINSS() {
        if (pj) {
            return 0;
        }

        double bruto = calcularBruto();
        double inss = 0;

        if (bruto > 0) {
            inss += Math.min(bruto, 1621.00) * 0.075;
        }
        if (bruto > 1621.00) {
            inss += (Math.min(bruto, 2902.84) - 1621.00) * 0.09;
        }
        if (bruto > 2902.84) {
            inss += (Math.min(bruto, 4354.27) - 2902.84) * 0.12;
        }
        if (bruto > 4354.27) {
            inss += (Math.min(bruto, 8475.55) - 4354.27) * 0.14;
        }

        return arredondar(inss);
    }

    public double calcularIRRF() {
        if (pj) {
            return 0;
        }

        double bruto = calcularBruto();

        // 1. Base do IR: usa o MAIOR entre INSS e desconto simplificado (não soma)
        double deducao = Math.max(calcularINSS(), 607.20);
        double baseIR = Math.max(0, bruto - deducao);

        // 2. Tabela progressiva
        double aliquota;
        double parcelaDeduzir;

        if (baseIR <= 2428.80) {
            aliquota = 0;
            parcelaDeduzir = 0;
        } else if (baseIR <= 2826.65) {
            aliquota = 0.075;
            parcelaDeduzir = 182.16;
        } else if (baseIR <= 3751.05) {
            aliquota = 0.15;
            parcelaDeduzir = 394.16;
        } else if (baseIR <= 4664.68) {
            aliquota = 0.225;
            parcelaDeduzir = 675.49;
        } else {
            aliquota = 0.275;
            parcelaDeduzir = 908.73;
        }

        double impostoInicial = arredondar(Math.max(0, baseIR * aliquota - parcelaDeduzir));

        // 3. Redução pela remuneração bruta
        double reducao;
        if (bruto <= 5000.00) {
            reducao = impostoInicial; // IRRF final zero
        } else if (bruto <= 7350.00) {
            reducao = arredondar(Math.max(0, 978.62 - 0.133145 * bruto));
        } else {
            reducao = 0;
        }

        // 4. IRRF final nunca negativo
        return arredondar(Math.max(0, impostoInicial - reducao));
    }

    public double calcularFGTS() {
        if (pj) {
            return 0;
        }
        return arredondar(calcularBruto() * 0.08);
    }

    public double calcularMEI() {
        if (pj) {
            return 86.05;
        }
        return 0;
    }

    public double calcularLiquido() {
        // FGTS não entra: é depósito do empregador.
        // Para CLT, MEI é zero; para PJ, INSS e IRRF são zero.
        return arredondar(calcularBruto()
                - calcularINSS()
                - calcularIRRF()
                - calcularMEI());
    }

    protected double arredondar(double valor) {
        return Math.round(valor * 100.0) / 100.0;
    }
}
