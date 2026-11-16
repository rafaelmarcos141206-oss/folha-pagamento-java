# Folha de pagamento em Java — Estudo guiado avaliativo

**Professor:** Alexandre Montanha
**Estudante:** Rafael Marcos Santos Baiao **RA:** 1252211620727 **Disciplina:** Algoritmos e Programação **Data:** 13/11/2026

## Como executar

```bash
javac *.java
java Main
```

Arquivos: `Funcionario.java` (CLT e cálculos tributários), `FuncionarioPJ.java` (bruto por hora + DAS),
`Professor.java` (bruto docente com DSR) e `Main.java` (cria os objetos e imprime os resultados).

## Resultados dos cinco objetos (saída de `java Main`)

| Objeto | Regime | Bruto | INSS | IRRF | FGTS | DAS | Líquido |
|---|---|---:|---:|---:|---:|---:|---:|
| Ana: mensal 3.000 | CLT | 3.000,00 | 248,60 | 0,00 | 240,00 | 0,00 | 2.751,40 |
| Bruno: mensal 6.000 | CLT | 6.000,00 | 641,51 | 385,10 | 480,00 | 0,00 | 4.973,39 |
| Carla: hora 50, 120 h/mês | PJ | 6.000,00 | 0,00 | 0,00 | 0,00 | 86,05 | 5.913,95 |
| Daniel: hora-aula 50, 20 h/semana | CLT | 5.250,00 | 536,51 | 89,53 | 420,00 | 0,00 | 4.623,96 |
| Elisa: hora-aula 100, 20 h/semana | CLT | 10.500,00 | 988,09 | 1.707,05 | 840,00 | 0,00 | 7.804,86 |

### Daniel calculado à mão

- Horas mensais = 20 × 4,5 = 90 h
- Remuneração das aulas = 50 × 90 = 4.500,00
- DSR = 4.500,00 ÷ 6 = 750,00 → **Bruto = 5.250,00**
- INSS = 1.621,00 × 7,5% (121,575) + 1.281,84 × 9% (115,3656) + 1.451,43 × 12% (174,1716) + 895,73 × 14% (125,4022) = 536,5144 → **536,51**
- IRRF: dedução = maior entre 536,51 e 607,20 = 607,20 → base = 4.642,80
  - Imposto inicial = 4.642,80 × 22,5% − 675,49 = **369,14**
  - Redução = 978,62 − 0,133145 × 5.250 = 279,60875 → **279,61**
  - IRRF final = 369,14 − 279,61 = **89,53**
- FGTS = 8% × 5.250 = **420,00**
- Líquido = 5.250,00 − 536,51 − 89,53 = **4.623,96**

## Ficha de testes

| Teste | Resultado esperado | Resultado obtido | Passou? | Correção realizada |
|---|---|---|---|---|
| Ana (CLT 3.000) | Bruto 3.000,00; INSS 248,60; IRRF 0,00; FGTS 240,00; Líquido 2.751,40 | Igual ao esperado | Sim | — |
| Bruno (CLT 6.000) | INSS 641,51; IRRF 385,10; FGTS 480,00; Líquido 4.973,39 | Igual ao esperado | Sim | — |
| Carla (PJ 50 × 120) | Bruto 6.000,00; DAS 86,05; Líquido 5.913,95 | Igual ao esperado | Sim | — |
| Daniel (Professor 50, 20 h/sem) | Bruto 5.250,00; INSS 536,51; IRRF 89,53; FGTS 420,00; Líquido 4.623,96 | Igual ao esperado | Sim | — |
| Elisa (Professor 100, 20 h/sem) | Bruto 10.500,00; INSS 988,09; IRRF 1.707,05; FGTS 840,00; Líquido 7.804,86 | Igual ao esperado | Sim | — |
| 1. Salário negativo | Criação rejeitada com `IllegalArgumentException` | Rejeitado: "Salário inválido: não pode ser negativo." | Sim | — |
| 1. Valor da hora negativo | Criação rejeitada | Rejeitado: "Valor da hora inválido: não pode ser negativo." | Sim | — |
| 1. Horas negativas | Criação rejeitada | Rejeitado: "Horas inválidas: não podem ser negativas." | Sim | — |
| 1. Nome em branco | Criação rejeitada | Rejeitado: "Nome inválido: o nome deve ser preenchido." | Sim | — |
| 2. CLT com salário zero | Todos os cálculos zero | Bruto, INSS, IRRF, FGTS, DAS e líquido = 0,00 | Sim | — |
| 3. PJ hora 50 e zero horas | Bruto 0,00; DAS 86,05; líquido −86,05 | Bruto 0,00; DAS 86,05; líquido −86,05 | Sim | — |
| 4. INSS 8.000 → 10.500 → 12.000 | Aumenta até o teto; 10.500 e 12.000 = 988,09 | 921,51 → 988,09 → 988,09 | Sim | — |
| 5. IRRF 5.000 e 6.000 | 5.000 isento (0,00); 6.000 redução parcial (385,10) | 0,00 e 385,10 | Sim | — |
| 6. `calcularLiquido()` duas vezes | Mesmo valor nas duas chamadas | 4.973,39 e 4.973,39 | Sim | — |
| 7. Ana recalculada após criar Bruno | Líquido de Ana continua 2.751,40 | 2.751,40 | Sim | — |

