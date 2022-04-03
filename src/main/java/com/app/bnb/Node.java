package com.app.bnb;

import com.app.board.Board;

import java.util.ArrayList;
import java.util.List;

public class Node implements Comparable<Node> {
    private Board board;
    private List<Character> pathTaken;
    private int cost;

    public Node(Board b) {
        this.board = new Board(b);
        this.pathTaken = new ArrayList<>();
        this.cost = 99;
    }

    public Node(Node previousNode, Character move) {
        this.board = new Board(previousNode.board, move);
        this.pathTaken = new ArrayList<>(previousNode.pathTaken);
        this.pathTaken.add(move);
        this.cost = this.board.countMisplacedTiles() + this.pathTaken.size();
    }

    public int getCost() {
        return this.cost;
    }

    public int compareTo(Node n) {
        // Perbandingan cost untuk priority queue
        return Integer.compare(this.cost, n.cost);
    }

    public List<Character> getPathTaken() {
        return new ArrayList<>(pathTaken);
    }

    public List<Character> possibleMoves() {
        // Mengembalikan list gerakan yang dapat dilakukan selanjutnya
        // Gerakan dapat dilakukan jika tidak membuat tile keluar batas dan tidak menyebabkan kembali ke posisi sebelumnya
        List<Character> l = new ArrayList<>();
        if (board.getEmptyLocationCol() != 0) {
            l.add('l');
        }
        if (board.getEmptyLocationRow() != 0) {
            l.add('u');
        }
        if (board.getEmptyLocationCol() != 3) {
            l.add('r');
        }
        if (board.getEmptyLocationRow() != 3) {
            l.add('d');
        }

        if (!pathTaken.isEmpty()) {
            Character c = pathTaken.get(pathTaken.size() - 1);
            if (c == 'l') {
                c = 'r';
            }
            else if (c == 'r') {
                c = 'l';
            }
            else if (c == 'u') {
                c = 'd';
            }
            else if (c == 'd') {
                c = 'u';
            }
            l.remove(c);
        }

        return l;
    }

    public boolean isSolution() {
        return board.countMisplacedTiles() == 0;
    }

    public void printNode() {
        System.out.println("Matriks: ");
        this.board.printBoard();
        this.pathTaken.forEach(n -> System.out.print(n + " "));
        System.out.println();
    }
}
