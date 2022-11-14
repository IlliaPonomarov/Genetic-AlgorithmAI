package org.example;

public interface OsUtils {
    static void clean () {
        try {
            Runtime.getRuntime().exec("cls");
        }catch (final Exception e){
            e.printStackTrace();
        }
    }
}
