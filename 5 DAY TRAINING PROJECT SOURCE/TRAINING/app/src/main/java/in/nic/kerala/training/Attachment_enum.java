package in.nic.kerala.training;

public enum Attachment_enum {
    select("0"),
    Document("1"),
    Photo("2"),
    VideoAudio("3");

    private final String value;

    Attachment_enum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public String getAttachment() {
        return this.value;
    }

}