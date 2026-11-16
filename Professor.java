// Autor: Rafael Marcos Santos Baiao - RA: 1252211620727 - Algoritmos e Programação - 13/11/2026

public class Professor extends FuncionarioPJ {

    public Professor(String nome, double valorHoraAula, double horasSemanais) {
        super(nome, valorHoraAula, horasSemanais, false); // professor é CLT
    }

    @Override
    public double calcularBruto() {
        double horasMensais = getHorasTrabalhadas() * 4.5;
        double remuneracaoAulas = getValorHora() * horasMensais;
        double dsr = remuneracaoAulas / 6.0;
        return remuneracaoAulas + dsr;
    }
}
