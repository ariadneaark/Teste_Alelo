package br.com.google.components;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

import br.com.google.functions.AleloLibrary;
import br.com.google.repository.InicialGoogleRepository;
import br.com.google.repository.ResultadoPesquisaRepository;

public class PesquisarDiretor {

	public static String nameDirector = "Terrence Malick; Michael Haneke; Peter Jackson; Tim Burton; J.J. Abrams; M. Night Shyamalan";
	public static String birthDate = "30/11/1943; 23/03/1942; 31/10/1961; 25/08/1958; 27/06/1966; 06/08/1970";
	public static String nameMovies = "Amor Pleno; Amor; King Kong; Alice no Pais das Maravilhas; Cloverfield - Monstro; Fragmentado";
	public static String releaseYear = "2011; 2012; 2002; 2010; 2008; 2016";

	public static void run(WebDriver driver) throws Exception {
		// ariadne.mendes: Validação da resposta do usuário referente aos dados para
		// execução.
		if (AleloLibrary.flagImportArchive == true) {
			try {
				AleloLibrary.lerArquivoCsv();
				System.out.println("Dados coletados do arquivo.");
			} catch (Exception e) {
				AleloLibrary.flagImportArchive = false;
				System.out.println("Dados coletados do parâmetro.");
			}
		}

		// ----------------------------------------------------------------------------------------//
		// ariadne.mendes: Adicionar o Título em cada lista
		AleloLibrary.listDiretorArq.add("Nome do Diretor");
		AleloLibrary.listDataNascArq.add("Data de Nascimento");
		AleloLibrary.listFilmeArq.add("Nome do Filme");
		AleloLibrary.listDataLancArq.add("Data de Lancamento");
		AleloLibrary.listResultSearch.add("Resultado da pesquisa");

		// ariadne.mendes: Separa todos os valores passados por parâmetro para dentro de
		// um Vetor
		String[] nameDirectorVet = nameDirector.split(";");
		String[] birthDateVet = birthDate.split(";");
		String[] nameMoviesVet = nameMovies.split(";");
		String[] releaseYearVet = releaseYear.split(";");

		int i = 0;
		while (i < nameDirectorVet.length) {
			// Adiciona os valores passados por parâmetro para uma lista
			AleloLibrary.listDiretorArq.add(nameDirectorVet[i]);
			AleloLibrary.listDataNascArq.add(birthDateVet[i]);
			AleloLibrary.listFilmeArq.add(nameMoviesVet[i]);
			AleloLibrary.listDataLancArq.add(releaseYearVet[i]);
			i++;
		}

		// ariadne.mendes: Insere na Biblioteca os dados de cada coluna
		AleloLibrary.qntDirector = AleloLibrary.listDiretorArq.toArray().length - 1;
		AleloLibrary.qntBirthDate = AleloLibrary.listDataNascArq.toArray().length - 1;
		AleloLibrary.qntNameMovies = AleloLibrary.listFilmeArq.toArray().length - 1;
		AleloLibrary.qntReleaseYear = AleloLibrary.listDataLancArq.toArray().length - 1;

		// ariadne.mendes: Imprime a quantidade de dados de cada coluna
		System.out.println("Quantidade de Diretores inseridos: " + AleloLibrary.qntDirector);
		System.out.println("Quantidade de Data de Aniversários inseridas: " + AleloLibrary.qntBirthDate);
		System.out.println("Quantidade de Filmes inseridos: " + AleloLibrary.qntNameMovies);
		System.out.println("Quantidade de Datas Lançamento inseridas: " + AleloLibrary.qntReleaseYear);

		System.out.println("----------------------------------");

		// ariadne.mendes: Inserir diretores com base na quantidade de usuários
		// informados
		for (i = 1; i < AleloLibrary.qntDirector + 1; i++) {
			String pesquisa = AleloLibrary.listDiretorArq.get(i) + " " + AleloLibrary.listFilmeArq.get(i);

			// ariadne.mendes: Validação da tela de login e inserção dos dados para pesquisa
			if (AleloLibrary.verifyExistID(driver, InicialGoogleRepository.logoGoogle, 5)) {
				driver.findElement(By.xpath(InicialGoogleRepository.txtPesquisa)).sendKeys(pesquisa + Keys.ENTER);
			} else {
				driver.findElement(By.xpath(InicialGoogleRepository.txtPesquisa)).clear();
				Thread.sleep(1000);
				driver.findElement(By.xpath(InicialGoogleRepository.txtPesquisa)).sendKeys(pesquisa + Keys.ENTER);
			}

			Thread.sleep(1000);

			System.out.println("Pesquisa: " + pesquisa);

			AleloLibrary.listResultSearch
					.add(driver.findElement(By.id(ResultadoPesquisaRepository.lblQtdeResultBusca)).getText());

			System.out.println(AleloLibrary.listResultSearch.get(i));

			try {
				String valorLanc;
				String ano;

				// ariadne.mendes: Validação na tela de pesquisa sobre o Ano de Lançamento do
				// filme
				valorLanc = driver.findElement(By.xpath(ResultadoPesquisaRepository.lblLancamentoFilme.replace("xxx",
						AleloLibrary.listFilmeArq.get(i)))).getText();
				ano = valorLanc.substring(0, 4);

				AleloLibrary.validateTextXPath(driver,
						ResultadoPesquisaRepository.lblLancamentoFilme.replace("xxx", AleloLibrary.listFilmeArq.get(i)),
						ano);
				System.out.println("Data de Lançamento: " + ano);
			} catch (Exception e) {
				System.out.println("Data de Lançamento do filme não encontrado.");
			}

			System.out.println("----------------------------------");

		}

		AleloLibrary.inserirDadosArquivo();

		System.out.println("Teste Finalizado com sucesso.");

	}

}
