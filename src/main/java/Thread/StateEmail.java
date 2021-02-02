package Thread;

import com.example.demo.util.SendEmail;

public abstract class StateEmail {
    protected SendEmail sendEmail=new SendEmail();
    protected String email;
    
    public abstract void enviarEmail();
}
