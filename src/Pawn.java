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

