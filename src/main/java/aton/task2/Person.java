package aton.task2;

import java.util.ArrayDeque;

public enum Person {
    Joey("Joey"), Chandler("Chandler"), Phoebe("Phoebe"), Monica("Monica"), Rachel("Rachel"),
    Ross("Ross"), None("#");
    private final String name;
    Person(String name){
        this.name=name;
    }

    public String getName() {
        return name;
    }
    public final ArrayDeque<String> phrases = new ArrayDeque<>();
    public void add(String phrase) {
        phrases.add(phrase);
    }
    public static synchronized void sayPhrase(Person person) {
        System.out.println(person.getName()+": "+person.phrases.peek());
        person.phrases.remove();
    }
}
