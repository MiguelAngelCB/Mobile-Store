package Thread;


public class CustomThread extends Thread{
    
    private StateEmail stateEmail;

    public CustomThread(StateEmail state) {
        this.stateEmail=state;
        this.start();
    }

    @Override
    public void run() {
        this.stateEmail.enviarEmail();
    }
}
