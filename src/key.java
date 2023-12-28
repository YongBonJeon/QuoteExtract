import java.util.Objects;

public class key {
    String date;
    String company;

    public key(String date, String company) {
        this.date = date;
        this.company = company;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        key key = (key) o;
        return Objects.equals(date, key.date) && Objects.equals(company, key.company);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, company);
    }
}
