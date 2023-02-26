import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Main {
    public static void main(String[] args) {
        openZip("Games/savegames/zip.zip", "Games/savegames");
        openProgress("Games/savegames/save3.dat");
    }

    public static void openZip (String zipName, String directoryName) {
        try (ZipInputStream zin = new ZipInputStream(new FileInputStream(zipName))) {
            ZipEntry entry;
            String name;
            File fileName;
            while ((entry = zin.getNextEntry()) != null) {
                name = entry.getName();
                fileName = new File(directoryName, name);
                FileOutputStream fout = new FileOutputStream(fileName);
                for (int c = zin.read(); c != -1; c = zin.read()) {
                    fout.write(c);
                }
                fout.flush();
                zin.closeEntry();
                fout.close();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void openProgress(String fileName) {
        GameProgress gameProgress = null;
        try (FileInputStream  fis = new FileInputStream(fileName);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            gameProgress = (GameProgress) ois.readObject();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println(gameProgress);
    }
}
