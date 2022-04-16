/*Class represents a Phone book*/

import java.io.*;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class PhoneBook {

    private TreeMap<String, String> contacts;

    public PhoneBook() {
        contacts = new TreeMap<String, String>();
    }

    //adds a contact to the phonebook only if it doesn't already exist
    //returns true if contact was added, false if it already existed
    public boolean addContact(Contact c) {
        if (!this.contains(c)) {
            contacts.put(c.getName(), c.getPhoneNumber());
            return true;
        }
        return false;
    }

    //updates phone of existing contact in phone book
    //returns true if contact was updated, false if it didn't exist in phone book
    public boolean updateContact(Contact c) {
        if (this.contains(c)) {
            contacts.put(c.getName(), c.getPhoneNumber());
            return true;
        }
        return false;
    }

    //Removes contact by name from phonebook
    //returns true if contact was removed, false if it didn't exist in phone book
    public boolean removeContact(String name) {
        if (contacts.remove(name) != null)
            return true;
        return false;

    }

    //Returns the phone Number of a contact by name, null if contact doesn't exist
    public String getPhoneNumber(String name) {
        return contacts.get(name);
    }

    //Returns if phonebook contains a contact by name
    public boolean contains(Contact c) {
        return contacts.containsKey(c.getName());
    }

    @Override
    //returns string representation of phonebook with all contacts
    public String toString() {
        String s = "";
        for (Map.Entry<String, String> entry : contacts.entrySet())
            s = s + entry.getKey() + " \t " + entry.getValue() + "\n";
        return s;

    }

    //returns if phonebook is empty
    public boolean isEmpty() {
        return contacts.isEmpty();
    }

    //returns iterator for phonebook
    public Iterator<Map.Entry<String, String>> iterator() {
        return contacts.entrySet().iterator();
    }

    //Saves all contacts to given file and return true
    //if phone book is empty,doesn't save and returns false
    public boolean saveContactsToFile(File file) throws IOException {
        if (!isEmpty()) {
            FileOutputStream fos = new FileOutputStream(file, false);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            writeContacts(oos);
            oos.close();
            fos.close();
            return true;
        }
        return false;
    }

    //write All contacts to file
    private void writeContacts(ObjectOutputStream oos) throws IOException {
        for (Map.Entry<String, String> entry : contacts.entrySet())
            oos.writeObject(new Contact(entry.getKey(), entry.getValue()));
    }

    //Loads all contacts from given file, returns false if empty file else true
    public boolean loadContactsFromFile(File file) throws IOException, ClassNotFoundException {
        try {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            contacts = new TreeMap<String, String>();
            try {
                while (true)
                    addContact((Contact) ois.readObject());
            }//catch end of file
            catch (EOFException e) {
                return true;
            } finally {
                ois.close();
                fis.close();
            }
            //catch empty file
        } catch (EOFException e) {
            return false;
        }
    }


}
