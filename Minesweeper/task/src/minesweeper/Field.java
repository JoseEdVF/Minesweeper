package minesweeper;
import java.util.Random;

public class Field {

    Random random = new Random();
    private char[][] field;
    private int n;
    private int mines;


    public void setField(char[][] field, int n) {
        this.field = field;
        this.n = n;
    }

    public void setMines(int a, int x, int y) {
        mines = a;
        for (int i = 0; i < a; i++) {
            int b = random.nextInt(n);
            int c = random.nextInt(n);
            if (field[b][c] == 'X'){
                a = a+1;
            }else if(b == x && c == y) {
                a = a+1;
            }else{
                field[b][c] = 'X';
            }
        }
    }

    public char[][] getField() {
        return field;
    }

    public void printField() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(field[i][j]);
            }
            System.out.println();
        }
    }


    public char[][] playerInput (int a, int b, char[][] aux) {
        if (aux[a-1][b-1] == '.' && field[a-1][b-1] == 'X') {
            mines = mines - 1;
            aux[a-1][b-1] = '*';
            return aux;
        }
        if (aux[a-1][b-1] == '*' && field[a-1][b-1] == 'X') {
            mines =mines + 1;
            aux[a-1][b-1] = '.';
            return aux;
        }
        if (aux[a-1][b-1] == '.') {
            mines =mines + 1;
            aux[a-1][b-1] = '*';
            return aux;
        }
        if (aux[a-1][b-1] == '*') {
            mines = mines - 1;
            aux[a-1][b-1] = '.';
            return aux;
        }
        return aux;
    }

    public boolean checkGame () {
        return mines == 0;
    }

    public boolean esValido(int x, int y) {
        return !(x < 0 || y < 0 || x > n-1 || y > n-1);
    }

    public void minesAround() {
        int cont = 0;
        int[] dr = new int[]{-1, -1, -1, 0 , 0, 1, 1, 1};
        int[] dc = new int[]{-1, 0, 1, -1, 1, -1, 0, 1};
        for(int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if(field[i][j] != 'X') {
                    for (int k = 0; k < dr.length; k++) {
                        int x = i + dr[k];
                        int y = j + dc[k];
                        if (esValido(x, y)) {
                            if (field[x][y] == 'X') {
                                cont++;
                            }
                        }
                    }
                    if (cont > 0) {
                        field[i][j] = (char) (cont + '0');
                        cont = 0;
                    }
                }
            }
        }
    }
}
