package com.example.jsonview;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class AuthorRepository {
    
    public List<Author> getUsers() {
        List<Book> tolkienBooks = 
            Arrays.asList(
                new Book(
                    "9780044403371",
                    "The Hobbit, or There and Back Again",
                    "1937",
                    "The Hobbit is set within Tolkien's fictional universe and follows the quest of home-loving Bilbo Baggins, the titular hobbit, to win a share of the treasure guarded by a dragon named Smaug. Bilbo's journey takes him from his light-hearted, rural surroundings into more sinister territory."
                ),
                new Book(
                    "9780261103689",
                    "The Lord of the Rings",
                    "1954",
                    "The title refers to the story's main antagonist, the Dark Lord Sauron, who in an earlier age created the One Ring to rule the other Rings of Power given to Men, Dwarves, and Elves, in his campaign to conquer all of Middle-earth. From homely beginnings in the Shire, a hobbit land reminiscent of the English countryside, the story ranges across Middle-earth, following the quest to destroy the One Ring mainly through the eyes of the hobbits Frodo, Sam, Merry and Pippin."    
                ));

            List<Book> sandersonBooks = 
                Arrays.asList(
                    new Book(
                        "9780765383105",
                        "Elantris",
                        "2005",
                        "The story follows three main characters: Prince Raoden of Arelon, Princess Sarene of Teod, and the priest Hrathen of Fjordell. At the beginning of the story, Raoden is cursed by an ancient transformation known as the Shaod and secretly exiled to the city of Elantris just days before his betrothed, princess Sarene of Teod, arrives for their wedding. As Raoden tries to avoid gangs, keep his sanity, and unite the people of Elantris, Sarene must cope with the loss of her husband-to-be and try to save Arelon from Hrathen, a priest tasked with converting all of Arelon to the religion of Fjordell or dooming it to destruction."
                    ),
                    new Book(
                        "9780765311788",
                        "Mistborn: The Final Empire",
                        "2006",
                        "Mistborn: The Final Empire is set on the dystopian world of Scadrial, where ash constantly falls from the sky, all plants are brown, and supernatural mists cloak the landscape every night. One thousand years before the start of the novel, the prophesied Hero of Ages ascended to godhood at the Well of Ascension in order to repel the Deepness, a terror threatening the world whose true nature has since been lost to time. Though the Deepness was successfully repelled and mankind saved, the world was changed into its current form by the Hero, who took the title Lord Ruler and has ruled over the Final Empire for a thousand years as an immortal tyrant and god. Under his rule, society is stratified into the nobility, believed to be the descendants of the friends and allies who helped him achieve godhood, and the brutally oppressed peasantry descended from those who opposed him, known as skaa."
                    ));                

        return  
            Arrays.asList(
                new Author(
                    1, 
                    "John Ronald Reuel", 
                    "Tolkien", 
                    Date.valueOf("1892-01-03"), 
                    "British", 
                    "https://upload.wikimedia.org/wikipedia/commons/thumb/6/66/J._R._R._Tolkien%2C_1940s.jpg/220px-J._R._R._Tolkien%2C_1940s.jpg",
                    tolkienBooks),
                new Author(
                    2, 
                    "Brandon", 
                    "Sanderson", 
                    Date.valueOf("1975-12-19"), 
                    "American", 
                    "https://upload.wikimedia.org/wikipedia/commons/thumb/e/ef/Brandon_Sanderson_-_Lucca_Comics_%26_Games_2016.jpg/220px-Brandon_Sanderson_-_Lucca_Comics_%26_Games_2016.jpg",
                    sandersonBooks)
            );
    }
}
