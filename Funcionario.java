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

        // Cada alíquota incide só sobre a parte do salário que cai na sua faixa.
        // 1ª faixa: até 1.621,00 (7,5%)
        if (bruto > 1621.00) {
            inss += 1621.00 * 0.075;
        } else {
            inss += bruto * 0.075;
        }

        // 2ª faixa: de 1.621,00 até 2.902,84 (9%)
        if (bruto > 2902.84) {
            inss += (2902.84 - 1621.00) * 0.09;
        } else if (bruto > 1621.00) {
            inss += (bruto - 1621.00) * 0.09;
        }

        // 3ª faixa: de 2.902,84 até 4.354,27 (12%)
        if (bruto > 4354.27) {
            inss += (4354.27 - 2902.84) * 0.12;
        } else if (bruto > 2902.84) {
            inss += (bruto - 2902.84) * 0.12;
        }

        // 4ª faixa: de 4.354,27 até 8.475,55 (14%); acima do teto não contribui mais
        if (bruto > 8475.55) {
            inss += (8475.55 - 4354.27) * 0.14;
        } else if (bruto > 4354.27) {
            inss += (bruto - 4354.27) * 0.14;
        }

        // Arredonda só o total, nunca cada faixa
        return arredondar(inss);
    }

    public double calcularIRRF() {
        if (pj) {
            return 0;
        }

        double bruto = calcularBruto();

        // 1. Dedução: o MAIOR entre o INSS e o desconto simplificado (não somar os dois)
        double inss = calcularINSS();
        double deducao = 607.20;
        if (inss > deducao) {
            deducao = inss;
        }

        // Base do IR nunca fica abaixo de zero
        double baseIR = bruto - deducao;
        if (baseIR < 0) {
            baseIR = 0;
        }

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

        // Imposto inicial nunca negativo, arredondado
        double impostoInicial = baseIR * aliquota - parcelaDeduzir;
        if (impostoInicial < 0) {
            impostoInicial = 0;
        }
        impostoInicial = arredondar(impostoInicial);

        // 3. Redução calculada pela remuneração BRUTA
        double reducao;
        if (bruto <= 5000.00) {
            reducao = impostoInicial; // até 5.000,00 o IRRF final é zero
        } else if (bruto <= 7350.00) {
            reducao = 978.62 - 0.133145 * bruto;
            if (reducao < 0) {
                reducao = 0;
            }
            reducao = arredondar(reducao);
        } else {
            reducao = 0; // acima de 7.350,00 não há redução
        }

        // 4. IRRF final nunca negativo, arredondado
        double irrf = impostoInicial - reducao;
        if (irrf < 0) {
            irrf = 0;
        }
        return arredondar(irrf);
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
