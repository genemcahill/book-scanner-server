import java.awt.*;
import java.net.Inet4Address;
import java.net.URL;
import java.net.UnknownHostException;

import static spark.Spark.*;

/**
 * Amazon Book Scanner Server
 * This application opens up an Amazon search page on the host machine when a REST request is made
 * To be used by the Android Book Scanner application to send requests for faster book listing
 */
public class BookScannerServer {

    public static final String AMAZON_BASE_URL = "http://www.amazon.com/s/ref=nb_sb_noss?" +
            "url=search-alias%3Daps&field-keywords=";

    public static void main(String[] args){

        /**
         * Default Index Page
         */
        get("/", (request, response) ->
                "http://"+getIPAddress()+":4567/search/[ISBN] to search...");

        /**
         * Default rest request - takes in book ISBN
         */
        get("/search/:isbn", (request, response) -> {
            Runnable r = () -> openWebpage(request.params("isbn"));
            r.run();
            return request.params("isbn");
        });
    }

    /**
     * Opens the default browser to the Amazon search page for the provided book ISBN
     * @param isbn
     */
    public static void openWebpage(final String isbn){
        try {
            Desktop.getDesktop().browse(new URL(AMAZON_BASE_URL+isbn).toURI());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @return IP address of host
     */
    public static String getIPAddress(){
        try{
            return Inet4Address.getLocalHost().getHostAddress();
        }
        catch(UnknownHostException e){
            e.printStackTrace();
            return null;
        }
    }
}
