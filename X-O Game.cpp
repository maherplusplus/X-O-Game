#include <iostream>
#include <vector>
#include <string>

using namespace std;

// ============ Player Class =========
class Player {
private:
    string name;
    char op;

public:
    Player() : name(""), op(' ') {}

    Player(string name, char op) : name(name), op(op) {}

    void setName(string name) {
        this->name = name;
    }

    void setOp(char op) {
        this->op = op;
    }

    string getName() const {
        return name;
    }

    char getOp() const {
        return op;
    }
};

// ============ Board Abstraction =========
class BoardInterface {
public:
    virtual bool isWin(const Player& p) const = 0;
};

// ============ Board Class =========
class Board : public BoardInterface {
private:
    vector<vector<char>> arr;

public:
    Board() {
        arr = {{'1', '2', '3'}, {'4', '5', '6'}, {'7', '8', '9'}};
    }

    int getRow(int idx) const {
        return (idx - 1) / 3;
    }

    int getCol(int idx) const {
        return (idx - 1) % 3;
    }

    bool isEmpty(int idx) const {
        if (idx < 1 || idx > 9) return false;

        int row = getRow(idx);
        int col = getCol(idx);
        if (arr[row][col] == 'x' || arr[row][col] == 'o')
            return false;
        return true;
    }

    bool isFull() const {
        for (int i = 1; i <= 9; i++) {
            if (isEmpty(i)) return false;
        }
        return true;
    }

    void draw() const {
        for (int i = 0; i < 3; i++) {
            cout << "--------------" << endl;
            for (int j = 0; j < 3; j++) {
                cout << "| " << arr[i][j] << " ";
            }
            cout << "|" << endl;
        }
        cout << "--------------" << endl;
    }

    bool replaceChar(int idx, char op) {
        if (isEmpty(idx)) {
            int row = getRow(idx);
            int col = getCol(idx);
            arr[row][col] = op;
            return true;
        }
        return false;
    }

    bool isWin(const Player& p) const override {
        char op = p.getOp();

        // Check rows
        for (int i = 0; i < 3; i++) {
            if (arr[i][0] == op && arr[i][1] == op && arr[i][2] == op)
                return true;
        }

        // Check columns
        for (int j = 0; j < 3; j++) {
            if (arr[0][j] == op && arr[1][j] == op && arr[2][j] == op)
                return true;
        }

        // Check diagonals
        if (arr[0][0] == op && arr[1][1] == op && arr[2][2] == op)
            return true;

        if (arr[0][2] == op && arr[1][1] == op && arr[2][0] == op)
            return true;

        return false;
    }
};

// ============ Game Class =========
class Game {
private:
    Player p1, p2;
    Board b;
    int count;

public:
    Game() : p1(), p2(), b(), count(0) {}

    void readPlayersData() {
        string name;
        char op;

        // First Player
        cout << "Welcome, Enter Your Name (First Player) : ";
        cin >> name;
        cout << "Enter an Operator (x , o) : ";
        cin >> op;

        p1.setName(name);
        p1.setOp(op);

        // Second Player
        cout << "Enter Your Name (Second Player) : ";
        cin >> name;
        p2.setName(name);

        if (p1.getOp() == 'x') p2.setOp('o');
        else p2.setOp('x');
    }

    void play() {
        readPlayersData();
        b.draw();

        while (!b.isFull()) {
            Player currentPlayer = (count % 2 == 0) ? p1 : p2;

            while (true) {
                int pos;
                cout << "Choose an Empty pos ( 1 , 9 ) : ";
                cin >> pos;
                if (b.replaceChar(pos, currentPlayer.getOp()))
                    break;
            }

            b.draw();
            if (b.isWin(currentPlayer)) {
                cout << "Winner is " << currentPlayer.getName() << endl;
                break;
            }
            count++;
        }

        if (b.isFull()) {
            cout << "Game is Draw . ." << endl;
        }
    }
};

// ============ Main =========
int main() {
    bool f = false;

    do {
        Game g;
        g.play();

        int ch;
        cout << "Play Again (1)yes (2)no : ";
        cin >> ch;
        f = (ch == 1);

    } while (f);

    return 0;
}
