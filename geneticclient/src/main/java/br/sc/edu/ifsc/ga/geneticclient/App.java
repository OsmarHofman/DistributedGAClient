package br.sc.edu.ifsc.ga.geneticclient;

import java.util.ArrayList;
import java.util.List;

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
	public static void main(String[] args) {
		int size = 100;
		RatingHandler[] rh = new RatingHandler[size];
		List<DTORating> dto = new ArrayList<>();

		System.out.println("Inicializando populaÃ§Ã£o...");
		Chromosome[] chromosomes = Boot.initialize(size);

		System.out.println("Avaliando primeira geraÃ§Ã£o de cromossomos...");
		DTORating avaliacao = Chromosome.getBestAvaliation(chromosomes);

		// TODO modificar o ponto de parada para um valor mais apropriado
		while (avaliacao.getChromosome().getAvaliation() >= 200) {

			System.out.println("Lendo dados do arquivo XML...");
			DTOServerData serverData = PullData.getAllData(chromosomes);

			System.out.println("Enviando cromossomos para calculo da funÃ§Ã£o de avaliaÃ§Ã£o");
			ConnectionFactory connection = new ConnectionFactory();
			System.setSecurityManager(new SecurityManager());
			connection.conectar("rmi://10.151.33.80:1099/Evaluation", serverData, 1);
			connection.conectar("rmi://10.151.33.112:1099/Evaluation", serverData, 2);
			connection.conectar("rmi://10.151.33.134:1099/Evaluation", serverData, 3);
			connection.conectar("rmi://10.151.33.162:1099/Evaluation", serverData, 4);

			while (connection.getFila() != 0) {
				System.out.println("Processando...");
			}

			dto.addAll(connection.getRespA());
			dto.addAll(connection.getRespB());
			dto.addAll(connection.getRespC());
			dto.addAll(connection.getRespD());

			System.out.println("Calculando FaA...");
			int faA = 0;
			for (int i = 0; i < rh.length; i++) {
				faA += dto.get(i).getChromosome().getAvaliation();
				rh[i] = new RatingHandler();
				rh[i].setFaA(faA);
				rh[i].setChromosomeId(dto.get(i).getId());
			}

			System.out.println("Fazendo seleÃ§Ã£o por elitismo...");
			int percentageChanceOfElitism = 10;
			int proportion = size / percentageChanceOfElitism;
			Chromosome[] eliteChromosomes = Selection.elitismSelection(chromosomes, proportion);

			System.out.println("Fazendo crossover...");
			int percentageChanceOfCrossover = 60;
			Chromosome[] crossedChromosomes = Crossover.cross(rh, faA, chromosomes, eliteChromosomes,
					percentageChanceOfCrossover);

			System.out.println("Fazendo mutaÃ§Ã£o...");
			int percentageChanceOfMutation = 20;
			Chromosome[] nextGeneration = Mutation.randomMutation(crossedChromosomes, percentageChanceOfMutation);

			System.out.println("Avaliando prÃ³xima geraÃ§Ã£o de cromossomos...");
			avaliacao = Chromosome.getBestAvaliation(nextGeneration);
		}

		System.out.println(avaliacao.toString());
	}
}
