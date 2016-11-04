package store;

import models.Book;
import service.Settings;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class BookRepository {
    private final Connection connection;
    List<Book> books = new ArrayList<Book>();

    public BookRepository() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        final Settings settings = Settings.getInstanse();
        try {
            this.connection = DriverManager.getConnection(settings.value("jdbc.url"), settings.value("jdbc.username"), settings.value("jdbc.password"));
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
    public void addBook(Book b) throws SQLException {

        final PreparedStatement uStatement = this.connection.prepareStatement("insert into books (book_name, book_author)"+
                "values ('"+ b.getBook_name()+ "','"+b.getBook_author()+"')");
        uStatement.executeUpdate();
        uStatement.close();
        System.out.println("book "+b.getBook_name()+" "+b.getBook_author()+" was added");

    }
    //--------------------------------------------------------------
    public void deleteBook(String name) throws SQLException {
        books.clear();
        selectBooksByName(name);
        int id = chooseBookId();
        if(id!=-1) {
            final PreparedStatement statement = this.connection.prepareStatement("delete from books " +
                    "where book_id = " + id);
            statement.executeUpdate();
            statement.close();
            System.out.println("book " + name + " was deleted");
        }

    }
    //--------------------------------------------------------------
    public int chooseBookId(){
        int id = -1;
        if(books.size() > 1){
            System.out.println("we have few books with such name please choose one by typing a number of book: ");
            writeSelectedBooks();
            System.out.println("choose number: ");
            Scanner sc = new Scanner(System.in);
            int index = sc.nextInt()-1;
            id = books.get(index).getBook_id();
        }else if(books.size()==0){
            System.out.println("we dont have this book!");
        }else{
            id = books.get(0).getBook_id();
        }
        return id;
    }
    //------------------------------------------------------------------
    public void updateBookName(String newBookName, String bookAuthor) throws SQLException {
        books.clear();
        selectBooksByAuthor(bookAuthor);
        int id = chooseBookId();
        if(id!=-1) {
            final PreparedStatement statement = this.connection.prepareStatement("update books " +
                    "set book_name = '" + newBookName + "' where book_id = " + id);
            statement.executeUpdate();
            statement.close();
            System.out.println("book was updated to " + newBookName + " " + bookAuthor);
        }

    }

//------------------------------------------------------

    public void selectAllBooks(){
        books.clear();
        try{
            final Statement statement = this.connection.createStatement();
            final ResultSet rs = statement.executeQuery("select * from books order by book_name");
            while (rs.next()){
                Book book = new Book();
                book.setBook_id(rs.getInt(3));
                book.setBook_name(rs.getString(1));
                book.setBook_author(rs.getString(2));
                books.add(book);

            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    //---------------------------------------
    public void selectBooksByName(String name){
        books.clear();
        try{
            final Statement statement = this.connection.createStatement();
            final ResultSet rs = statement.executeQuery("select * from books where book_name='"+name+"' order by " +
                    "book_name");
            while (rs.next()){
                Book book = new Book();
                book.setBook_id(rs.getInt(3));
                book.setBook_name(rs.getString(1));
                book.setBook_author(rs.getString(2));
                books.add(book);

            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    //---------------------------------------
    public void selectBooksByAuthor(String author){
        books.clear();
        try{
            final Statement statement = this.connection.createStatement();
            final ResultSet rs = statement.executeQuery("select * from books where book_author='"+author+"' order by " +
                    "book_name ");
            while (rs.next()){
                Book book = new Book();
                book.setBook_id(rs.getInt(3));
                book.setBook_name(rs.getString(1));
                book.setBook_author(rs.getString(2));
                books.add(book);

            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    //---------------------------------------

    public void writeSelectedBooks(){
        System.out.println("our books: ");
        int k = 1;
        for (Book i: books) {
            System.out.println(k+" "+i.getBook_name()+" "+i.getBook_author());
            k++;
        }

    }




}