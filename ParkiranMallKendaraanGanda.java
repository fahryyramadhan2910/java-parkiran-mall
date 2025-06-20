import java.text.DecimalFormat;
import java.util.*;

public class ParkiranMallKendaraanGanda {
    private static final int TOTAL_SLOT = 15;
    private static int slotTersedia = TOTAL_SLOT;

    private static Map<String, Kendaraan> dataParkir = new HashMap<>();
    private static Scanner scanner = new Scanner(System.in);
    private static final DecimalFormat formatUang = new DecimalFormat("Rp#,###");

    static class Kendaraan {
        String tipe;
        int jamMasuk;

        Kendaraan(String tipe, int jamMasuk) {
            this.tipe = tipe;
            this.jamMasuk = jamMasuk;
        }
    }

    public static void main(String[] args) {
        while (true) {
            cetakMenu();
            System.out.print(">> Pilih menu: ");
            
            int pilihan;
            try {
                pilihan = Integer.parseInt(scanner.nextLine()); // fix di sini
            } catch (NumberFormatException e) {
                System.out.println("❌ Input tidak valid. Masukkan angka 1-4.");
                continue;
            }

            switch (pilihan) {
                case 1 -> kendaraanMasuk();
                case 2 -> kendaraanKeluar();
                case 3 -> tampilkanSlot();
                case 4 -> {
                    System.out.println("👋 Terima kasih telah menggunakan sistem parkir.");
                    return;
                }
                default -> System.out.println("❌ Pilihan menu tidak tersedia.");
            }
        }
    }

    private static void cetakMenu() {
        System.out.println("\n===============================");
        System.out.println("|  🚗 PARKIR MALL SUN PLAZA 🏍️  |");
        System.out.println("=================================");
        System.out.println("| 1. 🚘 Kendaraan Masuk         |");
        System.out.println("| 2. 🚪 Kendaraan Keluar        |");
        System.out.println("| 3. 🅿️  Cek Slot Tersisa       |");
        System.out.println("| 4. ❌ Keluar Program          |");
        System.out.println("=================================");
    }

    private static void kendaraanMasuk() {
        if (slotTersedia <= 0) {
            System.out.println("❌ Maaf, semua slot parkir penuh.");
            return;
        }

        System.out.print("🔑 Masukkan plat nomor: ");
        String plat = scanner.nextLine().toUpperCase();

        if (dataParkir.containsKey(plat)) {
            System.out.println("❗ Kendaraan ini sudah parkir.");
            return;
        }

        System.out.print("🚦 Jenis kendaraan (motor/mobil): ");
        String jenis = scanner.nextLine().toLowerCase();
        if (!jenis.equals("motor") && !jenis.equals("mobil")) {
            System.out.println("❌ Jenis kendaraan tidak valid.");
            return;
        }

        System.out.print("⏰ Jam masuk (0-23): ");
        int jamMasuk;
        try {
            jamMasuk = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("❌ Input jam tidak valid.");
            return;
        }

        if (jamMasuk < 0 || jamMasuk > 23) {
            System.out.println("❌ Jam tidak valid.");
            return;
        }

        dataParkir.put(plat, new Kendaraan(jenis, jamMasuk));
        slotTersedia--;

        System.out.println("✅ " + jenis.toUpperCase() + " dengan plat " + plat + " berhasil masuk.");
        System.out.println("🔢 Slot tersedia: " + slotTersedia + "/" + TOTAL_SLOT);
    }

    private static void kendaraanKeluar() {
        System.out.print("🔑 Masukkan plat nomor kendaraan: ");
        String plat = scanner.nextLine().toUpperCase();

        if (!dataParkir.containsKey(plat)) {
            System.out.println("❌ Kendaraan tidak ditemukan di parkiran.");
            return;
        }

        Kendaraan k = dataParkir.get(plat);

        System.out.print("⏰ Jam keluar (0-23): ");
        int jamKeluar;
        try {
            jamKeluar = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("❌ Input jam tidak valid.");
            return;
        }

        if (jamKeluar < 0 || jamKeluar > 23) {
            System.out.println("❌ Jam keluar tidak valid.");
            return;
        }

        int durasi = (jamKeluar - k.jamMasuk + 24) % 24;
        if (durasi == 0) durasi = 1;

        int tarifAwal = k.tipe.equals("motor") ? 2000 : 3000;
        int tarifTambahan = k.tipe.equals("motor") ? 3000 : 4000;
        int totalBiaya = tarifAwal + (durasi - 1) * tarifTambahan;

        System.out.println("\n==============================");
        System.out.println("         STRUK PARKIR         ");
        System.out.println("==============================");
        System.out.println("Plat Nomor : " + plat);
        System.out.println("Jenis      : " + k.tipe.toUpperCase());
        System.out.println("Jam Masuk  : " + k.jamMasuk + ".00");
        System.out.println("Jam Keluar : " + jamKeluar + ".00");
        System.out.println("Durasi     : " + durasi + " jam");
        System.out.println("Total Bayar: " + formatUang.format(totalBiaya) + ",-");
        System.out.println("==============================\n");

        dataParkir.remove(plat);
        slotTersedia++;
    }

    private static void tampilkanSlot() {
        System.out.println("🅿️ Slot tersedia: " + slotTersedia + " dari " + TOTAL_SLOT);
    }
}
