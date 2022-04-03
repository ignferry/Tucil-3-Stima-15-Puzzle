# Tugas Kecil Strategi Algoritma - 15-Puzzle Solver
## Deskripsi Singkat
15-Puzzle adalah puzzle yang menugaskan kita untuk mengurutkan susunan angka 1-15 dari konfigurasi awal acak. Pada setiap langkah, pemain hanya bisa memindahkan angka yang berdekatan dengan ubin kosong ke lokasi ubin kosong tersebut.

Program ini menyelesaikan persoalan 15-Puzzle dengan algoritma *Branch and Bound*. Konfigurasi puzzle dapat diperoleh dari file teks dengan format seperti di folder test atau dibangkitkan secara acak.

## Requirements
- Java JDK 18
- IntelliJ IDEA
- JavaFX

## Cara Menjalankan
Unduh dan buka file Tucil-3-Stima-15-Puzzle.jar di dalam folder bin

## Cara Membuat Executable
1. Unduh repository ini
2. Buka repository ini di IntelliJ IDEA
3. Buka menu File > Project Structure > Artifacts >> Add >> JAR >> From modules with dependencies...
4. Pilih Main1 sebagai Main Class
5. Di menu Artifacts, pilih Add Copy of > File dan pilih semua file bin di dalam folder instalasi JavaFX
6. Set Output directory ke folder tujuan pembuatan executable
7. Klik Apply dan OK
8. Buka menu Build > pilih Build Artifacts... dan pilih Build
9. Buka file executable .jar di folder tujuan

## Cara Menggunakan Program
- Tekan tombol Import untuk memilih file teks yang berisi konfigurasi puzzle
- Tekan tombol Randomize untuk membangkitkan konfigurasi puzzle secara acak
- Tekan tombol Solve untuk memulai pencarian solusi puzzle dan menjalankan animasi langkah penyelesaian puzzle
- Tekan tombol Reset untuk mengembalikan posisi angka ke konfigurasi semula

## Pembuat Program
Ignasius Ferry Priguna (13520126)
