package main;

import dao.DaoImplMongoDB;

public class conector {
    public static void main(String[] args) {
    	DaoImplMongoDB dao = new DaoImplMongoDB();
        dao.connect();
        dao.disconnect();
    }
}
