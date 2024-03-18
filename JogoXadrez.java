/**
 * @author Gabriel
 */
import java.util.InputMismatchException;
import java.util.Scanner;

public class JogoXadrez {
    private static String vezDeJogar = "branca";
    public static void main(String[] args) {
        Tabuleiro tabuleiro = new Tabuleiro();
        tabuleiro.iniciarJogo();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.println("É a vez das peças " + vezDeJogar);
                System.out.println("Informe a posição da peça a ser movida (Ex: 1a): ");
                String origem = scanner.next();
                int colunaOrigem = origem.charAt(1) - 'a';
                int linhaOrigem = 8 - Integer.parseInt(origem.substring(0, 1));
                System.out.println("Informe a posição de destino (Ex: 2b): ");
                String destino = scanner.next();
                int colunaDestino = destino.charAt(1) - 'a';
                int linhaDestino = 8 - Integer.parseInt(destino.substring(0, 1));
                String corPecaOrigem = tabuleiro.getCorPeca(linhaOrigem, colunaOrigem);
                if (isValida(linhaOrigem, colunaOrigem, linhaDestino, colunaDestino, corPecaOrigem) &&
                        corPecaOrigem.equals(vezDeJogar)) {
                    if (tabuleiro.isMovimentoValido(linhaOrigem, colunaOrigem, linhaDestino, colunaDestino)) {
                        tabuleiro.moverPeca(linhaOrigem, colunaOrigem, linhaDestino, colunaDestino);
                        vezDeJogar = (vezDeJogar.equals("branca")) ? "preta" : "branca";
                    } else {
                        System.out.println("Jogada inválida para a peça em " + origem);
                    }
                } else {
                    System.out.println("Jogada inválida. Certifique-se de que as coordenadas estão corretas e é a vez das peças " + vezDeJogar);
                }
                tabuleiro.mostrarTabuleiro();
            } catch (InputMismatchException | NumberFormatException | StringIndexOutOfBoundsException e) {
                System.out.println("Entrada inválida. Por favor, insira uma posição válida.");
                scanner.nextLine();
            }
        }
    }
    private static boolean isValida(int linhaOrigem, int colunaOrigem, int linhaDestino, int colunaDestino, String corPecaOrigem) {
        return corPecaOrigem != null && isValida(linhaOrigem) && isValida(colunaOrigem) && isValida(linhaDestino) && isValida(colunaDestino);
    }
    private static boolean isValida(int valor) {
        return valor >= 0 && valor <= 7;
    }
}

class Tabuleiro {
    private Peca[][] pecas;
    public Tabuleiro() {
        pecas = new Peca[8][8];
    }
    public void iniciarJogo() {
        posicionarPecas();
        mostrarTabuleiro();
    }
    private void posicionarPecas() {
        pecas[0][0] = new Torre("branca");
        pecas[0][1] = new Cavalo("branca");
        pecas[0][2] = new Bispo("branca");
        pecas[0][3] = new Rainha("branca");
        pecas[0][4] = new Rei("branca");
        pecas[0][5] = new Bispo("branca");
        pecas[0][6] = new Cavalo("branca");
        pecas[0][7] = new Torre("branca");
        for (int i = 0; i < 8; i++) {
            pecas[1][i] = new Peao("branca");
        }
        pecas[7][0] = new Torre("preta");
        pecas[7][1] = new Cavalo("preta");
        pecas[7][2] = new Bispo("preta");
        pecas[7][3] = new Rainha("preta");
        pecas[7][4] = new Rei("preta");
        pecas[7][5] = new Bispo("preta");
        pecas[7][6] = new Cavalo("preta");
        pecas[7][7] = new Torre("preta");
        for (int i = 0; i < 8; i++) {
            pecas[6][i] = new Peao("preta");
        }
        for (int i = 2; i < 6; i++) {
            for (int j = 0; j < 8; j++) {
                pecas[i][j] = null;
            }
        }
    }

