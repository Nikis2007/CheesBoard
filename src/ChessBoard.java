import java.util.Scanner;
public class ChessBoard {
    public ChessPiece[][] board; // creating a field for game
    String nowPlayer;
    String color;

    public ChessBoard(String nowPlayer) {
        board = new ChessPiece[8][8];
        this.nowPlayer = nowPlayer;
    }

    public String nowPlayerColor() {
        return this.nowPlayer;
    }
    public boolean isPathClear(int startLine, int startColumn, int endLine, int endColumn) {
        int deltaLine = Integer.compare(endLine, startLine);
        int deltaColumn = Integer.compare(endColumn, startColumn);

        int currentLine = startLine + deltaLine;
        int currentColumn = startColumn + deltaColumn;

        while (currentLine != endLine || currentColumn != endColumn) {
            // Если на пути встречается фигура, возвращаем false
            if (board[currentLine][currentColumn] != null) {
                return false;
            }
            currentLine += deltaLine;
            currentColumn += deltaColumn;
        }
        return true;
    }
    public boolean isOnBoard(int line, int column) {
        return line >= 0 && line < 8 && column >= 0 && column < 8;
    }

    public boolean isUnderAttack(ChessBoard board, int line, int column) {
        int[] directions = {-1, 0, 1};
        for (int deltaLine : directions) {
            for (int deltaColumn : directions) {
                if (deltaLine == 0 && deltaColumn == 0) continue;

                int currentLine = line + deltaLine;
                int currentColumn = column + deltaColumn;

                while (isOnBoard(currentLine, currentColumn)) {
                    ChessPiece piece = board.getPiece(currentLine, currentColumn);

                    if (piece != null) {
                        if (piece.getColor() != this.color) {
                            if ((piece instanceof Rook && (deltaLine == 0 || deltaColumn == 0)) ||
                                    (piece instanceof Bishop && deltaLine != 0 && deltaColumn != 0) ||
                                    (piece instanceof Queen)) {
                                return true;
                            }
                        }
                        break;
                    }

                    currentLine += deltaLine;
                    currentColumn += deltaColumn;
                }
            }
        }
        int pawnDirection = this.color.equals("White") ? -1 : 1;
        if (isOnBoard(line + pawnDirection, column - 1) &&
                board.getPiece(line + pawnDirection, column - 1) instanceof Pawn &&
                board.getPiece(line + pawnDirection, column - 1).getColor() != this.color) {
            return true;
        }
        if (isOnBoard(line + pawnDirection, column + 1) &&
                board.getPiece(line + pawnDirection, column + 1) instanceof Pawn &&
                board.getPiece(line + pawnDirection, column + 1).getColor() != this.color) {
            return true;
        }
        int[][] horseMoves = {
                {2, 1}, {2, -1}, {-2, 1}, {-2, -1},
                {1, 2}, {1, -2}, {-1, 2}, {-1, -2}
        };
        for (int[] move : horseMoves) {
            int horseLine = line + move[0];
            int horseColumn = column + move[1];
            if (isOnBoard(horseLine, horseColumn) &&
                    board.getPiece(horseLine, horseColumn) instanceof Horse &&
                    board.getPiece(horseLine, horseColumn).getColor() != this.color) {
                return true;
            }
        }
        for (int dLine : directions) {
            for (int dColumn : directions) {
                if (dLine == 0 && dColumn == 0) continue;
                int kingLine = line + dLine;
                int kingColumn = column + dColumn;
                if (isOnBoard(kingLine, kingColumn) &&
                        board.getPiece(kingLine, kingColumn) instanceof King &&
                        board.getPiece(kingLine, kingColumn).getColor() != this.color) {
                    return true;
                }
            }
        }
        return false;
    }


