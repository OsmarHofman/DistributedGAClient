package br.sc.edu.ifsc.ga.geneticclient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import br.sc.edu.ifsc.ga.algorithm.Boot;
import br.sc.edu.ifsc.ga.algorithm.Crossover;
import br.sc.edu.ifsc.ga.algorithm.Mutation;
import br.sc.edu.ifsc.ga.algorithm.Selection;
import br.sc.edu.ifsc.ga.domain.Chromosome;
import br.sc.edu.ifsc.ga.util.ConnectionFactory;
import br.sc.edu.ifsc.ga.util.DTORating;
import br.sc.edu.ifsc.ga.util.DTOServerData;
import br.sc.edu.ifsc.ga.util.PullData;
import br.sc.edu.ifsc.ga.util.RatingHandler;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) throws InterruptedException {
		int size = 100;
		RatingHandler[] rh = new RatingHandler[size];
		List<DTORating> dto = new ArrayList<>();
		System.out.println("-------------------------------- Inicialização --------------------------------");
		System.out.println("\nInicializando populaÃ§Ã£o...");
		Chromosome[] chromosomes = Boot.initialize(size);

		System.out.println("\nAvaliando primeira geraÃ§Ã£o de cromossomos...");
		DTORating avaliacao = Chromosome.getBestAvaliation(chromosomes);

		// TODO modificar o ponto de parada para um valor mais apropriado

		System.out.println("\nLendo dados do arquivo XML...");
		DTOServerData serverData = PullData.getAllData(chromosomes);

		int count = 0;

		// TODO Verificar a condição da parada que são: 1000 iterações, e avaliacao
		// menor que 4000
		while ((count < 1000 && avaliacao.getChromosome().getAvaliation() < 3800) || count == 0) {

			System.out
					.println("-------------------------------- Geração " + count + " --------------------------------");

			System.out.println("\nEnviando cromossomos para calculo da funcao de avaliacao");
			ConnectionFactory connection = new ConnectionFactory();
			System.setSecurityManager(new SecurityManager());

			/*
			 * connection.conectar("rmi://10.151.33.80:1099/Evaluation", serverData, 1);
			 * connection.conectar("rmi://10.151.33.112:1099/Evaluation", serverData, 2);
			 * connection.conectar("rmi://10.151.33.134:1099/Evaluation", serverData, 3);
			 * connection.conectar("rmi://10.151.33.162:1099/Evaluation", serverData, 4);
			 */

			connection.conectar("rmi://10.151.31.135:1099/Evaluation", serverData, 1);
			connection.conectar("rmi://10.151.31.160:1099/Evaluation", serverData, 2);
			connection.conectar("rmi://10.151.31.198:1099/Evaluation", serverData, 3);
			connection.conectar("rmi://10.151.31.200:1099/Evaluation", serverData, 4);

			while (connection.getFila() != 0) {
				System.out.println("Processando...");
				TimeUnit.SECONDS.sleep(1);
			}
			dto = new ArrayList<DTORating>();
			dto.addAll(connection.getRespA());
			dto.addAll(connection.getRespB());
			dto.addAll(connection.getRespC());
			dto.addAll(connection.getRespD());

			for (int i = 0; i < dto.size(); i++) {
				dto.get(i).setId(i);
			}

			System.out.println("\nCalculando FaA...");
			int faA = 0;
			for (int i = 0; i < rh.length; i++) {
				faA += dto.get(i).getChromosome().getAvaliation();
				rh[i] = new RatingHandler();
				rh[i].setFaA(faA);
				rh[i].setChromosomeId(dto.get(i).getId());
			}

			chromosomes = new Chromosome[size];
			chromosomes = DTORating.getAllChromosomes(dto);

			System.out.println("\nFazendo selecao por elitismo...");
			int percentageChanceOfElitism = 10;
			int proportion = size / percentageChanceOfElitism;
			Chromosome[] eliteChromosomes = Selection.elitismSelection(chromosomes, proportion);

			System.out.println("\nFazendo crossover...");
			int percentageChanceOfCrossover = 60;
			Chromosome[] crossedChromosomes = Crossover.cross(rh, faA, chromosomes, eliteChromosomes,
					percentageChanceOfCrossover);

			System.out.println("\nFazendo mutacao...");
			int percentageChanceOfMutation = 20;
			Chromosome[] nextGeneration = Mutation.randomMutation(crossedChromosomes, percentageChanceOfMutation);

			System.out.println("\nAvaliando proxima geracao de cromossomos...");

			avaliacao = Chromosome.getBestAvaliation(nextGeneration);
			System.out.println(avaliacao.getChromosome().getAvaliation());

			count++;

			serverData.setChromosomes(Arrays.asList(nextGeneration));

		}
		System.out.println("\n\n\n\n\n\n\nAvaliacao Final: " + avaliacao.getChromosome().getAvaliation());
		System.out.println("\nCromossomo escolhido:\n" + avaliacao.getChromosome().toString());
	}
}
