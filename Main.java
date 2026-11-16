// Autor: Rafael Marcos Santos Baiao - RA: 1252211620727 - Algoritmos e Programação - 13/11/2026

public class Main {

    // Imprime nome, regime, bruto, INSS, IRRF, FGTS, DAS e líquido de qualquer funcionário
    public static void exibirFuncionario(Funcionario funcionario) {
        String regime;
        if (funcionario.isPJ()) {
            regime = "PJ";
        } else {
            regime = "CLT";
        }

        System.out.println("----------------------------------------");
        System.out.println("Nome: " + funcionario.getNome());
        System.out.println("Regime: " + regime);
        System.out.printf("Bruto: R$ %.2f%n", funcionario.calcularBruto());
        System.out.printf("INSS: R$ %.2f%n", funcionario.calcularINSS());
        System.out.printf("IRRF: R$ %.2f%n", funcionario.calcularIRRF());
        System.out.printf("FGTS: R$ %.2f%n", funcionario.calcularFGTS());
        System.out.printf("DAS: R$ %.2f%n", funcionario.calcularMEI());
        System.out.printf("Líquido: R$ %.2f%n", funcionario.calcularLiquido());
        System.out.println("----------------------------------------");
        System.out.println();
    }

    public static void main(String[] args) {

        Funcionario ana = new Funcionario("Ana", 3000.00);
        Funcionario bruno = new Funcionario("Bruno", 6000.00);
        FuncionarioPJ carla = new FuncionarioPJ("Carla", 50, 120);
        Professor daniel = new Professor("Daniel", 50, 20);
        Professor elisa = new Professor("Elisa", 100, 20);

        exibirFuncionario(ana);
        exibirFuncionario(bruno);
        exibirFuncionario(carla);
        exibirFuncionario(daniel);
        exibirFuncionario(elisa);

        System.out.println("===== TESTES OBRIGATÓRIOS =====");
        System.out.println();

        // Teste 1: salário, valor da hora e horas negativos devem impedir a criação.
        // O try/catch captura a exceção para o programa continuar e rodar os outros testes.
        try {
            new Funcionario("Teste", -1);
            System.out.println("Teste 1 (salário negativo): FALHOU, o objeto foi criado");
        } catch (IllegalArgumentException e) {
            System.out.println("Teste 1 (salário negativo): rejeitado - " + e.getMessage());
        }

        try {
            new FuncionarioPJ("Teste", -50, 10);
            System.out.println("Teste 1 (valor da hora negativo): FALHOU, o objeto foi criado");
        } catch (IllegalArgumentException e) {
            System.out.println("Teste 1 (valor da hora negativo): rejeitado - " + e.getMessage());
        }

        try {
            new FuncionarioPJ("Teste", 50, -10);
            System.out.println("Teste 1 (horas negativas): FALHOU, o objeto foi criado");
        } catch (IllegalArgumentException e) {
            System.out.println("Teste 1 (horas negativas): rejeitado - " + e.getMessage());
        }

        // Regra comum: o nome deve ser preenchido
        try {
            new Funcionario(" ", 1000);
            System.out.println("Teste 1 (nome em branco): FALHOU, o objeto foi criado");
        } catch (IllegalArgumentException e) {
            System.out.println("Teste 1 (nome em branco): rejeitado - " + e.getMessage());
        }
        System.out.println();

        // Teste 2: CLT com salário zero, todos os cálculos devem retornar zero
        System.out.println("Teste 2 (CLT com salário zero):");
        exibirFuncionario(new Funcionario("CLT salário zero", 0));

        // Teste 3: PJ com hora 50 e zero horas, bruto zero, DAS 86,05 e líquido -86,05
        System.out.println("Teste 3 (PJ com zero horas):");
        exibirFuncionario(new FuncionarioPJ("PJ zero horas", 50, 0));

        // Teste 4: o INSS aumenta até o teto e para em 988,09
        System.out.printf("Teste 4 (INSS): 8.000 = %.2f | 10.500 = %.2f | 12.000 = %.2f%n",
                new Funcionario("CLT 8.000", 8000).calcularINSS(),
                new Funcionario("CLT 10.500", 10500).calcularINSS(),
                new Funcionario("CLT 12.000", 12000).calcularINSS());

        // Teste 5: isenção de IRRF em 5.000 e redução parcial em 6.000
        System.out.printf("Teste 5 (IRRF): 5.000 = %.2f | 6.000 = %.2f%n",
                new Funcionario("CLT 5.000", 5000).calcularIRRF(),
                new Funcionario("CLT 6.000", 6000).calcularIRRF());

        // Teste 6: calcular o líquido duas vezes não pode mudar o resultado nem o bruto
        double liquido1 = bruno.calcularLiquido();
        double liquido2 = bruno.calcularLiquido();
        System.out.printf("Teste 6 (líquido de Bruno duas vezes): %.2f e %.2f, bruto continua %.2f%n",
                liquido1, liquido2, bruno.calcularBruto());
        if (liquido1 == liquido2) {
            System.out.println("Teste 6: OK, o resultado não mudou");
        } else {
            System.out.println("Teste 6: FALHOU, o resultado mudou");
        }
        System.out.println();

        // Teste 7: Ana recalculada depois de criar Bruno deve ter os mesmos valores
        System.out.println("Teste 7 (Ana recalculada depois de criar Bruno):");
        exibirFuncionario(ana);
    }
}
