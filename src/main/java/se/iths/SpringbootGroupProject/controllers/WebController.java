package se.iths.SpringbootGroupProject.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import se.iths.SpringbootGroupProject.repositories.MessageRepository;
import se.iths.SpringbootGroupProject.repositories.UserRepository;

@Controller
@RequestMapping("/web")
public class WebController {
    /* ToDo:
     * Startsida: publika meddelanden, inloggningsruta, språkalternativ, välkomstmeddelande
     * Homepage efter inloggning: dina meddelanden, din profil: din bild, namn, efternamn,email, profilnamn, utloggningsruta, språkalternativ
     * Sida för att se alla meddelanden: Se alla meddelanden privata som publika där du ska kunna redigera ditt meddelande. utloggningsruta, språkalternativ
     *
     * OM TID FINNS-> Alla användare sida: kunna se alla användare och söka på användare
     */


    @GetMapping("/welcome")
    public String getWelcomePage(Model model){

        return "welcome";
    }

}
