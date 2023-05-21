package aton.task1;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class DAO {
    static Scanner scanner;
    public static HashMap<Long,User> byAccount = new HashMap<>();
    public static HashMap<String, ArrayList<Long>> byName = new HashMap<>();
    public static HashMap<Double,ArrayList<Long>> byValue = new HashMap<>();

    public static void toStart(){
        System.out.println("All your notes:");
        byAccount.forEach((key, value) -> System.out.println(value.info()));
        System.out.println("What do you want to do with notes? write \"add\", \"find\",\"delete\" or \"change\"");
        try {
            scanner = new Scanner(System.in);
            switch (scanner.nextLine()) {
                case "add":
                    startToAdd();
                    break;
                case "find":
                    startToFind();
                    break;
                case "delete":
                    startToDelete();
                    System.out.println("account succesfully deleted");
                    break;
                case "change":
                    changeNote();
                    break;
                default:
                    throw new Exception();
            }
            toStart();
        } catch (Exception e){
            System.out.println("Wrong input");
            toStart();
        }
    }
    public static void info(ArrayList<User> users){
        StringBuilder answer = new StringBuilder();
        int i=0;
        for(User user : users) {
            i++;
            answer.append(user.info());
            if(i!=users.size()){
                answer.append("\n");
            }
        }
        System.out.println(answer);
    }
    private static void startToAdd() {
        System.out.println("Write id");
        long account = Long.parseLong(scanner.nextLine());
        System.out.println("Write name");
        String name = scanner.nextLine();
        System.out.println("Write value");
        double value = Double.parseDouble(scanner.nextLine());
        add(account, name, value,true);
        System.out.println("User was created");
    }

    public static void add(long account, String name, double value, boolean manually) {
        ArrayList<Long> users;
        User user = new User(account,name,value);
        if((byAccount.get(account)!=null)&&(manually)) {
            //не может быть двух одинаковых аккаунтов, так что, если аккаунт уже есть в словаре, то мы спрашиваем пользователя,
            //нужно ли заменить аккаунт
            System.out.println("Was found already created account with this id");
            System.out.println(byAccount.get(account).info());
            scanner= new Scanner(System.in);
            System.out.println("Do you want to delete this one and to create new? Write \"yes\" or \"no\"");
            switch (scanner.nextLine()){
                case "yes":
                    delete(account);
                    byAccount.put(account, user);
                    System.out.println("User was replaced");
                    break;
                case "no" :
                    System.out.println("Changes not implemented");
                    toStart();
                    break;
                default:
                    System.out.println("Wrong input");
                    toStart();
                    break;
            }
        } else if((byAccount.get(account)!=null)&&(!manually)) {
            byName.remove(byAccount.get(account).getName());
            byValue.remove(byAccount.get(account).getValue());
        }
        byAccount.put(account,user);
        users = new ArrayList<>();
        if(byName.get(name)!=null) {
            users = byName.get(name);
        }
        users.add(user.getAccount());
        byName.put(name, users);
        users = new ArrayList<>();
        if(byValue.get(value)!=null) {
            users = byValue.get(value);
        }
        users.add(user.getAccount());
        byValue.put(value, users);
    }

    public static void startToFind() throws Exception{
        System.out.println("What feature the note is to be found by? write \"account\", \"name\" and \"value\"");
        scanner = new Scanner(System.in);
        find(scanner.nextLine());
    }
    public static ArrayList<User> findAccounts(ArrayList<Long> refers){
        ArrayList<User> accs = new ArrayList<>();
        for(long refer : refers) {
            accs.add(byAccount.get(refer));
        }
        return accs;
    }//поиск по ссылкам
    public static ArrayList<User> find(String variable) throws Exception{
        ArrayList<User> users = new ArrayList<>();
        User user;
        switch (variable) {
            case "account":
                System.out.println("Input account in the format \"234678\"");
                user = byAccount.get(Long.parseLong(scanner.nextLine()));
                users.add(user);
                DAO.info(users);
                return users;
            case "name":
                System.out.println("Input name in the format \"Иванов Иван Иванович\"");
                users = findAccounts(byName.get(scanner.nextLine()));
                DAO.info(users);
                return users;
            case "value":
                System.out.println("Input value in the format \"203.5\"");
                users = findAccounts(byValue.get(Double.parseDouble(scanner.nextLine())));
                DAO.info(users);
                return users;
            default:
                throw new Exception();
        }
    }
    public static void startToDelete() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Write what feature should be used for deleting by: \"account\", \"name\", or \"value\"");
        try {
            //чтобы удалить по account надо сначала удалить связанные с этим аккаунтом ссылки в хэшмапах, и только потом
            //удалить акк в основном
            ArrayList<User> users=find(scanner.nextLine());
            long account;
            if(users.size()>1){
                System.out.println("Please input which id you need to delete");
                account = Long.parseLong(scanner.nextLine());
            } else {
                account = users.get(0).getAccount();
            }
            delete(account);
        } catch (Exception e){
            System.out.println("Wrong input");
        }
    }
    public static void delete(long account) {
        User user = byAccount.get(account);
        long found = 0;
        for(long i : byName.get(user.getName())){
            if (i==user.getAccount()){
                found = user.getAccount();
                break;
            }
        }
        if (found!=0) {
            ArrayList<Long> changed = byName.get(user.getName());
            changed.remove(found);
            if(!changed.isEmpty()){
                byName.put(user.getName(), changed);
            } else {
                byName.remove(user.getName());
            }
        }
        for(long i : byValue.get(user.getValue())){
            if (i==user.getAccount()){
                found = user.getAccount();
                break;
            }
        }
        if(found!=0){
            ArrayList<Long> changed = byValue.get(user.getValue());
            changed.remove(found);
            if(!changed.isEmpty()){
                byValue.put(user.getValue(), changed);
            } else {
                byValue.remove(user.getValue());
            }
        }
        byAccount.remove(account);
    }
    public static void changeNote() throws Exception{
        System.out.println("What feature the note is to be found by? write \"account\", \"name\" and \"value\"");
        ArrayList<User> users = find(scanner.nextLine());
        long account;
        User user;
        if (users.size()>1){
            System.out.println("Write account you want to change? Write id");
            account = Long.parseLong(scanner.nextLine());
            user = byAccount.get(account);
        } else {
            user = users.get(0);
        }
        System.out.println("Here it is");
        System.out.println(user.info());
        System.out.println("Ok, what do you want to change for this note? write \"account\", \"name\" and \"value\"");
        switch (scanner.nextLine()){
            case "account":
                System.out.println("Write new id");
                long newAccount = Long.parseLong(scanner.nextLine());
                delete(user.getAccount());
                add(newAccount,user.getName(),user.getValue(),false);
                System.out.println("The note has been changed and here it is");
                System.out.println(byAccount.get(newAccount).info());
                break;
            case "name":
                System.out.println("Write new name");
                String newName = scanner.nextLine();
                delete(user.getAccount());
                add(user.getAccount(), newName,user.getValue(),false);
                System.out.println("The note has been changed and here it is");
                System.out.println(byAccount.get(user.getAccount()).info());
                break;
            case "value":
                System.out.println("Write new value");
                double newValue = Double.parseDouble(scanner.nextLine());
                delete(user.getAccount());
                add(user.getAccount(), user.getName(),newValue,false);
                System.out.println("The note has been changed and here it is");
                System.out.println(byAccount.get(user.getAccount()).info());
                break;
            default:
                throw new Exception();
        }
        toStart();
    }
}
