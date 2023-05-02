package ToyShop;

import java.util.*;

public class ToysBox {
    ArrayList<Toy> toys_list;
    HashMap<String, Integer> count = new HashMap<>();
    HashMap<String, Integer> cost = new HashMap<>();

    public ToysBox() {
        this.clear();

    }

    public void clear() {
        toys_list = new ArrayList<>();
        count = new HashMap<>();
        cost = new HashMap<>();
    }

    public Toy get_toy_by_id(UUID id) {
        for (Toy toy : this.toys_list) {
            if (toy.get_id().equals(id)) {
                return toy;
            }
        }
        return null;
    }

    public void add(Toy toy) {
        toys_list.add(toy);
        Collections.shuffle(toys_list);
        count.merge(toy.get_name(), 1, Integer::sum);
        cost.put(toy.get_name(), toy.get_Cost());

    }

    public int size() {
        return this.toys_list.size();
    }

    public HashMap<String, Integer> getCount() {
        return count;
    }


    public UUID get_ID_by_cost(int marker, ToysPrizesList toysPrizesList) {
        for (Toy toy : toys_list) {
            int cost = toy.get_Cost();
            if (cost < marker) continue;
            UUID id = toy.get_id();
            if (!toysPrizesList.contains(id)) return id;
        }
        return null;
    }

    public void del_by_id(UUID id) {
        Iterator<Toy> iterator = this.toys_list.iterator();
        while (iterator.hasNext()) {
            Toy toy = iterator.next();
            if (toy.get_id().equals(id)) {
                this.count.put(toy.get_name(), this.count.get(toy.get_name()) - 1);
                if (this.count.get(toy.get_name()) == 0) {
                    this.count.remove(toy.get_name());
                }
                iterator.remove();
                return;
            }
        }
        this.update_statistic();
    }

    public Toy get_by_index(int i) {
        return this.toys_list.get(i);
    }

    public void update_by_index(int i, Toy toy) {
        Function.viewToy(toy);
        this.toys_list.add(toy);
        this.toys_list.remove(i);
    }

    public void update_statistic() {
        this.cost = new HashMap<>();
        this.count = new HashMap<>();
        for (Toy toy : toys_list) {
            count.merge(toy.get_name(), 1, Integer::sum);
            cost.put(toy.get_name(), toy.get_Cost());
        }

    }

    public ArrayList<Toy> get_toys_list() {
        return this.toys_list;
    }
}
