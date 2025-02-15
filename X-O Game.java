// ============ Player Class =========
package xo_game;


public class Player {
    private String name;
    private char op;

    public Player() {
    }

    public Player(String name, char op) {
        this.name = name;
        this.op = op;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOp(char op) {
        this.op = op;
    }

    public String getName() {
        return name;
    }

    public char getOp() {
        return op;
    }
    
}

// ============ BoardInterface =========

package xo_game;


public interface BoardInterface {
    public boolean isWin(Player p);
}



// ============ Board Class =========

package xo_game;


public class Board implements BoardInterface {
    private char [][]arr;
    
    public Board(){
        this.arr = new char [][] {{'1','2','3'},{'4','5','6'},{'7','8','9'}};
    }
    
    public int getRow(int idx){
        return (idx - 1) / 3;
    }
    
    public int getCol(int idx){
        return (idx - 1) % 3;
    }
    
    public boolean isEmpty(int idx){ // cell
        if (idx < 1 || idx > 9) return false;
        
        int row = getRow(idx);
        int col = getCol(idx);
        if (arr[row][col] == 'x' || arr[row][col] == 'o') 
            return false;
        return true;
    }
    
    public boolean isFull(){
        for (int i=1;i<=9;i++){ // 
            if(isEmpty(i)) return false;
        }
        return true;
    }
    
    public void drow(){
        for(int i=0;i<3;i++){
            System.out.println("--------------");
            for (int j=0;j<3;j++){
                System.out.print("| " + arr[i][j] + " ");
            }
            System.out.println("|");
        }
        
       System.out.println("--------------");
    }
    
    public boolean replaceChar(int idx, char op){
        if(isEmpty(idx)){
            int row = getRow(idx);
            int col = getCol(idx);
            arr[row][col] = op;
            return true;
        }
        return false;
    }

    @Override
    public boolean isWin(Player p) {
        // 3 row
        if (arr[0][0] == arr[0][1] && arr[0][1] == arr[0][2] && arr[0][0] == arr[0][2])
            return true;
        
        if (arr[1][0] == arr[1][1] && arr[1][1] == arr[1][2] && arr[1][0] == arr[1][2])
            return true;
        
        if (arr[2][0] == arr[2][1] && arr[2][1] == arr[2][2] && arr[2][0] == arr[2][2])
            return true;
        
        // 3 col
        if (arr[0][0] == arr[1][0] && arr[1][0] == arr[2][0] && arr[0][0] == arr[2][0])
            return true;
        
        if (arr[0][1] == arr[1][1] && arr[1][1] == arr[2][1] && arr[0][1] == arr[2][1])
            return true;
        
        if (arr[0][2] == arr[1][2] && arr[1][2] == arr[2][2] && arr[0][2] == arr[2][2])
            return true;
        
        // 2 diag
        if (arr[0][0] == arr[1][1] && arr[1][1] == arr[2][2] && arr[0][0] == arr[2][2])
            return true;
        
        if (arr[0][2] == arr[1][1] && arr[1][1] == arr[2][0] && arr[0][2] == arr[2][0])
            return true;
        
        return false;
    }
    
    
    
}

// ============ Game Class =========

package xo_game;

import java.util.Scanner;

public class Game {
    private Player p1,p2;
    private Board b;
    int count;

    public Game() {
        this.p1 = new Player();
        this.p2 = new Player();
        this.b = new Board();
        this.count = 0; 
    }
    
    public void readPlayersData(){
        Scanner cin = new Scanner (System.in);
        
        // First Player
        System.out.print("Welcome, Enter Your Name (First Player) : ");
        String name = cin.next();
        System.out.print("Enter an Operator (x , o) : ");
        char op = cin.next().charAt(0);
        
        p1.setName(name);
        p1.setOp(op);
        
        // Second Player
        System.out.print("Enter Your Name (Second Player) : ");
        name = cin.next();
        p2.setName(name);
        
        if (p1.getOp() == 'x') p2.setOp('o');
        else p2.setOp('x');
    }
    
    public void play(){
        Scanner cin = new Scanner (System.in);
        readPlayersData();
        b.drow();
        
        while (!b.isFull()){
            Player currentPlayer = p1;
            if (count % 2 == 1) currentPlayer = p2;
            
            while (true){
                System.out.print("Choose an Empty pos ( 1 , 9 ) : ");
                int pos = cin.nextInt();
                if (b.replaceChar(pos, currentPlayer.getOp()))
                       break;
            }
            
            b.drow();
            if (b.isWin(currentPlayer)){
                System.out.println("Winner is " + currentPlayer.getName());
                break;
            }
            count++;
        }
        
        if (b.isFull()){
            System.out.println("Game is Drow . .");
        }
    }
    
    
}

// ============ Main =========


package xo_game;

import java.util.Scanner;

public class XO_Game {

    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        boolean f = false;
        
        do {
            Game g = new Game();
            g.play();
            
            System.out.print("Play Agaia (1)yes (2)no : ");
            int ch = cin.nextInt();
            if (ch == 1) f = true;
            else f = false;
            
        }while (f);
        
        
    }
    
}

