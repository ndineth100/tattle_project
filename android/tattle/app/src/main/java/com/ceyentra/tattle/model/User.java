package com.ceyentra.tattle.model;

import com.ceyentra.tattle.R;

import java.util.Random;

public class User {
    private String name;
    private String[] names = {"Alligator","Anteater","Armadillo","Auroch","Axolotl","Badger","Bat","Beaver","Buffalo","Camel","Chameleon","Cheetah","Chipmunk","Chinchilla","Chupacabra","Cormorant","Coyote","Crow","Dingo","Dinosaur","Dog","Dolphin","Dragon","Duck","Dumbo","Octopus","Elephant","Ferret","Fox","Frog","Giraffe","Goose","Gopher","Grizzly","Hamster","Hedgehog","Hippo","Hyena","Jackal","Ibex","Ifrit","Iguana","Kangaroo","Koala","Kraken","Lemur","Leopard","Liger","Lion","Llama","Manatee","Mink","Monkey","Moose","Marwhal","Nyan","Cat","Orangutan","Otter","Panda","Penguin","Platypus","Python","Pumpkin","Quagga","Rabbit","Raccoon","Rhino","Sheep","Shrew","Skunk","Slow", "Loris","Squirrel","Tiger","Turtle","Unicorn","Walrus","Wolf","Wolverine","Wombat"};
    private static int[] avatars = {R.drawable.file1,R.drawable.file2,R.drawable.file3,R.drawable.file4,R.drawable.file5,R.drawable.file6,R.drawable.file7,R.drawable.file8,R.drawable.file9,R.drawable.file10,R.drawable.file11,R.drawable.file12,R.drawable.file13,R.drawable.file14,R.drawable.file15,R.drawable.file16,R.drawable.file17,R.drawable.file18,R.drawable.file19,R.drawable.file20,R.drawable.file21,R.drawable.file22,R.drawable.file23,R.drawable.file24,R.drawable.file25,R.drawable.file26,R.drawable.file27,R.drawable.file28,R.drawable.file29,R.drawable.file30,R.drawable.file31,R.drawable.file32,R.drawable.file33,R.drawable.file34,R.drawable.file35,R.drawable.file36,R.drawable.file37,R.drawable.file38,R.drawable.file39,R.drawable.file40,R.drawable.file41,R.drawable.file42,R.drawable.file43,R.drawable.file44,R.drawable.file45,R.drawable.file46,R.drawable.file47,R.drawable.file48,R.drawable.file49,R.drawable.file50,R.drawable.file51,R.drawable.file52,R.drawable.file53,R.drawable.file54,R.drawable.file55,R.drawable.file56,R.drawable.file57,R.drawable.file58,R.drawable.file59,R.drawable.file60};
    private static String[] chatColors = {"#00c96c","#ff9600","#ff9600","#ff001b","#c19e00","#c19e00","#00c96c","#029be2","#ff9600","#c19e00","#ff001b","#c19e00","#00c96c","#ff9600","#ff9600","#ff9600","#029be2","#c19e00","#ff001b","#029be2","#c19e00","#ff001b","#ff001b","#029be2","#00c96c","#029be2","#00c96c","#ff001b","#00c96c","#00c96c","#00c96c","#00c96c","#c19e00","#00c96c","#029be2","#00c96c","#c19e00","#00c96c","#029be2","#029be2","#029be2","#029be2","#ff001b","#c19e00","#ff001b","#029be2","#029be2","#029be2","#ff9600","#ff9600","#029be2","#ff9600","#029be2","#ff9600","#029be2","#ff001b","#c19e00","#ff9600","#c19e00","#c19e00"};
    public User(String name) {
        this.name = name;
    }

    public User() {
    }

    public int getRandomAvatar(){
        Random rand = new Random();
        return rand.nextInt(avatars.length);
    }

    public String getRandomUserName() {
        Random rand = new Random();
        int  n = rand.nextInt(names.length);
        int m = rand.nextInt(899)+100;
        return names[n]+"_"+ String.valueOf(m);
    }

    public static int getAvatar(int id) {
        return avatars[id];
    }

    public static String getChatColor(int id){
        return chatColors[id];
    }
}
