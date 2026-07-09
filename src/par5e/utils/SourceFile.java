/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package par5e.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import static org.apache.commons.lang3.StringEscapeUtils.escapeJava;

/**
 *
 * @author zeph
 */
public class SourceFile {
    
    public String path;
        
    public SourceFile(String aPath) {
        path = aPath;
    }
    
    public String[] readFile() throws IOException {
        String[] textData;
            try (BufferedReader textReader = Files.newBufferedReader(Paths.get(path), StandardCharsets.UTF_8)) {
                int numberOfLines = getLineCount();
                textData = new String[numberOfLines];
                for (int i=0; i < numberOfLines; i++) {
                    textData[i] = cleanTxt(textReader.readLine());    
                }   
            }
        return textData;
    }
    
    public int getLineCount() throws IOException {
        try (BufferedReader textReader = Files.newBufferedReader(Paths.get(path), StandardCharsets.UTF_8)) {
            String aLine;
            int numLines = 0;
            
            while (( aLine = textReader.readLine()) != null) {
                numLines++;
            }
            return numLines;
        }
    }
    public String cleanTxt(String aString) {
        String tmpString = escapeJava(aString);
        
        //Replace Character Patterns
        // €“
        tmpString = tmpString.replace("\\u20AC\\u201C", "-");
        // €”
        tmpString = tmpString.replace("\\u20AC\\u201D", "-");
        // €œ
        tmpString = tmpString.replace("\\u20AC\\u0153", "\"");
        // €�
        tmpString = tmpString.replace("\\u20AC\\uFFFD", "\"");
        tmpString = tmpString.replace("\\u20AC\\uFEFF", "-");
        // ï»¿
        tmpString = tmpString.replace("\\u00EF\\u00BB\\u00BF", "");
        
        // Replace null characters
        tmpString = tmpString.replace("\\u20AC", "\'");
        
        //Replace Characters
        // ‐ — with -
        tmpString = tmpString.replace("\\u2010", "-");
        tmpString = tmpString.replace("\\u2011", "-");
        tmpString = tmpString.replace("\\u2012", "-");
        tmpString = tmpString.replace("\\u2013", "-");
        tmpString = tmpString.replace("\\u2014", "-");
        
        //Replace ’ “ ” with "
        tmpString = tmpString.replace("\\u2019", "\'");
        tmpString = tmpString.replace("\\u201C", "\"");
        tmpString = tmpString.replace("\\u201D", "\"");
        
        //Replace Bullets with ?
        tmpString = tmpString.replace("\\u2022", "?");
        
        //Strip ™ and ® symbols 
        tmpString = tmpString.replace("\\u2122", "");
        tmpString = tmpString.replace("\\u00AE", "");
        
        //Strip Foreign Characters
        //Strip null characters
        tmpString = tmpString.replace("\\u00AD", "");
        tmpString = tmpString.replace("\\u00A0", "");
        tmpString = tmpString.replace("\\uFEFF", "");
        // Strip Â      
        tmpString = tmpString.replace("\\u00C2", "");
        //Strip â 
        tmpString = tmpString.replace("\\u00E2", "");
        
        
        return tmpString;
    }
}
