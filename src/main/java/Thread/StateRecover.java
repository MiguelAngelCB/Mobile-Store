package Thread;

public class StateRecover extends StateEmail{
    
    public StateRecover(String email){
        this.email=email;
    }

    @Override
    public void enviarEmail() {
        try {
            sendEmail.sendGenericEmail(this.email, "Has recuperado tu contraseña", "te hemos cambiado la password");
        } catch (Exception e) {
        }
    }

}