    public boolean moveToPosition(int startLine, int startColumn, int endLine, int endColumn) {
        if (checkPos(startLine) && checkPos(startColumn) && board[startLine][startColumn] != null) {
            if (!nowPlayer.equals(board[startLine][startColumn].getColor())) return false;

            if (board[startLine][startColumn].canMoveToPosition(this, startLine, startColumn, endLine, endColumn)) {
                ChessPiece targetPiece = board[endLine][endColumn];

                // Проверка для коня, который может прыгать через фигуры
                boolean isHorse = board[startLine][startColumn] instanceof Horse;
                if (!isHorse && !isPathClear(startLine, startColumn, endLine, endColumn)) {
                    return false; // Путь не свободен
                }

                // Если конечная позиция занята фигурой другого цвета, съедаем её
                if (targetPiece != null && !targetPiece.getColor().equals(nowPlayer)) {
                    board[endLine][endColumn] = board[startLine][startColumn];
                    board[startLine][startColumn] = null;
                    this.nowPlayer = this.nowPlayerColor().equals("White") ? "Black" : "White";
                    return true;
                }
                // Если конечная позиция пуста
                else if (targetPiece == null) {
                    board[endLine][endColumn] = board[startLine][startColumn];
                    board[startLine][startColumn] = null;
                    this.nowPlayer = this.nowPlayerColor().equals("White") ? "Black" : "White";
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }


    public void printBoard() {  //print board in console
        System.out.println("Turn " + nowPlayer);
        System.out.println();
        System.out.println("Player 2(Black)");
        System.out.println();
        System.out.println("\t0\t1\t2\t3\t4\t5\t6\t7");

        for (int i = 7; i > -1; i--) {
            System.out.print(i + "\t");
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == null) {
                    System.out.print(".." + "\t");
                } else {
                    System.out.print(board[i][j].getSymbol() + board[i][j].getColor().substring(0, 1).toLowerCase() + "\t");
                }
            }
            System.out.println();
            System.out.println();
        }
        System.out.println("Player 1(White)");
    }

    public ChessPiece getPiece(int line, int column) {
        // Проверяем, находится ли позиция в пределах доски
        if (line >= 0 && line < 8 && column >= 0 && column < 8) {
            return board[line][column];
        }
        return null;
    }

    public boolean checkPos(int pos) {
        return pos >= 0 && pos <= 7;
    }

    public boolean castling0() {
        if (nowPlayer.equals("White")) {
            if (board[0][0] == null || board[0][4] == null) return false;
            if (board[0][0].getSymbol().equals("R") && board[0][4].getSymbol().equals("K") &&
                    board[0][1] == null && board[0][2] == null && board[0][3] == null) {
                if (board[0][0].getColor().equals("White") && board[0][4].getColor().equals("White") &&
                        board[0][0].check && board[0][4].check &&
                        new King("White").isUnderAttack(this, 0, 2)) {
                    board[0][4] = null;
                    board[0][2] = new King("White");
                    board[0][2].check = false;
                    board[0][0] = null;
                    board[0][3] = new Rook("White");
                    board[0][3].check = false;
                    nowPlayer = "Black";
                    return true;
                } else return false;

            } else return false;

        }
        return false;
    }
    public boolean castling7() {
        if (nowPlayer.equals("Black")) {
            if (board[7][0] == null || board[7][4] == null) return false;
            if (board[7][0].getSymbol().equals("R") && board[7][4].getSymbol().equals("K") &&
                    board[7][1] == null && board[7][2] == null && board[7][3] == null) {
                if (board[7][0].getColor().equals("White") && board[7][4].getColor().equals("White") &&
                        board[7][0].check && board[7][4].check &&
                        new King("Black").isUnderAttack(this, 7, 2)) {
                    board[7][4] = null;
                    board[7][2] = new King("White");
                    board[7][2].check = false;
                    board[7][0] = null;
                    board[7][3] = new Rook("White");
                    board[7][3].check = false;
                    nowPlayer = "White";
                    return true;
                } else return false;

            } else return false;
        }
        return false;
    }
}
abstract class ChessPiece {
    String color;
    boolean check;