    public void moverPeca(int linhaOrigem, int colunaOrigem, int linhaDestino, int colunaDestino) {
        Peca peca = pecas[linhaOrigem][colunaOrigem];
        pecas[linhaOrigem][colunaOrigem] = null;
        if (peca instanceof Rei && Math.abs(colunaDestino - colunaOrigem) == 2) {
            int torreColunaOrigem = (colunaDestino > colunaOrigem) ? 7 : 0;
            int torreColunaDestino = (colunaDestino > colunaOrigem) ? colunaOrigem + 1 : colunaOrigem - 1;
            Peca torre = pecas[linhaOrigem][torreColunaOrigem];
            pecas[linhaOrigem][torreColunaOrigem] = null;
            pecas[linhaOrigem][torreColunaDestino] = torre;
        }
        pecas[linhaDestino][colunaDestino] = peca;
    }

    public void mostrarTabuleiro() {
        System.out.println("  a b c d e f g h");
        System.out.println(" +----------------");
        for (int i = 0; i < 8; i++) {
            System.out.print((8 - i));
            for (int j = 0; j < 8; j++) {
                Peca peca = pecas[i][j];
                if (peca != null) {
                    System.out.print(" " + peca.getSimbolo());
                } else {
                    System.out.print(" -");
                }
            }
            System.out.println();
        }
        System.out.println(" +----------------");
    }

    public boolean isMovimentoValido(int linhaOrigem, int colunaOrigem, int linhaDestino, int colunaDestino) {
        Peca peca = pecas[linhaOrigem][colunaOrigem];
        if (peca != null) {
            return peca.isMovimentoValido(linhaOrigem, colunaOrigem, linhaDestino, colunaDestino, this);
        }
        return false;
    }

    public String getCorPeca(int linha, int coluna) {
        if (pecas[linha][coluna] != null) {
            return pecas[linha][coluna].getCor();
        }
        return null;
    }

    public Peca getPeca(int linha, int coluna) {
        return pecas[linha][coluna];
    }
    
    public void copiarTabuleiro(Tabuleiro outroTabuleiro) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Peca peca = outroTabuleiro.getPeca(i, j);
                if (peca != null) {
                    this.pecas[i][j] = criarNovaInstanciaPeca(peca);
                } else {
                    this.pecas[i][j] = null;
                }
            }
        }
    }

    private Peca criarNovaInstanciaPeca(Peca peca) {
        if (peca instanceof Torre) {
            return new Torre(peca.getCor());
        } else if (peca instanceof Cavalo) {
            return new Cavalo(peca.getCor());
        } else if (peca instanceof Bispo) {
            return new Bispo(peca.getCor());
        } else if (peca instanceof Rainha) {
            return new Rainha(peca.getCor());
        } else if (peca instanceof Rei) {
            return new Rei(peca.getCor());
        } else if (peca instanceof Peao) {
            return new Peao(peca.getCor());
        } else {
            throw new IllegalArgumentException("Classe de peça não reconhecida: " + peca.getClass().getSimpleName());
        }
    }
}

abstract class Peca {
    private String cor;
    public Peca(String cor) {
        this.cor = cor;
    }
    public abstract String getSimbolo();
    public String getCor() {
        return cor;
    }
    public abstract boolean isMovimentoValido(int linhaOrigem, int colunaOrigem, int linhaDestino, int colunaDestino, Tabuleiro tabuleiro);
}

class Torre extends Peca {
    public Torre(String cor) {
        super(cor);
    }
    @Override
    public String getSimbolo() {
        return "T";
    }
    @Override
    public boolean isMovimentoValido(int linhaOrigem, int colunaOrigem, int linhaDestino, int colunaDestino, Tabuleiro tabuleiro) {
        return linhaOrigem == linhaDestino || colunaOrigem == colunaDestino;
    }
}

class Cavalo extends Peca {
    public Cavalo(String cor) {
        super(cor);
    }
    @Override
    public String getSimbolo() {
        return "C";
    }
    @Override
    public boolean isMovimentoValido(int linhaOrigem, int colunaOrigem, int linhaDestino, int colunaDestino, Tabuleiro tabuleiro) {
        int diffLinha = Math.abs(linhaDestino - linhaOrigem);
        int diffColuna = Math.abs(colunaDestino - colunaOrigem);
        return (diffLinha == 2 && diffColuna == 1) || (diffLinha == 1 && diffColuna == 2);
    }
}

