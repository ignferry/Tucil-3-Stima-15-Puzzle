package com.app.board;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.List;

public class Board {
    private int[][] contents;
    private int emptyLocationRow;
    private int emptyLocationCol;

    public Board() {
        contents = new int[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                this.contents[i][j] = i + 1;
            }
        }
        this.contents[3][3] = 0;
        this.emptyLocationRow = 3;
        this.emptyLocationCol = 3;
    }

    public Board(Board board) {
        // Set isi board sesuai dengan board pada parameter
        contents = new int[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                this.contents[i][j] = board.contents[i][j];
                if (this.contents[i][j] == 0) {
                    this.emptyLocationRow = i;
                    this.emptyLocationCol = j;
                }
            }
        }
    }

    public Board(Board board, Character move) {
        // Set isi board sesuai dengan board pada parameter
        contents = new int[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                this.contents[i][j] = board.contents[i][j];
                if (this.contents[i][j] == 0) {
                    this.emptyLocationRow = i;
                    this.emptyLocationCol = j;
                }
            }
        }

        // Ubah isi board berdasarkan karakter move
        switch (move) {
            case 'l' -> {
                this.contents[this.emptyLocationRow][this.emptyLocationCol] = this.contents[this.emptyLocationRow][this.emptyLocationCol - 1];
                this.contents[this.emptyLocationRow][this.emptyLocationCol - 1] = 0;
                this.emptyLocationCol--;
            }
            case 'r' -> {
                this.contents[this.emptyLocationRow][this.emptyLocationCol] = this.contents[this.emptyLocationRow][this.emptyLocationCol + 1];
                this.contents[this.emptyLocationRow][this.emptyLocationCol + 1] = 0;
                this.emptyLocationCol++;
            }
            case 'u' -> {
                this.contents[this.emptyLocationRow][this.emptyLocationCol] = this.contents[this.emptyLocationRow - 1][this.emptyLocationCol];
                this.contents[this.emptyLocationRow - 1][this.emptyLocationCol] = 0;
                this.emptyLocationRow--;
            }
            case 'd' -> {
                this.contents[this.emptyLocationRow][this.emptyLocationCol] = this.contents[this.emptyLocationRow + 1][this.emptyLocationCol];
                this.contents[this.emptyLocationRow + 1][this.emptyLocationCol] = 0;
                this.emptyLocationRow++;
            }
            default -> {
            }
        }
    }

    public int getContentsAt(int i, int j) {
        return this.contents[i][j];
    }

    public int getEmptyLocationRow() {
        return this.emptyLocationRow;
    }

    public int getEmptyLocationCol() {
        return this.emptyLocationCol;
    }

    public void setContentsRandomly() {
        // Set isi board secara acak
        Random rand = new Random();
        List<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            numbers.add(i);
        }

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int randomIdx = rand.nextInt(numbers.size());
                int randomNum = numbers.get(randomIdx);
                numbers.remove(randomIdx);
                this.contents[i][j] = randomNum;
                if (randomNum == 0) {
                    this.emptyLocationRow = i;
                    this.emptyLocationCol = j;
                }
            }
        }
    }

    public void setContentsFromFile(String path, boolean inTestFolder) throws IOException {
        // Set isi board berdasarkan file teks
        // Diasumsikan file selalu dalam format yang benar
        // Terdiri dari 16 angka, 4 angka per baris dipisahkan oleh spasi
        // 1 angka digantikan - untuk merepresentasikan tile kosong
        BufferedReader br;
        if (inTestFolder) {
            br = new BufferedReader(new FileReader("Test/" + path));
        }
        else {
            br = new BufferedReader(new FileReader(path));
        }

        try {
            String line = br.readLine();

            for (int i = 0; i < 4; i++) {
                String[] s = line.split(" ");
                for (int j = 0; j < 4; j++) {
                    if (!s[j].equals("-")) {
                        this.contents[i][j] = Integer.parseInt(s[j]);
                    }
                    else {
                        this.contents[i][j] = 0;
                        this.emptyLocationRow = i;
                        this.emptyLocationCol = j;
                    }

                }
                line = br.readLine();
            }
        } finally {
            br.close();
        }
    }

    public int countMisplacedTiles() {
        // Menghitung jumlah tile yang tidak berada di posisi yang seharusnya
        int count = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if ((contents[i][j] != 0 && contents[i][j] != i * 4 + j + 1 && i * 4 + j + 1 != 16) ||
                        (i * 4 + j + 1 == 16 && contents[i][j] != 0)) {
                    count++;
                }
            }
        }
        return count;
    }

    public void printBoard() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.print(this.contents[i][j] + " ");
            }
            System.out.println();
        }
    }
}
