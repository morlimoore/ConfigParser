import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class ConfigParser {
    /**
     * The appropriate file is read, and the data populated on a hashMap in key-value pairs.
     * Those values are then accessed by their keys, through a get method.
     *
     * As a result of problems with the IDE on selecting the file path correctly,
     * we rely on the Java Path class to locate the file on the project classpath, and
     * generate the file path as it sees it. This is the file path we use in creating the
     * file object for the program.
     */
    File file;
    private Map<String, String> mapOfFileData;

    public ConfigParser(String name_of_file) {
        mapOfFileData = new HashMap<String, String>();
//        this.file = new File(name_of_file);

        //An interim file object is created with the file name.
        //The temporary in turn gets used to create a Path object.
        Path pathToFile = new File(name_of_file).toPath();

        //This locates the file on the classpath and matches it up to fit our project structure
        if (!pathToFile.toAbsolutePath().toString().contains("src/main/java/"))
            pathToFile = FileSystems.getDefault().getPath("src/main/java/" + name_of_file);

        this.file = pathToFile.toFile();
        readConfigFile();
        getRunningEnvironment();
    }

    //This method fetches and prints values from the hashmap
    public String get(String key) {
//        System.out.println(key + ": " + mapOfFileData.get(key));
        return mapOfFileData.get(key);
    }

    public int getSizeOfFileMap() {
        return mapOfFileData.size();
    }

//    public String getString(String key) {
//        return key + ": " + mapOfFileData.get(key));
//    }

    //This method reads the file and populates the hashmap
    public void readConfigFile() {
        String[] arrayOfSplitData;
        try(FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
        ){
            String readLine = bufferedReader.readLine();
            String prefix = "";
            while(readLine != null) {
                arrayOfSplitData = readLine.split("=");
                if (arrayOfSplitData.length > 1) {

                    //Ensures we don't have duplicates pushed to the hashmap
                    //Prefix holds the title text, if encountered
                    if (!mapOfFileData.containsKey(prefix + arrayOfSplitData[0])) {
                        mapOfFileData.put(prefix + arrayOfSplitData[0], arrayOfSplitData[1]);
                    }
                }
                //This handles the title texts [xxx]; it extracts the text from the braces and appends a '.'
                if (arrayOfSplitData[0].contains("[")) {
                    prefix = arrayOfSplitData[0].substring(1, arrayOfSplitData[0].length() - 1);
                    prefix += ".";
                }

                //Clears any title text picked up
                if (arrayOfSplitData[0].length() == 0) {
                    prefix = "";
                }
                readLine = bufferedReader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Prints the environment the program is currently running on
    public void getRunningEnvironment() {
        System.out.println("Environment => " + mapOfFileData.get("mode"));
    }
}
