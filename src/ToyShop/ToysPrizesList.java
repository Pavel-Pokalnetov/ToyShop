package ToyShop;

import java.util.ArrayList;
import java.util.UUID;

public class ToysPrizesList {
    private ArrayList<UUID> toys_list;

    public ToysPrizesList() {
        this.clean();
    }

    public boolean add(UUID id) {
        if (this.toys_list.contains(id)) {
            return false;
        }
        this.toys_list.add(id);
        return true;
    }

    public boolean del(UUID id) {
        if (this.toys_list.size() > 0) {
            return toys_list.remove(id);
        }
        return false;
    }

    public int size() {
        return toys_list.size();
    }

    public UUID[] get_list() {
        int i = toys_list.size() - 1;
        UUID[] result = new UUID[i + 1];
        for (UUID id : toys_list) {
            result[i]=id;
            i--;
        }
        return result;
    }

    public boolean contains(UUID uuid) {
        return toys_list.contains(uuid);
    }

    public void clean() {
        this.toys_list = new ArrayList<>();
    }
}
