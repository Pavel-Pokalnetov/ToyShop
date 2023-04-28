package ToyShop;

public class Controller {
    private ToysList toysList;
    private  ToysBox toysBox;

    public Controller() throws InterruptedException {
        toysBox = new ToysBox();
        toysList = new ToysList();
        main_MenuLoop();
    }

    private void main_MenuLoop() throws InterruptedException {
        // главное меню
        Menu menu = new Menu();
        menu.add("1", "Посмотреть игрушки в призовой коробке");
        menu.add("2", "Посмотреть список игрушек на выдачу");
        menu.add("3", "Добавить игрушки в коробку");
        menu.add("4", "Сформировать список на выдачу");
        menu.add("5", "Выдать игрушку");
        menu.add("6", "Опустошить призовую коробку");
        menu.add("7", "Записать список подарков в файл");
        menu.add("Q", "Выход");
        while (true) {
            switch (menu.run()) {
                case "1":
                    Function.viewBox(this.toysBox);
                    break;
                case "2":
                    Function.viewList(this.toysList,this.toysBox);
                    break;
                case "3":
                    Function.add_Toys_to_Box(this.toysBox);
                    break;
                case "4":
                    this.toysList.clean();
                    Function.make_List_Toys(this.toysList,this.toysBox);
                    break;
                case "5":
                    Function.get_Toy_to_Bayer(this.toysList,this.toysBox);
                    break;
                case "6":
                    Function.clean_Box(this.toysBox,this.toysList);
                    break;
                case "7":
                    Function.save_List_Toys_to_File(this.toysList,this.toysBox);
                    break;
                case "Q":
                    System.out.println("Удачи!");
                    return;
            }
            System.out.println();
        }
    }


}
