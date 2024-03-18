/**
 * @author Gabriel
 */
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class CampoMinado {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        System.out.println("Bem-vindo ao Campo Minado!");
        System.out.print("Digite o tamanho do campo: ");
        int tamanhoCampo = scanner.nextInt();

        int[][] campo = new int[tamanhoCampo][tamanhoCampo];

        for (int i = 0; i < tamanhoCampo; i++) {
            for (int j = 0; j < tamanhoCampo; j++) {
                campo[i][j] = random.nextInt(2);
            }
        }

        Set<String> coordenadasEscolhidas = new HashSet<>();

        boolean encontrouBomba = false;
        while (!encontrouBomba) {
            System.out.println("Campo gerado:");
            for (int i = 0; i < tamanhoCampo; i++) {
                for (int j = 0; j < tamanhoCampo; j++) {
                    System.out.print(campo[i][j] + " ");
                }
                System.out.println();
            }

            int linha, coluna;
            String coordenada;
            do {
                System.out.print("Digite a linha desejada: ");
                linha = scanner.nextInt();
                System.out.print("Digite a coluna desejada: ");
                coluna = scanner.nextInt();

                coordenada = linha + "," + coluna;
                if (coordenadasEscolhidas.contains(coordenada)) {
                    System.out.println("Você já escolheu essa coordenada. Escolha outra.");
                }
            } while (coordenadasEscolhidas.contains(coordenada));

            coordenadasEscolhidas.add(coordenada);

            if (campo[linha][coluna] == 1) {
                System.out.println("BOOM! Você encontrou uma mina");
                encontrouBomba = true;
            } else {
                System.out.println("Você está seguro! Tente novamente.");
            }
        }
        scanner.close();
    }
}
