package ToyShop;

import java.util.UUID;

public class Toy {

    private String name;
    private int cost;
    private UUID id;


    public Toy(String name, int cost) {
        this.name = name;
        this.cost = cost;
        this.id = UUID.randomUUID();
    }

    public String get_name() {
        return name;
    }

    public int get_Cost() {
        return cost;
    }

    public UUID get_id() {
        return id;
    }

    public void set_cost(int newCost) {
        this.cost = newCost;
    }
}
