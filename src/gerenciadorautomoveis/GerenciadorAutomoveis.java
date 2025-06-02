package gerenciadorautomoveis;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class GerenciadorAutomoveis {
    private ArrayList<Automovel> automoveis;
    private final String nomeArquivo = "automoveis.txt";

    public GerenciadorAutomoveis() {
        this.automoveis = new ArrayList<>();
        carregarDados();
    }

    public boolean adicionarAutomovel(Automovel automovel) {
        if (buscarAutomovelPorPlaca(automovel.getPlaca()).isPresent()) {
            return false;
        }
        this.automoveis.add(automovel);
        return true;
    }

    public boolean removerAutomovel(String placa) {
        Optional<Automovel> automovelParaRemover = buscarAutomovelPorPlaca(placa);
        if (automovelParaRemover.isPresent()) {
            this.automoveis.remove(automovelParaRemover.get());
            return true;
        }
        return false;
    }

    public boolean alterarAutomovel(String placa, String novoModelo, String novaMarca, int novoAno, double novoValor) {
        Optional<Automovel> automovelOptional = buscarAutomovelPorPlaca(placa);
        if (automovelOptional.isPresent()) {
            Automovel automovelParaAlterar = automovelOptional.get();
            automovelParaAlterar.setModelo(novoModelo);
            automovelParaAlterar.setMarca(novaMarca);
            automovelParaAlterar.setAno(novoAno);
            automovelParaAlterar.setValor(novoValor);
            return true;
        }
        return false;
    }

    public Optional<Automovel> consultarAutomovel(String placa) {
        return buscarAutomovelPorPlaca(placa);
    }

    private Optional<Automovel> buscarAutomovelPorPlaca(String placa) {
        for (Automovel auto : this.automoveis) {
            if (auto.getPlaca().equalsIgnoreCase(placa)) {
                return Optional.of(auto);
            }
        }
        return Optional.empty();
    }

    public List<Automovel> listarAutomoveisOrdenadosPorPlaca() {
        return this.automoveis.stream()
                .sorted(Comparator.comparing(Automovel::getPlaca))
                .collect(Collectors.toList());
    }

    public List<Automovel> listarAutomoveisOrdenadosPorModelo() {
        return this.automoveis.stream()
                .sorted(Comparator.comparing(Automovel::getModelo).thenComparing(Automovel::getPlaca))
                .collect(Collectors.toList());
    }

    public List<Automovel> listarAutomoveisOrdenadosPorMarca() {
        return this.automoveis.stream()
                .sorted(Comparator.comparing(Automovel::getMarca).thenComparing(Automovel::getPlaca))
                .collect(Collectors.toList());
    }

    public void salvarDados() {
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(nomeArquivo))) {
            for (Automovel automovel : this.automoveis) {
                escritor.write(automovel.toCsvString());
                escritor.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar dados no arquivo: " + e.getMessage());
        }
    }

    private void carregarDados() {
        try (BufferedReader leitor = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha;
            this.automoveis.clear();
            while ((linha = leitor.readLine()) != null) {
                Automovel automovel = Automovel.fromCsvString(linha);
                if (automovel != null) {
                    this.automoveis.add(automovel);
                }
            }
        } catch (IOException e) {
            System.err.println("Arquivo de dados '" + nomeArquivo + "' não encontrado ou erro ao ler. Iniciando com lista vazia.");
        }
    }
}
