package mft.library.model.repository.dbsql;

import mft.library.model.repository.utils.ConnectionProvider;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class DatabaseMaker {
    public static void createDatabase() throws Exception {
        File file = new File("./src/main/java/mft/library/model/repository/dbsql/person.sql");

        Scanner scanner = new Scanner(file);
        String lines = "";
        while(scanner.hasNextLine()) {
            lines += scanner.nextLine();
        }

        ConnectionProvider connectionprovider = new ConnectionProvider();

        for (String sqlCommand : lines.split(";")) {
            connectionprovider.getConnection().prepareStatement(sqlCommand).execute();
        }

    }
}
