// Autor: Rafael Marcos Santos Baiao - RA: 1252211620727 - Algoritmos e Programação - 13/11/2026

public class Main {

    public static void exibirFuncionario(Funcionario funcionario) {
        System.out.println("----------------------------------------");
        System.out.println("Nome: " + funcionario.getNome());
        System.out.println("Regime: " + (funcionario.isPJ() ? "PJ" : "CLT"));
        System.out.printf("Salário Bruto: R$ %.2f%n", funcionario.calcularBruto());
        System.out.printf("INSS: R$ %.2f%n", funcionario.calcularINSS());
        System.out.printf("IRRF: R$ %.2f%n", funcionario.calcularIRRF());
        System.out.printf("FGTS: R$ %.2f%n", funcionario.calcularFGTS());
        System.out.printf("DAS-MEI: R$ %.2f%n", funcionario.calcularMEI());
        System.out.printf("Salário Líquido: R$ %.2f%n", funcionario.calcularLiquido());
        System.out.println("----------------------------------------");
        System.out.println();
    }

    private static void testarCriacaoInvalida(String descricao, Runnable criacao) {
        try {
            criacao.run();
            System.out.println("FALHOU - " + descricao + ": objeto foi criado");
        } catch (IllegalArgumentException e) {
            System.out.println("OK - " + descricao + ": rejeitado (" + e.getMessage() + ")");
        }
    }

    public static void main(String[] args) {

        Funcionario ana = new Funcionario("Ana", 3000);
        Funcionario bruno = new Funcionario("Bruno", 6000);
        FuncionarioPJ carla = new FuncionarioPJ("Carla", 50, 120);
        Professor daniel = new Professor("Daniel", 50, 20);
        Professor elisa = new Professor("Elisa", 100, 20);

        exibirFuncionario(ana);
        exibirFuncionario(bruno);
        exibirFuncionario(carla);
        exibirFuncionario(daniel);
        exibirFuncionario(elisa);

        System.out.println("===== TESTES OBRIGATÓRIOS =====");

        // 1. Valores negativos
        testarCriacaoInvalida("Salário negativo", () -> new Funcionario("X", -1));
        testarCriacaoInvalida("Valor hora negativo", () -> new FuncionarioPJ("X", -50, 10));
        testarCriacaoInvalida("Horas negativas", () -> new FuncionarioPJ("X", 50, -10));
        testarCriacaoInvalida("Nome vazio", () -> new Funcionario(" ", 1000));

        // 2. CLT com salário zero
        exibirFuncionario(new Funcionario("CLT Zero", 0));

        // 3. PJ com zero horas
        exibirFuncionario(new FuncionarioPJ("PJ Zero Horas", 50, 0));

        // 4. Teto do INSS
        System.out.printf("INSS 8.000: %.2f | 10.500: %.2f | 12.000: %.2f%n",
                new Funcionario("A", 8000).calcularINSS(),
                new Funcionario("B", 10500).calcularINSS(),
                new Funcionario("C", 12000).calcularINSS());

        // 5. Isenção e redução parcial
        System.out.printf("IRRF 5.000: %.2f | 6.000: %.2f%n",
                new Funcionario("D", 5000).calcularIRRF(),
                new Funcionario("E", 6000).calcularIRRF());

        // 6. Chamar calcularLiquido duas vezes
        double l1 = bruno.calcularLiquido();
        double l2 = bruno.calcularLiquido();
        System.out.printf("Líquido Bruno 2x: %.2f e %.2f -> %s%n", l1, l2, l1 == l2 ? "OK" : "FALHOU");

        // 7. Ana não muda após criar Bruno
        System.out.printf("Líquido Ana após Bruno: %.2f%n", ana.calcularLiquido());
    }
}
