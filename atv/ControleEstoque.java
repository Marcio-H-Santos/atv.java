import java.util.*;

class Produto {
    private String nome;
    private int qtdEstoque;
    private double precoUnitario;
    private String categoria;
    private int qtdMinima;

    public Produto(String nome, int qtdEstoque, double precoUnitario, String categoria, int qtdMinima) {
        this.nome = nome;
        this.qtdEstoque = qtdEstoque;
        this.precoUnitario = precoUnitario;
        this.categoria = categoria;
        this.qtdMinima = qtdMinima;
    }

    public String getNome() { return nome; }
    public int getQtdEstoque() { return qtdEstoque; }
    public double getPrecoUnitario() { return precoUnitario; }
    public String getCategoria() { return categoria; }
    public int getQtdMinima() { return qtdMinima; }

    public void setPrecoUnitario(double novoPreco) {
        this.precoUnitario = novoPreco;
    }

    public double valorTotal() {
        return qtdEstoque * precoUnitario;
    }

    @Override
    public String toString() {
        return nome + " | Estoque: " + qtdEstoque + " | Preço: R$ " + precoUnitario + " | Cat: " + categoria;
    }
}

public class ControleEstoque {
    private static Scanner sc = new Scanner(System.in);
    private static List<Produto> produtos = new ArrayList<>();

    public static void main(String[] args) {
        int opc;

        do {
            menu();
            opc = Integer.parseInt(sc.nextLine());

            switch (opc) {
                case 1 -> cadastrarProduto();
                case 2 -> listar();
                case 3 -> filtrarPorCategoria();
                case 4 -> ordenar();
                case 5 -> remover();
                case 6 -> atualizarPreco();
                case 7 -> listagemComSubtotal();
                case 0 -> System.out.println("Encerrando...");
                default -> System.out.println("Opção inválida!");
            }

        } while (opc != 0);
    }

    private static void menu() {
        System.out.println("\nMENU");
        System.out.println("1 - Cadastrar produto");
        System.out.println("2 - Listar");
        System.out.println("3 - Filtrar por categoria");
        System.out.println("4 - Ordenar (por nome)");
        System.out.println("5 - Remover produto");
        System.out.println("6 - Atualizar preço");
        System.out.println("7 - Listagem com subtotal por categoria");
        System.out.println("0 - Sair");
        System.out.print("Escolha: ");
    }

    private static void cadastrarProduto() {
        System.out.print("Nome: ");
        String nome = sc.nextLine();

        System.out.print("Quantidade em estoque: ");
        int qtd = Integer.parseInt(sc.nextLine());

        System.out.print("Preço unitário: ");
        double preco = Double.parseDouble(sc.nextLine());

        System.out.print("Categoria: ");
        String cat = sc.nextLine();

        System.out.print("Quantidade mínima: ");
        int min = Integer.parseInt(sc.nextLine());

        produtos.add(new Produto(nome, qtd, preco, cat, min));
        System.out.println("Cadastrado!");
    }

    private static void listar() {
        produtos.forEach(System.out::println);
    }

    private static void filtrarPorCategoria() {
        System.out.print("Informe a categoria: ");
        String cat = sc.nextLine();

        produtos.stream()
                .filter(p -> p.getCategoria().equalsIgnoreCase(cat))
                .forEach(System.out::println);
    }

    private static void ordenar() {
        produtos.sort(Comparator.comparing(Produto::getNome));
        System.out.println("Ordenado!");
    }

    private static void remover() {
        System.out.print("Nome do produto para remover: ");
        String nome = sc.nextLine();

        produtos.removeIf(p -> p.getNome().equalsIgnoreCase(nome));
        System.out.println("Removido (se existia).");
    }

    private static void atualizarPreco() {
        System.out.print("Nome do produto: ");
        String nome = sc.nextLine();

        for (Produto p : produtos) {
            if (p.getNome().equalsIgnoreCase(nome)) {
                System.out.print("Novo preço: ");
                double novo = Double.parseDouble(sc.nextLine());
                p.setPrecoUnitario(novo);
                System.out.println("Preço atualizado!");
                return;
            }
        }
        System.out.println("Produto não encontrado.");
    }

    private static void listagemComSubtotal() {
        Map<String, List<Produto>> mapa = new TreeMap<>();

        for (Produto p : produtos)
            mapa.computeIfAbsent(p.getCategoria(), k -> new ArrayList<>()).add(p);

        double totalGeral = 0;

        for (String cat : mapa.keySet()) {
            System.out.println("\nCategoria: " + cat);
            double subtotal = 0;

            for (Produto p : mapa.get(cat)) {
                System.out.println(p);
                subtotal += p.valorTotal();
            }

            System.out.println("Subtotal: R$ " + subtotal);
            totalGeral += subtotal;
        }

        System.out.println("\nTOTAL GERAL: R$ " + totalGeral);
    }
}