**Motivo dos resultados:**
1. Os construtores validam nome, salário, valor da hora e horas e lançam `IllegalArgumentException` antes de o objeto existir.
2. Com bruto zero todas as faixas do INSS ficam vazias, a base do IR é zero e 8% de zero é zero.
3. O DAS é fixo para o MEI e é devido mesmo sem faturamento, por isso o líquido fica negativo.
4. A parcela do salário acima de R$ 8.475,55 não entra no INSS, então a contribuição para de crescer no teto.
5. Até R$ 5.000,00 a redução zera o IR; entre R$ 5.000,00 e R$ 7.350,00 a redução só abate parte do imposto.
6. Os métodos só leem atributos e retornam valores; nenhum altera o estado do objeto.
7. Cada objeto guarda os próprios atributos privados; criar Bruno não mexe nos dados de Ana.

## Reflexão

**1. Qual é a diferença entre classe e objeto? Identifique dois objetos do seu programa.**
A classe é o molde: define quais atributos e métodos existem (por exemplo, `Funcionario` tem nome, salário e os métodos de cálculo). O objeto é uma instância concreta criada com `new`, com os seus próprios valores. No programa, `ana` (`new Funcionario("Ana", 3000)`) e `bruno` (`new Funcionario("Bruno", 6000)`) são dois objetos da mesma classe, cada um com seu salário.

**2. O que o construtor garante na criação? Por que o regime não tem setter?**
O construtor garante que nenhum objeto nasce inválido: o nome precisa estar preenchido e salário, valor da hora e horas não podem ser negativos; caso contrário ele lança `IllegalArgumentException`. Ele também fixa o regime (CLT ou PJ). O regime não tem setter porque é `final` e decide toda a tributação; se pudesse mudar depois, um mesmo objeto poderia ser calculado ora como CLT, ora como PJ, gerando resultados incoerentes.

**3. Por que o FGTS aparece no demonstrativo, mas não reduz o líquido?**
Porque o FGTS é um depósito feito pelo empregador numa conta do trabalhador, a mais do salário. Ele não é descontado do funcionário, então aparece só como informação e não entra em `calcularLiquido()`.

**4. Qual método foi sobrescrito? O que acontece quando o cálculo de INSS chama esse método em um professor?**
O método sobrescrito é `calcularBruto()` (em `FuncionarioPJ` e de novo em `Professor`). Quando `calcularINSS()` chama `calcularBruto()` num objeto `Professor`, o Java executa a versão do professor (polimorfismo / ligação dinâmica), ou seja, usa horas-aula × 4,5 × valor + DSR, e não o salário mensal. Assim os descontos herdados funcionam sem reescrever as fórmulas.

**5. Por que um professor CLT herdar de `FuncionarioPJ` causa um problema de modelagem? Sugira algo melhor.**
Herança significa "é um": dizer que `Professor` é um `FuncionarioPJ` é falso, pois o professor é CLT. A herança foi usada só para reaproveitar valor da hora e horas, e por isso foi preciso o construtor `protected` passando `pj = false`, o que confunde quem lê o código. Uma organização melhor seria criar uma classe intermediária neutra, como `FuncionarioHorista` (com valor da hora e horas), da qual herdariam `PrestadorPJ` e `Professor`; ou separar o regime numa estratégia própria (por exemplo, `RegimeCLT` e `RegimeMEI`) usada por composição, em vez de depender da hierarquia.
