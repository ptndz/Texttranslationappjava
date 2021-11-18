public class history {
    public String from_to;
    public String from;
    public String to;

    public String getFrom() {
        return from;
    }

    public String getFrom_to() {
        return from_to;
    }

    public String getTo() {
        return to;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setFrom_to(String from_to) {
        this.from_to = from_to;
    }

    public void setTo(String to) {
        this.to = to;
    }
    @Override
    public String toString() {
        return "From to:"+this.from_to +", From:"+this.from+", To:"+this.to;
    }
}
