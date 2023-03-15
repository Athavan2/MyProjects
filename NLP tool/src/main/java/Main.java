import java.io.*;
import edu.stanford.nlp.ling.*;
import edu.stanford.nlp.pipeline.*;
import java.nio.file.*;
import java.util.*;


public class Main {
    //attributes
    public static String titre="";
    public static int nombreDocsTraite=0;
    public static WordMap<String,FileMap> wordMap= new WordMap<>();

    //Using the ProbeHashMap Structure to keep NewEntry<FileName,NumberOfWordsInFile> for the search method.
    public static WordMap<String,Integer> repertoireCount= new WordMap<>();

    public static void main(String[] args) throws Exception {
        String exc = "Format attendu: Main [dataset] [query.txt]";
        String dataset = "";
        String queryFile = "";
        if(args.length > 2 || args.length ==0){
            System.out.println(exc);
            System.exit(0);
        }
        try{
            dataset   = args[0];
            queryFile = args[1];
        }
        catch (Exception e) {
            System.out.println(exc);
        }
        String path = new File("").getAbsolutePath().replace("\\", "/");
        String pathDataset=path.concat("/"+dataset);
        readDirectory(pathDataset);
        if(!queryFile.equals("")){
            readQuery(queryFile);
        }
    }
    //This method receives a path to a Folder and for each file it finds,
    //it will read the file and proceed to updating the wordMap.
    public static void readDirectory(String path) throws Exception {
        File dir = new File(path);
        for(File file:dir.listFiles()){
            nombreDocsTraite++;
            titre=file.getName().replace(".txt", "");
            read(readFileAsString(path+"/"+file.getName()));
        }
    }
    //This method takes 1 .txt file and returns a String containing all the words in the file.
    public static String readFileAsString(String fileName)throws Exception
    {
        String data = "";
        data = new String(Files.readAllBytes(Paths.get(fileName)));
        return data;
    }

    //This method receives a .txt file as a String and begins the NLP treatment and adds updates to the WordMap.
    public static void read(String text){
        ArrayList<String> str = new ArrayList<>(); // Temporary structure that stores all the words that are used.
        String newline = text.replaceAll("[^’'a-zA-Z0-9]", " ");
        String finalline = newline.replaceAll("\\s+", " ").trim();

        // ********************************************Pre-traitement********************************************
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize,pos,lemma");
        props.setProperty("coref.algorithm", "neural");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        CoreDocument document = new CoreDocument(finalline);
        pipeline.annotate(document);
        // ****************************************************************************************
        for (CoreLabel tok : document.tokens()) {
            String mot = tok.lemma();
            //System.out.println(mot+"     "+mot.hashCode());
            if (!(str.contains("'s") || str.contains("’s"))){
                str.add(mot.replaceAll("[^a-zA-Z0-9]", " ")
                        .replaceAll("\\s+", " ").trim());
            }
        }


        for(int i = 0; i< str.size();i++){
            String key= str.get(i);  //key represents each word from the array
            if(i == (str.size()-1)){// Case of being the last word in the list, there wouldn't be a neighbour.
                updateWordMap(key,titre,i,"");
            }
            else{
                updateWordMap(key,titre,i,str.get(i+1));
            }
            repertoireCount.put(titre,str.size());
        }
    }

    // This function is in charge of executing all the requests in the query.txt file.
    public static void readQuery(String path){

        try {
            Scanner scan = new Scanner(new FileInputStream(path));
            int lineNum = 0;
            while(scan.hasNext()){
                lineNum++;
                String[] line = scan.nextLine().split(" ");
                String word;
                String mot = "";
                if(line.length == 2 && line[0].equals("search")){
                    word = line[1];
                    mot =search(word);
                }

                else if(line.length == 6 && line[0].equals("the") &&
                        line[1].equals("most") && line[2].equals("probable") &&
                        line[3].equals("bigram") && line[4].equals("of")){
                    word = line[5];
                    mot = bigram(word);
                }
                if(!mot.equals("")){System.out.println(mot);}
            }

        }

        catch (FileNotFoundException e) {
            System.out.println("File not found.");;
        }

    }

