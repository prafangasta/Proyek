import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class pembayaran {
    public static void main(String[] args) {
        List<String> validTransactionIDs = generateValidTransactionIDs(15000);
        generatePembayaranData(15000, "pembayaran_data.sql", validTransactionIDs);
    }

    public static List<String> generateValidTransactionIDs(int count) {
        List<String> transactionIDs = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            transactionIDs.add("T" + String.format("%05d", i));
        }
        return transactionIDs;
    }

    public static void generatePembayaranData(int count, String outputFile, List<String> validTransactionIDs) {
        String[] methods = {
            "Cash", 
            "Bank Transfer", 
            "Credit Card", 
            "E-Wallet", 
            "Virtual Account"
        };

        Random random = new Random();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            for (int i = 1; i <= count; i++) {
                String ID_pembayaran = "B" + String.format("%04d", i);

                String ID_transaksi = validTransactionIDs.get(random.nextInt(validTransactionIDs.size()));

                String Metode_pembayaran = methods[random.nextInt(methods.length)];

                Calendar cal = Calendar.getInstance();
                cal.set(2024, random.nextInt(12), random.nextInt(28) + 1);
                String Tanggal_pembayaran = dateFormat.format(cal.getTime());

                int Jumlah = 100000 + (random.nextInt(51) * 10000);

                String sql = String.format(
                        "INSERT INTO pembayaran (Id_pembayaran, Metode_pembayaran, Tanggal_pembayaran, Jumlah, Id_transaksi) " +
                                "VALUES ('%s', '%s', '%s', %d, '%s');",
                        ID_pembayaran, Metode_pembayaran, Tanggal_pembayaran, Jumlah, ID_transaksi);

                writer.write(sql);
                writer.newLine();

                if (i % 100 == 0) {
                    System.out.println("Flushing data at record " + i);
                    writer.flush();
                }
            }
            System.out.println("File SQL Pembayaran berhasil dibuat: " + outputFile);
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan saat membuat file SQL.");
            e.printStackTrace();
        }
    }
}