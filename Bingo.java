import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Bingo {

    static final int quantidadenumSort = 5;
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
        int[][] cartelasIndicadas = new int[nomesJogadores.length][numerosCartela];
        int quantidadeRodadas = 0;
        int [] numSort = new int [quantidadeBolas];

        for (int i = 0; i < quantidadeBolas; i++) {
            numSort[i] = 0;
        }

        System.out.println("\n");
        automaticoManual();
        int respostaGerar = scanner.nextInt();

        switch (respostaGerar) {
            case 1:  criarCartelaManu(nomesJogadores, cartelas);
                break;

            case 2: criarCartelaAuto(nomesJogadores, cartelas);
                break;

            default:  
                System.out.println("\nA opção inserida é inválida. Por favor, tente novamente\n");
                break;
        }

        System.out.println("Resultado: \n");
        exibirCartelasNomes(nomesJogadores, cartelas);

        System.out.println("\n");
        automaticoManual();

        int respostaSorteio = scanner.nextInt();

        switch (respostaSorteio) {
            case 1:
                sortManu(numSort, nomesJogadores, cartelas, cartelasIndicadas, quantidadeRodadas);
                break;

            case 2:
                sortAuto(numSort, nomesJogadores, cartelas, cartelasIndicadas, quantidadeRodadas);
                break;

            default:
                 System.out.println("\nA opção inserida é inválida. Por favor, tente novamente\n");
                break;
        }

    }


    private static void exibirCartelasNomes(String[] nomesJogadores, int[][] cartelas) {
        for (int i = 0; i <nomesJogadores.length; i++){
            System.out.print(nomesJogadores[i]);
            System.out.println( Arrays.toString(cartelas[i]));
        }

    }


    private static void indicarCartela(String[] nomesJogadores, int[][] cartelas, int [] numSort, int[][] cartelasIndicadas) {
        for (int i = 0; i < nomesJogadores.length; i++) {

            for (int j = 0; j < cartelas[i].length; j++) {

                for (int k = 0; k < numSort.length; k++) {

                    if (cartelas[i][j] == numSort[k]) {

                        cartelasIndicadas[i][j] = 1;
                    }
                }
            }
        }
    
    }

    private static void sortAuto(int[] numSort, String[] nomesJogadores, int[][] cartelas, int[][] cartelasIndicadas, int quantidadeRodadas ) {
        int[] sorteio = new int[quantidadenumSort];
        for (int i = 0; i <sorteio.length; i++) {
            sorteio[i] = random.nextInt(1, 60);

            for (int j = 0; j < i; j++) {

                for (int k = 0; k < numSort.length; k++) {

                    if (sorteio[i] == sorteio[j] || sorteio[i] == numSort[k]) {
                        i--;
                    }
                    break;

                }
                break;

            }
            numSort[sorteio[i] - 1] = sorteio[i];

        }
        quantidadeRodadas += 1;

        System.out.println("A sequencia sorteada foi: " + Arrays.toString(sorteio));
        indicarCartela(nomesJogadores, cartelas, numSort, cartelasIndicadas);
        bingo(cartelasIndicadas, nomesJogadores, numSort, cartelas, quantidadeRodadas);
        repetirSortAuto(numSort, nomesJogadores, cartelas, cartelasIndicadas, quantidadeRodadas);
    }


    private static void automaticoManual() {
        System.out.println("Escolha o número da opção para gerar cartela: ");
        System.out.println("1. MANUAL");
        System.out.println("2. AUTOMÁTICO");
    }


    private static void criarCartelaManu(String[] nomesJogadores, int[][] cartelas) {
        int[] cartela = new int[numerosCartela];
        System.out.printf("Insira 5 números sortedos." +
                "\nExemplo: 2,26,15,4,31\n" );

        for (int i = 0; i <nomesJogadores.length; i++) {
            System.out.printf("Insira os números para o Jogador %s: \n", nomesJogadores[i]);
            String numeros = scanner.nextLine();
            String[] cartelaManual = numeros.split(",");

            for (int j = 0; j < cartelaManual.length; j++){
                cartela[j] = Integer.parseInt(cartelaManual[j]);
                cartelas[i][j] = cartela[j];
            }
        }
    }


    private static void mostrarnumSortCompleto(int[] numSort) {
        int qntNumSort = 0;
        System.out.print("\nA sequência sorteada: ");
        Arrays.sort(numSort);
        for (int i = 0; i < numSort.length; i++) {

            if(numSort[i] != 0) {
                System.out.print(numSort[i] + " ");
                qntNumSort += 1;
            }
        }

        System.out.printf("\nTotal de %d números numSort.\n", qntNumSort);
    }

    private static void repetirSortAuto(int []numSort, String[] nomesJogadores, int[][] cartelas, int[][] cartelasIndicadas, int quantidadeRodadas) {
        System.out.println("Escolha o número da opção almejada:");
        System.out.println("1. Novo Sorteio");
        System.out.println("2. Sair");

        int respostaNovoSorteio= scanner.nextInt();

        switch (respostaNovoSorteio) {
            case 1:
                sortAuto(numSort, nomesJogadores, cartelas, cartelasIndicadas, quantidadeRodadas);
                break;

            case 2: 
                System.out.println("FIM DE JOGO");
                break;

            default:
                System.out.println("A opção inserida é inválida. Por favor, tente novamente.");
                break;

        }

    }


    private static void criarCartelaAuto(String[] nomesJogadores, int[][] cartelas) {
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



    private static void bingo(int [][] cartelasIndicadas, String[] nomesJogadores, int [] numSort, int [][] cartelas, int quantidadeRodadas) {
        for (int i = 0; i < nomesJogadores.length; i++ ) {

            int[] qntMarcados = new int[nomesJogadores.length];
            qntMarcados[i] = 0;

            for (int j = 0; j < cartelasIndicadas[i].length; j++) {

                if (cartelasIndicadas[i][j] == 1) {
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
                mostrarnumSortCompleto(numSort);
                break;

            } else {
                System.out.printf("%s acertou %d vezes.\n", nomesJogadores[i], qntMarcados[i]);

            }
        }
        System.out.println("========================================");

    }


    private static void sortManu(int[] numSort, String[] nomesJogadores, int[][] cartelas, int[][] cartelasIndicadas, int quantidadeRodadas ) {
        int [] sorteio = new int[quantidadenumSort];

        System.out.printf("Insira 5 números sortedos ." + "\nExemplo: 2,26,15,4,31\n" );

        String numeros = scanner.next();
        String[] numSortManual = numeros.split(",");

        for (int i = 0; i < numSortManual.length; i++) {

            sorteio[i] = Integer.parseInt(numSortManual[i]);
            numSort[sorteio[i] - 1] = sorteio[i];
        }

        quantidadeRodadas += 1;

        indicarCartela(nomesJogadores, cartelas, numSort, cartelasIndicadas);
        bingo(cartelasIndicadas, nomesJogadores, numSort, cartelas,quantidadeRodadas);
        novoSortManu(numSort, nomesJogadores, cartelas, cartelasIndicadas, quantidadeRodadas);
    }


    private static void novoSortManu(int []numSort, String[] nomesJogadores, int[][] cartelas, int[][] cartelasIndicadas, int quantidadeRodadas) {
        System.out.println("Escolha o número da opção almejada: ");
        System.out.println("1. NOVO SORTEIO");
        System.out.println("2. Sair");

        int respostaNovoSorteio = scanner.nextInt();

        switch (respostaNovoSorteio) {
            case 1: 
                sortManu(numSort, nomesJogadores, cartelas, cartelasIndicadas, quantidadeRodadas);
                break;

            case 2: 
                System.out.println("FIM DE JOGO");
                break;

            default: 
                System.out.println("A opção inserida é inválida. Por favor, tente novamente.");
                break;

        }
    }
}