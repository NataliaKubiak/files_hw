import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    private static StringBuilder log = new StringBuilder();
    private static final String rootDirPath = "/Users/nataliakubiak/Games/";
    public static void main(String[] args) {
        //Задача 1. Пункт 1
        mkDir("src");
        mkDir("res");
        mkDir("savegames");
        mkDir("temp");

        //Задача 1. Пункт 2
        mkDir("src/main");
        mkDir("src/test");

        //Задача 1. Пункт 3
        mkFile( "src/main", "Main.java");
        mkFile("src/main", "Utils.java");

        //Задача 1. Пункт 4
        mkDir("res/drawables");
        mkDir("res/vectors");
        mkDir("res/icons");

        //Задача 1. Пункт 5
        mkFile("temp", "temp.txt");

        try (FileWriter writer =  new FileWriter(rootDirPath + "temp/temp.txt")) {
            writer.write(String.valueOf(log));
            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        //Задача 2. Пункт 1
        GameProgress progress1 = new GameProgress(100, 50, 80, 2000);
        GameProgress progress2 = new GameProgress(10, 5, 8, 200);
        GameProgress progress3 = new GameProgress(77, 35, 101, 5600);

        //Задача 2. Пункт 2
        saveGame("savegames/save1.dat", progress1);
        saveGame("savegames/save2.dat", progress2);
        saveGame("savegames/save3.dat", progress3);

        //Задача 2. Пункт 3
        List<String> packingFilePaths = List.of("savegames/save1.dat", "savegames/save2.dat", "savegames/save3.dat");
        List<String> packedFileNames = List.of("packed_save1.dat", "packed_save2.dat", "packed_save3.dat");

        zipFiles("savegames/zip.zip", packingFilePaths, packedFileNames);

        //Задача 2. Пункт 4
        deleteFile("savegames/save1.dat");
        deleteFile("savegames/save2.dat");
        deleteFile("savegames/save3.dat");
    }

    public static void mkDir(String dirName) {
        String dirPath = rootDirPath + dirName;
        File srcDir = new File(dirPath);

        if (srcDir.mkdir()) {
            log.append("[INFO] Directory " + dirPath + " was made\n");
        } else {
            log.append("[ERROR] Failed to make a directory " + dirPath + "\n");
        }
    }

    private static void mkFile(String filePath, String fileName) {
        File file = new File(rootDirPath + filePath + "/" + fileName);
        try {
            if (file.createNewFile()) {
                log.append("[INFO] " + fileName + " was made in a directory " + filePath + "\n");
            } else {
                log.append("[ERROR] Failed to make " + fileName + " in a directory " + filePath + "\n");
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void deleteFile(String filePath) {
        File file = new File(rootDirPath + filePath);
        if (file.delete()) {
            System.out.println(filePath + " was deleted");
        }
    }

    private static void saveGame(String path, GameProgress gameProgress) {
        try (FileOutputStream fos = new FileOutputStream(rootDirPath + path);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void zipFiles(String zipPath, List<String> packingFilePaths, List<String> packedFileNames) {
        //тут сделала try не с ресурсами а обычный, чтобы можно было в for-loop передавать сколько угодно файлов
        //которые нужно запаковать
        try {
            ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(rootDirPath + zipPath));

            for (int i = 0; i < packingFilePaths.size(); i++) {
                FileInputStream fis = new FileInputStream(rootDirPath + packingFilePaths.get(i));
                ZipEntry entry = new ZipEntry(packedFileNames.get(i));
                zout.putNextEntry(entry);
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                zout.write(buffer);
                zout.closeEntry();
                fis.close();
            }
            zout.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
