/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package par5e;

import par5e.utils.RegEx;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author zeph
 */
public class CleanXMLFile {
    
        public String path;
        public String[] textData;
        public Integer lineCount;
        
    public CleanXMLFile(String aPath) {
        path = aPath;
    }
    
    public void openFile() {
        try (BufferedReader textReader = Files.newBufferedReader(Paths.get(path), StandardCharsets.UTF_8)) {
            lineCount = getLineCount();
            textData = new String[lineCount];
            for (int i=0; i < lineCount; i++) {
                textData[i] = textReader.readLine();    
            }   
        } catch (IOException ex) {
            Logger.getLogger(CleanXMLFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void cleanXML() {
        String[] cleantextData = new String[lineCount];
            for (int i=0; i < lineCount; i++) {
                cleantextData[i] = textData[i];

                RegEx regex = new RegEx("&lt;",cleantextData[i]);
                if (regex.find()) {
                    cleantextData[i] = regex.replaceAll("\\<");
                }
                regex = new RegEx("&gt;",cleantextData[i]);
                if (regex.find()) {
                    cleantextData[i] = regex.replaceAll("\\>");
                }
                regex = new RegEx("null",cleantextData[i]);
                if (regex.find()) {
                    cleantextData[i] = regex.replaceAll("");
                }
//                regex = new RegEx("&#x2019;",cleantextData[i]);
//                if (regex.find()) {
//                    cleantextData[i] = regex.replaceAll("'");
//                }
//                regex = new RegEx("&#x2010;",cleantextData[i]);
//                if (regex.find()) {
//                    cleantextData[i] = regex.replaceAll("-");
//                }
//                regex = new RegEx("&#x2013;",cleantextData[i]);
//                if (regex.find()) {
//                    cleantextData[i] = regex.replaceAll("-");
//                }  
//                regex = new RegEx("&#x2014;",cleantextData[i]);
//                if (regex.find()) {
//                    cleantextData[i] = regex.replaceAll("-");
//                }
//                regex = new RegEx("&#x201c;",cleantextData[i]);
//                if (regex.find()) {
//                    cleantextData[i] = regex.replaceAll("\"");
//                }
//                regex = new RegEx("&#x201d;",cleantextData[i]);
//                if (regex.find()) {
//                    cleantextData[i] = regex.replaceAll("\"");
//                }
     }
            textData = cleantextData;
    }
    
    public void closeFile() {
        try (BufferedWriter textWriter = Files.newBufferedWriter(Paths.get(path), StandardCharsets.UTF_8)) {
            for (int i=0; i < lineCount; i++) {
                textWriter.write(textData[i] + "\n");
            }
        } catch (IOException ex) {
            Logger.getLogger(CleanXMLFile.class.getName()).log(Level.SEVERE, null, ex);
        }
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
    
}

