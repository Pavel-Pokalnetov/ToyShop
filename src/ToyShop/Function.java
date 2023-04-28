package ToyShop;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Function {

    public static void add_Toys_to_Box(ToysBox box) {
        System.out.println("Добавляем игрушки в коробку");
        System.out.println("для отмены введите пустую строку в любое поле");
        String name = null;
        int cost = 0;
        int quantity = 0;
        try {
            name = KeyScanner.getText("Название игрушки: ");
            if (name.isEmpty() || name.isBlank()) {
                throw new Exception();
            }

            cost = Integer.parseInt(KeyScanner.getText("вес в выдаче %: "));
            if (cost > 100 || cost <= 0) {
                throw new NumberFormatException();
            }
            quantity = Integer.parseInt(KeyScanner.getText("количество: "));
            if (quantity <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            System.out.println("Неверный ввод. Отмена.");
        } catch (Exception e) {
            System.out.println("Отмена");
            return;
        }
        while (quantity > 0) {
            box.add(new Toy(name, cost));
            quantity--;
        }
        System.out.println("Игрушки добавлены");
        System.out.printf("Всего в коробке: %d игрушек\n\n", box.size());

    }

    public static void viewBox(ToysBox toysBox) {
        System.out.printf("Всего в коробке: %d игрушек:\n", toysBox.size());
        if (toysBox.size() < 1) {
            return;
        }
        HashMap<String, Integer> output = toysBox.view_count();
        for (Map.Entry<String, Integer> entry : output.entrySet()) {
            String name = entry.getKey();
            Integer quantity = entry.getValue();
            Integer cost = toysBox.cost.get(name);
            System.out.printf(" - %s, %d шт., вес: %d %%;\n", name, quantity, cost);
        }
    }

    public static ToysList viewList(ToysList toysList, ToysBox toysBox) {
        System.out.printf("В списке на выдачу %d позиций:\n", toysList.size());
        int n = 1;
        for (UUID id : toysList.get_list()) {
            Toy toy = toysBox.get_toy_by_id(id);
            if (toy != null) {
                System.out.printf(" %d - %s;\n", n++, toy.get_name());
            }
        }
        return toysList;
    }

    public static ToysList make_List_Toys(ToysList toysList, ToysBox toysBox) throws InterruptedException {
        int quant;
        while (true) {
            System.out.printf("В коробке %d игрушек.\n", toysBox.size());
            try {
                quant = Integer.parseInt(KeyScanner.getText("сколько возьмем на подарки?: "));
            } catch (NumberFormatException e) {
                System.out.println("Неверный ввод. Отмена");
                return new ToysList();
            }
            if (quant == 0) {
                System.out.println("Отмена");
            } else if (quant > toysBox.size()) {
                System.out.println("У нас нет столько игрушек!");
                Thread.sleep(3000);
                continue;
            }
            break;
        }
        Random rnd = new Random();
        for (int i = 0; i < quant; i++) {
            boolean fl = true;
            while (fl) {
                int marker = rnd.nextInt(99) + 1;
                UUID id = toysBox.get_ID_by_cost(marker, toysList);
                if (id != null) {
                    toysList.add(id);
                    fl = false;
                }
            }
        }
        return toysList;
    }


    public static void get_Toy_to_Bayer(ToysList toysList, ToysBox toysBox) {
        if (toysList.size() == 0) {
            System.out.println("Нет списка для выдачи подарков");
            return;
        }
        UUID id = toysList.get_list()[0];
        Toy toy = toysBox.get_toy_by_id(id);
        System.out.println("Выдача подарка");
        System.out.printf("%s \n", toy.get_name());
        if (toysList.del(id)) {
            toysBox.del_by_id(id);
        }
        Function.viewBox(toysBox);
        Function.viewList(toysList, toysBox);
    }

    public static void clean_Box(ToysBox toysBox, ToysList toysList) {
        toysBox.clear();
        toysList.clean();
    }

    public static void save_List_Toys_to_File(ToysList toysList, ToysBox toysBox) {
        if (toysList.size()==0){
            System.out.println("Список еще не подготовлен. Выполните формирование списка на выдачу.");
            return;
        }
        String filename = KeyScanner.getText("Имя файла для записи(пусто для отмены): ");
        if (filename == null || filename.isBlank() || filename.isEmpty()) {
            System.out.println("Отмена записи");
        }
        StringBuilder text = new StringBuilder("Товары для выдачи в качестве подарка:\n");
        int n=1;
        for (UUID id : toysList.get_list()) {
            text.append(String.format("%d - %s\n",n++,toysBox.get_toy_by_id(id).get_name()));
        }
        try (FileWriter writer = new FileWriter(filename, false)) {

            writer.write(text.toString());
            writer.flush();
        } catch (IOException ex) {

            System.out.println(ex.getMessage());
        }
    }
}