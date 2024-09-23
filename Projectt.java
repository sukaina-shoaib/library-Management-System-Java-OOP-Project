/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.projectt;

/**
 *
 * 
 */
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class Library {
    public String authorName;
    public String bookName;

    Library(String authorName, String bookName) {
        this.authorName = authorName;
        this.bookName = bookName;
    }

    public void display() {
        System.out.println("Book Name: " + this.bookName);
        System.out.println("Author Name: " + this.authorName);
    }
}

class FictionalBook extends Library {
    public FictionalBook(String authorName, String bookName) {
        super(authorName, bookName);
    }

    public void display() {
        System.out.println("Fictional Book:");
        super.display();
    }
}

class EducationalBook extends Library {
    public EducationalBook(String authorName, String bookName) {
        super(authorName, bookName);
    }

    public void display() {
        System.out.println("Educational Book:");
        super.display();
    }
}

class HorrorBook extends Library {
    public HorrorBook(String authorName, String bookName) {
        super(authorName, bookName);
    }

    public void display() {
        System.out.println("Horror Book:");
        super.display();
    }
}

class ReligionBook extends Library {
    public ReligionBook(String authorName, String bookName) {
        super(authorName, bookName);
    }

    public void display() {
        System.out.println("Religion Book:");
        super.display();
    }
}

class Membership {
    int id;
    String name;

    public Membership(int id, String name) {
        this.id = id;
        this.name = name;
    }
}


public class Projectt extends JFrame {
    private ArrayList<Library> bookList = new ArrayList<>();
    private ArrayList<Membership> memberships = new ArrayList<>();
    private File bookFile = new File("books.txt");

    public Projectt() {
        JFrame f = new JFrame("LIBRARY MANAGEMENT SYSTEM");
        try {
            String imagePath = "image\\image.jpg"; 
            Image backgroundImage = new ImageIcon(imagePath).getImage();

            JPanel backgroundPanel = new JPanel() {
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            };

            // Set layout for background panel
            backgroundPanel.setLayout(new BorderLayout());

            // Add components to the background panel instead of directly to the frame
            JLabel l1 = new JLabel("WELCOME TO LIBRARY MANAGEMENT SYSTEM", SwingConstants.CENTER);
            l1.setFont(new Font("Serif", Font.BOLD, 24));
            backgroundPanel.add(l1, BorderLayout.NORTH);
            l1.setForeground(Color.white);
            // Add buttons, etc., to the background panel as well...

            // Add the background panel to the JFrame
            f.setContentPane(backgroundPanel);
        } catch (Exception e) {
            // Handle exceptions related to image loading
            e.printStackTrace();
        }

        // Create buttons
        JButton b1 = new JButton("fictional book");
        JButton b2 = new JButton("educational book");
        JButton b3 = new JButton("horror book");
        JButton b4 = new JButton("religious book");
        JButton b5 = new JButton("checkout");
        JButton b6 = new JButton("exit");
        Color c1 = new Color(139,69,19);  
         f.setBackground(c1);
  
        JButton[] buttons = {b1, b2, b3, b4, b5, b6};
        for (JButton button : buttons) {
            button.setBackground(c1);
            button.setForeground(Color.white);
            button.setPreferredSize(new Dimension(150, 70));
        }

        // Layout setsup
        f.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0; 
        gbc.gridy = 1;

        for (JButton button : buttons) {
            f.add(button, gbc);
            gbc.gridy++;
        }

        f.setSize(1600,1500);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        b1.addActionListener(e -> addBook("Fictional"));
        b2.addActionListener(e -> addBook("Educational"));
        b3.addActionListener(e -> addBook("Horror"));
        b4.addActionListener(e -> addBook("Religious"));
        b5.addActionListener(e -> checkout());
        b6.addActionListener(e -> System.exit(0));

        initializeData();
    }

