// File reading code from https://howtodoinjava.com/java/io/java-read-file-to-string-examples/
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class MarkdownParse {
    public static ArrayList<String> getLinks(String markdown) {
        ArrayList<String> toReturn = new ArrayList<>();
        // find the next [, then find the ], then find the (, then take up to
        // the next )
        int currentIndex = 0;
        while(currentIndex < markdown.length()) {
            int nextOpenBracket = markdown.indexOf("[", currentIndex);
            int nextCloseBracket = markdown.indexOf("]", nextOpenBracket);
            int openParen = markdown.indexOf("(", nextCloseBracket);
            int closeParen = markdown.indexOf(")", openParen);
            //this checks if any of the characters are not found within the file at all
            if (nextOpenBracket == -1 || nextCloseBracket == -1 || openParen == -1 || closeParen == -1) break;
            //this checks if the character before the open bracket is a '!'
            //since that would create an image
            //the 0 is to prevent out of bounds error if the open bracket is at index 0
            if (nextOpenBracket == 0 || markdown.charAt(nextOpenBracket-1) != '!' && markdown.charAt(openParen - 1) == ']') {
                toReturn.add(markdown.substring(openParen + 1, closeParen));
            }
            //the final change is to make it so that the link will be added only if the 
            //close bracket and open parenthesis are adjacent to each other
            currentIndex = closeParen + 1;
        }
        return toReturn;
    }
    public static void main(String[] args) throws IOException {
		Path fileName = Path.of(args[0]);
	    String contents = Files.readString(fileName);
        ArrayList<String> links = getLinks(contents);
        System.out.println(links);
    }
}

/*
if (markdown.charAt(nextOpenBracket - 1) == '!' && nextCloseBracket == openParen - 1) {
                toReturn.add(markdown.substring(openParen + 1, closeParen));
            }
*/

//testing commit
