package Thread;


public class StateBlock extends StateEmail{

    public StateBlock(String email){
        this.email=email;
    }

    @Override
    public void enviarEmail() {
        try {
            sendEmail.sendBlockEmail(this.email);
        } catch (Exception e) {
        }
    }
    

}
