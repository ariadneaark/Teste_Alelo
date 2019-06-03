package br.com.google.execute;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import br.com.google.components.PesquisarDiretor;
import br.com.google.components.StartScript;

public class Run{
	
	public static WebDriver driver;
		
	@Before
	public void StartTest() throws Exception 
	{
		driver = StartScript.getDriver();
	}
				
	@Test
	public void run() throws Exception
	{
		try 
		{
			PesquisarDiretor.run(driver);
	
		}
		catch (Exception e) 
		{
			System.out.println("Erro ao executar o cenário de Consulta.");
			throw new Exception(e);
		}
	}
	
	@After
	public void EndTest() {
		driver.quit();
	}

}
