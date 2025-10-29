public class Company {

    public Company() {
    }
//  public Gcompany g;
//  public Hcompany h;
//  public Scompany s;

    /**
     * @param []args 
     * @return
     */
    public static void main(String []args) {
        Gcompany g = new Gcompany();
        Hcompany h = new Hcompany();
        Scompany s = new Scompany();
        g.name = "울란도";
        g.number = 2021;
        System.out.println("Car name: " + g.name + ", Car number: " + g.number);
        System.out.println("Car year: " + g.getYear() + ", Car local number: " + g.getLocal_number());

        h.name = "소나타";
        h.price = 10000000;
        System.out.println("Car name: " + h.name + ", Car price: " + h.price);
        System.out.println("Car year: " + h.getYear() + ", Car navigate price: " + h.getNavigate());

        s.name = "QMS";
        s.cc = 2500;
        System.out.println("Car name: " + s.name + ", Car cc: " + s.cc);
        System.out.println("Car year: " + s.getYear() + ", Car speed: " + s.getSpeed());
    }

}