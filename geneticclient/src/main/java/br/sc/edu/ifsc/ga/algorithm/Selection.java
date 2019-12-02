package br.sc.edu.ifsc.ga.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import br.sc.edu.ifsc.ga.domain.Chromosome;

public class Selection {

	public static Chromosome[] elitismSelection(Chromosome[] chromosomes, int proportion) {
		Chromosome[] chosenChromosomes = new Chromosome[proportion];
		List<Chromosome> eliteChromosomes = new ArrayList<>(Arrays.asList(chromosomes.clone()));
		Collections.sort(eliteChromosomes, Comparator.comparing(Chromosome::getAvaliation));
		int count = 0;
		int interation = chromosomes.length - 1;
		for (int i = interation; i > interation - proportion; i--) {
			chosenChromosomes[count] = eliteChromosomes.get(i);
			count++;
		}
		return chosenChromosomes;
	}
}