    public ChessPiece(String color) {
        this.color = color;
        this.check = false;
    }

    public String getColor() {
        return color;
    }

    public boolean moveToPosition(int startLine, int startColumn, int endLine, int endColumn){
        return false;
    }


    public abstract boolean canMoveToPosition(ChessBoard chessBoard, int line, int column, int toLine, int toColumn);


    public abstract String getSymbol();
}

class Horse extends ChessPiece {
    private final String color;
    public Horse(String color) {
        super(color);
        this.color = color;

    }
    public boolean isOnBoard(int line, int column) {
        return line >= 0 && line < 8 && column >= 0 && column < 8;
    }

    @Override
    public String getColor() {
        return color;
    }

    @Override
    public boolean canMoveToPosition(ChessBoard chessBoard, int line, int column, int toLine, int toColumn) {
        if (!isOnBoard(toLine, toColumn)){
            return false;
        } else if ((Math.abs(line - toLine) == 2 && Math.abs(column - toColumn) == 1) || ((Math.abs(line - toLine) == 1) && (Math.abs(column - toColumn) == 2))) {
            ChessPiece targetPiece = chessBoard.board[toLine][toColumn];
            return targetPiece == null || !targetPiece.getColor().equals(this.getColor());
        } else {
            return false;

        }

    }
    public boolean isUnderAttack(ChessBoard board, int line, int column) {
        int[] directions = {-1, 0, 1};
        for (int deltaLine : directions) {
            for (int deltaColumn : directions) {
                if (deltaLine == 0 && deltaColumn == 0) continue;

                int currentLine = line + deltaLine;
                int currentColumn = column + deltaColumn;

                while (isOnBoard(currentLine, currentColumn)) {
                    ChessPiece piece = board.getPiece(currentLine, currentColumn);

                    if (piece != null) {
                        if (piece.getColor() != this.color) {
                            if ((piece instanceof Rook && (deltaLine == 0 || deltaColumn == 0)) ||
                                    (piece instanceof Bishop && deltaLine != 0 && deltaColumn != 0) ||
                                    (piece instanceof Queen)) {
                                return true;
                            }
                        }
                        break;
                    }

                    currentLine += deltaLine;
                    currentColumn += deltaColumn;
                }
            }
        }
        int pawnDirection = this.color.equals("White") ? -1 : 1;
        if (isOnBoard(line + pawnDirection, column - 1) &&
                board.getPiece(line + pawnDirection, column - 1) instanceof Pawn &&
                board.getPiece(line + pawnDirection, column - 1).getColor() != this.color) {
            return true;
        }
        if (isOnBoard(line + pawnDirection, column + 1) &&
                board.getPiece(line + pawnDirection, column + 1) instanceof Pawn &&
                board.getPiece(line + pawnDirection, column + 1).getColor() != this.color) {
            return true;
        }
        int[][] horseMoves = {
                {2, 1}, {2, -1}, {-2, 1}, {-2, -1},
                {1, 2}, {1, -2}, {-1, 2}, {-1, -2}
        };
        for (int[] move : horseMoves) {
            int horseLine = line + move[0];
            int horseColumn = column + move[1];
            if (isOnBoard(horseLine, horseColumn) &&
                    board.getPiece(horseLine, horseColumn) instanceof Horse &&
                    board.getPiece(horseLine, horseColumn).getColor() != this.color) {
                return true;
            }
        }
        for (int dLine : directions) {
            for (int dColumn : directions) {
                if (dLine == 0 && dColumn == 0) continue;
                int kingLine = line + dLine;
                int kingColumn = column + dColumn;
                if (isOnBoard(kingLine, kingColumn) &&
                        board.getPiece(kingLine, kingColumn) instanceof King &&
                        board.getPiece(kingLine, kingColumn).getColor() != this.color) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getSymbol() {
        return "H";
    }
}
class Pawn extends ChessPiece {
    private final String color;

    public Pawn(String color) {
        super(color);
        this.color = color;
    }

    private boolean isOnBoard(int line, int column) {
        return line >= 0 && line < 8 && column >= 0 && column < 8;
    }

    @Override
    public String getColor() {
        return color;
    }

    @Override
    public boolean canMoveToPosition(ChessBoard chessBoard, int line, int column, int toLine, int toColumn) {
        if (!isOnBoard(toLine, toColumn)) {
            return false;
        } else if (line == toLine && column == toColumn) {
            return false;
        } else if (color.equals("White") && line == 1 && Math.abs(line - toLine) == 2 && Math.abs(column - toColumn) == 0 ||
                color.equals("Black") && line == 6 && Math.abs(line - toLine) == 2 && Math.abs(column - toColumn) == 0 ) {
            ChessPiece targetPiece = chessBoard.board[toLine][toColumn];
            return targetPiece == null || targetPiece.getColor().equals(this.getColor());
        } else if (Math.abs(line - toLine) == 1 && Math.abs(column - toColumn) == 0) {
            ChessPiece targetPiece = chessBoard.board[toLine][toColumn];
            return targetPiece == null || !targetPiece.getColor().equals(this.getColor());
        } else return false;
    }

    @Override
    public String getSymbol() {
        return "P";
    }
}
class Bishop extends ChessPiece {
    private final String color;

    public Bishop(String color){
        super(color);
        this.color = color;

    }

    private boolean isOnBoard(int line, int column) {
        return line >= 0 && line < 8 && column >= 0 && column < 8;
    }

    @Override
    public String getColor() {
        return color;
    }

    @Override
    public String getSymbol() {
        return "B";
    }

    @Override
    public boolean canMoveToPosition(ChessBoard chessBoard, int line, int column, int toLine, int toColumn) {
        if (!isOnBoard(toLine, toColumn)) {
            return false;
        } else if (line == toLine && column == toColumn) {
            return false;
        } else if (Math.abs(toLine - line) == Math.abs(toColumn - column)){
            ChessPiece targetPiece = chessBoard.board[toLine][toColumn];
            return targetPiece == null || !targetPiece.getColor().equals(this.getColor());
        } else return false;
    }
}
class Rook extends ChessPiece {
    private final String color;
    private boolean check;

    public Rook(String color) {
        super(color);
        this.color = color;
    }

    private boolean isOnBoard(int line, int column) {
        return line >= 0 && line < 8 && column >= 0 && column < 8;
    }
    public boolean moveToPosition(int startLine, int startColumn, int endLine, int endColumn) {
        if ((startLine != endLine) || (startColumn != endColumn)) {
            check = true;
            return true;
        } else {
            check = false;
            return false;
        }
    }



    @Override
    public String getColor() {
        return color;
    }

    @Override
    public String getSymbol() {
        return "R";
    }

    @Override
    public boolean canMoveToPosition(ChessBoard chessBoard, int line, int column, int toLine, int toColumn) {
        if (!isOnBoard(toLine, toColumn)) {
            return false;
        } else if (line == toLine && column == toColumn) {
            return false;
        } else if (Math.abs(line - toLine) == 0 || Math.abs(column - toColumn) == 0) {
            ChessPiece targetPiece = chessBoard.board[toLine][toColumn];
            return targetPiece == null || !targetPiece.getColor().equals(this.getColor());
        } else return false;
    }

}
class Queen extends ChessPiece {
    private final String color;

    public Queen(String color) {
        super(color);
        this.color = color;
    }
    private boolean isOnBoard(int line, int column) {
        return line >= 0 && line < 8 && column >= 0 && column < 8;
    }

    @Override
    public String getColor() {
        return color;
    }

    @Override
    public String getSymbol() {
        return "Q";
    }

    @Override
    public boolean canMoveToPosition(ChessBoard chessBoard, int line, int column, int toLine, int toColumn) {
        if (!isOnBoard(toLine, toColumn)) {
            return false;
        } else if (line == toLine && column == toColumn) {
            return false;
        } else if (Math.abs(toLine - line) == Math.abs(toColumn - column) || (Math.abs(line - toLine) == 0 || Math.abs(column - toColumn) == 0)) {
            ChessPiece targetPiece = chessBoard.board[toLine][toColumn];
            return targetPiece == null || !targetPiece.getColor().equals(this.getColor());
        } else return false;
    }
}
class King extends ChessPiece {
    private final String color;

    public King(String color) {
        super(color);
        this.color = color;
    }

    private boolean isOnBoard(int line, int column) {
        return line >= 0 && line < 8 && column >= 0 && column < 8;
    }




    @Override
    public String getColor() {
        return color;
    }

    @Override
    public String getSymbol() {
        return "K";
    }

    public boolean isUnderAttack(ChessBoard board, int line, int column) {
        int[] directions = {-1, 0, 1};
        for (int deltaLine : directions) {
            for (int deltaColumn : directions) {
                if (deltaLine == 0 && deltaColumn == 0) continue;

                int currentLine = line + deltaLine;
                int currentColumn = column + deltaColumn;

                while (isOnBoard(currentLine, currentColumn)) {
                    ChessPiece piece = board.getPiece(currentLine, currentColumn);

                    if (piece != null) {
                        if (piece.getColor() != this.color) {
                            if ((piece instanceof Rook && (deltaLine == 0 || deltaColumn == 0)) ||
                                    (piece instanceof Bishop && deltaLine != 0 && deltaColumn != 0) ||
                                    (piece instanceof Queen)) {
                                return true;
                            }
                        }
                        break;
                    }

                    currentLine += deltaLine;
                    currentColumn += deltaColumn;
                }
            }
        }
        int pawnDirection = this.color.equals("White") ? -1 : 1;
        if (isOnBoard(line + pawnDirection, column - 1) &&
                board.getPiece(line + pawnDirection, column - 1) instanceof Pawn &&
                board.getPiece(line + pawnDirection, column - 1).getColor() != this.color) {
            return true;
        }
        if (isOnBoard(line + pawnDirection, column + 1) &&
                board.getPiece(line + pawnDirection, column + 1) instanceof Pawn &&
                board.getPiece(line + pawnDirection, column + 1).getColor() != this.color) {
            return true;
        }
        int[][] horseMoves = {
                {2, 1}, {2, -1}, {-2, 1}, {-2, -1},
                {1, 2}, {1, -2}, {-1, 2}, {-1, -2}
        };
        for (int[] move : horseMoves) {
            int horseLine = line + move[0];
            int horseColumn = column + move[1];
            if (isOnBoard(horseLine, horseColumn) &&
                    board.getPiece(horseLine, horseColumn) instanceof Horse &&
                    board.getPiece(horseLine, horseColumn).getColor() != this.color) {
                return true;
            }
        }
        for (int dLine : directions) {
            for (int dColumn : directions) {
                if (dLine == 0 && dColumn == 0) continue;
                int kingLine = line + dLine;
                int kingColumn = column + dColumn;
                if (isOnBoard(kingLine, kingColumn) &&
                        board.getPiece(kingLine, kingColumn) instanceof King &&
                        board.getPiece(kingLine, kingColumn).getColor() != this.color) {
                    return true;
                }
            }
        }
        return false;
    }


    @Override
    public boolean canMoveToPosition(ChessBoard chessBoard, int line, int column, int toLine, int toColumn) {
        if (!isOnBoard(toLine, toColumn)) {
            return false;
        } else if (line == toLine && column == toColumn) {
            return false;
        } else if (Math.abs(column - toColumn) == 1 && Math.abs(line - toLine) == 1 ||
                Math.abs(column - toColumn) == 0 && Math.abs(line - toLine) == 1 ||
                Math.abs(column - toColumn) == 1 && Math.abs(line - toLine) == 0) {
            ChessPiece targetPiece = chessBoard.board[toLine][toColumn];
            return targetPiece == null || !targetPiece.getColor().equals(this.getColor());
        } else if (!isUnderAttack(chessBoard, toLine, toColumn)) {
            return true; // Если позиция не под атакой, ход допустим
        } return false;
    }
}

class Mail {

        public static ChessBoard buildBoard() {
            ChessBoard board = new ChessBoard("White");

            board.board[0][0] = new Rook("White");
            board.board[0][1] = new Horse("White");
            board.board[0][2] = new Bishop("White");
            board.board[0][3] = new Queen("White");
            board.board[0][4] = new King("White");
            board.board[0][5] = new Bishop("White");
            board.board[0][6] = new Horse("White");
            board.board[0][7] = new Rook("White");
            board.board[1][0] = new Pawn("White");
            board.board[1][1] = new Pawn("White");
            board.board[1][2] = new Pawn("White");
            board.board[1][3] = new Pawn("White");
            board.board[1][4] = new Pawn("White");
            board.board[1][5] = new Pawn("White");
            board.board[1][6] = new Pawn("White");
            board.board[1][7] = new Pawn("White");

            board.board[7][0] = new Rook("Black");
            board.board[7][1] = new Horse("Black");
            board.board[7][2] = new Bishop("Black");
            board.board[7][3] = new Queen("Black");
            board.board[7][4] = new King("Black");
            board.board[7][5] = new Bishop("Black");
            board.board[7][6] = new Horse("Black");
            board.board[7][7] = new Rook("Black");
            board.board[6][0] = new Pawn("Black");
            board.board[6][1] = new Pawn("Black");
            board.board[6][2] = new Pawn("Black");
            board.board[6][3] = new Pawn("Black");
            board.board[6][4] = new Pawn("Black");
            board.board[6][5] = new Pawn("Black");
            board.board[6][6] = new Pawn("Black");
            board.board[6][7] = new Pawn("Black");
            return board;
        }

        public static void main(String[] args) {

            ChessBoard board = buildBoard();
            Scanner scanner = new Scanner(System.in);
            System.out.println("""
                    Чтобы проверить игру надо вводить команды:
                    'exit' - для выхода
                    'replay' - для перезапуска игры
                    'castling0' или 'castling7' - для рокировки по соответствующей линии
                    'move 1 1 2 3' - для передвижения фигуры с позиции 1 1 на 2 3(поле это двумерный массив от 0 до 7)
                    Проверьте могут ли фигуры ходить друг сквозь друга, корректно ли съедают друг друга, можно ли поставить шах и сделать рокировку?""");
            System.out.println();
            board.printBoard();
            while (true) {
                String s = scanner.nextLine();
                if (s.equals("exit")) break;
                else if (s.equals("replay")) {
                    System.out.println("Заново");
                    board = buildBoard();
                    board.printBoard();
                } else {
                    if (s.equals("castling0")) {
                        if (board.castling0()) {
                            System.out.println("Рокировка удалась");
                            board.printBoard();
                        } else {
                            System.out.println("Рокировка не удалась");
                        }
                    } else if (s.equals("castling7")) {
                        if (board.castling7()) {
                            System.out.println("Рокировка удалась");
                            board.printBoard();
                        } else {
                            System.out.println("Рокировка не удалась");
                        }
                    } else if (s.contains("move")) {
                        String[] a = s.split(" ");
                        try {
                            int line = Integer.parseInt(a[1]);
                            int column = Integer.parseInt(a[2]);
                            int toLine = Integer.parseInt(a[3]);
                            int toColumn = Integer.parseInt(a[4]);
                            if (board.moveToPosition(line, column, toLine, toColumn)) {
                                System.out.println("Успешно передвинулись");
                                board.printBoard();
                            } else System.out.println("Передвижение не удалось");
                        } catch (Exception e) {
                            System.out.println("Вы что-то ввели не так, попробуйте ещё раз");
                        }

                    }
                }
            }
        }

    }




