package aton.task2;

import java.io.*;
import java.util.*;


public class Task2 {
    static Map<Person,Thread> threadMap = new HashMap<>();
    static List<Person> persons = Arrays.asList(Person.values()).subList(0, 6);
    static volatile Person current = Person.None;
    static volatile Queue<Person> order = new ArrayDeque<>();
    static void goTONextPhrase(){
        order.remove();
        if(!order.isEmpty()){
            current = order.peek();
        } else {
            current = Person.None;
        }
    }
    public static void main(String[] args) throws IOException, InterruptedException {
        //читаем файл
        BufferedReader reader = new BufferedReader(new FileReader("resources_task2/piece.txt"));
        //переменная, в которую будет определяться реплика после двоеточия
        String scan  =  " ";
        while (scan != null) {
            for(Person person:persons) {
                if(scan.startsWith(person.getName())){
                    int index = person.getName().length()+2;
                    //добавляем реплику к соответствующему персонажу
                    person.add(scan.substring(index));
                    //добавляем реплику в очередь реплик, которые надо сказать
                    order.add(person);
                    //покидаем цикл, так как мыы уже определили, какой персонаж говорит реплику
                    break;
                }
            }
            scan = reader.readLine();
        }
        //запускаем потоки для каждого из персонажей поток "говорения"
        for (Person person:persons) {
            SpeakingActor tPerson = new SpeakingActor(person);
            //каждый поток добавлен в карту
            threadMap.put(person,tPerson);
            tPerson.setPriority(1);
            tPerson.start();
        }
        //на первую реплику задала наивысший уровень приоритета для соответственного персонажа
        threadMap.get(order.peek()).setPriority(10);
        current = order.peek();
        //уступаем место другим потокам, ждём, когда они завершатся
        for (Thread thread:threadMap.values()) {
            thread.join();
        }

    }


}
