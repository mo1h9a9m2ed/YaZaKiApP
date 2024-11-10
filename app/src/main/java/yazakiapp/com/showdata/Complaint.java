package yazakiapp.com.showdata;

public class Complaint {
    private String fileName;
    private String reason;
    private String message;

    public Complaint(String fileName, String reason, String message) {
        this.fileName = fileName;
        this.reason = reason;
        this.message = message;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
