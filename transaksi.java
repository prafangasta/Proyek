import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class transaksi {
    public static void main(String[] args) {
        generateTransaksiData(15000, "transaksi_data.sql");
    }

    public static void generateTransaksiData(int count, String outputFile) {
        Random random = new Random();

        String[] statusTransaksi = {"Selesai", "Pending", "Batal"};

        int jumlahKamera = 15000;
        int jumlahPeminjam = 15000;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            for (int i = 1; i <= count; i++) {
                String ID_transaksi = "T" + String.format("%05d", i);
                String Id_kamera = "K" + String.format("%05d", random.nextInt(jumlahKamera) + 1);
                String Id_peminjam = "P" + String.format("%05d", random.nextInt(jumlahPeminjam) + 1);

                int tahun = 2024;
                int bulanSewa = random.nextInt(12) + 1;
                int hariSewa = random.nextInt(28) + 1;
                String Tanggal_sewa = String.format("%d-%02d-%02d", tahun, bulanSewa, hariSewa);

                int bulanKembali = bulanSewa + random.nextInt(3);
                int hariKembali = random.nextInt(28) + 1;
                if (bulanKembali > 12) {
                    bulanKembali -= 12;
                    tahun += 1;
                }
                String Tanggal_pengembalian = String.format("%d-%02d-%02d", tahun, bulanKembali, hariKembali);

                int Total_harga = 100000 + (random.nextInt(9) * 50000);

                String Status_transaksi = statusTransaksi[random.nextInt(statusTransaksi.length)];

                String sql = String.format(
                        "INSERT INTO transaksi (Id_transaksi, Id_kamera, Id_peminjam, Tanggal_sewa, Tanggal_pengembalian, Total_harga, Status_transaksi) " +
                        "VALUES ('%s', '%s', '%s', '%s', '%s', %d, '%s');",
                        ID_transaksi, Id_kamera, Id_peminjam, Tanggal_sewa, Tanggal_pengembalian, Total_harga, Status_transaksi);

                writer.write(sql);
                writer.newLine();

                if (i % 100 == 0) {
                    writer.flush();
                }
            }

            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}