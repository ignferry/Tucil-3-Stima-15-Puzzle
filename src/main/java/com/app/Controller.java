package com.app;

import com.app.bnb.Solver;
import com.app.board.Board;
import com.app.elements.InfoLabel;
import com.app.elements.NumberTile;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;


public class Controller {

    private Solver solver;
    private Board currentBoard;
    private boolean isConfigured = false;
    private boolean isSolved = false;
    private int step = 0;
    private SequentialTransition sq;

    @FXML private Group NumGrid;
    @FXML private VBox InfoBox;
    @FXML private Button ResetButton;

    @FXML protected void onImportButtonClick() {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(stage);

        try {
            if (file != null) {
                NumGrid.getChildren().clear();
                this.currentBoard = new Board();
                this.currentBoard.setContentsFromFile(file.getPath(), false);
                this.solver = new Solver(this.currentBoard);
                configureInitialNumGrid();
                InfoBox.getChildren().clear();
                isConfigured = true;
                isSolved = false;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML protected void onRandomizeButtonClick() {
        NumGrid.getChildren().clear();
        this.currentBoard = new Board();
        this.currentBoard.setContentsRandomly();
        this.solver = new Solver(this.currentBoard);
        configureInitialNumGrid();
        InfoBox.getChildren().clear();
        isConfigured = true;
        isSolved = false;
    }

    @FXML protected void onSolveButtonClick() {
        ResetButton.setVisible(true);
        if (isConfigured && !isSolved){
            try {
                this.solver.solve();
                isSolved = true;
                isConfigured = false;
                this.step = 0;

                this.showSolveInfo();
                this.playSolutionAnimation();
            }
            catch (OutOfMemoryError e) {
                InfoBox.getChildren().clear();

                // Nilai fungsi pengecekan awal
                InfoLabel labelOutOfMem = new InfoLabel("Memory tidak cukup untuk melanjutkan pencarian.");
                InfoBox.getChildren().add(labelOutOfMem);

                // Nilai fungsi pengecekan awal
                InfoLabel labelFungsiPengecekan = new InfoLabel("SIGMA(KURANG(i)) + X = " + solver.initialCheckValue);
                InfoBox.getChildren().add(labelFungsiPengecekan);

                // Jumlah simpul yang dibangkitkan
                InfoLabel labelJumlahSimpul = new InfoLabel("Jumlah simpul yang dibangkitkan = " + this.solver.totalNodesCreated);
                InfoBox.getChildren().add(labelJumlahSimpul);
            }

        }
        else if (isSolved) {
            this.playSolutionAnimation();
        }
    }

    @FXML protected void onResetButtonClick() {
        this.currentBoard = new Board(this.solver.problem);
        NumGrid.getChildren().clear();
        configureInitialNumGrid();
        this.step = 0;
    }

    private void configureInitialNumGrid() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int num = this.solver.problem.getContentsAt(i, j);
                if (num != 0) {
                    NumberTile numberTile = new NumberTile(num, i, j);
                    this.NumGrid.getChildren().add(numberTile);
                }
            }
        }
    }

    private void showSolveInfo() {
        // Hapus isi InfoBox
        InfoBox.getChildren().clear();

        // Nilai fungsi pengecekan awal
        InfoLabel labelFungsiPengecekan = new InfoLabel("SIGMA(KURANG(i)) + X = " + solver.initialCheckValue);
        InfoBox.getChildren().add(labelFungsiPengecekan);

        // Bisa diselesaikan berdasarkan fungsi pengecekan awal atau tidak
        String canBeSolved = this.solver.solvable ? "Ya" : "Tidak";
        InfoLabel labelBisaDiselesaikan = new InfoLabel("Bisa diselesaikan? " + canBeSolved);
        InfoBox.getChildren().add(labelBisaDiselesaikan);

        if (this.solver.solvable) {
            // Jumlah langkah
            InfoLabel labelJumlahLangkah = new InfoLabel("Jumlah langkah = " + this.solver.solution.getPathTaken().size());
            InfoBox.getChildren().add(labelJumlahLangkah);

            // Jumlah simpul yang dibangkitkan
            InfoLabel labelJumlahSimpul = new InfoLabel("Jumlah simpul yang dibangkitkan = " + this.solver.totalNodesCreated);
            InfoBox.getChildren().add(labelJumlahSimpul);

            // Waktu eksekusi program
            InfoLabel labelWaktuEksekusi = new InfoLabel("Waktu eksekusi = " + this.solver.executionTime + " s");
            InfoBox.getChildren().add(labelWaktuEksekusi);

            // Langkah solusi
            InfoLabel labelInfoLangkah = new InfoLabel("Langkah menuju solusi : ");
            InfoBox.getChildren().add(labelInfoLangkah);

            int i = 0;
            while (i < this.solver.solution.getPathTaken().size()) {
                StringBuilder solution = new StringBuilder();
                int j = 0;
                while (i + j < this.solver.solution.getPathTaken().size() && j < 15) {
                    solution.append(Character.toUpperCase(this.solver.solution.getPathTaken().get(i + j)) + " ");
                    j++;
                }
                InfoLabel labeLangkahSolusi = new InfoLabel(solution.toString());
                InfoBox.getChildren().add(labeLangkahSolusi);
                i += 15;
            }

            InfoLabel labelInfoHuruf = new InfoLabel("* U = Up, D = Down, L = Left, R = Right");
            InfoBox.getChildren().add(labelInfoHuruf);
        }
    }

    private void playMove(int number, Character move) {
        for (Node n : NumGrid.getChildren()) {
            if (n instanceof NumberTile && n.getId().equals(Integer.toString(number))) {
                TranslateTransition translate = new TranslateTransition();
                translate.setNode(n);
                translate.setDuration(Duration.millis(300));

                switch (move) {
                    case 'l' -> {
                        translate.setByX(75);
                    }
                    case 'r' -> {
                        translate.setByX(-75);
                    }
                    case 'u' -> {
                        translate.setByY(75);
                    }
                    case 'd' -> {
                        translate.setByY(-75);
                    }
                    default -> {}
                }

                this.sq.getChildren().add(translate);
            }
        }
        this.sq.playFromStart();
    }

    private void moveTile() {
        if (this.step < this.solver.solution.getPathTaken().size()) {
            char move = this.solver.solution.getPathTaken().get(this.step);
            int moveRowLocation = this.currentBoard.getEmptyLocationRow();
            int moveColLocation = this.currentBoard.getEmptyLocationCol();

            switch (move) {
                case 'l' -> {
                    moveColLocation--;
                }
                case 'r' -> {
                    moveColLocation++;
                }
                case 'u' -> {
                    moveRowLocation--;
                }
                case 'd' -> {
                    moveRowLocation++;
                }
                default -> {}
            }

            int num = this.currentBoard.getContentsAt(moveRowLocation, moveColLocation);
            this.playMove(num, move);
            this.currentBoard = new Board(this.currentBoard, move);
            this.step++;
        }
    }

    private void playSolutionAnimation() {
        this.sq = new SequentialTransition();
        while (this.step < this.solver.solution.getPathTaken().size()) {
            this.moveTile();
        }
    }
}
