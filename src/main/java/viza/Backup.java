package viza;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Map;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class Backup {

    // args: -s path -r -d path -e ext -f filename
    public static void main(String[] args) {
        String soursePath = "";
        String destenationPath = "";
        String extension = "";
        String fileOut = "";
        boolean removeFiles = false;

        try {
            for (int i = 0; i < args.length; i++) {
                if (args[i].equals("-s")) {
                    soursePath = args[i + 1];
                }
                if (args[i].equals("-d")) {
                    destenationPath = args[i + 1];
                }
                if (args[i].equals("-r")) {
                    removeFiles = true;
                }
                if (args[i].equals("-e")){
                    extension = args[i+1];
                }
                if (args[i].equals("-f")){
                    fileOut = args[i+1];
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
            return;
        }

        if (soursePath.equals("") || destenationPath.equals("") || extension.equals("")) {
            System.out.println("You should use:\r\n -s path -d path -r -e ext - remove after copy files\r\n or \r\n -s path -d path -e ext -  don't remove after copy");
            return;
        }

        try {

            File f = new File(fileOut);
            f.getParentFile().mkdirs();
            f.createNewFile();

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            final String fExtension = extension;
            final String finalFileOut = fileOut;
            final boolean finalRemoveFiles = removeFiles;
            final String finalDestenationPath = destenationPath;
            Files.walk(Paths.get(soursePath))
                    .map(p -> p.toFile())
                    .filter(p -> p.getName().endsWith(fExtension))
                    .forEach(p -> {

                        Path source = Paths.get(p.getAbsolutePath());

                        //String nameForTarget = p.getAbsolutePath().substring(p.getAbsolutePath().lastIndexOf("\\")+1);

                        File f = new File("C:\\Hello\\AnotherFolder\\The File Name.PDF");

                        Path target = Paths.get(finalDestenationPath + f.getName());

                        try {
                            Files.copy(source, target, REPLACE_EXISTING);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        if (finalRemoveFiles){
                            try {
                                Files.delete(Paths.get(p.getAbsolutePath()));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        try {
                            Files.write(Paths.get(finalFileOut), (p.getAbsolutePath() + "\r\n").getBytes(), StandardOpenOption.APPEND);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
