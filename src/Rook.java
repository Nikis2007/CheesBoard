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
