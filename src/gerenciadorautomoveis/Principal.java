package gerenciadorautomoveis;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {
    private static GerenciadorAutomoveis gerenciador = new GerenciadorAutomoveis();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int opcao;
        do {
            exibirMenu();
            opcao = lerOpcaoDoMenu();

            switch (opcao) {
                case 1:
                    incluirAutomovel();
                    break;
                case 2:
                    removerAutomovel();
                    break;
                case 3:
                    alterarAutomovel();
                    break;
                case 4:
                    consultarAutomovel();
                    break;
                case 5:
                    listarAutomoveis();
                    break;
                case 6:
                    salvarESair();
                    break;
                case -1:
                    System.out.println("Entrada inválida. Por favor, digite um número para a opção.");
                    break;
                default:
                    if (opcao != 6) {
                       System.out.println("Opção inválida. Tente novamente.");
                    }
            }
            if (opcao != 6) {
                System.out.println("\nPressione Enter para continuar...");
                scanner.nextLine(); 
            }
            System.out.println();
        } while (opcao != 6);
        scanner.close();
    }

    private static void exibirMenu() {
        System.out.println("--- Sistema de Cadastro de Automóveis ---");
        System.out.println("1 - Incluir automóvel");
        System.out.println("2 - Remover automóvel");
        System.out.println("3 - Alterar dados de automóvel");
        System.out.println("4 - Consultar automóvel por placa");
        System.out.println("5 - Listar automóveis (ordenado)");
        System.out.println("6 - Salvar e sair");
        System.out.print("Escolha uma opção: ");
    }

    private static int lerOpcaoDoMenu() {
        try {
            int opcao = scanner.nextInt();
            scanner.nextLine(); 
            return opcao;
        } catch (InputMismatchException e) {
            scanner.nextLine(); 
            return -1; 
        }
    }
    
    private static String lerString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    private static int lerInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int valor = scanner.nextInt();
                scanner.nextLine(); 
                return valor;
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, insira um número inteiro.");
                scanner.nextLine(); 
            }
        }
    }

    private static double lerDouble(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                double valor = scanner.nextDouble();
                scanner.nextLine(); 
                return valor;
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, insira um número decimal (ex: 1234.56).");
                scanner.nextLine(); 
            }
        }
    }

    private static void incluirAutomovel() {
        System.out.println("\n--- Inclusão de Automóvel ---");
        String placa = lerString("Placa: ");
        if (gerenciador.consultarAutomovel(placa).isPresent()) {
            System.out.println("Erro: Placa já cadastrada.");
            return;
        }
        String modelo = lerString("Modelo: ");
        String marca = lerString("Marca: ");
        int ano = lerInt("Ano: ");
        double valor = lerDouble("Valor: R$");

        Automovel novoAutomovel = new Automovel(placa, modelo, marca, ano, valor);
        if (gerenciador.adicionarAutomovel(novoAutomovel)) {
            System.out.println("Automóvel incluído com sucesso!");
        } else {
            System.out.println("Erro ao incluir automóvel. A placa pode já existir.");
        }
    }

    private static void removerAutomovel() {
        System.out.println("\n--- Remoção de Automóvel ---");
        String placa = lerString("Digite a placa do automóvel a ser removido: ");
        if (gerenciador.removerAutomovel(placa)) {
            System.out.println("Automóvel removido com sucesso!");
        } else {
            System.out.println("Erro: Automóvel não encontrado com a placa informada.");
        }
    }

    private static void alterarAutomovel() {
        System.out.println("\n--- Alteração de Dados do Automóvel ---");
        String placa = lerString("Digite a placa do automóvel a ser alterado: ");
        Optional<Automovel> automovelOptional = gerenciador.consultarAutomovel(placa);

        if (automovelOptional.isPresent()) {
            Automovel automovel = automovelOptional.get();
            System.out.println("Dados atuais: " + automovel);

            String novoModelo = lerString("Novo Modelo (atual: " + automovel.getModelo() + "): ");
            String novaMarca = lerString("Nova Marca (atual: " + automovel.getMarca() + "): ");
            int novoAno = lerInt("Novo Ano (atual: " + automovel.getAno() + "): ");
            double novoValor = lerDouble("Novo Valor (atual: R$" + String.format("%.2f", automovel.getValor()) + "): R$");

            if (gerenciador.alterarAutomovel(placa, novoModelo, novaMarca, novoAno, novoValor)) {
                System.out.println("Dados do automóvel alterados com sucesso!");
            } else {
                System.out.println("Erro ao alterar dados do automóvel.");
            }
        } else {
            System.out.println("Erro: Automóvel não encontrado com a placa informada.");
        }
    }

    private static void consultarAutomovel() {
        System.out.println("\n--- Consulta de Automóvel por Placa ---");
        String placa = lerString("Digite a placa do automóvel a ser consultado: ");
        Optional<Automovel> automovelOptional = gerenciador.consultarAutomovel(placa);

        if (automovelOptional.isPresent()) {
            System.out.println("Automóvel encontrado:");
            System.out.println(automovelOptional.get());
        } else {
            System.out.println("Automóvel não encontrado com a placa informada.");
        }
    }

    private static void listarAutomoveis() {
        System.out.println("\n--- Listagem de Automóveis ---");
        System.out.println("Ordenar por:");
        System.out.println("1 - Placa");
        System.out.println("2 - Modelo");
        System.out.println("3 - Marca");
        System.out.print("Escolha o critério de ordenação (1-3): ");
        int criterio = lerOpcaoDoMenu();
        
        List<Automovel> automoveisListados;

        switch (criterio) {
            case 1:
                automoveisListados = gerenciador.listarAutomoveisOrdenadosPorPlaca();
                break;
            case 2:
                automoveisListados = gerenciador.listarAutomoveisOrdenadosPorModelo();
                break;
            case 3:
                automoveisListados = gerenciador.listarAutomoveisOrdenadosPorMarca();
                break;
            default:
                System.out.println("Critério de ordenação inválido.");
                return;
        }

        if (automoveisListados.isEmpty()) {
            System.out.println("Nenhum automóvel cadastrado.");
        } else {
            System.out.println("Lista de Automóveis:");
            for (Automovel automovel : automoveisListados) {
                System.out.println(automovel);
            }
        }
    }

    private static void salvarESair() {
        gerenciador.salvarDados();
        System.out.println("Dados salvos com sucesso. Saindo do sistema...");
    }
}
