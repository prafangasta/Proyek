import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class kamera {
    public static void main(String[] args) {
        generateKameraData(13000, 10000, "kamera_data.sql");
    }

    public static void generateKameraData(int totalCameras, int totalOwners, String outputFile) {
        String[] brands = {"Canon", "Nikon", "Sony", "Fujifilm", "Panasonic"};
        String[] types = {"Mirrorless", "DSLR", "Compact", "Bridge", "Instant"};
        String[] descriptions = {
            "Kamera profesional untuk fotografer berpengalaman",
            "Kamera ringan cocok untuk perjalanan",
            "Kamera dengan fitur video 4K",
            "Kamera untuk pemula dengan mode otomatis",
            "Kamera tahan air untuk aktivitas outdoor"
        };
        String[] statuses = {"Available", "Rented", "Maintenance"};

        List<String> owners = new ArrayList<>();
        for (int i = 1; i <= totalOwners; i++) {
            owners.add(String.format("ID%04d", i));
        }

        Random random = new Random();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            System.out.println("Starting to generate SQL data for Kamera...");

            for (int i = 1; i <= totalCameras; i++) {
                String Id_kamera = "K" + String.format("%04d", i);
                String Merek_kamera = brands[random.nextInt(brands.length)];
                String Type_kamera = types[random.nextInt(types.length)];
                int Harga_sewa_per_hari = 50000 + (random.nextInt(20) * 5000);
                String Status_kamera = statuses[random.nextInt(statuses.length)];
                String Deskripsi = descriptions[random.nextInt(descriptions.length)];
                String Id_owner = owners.get(random.nextInt(owners.size()));

                String sql = String.format(
                        "INSERT INTO kamera (Id_kamera, Merek_kamera, Type_kamera, Harga_sewa_per_hari, Status_kamera, Deskripsi, Id_owner) " +
                        "VALUES ('%s', '%s', '%s', %d, '%s', '%s', '%s');",
                        Id_kamera, Merek_kamera, Type_kamera, Harga_sewa_per_hari, Status_kamera, Deskripsi, Id_owner);

                writer.write(sql);
                writer.newLine();

                if (i <= 100) {
                    System.out.println(sql);
                }

                if (i % 100 == 0) {
                    writer.flush();
                    System.out.println("Flushing at camera " + i);
                }
            }

            System.out.println("File SQL Kamera berhasil dibuat: " + outputFile);
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan saat membuat file SQL Kamera.");
            e.printStackTrace();
        }
    }
}