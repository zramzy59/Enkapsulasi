public class pelanggan {
    // Atribut privat untuk menyimpan informasi pelanggan
    private String nomorPelanggan;
    private String nama;
    private int saldo;
    private String pin;
    private boolean isBlocked = false; // Menandakan apakah akun diblokir
    private int percobaanLogin = 0; // Menghitung jumlah percobaan login yang gagal

    // Konstruktor untuk menginisialisasi data pelanggan
    public pelanggan(String nomorPelanggan, String nama, int saldo, String pin) {
        this.nomorPelanggan = nomorPelanggan;
        this.nama = nama;
        this.saldo = saldo;
        this.pin = pin;
    }

    // Method untuk proses autentikasi pelanggan
    public boolean autentikasi(String nomor, String pinInput) {
        if (isBlocked) { // Jika akun diblokir
            System.out.println("Akun Anda telah diblokir.");
            return false;
        }

        // Jika nomor dan pin cocok
        if (nomorPelanggan.equals(nomor) && pin.equals(pinInput)) {
            percobaanLogin = 0; // Reset percobaan login
            return true;
        } else {
            percobaanLogin++; // Tambah percobaan login jika gagal
            if (percobaanLogin >= 3) {
                isBlocked = true; // Blokir akun setelah 3x gagal
                System.out.println("Akun Anda telah diblokir setelah 3x gagal.");
            } else {
                System.out.println("PIN atau nomor salah. Percobaan ke-" + percobaanLogin);
            }
            return false;
        }
    }

    // Method untuk menambahkan saldo
    public void topUp(int jumlah) {
        saldo += jumlah;
        System.out.println("Top up berhasil. Saldo sekarang: Rp" + saldo);
    }

    // Method untuk melakukan pembelian dengan perhitungan cashback
    public void beli(int jumlah) {
        int cashback = hitungCashback(jumlah); // Hitung cashback berdasarkan nominal
        int totalBayar = jumlah - cashback;

        // Cek apakah saldo mencukupi setelah pembelian dan batas minimal saldo adalah 10.000
        if (saldo - totalBayar < 10000) {
            System.out.println("Transaksi gagal. Saldo tidak mencukupi batas minimal.");
        } else {
            saldo -= totalBayar; // Kurangi saldo dengan total bayar
            saldo += cashback; // Tambahkan cashback ke saldo
            System.out.println("Pembelian berhasil. Cashback: Rp" + cashback);
            System.out.println("Sisa saldo: Rp" + saldo);
        }
    }

    // Method privat untuk menghitung cashback berdasarkan kode jenis pelanggan
    private int hitungCashback(int jumlah) {
        if (jumlah < 1000000) return 0; // Tidak ada cashback jika kurang dari 1 juta

        String kode = nomorPelanggan.substring(0, 2); // Ambil 2 digit awal nomor
        switch (kode) {
            case "38": return (int)(jumlah * 0.05); // silver (5%)
            case "56": return (int)(jumlah * 0.07); // gold (7%)
            case "74": return (int)(jumlah * 0.10); // platinum (10%)
            default: return 0;
        }
    }

    // Method getter untuk status blokir
    public boolean isBlocked() {
        return isBlocked;
    }

    // Method getter untuk nomor pelanggan
    public String getNomorPelanggan() {
        return nomorPelanggan;
    }

    // Method getter untuk nama pelanggan
    public String getNama() {
        return nama;
    }
}