    // This method receives a word, the name of the file in which we found the word,
    // the index of the word in this file, along with the next word.
    // It adds this entry to the wordMap accordingly to if the entry already exists..
    public static void updateWordMap(String mot, String nomFichier, int indexDansFichier,String voisin){
        if(wordMap.containsKey(mot)){
            FileMap<String,ArrayList> fileMap = wordMap.get(mot);
            ArrayList<Integer> arrayListOfIndexes = new ArrayList<>();
            if(fileMap.containsKey(nomFichier)){
                arrayListOfIndexes = fileMap.get(nomFichier);
                arrayListOfIndexes.add(indexDansFichier);
            }
            else{
                arrayListOfIndexes.add(indexDansFichier);
            }
            fileMap.put(nomFichier,arrayListOfIndexes);
            wordMap.put(mot,ajouterVoisin(fileMap,voisin));
        }
        else{
            FileMap<String,ArrayList> fileMap= new FileMap<>();
            ArrayList<Integer> newArrayListOfIndexes = new ArrayList<>();
            newArrayListOfIndexes.add(indexDansFichier);
            fileMap.put(nomFichier,newArrayListOfIndexes);
            wordMap.put(mot,ajouterVoisin(fileMap,voisin));
        }
    }
    //This method adds the next word as the neighbour to our current word into the fileMap.
    public static FileMap ajouterVoisin(FileMap fileMap,String voisin){
        FileMap<String,ArrayList> newFileMap= fileMap;
        if(!voisin.equals("")) {
            newFileMap.addVoisin(voisin);
        }
        return newFileMap;
    }

    //This method returns the name of the best file for a certain word.
    public static String search(String word){
        String bestFileTFIDF = "Aucun document contient le mot";
        FileMap fileMap = wordMap.get(word);
        if(fileMap != null){
            Iterator<NewEntry<String,ArrayList>> entries = fileMap.entrySet().iterator();
            int totalD = nombreDocsTraite;
            int countD = fileMap.size();
            double IDF = Math.log((double) totalD/ (double) countD);
            double bestTFIDF = 0.0;

            // Case where every file in the repository contains the word, TFIDF would always be ==0 (ln 1 =0),
            // so we return the file contain the alphabetic priority.
            if(totalD == countD){
                bestFileTFIDF="";
                while(entries.hasNext()){
                    String fileName= entries.next().getKey();
                    if(bestFileTFIDF.equals("")){
                        bestFileTFIDF=fileName+ ".txt";
                    }
                    else if(bestFileTFIDF.compareTo(fileName)>0){
                        bestFileTFIDF=fileName+ ".txt";
                    }
                }
            }
            else{
                while(entries.hasNext()){
                    NewEntry<String, ArrayList> entry = entries.next();
                    ArrayList<Integer> index = entry.getValue();
                    int count = index.size();// Number of repetition of the word in current file.
                    int total = repertoireCount.get(entry.getKey());//Number of words in the current file.

                    double TF = (double)count/ (double)total;
                    double TFIDF = TF*IDF;

                    if(TFIDF > bestTFIDF || (TFIDF == bestTFIDF && (bestFileTFIDF.compareTo(entry.getKey())>0))){
                        bestTFIDF = TFIDF;
                        bestFileTFIDF = entry.getKey()+ ".txt";
                    }
                }
            }
        }
        return bestFileTFIDF;
    }

    public static String bigram(String word){
        String prochainMot = "Votre mot n'a pas ete trouve!";
        if(wordMap.containsKey(word)){
            prochainMot =word+" "+ wordMap.get(word).getProchainMotPlusReccurent();
        }
        return prochainMot;
    }
}