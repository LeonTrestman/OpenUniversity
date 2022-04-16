/*Represents controller for Phone Book App */

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.io.*;

public class PhoneBookController {

    public static final String NAME_IS_EMPTY_ERR = "Invalid input,name is empty";
    public static final String INVALID_INPUT_ERR = "Invalid input, name is empty or phone number is not numeric";
    public static final String PHONE_BOOK_FILE_NAME = "phoneBook.ser";
    @FXML
    private TextField nameField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextArea contactsTextArea;

    private PhoneBook phoneBook;
    private String msg;
    public File phoneBookFile;


    @FXML
    public void initialize() {
        try {
            phoneBook = new PhoneBook();
            contactsTextArea.setText("");
            phoneBookFile = new File(PHONE_BOOK_FILE_NAME);
            phoneBookFile.createNewFile();
        } catch (IOException e) {
            popMsg(e.getMessage());
        }
    }

    @FXML
    //Executes a click for the corresponding button
    //if thrown exception is caught , pops a message with the exception message
    private void onBtnClick(ActionEvent event) {
        String input = ((Button) event.getSource()).getText();

        try {
            switch (input) {
                case "Add":
                    onAddClick();
                    break;
                case "Update phone":
                    onUpdatePhoneClick();
                    break;
                case "Remove":
                    onRemoveClick();
                    break;
                case "Search by name":
                    onSearchByNameClick();
                    break;
                case "Load from file":
                    onLoadFromFileClick();
                    break;
                case "Save to file":
                    onSaveToFileClick();
                    break;

                default:
                    msg = "Error, unknown button";
            }
            updateView();
            popMsg(msg);

            //In case of exception we will pop message to user
        } catch (Exception e) {
            popMsg(e.getMessage());
        }
    }

    //Updates existing contact in phone book , updates msg with result
    private void onUpdatePhoneClick() {
        if (!isValidInput())
            setMsg(INVALID_INPUT_ERR);
        else {
            Contact contact = new Contact(nameField.getText(), phoneField.getText());
            if (!phoneBook.updateContact(contact))
                setMsg("Error, " + contact.getName() + " not found");
            else
                setMsg(contact.getName() + " updated successfully");
        }
    }

    //Returns phone number by valid name , else returns matching error message
    private void onSearchByNameClick() {
        String name = nameField.getText();

        if (name.isEmpty())
            setMsg(NAME_IS_EMPTY_ERR);
        else {
            String contactPhone = phoneBook.getPhoneNumber(name);
            if (contactPhone != null)
                setMsg(name + " phone number is " + contactPhone);
            else
                setMsg(name + " not found");
        }
    }

    //sets the msg
    public void setMsg(String msg) {
        this.msg = msg;
    }

    //saves non-empty phone book to file and pops message to user with result
    private void onSaveToFileClick() throws IOException {
        if (phoneBook.saveContactsToFile(phoneBookFile))
            setMsg("Phone book saved to file");
        else
            setMsg("Phone book is empty, nothing to save");
    }

    //loads phone book from file and pops message to user with result
    private void onLoadFromFileClick() throws IOException, ClassNotFoundException {
        if (phoneBook.loadContactsFromFile(phoneBookFile))
            setMsg("Phone book loaded from file");
        else
            setMsg("Phone book is empty");
    }

    //Adds contact to the phone book if it doesn't exist in the phone book,
    //updates msg with result
    private void onAddClick() {
        if (!isValidInput())
            setMsg(INVALID_INPUT_ERR);
        else {
            Contact contact = new Contact(nameField.getText(), phoneField.getText());
            if (!phoneBook.addContact(contact))
                setMsg("Error, " + contact.getName() + " already exists");
            else
                setMsg(contact.getName() + " added successfully");
        }
    }

    //valid input is not empty name and numeric phone number
    private boolean isValidInput() {
        try {
            Long.parseLong(phoneField.getText()); //numeric phone number
            return !nameField.getText().isEmpty(); //name is not empty
        } catch (NumberFormatException e) {
            return false;
        }
    }

    //updates view with clear text fields and all contacts from phone book
    private void updateView() {
        clearView();
        viewPhoneBook();
    }

    //updates view with all contacts from phone book
    private void viewPhoneBook() {
        contactsTextArea.setText(phoneBook.toString());
    }

    //clears all text fields
    private void clearView() {
        nameField.clear();
        phoneField.clear();
        contactsTextArea.setText("");
    }

    //removes contact from phone book by name and pops message to user with result
    private void onRemoveClick() {
        String name = nameField.getText();

        if (name.isEmpty())
            setMsg(NAME_IS_EMPTY_ERR);
        else {
            if (phoneBook.removeContact(name))
                setMsg(name + " removed successfully");
            else
                setMsg("Error, " + name + " not found");
        }
    }

    //pops message to user with given message
    private void popMsg(String msg) {
        JOptionPane.showMessageDialog(null, msg);
    }
}
