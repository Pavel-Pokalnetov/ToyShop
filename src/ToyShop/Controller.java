package ToyShop;

public class Controller {
    private ToysPrizesList toysPrizesList;
    private ToysBox toysBox;

    public Controller() throws InterruptedException {
        toysBox = new ToysBox();
        toysPrizesList = new ToysPrizesList();
        main_MenuLoop();
    }

    private void main_MenuLoop() throws InterruptedException {
        // главное меню
        Menu menu = new Menu();
        menu.add("1", "Посмотреть игрушки в призовой коробке");
        menu.add("2", "Посмотреть список игрушек на выдачу");
        menu.add("3", "Добавить игрушки в коробку");
        menu.add("4", "Изменить \"вес\" игрушки");
        menu.add("5", "Сформировать список на выдачу");
        menu.add("6", "Выдать игрушку");
        menu.add("7", "Опустошить призовую коробку");
        menu.add("8", "Записать список подарков в файл");
        menu.add("Q", "Выход");
        while (true) {
            switch (menu.run()) {
                case "1":
                    Function.viewBox(this.toysBox);
                    break;
                case "2":
                    Function.viewList(this.toysPrizesList, this.toysBox);
                    break;
                case "3":
                    Function.add_Toys_to_Box(this.toysBox);
                    break;
                case "4":
                    Function.edit_Toys_cost(this.toysBox);
                    break;
                case "5":
                    this.toysPrizesList.clean();
                    Function.make_List_Toys(this.toysPrizesList, this.toysBox);
                    Function.viewList(this.toysPrizesList, this.toysBox);
                    break;
                case "6":
                    Function.get_Toy_to_Bayer(this.toysPrizesList, this.toysBox);
                    break;
                case "7":
                    Function.clean_Box(this.toysBox, this.toysPrizesList);
                    break;
                case "8":
                    Function.save_List_Toys_to_File(this.toysPrizesList, this.toysBox);
                    break;
                case "Q":
                    return;
            }
            System.out.println();
        }
    }


}
