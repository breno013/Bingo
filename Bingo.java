import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Bingo {

    static final int quantidadeSorteados = 5;
    static final int quantidadeBolas = 60;
    static final int numerosCartela = 5;
    private static Random random = new Random();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("===================================");
        System.out.println("============ BEM VINDO ============");
        System.out.println("=== AO BINGO LET'S CODE by ADA ===");
        System.out.println("===================================");
        
        System.out.println("\nInsira, por favor, os nomes dos jogadores:\n");
        System.out.println("Exemplo: player1-player2-player3");

        String nomes = scanner.next();
        String[] nomesJogadores = nomes.split("-");

        int[][] cartelas = new int[nomesJogadores.length][numerosCartela];
        int[][] cartelasMarcadas = new int[nomesJogadores.length][numerosCartela];
        int quantidadeRodadas = 0;
        int [] sorteados = new int [quantidadeBolas];

        for (int i = 0; i < quantidadeBolas; i++) {
            sorteados[i] = 0;
        }

        System.out.println("\n");
        automaticoManual();
        int respostaGerar = scanner.nextInt();

        switch (respostaGerar) {
            case 1:  gerarCartelaManual(nomesJogadores, cartelas);
                break;
            case 2: gerarCartelaAutomatico(nomesJogadores, cartelas);
                break;
            default:  
                System.out.println("\nA opção inserida é inválida. Por favor, tente novamente\n");
                break;
        }

        System.out.println("Resultado: \n");
        mostrarJogadoresECartelas(nomesJogadores, cartelas);
        System.out.println("\n");
        automaticoManual();
        int respostaSorteio = scanner.nextInt();

        switch (respostaSorteio) {
            case 1:
                sorteioManual(sorteados, nomesJogadores, cartelas, cartelasMarcadas, quantidadeRodadas);
                break;
            case 2:
                sorteioAutomatico(sorteados, nomesJogadores, cartelas, cartelasMarcadas, quantidadeRodadas);
                break;
            default:
                 System.out.println("\nA opção inserida é inválida. Por favor, tente novamente\n");
                break;
        }
    }

    private static void bingo(int [][] cartelasMarcadas, String[] nomesJogadores, int [] sorteados, int [][] cartelas, int quantidadeRodadas) {
        for (int i = 0; i < nomesJogadores.length; i++ ) {
            int[] qntMarcados = new int[nomesJogadores.length];
            qntMarcados[i] = 0;
            for (int j = 0; j < cartelasMarcadas[i].length; j++) {
                if (cartelasMarcadas[i][j] == 1) {
                    qntMarcados[i] += 1;
                }
            }
            if (qntMarcados[i] == 5) {
               System.out.printf("%s é o vencedor !!.\n", nomesJogadores[i]);
                System.out.println("========================================");
                System.out.println("--------------Estatísticas--------------");
                System.out.printf("Quantidade de rodadas: %d.\n", quantidadeRodadas);
                Arrays.sort(cartelas[i]);
                System.out.println("A cartela vencedora foi: " + Arrays.toString(cartelas[i]));
                todosOsSorteados(sorteados);
                break;
            } else {
                System.out.printf("%s acertou %d vezes.\n", nomesJogadores[i], qntMarcados[i]);
            }
        }
        System.out.println("========================================");
    }

    private static void todosOsSorteados(int[] sorteados) {
        int qntNumSorteados = 0;
        System.out.print("\nA sequência sorteada: ");
        Arrays.sort(sorteados);
        for (int i = 0; i < sorteados.length; i++) {
            if(sorteados[i] != 0) {
                System.out.print(sorteados[i] + " ");
                qntNumSorteados += 1;
            }
        }
        System.out.printf("\nTotal de %d números sorteados.\n", qntNumSorteados);
    }

    private static void marcarCartela(String[] nomesJogadores, int[][] cartelas, int [] sorteados, int[][] cartelasMarcadas) {
        for (int i = 0; i < nomesJogadores.length; i++) {
            for (int j = 0; j < cartelas[i].length; j++) {
                for (int k = 0; k < sorteados.length; k++) {
                    if (cartelas[i][j] == sorteados[k]) {
                        cartelasMarcadas[i][j] = 1;
                    }
                }
            }
        }
    }

    private static void novoSorteioAutomatico(int []sorteados, String[] nomesJogadores, int[][] cartelas, int[][] cartelasMarcadas, int quantidadeRodadas) {
        System.out.println("Escolha o número da opção almejada:");
        System.out.println("1 - Novo Sorteio");
        System.out.println("2 - Sair");
        int opNovoSorteio = scanner.nextInt();
        switch (opNovoSorteio) {
            case 1 -> sorteioAutomatico(sorteados, nomesJogadores, cartelas, cartelasMarcadas, quantidadeRodadas);
            case 2 -> System.out.println("FIM DE JOGO");
            default -> System.out.println("A opção inserida é inválida. Por favor, tente novamente.");
        }
    }

    private static void sorteioAutomatico(int[] sorteados, String[] nomesJogadores, int[][] cartelas, int[][] cartelasMarcadas, int quantidadeRodadas ) {
        int[] sorteio = new int[quantidadeSorteados];
        for (int i = 0; i <sorteio.length; i++) {
            sorteio[i] = random.nextInt(1, 60);
            for (int j = 0; j < i; j++) {
                for (int k = 0; k < sorteados.length; k++) {
                    if (sorteio[i] == sorteio[j] || sorteio[i] == sorteados[k]) {
                        i--;
                    }
                    break;
                }
                break;
            }
            sorteados[sorteio[i] - 1] = sorteio[i];
        }
        quantidadeRodadas += 1;

        System.out.println("A sequencia sorteada foi: " + Arrays.toString(sorteio));
        marcarCartela(nomesJogadores, cartelas, sorteados, cartelasMarcadas);
        bingo(cartelasMarcadas, nomesJogadores, sorteados, cartelas, quantidadeRodadas);
        novoSorteioAutomatico(sorteados, nomesJogadores, cartelas, cartelasMarcadas, quantidadeRodadas);
    }

    private static void novoSorteioManual(int []sorteados, String[] nomesJogadores, int[][] cartelas, int[][] cartelasMarcadas, int quantidadeRodadas) {
        System.out.println("Escolha o número da opção almejada: ");
        System.out.println("1. NOVO SORTEIO");
        System.out.println("2. Sair");
        int opNovoSorteio = scanner.nextInt();
        switch (opNovoSorteio) {
            case 1 -> sorteioManual(sorteados, nomesJogadores, cartelas, cartelasMarcadas, quantidadeRodadas);
            case 2 -> System.out.println("FIM DE JOGO");
            default -> System.out.println("A opção inserida é inválida. Por favor, tente novamente.");
        }
    }
    private static void sorteioManual(int[] sorteados, String[] nomesJogadores, int[][] cartelas, int[][] cartelasMarcadas, int quantidadeRodadas ) {
        int [] sorteio = new int[quantidadeSorteados];
        System.out.printf("Insira 5 números sortedos ." +
                "\nExemplo: 2,26,15,4,31\n" );

        String numeros = scanner.next();
        String[] sorteadosManual = numeros.split(",");
        for (int i = 0; i < sorteadosManual.length; i++) {
            sorteio[i] = Integer.parseInt(sorteadosManual[i]);
            sorteados[sorteio[i] - 1] = sorteio[i];
        }
        quantidadeRodadas += 1;
        marcarCartela(nomesJogadores, cartelas, sorteados, cartelasMarcadas);
        bingo(cartelasMarcadas, nomesJogadores, sorteados, cartelas,quantidadeRodadas);
        novoSorteioManual(sorteados, nomesJogadores, cartelas, cartelasMarcadas, quantidadeRodadas);
    }

    private static void mostrarJogadoresECartelas(String[] nomesJogadores, int[][] cartelas) {
        for (int i = 0; i <nomesJogadores.length; i++){
            System.out.print(nomesJogadores[i]);
            System.out.println( Arrays.toString(cartelas[i]));
        }
    }
    private static void automaticoManual() {
        System.out.println("Escolha o número da opção para gerar cartela: ");
        System.out.println("1 - MANUAL");
        System.out.println("2 - AUTOMÁTICO");
    }
    private static void gerarCartelaAutomatico(String[] nomesJogadores, int[][] cartelas) {
        int[] cartela = new int[numerosCartela];

        for (int i = 0; i <nomesJogadores.length; i++) {
            for (int j = 0; j < cartela.length; j++) {
                cartela[j] = random.nextInt(1, 60);
                for (int k = 0; k < j; k++ ) {
                    if (cartela[j] == cartela[k]) {
                        j--;
                    }
                }
                cartelas[i][j] = cartela[j];
            }
        }
    }
    private static void gerarCartelaManual(String[] nomesJogadores, int[][] cartelas) {
        int[] cartela = new int[numerosCartela];
        System.out.printf("Insira 5 números sortedos." +
                "\nExemplo: 2,26,15,4,31\n" );

        for (int i = 0; i <nomesJogadores.length; i++) {
            System.out.printf("Entre com os números sorteados para o Jogador %s: \n", nomesJogadores[i]);
            String numeros = scanner.nextLine();
            String[] cartelaManual = numeros.split(",");
            for (int j = 0; j < cartelaManual.length; j++){
                cartela[j] = Integer.parseInt(cartelaManual[j]);
                cartelas[i][j] = cartela[j];
            }
        }
    }
}