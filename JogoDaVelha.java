/**
 * @author Gabriel
 */
import java.util.Scanner;

public class JogoDaVelha {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        do {
            jogarJogo();
            System.out.println("Deseja jogar novamente? (Digite 's' para sim, 'n' para não):");
        } while (scanner.next().equalsIgnoreCase("s"));
    }

    public static void jogarJogo() {
        char[][] tabuleiro = {{' ', ' ', ' '}, {' ', ' ', ' '}, {' ', ' ', ' '}};
        char jogadorAtual = 'X';
        boolean jogoAcabou = false;

        while (!jogoAcabou) {
            exibirTabuleiro(tabuleiro);
            System.out.println("Jogador " + jogadorAtual + ", é a sua vez. Escolha uma posição (linha e coluna):");
            int linha = lerEntrada() - 1;
            int coluna = lerEntrada() - 1;

            if (posicaoValida(linha, coluna, tabuleiro)) {
                tabuleiro[linha][coluna] = jogadorAtual;

                if (verificarVitoria(jogadorAtual, tabuleiro)) {
                    exibirTabuleiro(tabuleiro);
                    System.out.println("Parabéns, jogador " + jogadorAtual + "! Você venceu!");
                    jogoAcabou = true;
                } else if (verificarEmpate(tabuleiro)) {
                    exibirTabuleiro(tabuleiro);
                    System.out.println("O jogo empatou!");
                    jogoAcabou = true;
                } else {
                    jogadorAtual = jogadorAtual == 'X' ? 'O' : 'X';
                }
            } else {
                System.out.println("Posição inválida. Tente novamente.");
            }
        }
    }

    public static void exibirTabuleiro(char[][] tabuleiro) {
        System.out.println("  1 2 3");
        for (int i = 0; i < 3; i++) {
            System.out.print(i + 1 + " ");
            for (int j = 0; j < 3; j++) {
                System.out.print(tabuleiro[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static int lerEntrada() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    public static boolean posicaoValida(int linha, int coluna, char[][] tabuleiro) {
        return linha >= 0 && linha < 3 && coluna >= 0 && coluna < 3 && tabuleiro[linha][coluna] == ' ';
    }

    public static boolean verificarVitoria(char jogador, char[][] tabuleiro) {
        for (int i = 0; i < 3; i++) {
            if (tabuleiro[i][0] == jogador && tabuleiro[i][1] == jogador && tabuleiro[i][2] == jogador) {
                return true;
            }
            if (tabuleiro[0][i] == jogador && tabuleiro[1][i] == jogador && tabuleiro[2][i] == jogador) {
                return true;
            }
        }
        if (tabuleiro[0][0] == jogador && tabuleiro[1][1] == jogador && tabuleiro[2][2] == jogador) {
            return true;
        }
        if (tabuleiro[0][2] == jogador && tabuleiro[1][1] == jogador && tabuleiro[2][0] == jogador) {
            return true;
        }
        return false;
    }

    public static boolean verificarEmpate(char[][] tabuleiro) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tabuleiro[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }
}
