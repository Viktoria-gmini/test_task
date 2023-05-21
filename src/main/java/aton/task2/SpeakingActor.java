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
            if(task2.current==person) {
                Person.sayPhrase(person);//говорим фразу
                task2.goTONextPhrase();//меняем current
                if (task2.current!=Person.None) {
                    task2.threadMap.get(task2.current).setPriority(10);//ставим следующему актёру высокий приоритет
                    setPriority(5);//а текущему приоритет понижаем
                }
            } else {
                try {
                    Thread.yield();
                } catch (Exception ignored) {}
            }
        }while(!task2.order.isEmpty());
    }
}
