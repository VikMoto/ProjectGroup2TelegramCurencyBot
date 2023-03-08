package temp.fsm;

import java.util.Scanner;

public class StateMachineApp {
    public static void main(String[] args) {
        StateMachine fsm = new StateMachine();
//        fsm.addListener(((massage, time) -> {
//            System.out.println("Listener called");
//            System.out.println("massage = " + massage);
//            System.out.println("time = " + time);
//        }));

        Scanner scanner = new Scanner(System.in);
        while (true){
            String text = scanner.nextLine();
            fsm.handle(text);
        }

    }
}
