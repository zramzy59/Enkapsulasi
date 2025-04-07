import java.util.Scanner;

public class MainTiny {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        pelanggan pelanggan; // Membuat objek pelanggan

        // --- Proses Pendaftaran Pelanggan Baru ---
        System.out.println("=== Daftar Pelanggan Baru ===");
        String nomor;
        while (true) {
            System.out.print("Masukkan nomor pelanggan (10 digit, awalan 38/56/74): ");
            nomor = sc.nextLine();
            // Validasi: nomor harus 10 digit dan diawali 38, 56, atau 74
            if (nomor.length() == 10 && (nomor.startsWith("38") || nomor.startsWith("56") || nomor.startsWith("74"))) {
                break;
            } else {
                System.out.println("Nomor tidak valid. Pastikan terdiri dari 10 digit dan diawali 38, 56, atau 74.");
            }
        }

        // Input nama pelanggan
        System.out.print("Masukkan nama pelanggan: ");
        String nama = sc.nextLine();

        // Input PIN pelanggan
        System.out.print("Buat PIN (4 digit): ");
        String pin = sc.nextLine();

        // Input saldo awal (minimal Rp10.000)
        int saldoAwal;
        while (true) {
            System.out.print("Masukkan saldo awal (minimal Rp10.000): ");
            saldoAwal = sc.nextInt();
            if (saldoAwal >= 10000) break;
            else System.out.println("Saldo terlalu kecil. Minimal Rp10.000 diperlukan untuk mendaftar.");
        }

        // Membuat objek pelanggan dengan data yang sudah diinput
        pelanggan = new pelanggan(nomor, nama, saldoAwal, pin);
        sc.nextLine(); // Menghindari bug input (newline)

        System.out.println("\nPendaftaran berhasil! Silakan login untuk melakukan transaksi.");

        // --- Proses Login dan Transaksi ---
        int percobaan = 0; // Menyimpan jumlah percobaan login
        boolean loginBerhasil = false; // Status login

        // Pengguna diberi maksimal 3 kali kesempatan login
        while (percobaan < 3 && !loginBerhasil) {
            System.out.print("Masukkan nomor pelanggan: ");
            String noLogin = sc.nextLine();
            System.out.print("Masukkan PIN: ");
            String pinLogin = sc.nextLine();

            // Proses autentikasi
            if (pelanggan.autentikasi(noLogin, pinLogin)) {
                loginBerhasil = true;
                System.out.println("\nSelamat datang, " + pelanggan.getNama());

                // Menu pilihan transaksi
                System.out.println("1. Top Up\n2. Pembelian\nPilih opsi:");
                int opsi = sc.nextInt();

                switch (opsi) {
                    case 1:
                        // Transaksi top up saldo
                        System.out.print("Masukkan jumlah top up: ");
                        int topup = sc.nextInt();
                        pelanggan.topUp(topup);
                        break;
                    case 2:
                        // Transaksi pembelian
                        System.out.print("Masukkan jumlah pembelian: ");
                        int belanja = sc.nextInt();
                        pelanggan.beli(belanja);
                        break;
                    default:
                        System.out.println("Opsi tidak valid. Silakan pilih 1 atau 2.");
                }
            } else {
                percobaan++; // Menambah jumlah percobaan login yang gagal
                if (percobaan >= 3) {
                    System.out.println("Akun diblokir setelah 3 kali percobaan gagal. Silakan hubungi layanan pelanggan.");
                }
            }
        }

        sc.close(); // Menutup scanner setelah selesai
    }
}
