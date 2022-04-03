package com.app.bnb;

import com.app.board.Board;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class Solver {
    public Board problem;
    public Node solution;
    public int initialCheckValue;
    public boolean solvable;
    public double executionTime;
    public int totalNodesCreated;

    public Solver() {
        this.problem = new Board();
        this.solution = new Node(this.problem);
        this.problem.setContentsRandomly();
        this.initialCheckValue = 0;
        this.solvable = false;
        this.executionTime = 0;
        this.totalNodesCreated = 0;
    }

    public Solver(Board board) {
        this.problem = new Board(board);
        this.solution = new Node(this.problem);
        this.initialCheckValue = 0;
        this.solvable = false;
        this.executionTime = 0;
        this.totalNodesCreated = 0;
    }

    public void solve() {
        // Mulai perhitungan waktu
        long startTime = System.nanoTime();

        // Cek apakah puzzle sudah berupa solusi
        if (problem.countMisplacedTiles() == 0) {
            solvable = true;
            totalNodesCreated++;
        }
        // Cek apakah puzzle bisa diselesaikan
        else if (isSolvable()) {
            solvable = true;
            // Buat priority queue dengan elemen awal kondisi awal board
            PriorityQueue<Node> pq = new PriorityQueue<>();
            pq.add(new Node(this.problem));
            totalNodesCreated++;

            // Iterasi sampai pq kosong
            while (!pq.isEmpty()) {
                // Ambil node di antrian terdepan
                Node currentNode = pq.poll();

                if (currentNode.isSolution()) {
                    // Hapus node dalam antrian yang costnya lebih besar dari solution baru
                    pq.removeIf(n -> n.getCost() > this.solution.getCost());

                    this.solution = currentNode;
                }
                else {
                    // Tambahkan node baru ke antrian
                    List<Character> possibleMoves = new ArrayList<>(currentNode.possibleMoves());
                    for (Character move : possibleMoves) {
                        Node newNode = new Node(currentNode, move);
                        if (newNode.getCost() < this.solution.getCost()) {
                            pq.add(new Node(currentNode, move));
                            totalNodesCreated++;
                        }
                    }
                }
            }

        }

        // Hentikan perhitungan waktu
        long stopTime = System.nanoTime();
        this.executionTime = ((double) (stopTime - startTime) / 1000000000);
    }

    public boolean isSolvable() {
        // Mengembalikan true jika puzzle bisa diselesaikan
        // Ditentukan berdasarkan penjumlahan jumlah kurang(i) + X
        int value = 0;

        for (int i = 0; i < 16; i++) {
            value += kurang(i);
        }

        if ((this.problem.getEmptyLocationRow() + this.problem.getEmptyLocationCol()) % 2 != 0) {
            value++;
        }

        // set initialCheckValue
        this.initialCheckValue = value;

        // Mengembalikan true jika hasil perhitungan genap
        return value % 2 == 0;

    }

    private int kurang(int I) {
        // Mengembalikan banyaknya ubin bernomor x sedemikian sehingga x < I dan
        // POSISI(x) > POSISI(I).
        // POSISI(I) = posisi ubin bernomor I pada susunan yang diperiksa

        int position = -1; // posisi i
        int res = 0;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (position == -1 && problem.getContentsAt(i, j) == I) {
                    // Set posisi i
                    position = i * 4 + j;
                } else if (position != -1 && problem.getContentsAt(i, j) < I && problem.getContentsAt(i, j) != 0) {
                    // Tambah jumlah ubin dengan posisi(X) > posisi(i)
                    res++;
                } else if (position != -1 && I == 0) {
                    res++;
                }
            }
        }
        return res;
    }

    public void printSolution() {
        System.out.println("Posisi awal:");
        this.problem.printBoard();
        System.out.println("Nilai fungsi pengecekan awal: " + this.initialCheckValue);
        System.out.println("Bisa diselesaikan? " + this.solvable);
        System.out.println("Solusi: ");
        this.solution.printNode();
        System.out.println("Jumlah simpul yang dibangkitkan: " + this.totalNodesCreated);
        System.out.println("Waktu eksekusi: " + this.executionTime);
    }
}
