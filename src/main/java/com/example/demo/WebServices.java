package com.example.demo;

import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


@RestController
@RequestMapping("api")
public class WebServices {
    List<Contact> contacts = new ArrayList<Contact>();

    @GetMapping(value = "/contacts")
    List<Contact> getContacts() {
        return contacts;
    }

    @PostMapping(value = "/contacts", consumes = "application/json")
    List<Contact> newContact(@RequestBody Contact contact) {
        int idMax=0;
        for (Contact cont: contacts) {
            if (cont.getId()>idMax){
                idMax=cont.getId();
            }
          }
        contact.setId(idMax+1);
        contacts.add(contact);
        return contacts;
    }

    private void writeToJSON(List<Contact> contacts) {
        try {
        FileWriter file = new FileWriter("contacts.json");
        new Gson().toJson(contacts, file);
        file.close();
        }
        catch (Exception ex ) {
        System.out.println("Erreur écriture dufichier:"+ex.getMessage());
        ex.printStackTrace();
        }
        }
        private List<Contact> readFromJSON() {
        Type listType = new TypeToken<ArrayList<Contact>>(){}.getType();
        try {
        return new Gson().fromJson(new FileReader("contacts.json"),
        listType);
        }
        catch (Exception ex ) {
        System.out.println("Erreur lecture du fichier:"+ex.getMessage());
        ex.printStackTrace();
        return new ArrayList<>();
        }
        }
        @DeleteMapping(value="/contact/{id}")
        List<Contact> deleteContact(@PathVariable int id) {
        // supprimer le contact d'identifiant spécifié
            return contacts;
        }
}
