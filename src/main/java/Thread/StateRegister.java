package Thread;


public class StateRegister extends StateEmail{

    public StateRegister(String email){
        this.email=email;
    }

    @Override
    public void enviarEmail() {
        try {
            sendEmail.sendGenericEmail(this.email, "Registro", "te has registrado");
        } catch (Exception e) {
        }
    }
}
