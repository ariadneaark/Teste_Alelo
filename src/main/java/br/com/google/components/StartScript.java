package br.com.google.components;

import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import br.com.google.functions.AleloLibrary;

public class StartScript {

	public static WebDriver getDriver() throws MalformedURLException 
	{		
		//ariadne.mendes: Configurações para a execução dos testes
		System.setProperty("webdriver.chrome.driver", "C:\\Proton\\Drivers\\chromedriver.exe");

		AleloLibrary.driver = new ChromeDriver();
		WebDriver driver = AleloLibrary.driver;
		
		driver.get("http://www.google.com/");
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();

		System.out.println("Teste iniciado");
		
		//--------------------------------------------------------------
		//ariadne.mendes: Modal para o usuário escolher entre a opção de 'Importar um arquivo' ou 'Definir os valores por parâmetro'
		int resposta;
	    
	    JFrame frame = new JFrame("Importar um arquivo");
	    resposta = JOptionPane.showConfirmDialog(frame,"Gostaria de importar um arquivo?", "Importar um arquivo", JOptionPane.YES_NO_OPTION);
 
	    if (resposta == JOptionPane.YES_OPTION) {
	    // verifica se o usuário clicou no botão YES
	    	AleloLibrary.csvFile = JOptionPane.showInputDialog("Qual o caminho do arquivo?", "");
	    	JOptionPane.showMessageDialog(null, "Obrigado(a).");
	    	AleloLibrary.flagImportArchive = true;
	    } else {
	    	JOptionPane.showMessageDialog(null, "Obrigado(a).");
	    	AleloLibrary.flagImportArchive = false;
	    }
		return driver;
	}
}
