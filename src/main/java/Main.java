public class Main {
    /**
     *This is where the program is run.
     * ConfigParser class is already instantiated, with get methods for certain key-values.
     * Specify the key-value you want by passing the key to the get method parameter.
     *
     * @param environment
     *
     */

    //This receives the environment and builds the necessary file name
    public static String buildFileName(String environment) {
        return "config" + environment + ".txt";
    }

    //Main method receives the user specified environment from the terminal, and
    //runs the buildFileName method.
    public static void main(String[] args) {
        String name_of_file = "";

        if (args.length == 0 || args[0].toLowerCase().equals("production")) {
            name_of_file = buildFileName("");
        }
        else if (args[0].toLowerCase().equals("staging")) {
            name_of_file = buildFileName("-staging");
        }
        else if (args[0].toLowerCase().equals("development")) {
            name_of_file = buildFileName("-dev");
        }
        else {
            System.out.println("Invalid environment");
            return;
        }

        //Instantiation, method calling and printing is done here
        ConfigParser config = new ConfigParser(name_of_file);

        String dbname = config.get("dbname");
        String applicationName = config.get("application.name");
        config.get("application.port");
        config.get("mode");

        System.out.println("dbname: " + dbname);
        System.out.println("application.name: " + applicationName);
    }
}
