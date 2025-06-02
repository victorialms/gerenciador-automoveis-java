package gerenciadorautomoveis;

public class Automovel {
    private String placa;
    private String modelo;
    private String marca;
    private int ano;
    private double valor;

    public Automovel(String placa, String modelo, String marca, int ano, double valor) {
        this.placa = placa;
        this.modelo = modelo;
        this.marca = marca;
        this.ano = ano;
        this.valor = valor;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "Placa: " + placa + ", Modelo: " + modelo + ", Marca: " + marca + ", Ano: " + ano + ", Valor: R$" + String.format("%.2f", valor);
    }

    public String toCsvString() {
        return placa + "," + modelo + "," + marca + "," + ano + "," + valor;
    }

    public static Automovel fromCsvString(String csvString) {
        String[] campos = csvString.split(",");
        if (campos.length == 5) {
            try {
                String placa = campos[0].trim();
                String modelo = campos[1].trim();
                String marca = campos[2].trim();
                int ano = Integer.parseInt(campos[3].trim());
                double valor = Double.parseDouble(campos[4].trim());
                return new Automovel(placa, modelo, marca, ano, valor);
            } catch (NumberFormatException e) {
                System.err.println("Erro ao converter dados do CSV para Automovel: " + csvString + " - " + e.getMessage());
                return null;
            }
        }
        System.err.println("Linha CSV inválida: " + csvString);
        return null;
    }
}
