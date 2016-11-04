import models.Book;
import store.BookRepository;

import java.sql.SQLException;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) throws SQLException {

        BookRepository repository = new BookRepository();
        Scanner sc = new Scanner(System.in);
        String name = "Undefigned";
        String author = "Undefigned";
        System.out.println("Hi!");
        while(true) {
            writeOptions();
            switch (sc.nextInt()) {
                case 1:
                    repository.selectAllBooks();
                    repository.writeSelectedBooks();
                    break;
                case 2:
                    System.out.println("for adding book enter name:");
                    name = sc.next();
                    System.out.println("enter author:");
                    author = sc.next();
                    Book b = new Book(name, author);
                    repository.addBook(b);
                    break;
                case 3:
                    System.out.println("for deleting book enter name:");
                    name = sc.next();
                    repository.deleteBook(name);
                    break;
                case 4:
                    System.out.println("for updating book enter new name:");
                    String newName = sc.next();
                    System.out.println("enter author :");
                    author = sc.next();
                    repository.updateBookName(newName, author);

                    break;
                case 5:
                    System.out.println("input name: ");
                    name = sc.next();
                    repository.selectBooksByName(name);
                    repository.writeSelectedBooks();
                    break;
                case 6:
                    System.out.println("bye!");
                    return;
                default:
                    System.out.println("you inputed wrong data");
            }
            System.out.println("return to menu ?(yes/no)");
            if("no".equals(sc.next())){
                System.out.println("bye!");
                return;
            }
        }
    }
    public static void writeOptions(){
        System.out.println("-----------menu-----------");
        System.out.println("1 - select all books;");
        System.out.println("2 - add new book;");
        System.out.println("3 - delete book;");
        System.out.println("4 - edit name of book;");
        System.out.println("5 - select books by name ;");
        System.out.println("6 - exit.");
        System.out.println("--------------------------");

    }
}

