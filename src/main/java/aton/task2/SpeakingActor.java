package aton.task2;

public class SpeakingActor extends Thread{
    private final Person person;
    SpeakingActor(Person person){
        this.person = person;
    }
    @Override
    public void run()
    {
        do{
            //если текущая фраза принадлежит именно этому актёру, он её читает
            if(Task2.current==person) {
                Person.sayPhrase(person);//говорим фразу
                Task2.goTONextPhrase();//меняем current
                if (Task2.current!=Person.None) {
                    Task2.threadMap.get(Task2.current).setPriority(10);//ставим следующему актёру высокий приоритет
                    setPriority(5);//а текущему приоритет понижаем
                }
            } else {
                try {
                    Thread.yield();
                } catch (Exception ignored) {}
            }
        }while(!Task2.order.isEmpty());
    }
}
