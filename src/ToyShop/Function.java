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
    public static void viewToy(Toy toy){
        System.out.printf("Название: %s\n",toy.get_name());
        System.out.printf("Вес: %d\n",toy.get_Cost());
    }

    public static void viewBox(ToysBox toysBox) {
        System.out.printf("Всего в коробке: %d игрушек:\n", toysBox.size());
        if (toysBox.size() < 1) {
            return;
        }
        HashMap<String, Integer> output = toysBox.view_count();
        int n=1;
        for (Map.Entry<String, Integer> entry : output.entrySet()) {
            String name = entry.getKey();
            Integer quantity = entry.getValue();
            Integer cost = toysBox.cost.get(name);
            System.out.printf("%d - %s, %d шт., вес: %d %%;\n",n++, name, quantity, cost);
        }
    }

    public static ToysPrizesList viewList(ToysPrizesList toysPrizesList, ToysBox toysBox) {
        System.out.printf("В списке на выдачу %d позиций:\n", toysPrizesList.size());
        int n = 1;
        for (UUID id : toysPrizesList.get_list()) {
            Toy toy = toysBox.get_toy_by_id(id);
            if (toy != null) {
                System.out.printf(" %d - %s;\n", n++, toy.get_name());
            }
        }
        return toysPrizesList;
    }

    public static ToysPrizesList make_List_Toys(ToysPrizesList toysPrizesList, ToysBox toysBox) throws InterruptedException {
        int quant;
        while (true) {
            System.out.printf("В коробке %d игрушек.\n", toysBox.size());
            try {
                quant = Integer.parseInt(KeyScanner.getText("сколько возьмем на подарки?: "));
            } catch (NumberFormatException e) {
                System.out.println("Неверный ввод. Отмена");
                return new ToysPrizesList();
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
                UUID id = toysBox.get_ID_by_cost(marker, toysPrizesList);
                if (id != null) {
                    toysPrizesList.add(id);
                    fl = false;
                }
            }
        }
        return toysPrizesList;
    }


    public static void get_Toy_to_Bayer(ToysPrizesList toysPrizesList, ToysBox toysBox) {
        if (toysPrizesList.size() == 0) {
            System.out.println("Нет списка для выдачи подарков");
            return;
        }
        UUID id = toysPrizesList.get_list()[0];
        Toy toy = toysBox.get_toy_by_id(id);
        System.out.println("Выдача подарка");
        System.out.printf("%s \n", toy.get_name());
        if (toysPrizesList.del(id)) {
            toysBox.del_by_id(id);
        }
        Function.viewBox(toysBox);
        Function.viewList(toysPrizesList, toysBox);
    }

    public static void clean_Box(ToysBox toysBox, ToysPrizesList toysPrizesList) {
        toysBox.clear();
        toysPrizesList.clean();
    }

    public static void save_List_Toys_to_File(ToysPrizesList toysPrizesList, ToysBox toysBox) {
        if (toysPrizesList.size()==0){
            System.out.println("Список еще не подготовлен. Выполните формирование списка на выдачу.");
            return;
        }
        String filename = KeyScanner.getText("Имя файла для записи(пусто для отмены): ");
        if (filename == null || filename.isBlank() || filename.isEmpty()) {
            System.out.println("Отмена записи");
        }
        StringBuilder text = new StringBuilder("Товары для выдачи в качестве подарка:\n");
        int n=1;
        for (UUID id : toysPrizesList.get_list()) {
            text.append(String.format("%d - %s\n",n++,toysBox.get_toy_by_id(id).get_name()));
        }
        try (FileWriter writer = new FileWriter(filename, false)) {

            writer.write(text.toString());
            writer.flush();
        } catch (IOException ex) {

            System.out.println(ex.getMessage());
        }
    }

    public static void edit_Toys_cost(ToysBox toysBox) {
        System.out.println("Измененме \"веса\" игрушки");
        Function.viewBox(toysBox);
        String text = KeyScanner.getText("Какую игрушку изменяем? Укажите номер игрушки для изменения, 0 или пусто для отмены: ");
        if ("0".equals(text)) return;
        int num = 0;
        try {
            num = Integer.parseInt(text);
        }catch (NumberFormatException e){
            System.out.println("Неверный ввод. Отмена");
            return;
        }
        if (num > toysBox.size()) {
            System.out.println("Такого номера нет. Отмена");
            return;
        }
        num--;
        Toy toy = toysBox.get_by_index(num);
        Function.viewToy(toy);
        int new_cost=0;
        try{
            new_cost = Integer.parseInt(KeyScanner.getText("Введите новую стоимость: "));
            if (new_cost>100 || new_cost<1){
                throw new NumberFormatException();
            }
        }catch (NumberFormatException e){
            System.out.println("Неверный ввод.\nОжидается целое число от 1 до 100.\nОтмена");
            return;
        }
        toy.set_cost(new_cost);
        toysBox.update_by_index(num,toy);
        toysBox.update_statistic();
        System.out.println("Изменения внесены.");
        Function.viewBox(toysBox);
    }
}