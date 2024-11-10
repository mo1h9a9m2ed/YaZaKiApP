package yazakiapp.com;

public class FileRequestModel {
    private String fileName;
    private String reason;
    private String message;

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    // Getters
    public String getFileName() {
        return fileName;
    }

    public String getReason() {
        return reason;
    }

    public String getMessage() {
        return message;
    }
}
