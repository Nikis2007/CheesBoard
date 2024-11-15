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