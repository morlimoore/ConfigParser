import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConfigParserTest {
    /**
     * This tests basic operations of our program.
     * It uses the config.txt file to execute the tests.
     */

    ConfigParser config;
    @BeforeEach
    void setup() {
        config = new ConfigParser("config.txt");
    }

    @Test
    @DisplayName("Can read accurate value from hashMap")
    void get() {
        assertAll(
                () -> assertEquals("sq04_db", config.get("dbname")),
                () -> assertEquals("127.0.0.1", config.get("host")),
                () -> assertEquals("fintek", config.get("application.name")),
                () -> assertEquals("8080", config.get("application.port"))
        );
    }

    @Test
    @DisplayName("Can populate hashMap")
    void readConfigFile() {
        assertTrue(config.getSizeOfFileMap() > 0);
    }
}