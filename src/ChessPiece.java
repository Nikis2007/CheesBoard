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
