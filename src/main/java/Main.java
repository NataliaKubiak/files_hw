import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main {

    private static StringBuilder log = new StringBuilder();
    public static void main(String[] args) {
        //1
        mkDir("src");
        mkDir("res");
        mkDir("savegames");
        mkDir("temp");

        //2
        mkDir("src/main");
        mkDir("src/test");

        //3
        mkFile("/Users/nataliakubiak/Games/src/main", "Main.java");
        mkFile("/Users/nataliakubiak/Games/src/main", "Utils.java");

        //4
        mkDir("res/drawables");
        mkDir("res/vectors");
        mkDir("res/icons");

        //5
        mkFile("/Users/nataliakubiak/Games/temp", "temp.txt");

        try (FileWriter writer =  new FileWriter("/Users/nataliakubiak/Games/temp/temp.txt")) {
            writer.write(String.valueOf(log));
            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void mkDir(String dirName) {
        String dirPath = "/Users/nataliakubiak/Games/" + dirName;
        File srcDir = new File(dirPath);

        if (srcDir.mkdir()) {
            log.append("[INFO] Directory " + dirPath + " was made\n");
        } else {
            log.append("[ERROR] Failed to make a directory " + dirPath + "\n");
        }
    }

    private static void mkFile(String filePath, String fileName) {
        File file = new File(filePath + "/" + fileName);
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
}
