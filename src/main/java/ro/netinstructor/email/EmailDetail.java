package ro.netinstructor.email;

public class EmailDetail {
    private String recipient;
    private String msgBody;
    private String subject;

    public EmailDetail() {
    }

    public EmailDetail(final String recipient, final String msgBody, final String subject) {
        this.recipient = recipient;
        this.msgBody = msgBody;
        this.subject = subject;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getMsgBody() {
        return msgBody;
    }

    /**
     * Getter method for subject field
     * 
     * @return subject
     */
    public String getSubject() {
        return subject;
    }
}
