package minesweeper;
import java.lang.reflect.Array;
import java.util.*;

class Point {
    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "("+ this.x +"," + this.y + ")";
    }

    @Override
    public boolean equals(Object obj) {
        Point punto = (Point) obj;
        return this.x == punto.getX() && this.y == punto.getY();
    }
}

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Scanner scanner1 = new Scanner(System.in);
        Field game = new Field();
        int n = 9;
        char[][] field = new char[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                field[i][j] = '.';
            }
        }

        System.out.print("How many mines do you want on the field? ");
        int mines = scanner.nextInt();
        game.setField(field, n);

        System.out.println(" |123456789|");
        System.out.println("-|---------|");
        for (int i = 0; i < n; i++) {
            System.out.print(i + 1 + "|");
            for (int j = 0; j < n; j++) {
                System.out.print(".");
            }
            System.out.print("|");
            System.out.println();
        }
        System.out.println("-|---------|");

        boolean flag = true;
        System.out.print("Set/unset mines marks or claim a cell as free: ");
        String res = scanner1.nextLine();
        String[] inputs = res.split(" ");
        String move = inputs[2];
        int x = Integer.parseInt(inputs[0]);
        int y = Integer.parseInt(inputs[1]);
        game.setMines(mines, y - 1, x - 1);
        game.minesAround();
        char[][] aux = new char[n][n];
        char[][] aux2 = game.getField();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (aux2[i][j] == 'X') {
                    aux[i][j] = '.';
                } else {
                    aux[i][j] = aux2[i][j];
                }
            }
        }

        char[][] final_game = new char[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                final_game[i][j] = '.';
            }
        }
        printGame(aux, final_game,y-1, x-1);

        while (flag) {
            System.out.print("Set/unset mines marks or claim a cell as free: ");
            res = scanner1.nextLine();
            inputs = res.split(" ");
            move = inputs[2];
            x = Integer.parseInt(inputs[0]);
            y = Integer.parseInt(inputs[1]);
            if(move.equals("mine")) {
                final_game = game.playerInput(y, x, final_game);
                printField(final_game);
            }else {
                if(aux2[y-1][x-1] == 'X') {
                    for (int i = 0; i < n; i++) {
                        for (int j = 0; j < n; j++) {
                            if(aux2[i][j] == 'X') {
                                final_game[i][j] = aux2[i][j];
                            }
                        }
                    }
                    printField(final_game);
                    System.out.println("You stepped on a mine and failed!");
                    System.exit(0);
                }else {
                    printGame(aux, final_game, y - 1, x - 1);
                }
            }
            if(game.checkGame()) {
                System.out.println("Congratulations! You found all mines!");
                flag = false;
            }
        }
    }

    public static void printField(char[][] field) {
        System.out.println(" |123456789|");
        System.out.println("-|---------|");
        for (int i = 0; i < 9; i++) {
            System.out.print(i + 1 + "|");
            for (int j = 0; j < 9; j++) {
                System.out.print(field[i][j]);
            }
            System.out.print("|");
            System.out.println();
        }
        System.out.println("-|---------|");
    }

    public static boolean esValido(Point p, int alto, int ancho) {
        return !(p.getX() < 0 || p.getY() < 0 || p.getX() > ancho || p.getY() > alto);
    }



    public static void printGame(char[][] field, char[][] aux, int x, int y) {
        if(field[x][y] != '.') {
            aux[x][y] = field[x][y];
        } else {
            Point punto = new Point(x, y);
            Deque<Point> cola = new ArrayDeque<>();
            List<Point> visitados = new ArrayList<>();
            cola.offer(punto);
            while (!cola.isEmpty()) {
                Point temp = cola.poll();
                if (esValido(temp, field.length - 1, field[0].length - 1)) {
                    if (field[temp.getX()][temp.getY()] == '.') {
                        aux[temp.getX()][temp.getY()] = '/';
                    }
                    visitados.add(temp);
                    int[] dr = new int[]{-1, -1, -1, 0 , 0, 1, 1, 1};
                    int[] dc = new int[]{-1, 0, 1, -1, 1, -1, 0, 1};
                    for (int i = 0; i < dr.length; i++) {
                        Point puntoAux = new Point(temp.getX() + dr[i], temp.getY() + dc[i]);
                        if (!visitados.contains(puntoAux) && esValido(puntoAux, field.length - 1, field[0].length - 1)) {
                            if (field[puntoAux.getX()][puntoAux.getY()] == '.') {
                                cola.offer(puntoAux);
                            }
                            aux[puntoAux.getX()][puntoAux.getY()] = field[puntoAux.getX()][puntoAux.getY()];
                        }
                    }
                }
            }
        }
        printField(aux);
    }
}
