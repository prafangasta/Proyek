import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class owner {
    public static void main(String[] args) {
        String outputFile = "owner_data.sql"; 
        int jumlahData = 10000;

        Map<String, String[]> provincesWithCities = Map.of(
            "DKI Jakarta", new String[]{"Jakarta Pusat", "Jakarta Barat", "Jakarta Timur", "Jakarta Selatan", "Jakarta Utara"},
            "Jawa Barat", new String[]{"Bandung", "Bogor", "Depok", "Cimahi", "Bekasi"},
            "Jawa Timur", new String[]{"Surabaya", "Malang", "Kediri", "Blitar", "Madiun"},
            "DI Yogyakarta", new String[]{"Yogyakarta", "Bantul", "Sleman", "Wonosari", "Kulon Progo"},
            "Sumatera Utara", new String[]{"Medan", "Binjai", "Pematangsiantar", "Tebing Tinggi", "Langkat"}
        );

        Random random = new Random();
        Set<String> existingIds = new HashSet<>();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            for (int i = 1; i <= jumlahData; i++) {
                String Id_owner = String.format("ID%04d", i); 

                while (existingIds.contains(Id_owner)) {
                    i++; 
                    Id_owner = String.format("ID%04d", i);
                }
                existingIds.add(Id_owner);

                String[] firstNames = {"John", "Jane", "Michael", "Emily", "Chris", "Anna", "David", "Laura"};
                String[] lastNames = {"Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia", "Miller", "Davis"};
                String Nama_lengkap = firstNames[random.nextInt(firstNames.length)] + " " + lastNames[random.nextInt(lastNames.length)];

                long No_telpon = 1000000000L + random.nextInt(900000000); 

                String Email = Nama_lengkap.replace(" ", ".").toLowerCase() + "@example.com";

                String[] provinces = provincesWithCities.keySet().toArray(new String[0]);
                String Provinsi = provinces[random.nextInt(provinces.length)];
                String[] cities = provincesWithCities.get(Provinsi);
                String Kota = cities[random.nextInt(cities.length)];

                String[] streets = {"Jl. Merdeka", "Jl. Sudirman", "Jl. Thamrin", "Jl. Gatot Subroto", "Jl. Gajah Mada"};
                String Jalan = streets[random.nextInt(streets.length)] + " No. " + (random.nextInt(100) + 1);
                int Kode_pos = 10000 + random.nextInt(90000); 

                String sql = String.format(
                        "INSERT INTO owner (ID_owner, Nama_lengkap, Nomor_telpon, Email, Jalan, Kota, Provinsi, Kode_pos) VALUES ('%s', '%s', %d, '%s', '%s', '%s', '%s', %d);",
                        Id_owner, Nama_lengkap, No_telpon, Email, Jalan, Kota, Provinsi, Kode_pos);

                writer.write(sql);
                writer.newLine();
                System.out.println(sql); 
            }
            System.out.println("File SQL berhasil dibuat: " + outputFile);
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan saat membuat file SQL.");
            e.printStackTrace();
        }
    }
}