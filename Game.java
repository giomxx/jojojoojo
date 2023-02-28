import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Game{
    
    private Player currentPlayer;
    private Cryptogram cryptoGame;
    Players GamePlayers = new Players();
    private List<Cryptogram> allCryptograms = new ArrayList<Cryptogram>();
    private List<Character> currentGame = new ArrayList<Character>();
    private List<Integer> currentGameMap = new ArrayList<Integer>();

   public Game(){
        currentPlayer = new Player(null, 0, 0, 0, 0);
        cryptoGame = new Cryptogram("zerkin on main", "abcdefghijklmnopqrstuvwxyz");

        File myObj = new File("AllCryptos.txt");
        try (Scanner in = new Scanner(myObj)){
            String[] data = null;

            while (in.hasNextLine()){

                data = in.nextLine().split(" # ");
                allCryptograms.add(new Cryptogram(data[0],data[1]));
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            e.printStackTrace();
        }
    }
    
    public void getHint(){

    }
    
    public void loadPlayer(){
        String newPlayerName;
        Scanner in = new Scanner(System.in);
        System.out.println("please enter your username");
        newPlayerName = in.nextLine();
        Player p = new Player(newPlayerName,0,0,0,0);
        if (GamePlayers.findPlayer(p) == null){
            System.out.println("would you like to create a new account? Y or N?");
            String input = in.nextLine();
            if(input.equals("Y")){
                GamePlayers.addPlayer(newPlayerName, 100, 0, 0, 0);
                currentPlayer = GamePlayers.findPlayer(p);
                System.out.println("new account succesfully created with username- " + newPlayerName);
            }else if(input.equals("N") ){                    
                System.out.println("New account has not been made");
            }else{
                System.out.println("please enter Y or N");
                }
            
            in.close();
        }else{
        System.out.println("Account successfully loaded");
        currentPlayer = GamePlayers.findPlayer(p);
        }
    }
    public void playGame(){
        Cryptogram currentCryptogram = generateCryptogram();
        Boolean running = true;
        Scanner in = new Scanner(System.in);
        while(running == true){
            String[] data = null;
            System.out.println(currentGame);
            System.out.println("please enter your guess and its position (e.g c 1)");
            data = in.nextLine().split(" ");
            if (Integer.valueOf(data[1]) < currentGameMap.size()){
                enterLetter(data[0].charAt(0),Integer.valueOf(data[1]));
                if (currentGame.toString().substring(1, 3 * currentGame.size() - 1).replaceAll(", ", "").equals((currentCryptogram.cryptogramPhrase).replaceAll(" ",""))){
                    System.out.println("well done! The cryptogram has been solved!");
                    running = false;
                }
            }else{
                System.out.println("please enter a valid position");
            }        
            System.out.println(currentGame.toString().substring(1, 3 * currentGame.size() - 1).replaceAll(", ", "") +" "+ (currentCryptogram.cryptogramPhrase).replaceAll(" ",""));
        }
        in.close();

    }
    public Cryptogram generateCryptogram(){
        int x = (int)((Math.random() * allCryptograms.size()));
        cryptoGame = allCryptograms.get(x);
        for(int i1 = 0; i1 < (cryptoGame.cryptogramPhrase).length(); i1++){
            if (cryptoGame.cryptogramPhrase.charAt(i1) != ' '){
                for(int i2 = 0; i2 < (cryptoGame.Alphabet).length(); i2++){
                    if (cryptoGame.Alphabet.charAt(i2) ==(cryptoGame.cryptogramPhrase).charAt(i1)){
                        currentGameMap.add(i2);
                    }
                
                }
            }        
        }
        for(int i =0; i < currentGameMap.size(); i++){
            currentGame.add(i, '_');
        }

        return allCryptograms.get(x);
    }
    public void enterLetter(char c, int pos){
        for(int i = 0; i < currentGameMap.size(); i++){
            if (currentGameMap.get(pos) == currentGameMap.get(i)){
                currentGame.set(i,c);
            }
        
        }
    }
    public void undoLetter(){

    }
    public void viewFrequencies(){
        System.out.println(Arrays.toString(cryptoGame.getFrequencies()));
    }
    public void savePlayer(){
        GamePlayers.savePlayer(currentPlayer);
    }
    public void loadGame(){

    }
    public void showSolution(){

    }
    public static void main(String[] args){
        Game G = new Game();
        G.loadPlayer();
        //test to check saving
        //G.currPlayer.updateAccuracy(50); 
        //System.out.println(G.currPlayer.getAccuracy());
        G.savePlayer();
        G.viewFrequencies();
        //test to check genCrypto
        //Cryptogram C = G.generateCryptogram();
        //System.out.printf(C.cryptogramPhrase +" "+ C.cypher);
        //System.out.println(G.currentGameMap);
        G.playGame();
    }
}
    
