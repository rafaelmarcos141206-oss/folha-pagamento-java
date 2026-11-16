// Autor: Rafael Marcos Santos Baiao - RA: 1252211620727 - Algoritmos e Programação - 13/11/2026

public class FuncionarioPJ extends Funcionario {

    private double valorHora;
    private double horasTrabalhadas;

    public FuncionarioPJ(String nome, double valorHora, double horasTrabalhadas) {
        this(nome, valorHora, horasTrabalhadas, true);
    }

    protected FuncionarioPJ(String nome, double valorHora,
                            double horasTrabalhadas, boolean pj) {
        super(nome, 0.0, pj);
        if (valorHora < 0) {
            throw new IllegalArgumentException("Valor da hora inválido: não pode ser negativo.");
        }
        if (horasTrabalhadas < 0) {
            throw new IllegalArgumentException("Horas inválidas: não podem ser negativas.");
        }
        this.valorHora = valorHora;
        this.horasTrabalhadas = horasTrabalhadas;
    }

    public double getValorHora() {
        return valorHora;
    }

    public double getHorasTrabalhadas() {
        return horasTrabalhadas;
    }

    @Override
    public double calcularBruto() {
        return valorHora * horasTrabalhadas;
    }
}