    private void addBook(String category) {
        String bookName = JOptionPane.showInputDialog(this, "Enter book name:");
        String authorName = JOptionPane.showInputDialog(this, "Enter author name:");

        if (bookName == null || authorName == null || bookName.isEmpty() || authorName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Book name and author name cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean found = false;
        for (Library book : bookList) {
            if (bookName.equals(book.bookName) && authorName.equals(book.authorName)) {
                found = true;
                break;
            }
        }

        if (found) {
            try (FileWriter writer = new FileWriter(bookFile, true)) {
                writer.write("Book Name: " + bookName + ", Author Name: " + authorName + "\n");
                JOptionPane.showMessageDialog(this, "The book '" + bookName + "' by " + authorName + " is added.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "File cannot be written.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "The book '" + bookName + "' by " + authorName + " is not available. Try a different book.", "Error",JOptionPane.ERROR_MESSAGE);
        }
    }

    private void checkout() {
        String idStr = JOptionPane.showInputDialog(this, "Enter your library ID:");
        String name = JOptionPane.showInputDialog(this, "Enter your name:");
    
        if (idStr == null || name == null || idStr.isEmpty() || name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "ID and name cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        int id = Integer.parseInt(idStr);
        boolean correct = false;
        for (Membership member : memberships) {
            if (member.id == id && member.name.equals(name)) {
                correct = true;
                break;
            }
        }
    
        if (correct) {
            String receipt = "Member found\n";
            receipt += "-----------------------\n";
            receipt += "    Library Card       \n";
            receipt += "-----------------------\n";
            receipt += "Library ID: " + id + "\n";
            receipt += "Books checked out:\n";
    
            // Read the books from the file
            try (Scanner reader = new Scanner(bookFile)) {
                while (reader.hasNextLine()) {
                    String line = reader.nextLine();
                    receipt += line + "\n";
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "File cannot be read.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
    
            receipt += "Have a nice day:)\n";
            receipt += "----------------------\n";
    
            // Show the receipt
            JOptionPane.showMessageDialog(this, receipt, "Checkout", JOptionPane.INFORMATION_MESSAGE);
    
            // Write the library card to the file
            try (FileWriter writer = new FileWriter(bookFile, true)) {
                writer.write("Library ID: " + id + ", Name: " + name + "\n");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "File cannot be written.", "Error", JOptionPane.ERROR_MESSAGE);
            }
    
            // Clear the book file after reading
            try (FileWriter writer = new FileWriter(bookFile)) {
                writer.write("");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "File cannot be cleared.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Member not found.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    

    private void initializeData() {
        bookList.add(new FictionalBook("xyz", "abc"));
        bookList.add(new FictionalBook("Erin Morgenstern", "The Night Circus"));
        bookList.add(new FictionalBook("Donna Tartt", "The Goldfinch"));
        bookList.add(new EducationalBook("Daniel Kahneman", "Thinking, Fast and Slow"));
        bookList.add(new EducationalBook("Stephen Hawking", "A Brief History of Time"));
        bookList.add(new EducationalBook("Thomas S. Kuhn", "The Structure of Scientific Revolutions"));
        bookList.add(new HorrorBook("Bram Stoker", "Dracula"));
        bookList.add(new HorrorBook("Mary Shelley", "Frankenstein"));
        bookList.add(new HorrorBook("Stephen King", "The Shining"));
        bookList.add(new ReligionBook("Karen Armstrong", "The History of God"));
        bookList.add(new ReligionBook("William James", "The Varieties of Religious Experience"));
        bookList.add(new ReligionBook("Lee Strobel", "The Case for Christ"));

        memberships.add(new Membership(1, "Sukaina"));
        memberships.add(new Membership(2, "Habiba"));
        memberships.add(new Membership(3, "Azhar"));
        memberships.add(new Membership(4, "Abdullah"));

        try {
            bookFile.createNewFile();
        } catch (IOException e) {
            System.out.println("File not created");
        }
    }

    public static void main(String[] args) {
        new Projectt();
    }
}