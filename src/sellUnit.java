import java.util.Objects;

public class sellUnit {
    String name;
    String date;
    String company;
    String quote;

    public sellUnit(String name, String date, String company, String quote) {
        this.name = name;
        this.date = date;
        this.company = company;
        this.quote = quote;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        sellUnit sellUnit = (sellUnit) o;
        return Objects.equals(name, sellUnit.name) && Objects.equals(date, sellUnit.date) && Objects.equals(company, sellUnit.company);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, date, company);
    }

    @Override
    public String toString() {
        return "sellUnit{" +
                "name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", company='" + company + '\'' +
                '}';
    }
}
