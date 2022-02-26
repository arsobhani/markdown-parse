// File reading code from https://howtodoinjava.com/java/io/java-read-file-to-string-examples/
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Stack;

public class shittymarkdownparse {

    public static ArrayList<String> getLinks(String markdown) {
        ArrayList<String> toReturn = new ArrayList<>();
        
        int currentIndex = 0;
        Stack<Character> bracketTracker = new Stack<>(); 
        boolean findLink = false;
        int start = 0;
        int end = 0;
        while (currentIndex < markdown.length()) {
            char curr = markdown.charAt(currentIndex);
            //if an escape char is found, skip it and the 
            //character it is escaping
            if (curr == '\\') {
                currentIndex += 2;
                continue;
            }
            //if we are potentially looking at a link with []
            if (findLink) {
                // if there arent any other brackets on the bracket tracker
                if (bracketTracker.isEmpty()) {
                    if (curr == '(') {
                        bracketTracker.push(curr);
                        start = currentIndex;
                    } else { //something else came after the ] that wasn't (
                        findLink = false;
                    }
                } else {
                    if (curr == ')') {
                        end = currentIndex;
                        if (start + 1 != end) {
                            toReturn.add(markdown.substring(start + 1, end));
                        }
                        bracketTracker.pop();
                        findLink = false;
                    }
                }
            } else {
                if (curr == '[') {
                    bracketTracker.push(curr);
                } else if (curr == ']') {
                    //it becomes empty when a close bracket is found
                    if (!bracketTracker.isEmpty()) {
                        bracketTracker.clear();
                        findLink = true;
                    }
                //image check
                } else if (curr == '!') {
                    if (currentIndex < markdown.length() - 1 && markdown.charAt(currentIndex + 1) == '[') {
                        currentIndex += 2;
                    }
                }
            }
            // move to next char
            currentIndex++;
        }

        return toReturn;
    }
    public static void main(String[] args) throws IOException {
        // take in the first command line arg as the file name to be searched for links.
		Path fileName = Path.of(args[0]);
        // read the file contents into a string
	    String contents = Files.readString(fileName);
        // run getLinks on the contents of the file
        ArrayList<String> links = getLinks(contents);
        // print out the links that we found
        System.out.println(links);
    }
}