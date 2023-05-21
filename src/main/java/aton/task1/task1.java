package aton.task1;


public class task1 {
    public static void main(String[] args) {
        DAO.add(234678, "Иванов Иван Иванович", 203.5,false);
        DAO.add(152664, "Петров Иван Иванович", 425.5,false);
        DAO.add(152664, "Петров Колян Иванович", 203.5,false);

        DAO.toStart();
    }
}
