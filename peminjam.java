import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Random;

public class peminjam {
    public static void main(String[] args) {
        generatePeminjamData(15000, "peminjam_data.sql");
    }

    public static void generatePeminjamData(int count, String outputFile) {
        Map<String, String[]> provincesWithCities = Map.of(
            "DKI Jakarta", new String[]{"Jakarta Pusat", "Jakarta Barat", "Jakarta Timur", "Jakarta Selatan", "Jakarta Utara"},
            "Jawa Barat", new String[]{"Bandung", "Bogor", "Depok", "Cimahi", "Bekasi"},
            "Jawa Timur", new String[]{"Surabaya", "Malang", "Kediri", "Blitar", "Madiun"},
            "DI Yogyakarta", new String[]{"Yogyakarta", "Bantul", "Sleman", "Wonosari", "Kulon Progo"},
            "Sumatera Utara", new String[]{"Medan", "Binjai", "Pematangsiantar", "Tebing Tinggi", "Langkat"},
            "Banten", new String[]{"Serang", "Cilegon", "Tangerang", "Tangerang Selatan", "Pandeglang"},
            "Bali", new String[]{"Denpasar", "Badung", "Gianyar", "Karangasem", "Bangli"},
            "Kalimantan Selatan", new String[]{"Banjarmasin", "Banjarbaru", "Martapura", "Pelaihari", "Barabai"},
            "Kalimantan Timur", new String[]{"Samarinda", "Balikpapan", "Bontang", "Tenggarong", "Kutai Kartanegara"},
            "Sulawesi Selatan", new String[]{"Makassar", "Parepare", "Palopo", "Sinjai", "Gowa"}
        );

        String[] namaDepan = {"Ahmad", "Budi", "Chandra", "Dewi", "Eka", "Fina", "Gina", "Hadi", "Indah", "Joko"};
        String[] namaBelakang = {"Sutrisno", "Pratama", "Wijaya", "Anwar", "Susanto", "Harsono", "Kurniawan", "Setiawan", "Sari", "Putra"};

        Random random = new Random();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            for (int i = 1; i <= count; i++) {
                String Id_peminjam = "P" + String.format("%04d", i);

                String Nama_lengkap = namaDepan[random.nextInt(namaDepan.length)] + " " +
                                      namaBelakang[random.nextInt(namaBelakang.length)];

                String Nomor_telpon = "08" + (random.nextInt(90000000) + 10000000);

                String Email = Nama_lengkap.replace(" ", ".").toLowerCase() + "@gmail.com";

                String Jalan = "Jl. " + namaDepan[random.nextInt(namaDepan.length)] + " " + random.nextInt(100);

                String[] provinces = provincesWithCities.keySet().toArray(new String[0]);
                String selectedProvince = provinces[random.nextInt(provinces.length)];
                String[] cities = provincesWithCities.get(selectedProvince);
                String Kota = cities[random.nextInt(cities.length)];
                String Provinsi = selectedProvince;

                int Kode_pos = random.nextInt(90000) + 10000;

                String Jaminan = "KTP" + String.format("%016d", Math.abs(random.nextLong() % 10000000000000000L));

                String sql = String.format(
                    "INSERT INTO peminjam (Id_peminjam, Nama_lengkap, No_telpon, Email, Jalan, Kota, Provinsi, Kode_pos, Jaminan) " +
                    "VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', %d, '%s');",
                    Id_peminjam, Nama_lengkap, Nomor_telpon, Email, Jalan, Kota, Provinsi, Kode_pos, Jaminan);

                writer.write(sql);
                writer.newLine();

                if (i % 100 == 0) {
                    writer.flush();
                }
            }
            System.out.println("File SQL Peminjam berhasil dibuat: " + outputFile);
        } catch (IOException e) {
            System.err.println("Terjadi kesalahan saat membuat file SQL.");
            e.printStackTrace();
        }
    }
}