class Bispo extends Peca {
    public Bispo(String cor) {
        super(cor);
    }
    @Override
    public String getSimbolo() {
        return "B";
    }
    @Override
    public boolean isMovimentoValido(int linhaOrigem, int colunaOrigem, int linhaDestino, int colunaDestino, Tabuleiro tabuleiro) {
        return Math.abs(linhaDestino - linhaOrigem) == Math.abs(colunaDestino - colunaOrigem);
    }
}

class Rainha extends Peca {
    public Rainha(String cor) {
        super(cor);
    }
    @Override
    public String getSimbolo() {
        return "Q";
    }
    @Override
    public boolean isMovimentoValido(int linhaOrigem, int colunaOrigem, int linhaDestino, int colunaDestino, Tabuleiro tabuleiro) {
        return linhaOrigem == linhaDestino || colunaOrigem == colunaDestino || Math.abs(linhaDestino - linhaOrigem) == Math.abs(colunaDestino - colunaOrigem);
    }
}

class Rei extends Peca {
    public Rei(String cor) {
        super(cor);
    }
    @Override
    public String getSimbolo() {
        return "K";
    }
    @Override
    public boolean isMovimentoValido(int linhaOrigem, int colunaOrigem, int linhaDestino, int colunaDestino, Tabuleiro tabuleiro) {
        int diffLinha = Math.abs(linhaDestino - linhaOrigem);
        int diffColuna = Math.abs(colunaDestino - colunaOrigem);
        if ((diffLinha <= 1 && diffColuna <= 1) && !colocaReiEmXeque(linhaOrigem, colunaOrigem, linhaDestino, colunaDestino, tabuleiro)) {
            return true;
        }
        return false;
    }
    private boolean colocaReiEmXeque(int linhaOrigem, int colunaOrigem, int linhaDestino, int colunaDestino, Tabuleiro tabuleiro) {
        String corRei = tabuleiro.getPeca(linhaDestino, colunaDestino).getCor();
        Tabuleiro tabuleiroSimulado = new Tabuleiro();
        tabuleiroSimulado.copiarTabuleiro(tabuleiro);
        tabuleiroSimulado.moverPeca(linhaOrigem, colunaOrigem, linhaDestino, colunaDestino);
        int linhaRei = -1;
        int colunaRei = -1;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Peca peca = tabuleiroSimulado.getPeca(i, j);
                if (peca instanceof Rei && peca.getCor().equals(corRei)) {
                    linhaRei = i;
                    colunaRei = j;
                    break;
                }
            }
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Peca peca = tabuleiroSimulado.getPeca(i, j);
                if (peca != null && !peca.getCor().equals(corRei) && peca.isMovimentoValido(i, j, linhaRei, colunaRei, tabuleiroSimulado)) {
                    return true;
                }
            }
        }
        return false;
    }
}

class Peao extends Peca {
    public Peao(String cor) {
        super(cor);
    }
    @Override
    public String getSimbolo() {
        return "P";
    }
    @Override
    public boolean isMovimentoValido(int linhaOrigem, int colunaOrigem, int linhaDestino, int colunaDestino, Tabuleiro tabuleiro) {
        int diffLinha = linhaDestino - linhaOrigem;
        int diffColuna = Math.abs(colunaDestino - colunaOrigem);
        if (getCor().equals("branca")) {
            if ((diffLinha == 1 || (diffLinha == 2 && linhaOrigem == 1)) && diffColuna == 0 && tabuleiro.getPeca(linhaDestino, colunaDestino) == null) {
                return true;
            } else if (diffLinha == 1 && diffColuna == 1 && tabuleiro.getPeca(linhaDestino, colunaDestino) != null &&
                    !tabuleiro.getCorPeca(linhaDestino, colunaDestino).equals(getCor())) {
                return true;
            }
        } else {
            if ((diffLinha == -1 || (diffLinha == -2 && linhaOrigem == 6)) && diffColuna == 0 && tabuleiro.getPeca(linhaDestino, colunaDestino) == null) {
                return true;
            } else if (diffLinha == -1 && diffColuna == 1 && tabuleiro.getPeca(linhaDestino, colunaDestino) != null &&
                    !tabuleiro.getCorPeca(linhaDestino, colunaDestino).equals(getCor())) {
                return true;
            }
        }
        return false;
    }
}
