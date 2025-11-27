package main;

import dao.DaoImplJDBC;

public class conector {
    public static void main(String[] args) {
        DaoImplJDBC dao = new DaoImplJDBC();
        dao.connect();
        dao.disconnect();
    }
}
