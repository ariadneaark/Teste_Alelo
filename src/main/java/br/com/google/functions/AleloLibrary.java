package br.com.google.functions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StreamTokenizer;
import java.io.Writer;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.output.FileWriterWithEncoding;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.opencsv.CSVWriter;

public class AleloLibrary {

	public static int qntDirector;
	public static int qntBirthDate;
	public static int qntNameMovies;
	public static int qntReleaseYear;
	public static WebDriver driver;
	public static boolean flagImportArchive = false;
	
	//C:\\Proton\\dadosFilmes.csv
	public static String csvFile = "C:\\Proton\\dados.csv";
	public static int columnArchive;
	
	public static ArrayList<String> listDiretorArq  = new ArrayList<String>();
	public static ArrayList<String> listDataNascArq = new ArrayList<String>();
	public static ArrayList<String> listFilmeArq = new ArrayList<String>();
	public static ArrayList<String> listDataLancArq = new ArrayList<String>();

	public static ArrayList<String> listResultSearch = new ArrayList<String>();

	
	public static boolean verifyExistID(WebDriver driver, String IdElement, int timeoutSeconds) {
		boolean result = false;		
		
		try {			
			driver.manage().timeouts().implicitlyWait(timeoutSeconds, TimeUnit.SECONDS);
			driver.findElement(By.id(IdElement));
			
			result = true;
		} catch (Exception e) {
//			System.out.println("Elemento '" + IdElement + "' não existe.");
			result = false;
		} finally {
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		}

		return result;
	}
	
	public static boolean verifyExistXPath(WebDriver driver, String XPathElement, int timeoutSeconds) {
		boolean result = false;

		try {
			driver.manage().timeouts().implicitlyWait(timeoutSeconds, TimeUnit.SECONDS);
			driver.findElement(By.xpath(XPathElement));

			result = true;
		} catch (Exception e) {
//			System.out.println("Elemento '" + XPathElement + "' não existe.");
			result = false;
		} finally {
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		}

		return result;
	}
	
	public static boolean validateTextID(WebDriver driver, String IdElement, String text) {
		boolean result = false;
		String path = driver.findElement(By.id(IdElement)).getText();
		
		if(path.contains(text))
			result = true;
		else
			result = false;

		return result;
	}
	
	public static boolean validateTextXPath(WebDriver driver, String XPathElement, String text) {
		boolean result = false;
		String path = driver.findElement(By.xpath(XPathElement)).getText();
		
		if(path.contains(text))
			result = true;
		else
			result = false;

		return result;
	}
	
	
	public static String[] redimArrayString(String[] original, int tamanhoNovo) {
		String[] novoArray = new String[tamanhoNovo];
		System.arraycopy(original, 0, novoArray, 0, Math.min(original.length, novoArray.length));
		return novoArray;
	}
	
	public static void lerArquivoCsv() {
		
        String line = "";
        String cvsSplitBy = ";";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
        	
            while ((line = br.readLine()) != null) {
            	
                // use comma as separator
                String[] dataReceived = line.split(cvsSplitBy);
                columnArchive = dataReceived.length;

                listDiretorArq.add(dataReceived[0]);
                System.out.println(dataReceived[0]);
                
                listDataNascArq.add(dataReceived[1]);
                System.out.println(dataReceived[1]);
                
                listFilmeArq.add(dataReceived[2]);
                System.out.println(dataReceived[2]);

                listDataLancArq.add(dataReceived[3]);
                System.out.println(dataReceived[3]);
                
            }
            
        } catch (Exception e) {
            System.out.println("Erro ao armazenar os dados do documento.");
        }
	}
	
	public static void inserirDadosArquivo() throws IOException{
		
		File file = new File(csvFile);
		if(flagImportArchive == false && !file.exists())
			file.createNewFile();
		
		try { 
	        FileWriter outputfile = new FileWriter(file); 
	        CSVWriter writer = new CSVWriter(outputfile);
	        	        
	        BufferedReader br = new BufferedReader(new FileReader(csvFile));
	        
	        if(flagImportArchive == false)
	        {
//	        	for(int i = 0; i < listDiretorArq.size(); i++){
//
//		            if(listResultSearch != null){
//		            	String [] diretor = listDiretorArq.get(i).trim().split(",");
//		                String [] dataNasc = listDataNascArq.get(i).trim().split(",");
//		                String [] filme= listFilmeArq.get(i).trim().split(",");
//		                String [] dataLanc = listDataLancArq.get(i).trim().split(",");
//		                String [] resultadoPesquisa = listResultSearch.get(i).trim().split(",");
//		                
//						writer.writeNext(diretor);
//		                writer.writeNext(dataNasc);
//		                writer.writeNext(filme);
//		                writer.writeNext(dataLanc);
//		                writer.writeNext(resultadoPesquisa);
//		                
////		            	String diretor = listDiretorArq.get(i).trim();
////		                String dataNasc = listDataNascArq.get(i).trim();
////		                String filme = listFilmeArq.get(i).trim();
////		                String dataLanc = listDataLancArq.get(i).trim();
////		                String resultadoPesquisa = listResultSearch.get(i).trim();
////		                		                
////		                outputfile.append(diretor).append(da9taNasc).append(filme).append(dataLanc).append(resultadoPesquisa);
//		            }
//
//	            }
	        	for(int i = 0; i < listDiretorArq.size(); i++){

		            if(listResultSearch != null){
		            	outputfile.append(listDiretorArq.get(i));
		            	outputfile.append(listDataNascArq.get(i));
		            	outputfile.append(listFilmeArq.get(i));
		            	outputfile.append(listDataLancArq.get(i));
		            	outputfile.append(listResultSearch.get(i) + "\n");
		            }

	            }
	        }
	        else 
	        {
	        	for(int i = 0; i < listResultSearch.size(); i++){

		            if(listResultSearch != null){
		            	String [] resultadoPesquisa = listResultSearch.get(i).trim().split(",");
		                writer.writeNext(resultadoPesquisa);
		            }

	            }
	        }
	        
	        
	        writer.close();
	            
	    } 
	    catch (Exception e) { 
	        // TODO Auto-generated catch block 
	        e.printStackTrace(); 
	    } 
	    	  
	}
	
	
}
