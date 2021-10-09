

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class UrlCrawler {

    public static void main(String[] args) {
        if (args.length == 2 && args[0].startsWith("--url") && args[1].startsWith("--words")) {
            String url = args[0].replaceAll("--url=","");
            String words = args[1].replaceAll("--words=","");

            urlCrawler(url, words);
        }
        else {
            // incorrect arguments
            System.out.println("please provide the input in this format : ");
            System.out.println("--url=https://anatta.io --words=Anatta,Growth");
        }
    }

    /**
     * connect to the url and fetches the content to a string
     * @param url : url to connect
     * @param words : list of words in a string
     */
    private static void urlCrawler(String url, String words) {
        if (url.length() == 0) {
            System.out.println("please provide a url");
        }
        else if (words.length() == 0){
            System.out.println("please provide a word or list of words to search, i.e. : Code,Coffee,Repeat");
        }
        else {
            try {

                Connection connection = Jsoup.connect(url);
                Document document = connection.get();

                String urlContent = document.body().text();

                if (urlContent.length() > 0) {
                    List<String> wordsLt = Arrays.asList(words.split(","));

                    for (String word : wordsLt) {
                        printWordCount(word.trim(), urlContent.toLowerCase());
                    }
                } else {
                    System.out.println("url content is empty");
                }

            } catch (IOException e) {
                System.out.println("please enter a correct url, i.e: https://anatta.io");
            }
        }
    }

    /**
     * counts how many times the given word has appeared on the url content and prints
     * @param word : word to search
     * @param urlContent : website content
     */
    private static void printWordCount(String word, String urlContent) {

        if (word.length() == 0)
        {
            System.out.println("no word provided.." + word);
        }
        else {
            int index = 0, count = 0;

            while(true) {
                index = urlContent.indexOf(word.toLowerCase(), index);

                if (index != -1) {
                    count++;
                    index += word.length();
                } else {
                    break;
                }
            }

            System.out.println(word+": "+count);
        }
    }
}
